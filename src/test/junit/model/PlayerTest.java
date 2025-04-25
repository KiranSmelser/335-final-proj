package model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;
import model.player.Player;

import java.util.List;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player("Test") {
            @Override
            public List<Card> selectDiscards(Card starter) {
                return null;
            }
        };
    }

    @Test
    public void testGetName() {
        assertEquals("Test", player.getName());
    }

    @Test
    public void testAddCard() {
        Card c = Card.get(Rank.FIVE, Suit.HEARTS);
        player.addCard(c);

        assertTrue(player.getHand().getCards().contains(c));
    }

    @Test
    public void testSortCards() {
        Card c1 = Card.get(Rank.KING, Suit.CLUBS);
        Card c2 = Card.get(Rank.FIVE, Suit.HEARTS);
        player.addCard(c1);
        player.addCard(c2);

        player.sortCards();

        List<Card> cards = player.getHand().getCards();
        assertEquals(c2, cards.get(0));
        assertEquals(c1, cards.get(1));
    }

    @Test
    public void testPlayCardReturnsPlayable() {
        player.addCard(Card.get(Rank.SIX, Suit.SPADES));
        player.addCard(Card.get(Rank.KING, Suit.HEARTS));

        List<Card> playedCards = Arrays.asList();
        int currentCount = 25;

        Card selected = player.playCard(playedCards, currentCount);

        assertNotNull(selected);
        assertTrue(selected.getValue() + currentCount <= 31);
    }

    @Test
    public void testPlayCardReturnsNull() {
        player.addCard(Card.get(Rank.KING, Suit.SPADES));
        player.addCard(Card.get(Rank.KING, Suit.HEARTS));

        List<Card> playedCards = Arrays.asList();
        int currentCount = 30;

        Card selected = player.playCard(playedCards, currentCount);

        assertNull(selected);
    }

    @Test
    public void testIncrementWins() {
        assertEquals(0, player.getWins());
        player.incrementWins();
        assertEquals(1, player.getWins());
    }

    @Test
    public void testResetHand() {
        player.addCard(Card.get(Rank.FIVE, Suit.HEARTS));
        player.resetHand();

        assertEquals(0, player.getHand().getCards().size());
    }
}
