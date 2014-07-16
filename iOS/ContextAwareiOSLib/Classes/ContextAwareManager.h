//
//  ContextAwareManager.h
//  ContextAware
//
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MNClientChannel.h"
#import "ContextCollector.h"

/**
 * @brief A ContextAwareManager is the central object managing the contextAware librairie.
 * @version Available in Motwin ContextAware v1.0
 */
@interface ContextAwareManager : NSObject

/**
 * Returns an instance of singleton.
 */
+ (ContextAwareManager *)sharedManager;

/**
 * Create the singleton ContextAwareManager
 * @param channel The communication channel with Motwin server.
 * @version Available in Motwin ContextAware v1.0
 */
+ (void)createManagerWithClientChannel:(MNClientChannel *)aClientChannel;

/**
 * Create the singleton ContextAwareManager
 * @param aClientChannel The communication channel with Motwin server.
 * @param aDictionnary The dictionnary describing manager configuration
 * @version Available in Motwin ContextAware v1.0
 */
+ (void)createManagerWithClientChannel:(MNClientChannel *)aClientChannel withProperties:(NSDictionary *)aDictionary;

/**
 * Starts the contextAwareCollection.
 * @version Available in Motwin ContextAware v1.0
 */
- (void)startContextCollection;

/**
 * Suspend the contextAwareCollection.
 * @version Available in Motwin ContextAware v1.0
 */
- (void)stopContextCollection;

/**
 * Activate a context for key "aContextElementName".
 * If you chose to activate a ContextElement which has properties using this method,
 * the properties specified in the plist file will be used.
 * @version Available in Motwin ContextAware v1.0
 */
//- (void)enableContextElement:(NSString *)aContextElementName;

/**
 * Activate a periodic context element for key "aContextElementName" with properties aPropertiesList.
 * aPropertiesList is the list of properties associated to the contextElement 'aContextElementName'.
 * The key properties must be consistent with those used in the plist.
 * @version Available in Motwin ContextAware v1.0
 */
//- (void)enableContextElement:(NSString *)aContextElementName
//              withProperties:(NSDictionary *)aPropertiesList;

/**
 * Desactivate a context for key "aContextElementName"
 * @version Available in Motwin ContextAware v1.0
 */
//- (void)disableContextElement:(NSString *)aContextElementName;

/**
 * register a custom context Collector.
 * @param aContextCollector the custom collector.
 * @version Available in Motwin ContextAware v1.0
 */
- (void)registerContextCollector:(ContextCollector *)aContextCollector withKey:(NSString *) aKey;

/**
 * unRegister a custom context Collector.
 * @param aContextCollector the custom collector.
 * @version Available in Motwin ContextAware v1.0
 */
- (void)unRegisterContextCollectorForKey:(NSString *) aKey;

@end
