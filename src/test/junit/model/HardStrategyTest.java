package player;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cribbage.Board;
import cribbage.Hand;
import deck.Card;
import deck.Rank;
import deck.Suit;

class HardStrategyTest {

	@Test
	void testHardStrategy() {

		Hand hand = new Hand();
		ArrayList<Card> cards = new ArrayList<>();
		
		cards.add(Card.get(Rank.FIVE, Suit.SPADES));
		cards.add(Card.get(Rank.FIVE, Suit.HEARTS));
		cards.add(Card.get(Rank.FIVE, Suit.DIAMONDS));
		cards.add(Card.get(Rank.KING, Suit.CLUBS));
		cards.add(Card.get(Rank.DEUCE, Suit.HEARTS));
		cards.add(Card.get(Rank.THREE, Suit.CLUBS));
		for (Card c : cards) {
			hand.addCard(c);
		}

		Board board = new Board(new ArrayList<>(), null);
		Card starter = Card.get(Rank.FIVE, Suit.CLUBS);

		HardStrategy strategy = new HardStrategy();

		List<Card> discards = strategy.discard(hand, board, starter);

		assertNotNull(discards);
		assertEquals(2, discards.size());

		Set<Card> handSet = new HashSet<>(cards);
		for (Card c : discards) {
			assertTrue(handSet.contains(c));
		}

		assertTrue(discards.contains(Card.get(Rank.DEUCE, Suit.HEARTS)));
		assertTrue(discards.contains(Card.get(Rank.THREE, Suit.CLUBS)));
	}
}
