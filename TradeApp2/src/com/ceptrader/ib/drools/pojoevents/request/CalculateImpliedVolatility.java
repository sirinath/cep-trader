package com.ceptrader.ib.drools.pojoevents.request;

import com.ib.client.Contract;

public class CalculateImpliedVolatility {
	public int		reqId;
	public Contract	contract;
	public double	optionPrice;
	public double	underPrice;
}