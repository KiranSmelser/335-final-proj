package model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({ CardTest.class, DeckTest.class })
public class ModelTests {
  
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
		assertEquals(hand.getHand().size(), 3);
		
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
		assertEquals(hand.getHand().size(), 3);
		hand.discard(c3);
		assertEquals(hand.getHand().size(), 2);
		
	}
	
	@Test
	public void testSum() {
		Hand hand1 = new Hand();
		hand1.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand1.addCard(Card.get(Rank.FOUR, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.HEARTS)); 
		assertEquals(hand1.getNumSum(), 8);
		
		Hand hand2 = new Hand();
		hand2.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.DEUCE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.FOUR, Suit.CLUBS));
		assertEquals(hand2.getNumSum(), 0);
		
		Hand hand3 = new Hand();
		hand3.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand3.addCard(Card.get(Rank.ACE, Suit.HEARTS));
		hand3.addCard(Card.get(Rank.ACE, Suit.SPADES));
		hand3.addCard(Card.get(Rank.JACK, Suit.DIAMONDS));
		hand3.addCard(Card.get(Rank.FIVE, Suit.DIAMONDS));
		assertEquals(hand3.getNumSum(), 2);
		
		Hand hand4 = new Hand();
		hand4.addCard(Card.get(Rank.FIVE, Suit.CLUBS));
		hand4.addCard(Card.get(Rank.FIVE, Suit.HEARTS)); 
		hand4.addCard(Card.get(Rank.FIVE, Suit.DIAMONDS)); 
		hand4.addCard(Card.get(Rank.FIVE, Suit.SPADES));
		assertEquals(hand4.getNumSum(), 8);
		
	}
	
	@Test
	public void testPairs() {
		Hand hand1 = new Hand();
		hand1.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand1.addCard(Card.get(Rank.FOUR, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.HEARTS)); 
		assertEquals(hand1.getPairsSum(), 2);
		
		Hand hand2 = new Hand();
		hand2.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.DEUCE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.FOUR, Suit.CLUBS));
		assertEquals(hand2.getPairsSum(), 0);
		
		Hand hand3 = new Hand();
		hand3.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand3.addCard(Card.get(Rank.ACE, Suit.HEARTS));
		hand3.addCard(Card.get(Rank.ACE, Suit.SPADES));
		hand3.addCard(Card.get(Rank.JACK, Suit.DIAMONDS));
		hand3.addCard(Card.get(Rank.FIVE, Suit.DIAMONDS));
		assertEquals(hand3.getPairsSum(), 6);
		
		Hand hand4 = new Hand();
		hand4.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand4.addCard(Card.get(Rank.ACE, Suit.HEARTS));
		hand4.addCard(Card.get(Rank.ACE, Suit.SPADES));
		hand4.addCard(Card.get(Rank.ACE, Suit.DIAMONDS));
		hand4.addCard(Card.get(Rank.FIVE, Suit.DIAMONDS));
		assertEquals(hand4.getPairsSum(), 12);
		
		Hand hand5 = new Hand();
		hand5.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand5.addCard(Card.get(Rank.ACE, Suit.HEARTS));
		hand5.addCard(Card.get(Rank.FIVE, Suit.SPADES));
		hand5.addCard(Card.get(Rank.JACK, Suit.DIAMONDS));
		hand5.addCard(Card.get(Rank.FIVE, Suit.DIAMONDS));
		assertEquals(hand5.getPairsSum(), 4);
	}
	
	@Test
	public void testRun() {
		Hand hand1 = new Hand();
		hand1.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand1.addCard(Card.get(Rank.FOUR, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.HEARTS)); 
		assertEquals(hand1.getRunSum(), 0);
		
		Hand hand2 = new Hand();
		hand2.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.DEUCE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.FOUR, Suit.CLUBS));
		assertEquals(hand2.getRunSum(), 4);
		
		Hand hand3 = new Hand();
		hand3.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand3.addCard(Card.get(Rank.DEUCE, Suit.CLUBS));
		hand3.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand3.addCard(Card.get(Rank.FIVE, Suit.CLUBS));
		assertEquals(hand3.getRunSum(), 3);
		
		Hand hand4 = new Hand();
		hand4.addCard(Card.get(Rank.DEUCE, Suit.CLUBS));
		hand4.addCard(Card.get(Rank.ACE, Suit.CLUBS));
		hand4.addCard(Card.get(Rank.FOUR, Suit.CLUBS));
		hand4.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand4.addCard(Card.get(Rank.FIVE, Suit.CLUBS));
		assertEquals(hand4.getRunSum(), 5);
		
	}
	
	@Test
	public void testFlush() {
		Hand hand1 = new Hand();
		hand1.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand1.addCard(Card.get(Rank.FOUR, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.HEARTS)); 
		assertEquals(hand1.getFlushSum(), 4);
		
		Hand hand2 = new Hand();
		hand2.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.FOUR, Suit.CLUBS)); 
		hand2.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand2.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
		hand2.addCard(Card.get(Rank.NINE, Suit.CLUBS)); 
		assertEquals(hand2.getFlushSum(), 5);
		
		Hand hand3 = new Hand();
		hand3.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand3.addCard(Card.get(Rank.FOUR, Suit.CLUBS)); 
		hand3.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand3.addCard(Card.get(Rank.EIGHT, Suit.HEARTS)); 
		hand3.addCard(Card.get(Rank.EIGHT, Suit.HEARTS)); 
		assertEquals(hand3.getFlushSum(), 0);
		
		Hand hand4 = new Hand();
		hand4.addCard(Card.get(Rank.EIGHT, Suit.CLUBS));
		hand4.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand4.addCard(Card.get(Rank.SEVEN, Suit.HEARTS)); 
		hand4.addCard(Card.get(Rank.SIX, Suit.CLUBS)); 
		hand4.addCard(Card.get(Rank.DEUCE, Suit.HEARTS));
		assertEquals(hand4.getRunSum(), 6);
		
		Hand hand5 = new Hand();
		hand5.addCard(Card.get(Rank.EIGHT, Suit.CLUBS));
		hand5.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand5.addCard(Card.get(Rank.SEVEN, Suit.HEARTS)); 
		hand5.addCard(Card.get(Rank.SIX, Suit.CLUBS)); 
		hand5.addCard(Card.get(Rank.SEVEN, Suit.HEARTS));
		assertEquals(hand5.getRunSum(), 9);
	}
	
	@Test
	public void testNobs() {
		Hand hand1 = new Hand();
		hand1.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand1.addCard(Card.get(Rank.FOUR, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.EIGHT, Suit.HEARTS)); 
		assertEquals(hand1.getNobsSum(Card.get(Rank.THREE, Suit.CLUBS)), 0);
		
		Hand hand2 = new Hand();
		hand2.addCard(Card.get(Rank.THREE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.JACK, Suit.CLUBS)); 
		hand2.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand2.addCard(Card.get(Rank.EIGHT, Suit.CLUBS)); 
		hand2.addCard(Card.get(Rank.JACK, Suit.HEARTS)); 
		assertEquals(hand2.getNobsSum(Card.get(Rank.THREE, Suit.CLUBS)), 1);
	}
	
	@Test
	public void testGetScore() {
		Hand hand1 = new Hand();
		hand1.addCard(Card.get(Rank.EIGHT, Suit.CLUBS));
		hand1.addCard(Card.get(Rank.SEVEN, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.SEVEN, Suit.HEARTS)); 
		hand1.addCard(Card.get(Rank.SIX, Suit.CLUBS)); 
		hand1.addCard(Card.get(Rank.DEUCE, Suit.HEARTS));
		assertEquals(hand1.getScore(Card.get(Rank.EIGHT, Suit.CLUBS)), 16);
		
		Hand hand2 = new Hand();
		hand2.addCard(Card.get(Rank.FIVE, Suit.CLUBS));
		hand2.addCard(Card.get(Rank.FIVE, Suit.HEARTS)); 
		hand2.addCard(Card.get(Rank.FIVE, Suit.DIAMONDS)); 
		hand2.addCard(Card.get(Rank.FIVE, Suit.SPADES)); 
		hand2.addCard(Card.get(Rank.JACK, Suit.CLUBS)); 
		assertEquals(hand2.getScore(Card.get(Rank.FIVE, Suit.CLUBS)), 29);
	}
}
