/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Dock type on change collector
 * 
 */

public class DockTypeCollector extends AbstractOnChangeCollector<String> {
    // Constant to define values available from API level 11
    public static final int         EXTRA_DOCK_STATE_LE_DESK = 3;
    public static final int         EXTRA_DOCK_STATE_HE_DESK = 4;

    public static final String      DOCK_TYPE_DESK           = "Desk";
    public static final String      DOCK_TYPE_CAR            = "Car";
    public static final String      DOCK_TYPE_NONE           = "None";
    public static final String      DOCK_TYPE_LE_DESK        = "Analog";
    public static final String      DOCK_TYPE_HE_DESK        = "Digital";
    public static final String      DOCK_TYPE_UNKNOWN        = "Unknown";

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
    public DockTypeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
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
        Context context = getApplicationContext();
        Preconditions.checkNotNull(context, "Context cannot be null at this stage");
        context.unregisterReceiver(dockStateReceiver);
    }

    /**
     * Handle Dock state change
     * 
     * @param intent
     *            The intent corresponding to Intent.ACTION_DOCK_EVENT
     */
    protected void handleDockChange(Intent intent) {
        int dockState;
        if (intent == null) {
            dockState = -1;
        } else {
            dockState = intent.getIntExtra(Intent.EXTRA_DOCK_STATE, 0);
        }
        String dockValue;
        switch (dockState) {
            case Intent.EXTRA_DOCK_STATE_UNDOCKED:
                dockValue = DOCK_TYPE_NONE;
                break;
            case Intent.EXTRA_DOCK_STATE_DESK:
                dockValue = DOCK_TYPE_DESK;
                break;
            case Intent.EXTRA_DOCK_STATE_CAR:
                dockValue = DOCK_TYPE_CAR;
                break;
            case EXTRA_DOCK_STATE_LE_DESK:
                dockValue = DOCK_TYPE_LE_DESK;
                break;
            case EXTRA_DOCK_STATE_HE_DESK:
                dockValue = DOCK_TYPE_HE_DESK;
                break;
            default:
                dockValue = DOCK_TYPE_UNKNOWN;
                break;
        }

        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.DOCK_TYPE_KEY, dockValue);
        onChange(contextElement);

    }
}
