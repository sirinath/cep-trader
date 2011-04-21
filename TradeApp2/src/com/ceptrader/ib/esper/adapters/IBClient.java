package com.ceptrader.ib.esper.adapters;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

import com.ceptrader.esper.CEPMan;
import com.ceptrader.ib.drools.pojoevents.request.CalculateImpliedVolatility;
import com.ceptrader.ib.drools.pojoevents.request.CalculateOptionPrice;
import com.ceptrader.ib.drools.pojoevents.request.CancelCalculateImpliedVolatility;
import com.ceptrader.ib.drools.pojoevents.request.CancelCalculateOptionPrice;
import com.ceptrader.ib.drools.pojoevents.request.CancelFundamentalData;
import com.ceptrader.ib.drools.pojoevents.request.CancelHistoricalData;
import com.ceptrader.ib.drools.pojoevents.request.CancelMktData;
import com.ceptrader.ib.drools.pojoevents.request.CancelMktDepth;
import com.ceptrader.ib.drools.pojoevents.request.CancelNewsBulletins;
import com.ceptrader.ib.drools.pojoevents.request.CancelOrder;
import com.ceptrader.ib.drools.pojoevents.request.CancelRealTimeBars;
import com.ceptrader.ib.drools.pojoevents.request.CancelScannerSubscription;
import com.ceptrader.ib.drools.pojoevents.request.EConnect;
import com.ceptrader.ib.drools.pojoevents.request.EDisconnect;
import com.ceptrader.ib.drools.pojoevents.request.ExerciseOptions;
import com.ceptrader.ib.drools.pojoevents.request.PlaceOrder;
import com.ceptrader.ib.drools.pojoevents.request.ReplaceFA;
import com.ceptrader.ib.drools.pojoevents.request.ReqAccountUpdates;
import com.ceptrader.ib.drools.pojoevents.request.ReqAllOpenOrders;
import com.ceptrader.ib.drools.pojoevents.request.ReqAutoOpenOrders;
import com.ceptrader.ib.drools.pojoevents.request.ReqContractDetails;
import com.ceptrader.ib.drools.pojoevents.request.ReqCurrentTime;
import com.ceptrader.ib.drools.pojoevents.request.ReqExecutions;
import com.ceptrader.ib.drools.pojoevents.request.ReqFundamentalData;
import com.ceptrader.ib.drools.pojoevents.request.ReqHistoricalData;
import com.ceptrader.ib.drools.pojoevents.request.ReqIds;
import com.ceptrader.ib.drools.pojoevents.request.ReqManagedAccts;
import com.ceptrader.ib.drools.pojoevents.request.ReqMktData;
import com.ceptrader.ib.drools.pojoevents.request.ReqMktDepth;
import com.ceptrader.ib.drools.pojoevents.request.ReqNewsBulletins;
import com.ceptrader.ib.drools.pojoevents.request.ReqOpenOrders;
import com.ceptrader.ib.drools.pojoevents.request.ReqRealTimeBars;
import com.ceptrader.ib.drools.pojoevents.request.ReqScannerParameters;
import com.ceptrader.ib.drools.pojoevents.request.ReqScannerSubscription;
import com.ceptrader.ib.drools.pojoevents.request.RequestFA;
import com.ceptrader.util.Logger;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.EReader;
import com.ib.client.EWrapper;
import com.ib.client.ExecutionFilter;
import com.ib.client.Order;
import com.ib.client.ScannerSubscription;

public class IBClient extends EClientSocket {
	static {
		final Properties p = new Properties();
		try {
			p.load(IBClient.class.getResourceAsStream("/IBClient.properties"));
			
			IBClient.host = p.getProperty("host", null);
			IBClient.port = Integer.parseInt(p.getProperty("port", "7496"));
		} catch (final IOException e) {
			Logger.log(e);
		}
	}
	
	private static int	     clientId	= 0;
	private static String	 host;
	private static int	     port;	         // 7496; 4001;
	private static IBClient	 ibc;
	private static IBAdapter	ibAdp;
	
	public synchronized static IBClient getIBClient() {
		if (IBClient.ibc == null) {
			IBClient.ibAdp = IBAdapter.getAdapter();
			
			IBClient.ibc = new IBClient(IBClient.ibAdp);
		}
		
		return IBClient.ibc;
	}
	
