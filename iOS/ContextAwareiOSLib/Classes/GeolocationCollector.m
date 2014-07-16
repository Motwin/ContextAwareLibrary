//
//  Geolocation.m
//  ContextAware
//
//  Created by Slim Besbes on 03/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "GeolocationCollector.h"
#import "ContextElement.h"
#import "GeoLocData.h"

@interface GeolocationCollector()
{
    CLLocationManager *locationManager;
}

@property(nonatomic, strong) CLLocationManager *locationManager;

@end

@implementation GeolocationCollector

@synthesize locationManager;

- (void)start
{
    NSNumber *accuracyValue       = [self.propertyList objectForKey:ACCURACY];
    NSNumber *distanceFilterValue = [self.propertyList objectForKey:DISTANCE_FILTER];
    
    self.locationManager                 = [[CLLocationManager alloc] init];
    self.locationManager.desiredAccuracy = [accuracyValue doubleValue];
    self.locationManager.distanceFilter  = [distanceFilterValue doubleValue];
    self.locationManager.delegate        = self;
    
    [self.locationManager startUpdatingLocation];
}

- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation
{
    if (newLocation != nil) {
        
        float aBearing = [self calculateBearing:oldLocation.coordinate toCoordinate:newLocation.coordinate];
        float aSpeed   = newLocation.speed;
        
        if(aSpeed < 0) {
            aSpeed = 0;
        }
        
        GeoLocData *geolocationObject = [[GeoLocData alloc] initWithLatitude:newLocation.coordinate.latitude
                                                                   longitude:newLocation.coordinate.longitude
                                                                   timeStamp:[newLocation.timestamp timeIntervalSince1970]
                                                                    altitude:newLocation.altitude
                                                                       speed:aSpeed
                                                                     bearing:aBearing
                                                                    accuracy:newLocation.horizontalAccuracy];
        
        ContextElement *geoLocContextElement = [[ContextElement alloc] initWithContextType:GEOLOCATION_KEY
                                                                                           contextData:geolocationObject];
        
        [self onChange:geoLocContextElement];
    }
}

- (float )calculateBearing:(CLLocationCoordinate2D)fromLocation toCoordinate:(CLLocationCoordinate2D)toLocation
{
    float fLat = degreesToRadiansFunction(fromLocation.latitude);
    float fLng = degreesToRadiansFunction(fromLocation.longitude);
    float tLat = degreesToRadiansFunction(toLocation.latitude);
    float tLng = degreesToRadiansFunction(toLocation.longitude);
    
    float degree = radiandsToDegreesFunction(atan2(sin(tLng-fLng)*cos(tLat), cos(fLat)*sin(tLat)-sin(fLat)*cos(tLat)*cos(tLng-fLng)));
    
    if (degree >= 0) {
        return degree;
    } else {
        return 360+degree;
    }
}

- (void)stop
{
    [self.locationManager stopUpdatingLocation];
}

@end
