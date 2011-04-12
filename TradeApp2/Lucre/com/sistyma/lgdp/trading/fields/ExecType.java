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


package com.sistyma.lgdp.trading.fields;

/**
 *
 * @author kham
 */
public enum ExecType {
  NOT_SPECIFIED((byte)0),
  NEW((byte)1),
  TRADE((byte)2),
  DONE_FOR_DAY((byte)3),
  CANCELED((byte)4),
  REPLACE((byte)5),
  PENDING_CANCEL((byte)6),
  STOPPED((byte)7),
  REJECTED((byte)8),
  SUSPENDED((byte)9),
  PENDING_NEW((byte)10),
  CALCULATED((byte)11),
  EXPIRED((byte)12),
  RESTATED((byte)13),
  PENDING_REPLACE((byte)14),
  TRADE_CORRECT((byte)16),
  TRADE_CANCEL((byte)17),
  ORDER_STATUS((byte)18),
  UNRESOLVED_TRADE((byte)30);

  private final byte value;

  private ExecType(byte value) {
    this.value = value;
  }

  public static ExecType init(byte value) {
    ExecType execType;
    switch(value) {
      case 0:
          execType = NOT_SPECIFIED;
        break;
      case 1:
          execType = NEW;
        break;
      case 2:
          execType = TRADE;
        break;
      case 3:
          execType = DONE_FOR_DAY;
        break;
      case 4:
          execType = CANCELED;
        break;
      case 5:
          execType = REPLACE;
        break;
      case 6:
          execType = PENDING_CANCEL;
        break;
      case 7:
          execType = STOPPED;
        break;
      case 8:
          execType = REJECTED;
        break;
      case 9:
          execType = SUSPENDED;
        break;
      case 10:
          execType = PENDING_NEW;
        break;
      case 11:
          execType = CALCULATED;
        break;
      case 12:
          execType = EXPIRED;
        break;
      case 13:
          execType = RESTATED;
        break;
      case 14:
          execType = PENDING_REPLACE;
        break;
      case 16:
          execType = TRADE_CORRECT;
        break;
      case 17:
          execType = TRADE_CANCEL;
        break;
      case 18:
          execType = ORDER_STATUS;
        break;
      case 30:
          execType = UNRESOLVED_TRADE;
        break;
      default:
          execType = NOT_SPECIFIED;
    }
    return execType;
  }

  public byte getValue()
  {
    return value;
  }
}
