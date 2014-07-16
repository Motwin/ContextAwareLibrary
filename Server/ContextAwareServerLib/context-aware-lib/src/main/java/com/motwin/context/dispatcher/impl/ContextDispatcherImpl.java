/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.dispatcher.impl;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.motwin.context.dispatcher.ContextDispatcher;
import com.motwin.context.model.ContextElement;

/**
 * The Class ContextDispatcherImpl.
 * 
 * implements the ContextDispatcher interface.
 */
public final class ContextDispatcherImpl implements ContextDispatcher {

    /** The dispatcher event bus. */
    private final EventBus dispatcherEventBus;

    /**
     * Instantiates a new context dispatcher.
     * 
     * @param aEventBus
     *            the event bus
     */
    public ContextDispatcherImpl(final EventBus aEventBus) {
        dispatcherEventBus = aEventBus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.context.extension.dispatcher.ContextDispatcher#reportEvent
     * (com.motwin.context.extension.events.ContextElementEvent)
     */
    @Override
    public void reportEvent(ContextElement<?> aEvent) {
        Preconditions.checkNotNull(aEvent, "Cannot dispatch a null event");
        dispatcherEventBus.post(aEvent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.context.contextDispatcherInterface.ContextDispatcherInterface
     * #subscribeHandler(java.lang.Object)
     */
    @Override
    public void subscribe(Object aContextHandler) {
        Preconditions.checkNotNull(aContextHandler, "aContextHandler cannot be null");
        dispatcherEventBus.register(aContextHandler);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.context.contextDispatcherInterface.ContextDispatcherInterface
     * #unsubscribeHandler(java.lang.Object)
     */
    @Override
    public void unsubscribe(Object aContextHandler) {
        Preconditions.checkNotNull(aContextHandler, "aContextHandler cannot be null");
        dispatcherEventBus.unregister(aContextHandler);
    }

}
