package net.mattknox.tictagtoe;

//import com.Tutorial.Sound.SoundManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

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
	Button facebookButton;
	Button facebookLogout;
	Spinner gameTypeSpinner;

	int gameType; // 0=LocalVsAi ; 1=LocalVsLocal ; 2=LocalVsInternet

	// create facebook object.
	Facebook facebook = new Facebook("239440319487634"); // app id
	AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);

		gameType = 1; // Defaulting this to local player vs. local player
		initDisplay(); // Initializes all of the widgets and what-not

	}

	private void initDisplay() {
		startGameButton = (Button) findViewById(R.id.startGameButton);
		startGameButton.setOnClickListener(clickStartListener);

		facebookButton = (Button) findViewById(R.id.facebookbtn);
		facebookButton.setOnClickListener(signInListener);

		facebookLogout = (Button) findViewById(R.id.facebooklogout);
		facebookLogout.setOnClickListener(logoutListener);

		gameTypeSpinner = (Spinner) findViewById(R.id.gameTypeSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.gameTypes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gameTypeSpinner.setAdapter(adapter);
	}

	OnClickListener signInListener = new OnClickListener() {
		public void onClick(View v) {

			// facebook single sign on
			facebook.authorize(MainMenuActivity.this, new DialogListener() {
				public void onComplete(Bundle values) {
				}

				public void onCancel() {
				}

				public void onFacebookError(FacebookError e) {
				}

				public void onError(DialogError e) {
				}
			});

		}
	};

	// for facebook
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	OnClickListener logoutListener = new OnClickListener() {
		public void onClick(View v) {
			mAsyncRunner.logout(getBaseContext(), null,
					new AsyncFacebookRunner.RequestListener() {
						public void onComplete(String response, Object state) {
						}

						public void onIOException(IOException e, Object state) {
						}

						public void onFileNotFoundException(
								FileNotFoundException e, Object state) {
						}

						public void onMalformedURLException(
								MalformedURLException e, Object state) {
						}

						public void onFacebookError(FacebookError e,
								Object state) {
						}
					});
		}
	};

	OnClickListener clickStartListener = new OnClickListener() {
		public void onClick(View v) {

			int selectedGameType = gameTypeSpinner.getSelectedItemPosition();
			if (selectedGameType != -1)
				gameType = selectedGameType;

			startGame();

		} // end onClick
	}; // end OnClickListener

	public void startGame() {

		Intent intent = new Intent(MainMenuActivity.this,
				GameDisplayActivity.class);

		intent.putExtra("type", gameType);
		startActivity(intent);

	}

}