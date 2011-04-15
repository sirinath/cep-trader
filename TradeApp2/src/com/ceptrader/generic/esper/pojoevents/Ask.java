package com.ceptrader.generic.esper.pojoevents;


public class Ask extends MarketDepthQuote implements DataItem {
	private static final long	serialVersionUID	= 1L;
	@Deprecated
	public Ask() {
	}
	
	public Ask(
	        final int ref,
	        final String ticker,
	        final int size,
	        final double price) {
		this.ref = ref;
		this.ticker = ticker;
		this.size = size;
		this.price = price;
	}
	
	public Ask(
	        final int ref,
	        final String ticker,
	        final long timeStamp,
	        final int size,
	        final double price) {
		this.ref = ref;
		this.ticker = ticker;
		this.timeStamp = timeStamp;
		this.size = size;
		this.price = price;
	}
	
	public Ask(
	        final int ref,
	        final String ticker,
	        final long timeStamp,
	        final long duration,
	        final int size,
	        final double price) {
		this.ref = ref;
		this.ticker = ticker;
		this.timeStamp = timeStamp;
		this.duration = duration;
		this.size = size;
		this.price = price;
	}
	
	public Ask(
	        final int ref,
	        final String ticker,
	        final long timeStamp,
	        final long duration,
	        final int size,
	        final double price,
	        final boolean isBest) {
		this.ref = ref;
		this.ticker = ticker;
		this.timeStamp = timeStamp;
		this.duration = duration;
		this.size = size;
		this.price = price;
		this.isBest = isBest;
	}
	
	public Ask(
	        final int ref,
	        final String ticker,
	        final long timeStamp,
	        final long duration,
	        final int size,
	        final double price,
	        final boolean isBest,
	        final int level) {
		this.ref = ref;
		this.ticker = ticker;
		this.timeStamp = timeStamp;
		this.duration = duration;
		this.size = size;
		this.price = price;
		this.isBest = isBest;
		this.level = level;
	}
}
