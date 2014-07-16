//
//  MobileNetworkCode.m
//  ContextAware
//
//  Created by Slim Besbes on 09/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "MobileNetworkCodeCollector.h"
#import <CoreTelephony/CTCarrier.h>
#import <CoreTelephony/CTTelephonyNetworkInfo.h>

@implementation MobileNetworkCodeCollector

- (ContextElement *)collect
{
    CTTelephonyNetworkInfo *networkInfo = [[CTTelephonyNetworkInfo alloc] init];
    CTCarrier *carrier                  = [networkInfo subscriberCellularProvider];
    NSString  *networkCode              = [carrier mobileNetworkCode];
    
    if(!networkCode || [networkCode isEqualToString:NOT_AVAILABLE_CODE]) {
        networkCode = @"n.a";
    }
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:MOBILE_NETWORK_CODE_KEY
                                                                     contextData:networkCode];
    
    return contextElement;
}

@end
