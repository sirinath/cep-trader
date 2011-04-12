/*
Copyright (c) 2010, Lucre capital
All rights reserved.

 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution
 *     * Neither the name of the Lucre capital nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Lucre capital BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package com.sistyma.lgdp.trading.events;

import com.sistyma.lgdp.trading.fields.ExecType;
import com.sistyma.lgdp.trading.fields.OrderSide;
import com.sistyma.lgdp.trading.fields.OrderStatus;
import com.sistyma.lgdp.trading.fields.OrderType;
import java.util.EventObject;

/**
 *
 * @author kham
 */
public class ExecutionReportEvent extends EventObject {
  private ExecType execType = null;
  private OrderStatus orderStatus = null;
  private int clOrderID = 0;
  private String executionID = null;
  private OrderType orderType = null;
  private OrderSide orderSide = null;
  private String exchange = null;
  private String symbol = null;
  private double lastQty  = 0;
  private double lastPrice = 0;
  private String text = null;
  private double cumQty  = 0;
  private double leavesQty  = 0;

  public ExecutionReportEvent(Object source, ExecType execType, OrderStatus orderStatus, int clOrderID,
                              String executionID, OrderType orderType, OrderSide orderSide, String exchange,
                              String symbol, double lastQty, double lastPrice, String text, double cumQty,
                              double leavesQty) {
    super(source);
    this.execType = execType;
    this.orderStatus = orderStatus;
    this.clOrderID = clOrderID;
    this.executionID = executionID;
    this.orderType = orderType;
    this.orderSide = orderSide;
    this.exchange = exchange;
    this.symbol = symbol;
    this.lastQty = lastQty;
    this.lastPrice = lastPrice;
    this.text = text;
    this.cumQty = cumQty;
    this.leavesQty = leavesQty;
  }

  public ExecType getExecType()
  {
    return execType;
  }

  public OrderStatus getOrderStatus()
  {
    return orderStatus;
  }

  public int getClOrderID()
  {
    return clOrderID;
  }

  public String getExecutionID()
  {
    return executionID;
  }

  public OrderType getOrderType()
  {
    return orderType;
  }

  public OrderSide getOrderSide()
  {
    return orderSide;
  }

  public String getExchange()
  {
    return exchange;
  }

  public String getSymbol()
  {
    return symbol;
  }

  public double getLastQty()
  {
    return lastQty;
  }

  public double getLastPrice()
  {
    return lastPrice;
  }

  public String getText()
  {
    return text;
  }

  public double getCumQty()
  {
    return cumQty;
  }

  public double getLeavesQty()
  {
    return leavesQty;
  }

}
