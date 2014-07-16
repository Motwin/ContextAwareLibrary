//
//  TagCollector.h
//  ContextAwareLib
//
//  Created by product on 17/04/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "OnChangeCollector.h"

@interface TagCollector : OnChangeCollector

- (void)setTag:(NSString *)aTagName;


@end
