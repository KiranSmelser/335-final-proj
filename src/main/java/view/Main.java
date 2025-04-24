import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		View view = new View();
		Scanner scanner = new Scanner(System.in);
		boolean active = true;
		
		System.out.println("Welcome to Cribbage!");
		view.playMode();

		while (active) {
			System.out.print("Make your move! (press q to quit): ");
			String choice = scanner.nextLine().trim();
			switch (choice) {
			case "q":
				active = false;
				System.out.println("Game Over");
				break;
			default:
				System.out.println("Invalid option");
				break;
			}

		}
		scanner.close();
		System.exit(0);

	}

}
