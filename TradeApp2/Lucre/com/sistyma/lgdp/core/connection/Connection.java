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


package com.sistyma.lgdp.core.connection;
 
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Runnable {
  private Thread readThread = null;
  private Socket socket = null;
  private BufferedInputStream inputStream = null;
  private ConnectionListener listener = null;
  private InetSocketAddress address = null;

  public Connection(ConnectionListener listener) throws IOException {
    this.listener = listener;
    socket = new Socket();
  }

  @Override
  public void run() {
    try {
      while(!isClosed()) {
        byte[] lenBuf = new byte[2];
        try {
          int readLen = 0;
          readLen = inputStream.read(lenBuf, 0, lenBuf.length);
          if(readLen < 0) {
            throw new IOException("Lost connection");
          }
          else if(readLen!= lenBuf.length) {
            throw new IOException("Unable to read buffer length");
          }
        }
        catch(IOException ioe) {
          listener.onConnectionError(ioe.getMessage());
          while (!restore()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Trying to restore connection");
            readThread.sleep(500);
          }
          continue;
        }
        ByteBuffer lenBuffer = ByteBuffer.wrap(lenBuf);
        lenBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int len = (new Short(lenBuffer.getShort())).intValue();
        byte[] resp = new byte[len];
        try {
          int readLen = 0;
          readLen = inputStream.read(resp, 0, len);
          if(readLen < 0) {
            throw new IOException("Lost connection");
          }
          else if(readLen != len) {
            throw new IOException("Unable to read buffer length");
          }
        }
        catch(IOException ioe) {
          listener.onConnectionError(ioe.getMessage());
          while (!restore()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Trying to restore connection");
            readThread.sleep(500);
          }
          continue;
        }
        listener.onMessage(resp);
      }
    }
    catch (InterruptedException ie) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Connection: Read thread stoped");
    }
  }

  public synchronized boolean sendMessage(byte[] message) {
    boolean result = false;
    try {
      socket.getOutputStream().write(message);
      socket.getOutputStream().flush();
      result = true;
    }
    catch(IOException ioe) {
      listener.onConnectionError(ioe.getMessage());
    }
    catch(NullPointerException npe) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Connection: unable to write message");
    }
    return result;
  }

  public synchronized boolean connect(String host, int port) throws IOException {
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Connect");
    boolean result = false;
    close();
    try {
      address = new InetSocketAddress(host, port);
      socket = new Socket();
      socket.connect(address);
      socket.setTcpNoDelay(true);
      socket.setKeepAlive(true);
      inputStream = new BufferedInputStream(socket.getInputStream());
      readThread = new Thread(this);
      readThread.start();
      listener.onConnectionEstablished();
      result = true;
    }
    catch(IOException ioe) {
      listener.onConnectionError(ioe.getMessage());
    }
    return result;
  }
 
  public synchronized boolean close() {
    boolean result = false;
    if(readThread != null) {
      readThread.interrupt();
    }
    try {
      socket.close();
      result = true;
    }
    catch(IOException ie) {
      listener.onConnectionError(ie.toString());
    }
    listener.onConnectionClosed();
    return result;
  }

  public boolean isConnected() {
    return socket.isConnected();
  }

  public boolean isClosed() {
    return socket.isClosed();
  }

  private synchronized boolean restore() {
    boolean result = false;
    try {
      socket.close();
    }
    catch(IOException ie) {
      listener.onConnectionError(ie.toString());
    }
    try {
      socket = new Socket();
      socket.connect(address);
      socket.setTcpNoDelay(true);
      socket.setKeepAlive(true);
      inputStream = new BufferedInputStream(socket.getInputStream());
      listener.onConnectionEstablished();
      result = true;
    }
    catch(IOException ioe) {
      listener.onConnectionError(ioe.getMessage());
    }   
    return result;
  }
}