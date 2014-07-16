/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.model;

import java.io.Serializable;

/**
 * The Class GeoLocData.<br>
 * 
 * <p>
 * The GeoLocData is the data model class which contains information about the
 * device geographic coordinates (latitude & longitude) and the timestamp when
 * these coordinates where collected.
 * 
 * 
 */
public class GeoLocData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 368001778186908628L;

    /** The latitude. */
    private double            latitude;

    /** The longitude. */
    private double            longitude;

    /** The timestamp. */
    private long              timestamp;

    /** The altitude. */
    private double            altitude;

    /** The speed. */
    private double            speed;

    /** The bearing. */
    private double            bearing;

    /** The accuracy. */
    private double            accuracy;

    /**
     * Gets the latitude.
     * 
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude.
     * 
     * @param aLatitude
     *            the latitude to set
     */
    public void setLatitude(double aLatitude) {
        latitude = aLatitude;
    }

    /**
     * Gets the longitude.
     * 
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude.
     * 
     * @param aLongitude
     *            the longitude to set
     */
    public void setLongitude(double aLongitude) {
        longitude = aLongitude;
    }

    /**
     * Gets the timestamp.
     * 
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     * 
     * @param aTimestamp
     *            the timestamp to set
     */
    public void setTimestamp(long aTimestamp) {
        timestamp = aTimestamp;
    }

    /**
     * Gets the altitude.
     * 
     * @return the altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Sets the altitude.
     * 
     * @param aAltitude
     *            the altitude to set
     */
    public void setAltitude(double aAltitude) {
        altitude = aAltitude;
    }

    /**
     * Gets the speed.
     * 
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed.
     * 
     * @param aSpeed
     *            the speed to set
     */
    public void setSpeed(double aSpeed) {
        speed = aSpeed;
    }

    /**
     * Gets the bearing.
     * 
     * @return the bearing
     */
    public double getBearing() {
        return bearing;
    }

    /**
     * Sets the bearing.
     * 
     * @param aBearing
     *            the bearing to set
     */
    public void setBearing(double aBearing) {
        bearing = aBearing;
    }

    /**
     * Gets the accuracy.
     * 
     * @return the accuracy
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the accuracy.
     * 
     * @param aAccuracy
     *            the accuracy to set
     */
    public void setAccuracy(double aAccuracy) {
        accuracy = aAccuracy;
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
        temp = Double.doubleToLongBits(accuracy);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(altitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(bearing);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(speed);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
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
        GeoLocData other = (GeoLocData) obj;
        if (Double.doubleToLongBits(accuracy) != Double.doubleToLongBits(other.accuracy))
            return false;
        if (Double.doubleToLongBits(altitude) != Double.doubleToLongBits(other.altitude))
            return false;
        if (Double.doubleToLongBits(bearing) != Double.doubleToLongBits(other.bearing))
            return false;
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
            return false;
        if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
            return false;
        if (Double.doubleToLongBits(speed) != Double.doubleToLongBits(other.speed))
            return false;
        if (timestamp != other.timestamp)
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
        return "GeoLocData [latitude=" + latitude + ", longitude=" + longitude + ", timestamp=" + timestamp
            + ", altitude=" + altitude + ", speed=" + speed + ", bearing=" + bearing + ", accuracy=" + accuracy + "]";
    }

}
