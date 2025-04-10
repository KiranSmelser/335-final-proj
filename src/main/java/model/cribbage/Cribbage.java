package cribbage;

import deck.*;
import player.*;

import java.util.*;

public class Cribbage {
    private final List<Player> players;
    private final Deck deck;
    private final Crib crib;
    private final Board board;
    private int currentPlayerIndex = 0;
    private int winningScore = 121;

    public Cribbage(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.crib = new Crib();
        this.board = new Board(players);
    }

    public void startGame() {
        while (!gameOver()) {
            playRound();
        }
        declareWinner();
    }

    private void playRound() {
        deck.reset();
        deck.shuffle();
        board.resetHands();

        dealCards();
        discardToCrib();

        Card starter = deck.pop();
        board.setStarter(starter);

        board.playPhase(currentPlayerIndex);
        board.scoreHands(starter, crib);
        board.scoreCrib(starter, crib, getDealer());

        rotateDealer();
    }

    private void dealCards() {
        for (int i = 0; i < 6; i++) {
            for (Player p : players) {
                p.addCard(deck.pop());
            }
        }
    }

    private void discardToCrib() {
        for (Player p : players) {
            List<Card> discards = p.selectDiscards();
            crib.addCards(discards);
        }
    }

    private boolean gameOver() {
        for (Player p : players) {
            if (board.getScore(p) >= winningScore) {
                return true;
            }
        }
        return false;
    }

    private void declareWinner() {
        Player winner = players.get(0);
        int maxScore = board.getScore(winner);

        for (int i = 1; i < players.size(); i++) {
            Player p = players.get(i);
            int score = board.getScore(p);
            if (score > maxScore) {
                winner = p;
                maxScore = score;
            }
        }

        System.out.println("The winner is " + winner.getName());
        winner.incrementWins();

        for (Player p : players) {
            if (!p.equals(winner)) {
                p.incrementLosses();
            }
        }
    }

    private void rotateDealer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private Player getDealer() {
        return players.get(currentPlayerIndex);
    }
}