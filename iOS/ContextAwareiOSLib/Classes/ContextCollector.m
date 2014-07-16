//
//  ContextCollector.m
//  ContextAware
//
//  Created by Slim Besbes on 02/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "ContextCollector.h"
#import "MNMessage.h"
#import "ContextCollector.h"

static NSString *MessageTypeContextAware = @"ContextAware";

@implementation ContextCollector

@synthesize clientChannel;
@synthesize propertyList;

- (ContextCollector *)initWithClientChannel:(MNClientChannel *)aClientChannel
{
    self = [super init];
    if(self) {
        self.clientChannel  = aClientChannel;
    }
    
    return self;
}

- (ContextCollector *)initWithClientChannel:(MNClientChannel *)aClientChannel
                               propertyList:(NSDictionary *)aPropertyList
{
    self = [super init];
    if(self) {
        self.clientChannel  = aClientChannel;
        self.propertyList   = [[NSDictionary alloc] initWithDictionary:aPropertyList];
    }
    
    return self;
}

- (void)start
{
    [NSException raise:NSInternalInconsistencyException
                format:@"You must override %@ in a subclass", NSStringFromSelector(_cmd)];
}

- (void)stop
{
    /**
     * Suspend the contextElement.
     * This method is overloaded in subclass.
     */
}

- (void)sendContextElement:(ContextElement *)aContextElement
{
    NSAssert(aContextElement != nil,
             @"ContextElement cannot be null");
   
    MNMessage *message = [MNMessage messageWithType:MessageTypeContextAware
                                            content:aContextElement];
    
    NSAssert(self.clientChannel != NULL,
             @"ClientChannel cannot be null");
    
    [self.clientChannel sendMessage:message];
    
#ifdef DEBUG
    NSLog(@"DEBUG CONTEXTAWARE: '%@' ContextElement is sent", aContextElement.type);
#endif
    
}

@end
