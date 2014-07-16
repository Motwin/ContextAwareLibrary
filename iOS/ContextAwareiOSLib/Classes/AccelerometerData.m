//
//  AccelerometerData.m
//  ContextAware
//
//  Created by Slim Besbes on 17/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "AccelerometerData.h"

@implementation AccelerometerData

@synthesize x;
@synthesize y;
@synthesize z;

- (AccelerometerData *)initWithPositionX:(double )aX
                               positionY:(double )aY
                               positionZ:(double )aZ
{
    self = [super init];
    
    if (self) {
        self.x = [NSNumber numberWithDouble:aX];
        self.y = [NSNumber numberWithDouble:aY];
        self.z = [NSNumber numberWithDouble:aZ];
    }
    
    return self;
}

- (id)copyWithZone:(NSZone *)zone
{
    AccelerometerData *accData = [[AccelerometerData allocWithZone:zone] init];
    
    accData->x = [self.x copyWithZone:zone];
    accData->y = [self.y copyWithZone:zone];
    accData->z = [self.z copyWithZone:zone];
    
    return accData;
}

@end
