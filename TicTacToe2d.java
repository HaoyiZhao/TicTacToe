import java.util.Scanner;

public class TicTacToe {
	static char[][] board = new char[3][3];
	static int row;
	static int column;

	public static void main(String[] args) {
		boardSetup();
		for (int i = 1; i < 10; i++) {
			write(i);
			displayBoard();
			if (winner()) {
				break;
			}
		}
	}

	public static void boardSetup() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = ' ';
			}
		}
	}

	public static void write(int turn) {

		int player;
		if (turn % 2 == 0) {
			player = 2;
		} else {
			player = 1;
		}
		getCoordinate(player);
		while (board[row - 1][column - 1] != ' ') {
			System.out.println("Location is already occupied, please enter a new location");
			getCoordinate(player);
		}
		if (player == 1) {
			board[row - 1][column - 1] = 'X';
		} else {
			board[row - 1][column - 1] = 'O';
		}
	}

	public static void displayBoard() {

		for (int i = 0; i < 3; i++) {
			System.out.print("|");
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j]);
				System.out.print("|");
			}
			System.out.println();
		}

	}

	private static void getCoordinate(int player) {
		char playerName;
		if (player == 1) {
			playerName = 'X';
		} else {
			playerName = 'O';
		}
		Scanner input = new Scanner(System.in);
		System.out.println(playerName + ", " + "Please input desired row");
		row = input.nextInt();
		while (row > 3 || row < 1) {
			System.out.println("Invalid row input, please input a number between 1 and 3");
			row = input.nextInt();
		}
		System.out.println(playerName + ", " + "Please input desired column");
		column = input.nextInt();
		while (column > 3 || column < 1) {
			System.out.println("Invalid column input, please input a number between 1 and 3");
			column = input.nextInt();
		}
	}

	public static boolean winner() {
		int winCounter = 0;
		for (int i = 0; i < 3; i++) {
			if (board[row - 1][column - 1] == board[row - 1][i]) {
				winCounter++;
			} else {
				winCounter = 0;
				break;
			}
		}
		for (int i = 0; i < 3; i++) {
			if (board[row - 1][column - 1] == board[i][column - 1]) {
				winCounter++;
			} else {
				winCounter = 0;
				break;
			}

		}
		for (int i = 0; i < 3; i++) {
			if (row == column) {
				if (board[row - 1][column - 1] == board[i][i]) {
					winCounter++;
				} else {
					winCounter = 0;
					break;
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			if (row == 4 - column) {
				if (board[row - 1][column - 1] == board[i][2 - i]) {
					winCounter++;
				} else {
					winCounter = 0;
					break;
				}

			}
		}
		if (winCounter == 3 && board[row - 1][column - 1] == 'X') {
			System.out.println("Player X has won the game.");
			return true;
		} else if (winCounter == 3 && board[row - 1][column - 1] == 'O') {
			System.out.println("Player O has won the game.");
			return true;
		}
		boolean full = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == ' ') {
					full = false;
					break;
				}
			}
		}
		if (full == true) {
			System.out.println("There is no winner.");
			return true;
		}
		return false;
	}
}
