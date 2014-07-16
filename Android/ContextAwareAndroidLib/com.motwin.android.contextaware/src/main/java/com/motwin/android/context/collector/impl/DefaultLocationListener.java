package com.motwin.android.context.collector.impl;

import android.location.Location;
import android.os.Bundle;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.Collector;
import com.motwin.android.context.collector.CollectorLocationListener;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.model.GeoLocData;
import com.motwin.android.context.utils.LocationHelper;

/**
 * Default Location Listener
 * 
 */
public class DefaultLocationListener implements CollectorLocationListener {

    private Collector<GeoLocData> collector;
    private Location              currentLocation;

    @Override
    public void onLocationChanged(Location aLocation) {
        currentLocation = LocationHelper.chooseBetterLocation(currentLocation, aLocation);

        if (currentLocation != null) {
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            long timestamp = currentLocation.getTime();
            double accuracy = currentLocation.getAccuracy();
            double bearing = currentLocation.getBearing();
            double spead = currentLocation.getSpeed();
            double altitude = currentLocation.getAltitude();

            GeoLocData geolocationData = new GeoLocData(longitude, latitude, timestamp, altitude, spead, bearing,
                    accuracy);

            ContextElement<GeoLocData> contextEelement = new ContextElement<GeoLocData>(ContextAware.GEOLOCATION_KEY,
                    geolocationData);

            collector.sendContextElement(contextEelement);
        }

    }

    @Override
    public void onStatusChanged(String aProvider, int aStatus, Bundle aExtras) {
        // NOOP
    }

    @Override
    public void onProviderEnabled(String aProvider) {
        // NOOP
    }

    @Override
    public void onProviderDisabled(String aProvider) {
        // NOOP
    }

    @Override
    public void registerCollector(Collector<GeoLocData> aCollector) {
        collector = Preconditions.checkNotNull(aCollector, "aCollector cannot be null");

    }

}
