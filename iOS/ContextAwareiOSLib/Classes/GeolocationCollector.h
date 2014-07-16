//
//  Geolocation.h
//  ContextAware
//
//  Created by Slim Besbes on 03/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import "OnChangeCollector.h"

@interface GeolocationCollector : OnChangeCollector<CLLocationManagerDelegate>

@end
