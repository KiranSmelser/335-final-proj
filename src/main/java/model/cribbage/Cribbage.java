package model.cribbage;

import model.deck.Card;
import model.deck.Deck;
import model.player.ComputerPlayer;
import model.player.Player;
import view.View;
import java.util.List;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Cribbage {
    private static final int TWO_PLAYER_HAND_CARDS = 6;
    private static final int MULTI_PLAYER_HAND_CARDS = 5;

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
        
        for (Player p : players) {
            if (p instanceof ComputerPlayer) {
                ((ComputerPlayer) p).setBoard(this.board);
            }
        }
    }

    public void startGame() {
    	deck.reset();
        deck.shuffle();
        LinkedHashMap<Player, Card> cutMap = new LinkedHashMap<>();
        for (Player p : players) {
            Card cutCard = deck.pop();
            cutMap.put(p, cutCard);
            view.displayCutCard(p.getName(), cutCard);
        }
        Player first = null;
        Card lowest = null;
        for (Entry<Player, Card> entry : cutMap.entrySet()) {
            Card c = entry.getValue();
            if (first == null
                || c.getValue() < lowest.getValue()
                || (c.getValue() == lowest.getValue()
                    && c.getRank().ordinal() < lowest.getRank().ordinal())) {
                first = entry.getKey();
                lowest = c;
            }
        }
        view.displayFirstPlayer(first.getName());
        currentPlayerIndex = players.indexOf(first);
    	
        board.resetScores();
        while (!gameOver()) {
            playRound();
        }
        declareWinner();
        view.displayWinner(getWinner().getName());
    }

    private void playRound() {
        deck.reset();
        deck.shuffle();
        board.resetHands();
        crib.clear();

        dealCards();

        Card starter = deck.pop();

        discardToCrib(starter);
        view.getStarterCard(starter);

        Map<Player, List<Card>> handStash = new HashMap<>();
        for (Player p : players) {
            handStash.put(p, new ArrayList<>(p.getHand().getCards()));
        }

        // Pegging phase
        board.playPhase(currentPlayerIndex);
        view.displayScores(board.getAllScores());
        for (Player p : players) {
            p.resetHand();
            for (Card c : handStash.get(p)) {
                p.addCard(c);
            }
        }
        if (gameOver()) return;

        view.displayAllHands(players);

        // Score hands
        Map<Player,Integer> beforeHands = new HashMap<>(board.getAllScores());
        board.scoreHands(starter);
        Map<Player,Integer> afterHands = board.getAllScores();
        view.displayHandScores(beforeHands, afterHands);
        if (gameOver()) return;

        view.displayCribContents(crib.getCards());

        // Score crib
        Map<Player,Integer> beforeCrib = new HashMap<>(board.getAllScores());
        board.scoreCrib(starter, getDealer());
        Map<Player,Integer> afterCrib = board.getAllScores();
        view.displayCribScores(beforeCrib, afterCrib, getDealer());
        view.displayScores(board.getAllScores());

        rotateDealer();
    }

    private void dealCards() {
        int cardsPer = (players.size() == 2) ? TWO_PLAYER_HAND_CARDS : MULTI_PLAYER_HAND_CARDS;
        for (int i = 0; i < cardsPer; i++) {
            for (Player p : players) {
                p.addCard(deck.pop());
            }
        }
    }

    private void discardToCrib(Card starter) {
    	for (Player p : players) {
            List<Card> discards = p.selectDiscards(starter);
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
    }

    private void rotateDealer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private Player getDealer() {
        return players.get(currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
