package com.ceptrader.ib.drools.pojoevents.request;

import com.ib.client.Contract;

public class ReqMktData {
	public int		tickerId;
	public Contract	contract;
	public String	genericTickList;
	public boolean	snapshot;
}