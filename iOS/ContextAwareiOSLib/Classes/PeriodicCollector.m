//
//  PeriodicContext.m
//  ContextAware
//
//  Created by Slim Besbes on 27/12/12.
//  Copyright (c) 2012 Motwin. All rights reserved.
//

#import "PeriodicCollector.h"

@implementation PeriodicCollector

- (ContextElement *)collect
{
    [NSException raise:NSInternalInconsistencyException
                format:@"You must override %@ in a subclass", NSStringFromSelector(_cmd)];
    
    return NULL;
}

@end
