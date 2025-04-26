package main.java.view;

/**
 * Handles all console-based user interaction for the Cribbage game.
 * 
 * Implementation authored by ChatGPT AI assistant.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.cribbage.*;
import model.player.*;
import model.deck.*;

public class View {
	
	private Scanner scanner;
	
	// prompt scanner
	public String prompt(String message) {
	    System.out.print(message);
	    return scanner.nextLine().trim();
	}

    /**
     * Constructs a new View for console interaction.
     * Initializes player list and input scanner.
     */
	public View() {
		this.scanner = new Scanner(System.in);
	}
	
    /**
     * Prompts a human player to select two cards to discard into the crib.
     *
     * @param player the human player making the discard
     * @param starter the starter card to which discards are compared
     * @return a list of two cards selected for the crib
     */
	public List<Card> promptDiscards(HumanPlayer player, Card starter) {
		List<Card> hand = player.getHand().getCards();
        List<Card> chosenDiscards = new ArrayList<>();

        System.out.println(player.getName() + ", your hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i));
        }

        while (chosenDiscards.size() < 2) {
            String input = prompt("Select card #" + (chosenDiscards.size() + 1) + " to discard (1-" + hand.size() + "): ");
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < hand.size()) {
                    Card chosen = hand.get(index);
                    if (!chosenDiscards.contains(chosen)) {
                        chosenDiscards.add(chosen);
                    } else {
                        System.out.println("You've already chosen that card. Pick another.");
                    }
                } else {
                    System.out.println("Invalid index. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }

        return chosenDiscards;
    }
	
    /**
     * Prompts a player to choose a card to play during pegging.
     *
     * @param player the player whose turn it is
     * @param playedCards the list of cards already played in this sequence
     * @param currentCount the current count total in pegging
     * @return the card chosen to play, or null if passing
     */
	public Card promptPlayCard(Player player, List<Card> playedCards, int currentCount) {
		List<Card> hand = player.getHand().getCards();

		System.out.println("\n" + player.getName() + "'s turn - Current count: " + currentCount);
		System.out.println("Your hand:");
		for (int i = 0; i < hand.size(); i++) {
			Card c = hand.get(i);
			String playableTag = (c.getValue() + currentCount <= 31) ? "" : " (not playable)";
			System.out.println((i + 1) + ": " + c + playableTag);
		}

		List<Card> playable = hand.stream()
			.filter(c -> c.getValue() + currentCount <= 31)
			.collect(Collectors.toList());

		if (playable.isEmpty()) {
			System.out.println("No playable cards. You pass.");
			return null;
		}

		while (true) {
            String input = prompt("Choose a card to play (1-" + hand.size() + "), or 0 to pass: ");
            try {
                int choice = Integer.parseInt(input);
                if (choice == 0) return null;

                if (choice >= 1 && choice <= hand.size()) {
                    Card selected = hand.get(choice - 1);
                    if (selected.getValue() + currentCount <= 31) {
                        return selected;
                    } else {
                        System.out.println("That play would exceed 31. Try again.");
                    }
                } else {
                    System.out.println("Invalid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    /**
     * Displays the specified player's hand in the console.
     *
     * @param player the player whose hand to print
     */
	// Print player's hand
	public void printHand(Player player) {
		System.out.println(player.getName() + ", your hand:");
	    List<Card> hand = player.getHand().getCards();
	    for (int i = 0; i < hand.size(); i++) {
	        System.out.println((i + 1) + ". " + hand.get(i));
	    }
	}
	
    /**
     * Displays the current scores of all players in a formatted table.
     *
     * @param scores a map from players to their current scores
     */
	public void displayScores(Map<Player, Integer> scores) {
	    System.out.println("\n==================== Current Scores ====================");
	    for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
	        System.out.printf("%-12s : %3d pts\n", entry.getKey().getName(), entry.getValue());
	    }
	    System.out.println("========================================================\n");
	}

    /**
     * Announces the winner of the game.
     *
     * @param winnerName the name of the winning player
     */
    public void displayWinner(String winnerName) {
        System.out.println("\nGame Over!");
        System.out.println("Winner: " + winnerName);
    }
	
    /**
     * Displays the cards played so far in the current pegging sequence.
     *
     * @param playedCards the sequence of cards played
     * @param playerName the name of the player who just played
     */
	public void displayPlayedCards(List<Card> playedCards, String playerName) {
	    String plays = playedCards.stream()
	        .map(Card::toString)
	        .collect(Collectors.joining(", "));
	    System.out.println("\n" + playerName + " played: " + plays + "\n");
	}

    /**
     * Shows the starter card to all players after discards are made.
     *
     * @param starter the starter card cut from the deck
     */
	public void getStarterCard(Card starter) {
	    System.out.println("\n****** Starter Card: " + starter + " ******\n");
	}
	
    /**
     * Displays the card each player cuts to decide first dealer.
     *
     * @param playerName the name of the player cutting
     * @param card the card that was cut
     */
	public void displayCutCard(String playerName, Card card) {
	    System.out.println(">> " + playerName + " cut: " + card + " <<");
	}

    /**
     * Announces which player will take the first turn.
     *
     * @param playerName the name of the starting player
     */
	public void displayFirstPlayer(String playerName) {
	    System.out.println("\n>>> " + playerName + " will go first! <<<\n");
	}

    /**
     * Displays pegging points as they occur during play.
     *
     * @param playerName the name of the player who scored
     * @param points the points scored in this play
     * @param totalScore the player's new total score
     */
    public void displayPegScore(String playerName, int points, int totalScore) {
    	System.out.printf("\n%s scores %d point%s! [Total: %d]\n\n",
                playerName, points, (points > 1 ? "s" : ""), totalScore);
    }

    /**
     * Displays individual hand scoring deltas for each player.
     *
     * @param before map of scores before hand scoring
     * @param after map of scores after hand scoring
     */
    public void displayHandScores(Map<Player,Integer> before, Map<Player,Integer> after) {
    	System.out.println("\n--- Hand Scoring ---");
        for (Player p : after.keySet()) {
            int delta = after.get(p) - before.getOrDefault(p, 0);
            System.out.printf("%-12s : +%d point%s\n",
                    p.getName(), delta, (delta > 1 ? "s" : ""));
        }
        System.out.println("--------------------\n");
    }

    /**
     * Displays the scoring delta for the dealer's crib.
     *
     * @param before map of scores before crib scoring
     * @param after map of scores after crib scoring
     * @param dealer the dealer who scores the crib
     */
    public void displayCribScores(Map<Player,Integer> before, Map<Player,Integer> after, Player dealer) {
    	System.out.println("\n--- Crib Scoring ---");
        int delta = after.get(dealer) - before.getOrDefault(dealer, 0);
        System.out.printf("%-12s : +%d point%s\n",
                dealer.getName(), delta, (delta > 1 ? "s" : ""));
        System.out.println("---------------------\n");
    }

    /**
     * Displays all players' hands prior to scoring.
     *
     * @param players the list of players whose hands to show
     */
    public void displayAllHands(List<Player> players) {
    	System.out.println("\n=== Players' Hands ===");
        for (Player p : players) {
            String hand = p.getHand().getCards().stream()
                    .map(Card::toString)
                    .collect(Collectors.joining("  "));
            System.out.printf("%-12s: %s\n", p.getName(), hand);
        }
        System.out.println("======================\n");
    }

    /**
     * Displays the contents of the crib before scoring.
     *
     * @param cards the list of cards in the crib
     */
    public void displayCribContents(List<Card> cards) {
    	System.out.println("\n=== Crib Contents ===");
        String cribCards = cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining("  "));
        System.out.println(cribCards);
        System.out.println("=====================\n");
    }
// View.java implementation completed by ChatGPT AI assistant.
}
