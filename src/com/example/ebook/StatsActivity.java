package com.example.ebook;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

public class StatsActivity extends SherlockActivity {

	private static final String KEY_SHORT = "Short";
	private static final String KEY_MEDIUM = "Medium";
	private static final String KEY_LONG = "Long";
	int countLong;
	int countMedium;
	int countShort;
	private static final String TAG = "sw1ch";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		SharedPreferences mPrefs = getSharedPreferences("stats", MODE_PRIVATE);
		
		//getting data from preferences 
		//and choosing the maximum value
		countLong = mPrefs.getInt(KEY_LONG, 0);
		countMedium = mPrefs.getInt(KEY_MEDIUM, 0);
		countShort = mPrefs.getInt(KEY_SHORT, 0);
		Log.d(TAG, "Short: " + countShort + "\nMedium: " 
		+ countMedium + "\nLong: " + countLong);
		
		int max = countLong;
		if (max <= countMedium) max = countMedium;
		if (max <= countShort) max = countShort;
		Log.d(TAG, "Maximal value: "+max);

		// Data arrangement
		GraphViewSeries mGraphSeries = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(0, countShort),
				new GraphViewData(1, countMedium),
				new GraphViewData(2, countLong)
				});

		GraphView graphView = new BarGraphView(this, "Line Statistics");
		graphView.addSeries(mGraphSeries);
		graphView.setHorizontalLabels(new String[] { "Short", "Medium", "Long"});
		graphView.getGraphViewStyle().setNumHorizontalLabels(3);
		graphView.setManualYAxisBounds(max, 0);

		// Style
		graphView.getGraphViewStyle().setTextSize(12);
		graphView.setBackgroundColor(Color.BLACK);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.WHITE);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.WHITE);
		((BarGraphView) graphView).setDrawValuesOnTop(true);

		LinearLayout ll = (LinearLayout) findViewById(R.id.graph);
		ll.addView(graphView);
	}

}
