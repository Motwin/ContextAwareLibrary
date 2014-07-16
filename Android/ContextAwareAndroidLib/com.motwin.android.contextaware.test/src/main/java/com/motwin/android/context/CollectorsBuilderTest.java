/**
 * 
 */
package com.motwin.android.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import org.mockito.Mock;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

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
public class CollectorsBuilderTest extends MockitoTestCase {

    private SpyChannel         channel;
    private Collectors.Builder builder;
    @Mock
    private Context            mockContext;
    @Mock
    private LocationManager    mockLocationManager;
    @Mock
    private SensorManager      mockSensorManager;
    @Mock
    private TelephonyManager   mockTelephonyManager;
    @Mock
    private AudioManager       mockAudioManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        channel = new SpyChannel();
        builder = new Collectors.Builder(channel, mockContext);
        when(mockContext.getSystemService(Context.LOCATION_SERVICE)).thenReturn(mockLocationManager);
        when(mockContext.getSystemService(Context.SENSOR_SERVICE)).thenReturn(mockSensorManager);
        when(mockContext.getSystemService(Context.TELEPHONY_SERVICE)).thenReturn(mockTelephonyManager);
        when(mockContext.getSystemService(Context.AUDIO_SERVICE)).thenReturn(mockAudioManager);
        when(mockLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
    }

    @Override
    public Context getContext() {
        return mockContext;
    }

    public void testNewChargerTypeCollector() {
        builder.addChargerTypeCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);

        assertTrue(collector instanceof ChargerTypeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewChargingStateCollector() {
        builder.addChargingStateCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);

        assertTrue(collector instanceof ChargingStateCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewCrashEventCollector() {
        UncaughtExceptionHandler handler = mock(UncaughtExceptionHandler.class);
        builder.addCrashEventCollector(handler);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);

        assertTrue(collector instanceof CrashEventCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(0, collector.getMinDelay());
        assertEquals(handler, ((CrashEventCollector) collector).getExceptionHandler());
    }

    public void testNewDockStateCollector() {
        builder.addDockStateCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);

        assertTrue(collector instanceof DockStateCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewDockTypeCollector() {
        builder.addDockTypeCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);

        assertTrue(collector instanceof DockTypeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewGeolocationOnChangeCollectorWithDefaultListener() {
        builder.addGeolocationOnChangeCollector(50, 100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);
        assertTrue(collector instanceof GeolocationOnChangeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
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
        builder.addGeolocationOnChangeCollector(50, 100, listener);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);
        assertTrue(collector instanceof GeolocationOnChangeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(50, collector.getMinDelay());
        assertEquals(100f, ((GeolocationOnChangeCollector) collector).getMinDistance());
        assertEquals(listener, ((GeolocationOnChangeCollector) collector).getLocationListener());

    }

    public void testNewNetworkIdentifierCollector() {
        builder.addNetworkIdentifierCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);
        assertTrue(collector instanceof NetworkIdentifierCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewNetworkReceptionCollector() {
        builder.addNetworkReceptionCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);
        assertTrue(collector instanceof NetworkReceptionCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewNetworkTypeCollector() {
        builder.addNetworkTypeCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);
        assertTrue(collector instanceof NetworkTypeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewScreenOrientationCollector() {
        builder.addScreenOrientationCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);
        assertTrue(collector instanceof ScreenOrientationCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewSoundModeCollector() {
        builder.addSoundModeCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);
        assertTrue(collector instanceof SoundModeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewSoundOutputCollector() {
        builder.addSoundOutputCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);
        assertTrue(collector instanceof SoundOutputCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewTagCollector() {
        builder.addTagCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);
        assertTrue(collector instanceof TagCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(0, collector.getMinDelay());
    }

    public void testNewDeviceTypeCollector() {
        builder.addDeviceModelCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);

        assertTrue(collector instanceof DeviceModelCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewLanguageCollector() {
        builder.addLanguageCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);

        assertTrue(collector instanceof LanguageCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewMobileCountryCodeCollector() {
        builder.addMobileCountryCodeCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);

        assertTrue(collector instanceof MobileCountryCodeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewMobileNetworkCodeCollector() {
        builder.addMobileNetworkCodeCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);

        assertTrue(collector instanceof MobileNetworkCodeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewRoamingCollector() {
        builder.addRoamingCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);

        assertTrue(collector instanceof RoamingCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewRoamingCountryCodeCollector() {
        builder.addRoamingCountryCodeCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);

        assertTrue(collector instanceof RoamingCountryCodeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewSimStateCollector() {
        builder.addSimStateCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);

        assertTrue(collector instanceof SimStateCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewTimeZoneCollector() {
        builder.addTimeZoneCollector();

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOneShotCollector<?> collector = (AbstractOneShotCollector<?>) collectors.get(0);

        assertTrue(collector instanceof TimeZoneCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
    }

    public void testNewAccelerometerOnChangeCollector() {
        builder.addAccelerometerOnChangeCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractOnChangeCollector<?> collector = (AbstractOnChangeCollector<?>) collectors.get(0);

        assertTrue(collector instanceof AccelerometerOnChangeCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getMinDelay());
    }

    public void testNewBatteryLevelPeriodicCollector() {
        builder.addBatteryLevelPeriodicCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractPeriodicCollector<?> collector = (AbstractPeriodicCollector<?>) collectors.get(0);

        assertTrue(collector instanceof BatteryLevelPeriodicCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getDelay());
    }

    public void testNewGeolocationPeriodicCollector() {
        builder.addPeriodicGeolocationCollector(100);

        List<Collector<?>> collectors = builder.build();
        assertEquals(1, collectors.size());

        AbstractPeriodicCollector<?> collector = (AbstractPeriodicCollector<?>) collectors.get(0);

        assertTrue(collector instanceof GeolocationPeriodicCollector);
        assertEquals(channel, collector.getClientChannel());
        assertEquals(getContext(), collector.getApplicationContext());
        assertEquals(100, collector.getDelay());
    }

}
