//
//  PeriodicContext.h
//  ContextAware
//
//  Created by Slim Besbes on 27/12/12.
//  Copyright (c) 2012 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ContextCollector.h"

@interface PeriodicCollector : ContextCollector

- (ContextElement *)collect;

@end
