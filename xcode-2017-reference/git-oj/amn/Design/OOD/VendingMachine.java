package amazon.ood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// 设计vending machine的题，
// 而且要求找的零钱数量越少越好.
// 我中间定义硬币的时候还因为不知道5分硬币叫啥问了一下

// state design pattern
// NO_SELECTION, HAS_SELECTION
// NO_COIN, COIN_INSERTED 
// SOLD, SOLD_OUT

interface Currency {
	// take some currency return back always us coin?
	// 自己的币种换算成美元硬币
	public Map<Coin, Integer> change(float usCurrency);
}

class USDollar implements Currency {
	private float ratio;

	public USDollar(float ratio) {
		this.ratio = ratio;
	}
	
	public Map<Coin, Integer> change(float currency) {
		currency *= (1 + ratio);
		return Collections.emptyMap();
	}

}

enum Coin {
	PENNY(0.01f), NICKLE(0.05f), DIME(0.10f), QUATER(0.25f);

	private float value;

	private Coin(float v) {
		value = v;
	}

	public float getValue() {
		return this.value;
	}
}

class VItem {
	private ItemInfo info;

	public void setItemInfo(ItemInfo info) {
		this.info = info;
	}

	public ItemInfo getItemInfo() {
		return this.info;
	}
}

class ItemInfo {
	private float price;

	public ItemInfo(float price) {
		this.price = price;
	}

	public float getPrice() {
		return this.price;
	}
}

class Sprite extends Item {
}

class Coke extends Item {
}

class MountainDew extends Item {
}

public class VendingMachine {
	List<Coin> coins;
	List<Coin> currentCoins;
	Map<ItemInfo, List<VItem>> stock;
	// "A1" -> coke1, but we have coke2, coke3 with same price
	Map<String, ItemInfo> itemIdentifiers;
	ItemInfo currentSelection;

	public VendingMachine() {
		this.currentCoins = new ArrayList<>();
	}

	public float selectItem(String selection) {
		this.currentSelection = itemIdentifiers.get(selection);
		return this.currentSelection.getPrice();
	}

	public void insertCoins(List<Coin> coins) {
		this.currentCoins.addAll(coins);
	}

	public VItem executeTrasaction() throws Exception {
		float myMoney = 0;
		for (Coin coin : this.currentCoins) {
			myMoney += coin.getValue();
		}

		if (myMoney < this.currentSelection.getPrice()) {
			throw new NotEnoughMoneyException();
		}

		Iterator<VItem> iterator = stock.get(currentSelection).iterator();
		if (!iterator.hasNext()) {
			throw new NotEnoughItemException();
		}

		refund(this.currentSelection.getPrice() - myMoney); // coin change
		return iterator.next();
	}

	// Return the current coins that has been inserted
	public List<Coin> cancelTransaction() {
		return this.currentCoins;
	}

	public void refillItems(List<VItem> items) {
		for (VItem item : items) {
			this.stock.get(item.getItemInfo()).add(item);
		}
	}

	private List<Coin> refund(float change) {
		// ? coin change
		// minimum number of coins
		// assumption: unlimited supply of coins
		return Collections.emptyList();
	}
}

class NotEnoughMoneyException extends Exception {
}

class NotEnoughItemException extends Exception {
}
