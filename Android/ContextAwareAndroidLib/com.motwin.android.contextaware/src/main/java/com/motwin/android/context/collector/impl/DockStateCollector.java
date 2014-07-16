/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Dock state on change collector
 * 
 */

public class DockStateCollector extends AbstractOnChangeCollector<Boolean> {

    private final BroadcastReceiver dockStateReceiver;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between context updates
     */
    public DockStateCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
        super(aClientChannel, aApplicationContext, aMinDelay);

        dockStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_DOCK_EVENT)) {
                    handleDockChange(intent);
                }
            }
        };
    }

    @Override
    public void start() {
        Intent sticky = getApplicationContext().registerReceiver(dockStateReceiver,
                new IntentFilter(Intent.ACTION_DOCK_EVENT));

        // sent initial DockState
        handleDockChange(sticky);
    }

    @Override
    public void stop() {
        getApplicationContext().unregisterReceiver(dockStateReceiver);

    }

    /**
     * Handle dock state change
     * 
     * @param intent
     *            The intent corresponding to Intent.ACTION_DOCK_EVENT
     */
    protected void handleDockChange(Intent intent) {
        boolean isDocked;
        if (intent == null) {
            isDocked = false;
        } else {
            int dockState = intent.getIntExtra(Intent.EXTRA_DOCK_STATE, Intent.EXTRA_DOCK_STATE_UNDOCKED);
            isDocked = dockState != Intent.EXTRA_DOCK_STATE_UNDOCKED;
        }
        ContextElement<Boolean> contextElement = new ContextElement<Boolean>(ContextAware.DOCK_SATE_KEY, isDocked);
        onChange(contextElement);

    }
}
