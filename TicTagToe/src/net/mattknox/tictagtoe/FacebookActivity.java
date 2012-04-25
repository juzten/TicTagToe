package net.mattknox.tictagtoe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FacebookActivity extends Activity implements DialogListener {

	private Facebook facebookClient;
	private LinearLayout facebookButton;
	private final String APP_API_ID = "239440319487634";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		facebookClient = new Facebook("239440319487634");
		// replace APP_API_ID with your own
		facebookClient.authorize(this, new String[] {
				"publish_stream", "read_stream", "offline_access" }, this);

	}

	public void onComplete(Bundle values) {
		  if (values.isEmpty())
	        {
	            //"skip" clicked ?
	            return;
	        }

	        // if facebookClient.authorize(...) was successful, this runs
	        // this also runs after successful post
	        // after posting, "post_id" is added to the values bundle
	        // I use that to differentiate between a call from
	        // faceBook.authorize(...) and a call from a successful post
	        // is there a better way of doing this?
	        if (!values.containsKey("239440319487634"))
	        {
	            try
	            {
	                Bundle parameters = new Bundle();
	                parameters.putString("message", "this is a test");// the message to post to the wall
	                facebookClient.dialog(this, "stream.publish", parameters, this);// "stream.publish" is an API call
	            }
	            catch (Exception e)
	            {
	                // TODO: handle exception
	                System.out.println(e.getMessage());
	            }
	        }
	    }

	    public void onError(DialogError e)
	    {
	        System.out.println("Error: " + e.getMessage());
	    }

	    public void onFacebookError(FacebookError e)
	    {
	        System.out.println("Error: " + e.getMessage());
	    }

	    public void onCancel()
	    {
	    	finish();
	    }

}