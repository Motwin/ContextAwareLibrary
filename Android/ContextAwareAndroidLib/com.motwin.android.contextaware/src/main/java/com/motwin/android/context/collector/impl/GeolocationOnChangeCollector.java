/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;
import android.location.LocationManager;

import com.google.common.base.Preconditions;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.collector.CollectorLocationListener;
import com.motwin.android.context.model.GeoLocData;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Geolocation on change collector
 */
public class GeolocationOnChangeCollector extends AbstractOnChangeCollector<GeoLocData> {

    private final LocationManager           locationManager;
    private final CollectorLocationListener locationListener;
    private final boolean                   isNetworkEnabled;
    private final boolean                   isGpsEnabled;
    private final float                     minDistance;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between location updates
     * @param aMinDistance
     *            The minimum distance between location updates
     * @param aLocationListener
     *            The custom location listener
     */
    public GeolocationOnChangeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay,
            float aMinDistance, CollectorLocationListener aLocationListener) {
        this(aClientChannel, aApplicationContext, aMinDelay, aMinDistance, aLocationListener,
                (LocationManager) aApplicationContext.getSystemService(Context.LOCATION_SERVICE));
    }

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between location updates
     * @param aMinDistance
     *            The minimum distance between location updates
     * @param aLocationListener
     *            The custom location listener
     * @param aLocationManager
     *            The location manager
     */
    protected GeolocationOnChangeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay,
            float aMinDistance, CollectorLocationListener aLocationListener, LocationManager aLocationManager) {
        super(aClientChannel, aApplicationContext, aMinDelay);

        minDistance = aMinDistance;
        locationListener = Preconditions.checkNotNull(aLocationListener, "aLocationListener cannot be null");
        locationManager = Preconditions
                .checkNotNull(
                        aLocationManager,
                        "aLocationManager cannot be null. Check that permission \"android.permission.ACCESS_FINE_LOCATION\" is added to your manifest.");

        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        locationListener.registerCollector(this);

        Preconditions.checkState(isNetworkEnabled || isGpsEnabled,
                "Unable to get GeoLocation. Check that you have the expected permissions");

    }

    @Override
    public void start() {
        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, getMinDelay(), minDistance,
                    locationListener);
        }
        if (isGpsEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, getMinDelay(), minDistance,
                    locationListener);
        }
    }

    @Override
    public void stop() {
        locationManager.removeUpdates(locationListener);
    }

    /**
     * Is GPS enabled
     * 
     * @return true if GPS is enabled
     */
    protected boolean isGpsEnabled() {
        return isGpsEnabled;
    }

    /**
     * Is Network enabled
     * 
     * @return true if Network is enabled
     */
    protected boolean isNetworkEnabled() {
        return isNetworkEnabled;
    }

    /**
     * Get min distance between two location updates
     * 
     * @return The min distance between two location updates
     */
    public float getMinDistance() {
        return minDistance;
    }

    /**
     * Get the location listener
     * 
     * @return The location listener
     */
    public CollectorLocationListener getLocationListener() {
        return locationListener;
    }
}
