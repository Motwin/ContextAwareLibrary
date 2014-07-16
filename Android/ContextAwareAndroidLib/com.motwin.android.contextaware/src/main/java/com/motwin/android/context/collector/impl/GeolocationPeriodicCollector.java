/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractPeriodicCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.model.GeoLocData;
import com.motwin.android.context.utils.LocationHelper;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Periodic geolocation collector
 */
public class GeolocationPeriodicCollector extends AbstractPeriodicCollector<GeoLocData> {
    private final LocationManager locationManager;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aDelay
     *            The delay between location updates
     */
    public GeolocationPeriodicCollector(ClientChannel aClientChannel, Context aApplicationContext, long aDelay) {
        this(aClientChannel, aApplicationContext, aDelay, (LocationManager) aApplicationContext
                .getSystemService(Context.LOCATION_SERVICE));
    }

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aDelay
     *            The delay between location updates
     * @param aLocationManager
     *            The location manager
     */
    protected GeolocationPeriodicCollector(ClientChannel aClientChannel, Context aApplicationContext, long aDelay,
            LocationManager aLocationManager) {
        super(aClientChannel, aApplicationContext, aDelay);
        locationManager = Preconditions
                .checkNotNull(
                        aLocationManager,
                        "aLocationManager cannot be null. Check that permission \"android.permission.ACCESS_FINE_LOCATION\" is added to your manifest.");

        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Preconditions.checkState(isNetworkEnabled || isGpsEnabled,
                "Unable to get GeoLocation. Check that you have the expected permissions");
    }

    @Override
    public ContextElement<GeoLocData> collect() {
        Location currentLocation = retrieveCurentLocation();

        GeoLocData geolocationData = null;
        if (currentLocation != null) {
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            long timestamp = currentLocation.getTime();
            float accuracy = currentLocation.getAccuracy();
            float bearing = currentLocation.getBearing();
            float spead = currentLocation.getSpeed();
            double altitude = currentLocation.getAltitude();

            geolocationData = new GeoLocData(longitude, latitude, timestamp, altitude, spead, bearing, accuracy);

        }
        ContextElement<GeoLocData> contextEelement = new ContextElement<GeoLocData>(
                ContextAware.GEOLCATION_PERIODIC_KEY, geolocationData);
        return contextEelement;
    }

    @Override
    public void onStart() {
        // NOOP
    }

    @Override
    public void onStop() {
        // NOOP
    }

    private Location retrieveCurentLocation() {
        Location gpsLocation = null;
        Location networkLocation = null;
        networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Location result;
        result = LocationHelper.chooseBetterLocation(networkLocation, gpsLocation);
        return result;
    }

}
