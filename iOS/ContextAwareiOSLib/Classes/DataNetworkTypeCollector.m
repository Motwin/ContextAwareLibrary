//
//  DataNetworkType.m
//  ContextAware
//
//  Created by Slim Besbes on 02/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "DataNetworkTypeCollector.h"
#import "ContextElement.h"
#import "MBReachability.h"

@interface DataNetworkTypeCollector() {
    
    MBReachability *reachability;
}

@property(nonatomic, strong) MBReachability *reachability;

@end

@implementation DataNetworkTypeCollector

@synthesize reachability;

- (void)start
{
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(handleNetworkTypeChange)
                                                 name:MBkReachabilityChangedNotification
                                               object:nil];
    
    self.reachability = [MBReachability reachabilityForInternetConnection];
    
    [self.reachability startNotifier];
    [self handleNetworkTypeChange];
}

- (void)handleNetworkTypeChange
{
    NetworkStatus status = [self.reachability currentReachabilityStatus];
    
    NSString *networkType;
    
    switch (status) {
            
        case NotReachable:
            networkType = OFF;
            break;
            
        case ReachableViaWiFi:
            networkType = WIFI;
            break;
            
        case ReachableViaWWAN:
            networkType = MOBILE;
            break;
            
        default:
            networkType = OTHER;
            break;
    }
    
    if(![networkType isEqualToString:OTHER]) {
        ContextElement *contextElement = [[ContextElement alloc] initWithContextType:NETWORK_TYPE_KEY
                                                                         contextData:networkType];
        
        [self onChange:contextElement];
    }
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)stop
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    [self.reachability stopNotifier];
}


@end
