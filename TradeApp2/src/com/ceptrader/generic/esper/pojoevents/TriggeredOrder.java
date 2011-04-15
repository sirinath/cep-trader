package com.ceptrader.generic.esper.pojoevents;

public class TriggeredOrder extends TrailingOrder {
	
	protected double	 level;
	
	public TriggeredOrder() {
		super();
	}
	
	public void setLevel(final double level) {
		this.level = level;
	}
	
	public double getLevel() {
		return level;
	}
	
}