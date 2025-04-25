package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.cribbage.Board;
import model.cribbage.Crib;
import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;
import model.player.ComputerPlayer;
import model.player.HardStrategy;
import model.player.Strategy;
import model.player.Player;
import view.View;

public class ComputerPlayerTest {

    private ComputerPlayer player;
    private Strategy strategy;
    private Board board;

    @Before
    public void setUp() {
        strategy = new HardStrategy();
        List<Player> players = new ArrayList<>();
        
        Crib crib = new Crib();
        View view = new View();
        board = new Board(players, crib, view);
        
        player = new ComputerPlayer("CPU", strategy, board);
        players.add(player);
    }

    @Test
    public void testSelectDiscards() {
        player.addCard(Card.get(Rank.FIVE, Suit.HEARTS));
        player.addCard(Card.get(Rank.FIVE, Suit.CLUBS));
        player.addCard(Card.get(Rank.TEN, Suit.DIAMONDS));
        player.addCard(Card.get(Rank.KING, Suit.SPADES));
        player.addCard(Card.get(Rank.DEUCE, Suit.HEARTS));
        player.addCard(Card.get(Rank.THREE, Suit.SPADES));

        Card starter = Card.get(Rank.FOUR, Suit.DIAMONDS);

        List<Card> discards = player.selectDiscards(starter);

        assertNotNull(discards);
        assertEquals(2, discards.size());
    }

    @Test
    public void testPlayCard() {
        player.addCard(Card.get(Rank.FIVE, Suit.HEARTS));
        player.addCard(Card.get(Rank.KING, Suit.DIAMONDS));

        List<Card> playedCards = new ArrayList<>();
        int currentCount = 20;

        Card chosen = player.playCard(playedCards, currentCount);

        if (chosen != null) {
            assertTrue(chosen.getValue() + currentCount <= 31);
        }
    }

    @Test
    public void testSetBoard() {
        Crib newCrib = new Crib();
        View newView = new View();
        List<Player> newPlayers = new ArrayList<>();
        Board newBoard = new Board(newPlayers, newCrib, newView);

        player.setBoard(newBoard);

        player.addCard(Card.get(Rank.ACE, Suit.HEARTS));
        player.addCard(Card.get(Rank.DEUCE, Suit.SPADES));
        player.addCard(Card.get(Rank.THREE, Suit.DIAMONDS));
        player.addCard(Card.get(Rank.FOUR, Suit.CLUBS));
        player.addCard(Card.get(Rank.FIVE, Suit.HEARTS));
        player.addCard(Card.get(Rank.SIX, Suit.SPADES));

        Card starter = Card.get(Rank.SEVEN, Suit.DIAMONDS);

        List<Card> discards = player.selectDiscards(starter);
        
        assertNotNull(discards);
        assertEquals(2, discards.size());
    }
}
