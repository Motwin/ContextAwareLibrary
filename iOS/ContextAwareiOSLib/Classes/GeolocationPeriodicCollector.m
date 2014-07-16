//
//  PeriodicGeolocationCollector.m
//  ContextAware
//
//  Created by Slim Besbes on 16/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "GeolocationPeriodicCollector.h"
#import "GeoLocData.h"

@interface GeolocationPeriodicCollector() {
    
    CLLocationManager *locationManager;
    NSNumber          *periodicityValue;
    NSNumber          *accuracyValue;
    NSTimer           *timer;
    NSRunLoop         *runner;
}

@property(nonatomic, strong) CLLocationManager *locationManager;
@property(nonatomic, strong) NSNumber          *periodicityValue;
@property(nonatomic, strong) NSNumber          *accuracyValue;
@property(nonatomic, strong) NSTimer           *timer;
@property(nonatomic, strong) NSRunLoop         *runner;

@end

@implementation GeolocationPeriodicCollector

@synthesize locationManager;
@synthesize periodicityValue;
@synthesize accuracyValue;
@synthesize timer;
@synthesize runner;

- (void)start
{
    self.periodicityValue  = [self.propertyList objectForKey:PERIODICITY];
    self.accuracyValue     = [self.propertyList objectForKey:ACCURACY];
    
    self.timer = [NSTimer scheduledTimerWithTimeInterval:[self.periodicityValue intValue]
                                                  target:self
                                                selector:@selector(activateLocationManager)
                                                userInfo:nil
                                                 repeats:YES];
    
    self.runner = [NSRunLoop currentRunLoop];
    [self.runner addTimer:self.timer forMode:NSDefaultRunLoopMode];
    
}



- (void)activateLocationManager
{
    self.locationManager                 = [[CLLocationManager alloc] init];
    self.locationManager.desiredAccuracy = [accuracyValue doubleValue];
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
        
        ContextElement *geoLocContextElement = [[ContextElement alloc] initWithContextType:GEOLOCATION_PERIODIC_KEY
                                                                                           contextData:geolocationObject];
        
        [self sendContextElement:geoLocContextElement];
        
        [self.locationManager stopUpdatingLocation];
    }
}

- (float)calculateBearing:(CLLocationCoordinate2D)fromLocation toCoordinate:(CLLocationCoordinate2D)toLocation
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
    [self.timer invalidate];
}

@end
