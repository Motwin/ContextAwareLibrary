//
//  ContextAware.h
//  ContextAwareLib
//
//  Created by product on 06/06/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#ifdef __OBJC__

// main headers
#import "ContextAwareConstants.h"
#import "ContextAwareManager.h"

// Collectors
#import "PeriodicCollector.h"
#import "OnChangeCollector.h"
#import "ContextCollector.h"

// OneShotCollectors
#import "OneShotCollector.h"
#import "AppNameCollector.h"
#import "AppVersionCollector.h"
#import "DeviceOSCollector.h"
#import "DeviceOSVersionCollector.h"
#import "DeviceTypeCollector.h"
#import "LanguageCollector.h"
#import "MobileCountryCodeCollector.h"
#import "MobileNetworkCodeCollector.h"
#import "TimeZoneCollector.h"
#import "UserAgentCollector.h"

// OnChangeCollectors
#import "TagCollector.h"
#import "CrashEventCollector.h"
#import "DataNetworkTypeCollector.h"
#import "GeolocationCollector.h"
#import "ScreenOrientationCollector.h"

// PeriodicCollectors
#import "AccelerometerPeriodicCollector.h"
#import "BatteryLevelPeriodicCollector.h"
#import "GeolocationPeriodicCollector.h"

#endif

