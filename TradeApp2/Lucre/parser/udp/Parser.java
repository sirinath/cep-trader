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

package parser.udp;

import parser.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import parser.events.PriceLevel;
import parser.events.Quote;
import parser.events.Trade;


class Parser implements PacketListener {
  private long seqNum = 0;
  private EventListener eventListener = null;
  private ArrayList<PriceLevel> priceLevels = null;

  public Parser(EventListener listener) {
    this.eventListener = listener;
    priceLevels = new ArrayList<PriceLevel>();
  }

  @Override
  public void onPacket(byte[] data) {
    ByteBuffer buf = ByteBuffer.wrap(data);
    buf.order(ByteOrder.LITTLE_ENDIAN);
    long newSeqNum = buf.getLong();
    if(newSeqNum > seqNum + 1 && seqNum != 0) {
      System.out.println("Gap detected: " + (newSeqNum - seqNum - 1) + " packets were lost!");
    }
    seqNum = newSeqNum;
    int currentPos = 8;
    while (currentPos < data.length - 1) {
      buf.position(currentPos);
      int len = buf.getShort();
      currentPos += 2;
      byte type = buf.get();
      buf.position(currentPos);
      currentPos += len;
      switch(type) {
        case 1:
           Quote quote = new Quote(buf);
           quote.setSequenceNumber(seqNum);
           eventListener.onQuote(quote);
          break;
        case 3:
           Trade trade = new Trade(buf);
           trade.setSequenceNumber(seqNum);
           eventListener.onTrade(trade);
          break;
        case 10:
           PriceLevel level = new PriceLevel(buf);
           level.setSequenceNumber(seqNum);
           priceLevels.add(level);
          break;
        default:
          System.out.println("unknown message type " +type);
      }
    }
    if(!priceLevels.isEmpty()) {
      eventListener.onPriceLevels(priceLevels);
      priceLevels.clear();
    }
  }
}
