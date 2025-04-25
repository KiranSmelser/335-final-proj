package model.cribbage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;

public class Hand {
    /* VARIABLES */
    private ArrayList<Card> cards;

    /* Rank ordinal helper */
    private int rankOrdinal(Card card) {
        return card.getRank().ordinal() + 1;
    }

    /* CONSTRUCTOR */
    public Hand() {
        cards = new ArrayList<>();
    }

    /* METHODS */

    // Add card to hand
    public void addCard(Card card) {
        cards.add(Card.get(card.getRank(), card.getSuit()));
    }

    // Discard card from hand
    public void discard(Card card) {
        cards.remove(card);
    }

    // Clear hand
    public void reset() {
        cards.clear();
    }

    // Return textual view of the hand
    public ArrayList<String> getHand() {
        ArrayList<String> list = new ArrayList<>();
        for (Card c : cards) {
            list.add(c.toString());
        }
        return list;
    }

    // Return actual Card list
    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    // Calcualte total points accumulated from hand
    public int getScore(Card starter) {
        List<Card> scoringCards = new ArrayList<>(cards);
        if (starter != null) {
            scoringCards.add(starter);
        }

        int total = countFifteens(scoringCards)
                  + getFlushSum(starter)
                  + countRuns(scoringCards)
                  + countPairs(scoringCards)
                  + getNobsSum(starter);
        return total;
    }

    // Count nobs
    public int getNobsSum(Card starter) {
        if (starter == null) return 0;
        for (Card card : cards) {
            if (card.getRank() == Rank.JACK && card.getSuit() == starter.getSuit()) {
                return 1;
            }
        }
        return 0;
    }

    // Count flush
    public int getFlushSum(Card starter) {
        if (cards.size() < 4) return 0;
        Suit suit0 = cards.get(0).getSuit();
        boolean allSame = cards.stream().allMatch(c -> c.getSuit() == suit0);
        if (!allSame) return 0;
        return (starter != null && starter.getSuit() == suit0) ? 5 : 4;
    }

    // Count fifteens
    private int countFifteens(List<Card> list) {
        int total = 0;
        int n = list.size();
        for (int mask = 1; mask < (1 << n); mask++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += list.get(i).getValue();
                }
            }
            if (sum == 15) {
                total += 2;
            }
        }
        return total;
    }

    // Count pairs
    private int countPairs(List<Card> list) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
            	if (list.get(i).getRank() == list.get(j).getRank()) {
                    total += 2;
                }
            }
        }
        return total;
    }

    // Count runs
    private int countRuns(List<Card> list) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (Card c : list) {
            int r = rankOrdinal(c);
            freq.put(r, freq.getOrDefault(r, 0) + 1);
        }

        int bestLen = 0, bestMult = 0;
        for (int start = 1; start <= 13; start++) {
            int len = 0, mult = 1, r = start;
            while (freq.containsKey(r)) {
                len++;
                mult *= freq.get(r);
                r++;
            }
            if (len >= 3 && (len > bestLen || (len == bestLen && mult > bestMult))) {
                bestLen = len;
                bestMult = mult;
            }
        }
        return bestLen >= 3 ? bestLen * bestMult : 0;
    }

    // To string method
    @Override
    public String toString() {
        return getHand().toString();
    }
}