package model;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.cribbage.*;
import model.deck.Card;
import model.deck.Deck;
import model.player.Player;

public class CribbageTest {
    private Cribbage game;
    private Player p1, p2;

    @BeforeEach
    public void setUp() {
        p1 = new Player("Alice") {
            @Override
            public List<Card> selectDiscards(Card starter) {
                List<Card> hand = getHand().getCards();
                return List.of(hand.get(0), hand.get(1));
            }
        };
        p2 = new Player("Bob") {
            @Override
            public List<Card> selectDiscards(Card starter) {
                List<Card> hand = getHand().getCards();
                return List.of(hand.get(0), hand.get(1));
            }
        };
        game = new Cribbage(List.of(p1, p2), null);
    }

    @Test
    public void testGetPlayers() {
        List<Player> players = game.getPlayers();
        
        assertEquals(2, players.size());
        assertSame(p1, players.get(0));
        assertSame(p2, players.get(1));
    }

    @Test
    public void testDealCardsAndDiscardToCrib() throws Exception {
        Method deal = Cribbage.class.getDeclaredMethod("dealCards");
        deal.setAccessible(true);
        deal.invoke(game);

        assertEquals(6, p1.getHand().getCards().size());
        assertEquals(6, p2.getHand().getCards().size());

        Field deckField = Cribbage.class.getDeclaredField("deck");
        deckField.setAccessible(true);
        Deck deck = (Deck) deckField.get(game);
        Card starter = deck.pop();

        Method discard = Cribbage.class.getDeclaredMethod("discardToCrib", Card.class);
        discard.setAccessible(true);
        discard.invoke(game, starter);

        assertEquals(4, p1.getHand().getCards().size());
        assertEquals(4, p2.getHand().getCards().size());

        Field cribField = Cribbage.class.getDeclaredField("crib");
        cribField.setAccessible(true);
        Crib crib = (Crib) cribField.get(game);
        assertEquals(4, crib.getCards().size());
    }

    @Test
    public void testRotateDealer() throws Exception {
        Field idxField = Cribbage.class.getDeclaredField("currentPlayerIndex");
        idxField.setAccessible(true);
        assertEquals(0, idxField.getInt(game));

        Method rotate = Cribbage.class.getDeclaredMethod("rotateDealer");
        rotate.setAccessible(true);
        rotate.invoke(game);

        assertEquals(1, idxField.getInt(game));
    }

    @Test
    public void testGetWinner() throws Exception {
        Field boardField = Cribbage.class.getDeclaredField("board");
        boardField.setAccessible(true);
        Board board = (Board) boardField.get(game);

        Field scoreMapField = Board.class.getDeclaredField("scoreMap");
        scoreMapField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Player,Integer> scores = (Map<Player,Integer>) scoreMapField.get(board);

        scores.put(p1,  10);
        scores.put(p2, 120);

        assertSame(p2, game.getWinner());
    }
}