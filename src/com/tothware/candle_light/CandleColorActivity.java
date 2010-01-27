package com.tothware.candle_light;

import java.util.HashMap;
import java.lang.reflect.Field;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.graphics.PorterDuff;



public class CandleColorActivity extends Activity implements OnClickListener {
	
	private static HashMap<Integer, Integer> buttonsToColors = new HashMap<Integer, Integer>();
	
	private static final String CANDLE_PREFS = "CandleLightPrefs";
	private static final int COLOR_ACTION = 2;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        //System.err.println("ON CREATE");
        setContentView(R.layout.colors);
        
        Class idClass = R.id.class;
                        
        
        for (CandleColor candleColor : CandleColor.values()){
        	String colorId = "color_" + candleColor.name().toLowerCase();
        	
        	Field idField; 
        	int idValue = 0;
        	try {
				idField = idClass.getField(colorId);
			   idValue = (Integer) idField.get(null);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	Button button = (Button) findViewById(idValue);
        	Drawable dr = button.getBackground();
        	
        	buttonsToColors.put(button.getId(), candleColor.color);
        	
        	dr.setColorFilter(candleColor.color,PorterDuff.Mode.MULTIPLY);
        	button.setOnClickListener(this);

        	
        }
	}

	@Override
	public void onClick(View v) {
		int color = (Integer) buttonsToColors.get(v.getId());
		
		Intent intent = new Intent();		
		SharedPreferences settings = getSharedPreferences(CANDLE_PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putInt("color", color);
		editor.commit();
		
		setResult(COLOR_ACTION, intent);
		finish();
	}




}
