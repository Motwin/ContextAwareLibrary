//
//  ContextAwareManager.m
//  ContextAware
//
//  Created by Slim Besbes on 27/12/12.
//  Copyright (c) 2012 Motwin. All rights reserved.
//

#import "ContextAwareManager.h"
#import "MNConstants.h"
#import "ContextCollector.h"
#import "ContextElement.h"
#import "PeriodicCollector.h"
#import "MotwinLogger.h"

static ContextAwareManager *sharedInstance = nil;

@interface ContextAwareManager() {
    // the list of handled collector' instances
    NSMutableDictionary  *contextCollectors;

    
    
    MNClientChannel      *clientChannel;
    BOOL                 isStarted;
}


@property(nonatomic, strong) NSMutableDictionary   *contextCollectors;
@property(nonatomic, strong) MNClientChannel       *clientChannel;
@property(nonatomic, assign) BOOL                  isStarted;
@property(nonatomic, assign) NSDictionary          *contextDictionary;

@end

@implementation ContextAwareManager

@synthesize clientChannel;
@synthesize contextCollectors;
@synthesize isStarted;
@synthesize contextDictionary;

- (ContextAwareManager *)initWithClientChannel:(MNClientChannel *)aClientChannel
{
    self = [super init];
    NSMutableArray        *collectorConfs;
    NSMutableArray        *periodicCollectorConfs;
    if(self) {
        
        // check and store client channel
        NSAssert(aClientChannel != nil, @"Client Channel cannot be null");
        self.clientChannel              = aClientChannel;        
        [self.clientChannel mapClass:[ContextElement class] withServerClassName:MAPCLASS_NAME];
        [self.clientChannel makeAvailableWithKey:CHANNEL_NAME];
        
        // init collectorManager
        self.contextCollectors    = [[NSMutableDictionary alloc] init];
        collectorConfs  = [[NSMutableArray alloc] init];
        periodicCollectorConfs  = [[NSMutableArray alloc] init];
        contextDictionary = [NSDictionary dictionary];
        
        // check if we can find a configuration file.
        NSString  *resourcesPath  = [[NSBundle mainBundle] pathForResource:CONTEXT_AWARE_FILE
                                                              ofType:@"plist"];
        if (resourcesPath != NULL) {
            // if yes, try build collectors from that configuration file.
            self.contextDictionary = [NSDictionary dictionaryWithContentsOfFile:resourcesPath];
        
            collectorConfs  = [[NSMutableArray alloc] initWithArray:[self.contextDictionary allKeysForObject:[NSNumber numberWithBool:YES]]];
        
            NSArray *contextElementValueList    = [self.contextDictionary allValues];
            NSArray *contextElementKeyList      = [self.contextDictionary allKeys];
        
            [self checkActivationProperties:contextElementKeyList
                             withValues:contextElementValueList];
            
            for (int i = 0; i < [contextElementValueList count]; i++) {
            
                if([[contextElementValueList objectAtIndex:i] isKindOfClass:[NSDictionary class]] && [[[contextElementValueList objectAtIndex:i] objectForKey:ACTIVATED] boolValue]) {
                
                    NSDictionary *periodicCollector = @{[contextElementKeyList objectAtIndex:i] : [contextElementValueList objectAtIndex:i]};
                    [periodicCollectorConfs addObject:periodicCollector];
                }
            }
        }
        [self initContextCollectors:collectorConfs];
        [self initPeriodicContextCollectors:periodicCollectorConfs];
        self.isStarted = NO;
    }
    
    return self;
}

