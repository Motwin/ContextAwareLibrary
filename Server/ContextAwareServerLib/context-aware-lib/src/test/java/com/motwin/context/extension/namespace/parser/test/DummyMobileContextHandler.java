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
import com.motwin.context.model.MobileContextElement;

/**
 * This ContextHandler is used to test the ContextBeanDefinitionParser and the
 * ContextDispatcher.
 * 
 * @author abdelbaki
 */
public class DummyMobileContextHandler {

    /** The received context element. */
    private final BlockingQueue<MobileContextElement<?>> receivedContextElement;

    /**
     * Instantiates a new dummy mobile context handler.
     */
    public DummyMobileContextHandler() {
        receivedContextElement = Queues.newLinkedBlockingQueue();
    }

    /**
     * On context element event.
     * 
     * @param aContextElement
     *            the context element
     */
    @Subscribe
    public void onContextElementEvent(MobileContextElement<?> aContextElement) {
        receivedContextElement.add(aContextElement);

    }

    /**
     * Gets the received context element.
     * 
     * @return the received context element
     */
    public BlockingQueue<MobileContextElement<?>> getReceivedContextElement() {
        return receivedContextElement;
    }
}
