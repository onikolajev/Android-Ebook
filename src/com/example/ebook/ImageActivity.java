package com.example.ebook;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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

public class ImageActivity extends Activity {

	private static final int REQUEST_CODE = 1;
	private static final String TAG = "sw1ch";
	private Bitmap mBitmap;
	private Bitmap mImage;
	private Canvas mCanvas;
	private ImageView mImageView;
	private int maxX;
	private int maxY;
	private int fX;
	private int fY;
	private int sX;
	private int sY;
	private Resources res;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		res = getResources();

		Log.d(TAG, "Activity started!");
		// create bitmap 32
		mBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);

		// get image
		mImage = BitmapFactory.decodeResource(res, R.drawable.me_mf);

		// canvas
		mCanvas = new Canvas(mBitmap);
		mCanvas.drawColor(0xff000000);

		// ImageView mImageView = new ImageView(this);
		// mImageView.setImageBitmap(mBitmap);
		setContentView(R.layout.activity_image_late);
		mImageView = (ImageView) findViewById(R.id.image12);
		mImageView.setImageBitmap(mBitmap);
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

		mPaint.setStrokeWidth(8); // A thick line

		// From top left to bottom right
		mCanvas.drawLine(fX, fY, sX, sY, mPaint);

		// draw updated bitmap
		mImageView.setImageBitmap(mBitmap);
		Log.d(TAG, "Line was drawn on canvas! [" + fX + ", " + fY + ",] and ["
				+ sX + ", " + sY + "]");
	}

	private int chooseColor(int fX, int fY, int sX, int xY) {
		// 50<dxy = near (green)
		// 50>dxy <125 = average (blue)
		// dxy>125 = far (red)
		int dXY = (int) (Math.sqrt(Math.pow((sX - fX), 2)
				+ Math.pow((sY - fY), 2)));
		Log.d(TAG, "dXY: " + dXY);

		if (dXY > 200)
			return 2;
		else if ((dXY <= 200) && (dXY > 100))
			return 1;
		else
			return 0;

	}

	public void clearCanvas(View v) {
		Log.d(TAG, "Canvas was cleared!");
		mCanvas.drawColor(0xff000000);
		mImageView.setImageBitmap(mBitmap);
	}

	public void chooseBitmap(View v) {
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.setType("image/*");
		Intent iChooser = Intent.createChooser(i, "Select");
		startActivityForResult(iChooser, REQUEST_CODE);
	}

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
				
				// New stream with data URI
				InputStream stream = getContentResolver().openInputStream(uri);
				
				// Get options 
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;			
				Log.d(TAG, "Options created");
				
				// Get Bitmap from stream with edited options
				BitmapFactory.decodeStream(stream, null, options);
				stream.close();

				
				// get dimensioms
				int displayW = getResources().getDisplayMetrics().widthPixels;
				int displayH = getResources().getDisplayMetrics().heightPixels;
				Log.d(TAG, "Display size:" + displayH + " x " + displayW);
				int w = options.outWidth;
				int h = options.outHeight;	
				Log.d(TAG, "Bitmap raw size:" + w + " x " + h);
				
				//Sampling
				int sample = 1;
				while (w > displayW * sample || h > displayH * sample) {
					sample = sample * 2;
				}
				Log.d(TAG, "Sampling at " + sample);
				
				//
				stream = getContentResolver().openInputStream(uri);				
				options.inJustDecodeBounds = false;
				options.inSampleSize = sample;
				
				// Get Bitmap from stream with edited options
				Bitmap iBitmap = BitmapFactory.decodeStream(stream, null, options);
				mBitmap = iBitmap.copy(Bitmap.Config.ARGB_8888, true);
				
				
				//ImageView v = (ImageView) findViewById(R.id.image12);
				//Add new canvas to the Bitmap
				mCanvas = new Canvas(mBitmap);
				mCanvas.drawColor(0x00000000);
				mImageView.setImageBitmap(mBitmap);
				Log.d(TAG, "Bitmap decoded successfully");

			} catch (Exception e) {
				Log.e(TAG, "Decoding error:", e);
			}
;
		}
	}
}