- (ContextAwareManager *)initWithClientChannel:(MNClientChannel *)aClientChannel withProperties:(NSDictionary *) aDictionary
{
    self = [super init];
    NSMutableArray        *collectorConfs;
    NSMutableArray        *periodicCollectorConfs;
    if(self) {
        
        NSAssert(aClientChannel != nil, @"Client Channel cannot be null");
        NSAssert(aDictionary != nil, @"Dictionnary cannot be null");
        
        self.clientChannel              = aClientChannel;
        self.contextCollectors    = [[NSMutableDictionary alloc] init];
        periodicCollectorConfs  = [[NSMutableArray alloc] init];
        
        [self.clientChannel mapClass:[ContextElement class] withServerClassName:MAPCLASS_NAME];
        [self.clientChannel makeAvailableWithKey:CHANNEL_NAME];
                
        collectorConfs  = [[NSMutableArray alloc] initWithArray:[aDictionary allKeysForObject:[NSNumber numberWithBool:YES]]];
        
        NSArray *contextElementValueList    = [aDictionary allValues];
        NSArray *contextElementKeyList      = [aDictionary allKeys];
        
        [self checkActivationProperties:contextElementKeyList
                             withValues:contextElementValueList];
        
        for (int i = 0; i < [contextElementValueList count]; i++) {
            
            if([[contextElementValueList objectAtIndex:i] isKindOfClass:[NSDictionary class]] && [[[contextElementValueList objectAtIndex:i] objectForKey:ACTIVATED] boolValue]) {
                
                NSDictionary *periodicCollector = @{[contextElementKeyList objectAtIndex:i] : [contextElementValueList objectAtIndex:i]};
                [periodicCollectorConfs addObject:periodicCollector];
            }
        }
        
        [self initContextCollectors:collectorConfs];
        [self initPeriodicContextCollectors:periodicCollectorConfs];
        self.isStarted = NO;
    }
    
    return self;
}

+ (ContextAwareManager *)sharedManager
{
    NSAssert(sharedInstance != nil,
             @"You must first use 'createManagerWithClientChannel:'");
    
    return sharedInstance;
}

+ (void)createManagerWithClientChannel:(MNClientChannel *)aClientChannel
{
    @synchronized([ContextAwareManager class]) {
        
		if (sharedInstance == nil){
            sharedInstance = [[ContextAwareManager alloc] initWithClientChannel:aClientChannel];
		}
	}
}

+ (void)createManagerWithClientChannel:(MNClientChannel *)aClientChannel withProperties:(NSDictionary *)aDictionary
{
    @synchronized([ContextAwareManager class]) {
        
		if (sharedInstance == nil){
            sharedInstance = [[ContextAwareManager alloc] initWithClientChannel:aClientChannel withProperties:aDictionary];
		}
	}
}
#pragma mark - init

- (void)initContextCollectors:(NSMutableArray *)aContextCollectorsList
{
    for (int i = 0; i < [aContextCollectorsList count]; i++) {
        
        NSString *className = [NSString stringWithFormat:@"%@%@",[aContextCollectorsList objectAtIndex:i], COLLECTOR_SUFFIX];
        
        NSAssert(NSClassFromString(className) != NULL, @"%@ does not exists", [aContextCollectorsList objectAtIndex:i]);
        
        ContextCollector *contextCollector = [[NSClassFromString(className) alloc] initWithClientChannel:self.clientChannel];
        
        [self.contextCollectors setObject:contextCollector
                                         forKey:[aContextCollectorsList objectAtIndex:i]];
    }
}

- (void)initPeriodicContextCollectors:(NSMutableArray *)aPeriodicContextCollectorsList
{
    for (int i = 0; i < [aPeriodicContextCollectorsList count]; i++) {
        
        NSString     *collectorName       = [[[aPeriodicContextCollectorsList objectAtIndex:i] allKeys] objectAtIndex:0];
        NSDictionary *collectorProperties = [[aPeriodicContextCollectorsList objectAtIndex:i] objectForKey:collectorName];
        
        NSString *className = [NSString stringWithFormat:@"%@%@", collectorName, COLLECTOR_SUFFIX];
        
        NSAssert(NSClassFromString(className) != NULL, @"%@ does not exists", collectorName);
        
        [self checkPeriodicCollectors:collectorName
                       withProperties:collectorProperties];
        
        ContextCollector *contextCollector = [[NSClassFromString(className) alloc] initWithClientChannel:self.clientChannel
                                                                                            propertyList:collectorProperties];
        
        [self.contextCollectors setObject:contextCollector forKey:collectorName];
    }
}

#pragma mark - start/stop methods

- (void)startContextCollection
{
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(startCollection:)
                                                 name:MNClientChannelSessionDidOpenNotification
                                               object:nil];
    
    if(!self.isStarted) {
        if([self.clientChannel isConnected] == YES) {
            [self startCollection:nil];
        }
    }
}

- (void)startCollection:(NSNotification *)notification
{
    
    if (! self.isStarted){
        NSArray *collectorObjectList = [self.contextCollectors allValues];
    
        for (int i = 0; i < [collectorObjectList count]; i++) {
        
            ContextCollector *contextCollector = [collectorObjectList objectAtIndex:i];
            [contextCollector start];

        }
        self.isStarted = YES;
        

        MFDebugLog(@"DEBUG CONTEXTAWARE: %i ContextElements are started.", [self.contextCollectors count]);

    } else {
        MFDebugLog(@"DEBUG CONTEXTAWARE: %i ContextElements already started.", [self.contextCollectors count]);
        
    }

}

