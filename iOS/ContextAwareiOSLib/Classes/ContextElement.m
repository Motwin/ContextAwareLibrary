//
//  ContextElementObject.m
//  ContextAware
//
//  Created by Slim Besbes on 27/12/12.
//  Copyright (c) 2012 Motwin. All rights reserved.
//

#import "ContextElement.h"

@implementation ContextElement

@synthesize type;
@synthesize data;

- (id)initWithContextType:(NSString *)aContextElementType
              contextData:(NSObject *)aContextElementData
{
    self = [super init];
    
    if (self) {
        
        NSAssert(aContextElementType, @"ContextElement Type cannot be null");
        NSAssert(aContextElementData, @"ContextElement Data cannot be null");
        
        self.type = aContextElementType;
        self.data = aContextElementData;
    }
    
    return self;
}

- (id)copyWithZone:(NSZone *)zone
{
    ContextElement *copyContextElement	= [[ContextElement allocWithZone:zone] init];
    
    copyContextElement->type	= [self.type copyWithZone:zone];
    copyContextElement->data	= [self.data copy];
	
	return copyContextElement;
}

@end
