//
//  ContextCollector.h
//  ContextAware
//
//  Copyright (c) 2012 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "ContextElement.h"
#import "MNClientChannel.h"

/**
 * @brief ContextCollector contains methods for collecting, managing and sending contextElement.
 * @version Available in Motwin ContextAware v1.0
 */
@interface ContextCollector : NSObject
{
    MNClientChannel *clientChannel;
    NSDictionary *propertyList;
}

/**
 * The communication channel with Motwin server.
 */
@property(nonatomic, strong) MNClientChannel *clientChannel;

/**
 * The Properties's list as a dictionary
 */
@property(nonatomic, strong) NSDictionary *propertyList;

/**
 * Returns a new instance of the ContextCollector.
 * If you override this method, be sure to call the super.
 * @param  clientChannel.
 * @return ContextCollector instance.
 * @version Available in Motwin ContextAware v1.0
 */
- (ContextCollector *)initWithClientChannel:(MNClientChannel *)aClientChannel;

/**
 * Returns a new instance of the ContextCollector.
 * If you override this method, be sure to call the super.
 * @param  clientChannel.
 $ @param  propertyList.
 * @return ContextCollector instance.
 * @version Available in Motwin ContextAware v1.0
 */
- (ContextCollector *)initWithClientChannel:(MNClientChannel *)aClientChannel
                               propertyList:(NSDictionary *)aPropertyList;

/**
 * Starts the context collector.
 * Subclass can override this method to implement a specific treatment.
 * @version Available in Motwin ContextAware v1.0
 */
- (void)start;

/**
 * Suspends the context collector.
 * Subclass can override this method to implement a specific treatment.
 * @version Available in Motwin ContextAware v1.0
 */
- (void)stop;

/**
 * Send the context element to the Motwin server.
 * If you override this method, be sure to call the super.
 * @param aContextElement
 * @version Available in Motwin ContextAware v1.0
 */
- (void)sendContextElement:(ContextElement *)aContextElement;

@end
