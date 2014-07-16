/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.model;

/**
 * The Class MobileContextElement.
 * 
 * The ContextElement is the exchange datamodel between the server and the
 * mobile.
 * 
 * @param <T>
 *            this parameter will specify the type of contextElementData
 * 
 */
public class MobileContextElement<T> extends ContextElement<T> {

    /**
     * 
     */
    private static final long serialVersionUID = -1691568183499592842L;

    /** The installation id. */
    private String            installationId;

    /**
     * Gets the installation id.
     * 
     * @return String the installationId
     */
    public String getInstallationId() {
        return installationId;
    }

    /**
     * Sets the installation id.
     * 
     * @param aInstallationId
     *            the installationId to set
     */
    public void setInstallationId(String aInstallationId) {
        installationId = aInstallationId;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((installationId == null) ? 0 : installationId.hashCode());
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
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MobileContextElement<?> other = (MobileContextElement<?>) obj;
        if (installationId == null) {
            if (other.installationId != null)
                return false;
        } else if (!installationId.equals(other.installationId))
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
        return "MobileContextElement [installationId=" + installationId + ", type=" + getType() + ", data=" + getData()
            + "]";
    }

    /**
     * Copy of.
     * 
     * @param <T>
     *            the generic type
     * @param aContextElement
     *            the context element
     * @return MobileContextElement<T> the mobile context element
     */
    public static <T> MobileContextElement<T> copyOf(ContextElement<T> aContextElement) {
        MobileContextElement<T> element;
        element = new MobileContextElement<T>();
        element.setType(aContextElement.getType());
        element.setData(aContextElement.getData());
        return element;
    }

}
