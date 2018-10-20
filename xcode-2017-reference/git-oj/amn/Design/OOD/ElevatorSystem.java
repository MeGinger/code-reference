package amazon.ood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum EDirection {
	UP, DOWN
}

enum Status {
	UP, DOWN, IDLE
}

class Request {
	private int level;

	public Request(int l) {
		level = l;
	}

	public int getLevel() {
		return level;
	}
}

class ExternalRequest extends Request {

	private EDirection direction;

	public ExternalRequest(int l, EDirection d) {
		super(l);
		this.direction = d;
	}

	public EDirection getDirection() {
		return direction;
	}
}

class InternalRequest extends Request {

	public InternalRequest(int l) {
		super(l);
	}
}

class ElevatorButton {
	private int level;
	private Elevator elevator; // has-a

	public ElevatorButton(int level, Elevator e) {
		this.level = level;
		this.elevator = e;
	}

	public void pressButton() {
		InternalRequest request = new InternalRequest(level);
		elevator.handleInternalRequest(request);
	}
}

class Elevator {

	private List<ElevatorButton> buttons;

	private List<Boolean> upStops;
	private List<Boolean> downStops;

	public int currLevel; // 电梯需要知道当前在哪一层
	public Status status; // 电梯至少需要三种状态

	public float weightLimit;
	private float currentWeight;

	public Elevator(int n) {
		buttons = new ArrayList<ElevatorButton>();
		upStops = new ArrayList<Boolean>();
		downStops = new ArrayList<Boolean>();
		currLevel = 0;
		status = Status.IDLE;

		for (int i = 0; i < n; i++) {
			upStops.add(false);
			downStops.add(false);
		}
	}

	public void insertButton(ElevatorButton eb) {
		buttons.add(eb);
	}

	// ElevatorSystem makes sure this ExternalRequest is properly sent to an
	// Elevator
	// invalid: Elevator (currentLevel: 3, status: UP) and ExternalRequest
	// (level: 2, status: UP)
	public void handleExternalRequest(ExternalRequest r) {
		if (r.getDirection() == EDirection.UP) {
			upStops.set(r.getLevel() - 1, true);

			if (noRequests(downStops)) { // elevator might be Status.IDLE
				status = Status.UP; // start up if idle
			}

		} else if (r.getDirection() == EDirection.DOWN) {
			downStops.set(r.getLevel() - 1, true);

			if (noRequests(upStops)) {
				status = Status.DOWN; // start up if idle
			}
		}
	}

	public void handleInternalRequest(InternalRequest r) {
		// check if invalid
		// add to stop list if valid

		if (status == Status.UP && currLevel + 1 <= r.getLevel()) {
			upStops.set(r.getLevel() - 1, true); // index: level - 1
		} else if (status == Status.DOWN && currLevel + 1 >= r.getLevel()) {
			downStops.set(r.getLevel() - 1, true);
		} else {
			// invalid - 内部request不能出现: Elevator (level: 3, status: UP),
			// InteralRequest (level: 1)
			// make sense: 现实生活也这样
		}
	}

	public void openGate() throws Exception {
		// update stop list
		// update currLevel
		if (status == Status.UP) {
			for (int i = 0; i < upStops.size(); i++) {
				// the below currLevel is the last position,
				// and currLevel % upStops.size() checking the next one
				int checkLevel = (currLevel + i) % upStops.size();
				if (upStops.get(checkLevel) == true) {
					currLevel = checkLevel; // checkLevel here is this actual
											// current level
					upStops.set(checkLevel, false);
					break;
				}
			}
		} else if (status == Status.DOWN) {
			// status: Down
			// prev currentLevel: 1
			// currentLevel: 3
			for (int i = 0; i < downStops.size(); i++) {
				int checkLevel = (currLevel - i + downStops.size()) % downStops.size();
				if (downStops.get(checkLevel) == true) {
					currLevel = checkLevel;
					downStops.set(checkLevel, false);
					break;
				}
			}
		}

	}

