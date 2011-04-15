package com.ceptrader.generic.esper.pojoevents;


public class SellLmt extends LimitOrder implements DataItem {
	private static final long	serialVersionUID	= 1L;
	
	@Deprecated
	public SellLmt() {
		setSide(Side.SELL);
	}
	
	public SellLmt(
	        final int ref,
	        final String ticker,
	        final double level,
	        final int size,
	        final String goodUntil) {
		setSide(Side.SELL);
		this.ref = ref;
		this.ticker = ticker;
		this.level = level;
		this.size = size;
		this.goodUntil = goodUntil;
	}
	
	public SellLmt(
	        final int ref,
	        final String ticker,
	        final long timeStamp,
	        final double level,
	        final int size,
	        final String goodUntil) {
		setSide(Side.SELL);
		this.ref = ref;
		this.ticker = ticker;
		this.timeStamp = timeStamp;
		this.level = level;
		this.size = size;
		this.goodUntil = goodUntil;
	}
}
