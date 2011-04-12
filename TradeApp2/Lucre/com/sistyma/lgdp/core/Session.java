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

import java.util.HashMap;
import com.sistyma.lgdp.core.connection.Connection;
import com.sistyma.lgdp.core.listeners.AuthorizationListener;
import com.sistyma.lgdp.core.listeners.ConnectionListener;
import com.sistyma.lgdp.core.listeners.ErrorListener;
import com.sistyma.lgdp.core.listeners.MessageListener;
import com.sistyma.lgdp.core.requests.Heartbeat;
import com.sistyma.lgdp.core.requests.Login;
import com.sistyma.lgdp.core.requests.Logout;
import com.sistyma.lgdp.core.requests.ResendRequest;
import com.sistyma.lgdp.core.requests.UserRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author kham
 */

public class Session extends SessionStateListener {
  private static HashMap<String, Session> sessions = new HashMap<String, Session>();
  private final SessionParams params;
  private Connection connection = null;
  private History history = null;
  private MessageHandler handler;
  private String sessionID = "";
  private boolean loggedIn = false;
  private int receivedSeqNum = 0;
  private int sentSeqNum = 0;
  private Thread heartbeatSender;

  public static Session initSession(SessionParams params) throws ClassNotFoundException, IOException{
    Session session = new Session(params);
    sessions.put(session.sessionID, session);
    return session;
  }

  public static Session lookupSession(String sessionID) {
    return sessions.get(sessionID);
  }

  public static void removeSession(String sessionID) {
    try {
      sessions.get(sessionID).stop();
    }
    catch(Exception e) {
      Logger.getLogger(Session.class.getName()).log(Level.SEVERE, "Unable to stop session " 
                       + sessionID);
    }
    sessions.remove(sessionID);
  }

  private Session(SessionParams params) throws ClassNotFoundException, IOException{
    this.params = params;
    sessionID = params.getLogin() + "-" + params.getHost() + "-" + params.getPort();
    if(params.isRerequestEnabled() || params.isResendEnabled()) {
      history = new History(params.getStorePath(), sessionID);
      sentSeqNum = history.getSentSeqNum();
      receivedSeqNum = history.getRecvSeqNum();
    }
    handler = new MessageHandler(this, this);
    connection = new Connection(handler);
  }

  public synchronized void start() throws Exception {
    while(!connection.connect(params.getHost(), params.getPort())) {
      Logger.getLogger(Session.this.getClass().getName()).log(Level.INFO, "Trying to connect to " 
                           + params.getHost()  + ":" + params.getPort());
      Thread.sleep(500);
    }
  }

  public synchronized void stop() throws Exception {
      if(loggedIn) {
        loggedIn = false;
        heartbeatSender.interrupt();
      }
      connection.sendMessage(new Logout().getBuffer());
  }

  public synchronized boolean sendUserRequest(UserRequest request) {
    if(loggedIn) {
      request.setSequenceNum(sentSeqNum);
      if(params.isRerequestEnabled()) {
        history.putRequest(sentSeqNum, request.getBuffer());
        history.setSentSeqNum(sentSeqNum);
      }
      sentSeqNum++;
      return connection.sendMessage(request.getBuffer());
    }
    else {
      return false;
    }
  }

  public boolean isConnected() {
    return connection.isConnected();
  }

  public boolean isLoggedIn()
  {
    return loggedIn;
  }

  public void addAuthorizationListener(AuthorizationListener authorizationListener) {
    handler.addAuthorizationListener(authorizationListener);
  }

  public void addConnectionListener(ConnectionListener connectionListener) {
    handler.addConnectionListener(connectionListener);
  }

  public void addErrorListener(ErrorListener errorListener) {
    handler.addErrorListener(errorListener);
  }

  public void addMessageListener(MessageListener messageListener) {
    handler.addMessageListener(messageListener);
  }

  public void removeAuthorizationListener(AuthorizationListener authorizationListener) {
    handler.removeAuthorizationListener(authorizationListener);
  }

  public void removeConnectionListener(ConnectionListener connectionListener) {
    handler.removeConnectionListener(connectionListener);
  }

