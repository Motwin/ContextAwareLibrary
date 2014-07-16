//
//  AccelerometerData.m
//  ContextAware
//
//  Created by Slim Besbes on 03/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "AccelerometerPeriodicCollector.h"
#import "ContextElement.h"
#import "AccelerometerData.h"

@interface AccelerometerPeriodicCollector() {
    
    UIAccelerometer  *accelerometer;
    CMMotionManager  *motionManager;
    NSOperationQueue *queue;
}

@property(nonatomic, strong) UIAccelerometer  *accelerometer;
@property(nonatomic, strong) CMMotionManager  *motionManager;
@property(nonatomic, strong) NSOperationQueue *queue;

@end

@implementation AccelerometerPeriodicCollector

@synthesize accelerometer;
@synthesize motionManager;
@synthesize queue;

- (void)start
{
    NSNumber *periodicityValue = [self.propertyList objectForKey:PERIODICITY];
    
    self.motionManager = [[CMMotionManager alloc] init];
    self.motionManager.accelerometerUpdateInterval  = [periodicityValue floatValue];
    
    if (self.motionManager.accelerometerAvailable) {
        
        self.queue = [NSOperationQueue currentQueue];
        
        
        CMAccelerometerHandler accelerometerHandler = ^(CMAccelerometerData *accelerometerData, NSError *error) {
            
            CMAcceleration acceleration = accelerometerData.acceleration;
            
            AccelerometerData *accData = [[AccelerometerData alloc] initWithPositionX:acceleration.x
                                                                            positionY:acceleration.y
                                                                            positionZ:acceleration.z];
            
            ContextElement *accContextElement = [[ContextElement alloc] initWithContextType:ACCELEROMETER_KEY
                                                                                contextData:accData];
            
            [self sendContextElement:accContextElement];
            
        };
        
        [self.motionManager startAccelerometerUpdatesToQueue:self.queue
                                                 withHandler:accelerometerHandler];
    }
}

- (void)stop
{
    [self.motionManager stopAccelerometerUpdates];
}


@end
