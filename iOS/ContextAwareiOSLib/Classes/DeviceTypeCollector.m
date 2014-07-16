//
//  DeviceType.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "DeviceTypeCollector.h"

@implementation DeviceTypeCollector

- (ContextElement *)collect
{
    NSString *deviceType = [[UIDevice currentDevice] model];
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:DEVICE_TYPE_KEY
                                                                     contextData:deviceType];
    return contextElement;
}

@end
