package com.ceptrader.ib.drools.pojoevents.request;

import com.ib.client.Contract;

public class ReqMktDepth {
	public int		tickerId;
	public Contract	contract;
	public int		numRows;
}