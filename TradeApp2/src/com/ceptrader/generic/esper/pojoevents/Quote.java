package com.ceptrader.generic.esper.pojoevents;


public class Quote extends BaseQuote {
	protected int	   size;
	protected double	price;
	protected Operator	operator	= Operator.NA;
	
	public static enum Operator {
		INSERT, UPDATE, DELETE, NA
	}
	
	public Quote() {
		super();
	}
	
	public void setSize(final int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setPrice(final double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
}