//
//  DeviceOSVersion.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "DeviceOSVersionCollector.h"

@implementation DeviceOSVersionCollector

- (ContextElement *)collect
{
    NSString *deviceOSVersion = [[UIDevice currentDevice] systemVersion];
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:DEVICE_OS_VERSION_KEY
                                                                     contextData:deviceOSVersion];
    return contextElement;
}

@end
