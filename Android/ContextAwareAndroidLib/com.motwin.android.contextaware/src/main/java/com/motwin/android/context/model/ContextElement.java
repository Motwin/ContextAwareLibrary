/**
 * 
 */
package com.motwin.android.context.model;

import com.google.common.base.Preconditions;

/**
 * Context Element <br>
 * This will be sent to the server to report context events. A context element
 * is composed of the type of context, and the data.
 * 
 * @param <T>
 *            The type of context data
 * 
 */
public class ContextElement<T> {
    private String type;
    private T      data;

    /**
     * Constructor
     */
    public ContextElement() {

    }

    /**
     * Constructor
     * 
     * @param aType
     *            The type of context element
     * @param aData
     *            The value of context data
     */
    public ContextElement(String aType, T aData) {
        type = Preconditions.checkNotNull(aType, "aType cannot be null");
        data = Preconditions.checkNotNull(aData, "aData cannot be null");
    }

    /**
     * Get type
     * 
     * @return The type of the context element
     */
    public String getType() {
        return type;
    }

    /**
     * Set type
     * 
     * @param aType
     *            The type of the context element
     */
    public void setType(String aType) {
        type = Preconditions.checkNotNull(aType, "aType cannot be null");
    }

    /**
     * Get data
     * 
     * @return The context element data
     */
    public T getData() {
        return data;
    }

    /**
     * Set data
     * 
     * @param aData
     *            The context element data
     */
    public void setData(T aData) {
        data = Preconditions.checkNotNull(aData, "aData cannot be null");
    }

    @Override
    public String toString() {
        return "ContextElement [type=" + type + ", data=" + data + "]";
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

}
