import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		View view = new View();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome to Cribbage!");
		view.playMode();
		
		Cribbage game = new Cribbage(view.getPlayers(), view);
		game.startGame();
		
		Player winner = game.getWinner();
		System.out.println("Winner: " + winner.getName());

		scanner.close();
		System.exit(0);

	}

}