	private IBClient(final EWrapper ew) {
		super(ew);
	}
	
	public synchronized static void connect() {
		if (IBClient.ibc == null) {
			IBClient.getIBClient();
		}
		
		if (!IBClient.ibc.isConnected()) {
			IBClient.ibc.eConnect(IBClient.host, IBClient.port,
			            IBClient.clientId);
			
			IBClient.clientId++;
		} else {
			throw new IllegalStateException("Already connected.");
		}
	}
	
	private static final String	broker	= "IB";
	private static final String	account	= "LoggedInAccount";
	
	public synchronized static void disconnect() {
		if (IBClient.ibc != null) {
			IBClient.ibc.eDisconnect();
		}
	}
	
	@Override
	public synchronized void calculateImpliedVolatility(final int reqId,
	        final Contract contract, final double optionPrice,
	        final double underPrice) {
		super.calculateImpliedVolatility(reqId, contract, optionPrice,
		        underPrice);
		
		final CalculateImpliedVolatility o = new CalculateImpliedVolatility();
		
		o.reqId = reqId;
		o.contract = contract;
		o.optionPrice = optionPrice;
		o.underPrice = underPrice;
		
		process(o);
	}
	
	@Override
	public synchronized void calculateOptionPrice(final int reqId,
	        final Contract contract,
	        final double volatility, final double underPrice) {
		super.calculateOptionPrice(reqId, contract, volatility, underPrice);
		
		final CalculateOptionPrice o = new CalculateOptionPrice();
		
		o.reqId = reqId;
		o.contract = contract;
		o.volatility = volatility;
		o.underPrice = underPrice;
		
		process(o);
	}
	
	@Override
	public synchronized void cancelCalculateImpliedVolatility(final int reqId) {
		super.cancelCalculateImpliedVolatility(reqId);
		
		final CancelCalculateImpliedVolatility o = new CancelCalculateImpliedVolatility();
		
		o.reqId = reqId;
		
		process(o);
	}
	
	@Override
	public synchronized void cancelCalculateOptionPrice(final int reqId) {
		super.cancelCalculateOptionPrice(reqId);
		
		final CancelCalculateOptionPrice o = new CancelCalculateOptionPrice();
		
		o.reqId = reqId;
		
		process(o);
	}
	
	@Override
	public synchronized void cancelFundamentalData(final int reqId) {
		super.cancelFundamentalData(reqId);
		
		final CancelFundamentalData o = new CancelFundamentalData();
		
		o.reqId = reqId;
		
		process(o);
	}
	
	@Override
	public synchronized void cancelHistoricalData(final int tickerId) {
		super.cancelHistoricalData(tickerId);
		
		final CancelHistoricalData o = new CancelHistoricalData();
		o.tickerId = tickerId;
		
		process(o);
	}
	
	@Override
	public synchronized void cancelMktData(final int tickerId) {
		super.cancelMktData(tickerId);
		
		final CancelMktData o = new CancelMktData();
		o.tickerId = tickerId;
		
		process(o);
	}
	
	@Override
	public synchronized void cancelMktDepth(final int tickerId) {
		super.cancelMktDepth(tickerId);
		
		final CancelMktDepth o = new CancelMktDepth();
		o.tickerId = tickerId;
		
		process(o);
	}
	
	@Override
	public synchronized void cancelNewsBulletins() {
		super.cancelNewsBulletins();
		
		final CancelNewsBulletins o = new CancelNewsBulletins();
		
		process(o);
	}
	
	@Override
	public synchronized void cancelOrder(final int id) {
		super.cancelOrder(id);
		
		final CancelOrder o = new CancelOrder();
		
		o.id = id;
		
		process(o);
	}
	
	@Override
	public void cancelRealTimeBars(final int tickerId) {
		super.cancelRealTimeBars(tickerId);
		
		final CancelRealTimeBars o = new CancelRealTimeBars();
		
		o.tickerId = tickerId;
		
		process(o);
	}
	
	@Override
	public synchronized void cancelScannerSubscription(final int tickerId) {
		super.cancelScannerSubscription(tickerId);
		
		final CancelScannerSubscription o = new CancelScannerSubscription();
		o.tickerId = tickerId;
		
		process(o);
	}
	
