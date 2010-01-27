package com.tothware.candle_light;

public enum FlickerPattern {
	START	("START", 1, 55, 100, 2, 2, 1),
	STEADY 	("Steady", 65, 65, 75, 1, 1, 5),
	FLARE 	("Flare", 65, 55, 100, 10, 10, 1);
	
	
	private final int start, min, max, upRate, downRate, count;
	private final String name;

	private FlickerPattern(String name, int start, int min, int max, int upRate, int downRate, int count){
		this.name 		= name;
		this.start 		= start;
		this.min 		= min;
		this.max 		= max;
		this.upRate 	= upRate;
		this.downRate 	= downRate;
		this.count 		= count;
	}

	
	public String getName(){
		return name;
	}
	
	public int getStart(){
		return start;
	}	

	public int getMin() {
		return min;
	}


	public int getMax() {
		return max;
	}


	public int getUpRate() {
		return upRate;
	}


	public int getDownRate() {
		return downRate;
	}


	public int getCount() {
		return count;
	}
}
