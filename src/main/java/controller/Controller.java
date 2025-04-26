package controller;

import java.util.ArrayList;
import java.util.List;

import model.cribbage.Board;
import model.cribbage.Crib;
import model.cribbage.Cribbage;
import model.player.ComputerPlayer;
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
	
	public void startGame() {
		setupPlayers();
	    Cribbage game = new Cribbage(players, view);
	    game.startGame();
	}
	
    private void setupPlayers() {
        playMode();
    }
    
	public void playMode() {
	    String choice;
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

	    if (choice.equals("1")) {
	        chooseStrategy();
	        String name;
	        do {
	            name = view.prompt("Enter your name: ");
	            if (name.isEmpty()) {
	                System.out.println("Name cannot be empty. Please enter a valid name.");
	            }
	        } while (name.isEmpty());
	        Player user = new HumanPlayer(name, view);

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
	        String name;
	        do {
	            name = view.prompt("Enter player one's name: ");
	            if (name.isEmpty()) {
	                System.out.println("Name cannot be empty. Please enter a valid name.");
	            }
	        } while (name.isEmpty());
	        Player p1 = new HumanPlayer(name, view);

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
	    this.board = new Board(players, crib, view);
	}
	
	private void chooseStrategy() {
	    String choice;
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

	    if (choice.equals("1")) {
	        strat = new EasyStrategy();
	        System.out.println("You're on easy mode");
	    } else {
	        strat = new HardStrategy();
	        System.out.println("You're on hard mode");
	    }
	}
	
	public void endGame() {
		System.out.println("Game Over!");
		System.exit(0);
	}
}

