/**
 * 
 */
package com.motwin.android.context.collector.impl;

import java.util.Locale;

import android.content.Context;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOneShotCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Language one shot collector
 * 
 */
public class LanguageCollector extends AbstractOneShotCollector<String> {

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public LanguageCollector(ClientChannel aClientChannel, Context aApplicationContext) {
        super(aClientChannel, aApplicationContext);
    }

    @Override
    public ContextElement<String> collect() {
        String currentLanguage = Locale.getDefault().getLanguage();
        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.LANGUAGE_KEY, currentLanguage);
        return contextElement;
    }

}
