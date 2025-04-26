package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.deck.Deck;
import model.cribbage.Hand;
import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;

public class HandTest {

    @Test
    public void testAddHand() {
        Deck deck = new Deck();
        Hand hand = new Hand();
        deck.shuffle();
        Card c1 = deck.pop();
        Card c2 = deck.pop();
        Card c3 = deck.pop();
        hand.addCard(c1);
        hand.addCard(c2);
        hand.addCard(c3);
        assertEquals(3, hand.getHand().size());
    }

    @Test
    public void testDiscard() {
        Deck deck = new Deck();
        Hand hand = new Hand();
        deck.shuffle();
        Card c1 = deck.pop();
        Card c2 = deck.pop();
        Card c3 = deck.pop();
        hand.addCard(c1);
        hand.addCard(c2);
        hand.addCard(c3);
        assertEquals(3, hand.getHand().size());
        hand.discard(c3);
        assertEquals(2, hand.getHand().size());
    }

    @Test
    public void testNobs() {
        Hand hand1 = new Hand();
        hand1.addCard(Card.get(Rank.THREE, Suit.CLUBS));
        hand1.addCard(Card.get(Rank.FOUR, Suit.CLUBS)); 
        hand1.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
        hand1.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
        hand1.addCard(Card.get(Rank.EIGHT, Suit.HEARTS)); 
        assertEquals(0, hand1.getNobsSum(Card.get(Rank.THREE, Suit.CLUBS)));
        
        Hand hand2 = new Hand();
        hand2.addCard(Card.get(Rank.THREE, Suit.CLUBS));
        hand2.addCard(Card.get(Rank.JACK, Suit.CLUBS)); 
        hand2.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
        hand2.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
        hand2.addCard(Card.get(Rank.JACK, Suit.HEARTS)); 
        assertEquals(1, hand2.getNobsSum(Card.get(Rank.THREE, Suit.CLUBS)));
    }

    @Test
    public void testGetScore() {
        Hand hand1 = new Hand();
        hand1.addCard(Card.get(Rank.EIGHT, Suit.CLUBS));
        hand1.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
        hand1.addCard(Card.get(Rank.SEVEN, Suit.HEARTS)); 
        hand1.addCard(Card.get(Rank.SIX, Suit.CLUBS)); 
        hand1.addCard(Card.get(Rank.DEUCE, Suit.HEARTS));
        assertEquals(28, hand1.getScore(Card.get(Rank.EIGHT, Suit.CLUBS)));
        
        Hand hand2 = new Hand();
        hand2.addCard(Card.get(Rank.FIVE, Suit.CLUBS));
        hand2.addCard(Card.get(Rank.FIVE, Suit.HEARTS)); 
        hand2.addCard(Card.get(Rank.FIVE, Suit.DIAMONDS)); 
        hand2.addCard(Card.get(Rank.FIVE, Suit.SPADES)); 
        hand2.addCard(Card.get(Rank.JACK, Suit.CLUBS)); 
        assertEquals(51, hand2.getScore(Card.get(Rank.FIVE, Suit.CLUBS)));
    }
    
    @Test
    public void testReset() {
        Hand hand = new Hand();
        hand.addCard(Card.get(Rank.ACE, Suit.SPADES));
        hand.addCard(Card.get(Rank.KING, Suit.HEARTS));
        assertEquals(2, hand.getCards().size());

        hand.reset();
        assertTrue(hand.getCards().isEmpty());
    }
    
    @Test
    public void testGetFlush() {
        Hand hand = new Hand();
        hand.addCard(Card.get(Rank.DEUCE, Suit.DIAMONDS));
        hand.addCard(Card.get(Rank.FIVE, Suit.DIAMONDS));
        hand.addCard(Card.get(Rank.SEVEN, Suit.DIAMONDS));
        hand.addCard(Card.get(Rank.JACK, Suit.DIAMONDS));

        assertEquals(4, hand.getFlushSum(null));
        assertEquals(4, hand.getFlushSum(Card.get(Rank.THREE, Suit.CLUBS)));
        assertEquals(5, hand.getFlushSum(Card.get(Rank.ACE, Suit.DIAMONDS)));
    }
    
    @Test
    public void testGetNobsWithNullStarter() {
        Hand hand = new Hand();
        hand.addCard(Card.get(Rank.JACK, Suit.HEARTS));

        assertEquals(0, hand.getNobsSum(null));
    }
}