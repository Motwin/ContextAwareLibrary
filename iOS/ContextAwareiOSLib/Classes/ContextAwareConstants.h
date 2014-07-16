//
//  ContextAwareConstants.h
//  ContextAwareLib
//
//  Created by Slim Besbes on 14/02/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#define BUNDLE_IDENTIFIER    @"CFBundleIdentifier"
#define BUNDLE_VERSION       @"CFBundleVersion"

#define APP_NAME                    @"AppName"
#define APP_NAME_KEY                @"appName"
#define APP_VERSION                 @"AppVersion"
#define APP_VERSION_KEY             @"appVersion"
#define USER_AGENT                  @"UserAgent"
#define USER_AGENT_KEY              @"userAgent"

#define DEVICE_OS                   @"DeviceOS"
#define DEVICE_OS_KEY               @"deviceOS"
#define DEVICE_OS_VERSION           @"DeviceOSVersion"
#define DEVICE_OS_VERSION_KEY       @"deviceOSVersion"
#define DEVICE_TYPE                 @"DeviceType"
#define DEVICE_TYPE_KEY             @"deviceType"

#define LANGUAGE                    @"Language"
#define LANGUAGE_KEY                @"language"

#define TIMEZONE                    @"TimeZone"
#define TIMEZONE_KEY                @"timeZone"

#define CRASH_EVENT                 @"CrashEvent"
#define CRASH_EVENT_KEY             @"crashEvent"

#define DOCK_STATE                  @"DockState"
#define DOCK_STATE_KEY              @"dockState"
#define DOCK_TYPE                   @"DockType"
#define DOCK_TYPE_KEY               @"dockType"

#define BATTERY_LEVEL               @"BatteryLevel"
#define BATTERY_LEVEL_KEY           @"batteryLevel"
#define CHARGER_TYPE                @"ChargerType"
#define CHARGER_TYPE_KEY            @"chargerType"
#define CHARGING_STATE              @"ChargingState"
#define CHARGING_STATE_KEY          @"chargingState"

#define GEOLOCATION                 @"Geolocation"
#define GEOLOCATION_KEY             @"geolocation"
#define GEOLOCATION_PERIODIC        @"PeriodicGeolocation"
#define GEOLOCATION_PERIODIC_KEY    @"periodicGeolocation"

#define SIM_STATE                   @"SimState"
#define SIM_STATE_KEY               @"simState"
#define MOBILE_COUNTRY_CODE         @"MobileCountryCode"
#define MOBILE_COUNTRY_CODE_KEY     @"mobileCountryCode"
#define MOBILE_NETWORK_CODE         @"MobileNetworkCode"
#define MOBILE_NETWORK_CODE_KEY     @"mobileNetworkCode"
#define NETWORK_IDENTIFIER          @"CellId"
#define NETWORK_IDENTIFIER_KEY      @"cellId"
#define NETWORK_RECEPTION           @"NetworkReceptionStrengh"
#define NETWORK_RECEPTION_KEY       @"networkReceptionStrengh"
#define NETWORK_TYPE                @"DataNetworkType"
#define NETWORK_TYPE_KEY            @"dataNetworkType"

#define ROAMING                     @"Roaming"
#define ROAMING_KEY                 @"roaming"
#define ROAMING_COUNTRY_CODE        @"RoamingCountryCode"
#define ROAMING_COUNTRY_CODE_KEY    @"roamingCountryCode"

#define SCREEN_ORIENTATION          @"ScreenOrientation"
#define SCREEN_ORIENTATION_KEY      @"screenOrientation"

#define ACCELEROMETER               @"AccelerometerData"
#define ACCELEROMETER_KEY           @"accelerometerData"

#define SOUND_MODE                  @"SoundMode"
#define SOUND_MODE_KEY              @"soundMode";
#define SOUND_OUTPUT                @"SoundOutPut"
#define SOUND_OUTPUT_KEY            @"soundOutPut";

#define TAG                         @"Tag"
#define TAG_KEY                     @"tag"


/**
 * ContextElement collectors properties
 */

#define ACTIVATED              @"Activated"
#define PERIODICITY            @"Periodicity"
#define DISTANCE_FILTER        @"Distance"
#define ACCURACY               @"Accuracy"

#define NOT_AVAILABLE        @"n.a"
#define NOT_AVAILABLE_CODE   @"65535"

#define OFF                  @"OFF"
#define WIFI                 @"WIFI"
#define MOBILE               @"MOBILE"
#define OTHER                @"UNDEFINED"

#define PORTRAIT             @"portrait"
#define PORTRAIT_DOWN        @"reversePortrait"
#define LAND_SCAPE_LEFT      @"landScape"
#define LAND_SCAPE_RIGHT     @"reverseLandScape"
#define ORIENTATION_UNKNOWN  @"unknown"

#define USER_AGENT_NAVIGATOR @"navigator.userAgent"

#define FOLDER_NAME          @"Documents"
#define CRASH_FILE           @"crashFile.txt"

#define COLLECTOR_SUFFIX     @"Collector"

#define MAPCLASS_NAME        @"ContextElement"
#define CHANNEL_NAME         @"com.motwin.contextAware"

#define CONTEXT_AWARE_FILE   @"ContextAware"


#define degreesToRadiansFunction(x) (M_PI * x / 180.0)
#define radiandsToDegreesFunction(x) (x * 180.0 / M_PI)
