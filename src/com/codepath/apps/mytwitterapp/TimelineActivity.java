package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class TimelineActivity extends Activity implements OnScrollListener{
	
	
	private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	ListView lvTweets;
	private long  id=0;
	private boolean isUpdating = false;
	private TweetsAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		//MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			//public void onSuccess(JSONArray jsonTweets){
				//tweets = Tweet.fromJson(jsonTweets);
				
				lvTweets = (ListView) findViewById(R.id.lvTweets);
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				loadTimeline();
				lvTweets.setOnScrollListener(this);	
			
	}
		
	
	
	private void loadTimeline() {
        isUpdating = true;
        MyTwitterApp.getRestClient().getTimeline(id, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONArray jsonTweets) {
                        Log.d("DEBUG", jsonTweets.toString());
                        tweets = Tweet.fromJson(jsonTweets);
                        adapter.addAll(tweets);
                        
                        if(tweets.size()>0) {
                                Tweet tweet = tweets.get(tweets.size()-1);
                                id = tweet.getId();
                        }
                        isUpdating = false;
                                
                }
        });
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100) {
                if(resultCode==RESULT_OK) {
                        adapter.clear();
                        id = 0;
                        loadTimeline();
                        Log.d("DEBUG", "onActivityResult-2");
                }
        }
	}


	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void composeTweet(MenuItem mi){
		Intent i = new Intent(getApplicationContext(),ComposeTweetActivity.class);
		startActivityForResult(i, 100);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if(isUpdating || totalItemCount == 0)
            return;
		if((totalItemCount - firstVisibleItem)<8)
            loadTimeline();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
