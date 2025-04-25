package model.player;

import java.util.*;
import model.cribbage.Board;
import model.cribbage.Hand;
import model.deck.Card;

public class HardStrategy implements Strategy {
    
    @Override
    public List<Card> discard(Hand hand, Board board, Card starter) {
        List<Card> cards = hand.getCards();
        List<Card> bestDiscards = null;
        int maxScore = -1;
        
        for (int i = 0; i < cards.size()-1; i++) {
            for (int j = i+1; j < cards.size(); j++) {
                // create a copy of hand object
                Hand handCopy = new Hand();
                for (Card c : cards) {
                    handCopy.addCard(c);
                }
                Card discard1 = cards.get(i); Card discard2 = cards.get(j);
                handCopy.discard(discard1); handCopy.discard(discard2);
                int currScore = handCopy.getScore(starter);
                if (currScore > maxScore) {
                    maxScore = currScore;
                    bestDiscards = new ArrayList<>();
                    // store the two cards to discard that leave the best scoring hand
                    bestDiscards.add(discard1); bestDiscards.add(discard2);
                }
            }
        }
        return bestDiscards; 
    }

    /**
     * Selects the card that maximizes immediate pegging points by evaluating potential points for each legal play.
     * Implementation by ChatGPT AI assistant.
     */
    @Override
    public Card playCard(Hand hand, List<Card> playedCards, int currentCount) {
        Card best = null;
        int bestPoints = -1;
        for (Card c : hand.getCards()) {
            if (c.getValue() + currentCount > 31) continue;
            int pts = calculatePegPoints(playedCards, currentCount, c);
            if (pts > bestPoints) {
                bestPoints = pts;
                best = c;
            }
        }
        return best;
    }

    /**
     * Calculates pegging points for a potential play, including 15s, pairs, and runs as per Cribbage rules.
     * Implementation by ChatGPT AI assistant.
     */
    private int calculatePegPoints(List<Card> playedCards, int currentCount, Card play) {
        List<Card> seg = new ArrayList<>(playedCards);
        seg.add(play);
        int newCount = currentCount + play.getValue();
        int points = 0;
        if (newCount == 15 || newCount == 31) points += 2;
        // pairs
        int size = seg.size();
        Card last = seg.get(size - 1);
        int pairCount = 0;
        for (int i = size - 2; i >= 0; i--) {
            if (seg.get(i).getRank() == last.getRank()) pairCount++;
            else break;
        }
        switch (pairCount) {
            case 1: points += 2; break;
            case 2: points += 6; break;
            case 3: points += 12; break;
        }
        // runs
        for (int runLen = size; runLen >= 3; runLen--) {
            Map<Integer,Integer> freq = new HashMap<>();
            for (Card c2 : seg.subList(size - runLen, size)) {
                int ord = c2.getRank().ordinal() + 1;
                freq.put(ord, freq.getOrDefault(ord, 0) + 1);
            }
            int min = Collections.min(freq.keySet());
            boolean contig = true;
            for (int r = min; r < min + runLen; r++) {
                if (!freq.containsKey(r)) { contig = false; break; }
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
        return points;
    }
}
