//
//  ContextElementObject.h
//  ContextAware
//
//  Copyright (c) 2012 Motwin. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * @brief ContextElement is the object sent to the Motwin Server.
 * It implements the NSCopying protocol.
 */
@interface ContextElement : NSObject<NSCopying>
{
    NSString *type;
    NSObject *data;
}

/**
 * The type of context element.
 */
@property(nonatomic, strong) NSString *type;

/**
 * The content of the context element.
 */
@property(nonatomic, strong) NSObject *data;


/**
 * Returns a new instance of the ContextElement.
 * @param  aContextElementType.
 * @param aContextElementData.
 * @version Available in Motwin ContextAware v1.0
 */
- (id)initWithContextType:(NSString *)aContextElementType
              contextData:(NSObject *)aContextElementData;

@end
