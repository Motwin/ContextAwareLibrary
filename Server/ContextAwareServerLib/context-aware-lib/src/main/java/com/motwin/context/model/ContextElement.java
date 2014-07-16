/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.model;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * The Class ContextElement.
 * 
 * The ContextElement is the exchange datamodel between the server and the
 * mobile.
 * 
 * @param <T>
 *            this parameter will specify the type of contextElementData
 */
public class ContextElement<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4747789194923453815L;

    /** The type. */
    private String            type;

    /** The data. */
    private T                 data;

    /**
     * Gets the type.
     * 
     * @return the contextElementType
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param aType
     *            the contextElementType to set
     */
    public void setType(String aType) {
        type = Preconditions.checkNotNull(aType, "aType cannot be null");

    }

    /**
     * Gets the data.
     * 
     * @return the contextElementData
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data.
     * 
     * @param aData
     *            the contextElementData to set
     */
    public void setData(T aData) {
        data = Preconditions.checkNotNull(aData, "aContextElementData cannot be null");

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContextElement<?> other = (ContextElement<?>) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ContextElement [type=" + type + ", data=" + data + "]";
    }

}
