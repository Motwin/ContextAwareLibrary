/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.dispatcher.impl;

import com.google.common.base.Preconditions;
import com.motwin.context.dispatcher.ContextDispatcher;
import com.motwin.context.model.ContextAwareConstants;
import com.motwin.context.model.ContextElement;
import com.motwin.context.model.MobileContextElement;
import com.motwin.sdk.application.channel.Channel;
import com.motwin.sdk.application.messaging.Message;
import com.motwin.sdk.application.messaging.MessageProcessor;

/**
 * The Class ContextMessageProcessor.
 * 
 * This class processes ContextAware specific Message and send it to the
 * contextDispatcher. ContextDispatcher is in charge to forward them to any
 * ContextHandler.
 * 
 */
public class ContextMessageProcessor implements MessageProcessor<ContextElement<?>> {

    /** The context dispatcher. */
    private ContextDispatcher contextDispatcher;

    /**
     * Instantiates a new context message processor.
     * 
     * @param aContextDispatcher
     *            the context dispatcher
     */
    public ContextMessageProcessor(ContextDispatcher aContextDispatcher) {
        contextDispatcher = aContextDispatcher;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.sdk.application.messaging.MessageProcessor#processMessage(
     * com.motwin.sdk.application.channel.Channel,
     * com.motwin.sdk.application.messaging.Message)
     */
    @Override
    public void processMessage(Channel aChannel, Message<ContextElement<?>> aContextElement) throws Exception {
        Preconditions.checkNotNull(aChannel, "aChannel cannot be null");
        Preconditions.checkNotNull(aContextElement, "aContextElement cannot be null");
        Preconditions.checkNotNull(aContextElement.getContent(), "aContextElement content cannot be null");

        ContextElement<?> contextElement;
        contextElement = aContextElement.getContent();

        MobileContextElement<?> mobileContextElement;
        mobileContextElement = MobileContextElement.copyOf(contextElement);
        if (aChannel.containsAttribute(ContextAwareConstants.KEY_INSTALLATION_ID)) {
            String installationId = aChannel.getAttribute(ContextAwareConstants.KEY_INSTALLATION_ID, String.class);
            mobileContextElement.setInstallationId(installationId);
        }

        contextDispatcher.reportEvent(mobileContextElement);
    }

    /**
     * Gets the context dispatcher.
     * 
     * @return ContextDispatcher the context dispatcher
     */
    public ContextDispatcher getContextDispatcher() {
        return contextDispatcher;
    }

    /**
     * Sets the context dispatcher.
     * 
     * @param aContextDispatcher
     *            the new context dispatcher
     */
    public void setContextDispatcher(ContextDispatcher aContextDispatcher) {
        contextDispatcher = aContextDispatcher;
    }
}