	@Override
	public EReader createReader(final EClientSocket socket,
	        final DataInputStream dis) {
		return super.createReader(socket, dis);
	}
	
	@Override
	public synchronized void eConnect(final Socket socket, final int clientId)
	        throws IOException {
		super.eConnect(socket, clientId);
		
		final EConnect o = new EConnect();
		final InetAddress inetAdd = socket.getInetAddress();
		o.host = inetAdd.getHostName();
		o.port = socket.getPort();
		o.clientId = clientId;
		
		process(o);
	}
	
	@Override
	public synchronized void eConnect(final String host, final int port,
	        final int clientId) {
		super.eConnect(host, port, clientId);
		
		final EConnect o = new EConnect();
		o.host = host;
		o.port = port;
		o.clientId = clientId;
		
		process(o);
	}
	
	@Override
	public synchronized void eDisconnect() {
		super.eDisconnect();
		
		final EDisconnect o = new EDisconnect();
		
		process(o);
	}
	
	@Override
	public synchronized void exerciseOptions(final int tickerId,
	        final Contract contract,
	        final int exerciseAction, final int exerciseQuantity,
	        final String account,
	        final int override) {
		super.exerciseOptions(tickerId, contract, exerciseAction,
		        exerciseQuantity,
		        account, override);
		
		final ExerciseOptions o = new ExerciseOptions();
		o.tickerId = tickerId;
		o.contract = contract;
		o.exerciseAction = exerciseAction;
		o.exerciseQuantity = exerciseQuantity;
		o.account = account;
		o.override = override;
		
		process(o);
	}
	
	@Override
	public synchronized void placeOrder(final int id, final Contract contract,
	        final Order order) {
		super.placeOrder(id, contract, order);
		
		final PlaceOrder o = new PlaceOrder();
		o.id = id;
		o.contract = contract;
		o.order = order;
		
		process(o);
	}
	
	@Override
	public boolean isConnected() {
		return super.isConnected();
	}
	
	@Override
	public synchronized void replaceFA(final int faDataType, final String xml) {
		super.replaceFA(faDataType, xml);
		
		final ReplaceFA o = new ReplaceFA();
		o.faDataType = faDataType;
		o.xml = xml;
		
		process(o);
	}
	
	@Override
	public synchronized void reqAccountUpdates(final boolean subscribe,
	        final String acctCode) {
		super.reqAccountUpdates(subscribe, acctCode);
		
		final ReqAccountUpdates o = new ReqAccountUpdates();
		o.subscribe = subscribe;
		o.acctCode = acctCode;
		
		process(o);
	}
	
	@Override
	public synchronized void reqAllOpenOrders() {
		super.reqAllOpenOrders();
		
		final ReqAllOpenOrders o = new ReqAllOpenOrders();
		
		process(o);
	}
	
	@Override
	public EReader reader() {
		return super.reader();
	}
	
	@Override
	public synchronized void reqAutoOpenOrders(final boolean bAutoBind) {
		super.reqAutoOpenOrders(bAutoBind);
		
		final ReqAutoOpenOrders o = new ReqAutoOpenOrders();
		o.bAutoBind = bAutoBind;
		
		process(o);
	}
	
	@Override
	public synchronized void reqContractDetails(final int reqId,
	        final Contract contract) {
		super.reqContractDetails(reqId, contract);
		
		final ReqContractDetails o = new ReqContractDetails();
		o.reqId = reqId;
		o.contract = contract;
		
		process(o);
	}
	
	@Override
	public synchronized void reqCurrentTime() {
		super.reqCurrentTime();
		
		final ReqCurrentTime o = new ReqCurrentTime();
		
		process(o);
	}
	
	@Override
	public synchronized void reqExecutions(final int reqId,
	        final ExecutionFilter filter) {
		super.reqExecutions(reqId, filter);
		
		final ReqExecutions o = new ReqExecutions();
		o.reqId = reqId;
		o.filter = filter;
		
		process(o);
	}
	
	@Override
	public synchronized void reqFundamentalData(final int reqId,
	        final Contract contract,
	        final String reportType) {
		super.reqFundamentalData(reqId, contract, reportType);
		
		final ReqFundamentalData o = new ReqFundamentalData();
		o.reqId = reqId;
		o.contract = contract;
		o.reportType = reportType;
		
		process(o);
	}
	