  public void removeErrorListener(ErrorListener errorListener) {
    handler.removeErrorListener(errorListener);
  }

  public void removeMessageListener(MessageListener messageListener) {
    handler.removeMessageListener(messageListener);
  }

  @Override
  final protected void onLogin(int recvSeqNum, int sentSeqNum) throws IOException {
    if(sentSeqNum == 0) {
      if(params.isRerequestEnabled() || params.isResendEnabled()) {
        history.clear(recvSeqNum);
      }
      this.sentSeqNum = 0;
      this.receivedSeqNum = recvSeqNum;
    } else{
      if(this.sentSeqNum > sentSeqNum && params.isResendEnabled()) {
        for(int i=sentSeqNum; i < this.sentSeqNum; i++) {
          connection.sendMessage(history.getRequests().get(i));
          Logger.getLogger(Session.this.getClass().getName()).log(Level.INFO, "Resending message " + i + " " + sessionID);
        }
      }
      if(this.receivedSeqNum < recvSeqNum && params.isRerequestEnabled()) {
        connection.sendMessage(new ResendRequest(this.receivedSeqNum + 1, recvSeqNum - 1).getBuffer());
        Logger.getLogger(Session.this.getClass().getName()).log(Level.INFO, "Rerequest from " + this.receivedSeqNum + " to " +
                                                               recvSeqNum + " " +sessionID);
      }
    }
    heartbeatSender = new Thread() {
      @Override
      public void run() {
        while(!connection.isClosed()) {
          connection.sendMessage(new Heartbeat(Session.this.sentSeqNum).getBuffer());
          if(params.isRerequestEnabled() && params.isResendEnabled()) {
            try {
              history.setSentSeqNum(Session.this.sentSeqNum);
              history.saveHistory();
            }
            catch(IOException ioe) {
              Logger.getLogger(Session.this.getClass().getName()).log(Level.SEVERE, sessionID + ": Unable to save history: "
                                                                      + ioe.toString());
            }
          }
          Session.this.sentSeqNum++;
          try {
            Thread.sleep(Session.this.params.getHeartbeatInterval());
          }
          catch (InterruptedException ie) {
            Logger.getLogger(Session.this.getClass().getName()).log(Level.INFO, sessionID + ": Heartbeats stopped");
          }
        }
      }
    };
    heartbeatSender.start();
    loggedIn = true;
  }

  @Override
  final protected void onLogout(){
    Logger.getLogger(Session.this.getClass().getName()).log(Level.INFO, sessionID + "Logout received");
    try {
      stop();
      connection.close();
      if(params.isRerequestEnabled() && params.isResendEnabled()) {
        history.saveHistory();
      }
    }
    catch(Exception e) {
      Logger.getLogger(Session.this.getClass().getName()).log(Level.SEVERE, "Stop session failed " + sessionID);
    }
  }

  @Override
  final protected void onHeartbeat() {
//    Logger.getLogger(Session.this.getClass().getName()).log(Level.INFO, "Heartbeat received " + sessionID);
  }

  @Override
  final protected void onReceivedSeqNum(int seqNum) {
    if(receivedSeqNum + 1 != seqNum) {
      Logger.getLogger(Session.this.getClass().getName()).log(Level.WARNING, "Gap in incoming messages detected: expected " +
                         receivedSeqNum + " received " + seqNum + " " + sessionID);
    }
    receivedSeqNum = seqNum;
    if(params.isRerequestEnabled()) {
      history.setRecvSeqNum(receivedSeqNum);
    }
  }

  @Override
  final protected void onConnect() {
    Logger.getLogger(Session.this.getClass().getName()).log(Level.INFO, "Connected " + sessionID);
    connection.sendMessage(new Login(params.getLogin(), params.getPassword()).getBuffer());
  }

  @Override
  final protected void onDisconnect() {
    if(loggedIn) {
      loggedIn = false;
      heartbeatSender.interrupt();
    }
    Logger.getLogger(Session.this.getClass().getName()).log(Level.INFO, "Disconnected " + sessionID);
  }

  @Override
  protected void finalize() throws Throwable
  {
    if(params.isRerequestEnabled() && params.isResendEnabled()) {
      history.saveHistory();
    }
  }
}
