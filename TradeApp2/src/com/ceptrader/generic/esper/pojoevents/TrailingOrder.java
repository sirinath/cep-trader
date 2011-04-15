package com.ceptrader.generic.esper.pojoevents;

public class TrailingOrder extends Order {
	
	private final double trail = 0;
	private boolean trailPct = false;

	public TrailingOrder() {
		super();
	}

	public double getTrail() {
    	return trail;
    }

	public void setTrailPct(final boolean trailPct) {
    	this.trailPct = trailPct;
    }

	public boolean isTrailPct() {
    	return trailPct;
    }
	
}