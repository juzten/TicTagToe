package group.four.test_for_github;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class Test_for_githubActivity extends Activity {
	
	MediaPlayer mediaPlayer;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mediaPlayer = MediaPlayer.create(this, R.raw.dynamite);

        Button buttonPlay = (Button)findViewById(R.id.play);
        Button buttonPause = (Button)findViewById(R.id.pause);
        buttonPlay.setOnClickListener(buttonPlayOnClickListener);
        buttonPause.setOnClickListener(buttonPauseOnClickListener);
    }

    Button.OnClickListener buttonPlayOnClickListener
    = new Button.OnClickListener(){
    @Override
    public void onClick(View v) {
    // TODO Auto-generated method stub
    if(!mediaPlayer.isPlaying()){
     mediaPlayer.start();
     Toast.makeText(Test_for_githubActivity.this,
       "mediaPlayer.start()",
       Toast.LENGTH_LONG).show();
    }
    }
    };

    Button.OnClickListener buttonPauseOnClickListener
    = new Button.OnClickListener(){
    @Override
    public void onClick(View v) {
    // TODO Auto-generated method stub
    if(mediaPlayer.isPlaying()){
     mediaPlayer.pause();
     Toast.makeText(Test_for_githubActivity.this,
       "mediaPlayer.pause()",
       Toast.LENGTH_LONG).show();
    }
    }
    };

    }