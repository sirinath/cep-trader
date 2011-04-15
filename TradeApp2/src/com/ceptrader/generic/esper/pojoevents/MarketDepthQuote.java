package com.ceptrader.generic.esper.pojoevents;

public class MarketDepthQuote extends Quote {
	
	protected boolean	isBest	    = true;
	private boolean	  isMarketDepth	= false;
	protected int	  level	        = -1;
	private final int	row	        = -1;
	private String	  marketMaker	= null;
	
	public MarketDepthQuote() {
		super();
	}
	
	public void setBest(final boolean isBest) {
		this.isBest = isBest;
	}
	
	public boolean isBest() {
		return isBest;
	}
	
	public void setLevel(final int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setMarketMaker(final String marketMaker) {
		this.marketMaker = marketMaker;
	}
	
	public String getMarketMaker() {
		return marketMaker;
	}
	
	public void setOperator(final Operator operator) {
		this.operator = operator;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public void setMarketDepth(final boolean isMarketDepth) {
		this.isMarketDepth = isMarketDepth;
	}
	
	public boolean isMarketDepth() {
		return isMarketDepth;
	}
	
	public int getRow() {
		return row;
	}
	
}