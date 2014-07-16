package com.motwin.android.context;

import com.motwin.android.context.collector.impl.AccelerometerOnChangeCollector;
import com.motwin.android.context.collector.impl.BatteryLevelPeriodicCollector;
import com.motwin.android.context.collector.impl.ChargerTypeCollector;
import com.motwin.android.context.collector.impl.ChargingStateCollector;
import com.motwin.android.context.collector.impl.CrashEventCollector;
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

/**
 * Static name definition of all context elements.
 * 
 */
public final class ContextAware {

    // Hide constructor
    private ContextAware() {

    }

    public static final String MESSAGE_TYPE                    = "ContextAware";

    public static final String DEVICE_MODEL_KEY                = "deviceModel";
    public static final String DEVICE_MODEL_CLASS_NAME         = DeviceModelCollector.class.getName();

    public static final String LANGUAGE_KEY                    = "language";
    public static final String LANGUAGE_CLASS_NAME             = LanguageCollector.class.getName();

    public static final String TIME_ZONE_KEY                   = "timeZone";
    public static final String TIME_ZONE_CLASS_NAME            = TimeZoneCollector.class.getName();

    public static final String CRASH_EVENT_KEY                 = "cashEvent";
    public static final String CRASH_EVENT_CLASS_NAME          = CrashEventCollector.class.getName();

    public static final String DOCK_SATE_KEY                   = "dockState";
    public static final String DOCK_SATE_CLASS_NAME            = DockStateCollector.class.getName();
    public static final String DOCK_TYPE_KEY                   = "dockType";
    public static final String DOCK_TYPE_CLASS_NAME            = DockTypeCollector.class.getName();

    public static final String BATTERY_LEVEL_KEY               = "batteryLevel";
    public static final String BATTERY_LEVEL_CLASS_NAME        = BatteryLevelPeriodicCollector.class.getName();
    public static final String CHARGER_TYPE_KEY                = "chargerType";
    public static final String CHARGER_TYPE_CLASS_NAME         = ChargerTypeCollector.class.getName();
    public static final String CHARGING_STATE_KEY              = "chargingState";
    public static final String CHARGING_STATE_CLASS_NAME       = ChargingStateCollector.class.getName();

    public static final String GEOLOCATION_KEY                 = "geolocation";
    public static final String GEOLOCATION_CLASS_NAME          = GeolocationOnChangeCollector.class.getName();
    public static final String GEOLCATION_PERIODIC_KEY         = "periodicGeolocation";
    public static final String GEOLCATION_PERIODIC_CLASS_NAME  = GeolocationPeriodicCollector.class.getName();

    public static final String SIM_STATE_KEY                   = "simState";
    public static final String SIM_STATE_CLASS_NAME            = SimStateCollector.class.getName();
    public static final String MOBILE_COUNTRY_CODE_KEY         = "mobileCountryCode";
    public static final String MOBILE_COUNTRY_CODE_CLASS_NAME  = MobileCountryCodeCollector.class.getName();
    public static final String MOBILE_NETWORK_CODE_KEY         = "mobileNetworkCode";
    public static final String MOBILE_NETWORK_CODE_CLASS_NAME  = MobileNetworkCodeCollector.class.getName();
    public static final String NETWORK_IDENTIFIER_KEY          = "cellId";
    public static final String NETWORK_IDENTIFIER_CLASS_NAME   = NetworkIdentifierCollector.class.getName();
    public static final String NETWORK_RECEPTION_KEY           = "networkReceptionStrengh";
    public static final String NETWORK_RECEPTION_CLASS_NAME    = NetworkReceptionCollector.class.getName();
    public static final String NETWORK_TYPE_KEY                = "dataNetworkType";
    public static final String NETWORK_TYPE_CLASS_NAME         = NetworkTypeCollector.class.getName();
    public static final String ROAMING_KEY                     = "roaming";
    public static final String ROAMING_CLASS_NAME              = RoamingCollector.class.getName();
    public static final String ROAMING_COUNTRY_CODE_KEY        = "roamingCountryCode";
    public static final String ROAMING_COUNTRY_CODE_CLASS_NAME = RoamingCountryCodeCollector.class.getName();

    public static final String SCREEN_ORIENTATION_KEY          = "screenOrientation";
    public static final String SCREEN_ORIENTATION_CLASS_NAME   = ScreenOrientationCollector.class.getName();

    public static final String ACCELEROMETER_KEY               = "accelerometerData";
    public static final String ACCELEROMETER_CLASS_NAME        = AccelerometerOnChangeCollector.class.getName();

    public static final String SOUND_MODE_KEY                  = "soundMode";
    public static final String SOUND_MODE_CLASS_NAME           = SoundModeCollector.class.getName();
    public static final String SOUND_OUTPUT_KEY                = "soundOutput";
    public static final String SOUND_OUTPUT_CLASS_NAME         = SoundOutputCollector.class.getName();

    public static final String TAG_KEY                         = "tag";
    public static final String TAG_COLLECTOR_CLASS_NAME        = TagCollector.class.getName();

}
