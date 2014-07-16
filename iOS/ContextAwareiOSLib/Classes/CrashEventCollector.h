//
//  CrashEventCollector.h
//  ContextAware
//
//  Created by Slim Besbes on 17/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "OnChangeCollector.h"

@interface CrashEventCollector : OnChangeCollector

void InstallUncaughtExceptionHandler();

@end
