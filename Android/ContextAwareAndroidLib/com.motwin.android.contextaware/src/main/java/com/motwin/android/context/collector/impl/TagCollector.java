/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Tag collector
 */
public class TagCollector extends AbstractOnChangeCollector<String> {

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public TagCollector(ClientChannel aClientChannel, Context aApplicationContext) {
        super(aClientChannel, aApplicationContext, 0);
    }

    /**
     * Sets the current tag. Calling this method will cause the tag to be sent
     * to the server.
     * 
     * @param aTag
     *            the tag to send
     */
    public void setTag(String aTag) {
        Preconditions.checkNotNull(aTag, "aTag cannot be null");

        ContextElement<String> contextEelement = new ContextElement<String>(ContextAware.TAG_KEY, aTag);
        sendContextElement(contextEelement);
    }

    @Override
    public void start() {
        // Nothing to do
    }

    @Override
    public void stop() {
        // Nothing to do
    }

}
