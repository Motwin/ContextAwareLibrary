/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Screen orientation on change collector
 */

public class ScreenOrientationCollector extends AbstractOnChangeCollector<String> {

    public static final String      ORIENTATION_LANDSCAPE = "Landscape";
    public static final String      ORIENTATION_PORTRAIT  = "Portrait";
    public static final String      ORIENTATION_UNKNOWN   = "Unknown";

    private final BroadcastReceiver receiver;

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
    public ScreenOrientationCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
        super(aClientChannel, aApplicationContext, aMinDelay);

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context aContext, Intent aIntent) {
                Configuration config = getApplicationContext().getResources().getConfiguration();
                onConfigurationChanged(config.orientation);
            }
        };

    }

    @Override
    public void start() {
        getApplicationContext().registerReceiver(receiver, new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));
    }

    @Override
    public void stop() {
        getApplicationContext().unregisterReceiver(receiver);
    }

    /**
     * Handles configuration change event
     * 
     * @param aOrientation
     *            The new orientation
     */
    protected void onConfigurationChanged(int aOrientation) {
        String orientation = null;

        switch (aOrientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                orientation = ORIENTATION_LANDSCAPE;
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                orientation = ORIENTATION_PORTRAIT;
                break;
            default:
                orientation = ORIENTATION_UNKNOWN;
                break;
        }
        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.SCREEN_ORIENTATION_KEY,
                orientation);
        onChange(contextElement);

    }

}
