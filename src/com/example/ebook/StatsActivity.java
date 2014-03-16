package com.example.ebook;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

public class StatsActivity extends Activity {

	private static final String KEY_SHORT = "Short";
	private static final String KEY_MEDIUM = "Medium";
	private static final String KEY_LONG = "Long";

	private static final String TAG = "sw1ch";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		SharedPreferences mPrefs = getSharedPreferences("stats", MODE_PRIVATE);
		int countLong = mPrefs.getInt(KEY_LONG, 0);
		int countMedium = mPrefs.getInt(KEY_MEDIUM, 0);
		int countShort = mPrefs.getInt(KEY_SHORT, 0);

		Log.d(TAG, "Short: " + mPrefs.getInt(KEY_SHORT, -1));
		Log.d(TAG, "Medium: " + mPrefs.getInt(KEY_MEDIUM, -1));;
		Log.d(TAG, "Long: " + mPrefs.getInt(KEY_LONG, -1));
		

		GraphViewSeries mGraphSeries = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(0, countShort),
				new GraphViewData(1, countMedium),
				new GraphViewData(2, countLong),
				new GraphViewData(3, 0)});

		GraphView graphView = new BarGraphView(this, "Line Statistics");
		graphView.addSeries(mGraphSeries);
		graphView.setHorizontalLabels(new String[] { "Low", "Medium", "Short", "" });
		// graphView.setVerticalLabels(new String[]{"Low", "Medium", "Short"});
		
		//Style
		graphView.getGraphViewStyle().setTextSize(12);

		LinearLayout ll = (LinearLayout) findViewById(R.id.graph);
		ll.addView(graphView);
	}

}