	@Override
	public synchronized void reqHistoricalData(final int tickerId,
	        final Contract contract,
	        final String endDateTime, final String durationStr,
	        final String barSizeSetting,
	        final String whatToShow, final int useRTH, final int formatDate) {
		super.reqHistoricalData(tickerId, contract, endDateTime, durationStr,
		        barSizeSetting, whatToShow, useRTH, formatDate);
		
		final ReqHistoricalData o = new ReqHistoricalData();
		o.tickerId = tickerId;
		o.contract = contract;
		o.endDateTime = endDateTime;
		o.durationStr = durationStr;
		o.barSizeSetting = barSizeSetting;
		o.whatToShow = whatToShow;
		o.useRTH = useRTH;
		o.formatDate = formatDate;
		
		process(o);
	}
	
	@Override
	public synchronized void reqIds(final int numIds) {
		super.reqIds(numIds);
		
		final ReqIds o = new ReqIds();
		o.numIds = numIds;
		
		process(o);
	}
	
	@Override
	public synchronized void reqManagedAccts() {
		super.reqManagedAccts();
		
		final ReqManagedAccts o = new ReqManagedAccts();
		
		process(o);
	}
	
	@Override
	public synchronized void reqMktData(final int tickerId,
	        final Contract contract,
	        final String genericTickList, final boolean snapshot) {
		super.reqMktData(tickerId, contract, genericTickList, snapshot);
		
		final ReqMktData o = new ReqMktData();
		o.tickerId = tickerId;
		o.contract = contract;
		o.genericTickList = genericTickList;
		o.snapshot = snapshot;
		
		process(o);
	}
	
	@Override
	public synchronized void reqMktDepth(final int tickerId,
	        final Contract contract,
	        final int numRows) {
		super.reqMktDepth(tickerId, contract, numRows);
		
		final ReqMktDepth o = new ReqMktDepth();
		o.tickerId = tickerId;
		o.contract = contract;
		o.numRows = numRows;
		
		process(o);
	}
	
	@Override
	public synchronized void reqNewsBulletins(final boolean allMsgs) {
		super.reqNewsBulletins(allMsgs);
		
		final ReqNewsBulletins o = new ReqNewsBulletins();
		o.allMsgs = allMsgs;
		
		process(o);
	}
	
	@Override
	public synchronized void reqOpenOrders() {
		super.reqOpenOrders();
		
		final ReqOpenOrders o = new ReqOpenOrders();
		
		process(o);
	}
	
	@Override
	public synchronized void reqRealTimeBars(final int tickerId,
	        final Contract contract,
	        final int barSize, final String whatToShow, final boolean useRTH) {
		super.reqRealTimeBars(tickerId, contract, barSize, whatToShow, useRTH);
		
		final ReqRealTimeBars o = new ReqRealTimeBars();
		o.tickerId = tickerId;
		o.contract = contract;
		o.barSize = barSize;
		o.whatToShow = whatToShow;
		o.useRTH = useRTH;
		
		process(o);
	}
	
	@Override
	public synchronized void reqScannerParameters() {
		super.reqScannerParameters();
		
		final ReqScannerParameters o = new ReqScannerParameters();
		
		process(o);
	}
	
	@Override
	public synchronized void reqScannerSubscription(final int tickerId,
	        final ScannerSubscription subscription) {
		super.reqScannerSubscription(tickerId, subscription);
		
		final ReqScannerSubscription o = new ReqScannerSubscription();
		o.tickerId = tickerId;
		o.subscription = subscription;
		
		process(o);
	}
	
	@Override
	public synchronized void requestFA(final int faDataType) {
		super.requestFA(faDataType);
		
		final RequestFA o = new RequestFA();
		o.faDataType = faDataType;
		
		process(o);
	}
	
	@Override
	public synchronized void setServerLogLevel(final int logLevel) {
		super.setServerLogLevel(logLevel);
	}
	
	public static int getClientid() {
		return IBClient.clientId;
	}
	
	public <T> void process(final T o) {
		CEPMan.getCEPMan().pumpEvent(o);
		
		Logger.log(o);
	}
}
