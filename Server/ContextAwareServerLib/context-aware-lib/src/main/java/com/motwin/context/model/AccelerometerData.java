/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.model;

import java.io.Serializable;

/**
 * The Class AccelerometerData.
 * 
 * The AccelerometerData is the data model class that stores information about
 * the mobile Accelerometer.
 * 
 */
public class AccelerometerData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7249221429549912113L;

    /** The x. */
    private double            x;

    /** The y. */
    private double            y;

    /** The z. */
    private double            z;

    /**
     * Gets the x.
     * 
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x.
     * 
     * @param aX
     *            the x to set
     */
    public void setX(double aX) {
        x = aX;
    }

    /**
     * Gets the y.
     * 
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y.
     * 
     * @param aY
     *            the y to set
     */
    public void setY(double aY) {
        y = aY;
    }

    /**
     * Gets the z.
     * 
     * @return the z
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the z.
     * 
     * @param aZ
     *            the z to set
     */
    public void setZ(double aZ) {
        z = aZ;
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
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        AccelerometerData other = (AccelerometerData) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
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
        return "AccelerometerData [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

}
