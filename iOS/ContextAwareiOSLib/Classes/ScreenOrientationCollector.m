//
//  ScreenOrientation.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "ScreenOrientationCollector.h"

@implementation ScreenOrientationCollector

- (void)start
{
    [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(orientationChanged)
                                                 name:UIDeviceOrientationDidChangeNotification
                                               object:nil];
    [self orientationChanged];
}

- (void)orientationChanged
{
    UIDevice *device = [UIDevice currentDevice];
    
    NSString *deviceOrientation;
    
    switch(device.orientation)
    {
        case UIDeviceOrientationPortraitUpsideDown:
            
            deviceOrientation = PORTRAIT_DOWN;
            break;
            
        case UIDeviceOrientationLandscapeLeft:
            
            deviceOrientation = LAND_SCAPE_LEFT;
            break;
            
        case UIDeviceOrientationLandscapeRight:
            
            deviceOrientation = LAND_SCAPE_RIGHT;
            break;
        case UIDeviceOrientationPortrait:
            
            deviceOrientation = PORTRAIT;
            break;
            
        default:
            
            deviceOrientation = ORIENTATION_UNKNOWN;
            break;
    };
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:SCREEN_ORIENTATION_KEY
                                                                     contextData:deviceOrientation];
    
    [self onChange:contextElement];
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}


- (void)stop
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    [[UIDevice currentDevice]  endGeneratingDeviceOrientationNotifications];
}

@end
