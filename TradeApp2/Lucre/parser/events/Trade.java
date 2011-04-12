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



public class Trade extends Event {
  private double price = 0;
  private int size = 0;
  private long volume = 0;
  private int seqNum = 0;
  private char indicator = 0;
  private char tickIndicator = 0;
  private byte flags1 = 0;
  private byte flags2 = 0;

  public Trade(ByteBuffer event) {
    super(event);
    price = buffer.getDouble();
    size = buffer.getInt();
    volume = buffer.getLong();
    seqNum = buffer.getInt();
    indicator = (char)buffer.get();
    tickIndicator = (char)buffer.get();
    flags1 = buffer.get();
    flags2 = buffer.get();
  }

  public double getPrice()
  {
    return price;
  }

  public int getSize()
  {
    return size;
  }

  public long getVolume()
  {
    return volume;
  }

  public int getSeqNum()
  {
    return seqNum;
  }

  public char getIndicator()
  {
    return indicator;
  }

  public char getTickIndicator()
  {
    return tickIndicator;
  }

  public byte getFlags1()
  {
    return flags1;
  }

  public byte getFlags2()
  {
    return flags2;
  }

  @Override
  public String toString() {
    return "Trade: " + super.toString() + " price=" + price +
            " size="+size + " volume=" + volume + " seqNum=" + seqNum +
            " indicator=" + indicator + " tickIndicator=" + tickIndicator +
            " flags1=" +flags1 + " flags2=" +flags2;
  }
}
