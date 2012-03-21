//If this shows up, I was able to commit a file -Matt

package net.mattknox.tictagtoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TicTagToeActivity extends Activity {
    /** Called when the activity is first created. */
	
	Button singlePlayerButton;
	Button multiPlayerButton;
	int gameType;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        
        singlePlayerButton = (Button) findViewById(R.id.singlePlayerButton);
        singlePlayerButton.setOnClickListener(clickSinglePlayerListener);
        multiPlayerButton = (Button) findViewById(R.id.multiPlayerButton);
        multiPlayerButton.setOnClickListener(clickMultiPlayerListener);
        
    }
    
    OnClickListener clickSinglePlayerListener = new OnClickListener() {
    	public void onClick(View v) {
    		gameType = 1;
    		startGame();
    	} //end onClick
    }; //end OnClickListener
    
    OnClickListener clickMultiPlayerListener = new OnClickListener() {
    	public void onClick(View v) {
    		gameType = 2;
    		startGame();
    	} //end onClick
    }; //end OnClickListener
    
    public void startGame() {
    	Intent intent = new Intent(TicTagToeActivity.this, GameBoard.class);
			startActivity(intent);
    }
    
}