//
//  ContextCollectorTest.m
//  ContextAwareLib
//
//  Created by Slim Besbes on 28/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//


#import <GHUnitIOS/GHUnit.h>
#import <OCMock/OCMock.h>
#import "ContextCollector.h"
#import "ContextAwareManager.h"
#import "MNMessage.h"
#import "ContextElement.h"

@interface ContextCollectorTest : GHTestCase
{
    id                  mockChannel;
    ContextAwareManager *contextAwareManager;
    ContextCollector    *contextCollector;
}

@end

@implementation ContextCollectorTest

- (void)setUpClass
{
    if (mockChannel == nil) {
       
        mockChannel      = [OCMockObject mockForClass:[MNClientChannel class]];
        contextCollector = [[ContextCollector alloc] initWithClientChannel:mockChannel];
    }
}

- (void)testSendContextElement
{
    BOOL (^messagetArgumentCheckBlock)(id) = ^BOOL(id value) {
        if (![value isKindOfClass:[MNMessage class]]) {
            return NO;
        }
        MNMessage *message = (MNMessage *)value;
        return ([message.type isEqualToString:@"ContextAware"] &&
                [message.content isKindOfClass:[ContextElement class]]);
    };    
     
    [[mockChannel expect] sendMessage:[OCMArg checkWithBlock:messagetArgumentCheckBlock]];
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:@"timeZone"
                                                                     contextData:@"Afrique"];
    
    [contextCollector sendContextElement:contextElement];
    
    [mockChannel verify];
}

@end
