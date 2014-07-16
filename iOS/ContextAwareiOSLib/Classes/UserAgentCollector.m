//
//  UserAgent.m
//  ContextAware
//
//  Created by Slim Besbes on 15/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "UserAgentCollector.h"

@implementation UserAgentCollector

- (ContextElement *)collect
{
    UIWebView *webView   = [[UIWebView alloc] init];
    NSString  *userAgent = [webView stringByEvaluatingJavaScriptFromString:USER_AGENT_NAVIGATOR];
    
    ContextElement *contextElement = [[ContextElement alloc] initWithContextType:USER_AGENT_KEY
                                                                     contextData:userAgent];
    return contextElement;
}

@end
