package com.ceptrader.ib.esper.adapters;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.ceptrader.util.Logger;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.EReader;
import com.ib.client.EWrapper;
import com.ib.client.ExecutionFilter;
import com.ib.client.Order;
import com.ib.client.ScannerSubscription;

public class IBClient extends EClientSocket {
	private static int	     clientId	= 0;
	private static String	 host	  = null;
	private static int	     port	  = 7496; // 7496; 4001;
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
	
	public static class CalculateImpliedVolatility {
		public int		reqId;
		public Contract	contract;
		public double	optionPrice;
		public double	underPrice;
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
		
		Logger.log(o);
	}
	
	public static class CalculateOptionPrice {
		public int		reqId;
		public Contract	contract;
		public double	volatility;
		public double	underPrice;
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
		
		Logger.log(o);
	}
	
	public static class CancelCalculateImpliedVolatility {
		public int	reqId;
	}
	
	@Override
	public synchronized void cancelCalculateImpliedVolatility(final int reqId) {
		super.cancelCalculateImpliedVolatility(reqId);
		
		final CancelCalculateImpliedVolatility o = new CancelCalculateImpliedVolatility();
		
		o.reqId = reqId;
		
		Logger.log(o);
	}
	
	public static class CancelCalculateOptionPrice {
		public int	reqId;
	}
	
	@Override
	public synchronized void cancelCalculateOptionPrice(final int reqId) {
		super.cancelCalculateOptionPrice(reqId);
		
		final CancelCalculateOptionPrice o = new CancelCalculateOptionPrice();
		
		o.reqId = reqId;
		
		Logger.log(o);
	}
	
	public static class CancelFundamentalData {
		public int	reqId;
	}
	
	@Override
	public synchronized void cancelFundamentalData(final int reqId) {
		super.cancelFundamentalData(reqId);
		
		final CancelFundamentalData o = new CancelFundamentalData();
		
		o.reqId = reqId;
		
		Logger.log(o);
	}
	
	public static class CancelHistoricalData {
		public int	tickerId;
	}
	
	@Override
	public synchronized void cancelHistoricalData(final int tickerId) {
		super.cancelHistoricalData(tickerId);
		
		final CancelHistoricalData o = new CancelHistoricalData();
		o.tickerId = tickerId;
		
		Logger.log(o);
	}
	
	public static class CancelMktData {
		public int	tickerId;
	}
	
	@Override
	public synchronized void cancelMktData(final int tickerId) {
		super.cancelMktData(tickerId);
		
		final CancelMktData o = new CancelMktData();
		o.tickerId = tickerId;
		
		Logger.log(o);
	}
	
	public static class CancelMktDepth {
		public int	tickerId;
	}
	
	@Override
	public synchronized void cancelMktDepth(final int tickerId) {
		super.cancelMktDepth(tickerId);
		
		final CancelMktDepth o = new CancelMktDepth();
		o.tickerId = tickerId;
		
		Logger.log(o);
	}
	
	public static class CancelNewsBulletins {
	}
	
	@Override
	public synchronized void cancelNewsBulletins() {
		super.cancelNewsBulletins();
		
		final CancelNewsBulletins o = new CancelNewsBulletins();
		
		Logger.log(o);
	}
	
	public static class CancelOrder {
		public int	id;
	}
	
	@Override
	public synchronized void cancelOrder(final int id) {
		super.cancelOrder(id);
		
		final CancelOrder o = new CancelOrder();
		
		o.id = id;
		
		Logger.log(o);
	}
	
	public static class CancelRealTimeBars {
		public int	tickerId;
	}
	
	@Override
	public void cancelRealTimeBars(final int tickerId) {
		super.cancelRealTimeBars(tickerId);
		
		final CancelRealTimeBars o = new CancelRealTimeBars();
		
		o.tickerId = tickerId;
		
		Logger.log(o);
	}
	
	public static class CancelScannerSubscription {
		public int	tickerId;
	}
	
	@Override
	public synchronized void cancelScannerSubscription(final int tickerId) {
		super.cancelScannerSubscription(tickerId);
		
		final CancelScannerSubscription o = new CancelScannerSubscription();
		o.tickerId = tickerId;
		
		Logger.log(o);
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
	}
	
	@Override
	public synchronized void eConnect(final String host, final int port,
	        final int clientId) {
		super.eConnect(host, port, clientId);
	}
	
	@Override
	public synchronized void eDisconnect() {
		super.eDisconnect();
	}
	
	public static class ExerciseOptions {
		public int		tickerId;
		public Contract	contract;
		public int		exerciseAction;
		public int		exerciseQuantity;
		public String	account;
		public int		override;
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
		
		Logger.log(o);
	}
	
	public static class PlaceOrder {
		public int		id;
		public Contract	contract;
		public Order	order;
	}
	
	@Override
	public synchronized void placeOrder(final int id, final Contract contract,
	        final Order order) {
		super.placeOrder(id, contract, order);
		
		final PlaceOrder o = new PlaceOrder();
		o.id = id;
		o.contract = contract;
		o.order = order;
		
		Logger.log(o);
	}
	
	@Override
	public boolean isConnected() {
		return super.isConnected();
	}
	
	public static class ReplaceFA {
		public int		faDataType;
		public String	xml;
	}
	
	@Override
	public synchronized void replaceFA(final int faDataType, final String xml) {
		super.replaceFA(faDataType, xml);
		
		final ReplaceFA o = new ReplaceFA();
		o.faDataType = faDataType;
		o.xml = xml;
		
		Logger.log(o);
	}
	
	public static class ReqAccountUpdates {
		public boolean	subscribe;
		public String	acctCode;
	}
	
