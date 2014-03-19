package com.example.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;

public class SubActivity extends SherlockActivity {
	public final static String EXTRA_MESSAGE = "com.example.ebook.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
	}

	public void sendData(View v) {
		EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
		EditText lastnameEdit = (EditText) findViewById(R.id.lastnameEdit);
		EditText phoneEdit = (EditText) findViewById(R.id.phoneEdit);
		String name = nameEdit.getText().toString();
		String lastname = lastnameEdit.getText().toString();
		String phone = phoneEdit.getText().toString();
		String message ="Name: " + name + "\nLastname " + lastname + " \nPhone number: "+ phone; 
		Intent i = new Intent(this, DisplayMessageActivity.class);
		i.putExtra(EXTRA_MESSAGE, message);
		startActivity(i);
	}

}
