package com.motwin.android.context.collector.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Charging state on change collector
 */
public class ChargingStateCollector extends AbstractOnChangeCollector<Boolean> {

    private final BroadcastReceiver batteryReceiver;

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
    public ChargingStateCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
        super(aClientChannel, aApplicationContext, aMinDelay);
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent anIntent) {
                handleBatterieChangeEvent(anIntent);

            }
        };
    }

    @Override
    public void start() {
        Intent intent = getApplicationContext().registerReceiver(batteryReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        // using sticky broadcast to init the charger type.
        if (intent != null) {
            handleBatterieChangeEvent(intent);
        }

    }

    @Override
    public void stop() {
        getApplicationContext().unregisterReceiver(batteryReceiver);

    }

    /**
     * Handle the batterie charging status change.
     * 
     * @param anIntent
     *            The intent corresponding to Intent.ACTION_BATTERY_CHANGED)
     */
    protected void handleBatterieChangeEvent(Intent anIntent) {
        Preconditions.checkNotNull(anIntent, "Intent cannot be null");

        int chargePlug = anIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        boolean isCharging = chargePlug != 0;

        ContextElement<Boolean> contextElement = new ContextElement<Boolean>(ContextAware.CHARGING_STATE_KEY,
                isCharging);
        onChange(contextElement);

    }
}
