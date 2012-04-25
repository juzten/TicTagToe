package net.mattknox.tictagtoe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class GameDisplayActivity extends Activity implements DialogListener {

	MediaPlayer mediaPlayer;// n
	GameManager manager;
	ImageButton[] boardUI;
	TextView viewWhichTurn;
	String[] ImageButtonLocation;
	int winner;
	int lastBtnPress = -1;
	int gameType;
	int post = 0;
	Facebook mFacebook = new Facebook("239440319487634");

	/** Called when the activity is first created. */
	// @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent b = getIntent();
		gameType = b.getExtras().getInt("type");

		// TODO Auto-generated method stub
		setContentView(R.layout.gameboard);

		boardUI = new ImageButton[9];
		ImageButtonLocation = new String[9];
		initBoardUI();
		manager = new GameManager();
		viewWhichTurn = (TextView) findViewById(R.id.viewWhichTurn);
		ImageButton submitButton;
		submitButton = (ImageButton) findViewById(R.id.submitBtn);
		submitButton.setOnClickListener(clickSubmitButtonListener);

		winner = 0;
		updateStatusMessage();

		mediaPlayer = MediaPlayer.create(this, R.raw.dynamite);
		mediaPlayer.start();
		// allows volume keys to set game volume
		setVolumeControlStream(AudioManager.STREAM_MUSIC); // added for music

	} // end onCreate

	public void updateBoardUI() {
		// for (int i = 0; i <= 8; i++) {

		// ImageButton currentImageButton = boardUI[i];
		// String moveMarker = manager.getMove(i);
		// int bckgrnd = R.drawable.transparent;
		// if (moveMarker == "X") bckgrnd = R.drawable.xmark;
		// else if (moveMarker == "O") bckgrnd = R.drawable.omark;
		// manager.getMove(i);
		// currentImageButton.setImageResource(bckgrnd);
		// currentImageButton.setClickable(false); //disables the ImageButton if
		// it contains text
		// } //end for

		updateStatusMessage();

	} // end updateBoardUI

	public void initBoardUI() {
		boardUI[0] = (ImageButton) findViewById(R.id.button0);
		boardUI[1] = (ImageButton) findViewById(R.id.button1);
		boardUI[2] = (ImageButton) findViewById(R.id.button2);
		boardUI[3] = (ImageButton) findViewById(R.id.button3);
		boardUI[4] = (ImageButton) findViewById(R.id.button4);
		boardUI[5] = (ImageButton) findViewById(R.id.button5);
		boardUI[6] = (ImageButton) findViewById(R.id.button6);
		boardUI[7] = (ImageButton) findViewById(R.id.button7);
		boardUI[8] = (ImageButton) findViewById(R.id.button8);

		for (int i = 0; i <= 8; i++) {
			boardUI[i].setOnClickListener(gameImageButtonListener);
			String ImageButtonId = boardUI[i].toString();
			ImageButtonLocation[i] = ImageButtonId;
		} // end for

	} // end initBoardUI

	OnClickListener gameImageButtonListener = new OnClickListener() {
		public void onClick(View v) {

			int moveMarker = manager.getCurrentPlayer();
			int location = 0;
			ImageButton subBtn = (ImageButton) findViewById(R.id.submitBtn);
			subBtn.setImageResource(R.drawable.passbutton);

			// this for loop matches the current ImageButton id to the id stored
			// in the ImageButtonLocation array
			for (int i = 0; i <= 8; i++) {
				String clickedId = v.toString();
				if (clickedId.equals(ImageButtonLocation[i]))
					location = i;
			}

			if (lastBtnPress >= 0)
				boardUI[lastBtnPress].setImageResource(R.drawable.transparent);

			ImageButton currentImageButton = boardUI[location];
			int bckgrnd = R.drawable.transparent;
			if (moveMarker == 1)
				bckgrnd = R.drawable.xmark;
			else if (moveMarker == 2)
				bckgrnd = R.drawable.omark;
			currentImageButton.setImageResource(bckgrnd);
			lastBtnPress = location;

			// if (winner == 0) manager.submitMove(location);
			// updateBoardUI();

		} // end onClick
	}; // end gameImageButtonListener

	public void updateStatusMessage() {
		winner = manager.getWinner();
		if (winner == 0) {
			String message = "";
			message += manager.getPlayerName(manager.getCurrentPlayer());
			message += "'s Turn";
			viewWhichTurn.setText(message);
		} else if (winner == 10) {
			String message = "No Remaining Moves!";
			viewWhichTurn.setText(message);
			for (int i = 0; i < 9; i++) {
				if (boardUI[i].isClickable())
					boardUI[i].setClickable(false);
			} // end for
			ImageButton mnuBtn = (ImageButton) findViewById(R.id.submitBtn);
			mnuBtn.setImageResource(R.drawable.menubutton);
		} else {
			String message = "";
			message += manager.getPlayerName(winner);
			message += " won the game!";
			viewWhichTurn.setText(message);
			for (int i = 0; i < 1; i++) {
				boardUI[i].setClickable(false);

				ImageButton facebookButton = (ImageButton) findViewById(R.id.fcbkBtn);
				facebookButton.setClickable(true);
				facebookButton.setVisibility(View.VISIBLE);
				facebookButton.setOnClickListener(facebookListener);

				ImageButton twitterButton = (ImageButton) findViewById(R.id.twitterBtn);
				twitterButton.setClickable(true);
				twitterButton.setVisibility(View.VISIBLE);
				twitterButton.setOnClickListener(twitterListener);
				//
				// AlertDialog.Builder builder = new AlertDialog.Builder(this);
				//
				// builder.setTitle("You Won!");
				//
				// builder.setMessage("Would you like to post to facebook or twitter?");
				//
				// builder.setCancelable(true);
				//
				// builder.setPositiveButton("Yes",
				// new DialogInterface.OnClickListener() {
				//
				// public void onClick(DialogInterface dialog,
				// int which) {
				//
				// setContentView(R.layout.share);
				// Button btnFacebook = (Button) findViewById(R.id.btnFacebook);
				// Button btnTwitter = (Button) findViewById(R.id.btnTwitter);
				//
				// // when facebook button is clicked
				// btnFacebook.setOnClickListener(new View.OnClickListener() {
				//
				//
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// // post on user's wall.
				//
				//
				// }
				// });//end btnfacebook
				//
				// // when twitter button is clicked
				// btnTwitter.setOnClickListener(new View.OnClickListener() {
				//
				//
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				//
				// String stringToTweet = "I won a game of tic tac toe!!!" ;
				// Intent twitterIntent = new
				// Intent(android.content.Intent.ACTION_SEND);
				// twitterIntent.setType("plain/text");
				//
				// twitterIntent.setAction("android.intent.action.SEND");
				// twitterIntent.setFlags(0x3000000);
				// twitterIntent.setType("text/plain");
				// twitterIntent.setClassName("com.twitter.android",
				// "com.twitter.android.PostActivity");
				// twitterIntent.putExtra(android.content.Intent.EXTRA_TEXT,
				// stringToTweet);
				// startActivity(twitterIntent);
				//
				// }
				// });//end btnTwitter
				//
				// }
				// });
				// builder.setNegativeButton("No", new
				// DialogInterface.OnClickListener() {
				//
				// public void onClick(DialogInterface dialog, int which) {
				//
				// finish();
				//
				// }
				// });
				//
				// AlertDialog resetDialog = builder.create();
				// resetDialog.show();
				//
				//
				//
				//
				// Bundle parameters = new Bundle();
				//
				// parameters.putString("message", "this is a test");
				// mFacebook.dialog(this, "stream.publish", parameters, this);
				//

				// AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(
				// mFacebook);
				// mAsyncRunner.request("me/feed", parameters,
				// new AsyncFacebookRunner.RequestListener() {
				//
				// public void onMalformedURLException(
				// MalformedURLException e, Object state) {
				// }
				//
				// public void onIOException(IOException e,
				// Object state) {
				// }
				//
				// public void onFileNotFoundException(
				// FileNotFoundException e, Object state) {
				// }
				//
				// public void onFacebookError(FacebookError e,
				// Object state) {
				// }
				//
				// public void onComplete(String response, Object state) {
				// }
				// });
				// AlertDialog.Builder builder = new AlertDialog.Builder(this);
				//
				// builder.setTitle("You Won!");
				//
				// builder.setMessage("Would you like to post to facebook?");
				//
				// builder.setCancelable(true);
				//
				// builder.setPositiveButton("Yes",
				// new DialogInterface.OnClickListener() {
				//
				// public void onClick(DialogInterface dialog,
				// int which) {
				//
				// Intent getURL = new Intent(Intent.ACTION_VIEW,
				// Uri.parse("http://www.facebook.com"));
				//
				// startActivity(getURL);
				//
				// }
				// });
				// builder.setNegativeButton("No", new
				// DialogInterface.OnClickListener() {
				//
				// public void onClick(DialogInterface dialog, int which) {
				// finish();
				//
				// }
				// });
				//
				// AlertDialog resetDialog = builder.create();
				// resetDialog.show();
			} // end for
		}
	}

	OnClickListener twitterListener = new OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub

			String stringToTweet = "I won a game of tic tac toe!!!";
			Intent twitterIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			twitterIntent.setType("plain/text");

			twitterIntent.setAction("android.intent.action.SEND");
			twitterIntent.setFlags(0x3000000);
			twitterIntent.setType("text/plain");
			twitterIntent.setClassName("com.twitter.android",
					"com.twitter.android.PostActivity");
			twitterIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					stringToTweet);
			startActivity(twitterIntent);
		}
	};
	OnClickListener facebookListener = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(GameDisplayActivity.this,
					FacebookActivity.class);
			startActivity(i);
		}
	};

	OnClickListener clickSubmitButtonListener = new OnClickListener() {
		public void onClick(View v) {

			int moveMarker = manager.getCurrentPlayer();
			int location;

			if (lastBtnPress == -1)
				finish();

			else if (winner == 0) {
				manager.submitMove(lastBtnPress);
				updateBoardUI();
				boardUI[lastBtnPress].setClickable(false);
				lastBtnPress = -1;
				ImageButton mnuBtn = (ImageButton) findViewById(R.id.submitBtn);
				mnuBtn.setImageResource(R.drawable.menubutton);

				if (winner == 0)
					moveMarker = manager.getCurrentPlayer();

				if (gameType == 1 && moveMarker == 2 && winner == 0) {
					AiPlayer computer = new AiPlayer(manager.game);
					location = computer.nextMove(moveMarker);
					lastBtnPress = location;
					int bckgrnd = R.drawable.omark;
					boardUI[location].setImageResource(bckgrnd);

					if (winner == 0) {
						manager.submitMove(lastBtnPress);
						updateBoardUI();
						boardUI[lastBtnPress].setClickable(false);
						lastBtnPress = -1;
					}
				}
			}

		} // end onClick

	}; // end OnClickListener

	public void onPause() {
		super.onPause();

		mediaPlayer.pause();
	} // end onPause

	public void onResume() {
		super.onResume();
		mediaPlayer.start();
	} // end onResume

	public void onComplete(Bundle values) {
		if (values.isEmpty()) {
			// "skip" clicked ?
			return;
		}

		// if facebookClient.authorize(...) was successful, this runs
		// this also runs after successful post
		// after posting, "post_id" is added to the values bundle
		// I use that to differentiate between a call from
		// faceBook.authorize(...) and a call from a successful post
		// is there a better way of doing this?
		if (!values.containsKey("239440319487634")) {
			try {
				Bundle parameters = new Bundle();
				parameters.putString("message", "this is a test");// the message
																	// to post
																	// to the
																	// wall
				mFacebook.dialog(this, "stream.publish", parameters, this);// "stream.publish"
																			// is
																			// an
																			// API
																			// call
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
	}

	public void onError(DialogError e) {
		System.out.println("Error: " + e.getMessage());
	}

	public void onFacebookError(FacebookError e) {
		System.out.println("Error: " + e.getMessage());
	}

	public void onCancel() {
		finish();
	}

}
