package cribbage;

import deck.Card;
import deck.Deck;
import player.Player;

import java.util.List;

public class Cribbage {
    private View view;
	private final List<Player> players;
    private final Deck deck;
    private final Crib crib;
    private final Board board;
    private int currentPlayerIndex = 0;
    private final int winningScore = 121;

    public Cribbage(List<Player> players, View view) {
    	this.view = view;
        this.players = players;
        this.deck    = new Deck();
        this.crib    = new Crib();
        this.board   = new Board(players, crib, view);
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
        crib.clear();

        dealCards();
        discardToCrib();

        Card starter = deck.pop();
        view.getStarterCard(starter);

        board.playPhase(currentPlayerIndex);
        view.displayScores(board.getAllScores());
        if (gameOver()) return;

        board.scoreHands(starter);
        if (gameOver()) return;

        board.scoreCrib(starter, getDealer());

        rotateDealer();
    }

    private void dealCards() {
        int cardsPer = (players.size() == 2) ? 6 : 5;
        for (int i = 0; i < cardsPer; i++) {
            for (Player p : players) {
                p.addCard(deck.pop());
            }
        }
    }

    private void discardToCrib() {
        Card starter = deck.pop();
        for (Player p : players) {
            List<Card> discards = (p instanceof player.ComputerPlayer)
                ? ((player.ComputerPlayer) p).selectDiscards(starter)
                : p.selectDiscards(starter);
            for (Card c : discards) {
                p.getHand().discard(c);
            }
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

    public Player getWinner() {
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
        return winner;
    }

    private void declareWinner() {
        Player winner = getWinner();
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
