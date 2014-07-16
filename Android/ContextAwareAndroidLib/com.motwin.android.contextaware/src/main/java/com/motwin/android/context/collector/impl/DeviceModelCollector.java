/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOneShotCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Device model collector.
 * 
 */
public class DeviceModelCollector extends AbstractOneShotCollector<String> {

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public DeviceModelCollector(ClientChannel aClientChannel, Context aApplicationContext) {
        super(aClientChannel, aApplicationContext);
    }

    @Override
    public ContextElement<String> collect() {
        String deviceType = android.os.Build.MODEL;
        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.DEVICE_MODEL_KEY, deviceType);
        return contextElement;
    }

}
