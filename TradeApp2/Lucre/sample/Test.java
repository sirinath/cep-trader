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

package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import parser.EventListener;
import parser.events.PriceLevel;
import parser.udp.UDPParser;
import parser.events.Quote;
import parser.events.Trade;



public class Test {
  public static void main(String[] args) {
    HashMap<String, String> hosts = new HashMap<String, String>();
    HashMap<String, Integer> ports = new HashMap<String, Integer>();  

    hosts.put("EUR/USD","239.1.1.1");
    ports.put("EUR/USD",30001);

    hosts.put("EUR/USD","239.1.1.13");
    ports.put("EUR/USD",30013);


    System.out.println("Usage: java [-Dproto_version=[simple|extended]] -jar <jar-file> [<symbol1> <symbol2> ... <symbolN>].\n Available symbols:");
    for(String symbol : hosts.keySet()) {
      System.out.println(symbol);
    }
    EventListener listener = new EventListener() {
                                                   @Override
                                                   public synchronized void onQuote(Quote quote) {
                                                     System.out.println(quote);
                                                   }
                                                   @Override
                                                   public synchronized void onTrade(Trade trade) {
                                                     System.out.println(trade);
                                                   }
                                                   @Override
                                                   public synchronized void onPriceLevel(PriceLevel priceLevel) {
                                                     System.out.println("Price Level:" + priceLevel.toString());
                                                   }
                                                   @Override
                                                   public synchronized void onPriceLevels(List<PriceLevel> priceLevels) {
                                                     System.out.println("Price Levels Block:");
                                                     for(PriceLevel level : priceLevels) {
                                                       System.out.println(">" + level); 
                                                     }
                                                   }

    };
    try{
      ArrayList<UDPParser> parsers = new ArrayList<UDPParser>();
      for(int i = 0; i<args.length; i++) {
        String ip = hosts.get(args[i]);
        int port = ports.get(args[i]);
        if(ip != null) {
          System.out.println("Trying to join MC group " + ip + ":" + port);
          UDPParser parser = new UDPParser(ip, port, listener);
          parser.start();
          parsers.add(parser);
        }
        else {
          System.out.println("No symbol " + args[i] + " available");
        }
      }
      if(parsers.size() > 0) {
        System.in.read();
        for(UDPParser parser : parsers) {
          parser.stop();
        }
      }
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }
}
