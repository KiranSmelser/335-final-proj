package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.player.*;
import model.cribbage.Hand;
import model.cribbage.Board;
import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;

class EasyStrategyTest {

	@Test
	void testEasyStrategy() {

		Hand hand = new Hand();
		ArrayList<Card> cards = new ArrayList<>();
		cards.add(Card.get(Rank.FIVE, Suit.HEARTS));
		cards.add(Card.get(Rank.SIX, Suit.SPADES));
		cards.add(Card.get(Rank.SEVEN, Suit.CLUBS));
		cards.add(Card.get(Rank.EIGHT, Suit.DIAMONDS));
		cards.add(Card.get(Rank.NINE, Suit.HEARTS));
		cards.add(Card.get(Rank.TEN, Suit.CLUBS));

		for (Card c : cards) {
			hand.addCard(c);
		}

		EasyStrategy strategy = new EasyStrategy();
		ArrayList<Player> players = new ArrayList<>();
		Board board = new Board(players, null, null);
		Card starter = Card.get(Rank.THREE, Suit.SPADES);

		List<Card> discarded = strategy.discard(hand, board, starter);

		assertEquals(2, discarded.size());

		Set<Card> handSet = new HashSet<>(cards);
		for (Card c : discarded) {
			assertTrue(handSet.contains(c));
		}
	}

    @Test
    void testPlayCardReturnsPlayable() {
        Hand hand = new Hand();
        Card three = Card.get(Rank.THREE, Suit.SPADES);
        Card king = Card.get(Rank.KING, Suit.HEARTS);
        hand.addCard(three);
        hand.addCard(king);
        
        EasyStrategy strategy = new EasyStrategy();
        List<Card> played = new ArrayList<>();
        Card choice = strategy.playCard(hand, played, 28);

        assertNotNull(choice);
        assertEquals(three, choice);
    }

    @Test
    void testPlayCardNoPlayable() {
        Hand hand = new Hand();
        Card ten = Card.get(Rank.TEN, Suit.DIAMONDS);
        hand.addCard(ten);
        
        EasyStrategy strategy = new EasyStrategy();
        List<Card> played = new ArrayList<>();
        Card choice = strategy.playCard(hand, played, 25);
        
        assertNull(choice);
    }
}