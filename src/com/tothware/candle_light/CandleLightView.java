package com.tothware.candle_light;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CandleLightView extends SurfaceView {

	public CandleLightView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CandleLightView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		
		super.onDraw(canvas);
		System.err.println("ON DRAW");
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		
		
		Rect rect = new Rect(50, 50, 200, 100);
		canvas.drawRect(rect, paint);
		
	}

	public CandleLightView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

}
