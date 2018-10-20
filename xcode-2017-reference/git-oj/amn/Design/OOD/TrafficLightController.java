package amazon.ood;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum TrafficLightColor {
	GREEN, YELLOW, RED;
}

// 设计一个traffic light
// system，要求是从南到北的十个红绿灯一开始都是红灯，如果有辆车从最南端开到北端，会触发第一个灯绿，并且接下来的灯能逐渐变绿让车能一直开到北边不需要停。
// 这题我回答的是写一个light
// class，用一个state表示红绿黄，然后写一个function，利用thread.sleep控制变化时间，同时在主函数里面不断唤醒下一个红绿灯。

// the traffic light needs to set its timing
// the traffic light needs to be able to update its status
// the traffic light needs to signal that a particular place
// on the road is blocked due to its current color

// The TrafficLight class is responsible for timing the light changes and
// signaling the current color.
class TrafficLight {
	private TrafficLightColor currentColor;
	private int greentime;
	private int redtime;
	private int yellowtime;
	private long startTime;

	// the duration of the red light should
	// be the sum of the green and yellow light in the opposite
	// direction.

	// Knowing that the yellow light has a fixed
	// duration of six cycles the user only needs to specify the
	// duration of green light in two directions (north/south vs.
	// east/west).
	public TrafficLight(int greentime, int redtime, int yellowtime) {
		this.greentime = greentime;
		this.redtime = redtime;
		this.yellowtime = yellowtime;
		this.startTime = new Date().getTime();
	}

	public void start() {
		// thread.sleep()
		while (true) {
			signal();
			// thread.sleep()
			changeColor();
		}
	}

	public void changeColor() {
		// G->Y->R ->G
		switch (this.currentColor) {
		case GREEN:
			this.currentColor = TrafficLightColor.YELLOW;
			break;
		case YELLOW:
			this.currentColor = TrafficLightColor.RED;
			break;
		case RED:
			this.currentColor = TrafficLightColor.GREEN;
			break;
		}
	}

	public TrafficLightColor signal() {
		return this.currentColor;
	}
}

// road (one lane of traffic - a static structure)
class Road {
	private TrafficLight trafficLight;
	private boolean walk;

	public Road(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}

	public void start() {
		this.trafficLight.start();
	}

	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}

	public void setWalk() {
		this.walk = true;
	}

	public boolean startWalk() {
		// observer design pattern
		// once traffic light is green
		// return true;
		return false;
	}
}

public class TrafficLightController {
	private List<Road> roads;

	public TrafficLightController(List<Road> roads) {
		this.roads = roads;
	}

	public TrafficLightController() {
		this.roads = new ArrayList<>(4);
	}

	public void init(int greentime, int redtime, int yellowtime) {
		for (int i = 0; i < roads.size(); i++) {
			roads.add(new Road(new TrafficLight(greentime, redtime, yellowtime)));
		}
	}

	public void start() {
		for (Road road : roads) {
			road.start();
		}
	}

	public void addRoad(Road road) {
		this.roads.add(road);
	}

	public void removeRoad(Road road) {
		this.roads.remove(road);
	}
}
