//
//  PeriodicGeolocationCollector.h
//  ContextAware
//
//  Created by Slim Besbes on 16/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import "PeriodicCollector.h"

@interface GeolocationPeriodicCollector : PeriodicCollector<CLLocationManagerDelegate>

@end
