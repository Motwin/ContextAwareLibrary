/**
 * 
 */
package com.motwin.android.context.collector;

import android.content.Context;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.log.Logger;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Abstract Collector class. This abstract class defines how context elements
 * are sent through the ClientChannel. It must be extended by all Context
 * Collectors.
 */
public abstract class AbstractCollector<T> implements Collector<T> {

    private static final String TAG = AbstractCollector.class.getSimpleName();

    private final ClientChannel clientChannel;
    private final Context       applicationContext;

    /**
     * Abstract constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send the context
     *            elements
     * @param aApplicationContext
     *            The application context
     */
    public AbstractCollector(ClientChannel aClientChannel, Context aApplicationContext) {
        super();
        clientChannel = Preconditions.checkNotNull(aClientChannel, "aClientChannel cannot be null");
        applicationContext = Preconditions.checkNotNull(aApplicationContext, "aApplicationContext cannot be null");
    }

    @Override
    public void sendContextElement(ContextElement<T> aContextElement) {
        Preconditions.checkNotNull(clientChannel, "clientChannel cannot be null");
        Preconditions.checkNotNull(aContextElement, "The collected context element cannot be null");

        clientChannel.sendMessage(ContextAware.MESSAGE_TYPE, aContextElement);

        Logger.d(TAG, aContextElement.toString());

    }

    @Override
    public abstract void start();

    @Override
    public abstract void stop();

    /**
     * Get the application conext
     * 
     * @return the application context
     */
    public Context getApplicationContext() {
        return applicationContext;
    }

    /**
     * Get the client channel
     * 
     * @return the client channel
     */
    public ClientChannel getClientChannel() {
        return clientChannel;
    }

}