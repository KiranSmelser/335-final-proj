package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import model.cribbage.Board;
import model.cribbage.Crib;
import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;
import model.player.Player;
import view.View;

public class BoardTest {

    private static class DummyPlayer extends Player {
        public DummyPlayer(String name) { super(name); }
        @Override public List<Card> selectDiscards(Card starter) { return List.of(); }
    }

    private static class NoOpView extends View {
        @Override public void displayPlayedCards(List<Card> cards, String name) {}
        @Override public void displayPegScore(String name, int pts, int total) {}
        @Override public void displayScores(Map<Player,Integer> scores) {}
        @Override public void displayHandScores(Map<Player,Integer> b, Map<Player,Integer> a) {}
        @Override public void displayCribContents(List<Card> cards) {}
        @Override public void displayCribScores(Map<Player,Integer> b, Map<Player,Integer> a, Player d) {}
        @Override public void displayAllHands(List<Player> ps) {}
        @Override public void displayCutCard(String name, Card c) {}
        @Override public void displayFirstPlayer(String name) {}
        @Override public void getStarterCard(Card c) {}
    }

    @Test
    void testInitialScoresAreZero() {
        DummyPlayer a = new DummyPlayer("A");
        DummyPlayer b = new DummyPlayer("B");
        Board board = new Board(Arrays.asList(a, b), new Crib(), new NoOpView());
        
        assertEquals(0, board.getScore(a));
        assertEquals(0, board.getScore(b));
    }

    @Test
    void testScoreHandsAddsFifteens() {
        DummyPlayer p = new DummyPlayer("P");
        Board board = new Board(List.of(p), new Crib(), new NoOpView());
        p.addCard(Card.get(Rank.SEVEN, Suit.HEARTS));
        p.addCard(Card.get(Rank.EIGHT, Suit.CLUBS));
        
        Card starter = Card.get(Rank.ACE, Suit.SPADES);
        board.scoreHands(starter);
        
        assertEquals(2, board.getScore(p));
    }

    @Test
    void testScoreCribCountsFifteens() {
        DummyPlayer dealer = new DummyPlayer("D");
        Crib crib = new Crib();
        crib.addCards(List.of(
            Card.get(Rank.FIVE, Suit.SPADES),
            Card.get(Rank.TEN, Suit.HEARTS)
        ));
        
        Board board = new Board(List.of(dealer), crib, new NoOpView());
        Card starter = Card.get(Rank.ACE, Suit.CLUBS);
        board.scoreCrib(starter, dealer);
        
        assertEquals(2, board.getScore(dealer));
    }

    @Test
    void testResetScores() {
        DummyPlayer p = new DummyPlayer("X");
        Board board = new Board(List.of(p), new Crib(), new NoOpView());
        p.addCard(Card.get(Rank.SEVEN, Suit.DIAMONDS));
        p.addCard(Card.get(Rank.EIGHT, Suit.DIAMONDS));
        
        board.scoreHands(Card.get(Rank.ACE, Suit.CLUBS));
        assertTrue(board.getScore(p) > 0);
        
        board.resetScores();
        assertEquals(0, board.getScore(p));
    }

    @Test
    void testResetHands() {
        DummyPlayer p = new DummyPlayer("Y");
        p.addCard(Card.get(Rank.ACE, Suit.SPADES));
        
        Board board = new Board(List.of(p), new Crib(), new NoOpView());
        board.resetHands();
        
        assertTrue(p.getHand().getCards().isEmpty());
    }

    @Test
    void testGetAllScores() {
        DummyPlayer p = new DummyPlayer("Z");
        Board board = new Board(List.of(p), new Crib(), new NoOpView());
        Map<Player,Integer> scores = board.getAllScores();
        
        assertThrows(UnsupportedOperationException.class, () -> scores.put(p, 100));
    }
}