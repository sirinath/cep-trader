// license-header java merge-point
//
// Attention: Generated code! Do not modify by hand!
// Generated by: SpringService.vsl in andromda-spring-cartridge.
//
package com.algoTrader.service;

/**
 * 
 */
public interface RuleService
{

    /**
     * 
     */
    public void initServiceProvider(java.lang.String strategyName);

    /**
     * 
     */
    public boolean isInitialized(java.lang.String strategyName);

    /**
     * 
     */
    public void destroyServiceProvider(java.lang.String strategyName);

    /**
     * 
     */
    public void deployRule(java.lang.String strategyName, java.lang.String moduleName, java.lang.String ruleName);

    /**
     * 
     */
    public void deployRule(java.lang.String strategyName, java.lang.String moduleName, java.lang.String ruleName, java.lang.Integer targetId);

    /**
     * 
     */
    public void deployModule(java.lang.String strategyName, java.lang.String moduleName);

    /**
     * 
     */
    public void deployAllModules(java.lang.String strategyName);

    /**
     * 
     */
    public boolean isDeployed(java.lang.String strategyName, java.lang.String ruleName);

    /**
     * 
     */
    public void undeployRule(java.lang.String strategyName, java.lang.String ruleName);

    /**
     * 
     */
    public void undeployRuleByTarget(java.lang.String strategyName, java.lang.String ruleName, int targetId);

    /**
     * 
     */
    public void undeployModule(java.lang.String strategyName, java.lang.String moduleName);

    /**
     * 
     */
    public void sendEvent(java.lang.String strategyName, java.lang.Object object);

    /**
     * 
     */
    public void routeEvent(java.lang.String strategyName, java.lang.Object object);

    /**
     * 
     */
    public java.lang.Object getLastEvent(java.lang.String strategyName, java.lang.String ruleName);

    /**
     * 
     */
    public java.lang.Object getLastEventProperty(java.lang.String strategyName, java.lang.String ruleName, java.lang.String property);

    /**
     * 
     */
    public java.util.List getAllEvents(java.lang.String strategyName, java.lang.String ruleName);

    /**
     * 
     */
    public java.util.List getAllEventsProperty(java.lang.String strategyName, java.lang.String ruleName, java.lang.String property);

    /**
     * 
     */
    public void setInternalClock(java.lang.String strategyName, boolean internal);

    /**
     * 
     */
    public boolean isInternalClock(java.lang.String strategyName);

    /**
     * 
     */
    public void setCurrentTime(com.espertech.esper.client.time.CurrentTimeEvent currentTime);

    /**
     * 
     */
    public long getCurrentTime(java.lang.String strategyName);

    /**
     * 
     */
    public void initCoordination(java.lang.String strategyName);

    /**
     * 
     */
    public void coordinate(java.lang.String strategyName, com.espertech.esperio.csv.CSVInputAdapterSpec csvInputAdapterSpec);

    /**
     * 
     */
    public void startCoordination(java.lang.String strategyName);

    /**
     * 
     */
    public void setProperty(java.lang.String strategyName, java.lang.String key, java.lang.String value);

}