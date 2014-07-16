/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOneShotCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Roaming one shot collector
 * 
 */
public class RoamingCollector extends AbstractOneShotCollector<Boolean> {
    private final TelephonyManager telephonyManager;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public RoamingCollector(ClientChannel aClientChannel, Context aApplicationContext) {
        this(aClientChannel, aApplicationContext, (TelephonyManager) aApplicationContext
                .getSystemService(Context.TELEPHONY_SERVICE));
    }

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aTelephonyManager
     *            The telephony manager
     */
    protected RoamingCollector(ClientChannel aClientChannel, Context aApplicationContext,
            TelephonyManager aTelephonyManager) {
        super(aClientChannel, aApplicationContext);
        telephonyManager = Preconditions
                .checkNotNull(
                        aTelephonyManager,
                        "aTelephonyManager cannot be null. Check that permission \"android.permission.READ_PHONE_STATE\" is declared in your AndroidManifest.xml");
    }

    @Override
    public ContextElement<Boolean> collect() {
        boolean isRoaming = telephonyManager.isNetworkRoaming();
        ContextElement<Boolean> contextElement = new ContextElement<Boolean>(ContextAware.ROAMING_KEY, isRoaming);
        return contextElement;
    }

}
