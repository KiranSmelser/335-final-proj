package controller;

import java.util.ArrayList;
import java.util.List;

import model.cribbage.Board;
import model.cribbage.Crib;
import model.cribbage.Cribbage;
import ComputerPlayer;
import model.player.EasyStrategy;
import model.player.HardStrategy;
import model.player.HumanPlayer;
import model.player.Player;
import model.player.Strategy;
import view.View;

public class Controller {
	private final View view;
	private Board board;
	private Crib crib;
	private Strategy strat;
	private List<Player> players;
	
	public Controller(View view) {
		this.view = view;
		this.players = new ArrayList<Player>();
		this.crib = new Crib();
	}
	
	/**
     * Begins the game by setting up players and starting the Cribbage game loop.
     */
	public void startGame() {
	    // Prompt and set up players before starting the game
		setupPlayers();
	    Cribbage game = new Cribbage(players, view);
	    game.startGame();
	}
	
	/**
     * Handles the playMode flow to configure and return the list of players.
     *
     * @return the configured list of players for the game
     */
    private void setupPlayers() {
        playMode();
    }
    
    /**
     * Prompts the user to select single or two-player mode and configures players accordingly.
     */
	// Choosing how to play
	public void playMode() {
	    String choice;
	    // Prompt for play mode until valid input
	    do {
	        System.out.println("1. Single Player");
	        System.out.println("2. (Local) Two Players");
	        choice = view.prompt("Press a number to choose how you want to play (1 or 2), or 'q' to quit: ");
	        if (choice.equals("q")) {
	            this.endGame();
	        }
	        if (!choice.equals("1") && !choice.equals("2")) {
	            System.out.println("Invalid choice. Please enter 1 or 2.");
	        }
	    } while (!choice.equals("1") && !choice.equals("2"));

	    // Configure players based on choice
	    if (choice.equals("1")) {
	        // Single player mode
	        chooseStrategy();
	        String name;
	        // Validate user name
	        do {
	            name = view.prompt("Enter your name: ");
	            if (name.isEmpty()) {
	                System.out.println("Name cannot be empty. Please enter a valid name.");
	            }
	        } while (name.isEmpty());
	        Player user = new HumanPlayer(name, view);

	        // Validate computer name
	        do {
	            name = view.prompt("Enter the computer's name: ");
	            if (name.isEmpty()) {
	                System.out.println("Name cannot be empty. Please enter a valid name.");
	            }
	        } while (name.isEmpty());
	        Player computer = new ComputerPlayer(name, this.strat, this.board);

	        players.add(user);
	        players.add(computer);
	    } else {
	        // Two-player mode
	        String name;
	        // Validate player one name
	        do {
	            name = view.prompt("Enter player one's name: ");
	            if (name.isEmpty()) {
	                System.out.println("Name cannot be empty. Please enter a valid name.");
	            }
	        } while (name.isEmpty());
	        Player p1 = new HumanPlayer(name, view);

	        // Validate player two name
	        do {
	            name = view.prompt("Enter player two's name: ");
	            if (name.isEmpty()) {
	                System.out.println("Name cannot be empty. Please enter a valid name.");
	            }
	        } while (name.isEmpty());
	        Player p2 = new HumanPlayer(name, view);

	        players.add(p1);
	        players.add(p2);
	    }

	    System.out.println(players.get(0).getName() + " vs " + players.get(1).getName());
	    // Initialize board with validated players
	    this.board = new Board(players, crib, view);
	}
	
	/**
     * Prompts the user to select the computer strategy (easy or hard).
     */
	// Choosing mode
	private void chooseStrategy() {
	    String choice;
	    // Prompt for strategy until valid input
	    do {
	        System.out.println("1. Easy");
	        System.out.println("2. Hard");
	        choice = view.prompt("Press a number to choose a mode (1 or 2), or 'q' to quit: ");
	        if (choice.equals("q")) {
	            this.endGame();
	        }
	        if (!choice.equals("1") && !choice.equals("2")) {
	            System.out.println("Invalid choice. Please enter 1 or 2.");
	        }
	    } while (!choice.equals("1") && !choice.equals("2"));

	    // Assign strategy based on valid choice
	    if (choice.equals("1")) {
	        strat = new EasyStrategy();
	        System.out.println("You're on easy mode");
	    } else {
	        strat = new HardStrategy();
	        System.out.println("You're on hard mode");
	    }
	}
	
	/**
     * Ends the game immediately, closing resources and exiting.
     */
	public void endGame() {
		System.out.println("Game Over!");
		System.exit(0);
	}
}

