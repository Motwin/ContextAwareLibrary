/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static org.mockito.Mockito.when;

import org.mockito.Mock;

import android.location.Location;
import android.location.LocationManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.model.GeoLocData;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;

/**
 * @author lorie
 * 
 */
public class GeolocationPeriodicCollectorTest extends MockitoTestCase {

    private SpyChannel                   channel;
    private GeolocationPeriodicCollector collector;
    @Mock
    private LocationManager              locationManager;
    @Mock
    private Location                     location;

    private double                       longitude;
    private double                       latitude;
    private long                         timestamp;
    private double                       altitude;
    private float                        speed;
    private float                        bearing;
    private float                        accuracy;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();

        longitude = 1.1;
        latitude = 2.2;
        timestamp = 1234L;
        altitude = 3.3;
        speed = 4.4f;
        bearing = 5.5f;
        accuracy = 6.6f;

        when(location.getLatitude()).thenReturn(latitude);
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getTime()).thenReturn(timestamp);
        when(location.getAccuracy()).thenReturn(accuracy);
        when(location.getBearing()).thenReturn(bearing);
        when(location.getSpeed()).thenReturn(speed);
        when(location.getAltitude()).thenReturn(altitude);

    }

    public void testCanInitWithNetworkEnabled() {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationPeriodicCollector(channel, getContext(), 100, locationManager);

    }

    public void testCanInitWithGpsEnabled() {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationPeriodicCollector(channel, getContext(), 100, locationManager);

    }

    public void testThrowExceptionOnInitIfSensorNotSupported() {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(false);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);

        try {
            collector = new GeolocationPeriodicCollector(channel, getContext(), 100, locationManager);
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
            // expected exception
        }
    }

    public void testCollectGivesGeoLocOnNetworkLocation() throws InterruptedException {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationPeriodicCollector(channel, getContext(), 100, locationManager);

        when(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)).thenReturn(location);
        when(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(null);

        ContextElement<GeoLocData> result = collector.collect();

        GeoLocData expectedData = new GeoLocData(longitude, latitude, timestamp, altitude, speed, bearing, accuracy);

        assertEquals(ContextAware.GEOLCATION_PERIODIC_KEY, result.getType());
        Object contextData = result.getData();
        assertTrue(contextData instanceof GeoLocData);
        assertEquals(expectedData, contextData);

    }

    public void testSendLocationOnStartAndRegisterListenerOnGpsLocation() throws InterruptedException {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationPeriodicCollector(channel, getContext(), 100, locationManager);

        when(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)).thenReturn(null);
        when(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(location);

        ContextElement<GeoLocData> result = collector.collect();

        GeoLocData expectedData = new GeoLocData(longitude, latitude, timestamp, altitude, speed, bearing, accuracy);

        assertEquals(ContextAware.GEOLCATION_PERIODIC_KEY, result.getType());
        Object contextData = result.getData();
        assertTrue(contextData instanceof GeoLocData);
        assertEquals(expectedData, contextData);

    }
}
