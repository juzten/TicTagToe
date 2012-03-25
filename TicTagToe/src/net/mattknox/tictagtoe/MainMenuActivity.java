package net.mattknox.tictagtoe;

//import com.Tutorial.Sound.SoundManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainMenuActivity extends Activity {
    /** Called when the activity is first created. */
	Button startGameButton;
	Spinner gameTypeSpinner;

	int gameType; // 0=LocalVsAi ; 1=LocalVsLocal ; 2=LocalVsInternet
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        
        gameType = 1; //Defaulting this to local player vs. local player
        initDisplay(); //Initializes all of the widgets and what-not
        
        
        
        
        
    }
    
    private void initDisplay() {
    	startGameButton = (Button) findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(clickStartListener);
        
        gameTypeSpinner = (Spinner) findViewById(R.id.gameTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gameTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameTypeSpinner.setAdapter(adapter);
    }
    
    OnClickListener clickStartListener = new OnClickListener() {
    	public void onClick(View v) {
    		
    		int selectedGameType = gameTypeSpinner.getSelectedItemPosition();
    		if (selectedGameType != -1) gameType = selectedGameType;
    		
    		startGame();
    		
    	} //end onClick
    }; //end OnClickListener
    
    public void startGame() {
    	
            
    	Intent intent = new Intent(MainMenuActivity.this, GameDisplayActivity.class);
    	

    	intent.putExtra("type", gameType);
			startActivity(intent);
			
    }

    
}