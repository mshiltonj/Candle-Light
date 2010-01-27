package com.tothware.candle_light;

import android.graphics.Color;

public enum CandleColor {
	CANDLE ("CANDLE", 0xff4f4f4f),
	BLUE   ("BLUE", Color.BLUE),
	CYAN   ("CYAN", Color.CYAN),
	DKGRAY ("DKGRAY", Color.DKGRAY),
	LTGRAY ("LTGRAY", Color.LTGRAY),
	GRAY   ("GRAY", Color.GRAY),
	GREEN  ("GREEN", Color.GREEN),
	MAGENTA ("MAGENTA", Color.MAGENTA),
	RED		("RED", Color.RED),
	WHITE	("WHITE", Color.WHITE),
	YELLOW	("YELLOW", Color.YELLOW)
	;
	
	public final int color;
	public final String name;
	
	CandleColor(String name, int color){
		this.name = name;
		this.color = color;
	}
}
