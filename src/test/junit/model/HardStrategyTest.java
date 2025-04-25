package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.cribbage.Board;
import model.cribbage.Hand;
import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;
import model.player.HardStrategy;

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

		Board board = new Board(new ArrayList<>(), null, null);
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

    @Test
    void testPlayCardScoresFifteen() {
        Hand hand = new Hand();
        Card two = Card.get(Rank.DEUCE, Suit.HEARTS);
        Card king = Card.get(Rank.KING, Suit.SPADES);
        hand.addCard(two);
        hand.addCard(king);
        
        HardStrategy strategy = new HardStrategy();
        List<Card> played = new ArrayList<>();
        Card choice = strategy.playCard(hand, played, 13);
        
        assertNotNull(choice);
        assertEquals(two, choice);
    }

    @Test
    void testPlayCardNoPlayable() {
        Hand hand = new Hand();
        Card king = Card.get(Rank.KING, Suit.CLUBS);
        hand.addCard(king);
        
        HardStrategy strategy = new HardStrategy();
        List<Card> played = new ArrayList<>();
        Card choice = strategy.playCard(hand, played, 25);
        
        assertNull(choice);
    }

    @Test
    void testPlayCardPrefersPair() {
        Hand hand = new Hand();
        Card twoH = Card.get(Rank.DEUCE, Suit.HEARTS);
        Card three = Card.get(Rank.THREE, Suit.CLUBS);
        hand.addCard(twoH);
        hand.addCard(three);
        
        HardStrategy strategy = new HardStrategy();
        List<Card> played = new ArrayList<>();
        Card twoS = Card.get(Rank.DEUCE, Suit.SPADES);
        played.add(twoS);
        int currentCount = twoS.getValue();
        Card choice = strategy.playCard(hand, played, currentCount);
        
        assertEquals(twoH, choice);
    }

    @Test
    void testPlayCardPrefersRun() {
        Hand hand = new Hand();
        Card four = Card.get(Rank.FOUR, Suit.CLUBS);
        Card five = Card.get(Rank.FIVE, Suit.HEARTS);
        hand.addCard(four);
        hand.addCard(five);
        
        HardStrategy strategy = new HardStrategy();
        List<Card> played = new ArrayList<>();
        Card two = Card.get(Rank.DEUCE, Suit.CLUBS);
        Card three = Card.get(Rank.THREE, Suit.DIAMONDS);
        played.add(two);
        played.add(three);
        int currentCount = two.getValue() + three.getValue();
        Card choice = strategy.playCard(hand, played, currentCount);
        
        assertEquals(four, choice);
    }
}