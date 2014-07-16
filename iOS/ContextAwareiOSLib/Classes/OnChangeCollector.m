//
//  OnChangeCollector.m
//  ContextAware
//
//  Created by Slim Besbes on 02/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "OnChangeCollector.h"

@implementation OnChangeCollector

- (void)onChange:(ContextElement *)aContextElement
{
    [self sendContextElement:aContextElement];
}

@end
