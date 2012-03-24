package net.mattknox.tictagtoe;

//import com.Tutorial.Sound.SoundManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.media.AudioManager; //added for music
import android.media.MediaPlayer;
public class TicTagToeActivity extends Activity {
    /** Called when the activity is first created. */
	Button singlePlayerButton;
	Button multiPlayerButton;
	int gameType;
	
	MediaPlayer mediaPlayer;//n
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        
        singlePlayerButton = (Button) findViewById(R.id.singlePlayerButton);
        singlePlayerButton.setOnClickListener(clickSinglePlayerListener);
        multiPlayerButton = (Button) findViewById(R.id.multiPlayerButton);
        multiPlayerButton.setOnClickListener(clickMultiPlayerListener);
        //allows volume keys to set game volume
        setVolumeControlStream(AudioManager.STREAM_MUSIC); //added for music
        
        mediaPlayer = MediaPlayer.create(this, R.raw.dynamite);
        
        
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
    	
            mediaPlayer.start();
            
    	Intent intent = new Intent(TicTagToeActivity.this, GameBoard.class);
    	

    	intent.putExtra("type", gameType);
			startActivity(intent);
			
    }
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	mediaPlayer.pause();
    }
    
}