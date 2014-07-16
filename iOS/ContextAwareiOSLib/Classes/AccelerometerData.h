//
//  AccelerometerData.h
//  ContextAware
//
//  Created by Slim Besbes on 17/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface AccelerometerData : NSObject<NSCopying>
{
    NSNumber *x;
    NSNumber *y;
    NSNumber *z;
}

@property(nonatomic, strong) NSNumber *x;
@property(nonatomic, strong) NSNumber *y;
@property(nonatomic, strong) NSNumber *z;

- (AccelerometerData *)initWithPositionX:(double )aX
                               positionY:(double )aY
                               positionZ:(double )aZ;

@end
