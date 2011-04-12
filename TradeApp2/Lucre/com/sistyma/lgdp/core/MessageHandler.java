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

import com.sistyma.lgdp.core.listeners.MessageListener;
import com.sistyma.lgdp.core.events.ConnectEvent;
import com.sistyma.lgdp.core.events.ConnectionLostEvent;
import com.sistyma.lgdp.core.events.ErrorEvent;
import com.sistyma.lgdp.core.events.LoginEvent;
import com.sistyma.lgdp.core.events.LogoutEvent;
import com.sistyma.lgdp.core.events.MessageEvent;
import com.sistyma.lgdp.core.listeners.AuthorizationListener;
import com.sistyma.lgdp.core.listeners.ConnectionListener;
import com.sistyma.lgdp.core.listeners.ErrorListener;
import com.sistyma.lgdp.core.responses.Heartbeat;
import com.sistyma.lgdp.core.responses.Login;
import com.sistyma.lgdp.core.responses.Logout;
import com.sistyma.lgdp.core.responses.Error;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

/**
 *
 * @author kham
 */
class MessageHandler implements com.sistyma.lgdp.core.connection.ConnectionListener {
  private LinkedList<AuthorizationListener> authorizationListeners;
  private LinkedList<ConnectionListener> connectionListeners;
  private LinkedList<ErrorListener> errorListeners;
  private LinkedList<MessageListener> messageListeners;
  private SessionStateListener sessionStateListener;
  private Object eventSource;
  
  public MessageHandler(SessionStateListener listener, Object evetSource) {
    authorizationListeners = new LinkedList<AuthorizationListener>();
    connectionListeners = new LinkedList<ConnectionListener>();
    errorListeners = new LinkedList<ErrorListener>();
    messageListeners = new LinkedList<MessageListener>();
    this.sessionStateListener = listener;
    this.eventSource = evetSource;
  }

  @Override
  public void onConnectionEstablished() {
    sessionStateListener.onConnect();
    synchronized(connectionListeners) {
      for(ConnectionListener listener: connectionListeners) {
        listener.onSucceedConnection(new ConnectEvent(eventSource));
      }
    }
  }

  @Override
  public void onConnectionError(String reason) {
    sessionStateListener.onDisconnect();
    synchronized(connectionListeners) {
      for(ConnectionListener listener: connectionListeners) {
        listener.onLostConnection(new ConnectionLostEvent(eventSource, reason));
      }
    }   
  }

  @Override
  public void onReadError(String reason) {
    sessionStateListener.onDisconnect();
    synchronized(connectionListeners) {
      for(ConnectionListener listener: connectionListeners) {
        listener.onLostConnection(new ConnectionLostEvent(eventSource, reason));
      }
    }   
  }

  @Override
  public void onWriteError(String reason) {
    sessionStateListener.onDisconnect();
    synchronized(connectionListeners) {
      for(ConnectionListener listener: connectionListeners) {
        listener.onLostConnection(new ConnectionLostEvent(eventSource, reason));
      }
    }   
  }

  @Override
  public void onConnectionClosed() {
    sessionStateListener.onDisconnect();
    synchronized(connectionListeners) {
      for(ConnectionListener listener: connectionListeners) {
        listener.onClosedConnection(new ConnectEvent(eventSource));
      }
    }   
  }

  @Override
  public void onMessage(byte[] data) {
    ByteBuffer buffer = ByteBuffer.wrap(data);
    short msgType = 0;
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    try {
      msgType = buffer.getShort();
      switch(msgType) {
        case Login.TYPE:
            Login login = new Login(buffer.slice());
            sessionStateListener.onLogin(login.getLastReceivedSeqNum(), login.getLastSentSeqNum());
            fireOnLogin(new LoginEvent(eventSource));
          break;
        case Logout.TYPE:
            Logout logout = new Logout(buffer.slice());
            sessionStateListener.onLogout();
            fireOnLogout(new LogoutEvent(eventSource));
          break;
        case Heartbeat.TYPE:
            Heartbeat heartbeat = new Heartbeat(buffer.slice());
            sessionStateListener.onReceivedSeqNum(heartbeat.getSeqNum());
            sessionStateListener.onHeartbeat();
          break;
        case Error.TYPE:
            Error error = new Error(buffer);
            sessionStateListener.onReceivedSeqNum(error.getSeqNum());
            fireMessageError(new ErrorEvent(eventSource, error.getCode(), error.getMessageID(), error.getErrorText()));
          break;
        default:
            int seqNum = buffer.getInt();
            sessionStateListener.onReceivedSeqNum(seqNum);
            fireMessage(new MessageEvent(eventSource, msgType, buffer.slice()));
      }
    }
    catch (IndexOutOfBoundsException ioobe) {
      fireProtoError(new ErrorEvent(eventSource, msgType, 0, "Unble to parse message " + ioobe.toString()));
    }
    catch (Exception e) {
      e.printStackTrace();
      fireError(new ErrorEvent(eventSource, msgType, 0, e.toString()));
    }
  }

  public void addAuthorizationListener(AuthorizationListener authorizationListener) {
    synchronized(authorizationListeners) {
      authorizationListeners.add(authorizationListener);
    }
  }

  public void addConnectionListener(ConnectionListener connectionListener) {
    synchronized(connectionListeners) {
      connectionListeners.add(connectionListener);
    }
  }

  public void addErrorListener(ErrorListener errorListener) {
    synchronized(errorListeners) {
      errorListeners.add(errorListener);
    }
  }

  public void addMessageListener(MessageListener messageListener) {
    synchronized(messageListeners) {
      messageListeners.add(messageListener);
    }
  }

  public void removeAuthorizationListener(AuthorizationListener authorizationListener) {
    synchronized(authorizationListeners) {
      authorizationListeners.remove(authorizationListener);
    }
  }

  public void removeConnectionListener(ConnectionListener connectionListener) {
    synchronized(connectionListeners) {
      connectionListeners.remove(connectionListener);
    }
  }

  public void removeErrorListener(ErrorListener errorListener) {
    synchronized(errorListeners) {
      errorListeners.remove(errorListener);
    }
  }

  public void removeMessageListener(MessageListener messageListener) {
    synchronized(messageListeners) {
      messageListeners.remove(messageListener);
    }
  }

  private void fireOnLogin(LoginEvent event) {
    synchronized(authorizationListeners) {
      for(AuthorizationListener listener: authorizationListeners) {
        listener.onLogin(event);
      }
    }
  }

  private void fireOnLogout(LogoutEvent event) {
    synchronized(authorizationListeners) {
      for(AuthorizationListener listener: authorizationListeners) {
        listener.onLogout(event);
      }
    }
  }

  private void fireProtoError(ErrorEvent event) {
    synchronized(errorListeners) {
      for(ErrorListener listener: errorListeners) {
        listener.onProtoError(event);
      }
    }
  }

  private void fireMessageError(ErrorEvent event) {
    synchronized(errorListeners) {
      for(ErrorListener listener: errorListeners) {
        listener.onMessageError(event);
      }
    }
  }

  private void fireError(ErrorEvent event) {
    synchronized(errorListeners) {
      for(ErrorListener listener: errorListeners) {
        listener.onError(event);
      }
    }
  }

  private void fireMessage(MessageEvent event) {
    synchronized(messageListeners) {
      for(MessageListener listener: messageListeners) {
        listener.onMessage(event);
      }
    }
  }
}
