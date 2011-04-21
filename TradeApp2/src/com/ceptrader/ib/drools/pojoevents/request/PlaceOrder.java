package com.ceptrader.ib.drools.pojoevents.request;

import com.ib.client.Contract;
import com.ib.client.Order;

public class PlaceOrder {
	public int		id;
	public Contract	contract;
	public Order	order;
}