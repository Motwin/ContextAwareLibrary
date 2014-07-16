package com.motwin.context.handler.uis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import com.motwin.context.model.AccelerometerData;
import com.motwin.context.model.GeoLocData;
import com.motwin.context.model.MobileContextElement;
import com.motwin.mf.uis.domain.InstalledApp;
import com.motwin.mf.uis.domain.MobileUser;
import com.motwin.mf.uis.services.InstalledAppService;
import com.motwin.mf.uis.services.MobileUserService;

/**
 * @author amal
 * 
 */
public class UisContextHandler {

    protected static final String     GEOLOC_ACCURACY_KEY = "accuracy";
    protected static final String     GEOLOC_BEARING_KEY  = "bearing";
    protected static final String     GEOLOC_SPEED_KEY    = "speed";
    protected static final String     GEOLOC_ALTITUDE_KEY = "altitude";
    protected static final String     GEOLOC_TIME_KEY     = "geolocTime";
    protected static final String     ACCELEROMETER_X_KEY = "x";
    protected static final String     ACCELEROMETER_Y_KEY = "y";
    protected static final String     ACCELEROMETER_Z_KEY = "z";

    private static final Logger       LOGGER              = LoggerFactory.getLogger(UisContextHandler.class);

    private final InstalledAppService installedAppService;
    private final MobileUserService   mobileUserService;

    public UisContextHandler(InstalledAppService anInstalledAppService, MobileUserService aMobileUserService) {
        installedAppService = anInstalledAppService;
        mobileUserService = aMobileUserService;
    }

    @Subscribe
    public void onContextEvent(final MobileContextElement<?> anEvent) {
        LOGGER.debug("Handle ContextElement {}", anEvent);

        String installationId;
        installationId = anEvent.getInstallationId();
        Preconditions.checkNotNull(installationId, "InstallationID cannot be null");

        String eventType;
        eventType = anEvent.getType();
        Preconditions.checkNotNull(eventType, "EventType cannot be null");

        Object eventData;
        eventData = anEvent.getData();
        Preconditions.checkNotNull(eventData, "EventData cannot be null");

        if (eventData instanceof GeoLocData) {
            handleGeoLocData(installationId, eventType, (GeoLocData) eventData);
        } else if (eventData instanceof AccelerometerData) {
            handleAccelerometerData(installationId, eventType, (AccelerometerData) eventData);
        } else {
            handleDefaultData(installationId, eventType, eventData);
        }

    }

    private void handleDefaultData(final String aInstallationId, final String aEventType, final Object aEventData) {
        Preconditions.checkNotNull(aInstallationId, "aInstallationId cannot be null");
        Preconditions.checkNotNull(aEventType, "aEventType cannot be null");
        Preconditions.checkNotNull(aEventData, "aEventData cannot be null");

        Map<String, Object> metaDataMap;
        metaDataMap = new HashMap<String, Object>();
        metaDataMap.put(aEventType, aEventData);
        installedAppService.addMetaData(aInstallationId, metaDataMap);
    }

    private void handleGeoLocData(final String aInstallationId, final String aEventType, final GeoLocData aEventData) {
        Preconditions.checkNotNull(aInstallationId, "aInstallationId cannot be null");
        Preconditions.checkNotNull(aEventType, "aEventType cannot be null");
        Preconditions.checkNotNull(aEventData, "aEventData cannot be null");

        double latitude = aEventData.getLatitude();
        double longitude = aEventData.getLongitude();
        long timestamp = aEventData.getTimestamp();
        double altitude = aEventData.getAltitude();
        double speed = aEventData.getSpeed();
        double bearing = aEventData.getBearing();
        double accuracy = aEventData.getAccuracy();

        Map<String, Object> metaDataMap;
        metaDataMap = new HashMap<String, Object>();
        metaDataMap.put(GEOLOC_TIME_KEY, timestamp);
        metaDataMap.put(GEOLOC_ALTITUDE_KEY, altitude);
        metaDataMap.put(GEOLOC_SPEED_KEY, speed);
        metaDataMap.put(GEOLOC_BEARING_KEY, bearing);
        metaDataMap.put(GEOLOC_ACCURACY_KEY, accuracy);

        MobileUser mobileUser;
        mobileUser = mobileUserService.findByInstalledApp(aInstallationId);

        InstalledApp installedApp = installedAppService.findOne(aInstallationId);
        mobileUser.removeInstalledApp(installedApp);

        double[] location = { longitude, latitude };
        installedApp.setLocation(location);

        mobileUser.addInstalledApp(installedApp);
        mobileUserService.save(mobileUser);

        installedAppService.addMetaData(aInstallationId, metaDataMap);
    }

    private void handleAccelerometerData(final String aInstallationId, final String aEventType,
                                         final AccelerometerData aEventData) {
        Preconditions.checkNotNull(aInstallationId, "aInstallationId cannot be null");
        Preconditions.checkNotNull(aEventType, "aEventType cannot be null");
        Preconditions.checkNotNull(aEventData, "aEventData cannot be null");

        double x = aEventData.getX();
        double y = aEventData.getY();
        double z = aEventData.getZ();

        Map<String, Object> metaDataMap = new HashMap<String, Object>();
        metaDataMap.put(ACCELEROMETER_X_KEY, x);
        metaDataMap.put(ACCELEROMETER_Y_KEY, y);
        metaDataMap.put(ACCELEROMETER_Z_KEY, z);
        installedAppService.addMetaData(aInstallationId, metaDataMap);
    }
}
