package com.ceptrader.generic.esper.pojoevents;

public class BaseQuote {
	
	protected int ref;
	protected String ticker;
	protected long timeStamp;
	protected long duration;

	public BaseQuote() {
		super();
	}

	public void setTimeStamp(final long timeStamp) {
    	this.timeStamp = timeStamp;
    }

	public long getTimeStamp() {
    	return timeStamp;
    }

	public void setDuration(final long duration) {
    	this.duration = duration;
    }

	public long getDuration() {
    	return duration;
    }

	public void setTicker(final String ticker) {
    	this.ticker = ticker;
    }

	public String getTicker() {
    	return ticker;
    }

	public void setRef(final int ref) {
    	this.ref = ref;
    }

	public int getRef() {
    	return ref;
    }
	
}