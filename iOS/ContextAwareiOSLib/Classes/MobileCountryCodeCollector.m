//
//  MobileContryCode.m
//  ContextAware
//
//  Created by Slim Besbes on 02/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "MobileCountryCodeCollector.h"
#import <CoreTelephony/CTCarrier.h>
#import <CoreTelephony/CTTelephonyNetworkInfo.h>

@implementation MobileCountryCodeCollector

- (ContextElement *)collect
{
    CTTelephonyNetworkInfo *networkInfo = [[CTTelephonyNetworkInfo alloc] init];
    CTCarrier *carrier                  = [networkInfo subscriberCellularProvider];
    NSString *countryCode               = [carrier mobileCountryCode];
    
    if(!countryCode || [countryCode isEqualToString:NOT_AVAILABLE_CODE]) {
        countryCode = @"n.a";
    }
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:MOBILE_COUNTRY_CODE_KEY
                                                                     contextData:countryCode];
    
    return contextElement;
}

@end
