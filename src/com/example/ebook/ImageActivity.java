package com.example.ebook;

import java.io.InputStream;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

public class ImageActivity extends SherlockActivity {

	private static final String KEY_SHORT = "Short";
	private static final String KEY_MEDIUM = "Medium";
	private static final String KEY_LONG = "Long";
	private static final int STROKE_WIDTH = 2;
	private static final int REQUEST_CODE = 1;
	private static final String TAG = "sw1ch";
	private Bitmap mBitmap;
	private Bitmap clearBitmap; // bitmap used for "clearing the canvas"
	private Canvas mCanvas;
	private ImageView mImageView;
	private int maxX;
	private int maxY;
	private int fX;
	private int fY;
	private int sX;
	private int sY;
	private SharedPreferences mPrefs;
	private Editor mEditor;
	private int countLong;
	private int countMedium;
	private int countShort;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		actionBar = getSupportActionBar();
		getResources();
		mPrefs = getSharedPreferences("stats", MODE_PRIVATE);
		mEditor = mPrefs.edit();
		countShort = mPrefs.getInt(KEY_SHORT, -1);
		countMedium = mPrefs.getInt(KEY_MEDIUM, -1);
		countLong = mPrefs.getInt(KEY_LONG, -1);

		Log.d(TAG, "Activity started!");
		// create bitmap 32
		mBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);

		// get Bitmap that will be used for clearing
		clearBitmap = Bitmap.createBitmap(mBitmap); // create immutable
		clearBitmap.eraseColor(0xff000000);

		// Add to canvas Bitmap
		mCanvas = new Canvas(mBitmap);
		mCanvas.drawColor(0xff000000);

		// ImageView mImageView = new ImageView(this);
		// mImageView.setImageBitmap(mBitmap);
		setContentView(R.layout.activity_image_late);
		mImageView = (ImageView) findViewById(R.id.image12);
		mImageView.setImageBitmap(mBitmap);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		SubMenu subMenu1 = menu.addSubMenu("Actions");
		subMenu1.add(menu.NONE, 1, menu.NONE, R.string.choose_image);
		subMenu1.add(menu.NONE, 2, menu.NONE, R.string.clear_image);
		subMenu1.add(menu.NONE, 3, menu.NONE, R.string.statistics);

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			chooseBitmap();
			return true;
		case 2:
			clearCanvas();
			return true;
		case 3:
			statisticActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		maxX = mCanvas.getWidth();
		maxY = mCanvas.getHeight();
		String mString;
		if (hasFocus)
			mString = "Window focus received";
		else
			mString = "Window focus lost";
		Log.d(TAG, mString);

	}

	// Draws the line on canvas from random coordinates;
	// method will be called from a push of a button
	public void drawLine(View v) {
		// First point coords
		fX = (int) (Math.random() * (maxX + 1));
		fY = (int) (Math.random() * (maxY + 1));
		// Second point coords
		sX = (int) (Math.random() * (maxX + 1));
		sY = (int) (Math.random() * (maxY + 1));

		Paint mPaint = new Paint();
		int colorCode = chooseColor(fX, fY, sX, sY);
		if (colorCode == 0)
			mPaint.setColor(0xffff0000); // red
		else if (colorCode == 1)
			mPaint.setColor(0xff0000ff); // green
		else
			mPaint.setColor(0xff00ff00); // blue

		mPaint.setStrokeWidth(STROKE_WIDTH); // A thick line

		// From top left to bottom right
		mCanvas.drawLine(fX, fY, sX, sY, mPaint);
		mEditor.commit();
		// add to ImageView updated Bitmap
		mImageView.setImageBitmap(mBitmap);
		Log.d(TAG, "Line was drawn on canvas! [" + fX + ", " + fY + ",] and ["
				+ sX + ", " + sY + "]");
	}

	// Function chooses a color based on length of a line
	// small lines - red
	// medium - blue
	// large - green
	// method will be called from a push of a button
	private int chooseColor(int fX, int fY, int sX, int xY) {
		// 50 < dxy = near (red)
		// 50 > dxy < 125 = average (blue)
		// dxy > 125 = far (green)
		int dXY = (int) (Math.sqrt(Math.pow((sX - fX), 2)
				+ Math.pow((sY - fY), 2)));
		Log.d(TAG, "dXY: " + dXY);
		if (dXY > 200) {
			mEditor.putInt(KEY_LONG, ++countLong);
			return 2;
		} else if ((dXY <= 200) && (dXY > 100)) {
			mEditor.putInt(KEY_MEDIUM, ++countMedium);
			return 1;
		} else {
			mEditor.putInt(KEY_SHORT, ++countShort);
			return 0;
		}
	}

	// clears the canvas
	// method will be called from a push of a button
	public void clearCanvas() {
		Log.d(TAG, "Canvas was cleared!");
		// if (clearBitmap == null) mCanvas.drawColor(0xFF000000);
		// mCanvas.drawBitmap(clearBitmap, 0, 0, null);
		mBitmap = clearBitmap.copy(Bitmap.Config.ARGB_8888, true);
		mCanvas = new Canvas(mBitmap);
		mImageView.setImageBitmap(mBitmap);
		Log.d(TAG, "Short: " + mPrefs.getInt(KEY_SHORT, -1));
		Log.d(TAG, "Medium: " + mPrefs.getInt(KEY_MEDIUM, -1));
		;
		Log.d(TAG, "Long: " + mPrefs.getInt(KEY_LONG, -1));

	}

	// choose a bitmap from directory
	// method will be called from a push of a button
	public void chooseBitmap() {
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.setType("image/*");
		Intent iChooser = Intent.createChooser(i, "Select");
		startActivityForResult(iChooser, REQUEST_CODE);
		// startActivityForResult -> onActivityResult
	}

	// Bitmap will be decoded 2 times:
	// first time will be to initiated for checking if the bitmap need to be
	// sampled
	// second time will be called to sample the image (if needed), add it to the
	// canvas and draw in on the ImageView
	// called from startActivityForResult()
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			// get URI for data
			Uri uri = data.getData();
			Log.d(TAG, uri.toString());
			Toast.makeText(getApplicationContext(), uri.toString(),
					Toast.LENGTH_LONG).show();
			try {
				// InputStream for decoding the bitmap
				InputStream stream = getContentResolver().openInputStream(uri);

				// Get options
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				Log.d(TAG, "Options created");

				// Get Bitmap from stream with edited options
				BitmapFactory.decodeStream(stream, null, options);
				stream.close();

				// get dimensions
				int displayW = getResources().getDisplayMetrics().widthPixels;
				int displayH = getResources().getDisplayMetrics().heightPixels;
				Log.d(TAG, "Display size:" + displayH + " x " + displayW);
				int w = options.outWidth;
				int h = options.outHeight;
				Log.d(TAG, "Bitmap raw size:" + w + " x " + h);

				// image sampling
				int sample = 1;
				while (w > displayW * sample || h > displayH * sample) {
					sample = sample * 2;
				}
				Log.d(TAG, "Sampling at " + sample);

				// InputStream for decoding the bitmap
				stream = getContentResolver().openInputStream(uri);
				options.inJustDecodeBounds = false;
				options.inSampleSize = sample;

				// Get Bitmap from stream with edited options
				clearBitmap = BitmapFactory.decodeStream(stream, null, options);
				stream.close();
				mBitmap = clearBitmap.copy(Bitmap.Config.ARGB_8888, true);

				// ImageView v = (ImageView) findViewById(R.id.image12);
				// Add new canvas to the Bitmap
				mCanvas = new Canvas(mBitmap);
				mImageView.setImageBitmap(mBitmap);
				Log.d(TAG, "Bitmap decoded successfully");

			} catch (Exception e) {
				Log.e(TAG, "Decoding error:", e);
			}

		}
	}

	public void statisticActivity() {
		Intent i = new Intent(this, StatsActivity.class);
		startActivity(i);
	}
}