	@Override
	public synchronized void reqAccountUpdates(final boolean subscribe,
	        final String acctCode) {
		super.reqAccountUpdates(subscribe, acctCode);
		
		final ReqAccountUpdates o = new ReqAccountUpdates();
		o.subscribe = subscribe;
		o.acctCode = acctCode;
		
		Logger.log(o);
	}
	
	public static class ReqAllOpenOrders {
	}
	
	@Override
	public synchronized void reqAllOpenOrders() {
		super.reqAllOpenOrders();
		
		final ReqAllOpenOrders o = new ReqAllOpenOrders();
		
		Logger.log(o);
	}
	
	@Override
	public EReader reader() {
		return super.reader();
	}
	
	public static class ReqAutoOpenOrders {
		public boolean	bAutoBind;
	}
	
	@Override
	public synchronized void reqAutoOpenOrders(final boolean bAutoBind) {
		super.reqAutoOpenOrders(bAutoBind);
		
		final ReqAutoOpenOrders o = new ReqAutoOpenOrders();
		o.bAutoBind = bAutoBind;
		
		Logger.log(o);
	}
	
	public static class ReqContractDetails {
		public int		reqId;
		public Contract	contract;
	}
	
	@Override
	public synchronized void reqContractDetails(final int reqId,
	        final Contract contract) {
		super.reqContractDetails(reqId, contract);
		
		final ReqContractDetails o = new ReqContractDetails();
		o.reqId = reqId;
		o.contract = contract;
		
		Logger.log(o);
	}
	
	public static class ReqCurrentTime {
	}
	
	@Override
	public synchronized void reqCurrentTime() {
		super.reqCurrentTime();
		
		final ReqCurrentTime o = new ReqCurrentTime();
		
		Logger.log(o);
	}
	
	public static class ReqExecutions {
		public int		       reqId;
		public ExecutionFilter	filter;
	}
	
	@Override
	public synchronized void reqExecutions(final int reqId,
	        final ExecutionFilter filter) {
		super.reqExecutions(reqId, filter);
		
		final ReqExecutions o = new ReqExecutions();
		o.reqId = reqId;
		o.filter = filter;
		
		Logger.log(o);
	}
	
	public static class ReqFundamentalData {
		public int		reqId;
		public Contract	contract;
		public String	reportType;
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
		
		Logger.log(o);
	}
	
	public static class ReqHistoricalData {
		public int		tickerId;
		public Contract	contract;
		public String	endDateTime;
		public String	durationStr;
		public String	barSizeSetting;
		public String	whatToShow;
		public int		useRTH;
		public int		formatDate;
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
		
		Logger.log(o);
	}
	
	public static class ReqIds {
		public int	numIds;
	}
	
	@Override
	public synchronized void reqIds(final int numIds) {
		super.reqIds(numIds);
		
		final ReqIds o = new ReqIds();
		o.numIds = numIds;
		
		Logger.log(o);
	}
	
	public static class ReqManagedAccts {
	}
	
	@Override
	public synchronized void reqManagedAccts() {
		super.reqManagedAccts();
		
		final ReqManagedAccts o = new ReqManagedAccts();
		
		Logger.log(o);
	}
	
	public static class ReqMktData {
		public int		tickerId;
		public Contract	contract;
		public String	genericTickList;
		public boolean	snapshot;
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
		
		Logger.log(o);
	}
	
	public static class ReqMktDepth {
		public int		tickerId;
		public Contract	contract;
		public int		numRows;
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
		
		Logger.log(o);
	}
	
	public static class ReqNewsBulletins {
		public boolean	allMsgs;
	}
	
	@Override
	public synchronized void reqNewsBulletins(final boolean allMsgs) {
		super.reqNewsBulletins(allMsgs);
		
		final ReqNewsBulletins o = new ReqNewsBulletins();
		o.allMsgs = allMsgs;
		
		Logger.log(o);
	}
	
	public static class ReqOpenOrders {
	}
	
	@Override
	public synchronized void reqOpenOrders() {
		super.reqOpenOrders();
		
		final ReqOpenOrders o = new ReqOpenOrders();
		
		Logger.log(o);
	}
	
	public static class ReqRealTimeBars {
		public int		tickerId;
		public Contract	contract;
		public int		barSize;
		public String	whatToShow;
		public boolean	useRTH;
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
		
		Logger.log(o);
	}
	
	public static class ReqScannerParameters {
	}
	
	@Override
	public synchronized void reqScannerParameters() {
		super.reqScannerParameters();
		
		final ReqScannerParameters o = new ReqScannerParameters();
		
		Logger.log(o);
	}
	
	public static class ReqScannerSubscription {
		public int		           tickerId;
		public ScannerSubscription	subscription;
	}
	
	@Override
	public synchronized void reqScannerSubscription(final int tickerId,
	        final ScannerSubscription subscription) {
		super.reqScannerSubscription(tickerId, subscription);
		
		final ReqScannerSubscription o = new ReqScannerSubscription();
		o.tickerId = tickerId;
		o.subscription = subscription;
		
		Logger.log(o);
	}
	
	public static class RequestFA {
		public int	faDataType;
	}
	
	@Override
	public synchronized void requestFA(final int faDataType) {
		super.requestFA(faDataType);
		
		final RequestFA o = new RequestFA();
		o.faDataType = faDataType;
		
		Logger.log(o);
	}
	
	@Override
	public synchronized void setServerLogLevel(final int logLevel) {
		super.setServerLogLevel(logLevel);
	}
	
	public static int getClientid() {
		return IBClient.clientId;
	}
}
