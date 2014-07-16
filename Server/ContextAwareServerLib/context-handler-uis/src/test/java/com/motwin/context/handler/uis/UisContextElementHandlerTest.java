/**
 * 
 */
package com.motwin.context.handler.uis;

import static org.hamcrest.collection.IsMapContaining.hasEntry;

import java.util.Arrays;
import java.util.Map;

import junit.framework.Assert;

import org.hamcrest.Matcher;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.Before;
import org.junit.Test;

import com.motwin.context.model.AccelerometerData;
import com.motwin.context.model.GeoLocData;
import com.motwin.context.model.MobileContextElement;
import com.motwin.mf.uis.domain.InstalledApp;
import com.motwin.mf.uis.domain.MobileUser;
import com.motwin.mf.uis.services.InstalledAppService;
import com.motwin.mf.uis.services.MobileUserService;

/**
 * This ContextHandler is used to test the ContextBeanDefinitionParser and the
 * ContextDispatcher
 * 
 * @author abdelbaki
 * 
 */
public class UisContextElementHandlerTest {

    private Mockery             mockery;
    private MobileUserService   mobileUserService;
    private InstalledAppService installedAppService;

    private UisContextHandler   uisContextHandler;

    @Before
    public void setUp() {
        mockery = new Mockery();
        mobileUserService = mockery.mock(MobileUserService.class);
        installedAppService = mockery.mock(InstalledAppService.class);

        uisContextHandler = new UisContextHandler(installedAppService, mobileUserService);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testHandleDefaultContextEvent() {
        MobileContextElement<String> defaultContextElement;

        final String installationId, type, data;
        installationId = "installationId";
        type = "toto";
        data = "tutu";

        defaultContextElement = new MobileContextElement<String>();
        defaultContextElement.setInstallationId(installationId);
        defaultContextElement.setType(type);
        defaultContextElement.setData(data);

        mockery.checking(new Expectations() {
            {
                one(installedAppService).addMetaData(with(installationId), (Map<String, ?>) with(hasEntry(type, data)));
            }
        });

        uisContextHandler.onContextEvent(defaultContextElement);
        mockery.assertIsSatisfied();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testHandleGeoLocEvent() {
        MobileContextElement<GeoLocData> defaultContextElement;
        final String installationId, type;
        final GeoLocData data;
        final double accuracy, altitude, bearing, latitude, longitude, speed;
        final long timestamp;

        installationId = "installationId";
        type = "toto";
        accuracy = 12.1;
        altitude = 1.02;
        bearing = 2.05;
        latitude = 48.856614;
        longitude = 2.352222;
        speed = 0.00;
        timestamp = System.currentTimeMillis();

        data = new GeoLocData();
        data.setAccuracy(accuracy);
        data.setAltitude(altitude);
        data.setBearing(bearing);
        data.setLatitude(latitude);
        data.setLongitude(longitude);
        data.setSpeed(speed);
        data.setTimestamp(timestamp);

        defaultContextElement = new MobileContextElement<GeoLocData>();
        defaultContextElement.setInstallationId(installationId);
        defaultContextElement.setType(type);
        defaultContextElement.setData(data);

        final MobileUser mockedMobileUser;
        mockedMobileUser = new MobileUser();

        final InstalledApp mockedInstalledApp;
        mockedInstalledApp = new InstalledApp();

        final Sequence sequence;
        sequence = mockery.sequence("testHandleGeoLocEvent");

        mockery.checking(new Expectations() {
            {
                one(mobileUserService).findByInstalledApp(with(installationId));
                will(returnValue(mockedMobileUser));
                inSequence(sequence);

                one(installedAppService).findOne(with(installationId));
                will(returnValue(mockedInstalledApp));
                inSequence(sequence);

                one(mobileUserService).save(with(mockedMobileUser));
                inSequence(sequence);

                Matcher<Map<? extends String, ? extends Object>> mapMatcher = AllOf.allOf(
                        IsMapContaining.<String, Object> hasEntry(UisContextHandler.GEOLOC_ACCURACY_KEY, accuracy),
                        IsMapContaining.<String, Object> hasEntry(UisContextHandler.GEOLOC_ALTITUDE_KEY, altitude),
                        IsMapContaining.<String, Object> hasEntry(UisContextHandler.GEOLOC_BEARING_KEY, bearing),
                        IsMapContaining.<String, Object> hasEntry(UisContextHandler.GEOLOC_SPEED_KEY, speed),
                        IsMapContaining.<String, Object> hasEntry(UisContextHandler.GEOLOC_TIME_KEY, timestamp)

                );
                one(installedAppService).addMetaData(with(installationId), (Map<String, ?>) with(mapMatcher));
            }
        });

        uisContextHandler.onContextEvent(defaultContextElement);

        Assert.assertTrue(Arrays.equals(new double[] { longitude, latitude }, mockedInstalledApp.getLocation()));

        mockery.assertIsSatisfied();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testHandleAccelerometerEvent() {
        MobileContextElement<AccelerometerData> defaultContextElement;
        final String installationId, type;
        final AccelerometerData data;
        final double x, y, z;

        installationId = "installationId";
        type = "toto";
        x = 1.1;
        y = 2.2;
        z = 3.3;

        data = new AccelerometerData();
        data.setX(x);
        data.setY(y);
        data.setZ(z);

        defaultContextElement = new MobileContextElement<AccelerometerData>();
        defaultContextElement.setInstallationId(installationId);
        defaultContextElement.setType(type);
        defaultContextElement.setData(data);

        mockery.checking(new Expectations() {
            {
                Matcher<Map<? extends String, ? extends Object>> mapMatcher = AllOf.allOf(
                        IsMapContaining.<String, Object> hasEntry(UisContextHandler.ACCELEROMETER_X_KEY, x),
                        IsMapContaining.<String, Object> hasEntry(UisContextHandler.ACCELEROMETER_Y_KEY, y),
                        IsMapContaining.<String, Object> hasEntry(UisContextHandler.ACCELEROMETER_Z_KEY, z)

                );
                one(installedAppService).addMetaData(with(installationId), (Map<String, ?>) with(mapMatcher));
            }
        });

        uisContextHandler.onContextEvent(defaultContextElement);

        mockery.assertIsSatisfied();
    }
}
