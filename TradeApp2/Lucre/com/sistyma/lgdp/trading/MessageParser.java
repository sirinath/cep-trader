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


package com.sistyma.lgdp.trading;

import com.sistyma.lgdp.core.events.MessageEvent;
import com.sistyma.lgdp.core.listeners.MessageListener;
import com.sistyma.lgdp.trading.events.ExecutionReportEvent;
import com.sistyma.lgdp.trading.listeners.ExecutionReportListener;
import com.sistyma.lgdp.trading.responses.ExecutionReport;
import java.util.LinkedList;

/**
 *
 * @author kham
 */
public class MessageParser implements MessageListener {
  private Object eventSource = null;
  private LinkedList<ExecutionReportListener> executionReportListeners;

  public MessageParser(Object source) {
    this.eventSource = source;
    executionReportListeners = new LinkedList<ExecutionReportListener>();
  }

  public void addExecutionReportListener(ExecutionReportListener listener) {
    synchronized(executionReportListeners) {
      executionReportListeners.add(listener);
    }
  }

  public void removeExecutionReportListener(ExecutionReportListener listener) {
    synchronized(executionReportListeners) {
      executionReportListeners.remove(listener);
    }
  }

  @Override
  public void onMessage(MessageEvent event) {
    switch(event.getType()) {
      case ExecutionReport.TYPE:
          ExecutionReport report = new ExecutionReport(event.getBuffer());
          fireExecutionReport(new ExecutionReportEvent(eventSource, report.getExecType(), report.getOrderStatus(), report.getClOrderID(),
                                                       report.getExecutionID(), report.getOrderType(), report.getOrderSide(), 
                                                       report.getExchange(), report.getSymbol(), report.getLastQty(), report.getLastPrice(),
                                                       report.getText(), report.getCumQty(), report.getLeavesQty()));
        break;
    }
  }

  private void fireExecutionReport(ExecutionReportEvent event) {
    synchronized(executionReportListeners) {
      for(ExecutionReportListener listener: executionReportListeners) {
        listener.onExecutionReport(event);
      }
    }
  }
}
