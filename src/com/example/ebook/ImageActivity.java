package com.example.ebook;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		Resources res = getResources();

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
		Log.d(TAG, "Dimmensions: " + maxX + " " + maxY);
	}

	public void drawLine(View v) {
		// First point coords
		Log.d(TAG, "Line will be drawn");
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
	
	public void clearCanvas(View v){
		Log.d(TAG, "Canvas was cleared!");
		mCanvas.drawColor(0xff000000);
		mImageView.setImageBitmap(mBitmap);
	}
}
