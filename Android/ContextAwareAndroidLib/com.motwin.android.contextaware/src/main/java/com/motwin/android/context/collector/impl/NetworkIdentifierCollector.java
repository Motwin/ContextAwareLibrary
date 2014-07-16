/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOneShotCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.log.Logger;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Network identifier one shot collector
 */

public class NetworkIdentifierCollector extends AbstractOneShotCollector<String> {

    private final TelephonyManager telephonyManager;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public NetworkIdentifierCollector(ClientChannel aClientChannel, Context aApplicationContext) {
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
    protected NetworkIdentifierCollector(ClientChannel aClientChannel, Context aApplicationContext,
            TelephonyManager aTelephonyManager) {
        super(aClientChannel, aApplicationContext);
        telephonyManager = Preconditions
                .checkNotNull(
                        aTelephonyManager,
                        "aTelephonyManager cannot be null. Check that permission \"android.permission.READ_PHONE_STATE\" is declared in your AndroidManifest.xml");
    }

    @Override
    public ContextElement<String> collect() {
        ContextElement<String> contextElement;

        GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();
        if (location == null) {
            Logger.w("NetworkIdentifierCollector", "Current cell location is not available");
            contextElement = null;

        } else {
            int cellId = location.getCid();
            contextElement = new ContextElement<String>(ContextAware.NETWORK_IDENTIFIER_KEY, String.valueOf(cellId));
        }

        return contextElement;
    }

}
