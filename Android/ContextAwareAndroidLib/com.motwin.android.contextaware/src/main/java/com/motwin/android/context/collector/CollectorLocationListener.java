package com.motwin.android.context.collector;

import android.location.LocationListener;

import com.motwin.android.context.model.GeoLocData;

/**
 * Interface for custom location listener. It extends the
 * {@link android.location.LocationListener}
 * 
 * Default implementation :
 * {@link com.motwin.android.context.collector.impl.DefaultLocationListener}
 * 
 */
public interface CollectorLocationListener extends LocationListener {

    /**
     * Registers the Collector. This method allows the location listener to
     * register the instance of Collector that must be called when location
     * changes significantly.
     * 
     * @param aCollector
     *            The Geolocation Collector.
     */
    void registerCollector(Collector<GeoLocData> aCollector);

}
