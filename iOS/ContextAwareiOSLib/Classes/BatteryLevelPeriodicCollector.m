//
//  BatteryLevel.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "BatteryLevelPeriodicCollector.h"

@interface BatteryLevelPeriodicCollector()
{
    NSRunLoop *runner;
    NSTimer   *timer;
}

@property(nonatomic, strong) NSRunLoop *runner;
@property(nonatomic, strong) NSTimer   *timer;

@end

@implementation BatteryLevelPeriodicCollector

@synthesize runner;
@synthesize timer;

- (void)start
{
    NSNumber *periodicityValue = [self.propertyList objectForKey:PERIODICITY];
    
    self.timer = [NSTimer scheduledTimerWithTimeInterval:[periodicityValue intValue]
                                                  target:self
                                                selector:@selector(collect)
                                                userInfo:nil
                                                 repeats:YES];
    
    self.runner = [NSRunLoop currentRunLoop];
    [self.runner addTimer:self.timer forMode:NSDefaultRunLoopMode];
}

- (ContextElement *)collect
{
    UIDevice *device = [UIDevice currentDevice];
    [device setBatteryMonitoringEnabled:YES];
    
    float batteryLevel = [device batteryLevel];
    int   batteryInfo  = (batteryLevel * 100);
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:BATTERY_LEVEL_KEY
                                                                     contextData:[NSString stringWithFormat:@"%i", batteryInfo]];
    
    [self sendContextElement:contextElement];

    return contextElement;
}

- (void)stop
{
    [self.timer invalidate];
}


@end
