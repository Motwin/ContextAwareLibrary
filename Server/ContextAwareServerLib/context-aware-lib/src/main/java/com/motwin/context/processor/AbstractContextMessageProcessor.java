/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.processor;

import com.google.common.base.Preconditions;
import com.motwin.context.model.ContextAwareConstants;
import com.motwin.context.model.ContextElement;
import com.motwin.context.model.MobileContextElement;
import com.motwin.sdk.application.channel.Channel;
import com.motwin.sdk.application.messaging.Message;
import com.motwin.sdk.application.messaging.MessageProcessor;

/**
 * The Class AbstractContextMessageProcessor.
 * 
 * This class defines an abstract message processor dedicated to the processing
 * of ContextAware messages sent using the Motwin protocol.
 * 
 * the definition of such a processor have to be added to the application.xml as
 * follow :
 * <p>
 * 
 * <import resource="classpath:META-INF/motwin/motwin-context-model.xml" />
 * 
 * <message:processor type="ContextAware"
 * class="com.motwin.MyProcessorClass"></message:processor>
 * 
 * </p>
 * 
 * The MyProcessorClass will handle any type of message sent by the ContextAware
 * library for the specific Channel by implementing :
 * <p>
 * public abstract void ProcessContextElement(Channel aChannel,
 * ContextElement<?> aContextElement);
 * 
 * </p>
 */
public abstract class AbstractContextMessageProcessor implements MessageProcessor<ContextElement<?>> {

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
        ProcessContextElement(aChannel, mobileContextElement);
    }

    /**
     * Process context element.
     * 
     * @param aChannel
     *            the channel
     * @param aContextElement
     *            the context element
     */
    public abstract void ProcessContextElement(Channel aChannel, ContextElement<?> aContextElement);
}
