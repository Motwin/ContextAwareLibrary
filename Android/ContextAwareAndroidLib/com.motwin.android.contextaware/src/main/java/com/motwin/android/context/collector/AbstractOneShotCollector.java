/**
 * 
 */
package com.motwin.android.context.collector;

import android.content.Context;

import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Abstract one shot collector. This type of collector will automatically send
 * context element on startup.
 * 
 */
public abstract class AbstractOneShotCollector<T> extends AbstractCollector<T> {

    /**
     * Abstract constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send the context
     *            element
     * @param aApplicationContext
     *            The application context
     */
    public AbstractOneShotCollector(ClientChannel aClientChannel, Context aApplicationContext) {
        super(aClientChannel, aApplicationContext);
    }

    /**
     * Abstract method that must be implemented to retreive the ContextElement
     * to be forwarded to server on start.
     * 
     * @return The ContextElement. If null, nothing will be sent to the server.
     */
    public abstract ContextElement<T> collect();

    @Override
    public final void start() {
        ContextElement<T> contextElement = collect();
        if (contextElement != null) {
            sendContextElement(contextElement);
        }

    }

    @Override
    public final void stop() {
        // Nothing to do
    }

}
