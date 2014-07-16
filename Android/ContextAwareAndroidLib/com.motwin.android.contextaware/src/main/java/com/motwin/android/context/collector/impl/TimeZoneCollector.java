/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOneShotCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.utils.TimeZoneHelper;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Time zone one shot collector
 * 
 */
public class TimeZoneCollector extends AbstractOneShotCollector<String> {

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public TimeZoneCollector(ClientChannel aClientChannel, Context aApplicationContext) {
        super(aClientChannel, aApplicationContext);
    }

    @Override
    public ContextElement<String> collect() {
        String timeZoneValue = TimeZoneHelper.retriveTimeZoneValue();

        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.TIME_ZONE_KEY, timeZoneValue);

        return contextElement;
    }

}
