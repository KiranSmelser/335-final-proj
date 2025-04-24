package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    	private Cribbage cribbage;
	private Board board;
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
		
	}
	
	// Print player's hand
	public void printHand(Player player) {
		System.out.println(player.getName() + ", your hand:");
	    List<Card> hand = player.getHand().getCards();
	    for (int i = 0; i < hand.size(); i++) {
	        System.out.println((i + 1) + ". " + hand.get(i));
	    }
	}
	
	// Selecting card for player
	private Card playerPlay(Player player, int currentCount) {
		printHand(player);
		List<Card> hand = player.getHand().getCards();
	    int cardIndex = -1;
	    while (cardIndex < 1 || cardIndex > hand.size()) {
	        String input = prompt("Enter the number of the card you want to play (1-" + hand.size() + "): ");
	        try {
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
			Player user = new HumanPlayer(choice);
			
			choice =  prompt("Type in the computer's name: ");
			Player computer = new ComputerPlayer(choice, this.strat, this.board);
			
			players.add(user);
			players.add(computer);
			
			System.out.println(players.get(0).getName() + " vs " + players.get(1).getName());
			break;
		case "2": 
			choice =  prompt("Type in player one's name: ");
			Player p1 = new HumanPlayer(choice);
			
			choice =  prompt("Type in player two's name: ");
			Player p2 = new HumanPlayer(choice);
			
			players.add(p1);
			players.add(p2);
			
			System.out.println(players.get(0).getName() + " vs " + players.get(1).getName());
			break;
		default:
			System.out.println("Invalid choice. Default set against computer on easy mode");
			strat = new EasyStrategy();
			choice =  prompt("Type in your name: ");
			Player playerUser = new HumanPlayer(choice);
			
			choice =  prompt("Type in the computer's name: ");
			Player compUser = new ComputerPlayer(choice, this.strat, this.board);
			
			players.add(playerUser);
			players.add(compUser);
			
			System.out.println(players.get(0).getName() + " vs " + players.get(1).getName());
			break;
		}
		this.cribbage = new Cribbage(players);
		this.board = new Board(players, crib);
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
		default:
			strat = new EasyStrategy();
			System.out.println("Invalid choice. Default set to easy mode");
			break;
		}
	}
	
	// WIP
	public void getMenu() {
		System.out.println("Menu");
		System.out.println("Quit Game: q");
	}
}
