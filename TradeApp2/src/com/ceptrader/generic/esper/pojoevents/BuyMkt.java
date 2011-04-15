package com.ceptrader.generic.esper.pojoevents;

public class BuyMkt extends Order implements DataItem {
	private static final long	serialVersionUID	= 1L;
	
	public BuyMkt(
	        final int ref,
	        final String ticker,
	        final int size,
	        final String goodUntil) {
		setSide(Side.BUY);
		this.ref = ref;
		this.ticker = ticker;
		this.size = size;
		this.goodUntil = goodUntil;
	}
	
	public BuyMkt(
	        final int ref,
	        final String ticker,
	        final long timeStamp,
	        final int size,
	        final String goodUntil) {
		setSide(Side.BUY);
		this.ref = ref;
		this.ticker = ticker;
		this.timeStamp = timeStamp;
		this.size = size;
		this.goodUntil = goodUntil;
	}
}
