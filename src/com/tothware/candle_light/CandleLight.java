package com.tothware.candle_light;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnTouchListener;

public class CandleLight extends Activity implements Callback, OnClickListener,
		OnTouchListener, OnCreateContextMenuListener {
	/** Called when the activity is first created. */

	private static final int MENU_ABOUT = Menu.FIRST;
	private static final int MENU_QUIT = Menu.FIRST + 1;
	private static final int MENU_COLOR = Menu.FIRST + 2;

	private static final int ABOUT_ACTION = 1;
	private static final int COLOR_ACTION = 2;

	private static final int UP = 1;
	private static final int DOWN = 2;

	private static final String CANDLE_PREFS = "CandleLightPrefs";

	private Flicker mFlicker;

	private int mWindowHeight;
	private int mWindowWidth;
	private int mDirection = UP;

	private SurfaceView mSurface;
	private SurfaceHolder mHolder;

	private FlickerPattern currentPattern = FlickerPattern.START;
	private int patternCount = FlickerPattern.START.getCount();
	private int mPercent = FlickerPattern.START.getStart();

	private boolean steadyPattern = false;

	private Paint mBackgroundPaint;
	private Paint mForegroundPaint;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.screenBrightness = 100 / 100.0f;
		getWindow().setAttributes(lp);

		mSurface = (SurfaceView) findViewById(R.id.body);
		mHolder = mSurface.getHolder();
		mHolder.addCallback(this);

		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(Color.BLACK);

		SharedPreferences settings = getSharedPreferences(CANDLE_PREFS, 0);

		mForegroundPaint = new Paint();
		mForegroundPaint.setColor(settings.getInt("color", Color.YELLOW));

		mWindowHeight = mSurface.getMeasuredHeight();
		mWindowWidth = mSurface.getMeasuredWidth();

		mSurface.setOnTouchListener(this);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		mFlicker.setRunning(false);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, MENU_COLOR, 0, R.string.menu_color);
		menu.add(0, MENU_ABOUT, 0, R.string.menu_about);
		menu.add(0, MENU_QUIT, 0, R.string.menu_quit);

		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent();

		switch (item.getItemId()) {
		case MENU_COLOR:
			i.setClass(this,
					com.tothware.candle_light.CandleColorActivity.class);
			startActivityForResult(i, COLOR_ACTION);
			break;
		case MENU_QUIT:
			finish();
			break;
		case MENU_ABOUT:
			i.setClass(this, com.tothware.candle_light.AboutActivity.class);
			startActivityForResult(i, ABOUT_ACTION);
			break;
		}
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case ABOUT_ACTION:
			// do nothing
			break;
		case COLOR_ACTION:
			refreshForegroundPaint();
			break;
		default:
			// do nothing
		}

	}

	private void refreshForegroundPaint() {
		SharedPreferences settings = getSharedPreferences(CANDLE_PREFS, 0);
		mForegroundPaint.setColor(settings.getInt("color", Color.YELLOW));
	}

	public void draw() {
		Canvas c = null;
		
		mWindowHeight = mSurface.getMeasuredHeight();
		mWindowWidth = mSurface.getMeasuredWidth();


		try {

			c = mHolder.lockCanvas();

			if (c != null) {
				doDraw(c);
			} else {

			}
		} finally {
			if (c != null) {
				mHolder.unlockCanvasAndPost(c);
			}
		}
	}

	private void doDraw(Canvas c) {
		c.drawRect(0, 0, mWindowWidth, mWindowHeight, mBackgroundPaint);

		if (patternCount <= 0) {
			// current pattern is complete. get a new one and restart count;
			// for the first release, let's just alternate between steady and
			// flare.
			steadyPattern = steadyPattern == false ? true : false;

			currentPattern = steadyPattern ? FlickerPattern.STEADY
					: FlickerPattern.FLARE;
			mPercent = currentPattern.getStart();
			patternCount = currentPattern.getCount();
			mDirection = UP;
		}

		if (mDirection == UP) {
			mPercent += currentPattern.getUpRate();
		} else // must be DOWN
		{
			mPercent -= currentPattern.getDownRate();
		}

		// Rect rect = new Rect(0, 0, mWindowWidth, mBaseHeight + mSize);
		int height = candleHeight(mPercent);
		
		int offset = (mWindowHeight - height) / 2;
		
		int bottom = offset + height;
		
		Rect rect = new Rect(0, offset, mWindowWidth, bottom);

		c.drawRect(rect, mForegroundPaint);
	

		if (mDirection == UP && mPercent >= currentPattern.getMax()) {
			// let's go back down
			mDirection = DOWN;
		}

		if (mDirection == DOWN && mPercent <= currentPattern.getMin()) {
			patternCount--;
			mDirection = UP;
		}
	}

	private int candleHeight(int i) {
		// return (new Double(new
		// Integer(mWindowWidth)).doubleValue())).intValue() ;
		return (int) ((double) (mWindowHeight / 100) * (double) i);

	}

	/*
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	*/
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mFlicker = new Flicker();
		mFlicker.start();
	}

	protected void onPause() {
		super.onPause();
		mFlicker.safeStop();

	}

	protected void onStop() {
		super.onStop();
		mFlicker.safeStop();

	}

	public int getPercent() {
		return mPercent;
	}

	public void setPercent(int mPercent) {
		this.mPercent = mPercent;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int eventAction = event.getAction();

		switch (eventAction) {
		case MotionEvent.ACTION_DOWN:
			if (mFlicker.isAlive()) {

			} else {
				this.mFlicker = new Flicker();
				this.mFlicker.start();
			}
			return true;

		}

		return false;
	}

	private class Flicker extends Thread {
		private volatile boolean running = true;

		public void setRunning(boolean running) {
			this.running = running;
		}

		public void run() {

			while (running) {
				try {

					if (mPercent <= 0) {
						running = false;
					}

					TimeUnit.MILLISECONDS.sleep(20);
					draw();

				} catch (InterruptedException ie) {
					running = false;
				}
			}
		}

		public void safeStop() {
			running = false;
			interrupt();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}