package com.ceptrader.generic.esper.pojoevents;

public class BuyLmt extends LimitOrder implements DataItem {
	private static final long	serialVersionUID	= 1L;
	
	@Deprecated
	public BuyLmt() {
		setSide(Side.BUY);
	}
	
	public BuyLmt(
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
	
	public BuyLmt(
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
