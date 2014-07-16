//
//  DeviceOS.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "DeviceOSCollector.h"

@implementation DeviceOSCollector

- (ContextElement *)collect
{
    NSString *deviceOSName = [[UIDevice currentDevice] systemName];
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:DEVICE_OS_KEY
                                                                     contextData:deviceOSName];
    
    return contextElement;
}

@end
