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


package com.sistyma.lgdp.core;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kham
 */
class History {
  private String path = null;
  private String sessionID = null;
  private int recvSeqNum = 0;
  private int sentSeqNum = 0;
  private TreeMap<Integer, byte[]> requests;

  public History(String path, String sessionID) throws ClassNotFoundException, IOException{
    this.path = path;
    this.sessionID = sessionID;
    checkPath(path);
    try {
      ObjectInputStream stream = new ObjectInputStream(new FileInputStream(getFileName(path)));
      if(stream.available() >= 8) { 
        recvSeqNum = stream.readInt();
        sentSeqNum = stream.readInt();
        if(stream.available() > 0 ) {
          requests = (TreeMap<Integer, byte[]>)stream.readObject();
        }
      }
      stream.close();
    }
    catch(EOFException eofe) {
      Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Log file is empty");
    }
    if(requests == null) {
      requests = new TreeMap<Integer, byte[]>();
    }
  }

  public synchronized void saveHistory() throws IOException {
    synchronized(requests) {
      ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(getFileName(path)));
      stream.writeInt(recvSeqNum);
      stream.writeInt(sentSeqNum);
      stream.writeObject(requests);
      stream.flush();
      stream.close();
    }
  }

  public void putRequest(int seqNum, byte[] request) {
    synchronized(requests) {
      requests.put(seqNum, request);
    }
  }

  public Map<Integer, byte[]> getRequests() {
    synchronized(requests) {
      return requests;
    }
  }

  public int getRecvSeqNum()
  {
    return recvSeqNum;
  }

  public synchronized  void setRecvSeqNum(int recvSeqNum)
  {
    this.recvSeqNum = recvSeqNum;
  }

  public int getSentSeqNum()
  {
    return sentSeqNum;
  }

  public synchronized void setSentSeqNum(int sentSeqNum)
  {
    this.sentSeqNum = sentSeqNum;
  }

  public void clear(int recvSeqNum) throws IOException {
    synchronized(requests) {
      requests.clear();
    }
    sentSeqNum = 0;
    this.recvSeqNum = recvSeqNum;
    saveHistory();
  }

  @Override
  protected void finalize() throws Throwable
  {
    saveHistory();
  }

  private String getFileName(String path) {
    if(System.getProperty("os.name").toLowerCase().indexOf( "win" ) >= 0) {
      if(!path.trim().endsWith("\\")) {
        path = path.trim() + "\\"; 
      }
    } else {
      if(!path.trim().endsWith("/")) {
        path = path.trim() + "/"; 
      }
    }
    return path + sessionID + ".history";
  }

  private void checkPath(String path) throws IOException {
    File dir = new File(path);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    File file = new File(getFileName(path));
    if(!file.exists()) {
      file.createNewFile();
    }   
  }
}
