package com.example.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;

public class MenuActivity extends SherlockActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}

	public void goToBook(View v) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);

	}

	public void goToForm(View v) {
		Intent i = new Intent(this, SubActivity.class);
		startActivity(i);
	}

	public void goToMusic(View v) {
		Intent i = new Intent(this, MusicActivity.class);
		startActivity(i);
	}

	public void goToImage(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, ImageActivity.class);
		startActivity(i);
	}
}
