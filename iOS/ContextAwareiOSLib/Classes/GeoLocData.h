//
//  GeolocationObject.h
//  ContextAware
//
//  Created by Slim Besbes on 04/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GeoLocData : NSObject<NSCopying>
{
    NSNumber *latitude;
    NSNumber *longitude;
    NSNumber *timestamp;
    NSNumber *altitude;
    NSNumber *speed;
    NSNumber *bearing;
    NSNumber *accuracy;
}

@property(nonatomic, strong) NSNumber *latitude;
@property(nonatomic, strong) NSNumber *longitude;
@property(nonatomic, strong) NSNumber *timestamp;
@property(nonatomic, strong) NSNumber *altitude;
@property(nonatomic, strong) NSNumber *speed;
@property(nonatomic, strong) NSNumber *bearing;
@property(nonatomic, strong) NSNumber *accuracy;

- (GeoLocData *)initWithLatitude:(double )aLatitude
                       longitude:(double )aLongitude
                       timeStamp:(NSUInteger )aTimeStamp
                        altitude:(double )anAltitude
                           speed:(double )aSpeed
                         bearing:(double )aBearing
                        accuracy:(double )anAccuracy;

@end
