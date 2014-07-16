//
//  TagCollector.m
//  ContextAwareLib
//
//  Created by product on 17/04/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "TagCollector.h"

@implementation TagCollector


- (void)setTag:(NSString *)aTagName {
    ContextElement *tagContextElement = [[ContextElement alloc] initWithContextType:TAG_KEY
                                                                           contextData:aTagName];
    
    [self onChange:tagContextElement];
}

- (void)start
{
    
}

@end
