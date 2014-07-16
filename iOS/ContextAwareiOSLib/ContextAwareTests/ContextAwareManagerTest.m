//
//  ContextAwareManagerTest.m
//  ContextAwareLib
//
//  Created by Slim Besbes on 28/01/13.
//  Copyright (c) 2013. All rights reserved.
//

#import <GHUnitIOS/GHUnit.h>
#import <OCMock/OCMock.h>
#import "ContextCollector.h"
#import "ContextAwareManager.h"

@interface ContextAwareManager()

- (void)initContextCollectors:(NSMutableArray *)aContextCollectorsList;
- (void)startCollection:(NSNotification *)notification;

@end


@interface ContextAwareManagerTest : GHTestCase
{
    id                  mockChannel;
    ContextAwareManager *contextAwareManager;
    NSMutableArray      *contextCollectors;
}

@end

@implementation ContextAwareManagerTest

- (void)setUpClass
{
    if (mockChannel == nil && contextAwareManager == nil) {
        mockChannel  = [OCMockObject mockForClass:[MNClientChannel class]];
        
        [[mockChannel expect] mapClass:[OCMArg any] withServerClassName:[OCMArg any]];
        
        [ContextAwareManager createManagerWithClientChannel:mockChannel];
        
        contextAwareManager = [ContextAwareManager sharedManager];
    }
}

- (void)testInitContextCollectors
{
    id mockCollector   = [OCMockObject mockForClass:[ContextCollector class]];
    id mockArray       = [OCMockObject mockForClass:[NSMutableArray class]];
    
    [[mockArray expect] addObject:mockCollector];
    
    for (int i = 0; i < [[mockArray expect] count]; i++) {
        (void) [[mockCollector expect] initWithClientChannel:[OCMArg any]];
    }
    
    [contextAwareManager initContextCollectors:mockArray];
    [mockCollector verify];
}

- (void)testStartCollection
{
    id mockCollector    = [OCMockObject mockForClass:[ContextCollector class]];
    id mockDictionary   = [OCMockObject mockForClass:[NSMutableDictionary class]];
    
    (void)[[mockCollector stub] initWithClientChannel:mockChannel];
    [[mockDictionary expect] setValue:mockCollector forKey:[OCMArg any]];
    
    for (int i = 0; i < [[mockDictionary expect] count]; i++) {
        
        [[[mockDictionary expect] andReturn:mockCollector] objectForKey:[OCMArg any]];
        [(ContextCollector *)[mockCollector expect] start];
    }
    
    [contextAwareManager startCollection:mockDictionary];
    [mockCollector verify];
}

- (void)testStopCollection
{
    id mockCollector    = [OCMockObject mockForClass:[ContextCollector class]];
    id mockDictionary   = [OCMockObject mockForClass:[NSMutableDictionary class]];
    
    (void)[[mockCollector stub] initWithClientChannel:mockChannel];
    [[mockDictionary expect] setValue:mockCollector forKey:[OCMArg any]];
    
    for (int i = 0; i < [[mockDictionary expect] count]; i++) {
        
        [[[mockDictionary expect] andReturn:mockCollector] objectForKey:[OCMArg any]];
        
        [(ContextCollector *)[mockCollector expect] stop];
    }
    
    [contextAwareManager stopContextCollection];
    [mockCollector verify];
}

- (void)testEnableContextElement
{
    id mockCollector  = [OCMockObject mockForClass:[ContextCollector class]];
    id mockDictionary = [OCMockObject mockForClass:[NSMutableDictionary class]];
    
    [[mockDictionary expect] objectForKey:[OCMArg any]];
    
    (void)[[mockCollector stub] initWithClientChannel:mockChannel];
    
    [(ContextCollector *)[mockCollector stub] start];
    
    [[mockDictionary expect] setValue:mockCollector forKey:[OCMArg any]];
    
    [contextAwareManager enableContextElement:@"Tagging"];
    
    [mockCollector verify];
}

- (void)testDisableContextElement
{
    id mockCollector   = [OCMockObject mockForClass:[ContextCollector class]];
    id mockDictionary  = [OCMockObject mockForClass:[NSDictionary class]];
    
    [[[mockDictionary expect] andReturn:mockCollector] objectForKey:[OCMArg any]];
    [(ContextCollector *)[mockCollector stub] stop];
    
    [contextAwareManager disableContextElement:@"Tagging"];
    [mockCollector verify];
}

- (void)testRegisterContextCollector
{
    ContextCollector *collector = [[ContextCollector alloc] initWithClientChannel:mockChannel];
    
    id mockCollector = [OCMockObject partialMockForObject:collector];
    
    [(ContextCollector *)[mockCollector expect]start];
    
    [contextAwareManager registerContextCollector:mockCollector];
    
    [mockCollector verify];
}

- (void)testUnRegisterContextCollector
{
    id mockCollector = [OCMockObject mockForClass:[ContextCollector class]];
    [(ContextCollector *)[mockCollector expect]stop];
    
    [contextAwareManager unRegisterContextCollector:mockCollector];
    
    [mockCollector verify];
}

@end
