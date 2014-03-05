package com.example.ebook;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MusicActivity extends Activity {
	MediaPlayer msp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		msp = MediaPlayer.create(this, R.raw.music);
		msp.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		msp.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		msp.start();
		super.onResume();
	}

	public void playMusic(View v) {
		// TODO Auto-generated method stub
		msp.start();
	}

	public void pauseMusic(View v) {
		// TODO Auto-generated method stub
		msp.pause();
	}

	public void stopMusic(View v) {
		// TODO Auto-generated method stub
		msp.stop();
	}
	
	public void openWiki(View v){
		String url = "http://www.wikipedia.org";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
	
}