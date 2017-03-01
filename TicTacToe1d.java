import java.util.Scanner;
/**
 * COMP1006 Assignment 1 Problem 1 Name: Haoyi Zhao ID:101056211 Due:July 11, 2016
 * Program plays a game of tictactoe, with player always going first. Player first selects symbol,
 * and then makes a move, followed by the computer making a move. The board is displayed after each
 * move. Whether or not there is a winner is also checked after every move. If the game is over,
 * player will be prompted to input whether or not they will play again. Compile and run by using the 
 * main method provided in the program.
 * @author HowieZhao
 *
 */
public class TicTacToe {
	//character array which stores the value of each coordinate of the tictactoe board
	static char[] board = new char[9];
	//stores and determines where player symbol will be placed
	static int placement;
	//stores playerSymbol
	static char playerSymbol;
	//stores winner of game, if stalemate, winner will equal null
	static String winner;
	
	/**
	 * Runs the program. Will ask for input at the end of game, to see if player would like to play again
	 * @param args
	 */
	public static void main(String[] args) {
		//create scanner variable, which will subsequently be used by all called methods as input
		Scanner input = new Scanner(System.in);
		//variable will determine whether or not a new game will take place, intialize as "yes" so game can start
		String playAgain = "yes";
		chooseSymbol(input);
		//starts game if playAgain equals yes, input taken after game finishes (while loop ends)
		while (playAgain.equals("yes")) {
			boardSetup();
			displayBoard();
			while (winner() == false) {
				write(input);
				displayBoard();
				//Checks if game is over after player move
				if (winner()) {
					break;
				}
				computerMove();
				displayBoard();
				//if game is over after computer move, winner() will be true and loop will terminate
			}
			//Prints who won the game. If winner = null, winner has won phrase will not be printed, 
			//instead only STALEMATE will be printed (See winner() method)
			if (winner != null) {
				System.out.println(winner + " has won.");
			}
			//accounts for case insensitive "yes" being input, correct input is assumed, so no case for "no"
			//is needed. Resets winner to null
			System.out.println("Would you like to play again? Enter yes for another game, or no to exit.");
			playAgain = input.next().toLowerCase();
			winner = null;
		}
		//End program, if yes is not input. Scanner closed.
		System.out.print("Game finished, now exiting...");
		input.close();

	}
	
	/**
	 * Method takes as input a Scanner variable input. If input is X or O(case insensitive), input will be
	 * assigned to playerSymbol, otherwise, invalid input message will be printed, and player will be prompted
	 * to enter X or O again.
	 * @param input
	 */
	public static void chooseSymbol(Scanner input) {
		System.out.println("Please select your symbol(X or O).");
		playerSymbol = Character.toUpperCase(input.next().charAt(0));
		while (playerSymbol != 'X' && playerSymbol != 'O') {
			System.out.println("Invalid input. Please enter X or O.");
			playerSymbol = Character.toUpperCase(input.next().charAt(0));
		}
	}
	
	/**
	 * Method sets up board by setting all elements in the board array to an empty space for 
	 * better aesthetic of viewing when displaying board.
	 */
	public static void boardSetup() {
		for (int i = 0; i < 9; i++) {
			board[i] = ' ';
		}
	}
	
