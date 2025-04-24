package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.game.*;
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

	public View() {
		this.strat = null;
		this.players = new ArrayList<Player>();
		this.scanner = new Scanner(System.in);
		this.crib = new Crib();
		
	}
	
	public void startGame() {
		Cribbage cribbage = new Cribbage(players, this);
		cribbage.startGame();
		System.out.println("Game Over!");
		System.out.println("Winner: " + cribbage.getWinner().getName());
	}
	
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
				if (input == "q") {
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

	// Print player's hand
	public void printHand(Player player) {
		System.out.println(player.getName() + ", your hand:");
	    List<Card> hand = player.getHand().getCards();
	    for (int i = 0; i < hand.size(); i++) {
	        System.out.println((i + 1) + ". " + hand.get(i));
	    }
	}
	
	public void displayScores(Map<Player, Integer> scores) {
	    System.out.println("Current Scores:");
	    for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
	        System.out.println(entry.getKey().getName() + ": " + entry.getValue() + " points");
	    }
	    System.out.println("-----------------------------");
	}

	
	// Selecting card for player
	private Card playerPlay(Player player, int currentCount) {
		printHand(player);
		List<Card> hand = player.getHand().getCards();
	    int cardIndex = -1;
	    while (cardIndex < 1 || cardIndex > hand.size()) {
	        String input = prompt("Enter the number of the card you want to play (1-" + hand.size() + "): ");
	        try {
	        	if (input == "q") {
	        		this.endGame();
	        	}
	            cardIndex = Integer.parseInt(input); 
	            if (cardIndex < 1 || cardIndex > hand.size()) {
	                System.out.println("Invalid choice. Please select a valid card.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number.");
	        }
	    }
	    Card selectedCard = hand.get(cardIndex - 1);
	    System.out.println("Card you picked: " + selectedCard);
	    return selectedCard;
	}
	
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
	
	//
	public List<Player> getPlayers() {
		return new ArrayList<Player>(List.copyOf(players));
	}
	
	public void displayPlayedCards(List<Card> playedCards, String playerName) {
	    System.out.println(playerName + " played: ");
	    for (Card card : playedCards) {
	        System.out.println(card);
	    }
	}


	public void getStarterCard(Card starter) {
		System.out.println("\nStarter Card: " + starter);
		
	}
	
	public void endGame() {
		System.out.println("Game Over!");
		scanner.close();
		System.exit(0);
	}
}
}
