package model.deck;

import java.util.Objects;
import java.util.Comparator;

public class Card {

	private final Suit suit;
	private final Rank rank;

	/* PRIVATE CONSTRUCTORS */
	private Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/* PUBLIC STATIC GETTER */
	public static Card get(Rank pRank, Suit pSuit) {
		Objects.requireNonNull(pRank);
		Objects.requireNonNull(pSuit);
		return CARDS[pSuit.ordinal()][pRank.ordinal()];
	}

	/* PRIVATE STATIC STORE */
	private static final Card[][] CARDS = new Card[Suit.values().length][Rank.values().length];

	static {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				CARDS[suit.ordinal()][rank.ordinal()] = new Card(rank, suit);
			}
		}
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue() {
		return rank.getValue();
	}

	public static Comparator<Card> rankFirstComparator() {
		return Comparator.comparing(Card::getRank)
                .thenComparing(Card::getSuit);
	}

	public String toString() {
		char suitIcon = '\u2663';
		if (suit == Suit.DIAMONDS)
			suitIcon = '\u2666';
		if (suit == Suit.HEARTS)
			suitIcon = '\u2665';
		if (suit == Suit.SPADES)
			suitIcon = '\u2660';

		String value = this.getValue() + "";
		if (rank == Rank.ACE) {
			value = "A";
		}
		if (rank == Rank.KING) {
			value = "K";
		}
		if (rank == Rank.QUEEN) {
			value = "Q";
		}
		if (rank == Rank.JACK) {
			value = "J";
		}
		return value + "" + suitIcon;
	}
}
