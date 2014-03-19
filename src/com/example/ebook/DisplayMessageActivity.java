package com.example.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class DisplayMessageActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		Intent i = getIntent();
		String message = i.getStringExtra(SubActivity.EXTRA_MESSAGE);
		TextView v = (TextView) findViewById(R.id.message);
		v.setText(message);
	}

	

}
