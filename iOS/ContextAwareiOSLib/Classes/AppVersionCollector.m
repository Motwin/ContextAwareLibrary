//
//  AppVersion.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "AppVersionCollector.h"

@implementation AppVersionCollector

- (ContextElement *)collect
{
    
    NSString *appVersion = [[[NSBundle mainBundle] infoDictionary] objectForKey:BUNDLE_VERSION];
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:APP_VERSION_KEY
                                                                     contextData:appVersion];
    return contextElement;
}

@end
