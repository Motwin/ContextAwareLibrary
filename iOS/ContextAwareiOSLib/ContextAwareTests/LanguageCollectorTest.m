//
//  LanguageCollectorTest.m
//  ContextAwareLib
//
//  Created by Slim Besbes on 29/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <GHUnitIOS/GHUnit.h>
#import <OCMock/OCMock.h>
#import "ContextElement.h"
#import "LanguageCollector.h"

@interface LanguageCollectorTest : GHTestCase
{
    id  mockChannel;
}

@end

@implementation LanguageCollectorTest

- (void)setUpClass
{
    if (mockChannel == nil) {
        mockChannel  = [OCMockObject mockForClass:[MNClientChannel class]];
        
    }
}


- (void)testCollect
{
    id mockArray       = [OCMockObject mockForClass:[NSMutableArray class]];
    id mockString      = [OCMockObject mockForClass:[NSString class]];
    id mockContext     = [OCMockObject mockForClass:[ContextElement class]];
    id mockCollector   = [OCMockObject mockForClass:[LanguageCollector class]];
    
    NSArray *localLanguage = [NSLocale preferredLanguages];
    
    (void)[[[mockArray expect] andReturn:mockArray] initWithArray:localLanguage];
    
    [[[mockArray expect] andReturn:mockString] objectAtIndex:0];
    
    (void)[[mockContext expect] initWithContextType:OCMOCK_ANY
                                        contextData:OCMOCK_ANY];
    
    LanguageCollector *collector = [[LanguageCollector alloc] initWithClientChannel:mockChannel];
    [collector collect];
    
    [mockCollector verify];
}

@end
