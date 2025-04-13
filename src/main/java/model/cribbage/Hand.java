package cribbage;

import java.util.ArrayList;
import java.util.Collections;

import model.deck.Card;
import model.deck.Rank;
import model.deck.Suit;

public class Hand {
	/* VARIABLES */
	private ArrayList<Card> cards;
	
	/* CONSTRUCTOR */
	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	/* METHODS */
	
	// Add card to hand
	public void addCard(Card card) {
		Card c = Card.get(card.getRank(), card.getSuit());
		cards.add(c);
	}
	
	// Discard card from hand
	public void discard(Card card) {
		cards.remove(card);
	}
	
	// Clear hand
	public void reset() {
		cards.clear();
	}

	// Get hand
	public ArrayList<String> getHand() {
		ArrayList<String> list = new ArrayList<String>();
		for (Card c : cards) {
			list.add(c.toString());
		}
		return list;
	}
	
	// Calcualte total points accumulated from hand
	public int getScore(Card starter) {
		int total = getNumSum() + getFlushSum() + getRunSum() + getPairsSum() + getNobsSum(starter);
		return total;
	}
	
	// Count nobs
	public int getNobsSum(Card starter) {
		Suit val = starter.getSuit();
		for (Card card : cards) {
			if (card.getRank() == Rank.JACK && card.getSuit() == val) {
				return 1;
			}
		}
		return 0;
	}
	
	// Count flush
	public int getFlushSum() {
		int total = 0;
		Collections.sort(cards, Card.rankFirstComparator());
		Suit val = cards.get(0).getSuit();
		for (Card card : cards) {
			if (card.getSuit() == val) {
				total += 1;
			}
		}
		if (total >= 4) {
			return total;
		}
		return 0;

	}
	
	// Count run
	public int getRunSum() {
		int total = 0;
		boolean isRun = false;
		int dups = 1;
		Collections.sort(cards, Card.rankFirstComparator());
		int val = cards.get(0).getValue();
		for (Card card : cards) {
			if (card.getValue() == val) {
				total += 1;
				val++;
			} else if (card.getValue() == val - 1) {
				dups++;
			} else {
				if (!isRun) {
					total = 1;
					val = card.getValue() + 1;
				}
			}
			if (total >= 3) {
				isRun = true;
			}

		}
		if (total >= 3) {
			return total * dups;
		}
		return 0;
	}
	
	// Count pairs
	public int getPairsSum() {
		int total = 0;
		ArrayList<ArrayList<Card>> pairs = getAllPairs();
		for (ArrayList<Card> pair : pairs) {
			if (pair.get(0).getValue() == pair.get(1).getValue()) {
				total += 2;
			}
		}
		return total;
	}
	
	// Count 15s
	public int getNumSum() {
		return getNumSumHelper(0, 0);
	}
	
	// Count 15s helper
	private int getNumSumHelper(int index, int currSum) {
		if (currSum == 15) {
			return 2;
		} else if (currSum > 15 || index >= cards.size()) {
			return 0;
		} else {
			return getNumSumHelper(index+1, currSum + cards.get(index).getValue()) +
					getNumSumHelper(index+1, currSum);
		}
	}
	
	// Get all possible pairs
	private ArrayList<ArrayList<Card>> getAllPairs() {
		ArrayList<ArrayList<Card>> pairs = new ArrayList<ArrayList<Card>>();
		for (int i = 0; i < cards.size(); i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				ArrayList<Card> pair = new ArrayList<Card>();
				pair.add(cards.get(i));
				pair.add(cards.get(j));
				pairs.add(pair);
			}
		}
		return pairs;
	}

	// To string method
	public String toString() {
		ArrayList<String> list = this.getHand();
		return list.toString();
	}
}
