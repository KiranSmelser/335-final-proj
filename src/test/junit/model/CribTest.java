package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.cribbage.Crib;
import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;

public class CribTest {
    private Crib crib;

    @BeforeEach
    void setUp() {
        crib = new Crib();
    }

    @Test
    void testEmptyCribAndZeroScore() {
        assertTrue(crib.getCards().isEmpty());
        assertEquals(0, crib.score(null));
        assertEquals(0, crib.score(Card.get(Rank.ACE, Suit.SPADES)));
    }
    
    @Test
    void testAddAndGetCards() {
    	Card c1 = Card.get(Rank.ACE, Suit.SPADES);
        Card c2 = Card.get(Rank.KING, Suit.HEARTS);
        crib.addCards(Arrays.asList(c1, c2));
    	
        List<Card> cards = crib.getCards();
        assertEquals(2, cards.size());
        cards.clear();
        assertEquals(2, crib.getCards().size());
    }

    @Test
    void testClear() {
        crib.addCards(Arrays.asList(
            Card.get(Rank.DEUCE, Suit.CLUBS),
            Card.get(Rank.THREE, Suit.HEARTS)
        ));
        assertFalse(crib.getCards().isEmpty());
        crib.clear();
        assertTrue(crib.getCards().isEmpty());
    }

    @Test
    void testFifteen() {
        Card five = Card.get(Rank.FIVE, Suit.DIAMONDS);
        Card ten  = Card.get(Rank.TEN, Suit.SPADES);
        crib.addCards(Arrays.asList(five, ten));
        
        assertEquals(2, crib.score(null));
    }

    @Test
    void testPairs() {
        Card d1 = Card.get(Rank.DEUCE, Suit.CLUBS);
        Card d2 = Card.get(Rank.DEUCE, Suit.HEARTS);
        crib.addCards(Arrays.asList(d1, d2));
        
        assertEquals(2, crib.score(null));
    }

    @Test
    void testNobs() {
        Card jackC = Card.get(Rank.JACK, Suit.CLUBS);
        crib.addCards(Arrays.asList(jackC));

        assertEquals(1, crib.score(Card.get(Rank.ACE, Suit.CLUBS)));
        assertEquals(0, crib.score(Card.get(Rank.ACE, Suit.HEARTS)));
    }

    @Test
    void testFiveCardFlush() {
        List<Card> flushCards = Arrays.asList(
            Card.get(Rank.DEUCE, Suit.HEARTS),
            Card.get(Rank.FOUR, Suit.HEARTS),
            Card.get(Rank.SIX, Suit.HEARTS),
            Card.get(Rank.EIGHT, Suit.HEARTS)
        );
        crib.addCards(flushCards);
        Card starter = Card.get(Rank.KING, Suit.HEARTS);
        assertEquals(5, crib.score(starter));
    }
}
