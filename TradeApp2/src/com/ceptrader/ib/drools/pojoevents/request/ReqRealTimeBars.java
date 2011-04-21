package com.ceptrader.ib.drools.pojoevents.request;

import com.ib.client.Contract;

public class ReqRealTimeBars {
	public int		tickerId;
	public Contract	contract;
	public int		barSize;
	public String	whatToShow;
	public boolean	useRTH;
}