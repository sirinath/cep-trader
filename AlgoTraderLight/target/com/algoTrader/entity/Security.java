// license-header java merge-point
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntity.vsl in andromda-hibernate-cartridge.
//
package com.algoTrader.entity;

/**
 * 
 */
public abstract class Security
    extends com.algoTrader.BaseObject
    implements java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -5368535603373926222L;

    private java.lang.String isin;

    /**
     * 
     */
    public java.lang.String getIsin()
    {
        return this.isin;
    }

    public void setIsin(java.lang.String isin)
    {
        this.isin = isin;
    }

    private java.lang.String symbol;

    /**
     * 
     */
    public java.lang.String getSymbol()
    {
        return this.symbol;
    }

    public void setSymbol(java.lang.String symbol)
    {
        this.symbol = symbol;
    }

    private int id;

    /**
     * 
     */
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    private com.algoTrader.entity.Security underlaying;

    /**
     * 
     */
    public com.algoTrader.entity.Security getUnderlaying()
    {
        return this.underlaying;
    }

    public void setUnderlaying(com.algoTrader.entity.Security underlaying)
    {
        this.underlaying = underlaying;
    }

    private java.util.Collection positions = new java.util.HashSet();

    /**
     * 
     */
    public java.util.Collection getPositions()
    {
        return this.positions;
    }

    public void setPositions(java.util.Collection positions)
    {
        this.positions = positions;
    }

    private com.algoTrader.entity.SecurityFamily securityFamily;

    /**
     * 
     */
    public com.algoTrader.entity.SecurityFamily getSecurityFamily()
    {
        return this.securityFamily;
    }

    public void setSecurityFamily(com.algoTrader.entity.SecurityFamily securityFamily)
    {
        this.securityFamily = securityFamily;
    }

    private java.util.Collection securityFamilies = new java.util.HashSet();

    /**
     * 
     */
    public java.util.Collection getSecurityFamilies()
    {
        return this.securityFamilies;
    }

    public void setSecurityFamilies(java.util.Collection securityFamilies)
    {
        this.securityFamilies = securityFamilies;
    }

    private java.util.Collection watchListItems = new java.util.HashSet();

    /**
     * 
     */
    public java.util.Collection getWatchListItems()
    {
        return this.watchListItems;
    }

    public void setWatchListItems(java.util.Collection watchListItems)
    {
        this.watchListItems = watchListItems;
    }

    /**
     * 
     */
    public abstract boolean isOnWatchlist();

    /**
     * 
     */
    public abstract com.algoTrader.entity.Tick getLastTick();

    /**
     * 
     */
    public abstract double getMargin();

    /**
     * Returns <code>true</code> if the argument is an Security instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Security))
        {
            return false;
        }
        final Security that = (Security)object;
        if (this.id != that.getId())
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code based on this entity's identifiers.
     */
    public int hashCode()
    {
        int hashCode = 0;
        hashCode = 29 * hashCode + (int)id;

        return hashCode;
    }

    /**
     * Constructs new instances of {@link com.algoTrader.entity.Security}.
     */
    public static final class Factory
    {
        /**
         * Constructs a new instance of {@link com.algoTrader.entity.Security}.
         */
        public static com.algoTrader.entity.Security newInstance()
        {
            return new com.algoTrader.entity.SecurityImpl();
        }

        /**
         * Constructs a new instance of {@link com.algoTrader.entity.Security}, taking all required and/or
         * read-only properties as arguments.
         */
        public static com.algoTrader.entity.Security newInstance(java.lang.String isin, com.algoTrader.entity.SecurityFamily securityFamily)
        {
            final com.algoTrader.entity.Security entity = new com.algoTrader.entity.SecurityImpl();
            entity.setIsin(isin);
            entity.setSecurityFamily(securityFamily);
            return entity;
        }

        /**
         * Constructs a new instance of {@link com.algoTrader.entity.Security}, taking all possible properties
         * (except the identifier(s))as arguments.
         */
        public static com.algoTrader.entity.Security newInstance(java.lang.String isin, java.lang.String symbol, com.algoTrader.entity.Security underlaying, java.util.Collection positions, com.algoTrader.entity.SecurityFamily securityFamily, java.util.Collection securityFamilies, java.util.Collection watchListItems)
        {
            final com.algoTrader.entity.Security entity = new com.algoTrader.entity.SecurityImpl();
            entity.setIsin(isin);
            entity.setSymbol(symbol);
            entity.setUnderlaying(underlaying);
            entity.setPositions(positions);
            entity.setSecurityFamily(securityFamily);
            entity.setSecurityFamilies(securityFamilies);
            entity.setWatchListItems(watchListItems);
            return entity;
        }
    }

// HibernateEntity.vsl merge-point
}