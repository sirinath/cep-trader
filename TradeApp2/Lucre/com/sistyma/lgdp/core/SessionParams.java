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

/**
 *
 * @author kham
 */
public class SessionParams {
  private static final String DEFAULT_LOGIN = "login";
  private static final String DEFAULT_PASSWORD = "password";
  private static final String DEFAULT_HOST = "192.168.242.34";
  private static final int DEFAULT_PORT = 10000;
  private static final int DEFAULT_HERTBEAT_INTERVAL = 10000;
  private static final String DEFAULT_STORE_PATH = "store";
  private static final boolean DEFAULT_REREQUEST = true;
  private static final boolean DEFAULT_RESEND = true;

  private String login = DEFAULT_LOGIN;
  private String password = DEFAULT_PASSWORD;
  private String host = DEFAULT_HOST;
  private int port = DEFAULT_PORT;
  private int heartbeatInterval = DEFAULT_HERTBEAT_INTERVAL;
  private String storePath = DEFAULT_STORE_PATH;
  private boolean rerequestEnabled = DEFAULT_REREQUEST;
  private boolean resendEnabled = DEFAULT_RESEND;

  public SessionParams() {  
  }

  public String getLogin()
  {
    return login;
  }

  public void setLogin(String login)
  {
    this.login = login;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getHost()
  {
    return host;
  }

  public void setHost(String host)
  {
    this.host = host;
  }

  public int getPort()
  {
    return port;
  }

  public void setPort(int port)
  {
    this.port = port;
  }

  public int getHeartbeatInterval()
  {
    return heartbeatInterval;
  }

  public void setHeartbeatInterval(int heartbeatInterval)
  {
    this.heartbeatInterval = heartbeatInterval;
  }

  public String getStorePath()
  {
    return storePath;
  }

  public void setStorePath(String storePath)
  {
    this.storePath = storePath;
  }

  public boolean isRerequestEnabled()
  {
    return rerequestEnabled;
  }

  public void setRerequestEnabled(boolean rerequestEnabled)
  {
    this.rerequestEnabled = rerequestEnabled;
  }

  public boolean isResendEnabled()
  {
    return resendEnabled;
  }

  public void setResendEnabled(boolean resendEnabled)
  {
    this.resendEnabled = resendEnabled;
  }
}
