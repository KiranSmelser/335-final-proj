package cribbage;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import deck.Card;
import player.Player;

public class Board {
	private static final int WINNING_SCORE = 121;
	
	private View view;
    private final List<Player> players;
    private final Map<Player, Integer> scoreMap = new HashMap<>();
    private final Crib crib;

    public Board(List<Player> players, Crib crib, View view) {
    	this.view = view;
        this.players = players;
        this.crib = crib;
        for (Player p : players) {
            scoreMap.put(p, 0);
        }
    }

    public void resetHands() {
        for (Player p : players) {
            p.resetHand();
        }
    }

    public void playPhase(int dealerIndex) {
        List<Hand> hands = new ArrayList<>();
        int totalCards = 0;
        for (Player p : players) {
            hands.add(p.getHand());
            totalCards += hands.get(hands.size() - 1).getCards().size();
        }

        List<Card> playedCards = new ArrayList<>();
        int currentCount = 0;
        boolean[] hasPassed = new boolean[players.size()];
        int currentPlayer = (dealerIndex + 1) % players.size();
        int lastPlayer = -1;

        while (totalCards > 0) {
            boolean anyPlayed = false;

            for (int i = 0; i < players.size(); i++) {
                int idx = (currentPlayer + i) % players.size();
                if (hasPassed[idx]) continue;

                Player p = players.get(idx);
                Hand hand = hands.get(idx);
                Card play = p.playCard(new ArrayList<>(playedCards), currentCount);

                if (play != null && hand.getCards().contains(play)) {
                    hand.discard(play);
                } else {
                    hasPassed[idx] = true;
                    continue;
                }

                lastPlayer = idx;
                currentCount += play.getValue();
                playedCards.add(play);
                totalCards--;
                anyPlayed = true;
                
                view.displayPlayedCards(playedCards, p.getName());
                
                scorePeg(idx, playedCards, currentCount);

                if (scoreMap.get(players.get(idx)) >= WINNING_SCORE) {
                    return;
                }

                if (currentCount == 31) {
                    currentCount = 0;
                    playedCards.clear();
                    Arrays.fill(hasPassed, false);
                    currentPlayer = idx;
                } else {
                    currentPlayer = (idx + 1) % players.size();
                }
                break;
            }

            if (!anyPlayed) {
                if (currentCount > 0 && lastPlayer != -1) {
                    scoreMap.put(
                        players.get(lastPlayer),
                        scoreMap.get(players.get(lastPlayer)) + 1
                    );
                    if (scoreMap.get(players.get(lastPlayer)) >= WINNING_SCORE) {
                        return;
                    }
                    currentPlayer = lastPlayer;
                }
                currentCount = 0;
                playedCards.clear();
                Arrays.fill(hasPassed, false);
            }
        }
    }

    private void scorePeg(int idx, List<Card> playedCards, int currentCount) {
        Player player = players.get(idx);
        int points = 0;

        // Fifteens & 31
        if (currentCount == 15 || currentCount == 31) {
            points += 2;
        }

        // Pairs/multiples
        int size = playedCards.size();
        if (size >= 2) {
            Card last = playedCards.get(size - 1);
            int pairCount = 0;
            for (int i = size - 2; i >= 0; i--) {
                if (playedCards.get(i).getRank() == last.getRank()) {
                    pairCount++;
                } else {
                    break;
                }
            }
            switch (pairCount) {
                case 1: points += 2; break;
                case 2: points += 6; break;
                case 3: points += 12; break;
            }
        }

        // Runs
        for (int runLen = size; runLen >= 3; runLen--) {
            List<Card> seg = playedCards.subList(size - runLen, size);
            Map<Integer,Integer> freq = new HashMap<>();
            for (Card c : seg) {
                int ord = c.getRank().ordinal() + 1;
                freq.put(ord, freq.getOrDefault(ord, 0) + 1);
            }
            int min = Collections.min(freq.keySet());
            boolean contig = true;
            for (int r = min; r < min + runLen; r++) {
                if (!freq.containsKey(r)) {
                    contig = false;
                    break;
                }
            }
            if (contig) {
                int mult = 1;
                for (int r = min; r < min + runLen; r++) {
                    mult *= freq.get(r);
                }
                points += runLen * mult;
                break;
            }
        }

        if (points > 0) {
            scoreMap.put(player, scoreMap.get(player) + points);
        }
    }

    public void scoreHands(Card starter) {
        for (Player p : players) {
            Hand hand = p.getHand();
            int points = hand.getScore(starter);
            scoreMap.put(p, scoreMap.get(p) + points);
        }
    }

    public void scoreCrib(Card starter, Player dealer) {
        int points = crib.score(starter);
        scoreMap.put(dealer, scoreMap.get(dealer) + points);
    }

    public int getScore(Player p) {
        return scoreMap.getOrDefault(p, 0);
    }
}
