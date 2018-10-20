package amazon.ood;

import java.util.ArrayList;

// a property of a card
enum Suit {
	// 红桃，梅花。。
	Club(0), Diamond(1), Heart(2), Spade(3);
	private int value;

	private Suit(int v) {
		value = v;
	}

	public int getValue() {
		return value;
	}

	public static Suit getSuitFromValue(int value) {
		// need implementations
		return null;
	}
}

abstract class Card {
	private boolean available = true;
	/*
	 * number or face that's on card - a number 2 through 10, or 11 for Jack, 12
	 * for Queen, 13 for King, or 1 for Ace
	 */
	protected int faceValue;
	protected Suit suit;

	public Card(int c, Suit s) {
		faceValue = c;
		suit = s;
	}

	public abstract int value();

	public Suit suit() {
		return suit;
	}

	/* Checks if the card is available to be given out to someone */
	public boolean isAvailable() {
		return available;
	}

	public void markUnavailable() {
		available = false;
	}

	public void markAvailable() {
		available = true;
	}
}

// standard 52-card deck: 一副牌
// dealing some cards: 发牌
class Deck<T extends Card> {
	private ArrayList<T> cards; // all cards, dealt or not
	private int dealtlndex = 0; // marks first undealt card

	public void setDeckOfCards(ArrayList<T> deckOfCards) {
		cards = deckOfCards;
	}

	public void shuffle() {
		// need implementations
	}

	public int remainingCards() {
		return cards.size() - dealtlndex;
	}

	// 发number张牌
	public T[] dealHand(int number) {
		// need implementations
		return null;
	}

	// 发一张牌
	public T dealCard() {
		// need implementations
		return null;
	}
}

// 一手牌
class Hand<T extends Card> {
	protected ArrayList<T> cards = new ArrayList<T>();

	// 计算这手牌的得分
	public int score() {
		int score = 0;
		for (T card : cards) {
			score += card.value();
		}
		return score;
	}

	// 加一张牌
	public void addCard(T card) {
		cards.add(card);
	}
}

class BlackJackHand extends Hand<BlackJackCard> {

	/*
	 * There are multiple possible scores for a blackjack hand, since aces have
	 * multiple values. Return the highest possible score that's under 21, or
	 * the lowest score that's over
	 */
	public int score() {
		ArrayList<Integer> scores = possibleScores();
		int maxUnder = Integer.MIN_VALUE;
		int minOver = Integer.MAX_VALUE;
		for (int score : scores) {
			if (score > 21 && score < minOver) {
				minOver = score;
			} else if (score <= 21 && score > maxUnder) {
				maxUnder = score;
			}
		}
		return maxUnder == Integer.MIN_VALUE ? minOver : maxUnder;
	}

	/*
	 * return a list of all possible scores this hand could have (evaluating
	 * each ace as both 1 and 11
	 */
	// DFS
	private ArrayList<Integer> possibleScores() {
		// need implementations
		return null;
	}

	public boolean busted() {
		return score() > 21;
	}

	public boolean is21() {
		return score() == 21;
	}

	// Get 21 points on the player's first two cards (called a "blackjack" or
	// "natural"), without a dealer blackjack;
	public boolean isBlackJack() {
		// need implementations
		return false;
	}
}

class BlackJackCard extends Card {

	public BlackJackCard(int c, Suit s) {
		super(c, s);
	}

	public int value() {
		if (isAce())
			return 1;
		else if (faceValue >= 11 && faceValue <= 13)
			return 10;
		else
			return faceValue;
	}

	public int minValue() {
		if (isAce())
			return 1;
		else
			return value();
	}

	public int maxValue() {
		if (isAce())
			return 11;
		else
			return value();
	}

	public boolean isAce() {
		return faceValue == 1;
	}

	public boolean isFaceCard() {
		return faceValue >= 11 && faceValue <= 13;
	}
}

public class BlackJack {
	// single responsibility
	// dealHand, dealCard & addCard are independent - it is generic
}
