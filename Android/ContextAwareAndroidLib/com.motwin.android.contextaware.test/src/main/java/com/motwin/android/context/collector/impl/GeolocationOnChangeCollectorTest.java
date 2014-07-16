/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.Mock;

import android.location.Location;
import android.location.LocationManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.CollectorLocationListener;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.model.GeoLocData;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class GeolocationOnChangeCollectorTest extends MockitoTestCase {

    private SpyChannel                   channel;
    private GeolocationOnChangeCollector collector;
    @Mock
    private LocationManager              locationManager;
    @Mock
    private Location                     location;
    @Mock
    private CollectorLocationListener    locationListener;

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

    public void testNetworkEnabled() {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationOnChangeCollector(channel, getContext(), 100, 100, locationListener, locationManager);

        assertTrue(collector.isNetworkEnabled());
        assertFalse(collector.isGpsEnabled());

    }

    public void testGpsEnabled() {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(false);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(true);
        collector = new GeolocationOnChangeCollector(channel, getContext(), 100, 100, locationListener, locationManager);

        assertFalse(collector.isNetworkEnabled());
        assertTrue(collector.isGpsEnabled());

    }

    public void testOnStartRegisterListenerOnNetworkLocation() throws InterruptedException {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationOnChangeCollector(channel, getContext(), 100, 100, locationListener, locationManager);

        when(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)).thenReturn(location);
        when(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(null);
        collector.start();

        verify(locationManager).requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 100, locationListener);
    }

    public void testOnStartRegisterListenerOnGpsLocation() throws InterruptedException {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationOnChangeCollector(channel, getContext(), 100, 100, locationListener, locationManager);

        when(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)).thenReturn(null);
        when(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(location);
        collector.start();

        verify(locationManager).requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 100, locationListener);

    }

    public void testThrowExceptionOnStartIfSensorNotSupported() {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(false);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);

        try {
            collector = new GeolocationOnChangeCollector(channel, getContext(), 100, 100, locationListener,
                    locationManager);
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
            // expected exception
        }
    }

    public void testRemoveListenerOnStop() {
        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationOnChangeCollector(channel, getContext(), 100, 100, locationListener, locationManager);
        collector.stop();

        verify(locationManager).removeUpdates(locationListener);
    }

    public void testForwardContextElementOnLocationChanged() throws InterruptedException {
        DefaultLocationListener defaultLocationListener;
        defaultLocationListener = new DefaultLocationListener();

        when(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
        when(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        collector = new GeolocationOnChangeCollector(channel, getContext(), 100, 100, defaultLocationListener,
                locationManager);

        defaultLocationListener.onLocationChanged(location);

        GeoLocData expectedData = new GeoLocData(longitude, latitude, timestamp, altitude, speed, bearing, accuracy);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        ContextElement<?> messageContent = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.GEOLOCATION_KEY, ((ContextElement<?>) messageContent).getType());
        Object contextData = ((ContextElement<?>) messageContent).getData();
        assertTrue(contextData instanceof GeoLocData);
        assertEquals(expectedData, contextData);
    }
}
