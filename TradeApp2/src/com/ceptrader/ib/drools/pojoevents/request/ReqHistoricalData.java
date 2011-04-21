package com.ceptrader.ib.drools.pojoevents.request;

import com.ib.client.Contract;

public class ReqHistoricalData {
	public int		tickerId;
	public Contract	contract;
	public String	endDateTime;
	public String	durationStr;
	public String	barSizeSetting;
	public String	whatToShow;
	public int		useRTH;
	public int		formatDate;
}