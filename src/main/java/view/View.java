package view;

/**
 * Handles all console-based user interaction for the Cribbage game.
 * <p>
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
	
	private Board board;
	private Crib crib;
	private Strategy strat;
	private List<Player> players;
	private Scanner scanner;
	
	// prompt scanner
	private String prompt(String message) {
	    System.out.print(message);
	    return scanner.nextLine().trim();
	}

    /**
     * Constructs a new View for console interaction.
     * Initializes player list and input scanner.
     */
	public View() {
		this.strat = null;
		this.players = new ArrayList<Player>();
		this.scanner = new Scanner(System.in);
		this.crib = new Crib();
		
	}
	
    /**
     * Begins the game by setting up players and starting the Cribbage game loop.
     */
	public void startGame() {
	    // Prompt and set up players before starting the game
	    List<Player> players = setupPlayers();
	    Cribbage game = new Cribbage(players, this);
	    game.startGame();
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
	        	if (input.equals("q")) {
	        		this.endGame();
	        	}
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
			.toList();

		if (playable.isEmpty()) {
			System.out.println("No playable cards. You pass.");
			return null;
		}

		while (true) {
			String input = prompt("Choose a card to play (1-" + hand.size() + "), or 0 to pass: ");
			try {
				if (input.equals("q")) {
					this.endGame();
				}
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
     * Handles the playMode flow to configure and return the list of players.
     *
     * @return the configured list of players for the game
     */
    private List<Player> setupPlayers() {
        playMode();
        return new ArrayList<>(players);
    }
	
    /**
     * Prompts the user to select single or two-player mode and configures players accordingly.
     */
	// Choosing how to play
	public void playMode() {
		System.out.println("1. Single Player");
		System.out.println("2. (Local) Two Players");
		String choice = prompt("Press a number to choose how you want to play: ");
		switch (choice) {
		case "1":
			this.chooseStrategy();
			choice =  prompt("Type in your name: ");
			Player user = new HumanPlayer(choice, this);
			
			choice =  prompt("Type in the computer's name: ");
			Player computer = new ComputerPlayer(choice, this.strat, this.board);
			
			players.add(user);
			players.add(computer);
			
			break;
		case "2": 
			choice =  prompt("Type in player one's name: ");
			Player p1 = new HumanPlayer(choice, this);
			
			choice =  prompt("Type in player two's name: ");
			Player p2 = new HumanPlayer(choice, this);
			
			players.add(p1);
			players.add(p2);
			break;
		case "q":
			this.endGame();
		default:
			System.out.println("Invalid choice. Default set against computer on easy mode");
			strat = new EasyStrategy();
			choice =  prompt("Type in your name: ");
			Player playerUser = new HumanPlayer(choice, this);
			
			choice =  prompt("Type in the computer's name: ");
			Player compUser = new ComputerPlayer(choice, this.strat, this.board);
			
			players.add(playerUser);
			players.add(compUser);
			
			break;
		}
		System.out.println(players.get(0).getName() + " vs " + players.get(1).getName());
		this.board = new Board(players, crib, this);
	}
	
    /**
     * Prompts the user to select the computer strategy (easy or hard).
     */
	// Choosing mode
	private void chooseStrategy() {
		System.out.println("1. Easy");
		System.out.println("2. Hard");
		String choice = prompt("Press a number to choose a mode: ");
		switch (choice) {
		case "1":
			strat = new EasyStrategy();
			System.out.println("You're on easy mode");
			break;
		case "2": 
			strat = new HardStrategy();
			System.out.println("You're on hard mode");
			break;
		case "q":
			this.endGame();
		default:
			strat = new EasyStrategy();
			System.out.println("Invalid choice. Default set to easy mode");
			break;
		}
	}
	
    /**
     * Returns a copy of the current player list.
     *
    * @return a list of players configured for the game
     */
	public List<Player> getPlayers() {
		return new ArrayList<Player>(List.copyOf(players));
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
     * Ends the game immediately, closing resources and exiting.
     */
	public void endGame() {
		System.out.println("Game Over!");
		scanner.close();
		System.exit(0);
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
            playerName,
            points,
            (points > 1 ? "s" : ""),
            totalScore
        );
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
                p.getName(),
                delta,
                (delta > 1 ? "s" : "")
            );
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
            dealer.getName(),
            delta,
            (delta > 1 ? "s" : "")
        );
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