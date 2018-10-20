package amazon.ood;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class Customer {
	public int customerId;
	public String customerName;
}

class Item {
	public int itemId;
	public String itemName;
	public float price;
}

public class AmazonFeatures_Latest3ViewedItemdByCustomer {
	private Map<Customer, Queue<Item>> latest3ViewedItemsByCustomer;

	public AmazonFeatures_Latest3ViewedItemdByCustomer() {
		this.latest3ViewedItemsByCustomer = new HashMap<>();
	}

	public void update(Customer customer, Item viewedItem) {
		Queue<Item> latest3ViewedItems = latest3ViewedItemsByCustomer.computeIfAbsent(customer,
				k -> new LinkedList<>());

		if (latest3ViewedItems.size() == 3) {
			latest3ViewedItems.poll();
		}

		// size < 3
		latest3ViewedItems.add(viewedItem);
	}

	public void display(Customer customer) {
		Queue<Item> latest3ViewedItems = this.latest3ViewedItemsByCustomer.get(customer);
		if (latest3ViewedItems == null) {
			// ...
		}
		// ...
	}
}
