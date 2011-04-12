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

import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.io.IOException;

class UDPReader extends Thread {
  private final int MAX_PACKET_SIZE = 64*1024;
  private InetAddress address = null;
  private MulticastSocket socket = null;
  private PacketListener packetListener = null;

  public UDPReader(String host, int port, PacketListener packetListener) throws IOException {
    address = InetAddress.getByName(host);
    socket = new MulticastSocket(port);
    socket.setReceiveBufferSize(MAX_PACKET_SIZE);
    this.packetListener = packetListener;
  }

  @Override
  public void start() {
    try {
      super.start();
      socket.joinGroup(address);
    }
    catch(IOException ioe) {
      System.err.printf("UDPReader: can't join multicast group(host is not a multicast address) " + ioe);
    }
    catch(SecurityException se) {
      System.err.printf("UDPReader: can't join multicast group(not allowed) " + se);
    }
    catch(IllegalThreadStateException itse) {
      System.err.printf("UDPReader: Thread already started " + itse);
    }
  }

  public void finish() {
    try {
      socket.leaveGroup(address);
      join();
    }
    catch(IOException ioe) {
      System.err.printf("UDPReader: can't leave multicast group(host is not a multicast address) " + ioe);
    }
    catch(SecurityException se) {
      System.err.printf("UDPReader: can't leave multicast group(not allowed) " + se);
    }
    catch(InterruptedException ie) {
      System.err.printf("UDPReader: thread was interrupted " + ie);
    }
  }

  @Override
  public void run() {
    while(isAlive()) {
      try {
        byte[] response = new byte[MAX_PACKET_SIZE];
        DatagramPacket packet = new DatagramPacket(response, 0, response.length);
        socket.receive(packet);
        byte[] packetData = new byte[packet.getLength()];
        System.arraycopy(packet.getData(), 0, packetData, 0, packet.getLength());
        packetListener.onPacket(packetData);
      }
      catch(Exception e) {
        System.err.printf("UDPReader: problem in message receiving " + e);
        e.printStackTrace();
      }
    }
  }
}
