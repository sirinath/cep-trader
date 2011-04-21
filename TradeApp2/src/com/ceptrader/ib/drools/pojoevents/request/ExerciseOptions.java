package com.ceptrader.ib.drools.pojoevents.request;

import com.ib.client.Contract;

public class ExerciseOptions {
	public int		tickerId;
	public Contract	contract;
	public int		exerciseAction;
	public int		exerciseQuantity;
	public String	account;
	public int		override;
}