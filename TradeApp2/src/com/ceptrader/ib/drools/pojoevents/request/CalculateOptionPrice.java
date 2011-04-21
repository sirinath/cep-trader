package com.ceptrader.ib.drools.pojoevents.request;

import com.ib.client.Contract;

public class CalculateOptionPrice {
	public int		reqId;
	public Contract	contract;
	public double	volatility;
	public double	underPrice;
}