//
//  TimeZone.m
//  ContextAware
//
//  Created by Slim Besbes on 10/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "TimeZoneCollector.h"

const int oneHour = 3600;

@implementation TimeZoneCollector

- (ContextElement *)collect
{
    NSInteger timeZoneOffset = [[NSTimeZone localTimeZone] secondsFromGMT];
    NSInteger offsetInHour   = timeZoneOffset / oneHour;
    
    NSString  *timeZone;
    
    if(offsetInHour > 0) {
        timeZone = [NSString stringWithFormat:@"GMT+%.2i:00", offsetInHour];
    }
    else {
        timeZone = [NSString stringWithFormat:@"GMT%.2i:00", offsetInHour];
    }

    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:TIMEZONE_KEY
                                                                     contextData:timeZone];
    
    return contextElement;
}

@end
