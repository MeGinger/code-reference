package amazon.ood;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

abstract class AddOn {
	protected double price; // per minute
	protected double base; // base cost

	abstract String description();
}

class Talk extends AddOn {
	public Talk(double price, double base) {
		this.price = price;
		this.base = base;
	}

	public String description() {
		return "Cost per minute: " + this.price;
	}
}

class Text extends AddOn {
	public Text(double price, double base) {
		this.price = price;
		this.base = base;
	}

	public String description() {
		return "Cost per message: " + this.price;
	}
}

class Data extends AddOn {
	public Data(double price, double base) {
		this.price = price;
		this.base = base;
	}

	public String description() {
		return "Cost per GB: " + this.price;
	}
}

abstract class Plan {
	abstract Map<AddOn, Integer> getPlan();

	abstract Data getData();

	abstract Text getText();

	abstract Talk getTalk();
}

class UnlimitedDataPlan extends Plan {
	private Map<AddOn, Integer> plan;
	private Data data;
	private Text text;
	private Talk talk;

	public UnlimitedDataPlan() {
		plan = new HashMap<>();

		data = new Data(1, 10);
		plan.put(data, 100);

		text = new Text(0, 10);
		plan.put(text, Integer.MAX_VALUE);

		talk = new Talk(0, 10);
		plan.put(talk, Integer.MAX_VALUE);
	}

	public Map<AddOn, Integer> getPlan() {
		return plan;
	}

	public Data getData() {
		return data;
	}

	public Text getText() {
		return text;
	}

	public Talk getTalk() {
		return talk;
	}
}

class Bill {
	private User user;
	private Plan plan;

	public void updateText(int num) {
	}

	public void updateTalk(int num) {
	}

	public void updateData(int num) {
		Data data = plan.getData();
		Map<AddOn, Integer> map = plan.getPlan();
		map.put(data, map.get(data) - num);
	}

	public double monthlyBill() {
		double cost = 0;
		for (Map.Entry<AddOn, Integer> entry : plan.getPlan().entrySet()) {
			AddOn addOn = entry.getKey();
			Integer count = entry.getValue();
			cost += addOn.base;
			if (count < 0)
				cost += addOn.price * Math.abs(count);
		}
		return cost;
	}
}

public class CellularBillingSystem {
	private Map<User, Bill> userBills = new HashMap<>();

	// called every hour
	public void consumeData(User user, int num) {
		// check if currentTimestamp is in the
		// 手机计划有免费时段，比如晚7点到第二天早7点和周末免费
		long currentTimestamp = new Date().getTime();
		// if () -> early return
		userBills.get(user).updateData(num);
	}

	public double getBill(User user) {
		return userBills.get(user).monthlyBill();
	}
}
