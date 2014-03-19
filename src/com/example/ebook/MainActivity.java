package com.example.ebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends Activity {
	WebView pointer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pointer = (WebView) findViewById(R.id.webView1);
		pointer.loadUrl("file:///android_asset/index.html");
		pointer.getSettings().setBuiltInZoomControls(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (pointer.canGoBack())) {
			pointer.goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void goToForm(View v) {
		Intent i = new Intent(this, SubActivity.class);
		startActivity(i);
	}

}
