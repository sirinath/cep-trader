package com.ceptrader.generic.esper.pojoevents;


public class SellMkt extends Order implements DataItem {
	private static final long	serialVersionUID	= 1L;
	
	public SellMkt(
	        final int ref,
	        final String ticker,
	        final int size,
	        final String goodUntil) {
		setSide(Side.SELL);
		this.ref = ref;
		this.ticker = ticker;
		this.size = size;
		this.goodUntil = goodUntil;
	}
	
	public SellMkt(
	        final int ref,
	        final String ticker,
	        final long timeStamp,
	        final int size,
	        final String goodUntil) {
		setSide(Side.SELL);
		this.ref = ref;
		this.ticker = ticker;
		this.timeStamp = timeStamp;
		this.size = size;
		this.goodUntil = goodUntil;
	}
}
