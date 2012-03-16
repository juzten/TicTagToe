package net.mattknox.tictagtoe;

public class GameState {
	
final static int BOARD_SIZE = 9;

//these variable declarations are just for readability
final static int X = 1;
final static int O = 2;

int winner;
	
	//Array of squares that represents the state of the gameboard
	//Location of square in array determines which square it represents in the display
	Square[] squares;
	
		public GameState() {
		
			squares = new Square[BOARD_SIZE];
			for (int i = 0; i < squares.length; i++) {
				squares[i] = new Square();
			} //end for
			
			winner = 0;
		} //end constructor
		
		private void checkForWinner() {
	    	
	    	
	    	//3 horizontal rows
		    	compareSquares(squares[0], squares[1], squares[2]);
		    	compareSquares(squares[3], squares[4], squares[5]);
		    	compareSquares(squares[6], squares[7], squares[8]);
		    //3 vertical rows
		    	compareSquares(squares[0], squares[3], squares[6]);
		    	compareSquares(squares[1], squares[4], squares[7]);
		    	compareSquares(squares[2], squares[5], squares[8]);
		    //2 diagonals
		    	compareSquares(squares[0], squares[4], squares[8]);
		    	compareSquares(squares[2], squares[4], squares[6]);

	    	
	    } //end checkForWinner
		
		private void compareSquares(Square square1, Square square2, Square square3) {
	    	int button1 = square1.getMove();
	    	int button2 = square2.getMove();
	    	int button3 = square3.getMove();
	    	
	    	//make sure the square contains a string
	    	if (button1 != 0) {
	    		if ( (button1 == button2) && (button2 == button3) ) {
	    			winner = square1.getMove();
	    		} //end nested if
	    	} //end if
	    } //end compareSquares
		
		public String getMoveString(int location) {
			int move = squares[location].getMove();
			String moveString = "";
			if (move == 1) moveString = "X";
			else if (move == 2) moveString = "O";
			return moveString;
			
		}
		
		public void setMove(int locationArg, int playerArg) {
			if (winner == 0) squares[locationArg].setMove(playerArg);
			checkForWinner();
		}
		
		public int getWinner() {
			return winner;
		}
	
	public class Square {
	
		boolean containsMove;
		int xOrO;
		
		public Square() {
			containsMove = false;
			xOrO = 0;
		} //end Constructor
		
		//setMove takes a 0, 1, or 2.  0 resets the square, 1 places an X, 2 places an O
		public void setMove(int playerArg) {
			xOrO = playerArg;
		}
		
		public int getMove() {
			return xOrO;
		}
	
	} //end Square
	
}
