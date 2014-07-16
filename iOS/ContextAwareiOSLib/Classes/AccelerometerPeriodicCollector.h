//
//  AccelerometerData.h
//  ContextAware
//
//  Created by Slim Besbes on 03/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreMotion/CoreMotion.h>
#import <UIKit/UIKit.h>
#import "PeriodicCollector.h"

@interface AccelerometerPeriodicCollector : PeriodicCollector<UIAccelerometerDelegate>

@end
