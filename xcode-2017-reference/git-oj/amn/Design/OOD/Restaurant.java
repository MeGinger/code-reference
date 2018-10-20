package amazon.ood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/*
 * 不能订外卖

能预订座位

MAX_DINETIME 为 2， 意为占用一桌吃饭的最大时长为2小时

如果餐桌被预定，则无法入座

餐馆的桌子有不同大小

餐馆会优先选择适合当前Party最小的空桌
每次调用findTable, takeOrder, checkOut之后都会调用restaurantDescription, 来验证你的程序是否正确
相对设计餐馆 I，Table新增functions 需要实现。相关函数之后会调用restaurantDescription, 来验证你的程序是否正确。
 * */
class NoTableException extends Exception {

	public NoTableException(Party p) {
		super("No table available for party size: " + p.getSize());
	}
}

class Meal {
	private float price;

	public Meal(float price) {
		this.price = price;
	}

	public float getPrice() {
		return this.price;
	}
}

class Order {
	private List<Meal> meals;

	public Order() {
		meals = new ArrayList<Meal>();
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void mergeOrder(Order order) {
		if (order != null) {
			for (Meal meal : order.getMeals()) {
				meals.add(meal);
			}
		}
	}

	public float getBill() {
		int bill = 0;
		for (Meal meal : meals) {
			bill += meal.getPrice();
		}
		return bill;
	}
}

class Party {
	private int size;

	public Party(int size) {
		this.size = size;
	}

	public int getSize() {
		return this.size;
	}
}

class Table implements Comparable<Table> {
	private int id;
	private int capacity;
	private boolean available;
	private Order order;
	List<Date> reservations;

	public Table(int id, int capacity) {
		this.id = id;
		this.capacity = capacity;
		available = true;
		order = null;
		reservations = new ArrayList<>();
	}

	public int getId() {
		return this.id;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public List<Date> getReservation() {
		return reservations;
	}

	public boolean isAvailable() {
		return this.available;
	}

	public void markAvailable() {
		this.available = true;
	}

	public void markUnavailable() {
		this.available = false;
	}

	public Order getCurrentOrder() {
		return this.order;
	}

	public void setOrder(Order o) {
		if (order == null) {
			this.order = o;
		} else {
			if (o != null) {
				this.order.mergeOrder(o);
			}
		}
	}

	@Override
	public int compareTo(Table compareTable) {
		return this.capacity - compareTable.getCapacity();
	}

	public boolean noFollowReservation(Date timeslot) {
		Date prev = new Date(timeslot.getTime() - 2 * 3600 * 1000);
		return !this.reservations.contains(prev) && !this.reservations.contains(timeslot);
	}

	public boolean reserveForDate(Date d) {
		if (this.reservations.contains(d)) {
			return false;
		}
		return this.reservations.add(d);
	}

	public void removeReservation(Date d) {
		this.reservations.remove(d);
	}
}

class Reservation {
	private Table table;
	private Date date;

	public Reservation(Table table, Date date) {
		this.table = table;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public Table getTable() {
		return table;
	}
}

public class Restaurant {
	private List<Table> tables;
	private List<Meal> menu;
	public static final int MAX_DINEHOUR = 2;
	public static final long HOUR = 3600 * 1000;

	public Restaurant() {
		tables = new ArrayList<Table>();
		menu = new ArrayList<Meal>();
	}

	// binary search
	public Table findTable(Party p) throws NoTableException {
		List<Table> candidates = new ArrayList<>();
		for (Table table : tables) {
			if (table.isAvailable() && table.noFollowReservation(new Date())) {
				candidates.add(table);
			}
		}

		return treeMapSearch(candidates, p.getSize());
		// TreeMap?, binary search code
		// int index = Collections.binarySearch(candidates, new Table(0,
		// p.getSize()));
		// if (index < 0) {
		// index = -(index - 1);
		// }
		//
		// return candidates.get(index);
	}

	private Table treeMapSearch(List<Table> tables, int target) {
		TreeMap<Integer, Table> treemap = new TreeMap<>();
		for (Table table : tables) {
			treemap.put(table.getCapacity(), table);
		}
		return treemap.ceilingEntry(target).getValue();
	}

	public void takeOrder(Table t, Order o) {
		t.markUnavailable(); // ?
		t.setOrder(o);
	}

	public float checkOut(Table t) {
		t.markAvailable();
		return t.getCurrentOrder().getBill();
	}

	public List<Meal> getMenu() {
		return menu;
	}

	public void addTable(Table t) {
		this.tables.add(t);
	}

	//
	//
	public Reservation findTableForReservation(Party p, Date date) throws Exception {
		long currentTime = new Date().getTime();
		long pickedTime = date.getTime();
		for (Table table : tables) {
			if (pickedTime - MAX_DINEHOUR * HOUR >= currentTime) {
				if (table.noFollowReservation(date)) {
					return new Reservation(table, date);
				}
			} else {
				if (table.isAvailable()) {
					return new Reservation(table, date);
				}
			}
		}
		throw new Exception();
	}

	public void cancelReservation(Reservation r) {
		Date date = r.getDate();
		r.getTable().removeReservation(date);
	}

	public void redeemReservation(Reservation r) {
		Date date = r.getDate();
		Table table = r.getTable();

		table.markUnavailable();
		table.removeReservation(date);
	}

	public String restaurantDescription() {
		String description = "";
		for (int i = 0; i < tables.size(); i++) {
			Table table = tables.get(i);
			description += ("Table: " + table.getId() + ", table size: " + table.getCapacity() + ", isAvailable: "
					+ table.isAvailable() + ".");
			if (table.getCurrentOrder() == null)
				description += " No current order for this table";
			else
				description += " Order price: " + table.getCurrentOrder().getBill();

			description += ". Current reservation dates for this table are: ";

			for (Date date : table.getReservation()) {
				description += date.toGMTString() + " ; ";
			}

			description += ".\n";
		}
		description += "*****************************************\n";
		return description;
	}
}