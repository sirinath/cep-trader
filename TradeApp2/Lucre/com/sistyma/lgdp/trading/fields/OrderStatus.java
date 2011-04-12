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
public enum OrderStatus {
  NOT_SPECIFIED((byte)0),
  NEW((byte)1),
  PARTIALLY_FILLED((byte)2),
  FILLED((byte)3),
  DONE_FOR_DAY((byte)4),
  CANCELED((byte)5),
  PENDING_CANCEL((byte)6),
  STOPPED((byte)7),
  REJECTED((byte)8),
  SUSPENDED((byte)9),
  PENDING_NEW((byte)10),
  CALCULATED((byte)11),
  EXPIRED((byte)12),
  ACCEPTED_FOR_BIDDING((byte)13),
  PENDING_REPLACE((byte)14),
  REPLACED((byte)15);

  private final byte value;

  private OrderStatus(byte value) {
    this.value = value;
  }

  public byte getValue() {
    return value;
  }

  public static OrderStatus init(byte value) {
    OrderStatus orderStatus;   
    switch(value) {
      case 0:
          orderStatus = NOT_SPECIFIED;
        break;
      case 1:
          orderStatus = NEW;
        break;
      case 2:
          orderStatus = PARTIALLY_FILLED;
        break;
      case 3:
          orderStatus = FILLED;
        break;
      case 4:
          orderStatus = DONE_FOR_DAY;
        break;
      case 5:
          orderStatus = CANCELED;
        break;
      case 6:
          orderStatus = PENDING_CANCEL;
        break;
      case 7:
          orderStatus = STOPPED;
        break;
      case 8:
          orderStatus = REJECTED;
        break;
      case 9:
          orderStatus = SUSPENDED;
        break;
      case 10:
          orderStatus = PENDING_NEW;
        break;
      case 11:
          orderStatus = CALCULATED;
        break;
      case 12:
          orderStatus = EXPIRED;
        break;
      case 13:
          orderStatus = ACCEPTED_FOR_BIDDING;
        break;
      case 14:
          orderStatus = PENDING_REPLACE;
        break;
      case 15:
          orderStatus = REPLACED;
        break;
      default:
          orderStatus = NOT_SPECIFIED;
    }
    return orderStatus;
  }
}
