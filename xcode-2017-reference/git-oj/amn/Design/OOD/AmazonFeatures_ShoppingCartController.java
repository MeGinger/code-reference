package amazon.ood;

import java.util.HashMap;
import java.util.Map;

class Coupon {
	public int couponId;
	public String code;
	public float rate;
}

// We already have
// class Customer {
// public int customerId;
// public String customerName;
// }
//
// class Item {
// public int itemId;
// public String itemName;
// }

class ShoppingCart {
	private Map<Item, Integer> shoppingCart;
	private float sum;

	public void addItem(Item item, int quantity) {
		this.shoppingCart.put(item, this.shoppingCart.getOrDefault(item, 0) + quantity);
		this.sum += item.price * quantity;
	}

	// removeItem

	public void applyCoupon(Coupon coupon) {
		this.sum *= coupon.rate;
	}

	public void displayTotalPrice() {
		System.out.println(this.sum);
	}

	public float calcualteTotalPrice() {
		return this.sum;
	}
}

// https://www.careercup.com/question?id=5089838692827136
// Add item in cart
// save cart item in data base
// Update cart amount on item added in art
// Calculate total price
// Apply coupon
// Get quantity
// restrict user can not add more than 10 same items in cart
// Remove cart item remove on request
// send notification to user that item going to be out of stock
// remove item if item is removed from inventory and message and mail to user
// Server side Handle thousands of request per second - use load balancer
// Data base selection data base sql or no sql - nosql store data in form of
// tree - design sql db design
public class AmazonFeatures_ShoppingCartController {
	private Map<Customer, ShoppingCart> shoppingCartByCustomer;

	public AmazonFeatures_ShoppingCartController() {
		this.shoppingCartByCustomer = new HashMap<>();
	}

	public void addItemInCart(Customer cutomer, Item item, int quantity) {
		this.shoppingCartByCustomer.get(cutomer).addItem(item, quantity);
	}

	public void applyCoupon(Customer cutomer, Coupon coupon) {
		this.shoppingCartByCustomer.get(cutomer).applyCoupon(coupon);
	}
}
