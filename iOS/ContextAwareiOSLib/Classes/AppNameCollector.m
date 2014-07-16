//
//  AppName.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "AppNameCollector.h"

@implementation AppNameCollector

- (ContextElement *)collect
{
    NSString *appName  = [[[NSBundle mainBundle] infoDictionary] objectForKey:BUNDLE_IDENTIFIER];
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:APP_NAME_KEY
                                                                     contextData:appName];
    return contextElement;
}

@end
