package net.mattknox.tictagtoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameBoard extends Activity {

	GameManager manager;
	Button[] boardUI;
	TextView viewWhichTurn;
	String[] buttonLocation;
	int winner;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    setContentView(R.layout.gameboard);
	    
	    boardUI = new Button[9];
	    buttonLocation = new String[9];
	    initBoardUI();
	    manager = new GameManager();
	    viewWhichTurn = (TextView) findViewById(R.id.viewWhichTurn);
	    
	    
	    winner = 0;
	    updateStatusMessage();
	}
	
	public void updateBoardUI() {
		for (int i = 0; i <= 8; i++) {
			
			Button currentButton = boardUI[i];
			currentButton.setText(manager.getMove(i));
			if (currentButton.getText() != "") currentButton.setClickable(false); //disables the button if it contains text
		} //end for
		
		updateStatusMessage();
		
	} //end updateBoardUI
	
	public void initBoardUI() {
		boardUI[0] = (Button)findViewById(R.id.button0);
		boardUI[1] = (Button)findViewById(R.id.button1);
		boardUI[2] = (Button)findViewById(R.id.button2);
		boardUI[3] = (Button)findViewById(R.id.button3);
		boardUI[4] = (Button)findViewById(R.id.button4);
		boardUI[5] = (Button)findViewById(R.id.button5);
		boardUI[6] = (Button)findViewById(R.id.button6);
		boardUI[7] = (Button)findViewById(R.id.button7);
		boardUI[8] = (Button)findViewById(R.id.button8);
		
		for (int i = 0; i <= 8; i++) {
			boardUI[i].setOnClickListener(gameButtonListener);
			String buttonId = boardUI[i].toString();
			buttonLocation[i] = buttonId;
		} //end for
		
	} //end initBoardUI
	
	OnClickListener gameButtonListener = new OnClickListener() {
		public void onClick(View v) {
			int location = 0;
			
			//this for loop matches the current button id to the id stored in the buttonLocation array 
			for (int i = 0; i <= 8; i++) {
				String clickedId = v.toString();
				if (clickedId.equals(buttonLocation[i])) location = i;
			}
			
			if (winner == 0) manager.submitMove(location);
			updateBoardUI();
			
		} //end onClick
	}; //end gameButtonListener
	
	public void updateStatusMessage() {
		winner = manager.getWinner();
		if (winner == 0) {
			String message = "";
			message += manager.getPlayerName(manager.getCurrentPlayer());
			message += "'s Turn";
			viewWhichTurn.setText(message);
		}
		else {
			String message = "";
			message += manager.getPlayerName(winner);
			message += " won the game!";
			viewWhichTurn.setText(message);
		}
	}

}
