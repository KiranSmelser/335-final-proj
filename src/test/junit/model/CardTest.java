package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;
import java.util.List;
import java.util.Arrays;

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
        assertEquals(Rank.ACE.getValue(), aceCard.getValue());
        
        Card kingCard = Card.get(Rank.KING, Suit.DIAMONDS);
        assertEquals(Rank.KING.getValue(), kingCard.getValue());
        
        Card deuceCard = Card.get(Rank.DEUCE, Suit.SPADES);
        assertEquals(Rank.DEUCE.getValue(), deuceCard.getValue());
    }

    @Test
    public void testComparatorCompare() {
        Card card1 = Card.get(Rank.THREE, Suit.CLUBS);
        Card card2 = Card.get(Rank.THREE, Suit.HEARTS);
        assertTrue(Card.rankFirstComparator().compare(card1, card2) < 0);
        assertTrue(Card.rankFirstComparator().compare(card2, card1) > 0);

        Card lowerCard = Card.get(Rank.DEUCE, Suit.CLUBS);
        Card higherCard = Card.get(Rank.THREE, Suit.HEARTS);
        assertTrue(Card.rankFirstComparator().compare(lowerCard, higherCard) < 0);
        assertTrue(Card.rankFirstComparator().compare(higherCard, lowerCard) > 0);
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
        assertThrows(NullPointerException.class, () -> Card.get(null, Suit.CLUBS));
        assertThrows(NullPointerException.class, () -> Card.get(Rank.ACE, null));
    }
    
    @Test
    public void testRankFirstComparator() {
        List<Card> cards = Arrays.asList(
            Card.get(Rank.THREE, Suit.HEARTS),
            Card.get(Rank.DEUCE, Suit.SPADES),
            Card.get(Rank.DEUCE, Suit.CLUBS),
            Card.get(Rank.THREE, Suit.CLUBS)
        );
        cards.sort(Card.rankFirstComparator());
        assertEquals(Card.get(Rank.DEUCE, Suit.CLUBS), cards.get(0));
        assertEquals(Card.get(Rank.DEUCE, Suit.SPADES), cards.get(1));
        assertEquals(Card.get(Rank.THREE, Suit.CLUBS), cards.get(2));
        assertEquals(Card.get(Rank.THREE, Suit.HEARTS), cards.get(3));
    }
}
