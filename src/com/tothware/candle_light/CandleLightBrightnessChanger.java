package com.tothware.candle_light;

import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;

public class CandleLightBrightnessChanger implements Runnable {
	
	private final WindowManager.LayoutParams lp;
	private final View view;
	
	public CandleLightBrightnessChanger(WindowManager.LayoutParams lp, View view){
		this.lp = lp;
		this.view = view;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			Thread.sleep(5000);
			lp.screenBrightness = 0.5f;
			view.setBackgroundColor(Color.RED);
			boolean check = (1 == 1);
			if (check == true){
				// do nothing
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
	
		}
		

		
	}
	

}
