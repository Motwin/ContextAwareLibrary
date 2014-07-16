//
//  GeolocationObject.m
//  ContextAware
//
//  Created by Slim Besbes on 04/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "GeoLocData.h"

@implementation GeoLocData

@synthesize latitude;
@synthesize longitude;
@synthesize timestamp;
@synthesize altitude;
@synthesize bearing;
@synthesize speed;
@synthesize accuracy;

- (GeoLocData *)initWithLatitude:(double )aLatitude
                       longitude:(double )aLongitude
                       timeStamp:(NSUInteger )aTimeStamp
                        altitude:(double )anAltitude
                           speed:(double )aSpeed
                         bearing:(double )aBearing
                        accuracy:(double )anAccuracy
{
    self = [super init];
    
    if(self) {
        self.latitude  = [NSNumber numberWithDouble:aLatitude];
        self.longitude = [NSNumber numberWithDouble:aLongitude];
        self.timestamp = [NSNumber numberWithInt:aTimeStamp];
        self.altitude  = [NSNumber numberWithDouble:anAltitude];
        self.speed     = [NSNumber numberWithDouble:aSpeed];
        self.bearing   = [NSNumber numberWithDouble:aBearing];
        self.accuracy  = [NSNumber numberWithDouble:anAccuracy];
    } 
    
    return self;
}

- (id)copyWithZone:(NSZone *)zone
{
    GeoLocData *copyGeoLocObject = [[GeoLocData allocWithZone:zone] init];
    
	copyGeoLocObject->latitude	= [self.latitude  copyWithZone:zone];
	copyGeoLocObject->longitude	= [self.longitude copyWithZone:zone];
    copyGeoLocObject->timestamp	= [self.timestamp copyWithZone:zone];
    copyGeoLocObject->altitude	= [self.altitude copyWithZone:zone];
    copyGeoLocObject->speed	    = [self.speed copyWithZone:zone];
    copyGeoLocObject->bearing	= [self.bearing copyWithZone:zone];
    copyGeoLocObject->accuracy	= [self.accuracy copyWithZone:zone];

    return copyGeoLocObject;
}

@end
