package com.codepath.apps.mytwitterapp;

import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ComposeTweetActivity extends Activity {
	Button btButton;
	EditText textTweet;
	TextView tvText;
	TextView counter;
	Button cancel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		
		btButton = (Button) findViewById(R.id.btCompose);
		textTweet = (EditText)findViewById(R.id.etCompose);
		tvText = (TextView)findViewById(R.id.tvCompose);
		counter = (TextView)findViewById(R.id.tvCount);
		cancel = (Button)findViewById(R.id.btnCancel);
		
		
		
		final TextWatcher textwatcher = new TextWatcher() {
	        
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	        
	        }
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	                counter.setText("Count Remaining: "+String.valueOf(140-s.length()));
	                
	        }


	        public void afterTextChanged(Editable s) {
	        
	        }
	        
	        
		};
		
		textTweet.addTextChangedListener(textwatcher);
	
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}
	
	public void cancel(View v) {
        finish();
}

	public void save(View v) {
        String status = textTweet.getText().toString();
        MyTwitterApp.getRestClient().postTweet(status, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                        Log.d("DEBUG", jsonObject.toString());
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    
                }
        });
        
}

	
	//public void save(View v){
		//String tweetText = textTweet.getText().toString();
	//	Intent i = new Intent();
		//i.putExtra("Tweet Text", tweetText);
		//finish();
	//}

}
