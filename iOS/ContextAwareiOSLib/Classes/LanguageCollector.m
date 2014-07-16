//
//  Language.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "LanguageCollector.h"

@implementation LanguageCollector

- (ContextElement *)collect
{
    NSString *languageID = [[NSLocale preferredLanguages] objectAtIndex:0];
        
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:LANGUAGE_KEY
                                                                     contextData:languageID];
    return contextElement;
}

@end
