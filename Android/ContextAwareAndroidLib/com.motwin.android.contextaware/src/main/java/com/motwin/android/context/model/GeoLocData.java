/**
 * 
 */
package com.motwin.android.context.model;

/**
 * Geolocation data. <br>
 * This context element data is composed of
 * <ul>
 * <li>longitude</li>
 * <li>latitude</li>
 * <li>timestamp</li>
 * <li>altitude</li>
 * <li>speed</li>
 * <li>bearing</li>
 * <li>accuracy</li>
 * </ul>
 * 
 */
public class GeoLocData {

    private double longitude;
    private double latitude;
    private long   timestamp;
    private double altitude;
    private double speed;
    private double bearing;
    private double accuracy;

    /**
     * Constructor
     * 
     * @param aLongitude
     *            The longitude
     * @param aLatitude
     *            The latitude
     * @param aTimestamp
     *            The timestamp
     */
    public GeoLocData(double aLongitude, double aLatitude, long aTimestamp) {
        longitude = aLongitude;
        latitude = aLatitude;
        timestamp = aTimestamp;
    }

    /**
     * Constructor
     * 
     * @param aLongitude
     * @param aLatitude
     * @param aTimestamp
     * @param aAltitue
     * @param aSpeed
     * @param aBearing
     * @param aAccuracy
     */
    public GeoLocData(double aLongitude, double aLatitude, long aTimestamp, double aAltitue, double aSpeed,
            double aBearing, double aAccuracy) {
        longitude = aLongitude;
        latitude = aLatitude;
        timestamp = aTimestamp;
        altitude = aAltitue;
        speed = aSpeed;
        bearing = aBearing;
        accuracy = aAccuracy;
    }

    /**
     * Get longitude
     * 
     * @return The longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set longitude
     * 
     * @param aLongitude
     *            The longitude
     */
    public void setLongitude(double aLongitude) {
        longitude = aLongitude;
    }

    /**
     * Get latitude
     * 
     * @return The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set latitude
     * 
     * @param aLatitude
     *            The latitude
     */
    public void setLatitude(double aLatitude) {
        latitude = aLatitude;
    }

    /**
     * Get timestamp
     * 
     * @return The timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Set timestamp
     * 
     * @param aTimestamp
     *            The timestamp
     */
    public void setTimestamp(long aTimestamp) {
        timestamp = aTimestamp;
    }

    /**
     * Get altitude
     * 
     * @return The altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Set altitude
     * 
     * @param aAltitude
     *            The altitude
     */
    public void setAltitude(double aAltitude) {
        altitude = aAltitude;
    }

    /**
     * Get speed
     * 
     * @return The speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Set speed
     * 
     * @param aSpeed
     *            The speed
     */
    public void setSpeed(double aSpeed) {
        speed = aSpeed;
    }

    /**
     * Get bearing
     * 
     * @return The bearing
     */
    public double getBearing() {
        return bearing;
    }

    /**
     * Set bearing
     * 
     * @param aBearing
     *            The bearing
     */
    public void setBearing(double aBearing) {
        bearing = aBearing;
    }

    /**
     * Get accuracy
     * 
     * @return The accuracy
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * Set accuracy
     * 
     * @param aAccuracy
     *            The accuracy
     */
    public void setAccuracy(double aAccuracy) {
        accuracy = aAccuracy;
    }

    @Override
    public String toString() {
        return "GeoLocData [longitude=" + longitude + ", latitude=" + latitude + ", timestamp=" + timestamp
            + ", altitude=" + altitude + ", speed=" + speed + ", bearing=" + bearing + ", accuracy=" + accuracy + "]";
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

}
