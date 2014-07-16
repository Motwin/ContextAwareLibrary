/**
 * 
 */
package com.motwin.android.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.Thread.UncaughtExceptionHandler;

import org.mockito.Mock;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.collector.AbstractOneShotCollector;
import com.motwin.android.context.collector.AbstractPeriodicCollector;
import com.motwin.android.context.collector.Collector;
import com.motwin.android.context.collector.CollectorLocationListener;
import com.motwin.android.context.collector.impl.AccelerometerOnChangeCollector;
import com.motwin.android.context.collector.impl.BatteryLevelPeriodicCollector;
import com.motwin.android.context.collector.impl.ChargerTypeCollector;
import com.motwin.android.context.collector.impl.ChargingStateCollector;
import com.motwin.android.context.collector.impl.CrashEventCollector;
import com.motwin.android.context.collector.impl.DefaultLocationListener;
import com.motwin.android.context.collector.impl.DeviceModelCollector;
import com.motwin.android.context.collector.impl.DockStateCollector;
import com.motwin.android.context.collector.impl.DockTypeCollector;
import com.motwin.android.context.collector.impl.GeolocationOnChangeCollector;
import com.motwin.android.context.collector.impl.GeolocationPeriodicCollector;
import com.motwin.android.context.collector.impl.LanguageCollector;
import com.motwin.android.context.collector.impl.MobileCountryCodeCollector;
import com.motwin.android.context.collector.impl.MobileNetworkCodeCollector;
import com.motwin.android.context.collector.impl.NetworkIdentifierCollector;
import com.motwin.android.context.collector.impl.NetworkReceptionCollector;
import com.motwin.android.context.collector.impl.NetworkTypeCollector;
import com.motwin.android.context.collector.impl.RoamingCollector;
import com.motwin.android.context.collector.impl.RoamingCountryCodeCollector;
import com.motwin.android.context.collector.impl.ScreenOrientationCollector;
import com.motwin.android.context.collector.impl.SimStateCollector;
import com.motwin.android.context.collector.impl.SoundModeCollector;
import com.motwin.android.context.collector.impl.SoundOutputCollector;
import com.motwin.android.context.collector.impl.TagCollector;
import com.motwin.android.context.collector.impl.TimeZoneCollector;
import com.motwin.android.context.model.GeoLocData;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;

/**
 * @author lorie
 * 
 */
public class CollectorsTest extends MockitoTestCase {

