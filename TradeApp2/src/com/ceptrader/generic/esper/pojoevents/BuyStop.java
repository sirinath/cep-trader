package com.ceptrader.generic.esper.pojoevents;


public class BuyStop extends TriggeredOrder implements DataItem {
	private static final long	serialVersionUID	= 1L;
	
	@Deprecated
	public BuyStop() {
	}
	
	public BuyStop(
	        final int ref,
	        final String ticker,
	        final double level,
	        final int size,
	        final String goodUntil) {
		setSide(Side.BUY);
		this.ref = ref;
		this.ticker = ticker;
		this.level = level;
		this.size = size;
		this.goodUntil = goodUntil;
	}
	
	public BuyStop(
	        final int ref,
	        final String ticker,
	        final long timeStamp,
	        final double level,
	        final int size,
	        final String goodUntil) {
		setSide(Side.BUY);
		this.ref = ref;
		this.ticker = ticker;
		this.timeStamp = timeStamp;
		this.level = level;
		this.size = size;
		this.goodUntil = goodUntil;
	}
}
