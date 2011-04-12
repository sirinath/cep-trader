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


package com.sistyma.lgdp.trading.requests;

import com.sistyma.lgdp.trading.fields.OrderSide;
import com.sistyma.lgdp.trading.fields.OrderType;
import com.sistyma.lgdp.trading.fields.TimeInForce;

/**
 *
 * @author kham
 */
public class OrderParams {
  private static int orderID = 0;
  private int clOrderID = 0;
  private OrderType orderType = null;
  private OrderSide orderSide = null;
  private String exchange = null;
  private String symbol = null;
  private double price = -1.0;
  private int size = -1;
  private TimeInForce timeInForce = null;
  private int minSize;

  public OrderParams() {
    clOrderID = orderID++;
  }

  public int getClOrderID()
  {
    return clOrderID;
  }

  public void setClOrderID(int clOrderID)
  {
    this.clOrderID = clOrderID;
  }

  public OrderType getOrderType() throws NullPointerException
  {
    if(orderType == null) {
      throw  new NullPointerException("Order Type not specified");
    }
    return orderType;
  }

  public void setOrderType(OrderType orderType)
  {
    this.orderType = orderType;
  }

  public OrderSide getOrderSide() throws NullPointerException
  {
    if(orderSide == null) {
      throw  new NullPointerException("Order Side not specified");
    }
    return orderSide;
  }

  public void setOrderSide(OrderSide orderSide)
  {
    this.orderSide = orderSide;
  }

  public String getExchange() throws NullPointerException
  {
    if(exchange == null) {
      throw new NullPointerException("Exchange not specified");
    }
    return exchange;
  }

  public void setExchange(String exchange)
  {
    this.exchange = exchange.trim() + '\0';
  }

  public String getSymbol() throws NullPointerException
  {
    if(symbol == null) {
      throw new NullPointerException("Symbol not specified");
    }
    return symbol;
  }

  public void setSymbol(String symbol)
  {
    this.symbol = symbol.trim() + '\0';
  }

  public double getPrice() throws NullPointerException
  {
    if(price < 0) {
      throw new NullPointerException("Price not specified");
    }
    return price;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public int getSize() throws NullPointerException
  {
    if(size < 0) {
      throw new NullPointerException("Size not specified");
    }
    return size;
  }

  public void setSize(int size)
  {
    this.size = size;
  }

  public TimeInForce getTimeInForce() throws NullPointerException
  {
    if(timeInForce == null) {
      throw new NullPointerException("TIF not specified");
    }
    return timeInForce;
  }

  public void setTimeInForce(TimeInForce timeInForce)
  {
    this.timeInForce = timeInForce;
  }

    /**
     * @return the minSize
     */
    public int getMinFillSize() {
        return minSize;
    }

    /**
     * @param minSize the minSize to set
     */
    public void setMinFillSize(int minSize) {
        this.minSize = minSize;
    }
}
