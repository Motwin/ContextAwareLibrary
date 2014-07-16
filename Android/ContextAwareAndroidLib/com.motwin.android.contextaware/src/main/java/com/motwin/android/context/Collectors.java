package com.motwin.android.context;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import android.content.Context;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
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
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Facility class to instantiate Context Collectors
 * 
 */
public final class Collectors {

    // Hide constructor
    private Collectors() {

    }

    /**
     * Get charger type collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * @return a ChargerTypeCollector instance
     */
    public static ChargerTypeCollector newChargerTypeCollector(ClientChannel aClientChannel,
                                                               Context anApplicationContext, long aMinDelay) {
        return new ChargerTypeCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get charging state collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * @return a ChargingStateCollector instance
     */
    public static ChargingStateCollector newChargingStateCollector(ClientChannel aClientChannel,
                                                                   Context anApplicationContext, long aMinDelay) {
        return new ChargingStateCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get crash event collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aExceptionHandler
     *            A custom exception handler for uncaught exceptions
     * @return a CrashEventCollector instance
     */
    public static CrashEventCollector newCrashEventCollector(ClientChannel aClientChannel,
                                                             Context anApplicationContext,
                                                             UncaughtExceptionHandler aExceptionHandler) {
        return new CrashEventCollector(aClientChannel, anApplicationContext, aExceptionHandler);
    }

    /**
     * Get dock state collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * @return a DockStateCollector instance
     */
    public static DockStateCollector newDockStateCollector(ClientChannel aClientChannel, Context anApplicationContext,
                                                           long aMinDelay) {
        return new DockStateCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get dock type collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * @return a DockTypeCollector instance
     */
    public static DockTypeCollector newDockTypeCollector(ClientChannel aClientChannel, Context anApplicationContext,
                                                         long aMinDelay) {
        return new DockTypeCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get an on change geolocation collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two geolocation updates
     * @param aMinDistance
     *            The minimum distance between two geolocation updates
     * @param aLocationListener
     *            A custom location listener
     * 
     * @return a GeolocationOnChangeCollector instance
     */
    public static GeolocationOnChangeCollector
            newGeolocationOnChangeCollector(ClientChannel aClientChannel, Context anApplicationContext, long aMinDelay,
                                            float aMinDistance, CollectorLocationListener aLocationListener) {
        return new GeolocationOnChangeCollector(aClientChannel, anApplicationContext, aMinDelay, aMinDistance,
                aLocationListener);
    }

    /**
     * Get an on change geolocation collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two geolocation updates
     * @param aMinDistance
     *            The minimum distance between two geolocation updates
     * 
     * @return a GeolocationOnChangeCollector instance. This is equivalent to
     *         {@link #newGeolocationOnChangeCollector(ClientChannel, Context, long, float, CollectorLocationListener)
     *         with default location listener}
     */
    public static GeolocationOnChangeCollector newGeolocationOnChangeCollector(ClientChannel aClientChannel,
                                                                               Context anApplicationContext,
                                                                               long aMinDelay, float aMinDistance) {
        return new GeolocationOnChangeCollector(aClientChannel, anApplicationContext, aMinDelay, aMinDistance,
                new DefaultLocationListener());
    }

    /**
     * Get a network identifier collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a NetworkIdentifierCollector instance.
     */
    public static NetworkIdentifierCollector newNetworkIdentifierCollector(ClientChannel aClientChannel,
                                                                           Context anApplicationContext) {
        return new NetworkIdentifierCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a network reception collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * 
     * @return a NetworkReceptionCollector instance.
     */
    public static NetworkReceptionCollector newNetworkReceptionCollector(ClientChannel aClientChannel,
                                                                         Context anApplicationContext, long aMinDelay) {
        return new NetworkReceptionCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get a network type collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * 
     * @return a NetworkTypeCollector instance.
     */
    public static NetworkTypeCollector newNetworkTypeCollector(ClientChannel aClientChannel,
                                                               Context anApplicationContext, long aMinDelay) {
        return new NetworkTypeCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get a screen orientation collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * 
     * @return a ScreenOrientationCollector instance.
     */
    public static ScreenOrientationCollector
            newScreenOrientationCollector(ClientChannel aClientChannel, Context anApplicationContext, long aMinDelay) {
        return new ScreenOrientationCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get a sound mode collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * 
     * @return a SoundModeCollector instance.
     */
    public static SoundModeCollector newSoundModeCollector(ClientChannel aClientChannel, Context anApplicationContext,
                                                           long aMinDelay) {
        return new SoundModeCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get a sound output collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates
     * 
     * @return a SoundOutputCollector instance.
     */
    public static SoundOutputCollector newSoundOutputCollector(ClientChannel aClientChannel,
                                                               Context anApplicationContext, long aMinDelay) {
        return new SoundOutputCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get a tag collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a TagCollector instance.
     */
    public static TagCollector newTagCollector(ClientChannel aClientChannel, Context anApplicationContext) {
        return new TagCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a device model collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a DeviceModelCollector instance.
     */
    public static DeviceModelCollector newDeviceModelCollector(ClientChannel aClientChannel,
                                                               Context anApplicationContext) {
        return new DeviceModelCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a language collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a LanguageCollector instance.
     */
    public static LanguageCollector newLanguageCollector(ClientChannel aClientChannel, Context anApplicationContext) {
        return new LanguageCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a mobile country code collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a MobileCountryCodeCollector instance.
     */
    public static MobileCountryCodeCollector newMobileCountryCodeCollector(ClientChannel aClientChannel,
                                                                           Context anApplicationContext) {
        return new MobileCountryCodeCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a mobile network code collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a MobileNetworkCodeCollector instance.
     */
    public static MobileNetworkCodeCollector newMobileNetworkCodeCollector(ClientChannel aClientChannel,
                                                                           Context anApplicationContext) {
        return new MobileNetworkCodeCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a roaming collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a RoamingCollector instance.
     */
    public static RoamingCollector newRoamingCollector(ClientChannel aClientChannel, Context anApplicationContext) {
        return new RoamingCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a roaming country code collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a RoamingCountryCodeCollector instance.
     */
    public static RoamingCountryCodeCollector newRoamingCountryCodeCollector(ClientChannel aClientChannel,
                                                                             Context anApplicationContext) {
        return new RoamingCountryCodeCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a sim state collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a SimStateCollector instance.
     */
    public static SimStateCollector newSimStateCollector(ClientChannel aClientChannel, Context anApplicationContext) {
        return new SimStateCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get a time zone collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * 
     * @return a TimeZoneCollector instance.
     */
    public static TimeZoneCollector newTimeZoneCollector(ClientChannel aClientChannel, Context anApplicationContext) {
        return new TimeZoneCollector(aClientChannel, anApplicationContext);
    }

    /**
     * Get an periodic accelerometer collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between context updates
     * 
     * @return a AccelerometerOnChangeCollector instance.
     */
    public static AccelerometerOnChangeCollector newAccelerometerOnChangeCollector(ClientChannel aClientChannel,
                                                                                   Context anApplicationContext,
                                                                                   long aMinDelay) {
        return new AccelerometerOnChangeCollector(aClientChannel, anApplicationContext, aMinDelay);
    }

    /**
     * Get a periodic battery level collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aPeriodInMilliseconds
     *            The delay between context updates
     * 
     * @return a BatteryLevelPeriodicCollector instance.
     */
    public static BatteryLevelPeriodicCollector newBatteryLevelPeriodicCollector(ClientChannel aClientChannel,
                                                                                 Context anApplicationContext,
                                                                                 long aPeriodInMilliseconds) {
        return new BatteryLevelPeriodicCollector(aClientChannel, anApplicationContext, aPeriodInMilliseconds);
    }

    /**
     * Get an periodic geolocation collector
     * 
     * @param aClientChannel
     *            The client channel that will be used to send the context
     *            elements
     * @param anApplicationContext
     *            The application context
     * @param aPeriodInMilliseconds
     *            The delay between context updates
     * 
     * @return a GeolocationPeriodicCollector instance.
     */
    public static GeolocationPeriodicCollector newGeolocationPeriodicCollector(ClientChannel aClientChannel,
                                                                               Context anApplicationContext,
                                                                               long aPeriodInMilliseconds) {
        return new GeolocationPeriodicCollector(aClientChannel, anApplicationContext, aPeriodInMilliseconds);
    }

    /**
     * Facility class to build Context Collectors list
     * 
     */
    public static final class Builder {
        private final ClientChannel      clientChannel;
        private final Context            applicationContext;
        private final List<Collector<?>> collectorsList;

        /**
         * Builder Constructor
         * 
         * @param aClientChannel
         *            The client channel that will be used to send all context
         *            elements
         * @param anApplicationContext
         *            The application context
         */
        public Builder(ClientChannel aClientChannel, Context anApplicationContext) {
            clientChannel = Preconditions.checkNotNull(aClientChannel, "aClientChannel cannot be null");
            applicationContext = Preconditions
                    .checkNotNull(anApplicationContext, "anApplicationContext cannot be null");
            collectorsList = Lists.newArrayList();
        }

        /**
         * Add a charger type collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addChargerTypeCollector(long aMinDelay) {
            collectorsList.add(Collectors.newChargerTypeCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add a charging state collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addChargingStateCollector(long aMinDelay) {
            collectorsList.add(Collectors.newChargingStateCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add a crash event collector to the list
         * 
         * @param aExceptionHandler
         *            A custom exception handler for uncaught exceptions
         * @return the Builder instance for fluent API
         */
        public Builder addCrashEventCollector(UncaughtExceptionHandler aExceptionHandler) {
            collectorsList.add(Collectors.newCrashEventCollector(clientChannel, applicationContext, aExceptionHandler));
            return this;
        }

        /**
         * Add a dock state collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addDockStateCollector(long aMinDelay) {
            collectorsList.add(Collectors.newDockStateCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add a dock type collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addDockTypeCollector(long aMinDelay) {
            collectorsList.add(Collectors.newDockTypeCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add an on change geolocation collector to the list. <br>
         * Is equivalent to
         * {@link #addGeolocationOnChangeCollector(long, float, CollectorLocationListener)
         * with default listener}
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @param aMinDistance
         *            The minimum distance between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addGeolocationOnChangeCollector(long aMinDelay, float aMinDistance) {
            collectorsList.add(Collectors.newGeolocationOnChangeCollector(clientChannel, applicationContext, aMinDelay,
                    aMinDistance));
            return this;
        }

        /**
         * Add an on change geolocation collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @param aMinDistance
         *            The minimum distance between context updates
         * @param aLocationListener
         *            A custom location listener
         * @return the Builder instance for fluent API
         */
        public Builder addGeolocationOnChangeCollector(long aMinDelay, float aMinDistance,
                                                       CollectorLocationListener aLocationListener) {
            collectorsList.add(Collectors.newGeolocationOnChangeCollector(clientChannel, applicationContext, aMinDelay,
                    aMinDistance, aLocationListener));
            return this;
        }

        /**
         * Add a network identifier collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addNetworkIdentifierCollector() {
            collectorsList.add(Collectors.newNetworkIdentifierCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a network reception collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addNetworkReceptionCollector(long aMinDelay) {
            collectorsList.add(Collectors.newNetworkReceptionCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add a network type collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addNetworkTypeCollector(long aMinDelay) {
            collectorsList.add(Collectors.newNetworkTypeCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add a screen orientation collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addScreenOrientationCollector(long aMinDelay) {
            collectorsList.add(Collectors.newScreenOrientationCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add a sound mode collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addSoundModeCollector(long aMinDelay) {
            collectorsList.add(Collectors.newSoundModeCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add a sound output collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * @return the Builder instance for fluent API
         */
        public Builder addSoundOutputCollector(long aMinDelay) {
            collectorsList.add(Collectors.newSoundOutputCollector(clientChannel, applicationContext, aMinDelay));
            return this;
        }

        /**
         * Add a tag collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addTagCollector() {
            collectorsList.add(Collectors.newTagCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a device model collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addDeviceModelCollector() {
            collectorsList.add(Collectors.newDeviceModelCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a language collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addLanguageCollector() {
            collectorsList.add(Collectors.newLanguageCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a mobile country code collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addMobileCountryCodeCollector() {
            collectorsList.add(Collectors.newMobileCountryCodeCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a mobile network code collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addMobileNetworkCodeCollector() {
            collectorsList.add(Collectors.newMobileNetworkCodeCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a roaming to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addRoamingCollector() {
            collectorsList.add(Collectors.newRoamingCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a roaming country code collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addRoamingCountryCodeCollector() {
            collectorsList.add(Collectors.newRoamingCountryCodeCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a sim state collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addSimStateCollector() {
            collectorsList.add(Collectors.newSimStateCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add a time zone collector to the list
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addTimeZoneCollector() {
            collectorsList.add(Collectors.newTimeZoneCollector(clientChannel, applicationContext));
            return this;
        }

        /**
         * Add an on change accelerometer collector to the list
         * 
         * @param aMinDelay
         *            The minimum delay between context updates
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addAccelerometerOnChangeCollector(long aMinDelay) {
            collectorsList.add(Collectors.newAccelerometerOnChangeCollector(clientChannel, applicationContext,
                    aMinDelay));
            return this;
        }

        /**
         * Add a periodic battery level collector to the list
         * 
         * @param aPeriodInMillisecond
         *            The delay between context updates
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addBatteryLevelPeriodicCollector(long aPeriodInMillisecond) {
            collectorsList.add(Collectors.newBatteryLevelPeriodicCollector(clientChannel, applicationContext,
                    aPeriodInMillisecond));
            return this;
        }

        /**
         * Add a periodic geolocation collector to the list
         * 
         * @param aPeriodInMillisecond
         *            The delay between context updates
         * 
         * @return the Builder instance for fluent API
         */
        public Builder addPeriodicGeolocationCollector(long aPeriodInMillisecond) {
            collectorsList.add(Collectors.newGeolocationPeriodicCollector(clientChannel, applicationContext,
                    aPeriodInMillisecond));
            return this;
        }

        /**
         * Add a collector to the list
         * 
         * @param aCollector
         *            The collector
         * 
         * @return the Builder instance for fluent API
         */
        public Builder add(Collector<?> aCollector) {
            Preconditions.checkNotNull(aCollector, "aCollector cannot be null");
            collectorsList.add(aCollector);
            return this;
        }

        /**
         * Builds the collector list
         * 
         * @return the list of Collectors
         */
        public List<Collector<?>> build() {
            return ImmutableList.<Collector<?>> copyOf(collectorsList);
        }
    }
}