- (void)stopContextCollection
{
    
    if (self.isStarted){
        self.isStarted = NO;
    
        for (int i = 0; i < [[self.contextCollectors allValues] count]; i++) {
        
            ContextCollector *contextCollector = [[self.contextCollectors allValues] objectAtIndex:i];
            [contextCollector stop];

        }
    
        MFDebugLog(@"DEBUG CONTEXTAWARE: %i ContextElements are stopped.",[[self.contextCollectors allValues] count]);
    } else {
        MFDebugLog(@"DEBUG CONTEXTAWARE: %i ContextElements already stopped.",[[self.contextCollectors allValues] count]);
        
    }
    
}

//#pragma mark - enable/disable ContextElement methods
/*
- (void)enableContextElement:(NSString *)aContextElementName
{
    // check we have a context collector class for this 
    NSString *className = [NSString stringWithFormat:@"%@%@", aContextElementName, COLLECTOR_SUFFIX];
    NSAssert(NSClassFromString(className) != NULL, @"%@ does not exists", aContextElementName);
        
    ContextCollector *contextCollector;    
    NSDictionary *plistFileContent = self.contextDictionary;
    
    if([[plistFileContent valueForKey:aContextElementName] isKindOfClass:[NSDictionary class]]) {
        
        NSDictionary *collectorProperties = [plistFileContent valueForKey:aContextElementName];        
        contextCollector = [[NSClassFromString(className) alloc] initWithClientChannel:self.clientChannel
                                                                          propertyList:collectorProperties];        
        NSDictionary *periodicCollector = @{aContextElementName : collectorProperties};        
        [self.periodicCollectorConfs addObject:periodicCollector];
    }
    else {
        contextCollector = [[NSClassFromString(className) alloc] initWithClientChannel:self.clientChannel];
        [self.collectorConfs addObject:contextCollector];
    }
    
    [self.contextCollectors setObject:contextCollector forKey:aContextElementName];
    
    if(self.isStarted) {
        [contextCollector start];
        
    }
    
#ifdef DEBUG
    NSLog(@"DEBUG CONTEXTAWARE: %@ ContextElements is enabled", aContextElementName);
#endif
    
}
*/
/*
- (void)enableContextElement:(NSString *)aContextElementName withProperties:(NSDictionary *)aPropertiesList
{
    NSString *className = [NSString stringWithFormat:@"%@%@", aContextElementName, COLLECTOR_SUFFIX];
    
    NSAssert(NSClassFromString(className) != NULL, @"%@ does not exists", aContextElementName);
        
    if (![self.contextCollectors objectForKey:aContextElementName]){
        [self checkPeriodicCollectors:aContextElementName withProperties:aPropertiesList];
    
        ContextCollector *contextCollector = [[NSClassFromString(className) alloc] initWithClientChannel:self.clientChannel
                                                                                        propertyList:aPropertiesList];
    
        [self.periodicCollectorConfs addObject:contextCollector];
        [self.contextCollectors setObject:contextCollector forKey:aContextElementName];
        
        if(self.isStarted) {
            [contextCollector start];
        }
    }
    
#ifdef DEBUG
    NSLog(@"DEBUG CONTEXTAWARE: %@ ContextElements is enabled", aContextElementName);
#endif
}

 */
/*
- (void)disableContextElement:(NSString *)aContextElementName
{
    
    ContextCollector *contextCollector = [self.contextCollectors objectForKey:aContextElementName];
    [contextCollector stop];
    
    [self.contextCollectors removeObjectForKey:aContextElementName];
    
#ifdef DEBUG
    NSLog(@"DEBUG CONTEXTAWARE: %@ ContextElements is disabled", aContextElementName);
#endif
}
*/
#pragma mark - register/unRegister ContextCollectors methods

- (void)registerContextCollector:(ContextCollector *)aContextCollector withKey:(NSString *) aKey
{

    NSAssert([self.contextCollectors objectForKey:aKey] == NULL, @"A ContextCollector already exists for key : %@ ", aKey);

    [self.contextCollectors setObject:aContextCollector forKey:aKey];
    
    if (self.isStarted){
        [aContextCollector start];
    }
    
#ifdef DEBUG
    NSLog(@"DEBUG CONTEXTAWARE: Custom Context Collector is registered");
#endif
}

