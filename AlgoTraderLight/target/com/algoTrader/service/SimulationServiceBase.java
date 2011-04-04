// license-header java merge-point
//
// Attention: Generated code! Do not modify by hand!
// Generated by: SpringServiceBase.vsl in andromda-spring-cartridge.
//
package com.algoTrader.service;

/**
 * <p>
 * Spring Service base class for <code>com.algoTrader.service.SimulationService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see com.algoTrader.service.SimulationService
 */
public abstract class SimulationServiceBase
    implements com.algoTrader.service.SimulationService
{

    private com.algoTrader.service.RuleService ruleService;

    /**
     * Sets the reference to <code>ruleService</code>.
     */
    public void setRuleService(com.algoTrader.service.RuleService ruleService)
    {
        this.ruleService = ruleService;
    }

    /**
     * Gets the reference to <code>ruleService</code>.
     */
    protected com.algoTrader.service.RuleService getRuleService()
    {
        return this.ruleService;
    }

    private com.algoTrader.entity.SecurityDao securityDao;

    /**
     * Sets the reference to <code>security</code>'s DAO.
     */
    public void setSecurityDao(com.algoTrader.entity.SecurityDao securityDao)
    {
        this.securityDao = securityDao;
    }

    /**
     * Gets the reference to <code>security</code>'s DAO.
     */
    protected com.algoTrader.entity.SecurityDao getSecurityDao()
    {
        return this.securityDao;
    }

    private com.algoTrader.entity.TransactionDao transactionDao;

    /**
     * Sets the reference to <code>transaction</code>'s DAO.
     */
    public void setTransactionDao(com.algoTrader.entity.TransactionDao transactionDao)
    {
        this.transactionDao = transactionDao;
    }

    /**
     * Gets the reference to <code>transaction</code>'s DAO.
     */
    protected com.algoTrader.entity.TransactionDao getTransactionDao()
    {
        return this.transactionDao;
    }

    private com.algoTrader.entity.PositionDao positionDao;

    /**
     * Sets the reference to <code>position</code>'s DAO.
     */
    public void setPositionDao(com.algoTrader.entity.PositionDao positionDao)
    {
        this.positionDao = positionDao;
    }

    /**
     * Gets the reference to <code>position</code>'s DAO.
     */
    protected com.algoTrader.entity.PositionDao getPositionDao()
    {
        return this.positionDao;
    }

    private com.algoTrader.entity.StrategyDao strategyDao;

    /**
     * Sets the reference to <code>strategy</code>'s DAO.
     */
    public void setStrategyDao(com.algoTrader.entity.StrategyDao strategyDao)
    {
        this.strategyDao = strategyDao;
    }

    /**
     * Gets the reference to <code>strategy</code>'s DAO.
     */
    protected com.algoTrader.entity.StrategyDao getStrategyDao()
    {
        return this.strategyDao;
    }

    private com.algoTrader.entity.WatchListItemDao watchListItemDao;

    /**
     * Sets the reference to <code>watchListItem</code>'s DAO.
     */
    public void setWatchListItemDao(com.algoTrader.entity.WatchListItemDao watchListItemDao)
    {
        this.watchListItemDao = watchListItemDao;
    }

    /**
     * Gets the reference to <code>watchListItem</code>'s DAO.
     */
    protected com.algoTrader.entity.WatchListItemDao getWatchListItemDao()
    {
        return this.watchListItemDao;
    }

    /**
     * @see com.algoTrader.service.SimulationService#resetDB()
     */
    public void resetDB()
    {
        try
        {
            this.handleResetDB();
        }
        catch (Throwable th)
        {
            throw new com.algoTrader.service.SimulationServiceException(
                "Error performing 'com.algoTrader.service.SimulationService.resetDB()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #resetDB()}
      */
    protected abstract void handleResetDB()
        throws java.lang.Exception;

    /**
     * @see com.algoTrader.service.SimulationService#inputCSV()
     */
    public void inputCSV()
    {
        try
        {
            this.handleInputCSV();
        }
        catch (Throwable th)
        {
            throw new com.algoTrader.service.SimulationServiceException(
                "Error performing 'com.algoTrader.service.SimulationService.inputCSV()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #inputCSV()}
      */
    protected abstract void handleInputCSV()
        throws java.lang.Exception;

    /**
     * @see com.algoTrader.service.SimulationService#runByUnderlayings()
     */
    public com.algoTrader.vo.SimulationResultVO runByUnderlayings()
    {
        try
        {
            return this.handleRunByUnderlayings();
        }
        catch (Throwable th)
        {
            throw new com.algoTrader.service.SimulationServiceException(
                "Error performing 'com.algoTrader.service.SimulationService.runByUnderlayings()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #runByUnderlayings()}
      */
    protected abstract com.algoTrader.vo.SimulationResultVO handleRunByUnderlayings()
        throws java.lang.Exception;

    /**
     * @see com.algoTrader.service.SimulationService#simulateWithCurrentParams()
     */
    public void simulateWithCurrentParams()
    {
        try
        {
            this.handleSimulateWithCurrentParams();
        }
        catch (Throwable th)
        {
            throw new com.algoTrader.service.SimulationServiceException(
                "Error performing 'com.algoTrader.service.SimulationService.simulateWithCurrentParams()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #simulateWithCurrentParams()}
      */
    protected abstract void handleSimulateWithCurrentParams()
        throws java.lang.Exception;

    /**
     * @see com.algoTrader.service.SimulationService#getSimulationResultVO(long)
     */
    public com.algoTrader.vo.SimulationResultVO getSimulationResultVO(long startTime)
    {
        try
        {
            return this.handleGetSimulationResultVO(startTime);
        }
        catch (Throwable th)
        {
            throw new com.algoTrader.service.SimulationServiceException(
                "Error performing 'com.algoTrader.service.SimulationService.getSimulationResultVO(long startTime)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getSimulationResultVO(long)}
      */
    protected abstract com.algoTrader.vo.SimulationResultVO handleGetSimulationResultVO(long startTime)
        throws java.lang.Exception;

    /**
     * Gets the current <code>principal</code> if one has been set,
     * otherwise returns <code>null</code>.
     *
     * @return the current principal
     */
    protected java.security.Principal getPrincipal()
    {
        return com.algoTrader.PrincipalStore.get();
    }
}