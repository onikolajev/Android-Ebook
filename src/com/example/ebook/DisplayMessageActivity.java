package com.example.ebook;

import com.example.ebook.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

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