    private SpyChannel      channel;
    @Mock
    private Context         mockContext;
    @Mock
    private LocationManager mockLocationManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        channel = new SpyChannel();
        when(mockContext.getSystemService(Context.LOCATION_SERVICE)).thenReturn(mockLocationManager);
        when(mockLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);

    }

    public void testNewChargerTypeCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newChargerTypeCollector(channel, getContext(), 100);

        assertTrue(collector instanceof ChargerTypeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewChargingStateCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newChargingStateCollector(channel, getContext(), 100);

        assertTrue(collector instanceof ChargingStateCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewCrashEventCollector() {
        UncaughtExceptionHandler handler = mock(UncaughtExceptionHandler.class);
        AbstractOnChangeCollector<?> collector = Collectors.newCrashEventCollector(channel, getContext(), handler);

        assertTrue(collector instanceof CrashEventCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(0, collector.getMinDelay());
        assertEquals(handler, ((CrashEventCollector) collector).getExceptionHandler());
    }

    public void testNewDockStateCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newDockStateCollector(channel, getContext(), 100);

        assertTrue(collector instanceof DockStateCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewDockTypeCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newDockTypeCollector(channel, getContext(), 100);

        assertTrue(collector instanceof DockTypeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewGeolocationOnChangeCollectorWithDefaultListener() {
        AbstractOnChangeCollector<?> collector = Collectors.newGeolocationOnChangeCollector(channel, mockContext, 50,
                100);

        assertTrue(collector instanceof GeolocationOnChangeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(mockContext, collector.getApplicationContext());
        assertEquals(50, collector.getMinDelay());
        assertEquals(100f, ((GeolocationOnChangeCollector) collector).getMinDistance());
        assertTrue(((GeolocationOnChangeCollector) collector).getLocationListener() instanceof DefaultLocationListener);
    }

    public void testNewGeolocationOnChangeCollector() {
        CollectorLocationListener listener = new CollectorLocationListener() {

            @Override
            public void onStatusChanged(String aProvider, int aStatus, Bundle aExtras) {
            }

            @Override
            public void onProviderEnabled(String aProvider) {
            }

            @Override
            public void onProviderDisabled(String aProvider) {
            }

            @Override
            public void onLocationChanged(Location aLocation) {
            }

            @Override
            public void registerCollector(Collector<GeoLocData> aCollector) {
            }
        };
        AbstractOnChangeCollector<?> collector = Collectors.newGeolocationOnChangeCollector(channel, mockContext, 50,
                100, listener);

        assertTrue(collector instanceof GeolocationOnChangeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(mockContext, collector.getApplicationContext());
        assertEquals(50, collector.getMinDelay());
        assertEquals(100f, ((GeolocationOnChangeCollector) collector).getMinDistance());
        assertEquals(listener, ((GeolocationOnChangeCollector) collector).getLocationListener());
    }

    public void testNewNetworkIdentifierCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newNetworkIdentifierCollector(channel, getContext());

        assertTrue(collector instanceof NetworkIdentifierCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewNetworkReceptionCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newNetworkReceptionCollector(channel, getContext(), 100);

        assertTrue(collector instanceof NetworkReceptionCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewNetworkTypeCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newNetworkTypeCollector(channel, getContext(), 100);

        assertTrue(collector instanceof NetworkTypeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewScreenOrientationCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newScreenOrientationCollector(channel, getContext(), 100);

        assertTrue(collector instanceof ScreenOrientationCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewSoundModeCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newSoundModeCollector(channel, getContext(), 100);

        assertTrue(collector instanceof SoundModeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewSoundOutputCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newSoundOutputCollector(channel, getContext(), 100);

        assertTrue(collector instanceof SoundOutputCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewTagCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newTagCollector(channel, getContext());

        assertTrue(collector instanceof TagCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(0, collector.getMinDelay());
    }

    public void testNewDeviceTypeCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newDeviceModelCollector(channel, getContext());

        assertTrue(collector instanceof DeviceModelCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewLanguageCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newLanguageCollector(channel, getContext());

        assertTrue(collector instanceof LanguageCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewMobileCountryCodeCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newMobileCountryCodeCollector(channel, getContext());

        assertTrue(collector instanceof MobileCountryCodeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewMobileNetworkCodeCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newMobileNetworkCodeCollector(channel, getContext());

        assertTrue(collector instanceof MobileNetworkCodeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewRoamingCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newRoamingCollector(channel, getContext());

        assertTrue(collector instanceof RoamingCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewRoamingCountryCodeCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newRoamingCountryCodeCollector(channel, getContext());

        assertTrue(collector instanceof RoamingCountryCodeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewSimStateCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newSimStateCollector(channel, getContext());

        assertTrue(collector instanceof SimStateCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewTimeZoneCollector() {
        AbstractOneShotCollector<?> collector = Collectors.newTimeZoneCollector(channel, getContext());

        assertTrue(collector instanceof TimeZoneCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewAccelerometerOnChangeCollector() {
        AbstractOnChangeCollector<?> collector = Collectors.newAccelerometerOnChangeCollector(channel, getContext(),
                100);

        assertTrue(collector instanceof AccelerometerOnChangeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewBatteryLevelPeriodicCollector() {
        AbstractPeriodicCollector<?> collector = Collectors
                .newBatteryLevelPeriodicCollector(channel, getContext(), 100);

        assertTrue(collector instanceof BatteryLevelPeriodicCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getDelay());
    }

    public void testNewGeolocationPeriodicCollector() {
        AbstractPeriodicCollector<?> collector = Collectors.newGeolocationPeriodicCollector(channel, mockContext, 100);

        assertTrue(collector instanceof GeolocationPeriodicCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(mockContext, collector.getApplicationContext());
        assertEquals(100, collector.getDelay());
    }

}
