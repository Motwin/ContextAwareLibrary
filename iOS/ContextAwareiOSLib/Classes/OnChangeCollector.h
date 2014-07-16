//
//  OnChangeCollector.h
//  ContextAware
//
//  Created by Slim Besbes on 02/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ContextCollector.h"

@interface OnChangeCollector : ContextCollector

- (void)onChange:(ContextElement *)aContextElement;

@end
