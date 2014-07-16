/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.dispatcher;

import com.motwin.context.model.ContextElement;

/**
 * The Interface ContextDispatcher.
 * 
 * Will dispatch ContextEvent to any subscribed Handler.
 * 
 */
public interface ContextDispatcher {

    /**
     * Report a new ContextElement.
     * 
     * @param anElement
     *            the new ContextElement
     */
    void reportEvent(ContextElement<?> anElement);

    /**
     * Subscribe the given ContextHandler : after subscription, it will receive
     * all context information from all users.
     * 
     * @param aContextHandler
     *            the ContextHandler
     */
    void subscribe(Object aContextHandler);

    /**
     * Unsbscribe the given ContextHandler : the handler will stop receving
     * context information.
     * 
     * @param aContextHandler
     *            the ContextHandler
     */
    void unsubscribe(Object aContextHandler);
}
