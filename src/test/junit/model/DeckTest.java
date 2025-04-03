package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    public void setup() {
        deck = new Deck();
    }

    @Test
    public void testConstructor() {
        Set<Card> uniqueCards = new HashSet<>();
        for (int i = 0; i < 52; i++) {
            Card card = deck.pop();
            assertNotNull(card);
            
            uniqueCards.add(card);
        }
        
        assertEquals(52, uniqueCards.size());
    }

    @Test
    public void testPop() {
        Card topCard = Card.get(Rank.DEUCE, Suit.CLUBS);
        Card poppedCard = deck.pop();
        assertEquals(topCard, poppedCard);
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            for (int i = 0; i < 52; i++) {
                deck.pop();
            }
        });
    }

    @Test
    public void testShuffle() {
        Deck unshuffled = new Deck();
        deck.shuffle();

        Set<Card> setOriginal = new HashSet<>();
        Set<Card> setShuffled = new HashSet<>();
        for (int i = 0; i < 52; i++) {
            setOriginal.add(unshuffled.pop());
            setShuffled.add(deck.pop());
        }

        assertEquals(setOriginal, setShuffled);
    }

    @Test
    public void testReset() {
        deck.pop();
        deck.pop();
        deck.reset();

        Set<Card> cardSet = new HashSet<>();
        for (int i = 0; i < 52; i++) {
            cardSet.add(deck.pop());
        }

        assertEquals(52, cardSet.size());
    }

    @Test
    public void testShuffleAndReset() {
    	for (int i = 0; i < 3; i++) {
            deck.reset();

            deck.shuffle();
            Card popped = deck.pop();
            assertNotNull(popped);

            deck.reset();

            Set<Card> cardSet = new HashSet<>();
            for (int j = 0; j < 52; j++) {
                cardSet.add(deck.pop());
            }
            assertEquals(52, cardSet.size());
        }
    }
}