- (void)unRegisterContextCollectorForKey:(NSString *) aKey
{
    ContextCollector *aContextCollector = [self.contextCollectors objectForKey:aKey];
    [aContextCollector stop];
    [self.contextCollectors removeObjectForKey:aKey];
    
#ifdef DEBUG
    NSLog(@"DEBUG CONTEXTAWARE: Custom Context Collector is unregistered");
#endif
}

#pragma mark - checking methods

- (void)checkPeriodicCollectors:(NSString *)aCollectorName withProperties:(NSDictionary *)aPropertiesList
{
    if([aPropertiesList objectForKey:PERIODICITY]) {
        
        NSAssert([[aPropertiesList objectForKey:PERIODICITY] isKindOfClass:[NSNumber class]],
                                                    @"The Periodicity property for %@ contextElement must be an number", aCollectorName);
    }
    
    if([aPropertiesList objectForKey:ACCURACY]) {
        
        NSAssert([[aPropertiesList objectForKey:ACCURACY] isKindOfClass:[NSNumber class]],
                                                    @"The Accuracy property for %@ contextElement must be an number", aCollectorName);
    }
    
    if([aPropertiesList objectForKey:DISTANCE_FILTER]) {
        
        NSAssert([[aPropertiesList objectForKey:DISTANCE_FILTER] isKindOfClass:[NSNumber class]],
                                                   @"The Distance property for %@ contextElement must be an number", aCollectorName);
    }
    
    
    if([aCollectorName isEqualToString:BATTERY_LEVEL]) {
        NSAssert([aPropertiesList objectForKey:PERIODICITY] != NULL,
                 @"BatteryLevel contextElement must contains Periodicity property");
    }
    
    else if ([aCollectorName isEqualToString:ACCELEROMETER]) {
        NSAssert([aPropertiesList objectForKey:PERIODICITY] != NULL ,
                 @"AccelerometerData contextElement must contains Periodicity property");
    }
    
    else if ([aCollectorName isEqualToString:GEOLOCATION]) {
        NSAssert([aPropertiesList objectForKey:DISTANCE_FILTER] != NULL && [aPropertiesList objectForKey:ACCURACY] != NULL,
                 @"Geolocation contextElement must contains Distance and Accuracy properties");
    }
    
    else if ([aCollectorName isEqualToString:GEOLOCATION_PERIODIC]) {
        NSAssert([aPropertiesList objectForKey:PERIODICITY] != NULL && [aPropertiesList objectForKey:ACCURACY] != NULL,
                 @"PeriodicGeolocation contextElement must contains Periodicity and Accuracy properties");
    }
}

- (void)checkActivationProperties:(NSArray *)aPlistFilekeys withValues:(NSArray *)aPlistFileValues
{
    for (int i = 0; i < [aPlistFileValues count]; i++) {
        
        if([[aPlistFileValues objectAtIndex:i] isKindOfClass:[NSDictionary class]]) {
            
            if([[aPlistFileValues objectAtIndex:i] objectForKey:ACTIVATED]) {
                
                if(![[[aPlistFileValues objectAtIndex:i] objectForKey:ACTIVATED] isKindOfClass:[[[NSNumber numberWithBool:YES] class] class]] &&
                   ![[[aPlistFileValues objectAtIndex:i] objectForKey:ACTIVATED] isKindOfClass:[[[NSNumber numberWithBool:NO] class] class]]) {
#ifdef DEBUG
                    NSLog(@"DEBUG CONTEXTAWARE: Activated property for %@ contextElement must be a boolean value", [aPlistFilekeys objectAtIndex:i]);
#endif
                }
            }
            else {
#ifdef DEBUG
                NSLog(@"DEBUG CONTEXTAWARE: %@ must have Activated property", [aPlistFilekeys objectAtIndex:i]);
#endif
            }
        }
        else {
            if(![[aPlistFileValues objectAtIndex:i] isKindOfClass:[[[NSNumber numberWithBool:YES] class] class]] &&
               ![[aPlistFileValues objectAtIndex:i] isKindOfClass:[[[NSNumber numberWithBool:NO] class] class]]) {
                
#ifdef DEBUG
                NSLog(@"DEBUG CONTEXTAWARE: %@ must be a boolean value", [aPlistFilekeys objectAtIndex:i]);
#endif
                
            }
        }
    }
}

@end