	public void closeGate() throws Exception {
		// check if overweight
		if (this.currentWeight > this.weightLimit) {
			throw new Exception();
		}

		// change status if empty requests
		if (status == Status.IDLE) {
			// this elevator is IDEL, close gate means: more user coming into
			// elevator
			if (noRequests(downStops)) {
				status = Status.UP;
				return;
			}
			if (noRequests(upStops)) {
				status = Status.DOWN;
				return;
			}
		} else if (status == Status.UP) {
			if (noRequests(upStops)) {
				if (noRequests(downStops)) {
					status = Status.IDLE;
				} else
					status = Status.DOWN;
			}
		} else {
			if (noRequests(downStops)) {
				if (noRequests(upStops)) {
					status = Status.IDLE;
				} else
					status = Status.UP;
			}
		}
	}

	public void updateCurrentWeight(float currentWeight) {
		this.currentWeight = currentWeight;
	}

	public float getCurrentWeight() {
		return this.currentWeight;
	}

	private boolean noRequests(List<Boolean> stops) {
		for (int i = 0; i < stops.size(); i++) {
			if (stops.get(i)) {
				return false;
			}
		}
		return true;
	}

	public String elevatorStatusDescription() {
		String description = "Currently elevator status is : " + status + ".\nCurrent level is at: " + (currLevel + 1) // !!!
																														// +
																														// 1
				+ ".\nup stop list looks like: " + upStops + ".\ndown stop list looks like:  " + downStops
				+ ".\n*****************************************\n";
		return description;
	}
}

// strategy pattern - handleRequestStrategy
public class ElevatorSystem {
	private List<Elevator> elevators;

	private float ratio;

	private HandleRequestStrategy strategy;
	
	// 电梯调度算法
	// 每个电梯响应乘客请求使乘客获得服务的次序是由其发出请求的乘客的位置与当前电梯位置之间的距离来决定的
	// 它进行寻找楼层的优化
	public void handleExternalRequests(ExternalRequest r) {
		// find elevator candidates
		// Among available elevator pool
		// 1. closest to user/request
		// 2. not close to overweight
		int min = Integer.MAX_VALUE;
		Elevator candidate = null;
		for (Elevator elevator : elevators) {
			if (elevator.getCurrentWeight() > elevator.weightLimit * ratio) {
				continue;
			}

			if (elevator.status == Status.IDLE
					|| elevator.status == Status.UP && r.getDirection() == EDirection.UP
							&& elevator.currLevel <= r.getLevel()
					|| elevator.status == Status.DOWN && r.getDirection() == EDirection.DOWN
							&& elevator.currLevel >= r.getLevel()) {
				if (min > Math.abs(r.getLevel() - elevator.currLevel)) {
					min = Math.abs(r.getLevel() - elevator.currLevel);
					candidate = elevator;
				}
			}
		}
		candidate.handleExternalRequest(r);
	}
	
	public void handleExternalRequestsByStrategy(ExternalRequest request) {
		this.strategy.hanldeRequest(request, elevators);
	}
	
	public void setStrategy(HandleRequestStrategy strategy) {
		this.strategy = strategy;
	}
}

interface HandleRequestStrategy {
	void hanldeRequest(ExternalRequest request, List<Elevator> elevators);
}

class RandomHandleRequestStrategy implements HandleRequestStrategy {
	Random random = new Random();

	public void hanldeRequest(ExternalRequest request, List<Elevator> elevators) {
		int n = random.nextInt(elevators.size());
		elevators.get(n).handleExternalRequest(request);
	}
}

class AlwaysOneElevatorHandleRequestStrategy implements HandleRequestStrategy {
	Random random = new Random();

	public void hanldeRequest(ExternalRequest request, List<Elevator> elevators) {
		elevators.get(0).handleExternalRequest(request);
	}
}