/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.extension.namespace.parser.test;

import java.util.concurrent.BlockingQueue;

import com.google.common.collect.Queues;
import com.google.common.eventbus.Subscribe;
import com.motwin.context.model.ContextElement;

/**
 * This ContextHandler is used to test the ContextBeanDefinitionParser and the
 * ContextDispatcher.
 * 
 * @author abdelbaki
 */
public class DummyContextHandler {

    /** The received context element. */
    private final BlockingQueue<ContextElement<?>> receivedContextElement;

    /**
     * Instantiates a new dummy context handler.
     */
    public DummyContextHandler() {
        receivedContextElement = Queues.newLinkedBlockingQueue();
    }

    /**
     * On context element event.
     * 
     * @param aContextElement
     *            the context element
     */
    @Subscribe
    public void onContextElementEvent(ContextElement<?> aContextElement) {
        receivedContextElement.add(aContextElement);

    }

    /**
     * Gets the received context element.
     * 
     * @return the received context element
     */
    public BlockingQueue<ContextElement<?>> getReceivedContextElement() {
        return receivedContextElement;
    }
}
