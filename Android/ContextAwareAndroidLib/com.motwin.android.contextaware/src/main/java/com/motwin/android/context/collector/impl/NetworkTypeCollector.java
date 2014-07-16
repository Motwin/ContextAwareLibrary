/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Network type on change collector
 * 
 */
public class NetworkTypeCollector extends AbstractOnChangeCollector<String> {

    public static final String      NETWORK_TYPE_OFF = "OFF";
    private final BroadcastReceiver networkStateIntentReceiver;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between location updates
     */
    public NetworkTypeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
        super(aClientChannel, aApplicationContext, aMinDelay);
        networkStateIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                    onNetworkTypeChange(info);
                }
            }
        };
    }

    @Override
    public void start() {
        getApplicationContext().registerReceiver(networkStateIntentReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void stop() {
        getApplicationContext().unregisterReceiver(networkStateIntentReceiver);
    }

    /**
     * Handles network type change event
     * 
     * @param aInfo
     *            The new network info
     */
    protected void onNetworkTypeChange(NetworkInfo aInfo) {
        Preconditions.checkNotNull(aInfo, "aInfo cannot be null");

        String networkTypeValue = "";
        if (!aInfo.isConnectedOrConnecting()) {
            networkTypeValue = NETWORK_TYPE_OFF;
        } else {
            networkTypeValue = aInfo.getTypeName();
        }

        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.NETWORK_TYPE_KEY,
                networkTypeValue);
        onChange(contextElement);

    }
}
