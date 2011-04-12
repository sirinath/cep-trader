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
import java.nio.ByteOrder;

class Event {
  private long sequenceNumber = 0;
  private int timestamp = 0;
  private short mills = 0;
  private byte[] exchange = new byte[2];
  private byte[] symbol = new byte[15];
  protected ByteBuffer buffer = null;

  protected Event(ByteBuffer event) {
    buffer = event;
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    buffer.get();
    timestamp = buffer.getInt();
    mills = buffer.getShort();
    buffer.get(exchange, 0, exchange.length);
    buffer.get(symbol, 0, symbol.length);
  }

  public int getTimestamp()
  {
    return timestamp;
  }

  public short getMills()
  {
    return mills;
  }

  public String getExchange()
  {
    return new String(exchange).trim();
  }

  public String getSymbol()
  {
    return new String(symbol).trim();
  }

  @Override
  public String toString()
  {
    return "" + timestamp + "." + mills + " " + getExchange() + "/" +getSymbol();
  }

  public long getSequenceNumber()
  {
    return sequenceNumber;
  }

  public void setSequenceNumber(long sequenceNumber)
  {
    this.sequenceNumber = sequenceNumber;
  }
}
