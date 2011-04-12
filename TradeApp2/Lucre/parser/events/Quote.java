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

package parser.events;

import java.nio.ByteBuffer;


public class Quote extends Event {
  private double bidPrice = 0;
  private int bidSize = 0;
  private double askPrice = 0;
  private int askSize = 0;
  private byte[] askExchange = new byte[2];
  private byte[] bidExchange = new byte[2];
  private char indicator = 0;
  private char tickIndicator = 0;

  public Quote(ByteBuffer event) {
    super(event);
    bidSize = buffer.getInt();
    askSize = buffer.getInt();
    bidPrice = buffer.getDouble();
    askPrice = buffer.getDouble();
    buffer.get(askExchange);
    indicator = (char)buffer.get();
    tickIndicator = (char)buffer.get();
    buffer.get(bidExchange);
  }

  public double getBidPrice()
  {
    return bidPrice;
  }

  public int getBidSize()
  {
    return bidSize;
  }

  public double getAskPrice()
  {
    return askPrice;
  }

  public int getAskSize()
  {
    return askSize;
  }

  public String getAskExchange()
  {
    return new String(askExchange).trim();
  }

  public String getBidExchange()
  {
    return new String(bidExchange).trim();
  }

  public char getIndicator()
  {
    return indicator;
  }

  public char getTickIndicator()
  {
    return tickIndicator;
  }

  @Override
  public String toString() {
    return "Quote: " + super.toString() + " bidPrice=" + bidPrice +
            " bidSize=" + bidSize + " bidExchange=" + getBidExchange() +
            " askPrice=" + askPrice + " askSize=" + askSize + " askExchange=" + getAskExchange() +
            " indicator=" + indicator + " tickIndicator=" + tickIndicator;
  }
}
