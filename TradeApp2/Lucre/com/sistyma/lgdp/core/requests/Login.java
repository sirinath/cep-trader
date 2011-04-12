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


package com.sistyma.lgdp.core.requests;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author kham
 */
public class Login extends Request {
  public static final short TYPE = 1;
  public static final short LENGTH = 40;
  private static final int LOGIN_LEN = 16;
  private static final int PASSWORD_LEN = 16;
  private static final String DEFAULT_ENCODING = "ASCII";
  private static final String VERSION_KEY = "lgdp.proto.version";
  private final short  DEFAULT_VERSION = 1;
  private short version = DEFAULT_VERSION;

  public Login(String login, String password) {
    super(TYPE, LENGTH);
    String versionProp = System.getProperty(VERSION_KEY);
    if(versionProp != null) {
      try{
        version = Short.parseShort(versionProp);
      }
      catch(NumberFormatException nfe) {
        System.out.println("Invalid version value - using default = " + DEFAULT_VERSION);
      }
    }
    try {
      byte[] loginBuf = new byte[LOGIN_LEN];
      System.arraycopy(login.getBytes(DEFAULT_ENCODING), 0, loginBuf, 0, Math.min(LOGIN_LEN, login.getBytes(DEFAULT_ENCODING).length));
      byte[] passBuf = new byte[PASSWORD_LEN];
      System.arraycopy(password.getBytes(DEFAULT_ENCODING), 0, passBuf, 0, Math.min(PASSWORD_LEN, password.getBytes(DEFAULT_ENCODING).length));
      buffer.putShort(version);
      buffer.put(loginBuf);
      buffer.put(passBuf);
      buffer.putInt(0);//unused
    }
    catch(UnsupportedEncodingException uee) {
      System.out.println(uee);
    }
  }
}
