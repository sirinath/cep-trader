package com.ceptrader.generic.esper.pojoevents;

public class LimitOrder extends TrailingOrder {
	
	protected double	 level;
	
	public LimitOrder() {
		super();
	}
	
	public void setLevel(final double level) {
		this.level = level;
	}
	
	public double getLevel() {
		return level;
	}
	
}