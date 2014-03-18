package com.example.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class MenuActivity extends SherlockActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
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
