package amazon.ood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

enum VehicleSize {
	Motorcycle, Compact, Large
}

abstract class Vehicle {
	protected VehicleSize size;

	public VehicleSize getSize() {
		return this.size;
	}
}

class Bus extends Vehicle {
	public Bus(VehicleSize size) {
		this.size = size;
	}
}

class Car extends Vehicle {
	public Car(VehicleSize size) {
		this.size = size;
	}
}

class Motorcycle extends Vehicle {
	public Motorcycle(VehicleSize size) {
		this.size = size;
	}
}

enum SpotSize {
	Small, Compact, Large
}

abstract class Spot {
	protected int row;
	protected int spotNumber;
	protected Level level;
	protected boolean available;
	protected SpotSize size;

	public Spot(int row, int spotNumber, Level level, SpotSize size) {
		this.row = row;
		this.spotNumber = spotNumber;
		this.level = level;
		this.size = size;
	}

	public boolean isAvailable() {
		return this.available;
	}

	public void takeSpot() {
		this.available = false;
	}

	public void leaveSpot() {
		this.available = true;
	}
}

class SmallSpot extends Spot {

	public SmallSpot(int row, int spotNumber, Level level, SpotSize size) {
		super(row, spotNumber, level, size);
	}
}

class MiddleSpot extends Spot {
	public MiddleSpot(int row, int spotNumber, Level level, SpotSize size) {
		super(row, spotNumber, level, size);
	}
}

class LargeSpot extends Spot {
	public LargeSpot(int row, int spotNumber, Level level, SpotSize size) {
		super(row, spotNumber, level, size);
	}
}

class Level {
	public int floor;
	private Spot[] spots;
	private int availableSpots = 0; // number of free spots
	private static final int SPOTS_PER_ROW = 10;

	public Level(int flr, int numberSpots) {
		this.floor = flr;
		this.spots = new Spot[numberSpots];
		this.availableSpots = numberSpots;
	}

	public int getAvailableSpots() {
		return availableSpots;
	}

	public void updateAvailableCount(int diff) {
		this.availableSpots -= diff;
	}

}

public class ParkingLot {
	private Level[] levels;
	private final int NUM_LEVELS = 5;
	private float hourlyRate = 5.0f;

	private Queue<Spot> smallSpots = new LinkedList<>();
	private Queue<Spot> middleSpots = new LinkedList<>();
	private Queue<Spot> largeSpots = new LinkedList<>();

	public ParkingLot() {
		levels = new Level[NUM_LEVELS];
		for (int i = 0; i < NUM_LEVELS; i++) {
			levels[i] = new Level(i, 30);
		}
		// initialize spot lists
	}

	public int getAvailbleCount() {
		int sum = 0;
		for (Level level : levels) {
			sum += level.getAvailableSpots();
		}
		return sum;
	}

	/*
	 * 2) The parking lot can park motorcycles, cars, and buses. 3) The parking
	 * lot has motorcycle spots, compact spots, and large spots. 4) A motorcycle
	 * can park in any spot. 5) A car can park in either a single compact spot
	 * or a single large spot. 6) A bus can park in five large spots that are
	 * consecutive and within the same row. It cannot park in small spots.
	 */

	private List<Spot> findSpotsForVehicle(Vehicle vehicle) {
		List<Spot> spots = new ArrayList<>();
		switch (vehicle.getSize()) {
		case Motorcycle:
			spots.add(!smallSpots.isEmpty() ? smallSpots.poll()
					: (!middleSpots.isEmpty() ? middleSpots.poll()
							: (!largeSpots.isEmpty() ? largeSpots.poll() : null)));
			break;
		case Compact:
			spots.add(!middleSpots.isEmpty() ? middleSpots.poll() : (!largeSpots.isEmpty() ? largeSpots.poll() : null));
			break;
		case Large:
			List<Spot> largeSpotList = new ArrayList<>(largeSpots);
			Collections.sort(largeSpotList,
					(a, b) -> a.level.floor != b.level.floor ? a.level.floor - b.level.floor
							: (a.row != b.row ? a.row - b.row
									: (a.spotNumber != b.spotNumber ? a.spotNumber - b.spotNumber : 0)));
			for (int i = 0; i < largeSpotList.size() - 4; i++) {
				boolean find = true;
				for (int j = i; j < i + 4; j++) {
					Spot cur = largeSpotList.get(j);
					Spot nex = largeSpotList.get(j + 1);
					if (cur.level.floor != nex.level.floor || cur.row != nex.row
							|| cur.spotNumber + 1 != nex.spotNumber) {
						find = false;
						break;
					}
				}
				if (find) {
					for (int j = i; j < i + 5; j++) {
						largeSpots.remove(largeSpotList.get(j));
						spots.add(largeSpotList.get(j));
					}
				}
			}
			break;
		}

		return spots;
		// vehicle type -> spot size
		// selection strategy
	}

	/*
	 * Park the vehicle in a spot (or multiple spots). Return false if failed.
	 */
	public boolean parkVehicle(Vehicle vehicle) {
		List<Spot> spots = this.findSpotsForVehicle(vehicle);
		if (spots == null || spots.isEmpty()) {
			return false;
		}

		for (Spot spot : spots) {
			spot.takeSpot();
		}

		// generate ticket
		Ticket ticket = new Ticket(vehicle, spots);
		return true; // ticket
	}
	// public Ticket parkVehicle(Vehicle vehicle) {}

	public void clearSpot(Ticket t) {
		for (Spot spot : t.getSpots()) {
			spot.leaveSpot();
			// add back to queues
			switch (spot.size) {
			case Small:
				smallSpots.offer(spot);
				break;
			case Compact:
				middleSpots.offer(spot);
				break;
			case Large:
				largeSpots.offer(spot);
				break;
			default:
				break;
			}
		}
		System.out.println(this.calculatePrice(t));
	}

	private float calculatePrice(Ticket t) {
		long milliseconds = new Date().getTime() - t.getStartTime().getTime();
		float hours = (float) milliseconds / 1000 / 3600;
		return hours * this.hourlyRate;
	}

	public void displayPrice() {
		System.out.println("Hourly Rate: " + hourlyRate);
	}

	public void displayAvailableSpots() {
		for (int i = 0; i < levels.length; i++) {
			System.out.print("Level" + i + ": ");
			levels[i].getAvailableSpots();
			System.out.println("");
		}
		System.out.println("");
	}
}

class Ticket {
	private Vehicle vehicle;
	private List<Spot> spots;
	private Date startTime;

	public Ticket(Vehicle vehicle, List<Spot> spots) {
		this.vehicle = vehicle;
		this.spots = spots;
		this.startTime = new Date();
	}

	public List<Spot> getSpots() {
		return this.spots;
	}

	public Date getStartTime() {
		return this.startTime;
	}
}
