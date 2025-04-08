package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CardTest {

    @Test
    public void testFlyweight() {
        Card card1 = Card.get(Rank.ACE, Suit.SPADES);
        Card card2 = Card.get(Rank.ACE, Suit.SPADES);
        assertSame(card1, card2);
    }

    @Test
    public void testGetRankAndSuit() {
        Card card = Card.get(Rank.KING, Suit.HEARTS);
        assertEquals(Rank.KING, card.getRank());
        assertEquals(Suit.HEARTS, card.getSuit());
    }

    @Test
    public void testGetValue() {
        Card aceCard = Card.get(Rank.ACE, Suit.CLUBS);
        assertEquals(1, aceCard.getValue());
        
        Card kingCard = Card.get(Rank.KING, Suit.DIAMONDS);
        assertEquals(13, kingCard.getValue());
        
        Card deuceCard = Card.get(Rank.DEUCE, Suit.SPADES);
        assertEquals(2, deuceCard.getValue());
    }

    @Test
    public void testCompareTo() {
        Card card1 = Card.get(Rank.THREE, Suit.CLUBS);
        Card card2 = Card.get(Rank.THREE, Suit.HEARTS);
        assertEquals(0, card1.compareTo(card2));
        
        Card lowerCard = Card.get(Rank.DEUCE, Suit.CLUBS);
        Card higherCard = Card.get(Rank.THREE, Suit.HEARTS);
        
        assertTrue(lowerCard.compareTo(higherCard) < 0);
        assertTrue(higherCard.compareTo(lowerCard) > 0);
    }

    @Test
    public void testToString() {
        Card aceCard = Card.get(Rank.ACE, Suit.SPADES);
        String expectedAce = "A" + "\u2660";
        assertEquals(expectedAce, aceCard.toString());
        
        Card kingCard = Card.get(Rank.KING, Suit.DIAMONDS);
        String expectedKing = "K" + "\u2666";
        assertEquals(expectedKing, kingCard.toString());
        
        Card queenCard = Card.get(Rank.QUEEN, Suit.HEARTS);
        String expectedQueen = "Q" + "\u2665";
        assertEquals(expectedQueen, queenCard.toString());
        
        Card jackCard = Card.get(Rank.JACK, Suit.CLUBS);
        String expectedJack = "J" + "\u2663"; 
        assertEquals(expectedJack, jackCard.toString());
        
        Card threeCard = Card.get(Rank.THREE, Suit.DIAMONDS);
        String expectedThree = "3" + "\u2666";
        assertEquals(expectedThree, threeCard.toString());
        
        Card tenCard = Card.get(Rank.TEN, Suit.HEARTS);
        String expectedTen = "10" + "\u2665";
        assertEquals(expectedTen, tenCard.toString());
    }

    @Test
    public void testNullArguments() {
        assertThrows(AssertionError.class, () -> {
            Card.get(null, Suit.CLUBS);
        });
        
        assertThrows(AssertionError.class, () -> {
            Card.get(Rank.ACE, null);
        });
    }
}