	/**
	 * Method prints out board, with board array elements separated by "|". Prints a new line
	 * if board array index is divisible by 3 without remainder, with the exception of the new line
	 *  to emulate a tictactoe board.
	 */
	public static void displayBoard() {
		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0) {
				if (i != 0) {
					System.out.println();
				}
				System.out.print("|");
				System.out.print(board[i]);
				System.out.print("|");
			} else {
				System.out.print(board[i]);
				System.out.print("|");
			}
		}
		System.out.println();

	}
	
	/**
	 * Method generates random computer move (random number from 1-9) and assigns to board array index depending
	 * on player symbol.If randomized index is already occupied, method will keep generating random moves until
	 * empty index is obtained
	 */
	public static void computerMove() {
		System.out.println("Computer moved.");
		int computerMove = (int) (9 * Math.random());
		while (board[computerMove] != ' ') {
			computerMove = (int) (9 * Math.random());
		}
		if (playerSymbol == 'X') {
			board[computerMove] = 'O';
		} else {
			board[computerMove] = 'X';
		}
	}
	
	/**
	 * Method takes as input a scanner variable input and assigns a value to placement variable which
	 * will subsequently be used by write() method. If invalid input is given, invalid input message
	 * will be displayed, and user will be prompted to enter a valid number between 1-9
	 * @param input
	 */
	private static void getCoordinate(Scanner input) {
		System.out.println("Please input desired placement space (1-9):");
		placement = input.nextInt();
		while (placement > 9 || placement < 1) {
			System.out.println("Invalid input, please input a number between 1 and 9");
			placement = input.nextInt();
		}
	}
	
	/**
	 * Method takes as input a scanner variable input and through the use of getCoordinate method,
	 * assigns the playerSymbol to the index of the board array selected by player. If an already occupied 
	 * coordinate is selected, a message will be displayed and player will be prompted to select a new coordinate
	 * @param input
	 */
	public static void write(Scanner input) {
		//obtains desired placement position from player input
		getCoordinate(input);
		//array indexing, so 1 must be subtracted from placement so that the correct index 
		//of board array is assigned
		while (board[placement - 1] != ' ') {
			System.out.println("Coordinate is already occupied, please enter a new coordinate");
			getCoordinate(input);
		}

		if (playerSymbol == 'X') {
			board[placement - 1] = 'X';
		} else {
			board[placement - 1] = 'O';
		}
	}

	
	/**
	 * Method checks if there is a winner, or if board is full and returns a boolean variable to signify
	 * whether or not game is finished. Method checks for all possible cases in which a player can win,
	 * and if board is full. If a player or the computer has won, "Player" or  "Computer" will be assigned
	 * to the string variable winner, depending on the victor. If board is full and there is no winner,
	 * STALEMATE will be printed and winner will stay as null (refer to main method as to why)
	 * @return
	 */
	public static boolean winner() {
		//Check horizontal lines for three of the same character and ensures lines are not full of spaces.
		//Return true if there is winner, and "Player" or "Computer" to winner variable based on winner
		if (board[0] == board[1] && board[1] == board[2] && board[0] != ' ') {
			if (playerSymbol == board[0]) {
				winner = "Player";
				return true;
			} else {
				winner = "Computer";
				return true;
			}
		}
		if (board[3] == board[4] && board[4] == board[5] && board[3] != ' ') {
			if (playerSymbol == board[3]) {
				winner = "Player";
				return true;
			} else {
				winner = "Computer";
				return true;
			}
		}
		if (board[6] == board[7] && board[7] == board[8] && board[6] != ' ') {
			if (playerSymbol == board[6]) {
				winner = "Player";
				return true;
			} else {
				winner = "Computer";
				return true;
			}
		}
		//Check diagonal lines for three of the same character and ensures lines are not full of spaces.
		//Return true if there is winner, and "Player" or "Computer" to winner variable based on winner
		if (board[0] == board[4] && board[4] == board[8] && board[0] != ' ') {
			if (playerSymbol == board[0]) {
				winner = "Player";
				return true;
			} else {
				winner = "Computer";
				return true;
			}
		}
		if (board[6] == board[4] && board[4] == board[2] && board[6] != ' ') {
			if (playerSymbol == board[6]) {
				winner = "Player";
				return true;
			} else {
				winner = "Computer";
				return true;
			}
		}
		//Check vertical lines for three of the same character and ensures lines are not full of spaces.
		//Return true if there is winner, and "Player" or "Computer" to winner variable based on winner
		if (board[0] == board[3] && board[3] == board[6] && board[0] != ' ') {
			if (playerSymbol == board[0]) {
				winner = "Player";
				return true;
			} else {
				winner = "Computer";
				return true;
			}
		}
		if (board[1] == board[4] && board[4] == board[7] && board[1] != ' ') {
			if (playerSymbol == board[1]) {
				winner = "Player";
				return true;
			} else {
				winner = "Computer";
				return true;
			}
		}
		if (board[2] == board[5] && board[5] == board[8] && board[2] != ' ') {
			if (playerSymbol == board[2]) {
				winner = "Player";
				return true;
			} else {
				winner = "Computer";
				return true;
			}
		}
		//Checks if board is full, return true and print STALEMATE is board is full and there is no winner
		boolean full = true;
		for (int i = 0; i < 9; i++) {

			if (board[i] == ' ') {
				full = false;
				break;
			}
		}

		if (full == true) {
			System.out.println("STALEMATE");
			return true;
		}
		//return false if no winner and end method
		return false;
	}
}
