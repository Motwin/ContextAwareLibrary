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
 * Charger type on change collector
 */
public class ChargerTypeCollector extends AbstractOnChangeCollector<String> {
    // value defined in API level 17
    public static final int         BATTERY_PLUGGED_WIRELESS = 4;

    public static final String      CHARGER_TYPE_USB         = "USB";
    public static final String      CHARGER_TYPE_AC          = "AC";
    public static final String      CHARGER_TYPE_WIRELESS    = "Wireless";
    public static final String      CHARGER_TYPE_NONE        = "None";

    private final BroadcastReceiver batteryReceiver;

    /**
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between context updates
     */
    public ChargerTypeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
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
     * Handle the batterie charger type change.
     * 
     * @param anIntent
     *            The intent corresponding to Intent.ACTION_BATTERY_CHANGED)
     */
    protected void handleBatterieChangeEvent(Intent anIntent) {
        Preconditions.checkNotNull(anIntent, "Intent cannot be null");
        int chargePlug = anIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        String chargeType;
        switch (chargePlug) {
            case BatteryManager.BATTERY_PLUGGED_USB:
                chargeType = CHARGER_TYPE_USB;
                break;
            case BatteryManager.BATTERY_PLUGGED_AC:
                chargeType = CHARGER_TYPE_AC;
                break;
            case BATTERY_PLUGGED_WIRELESS:
                chargeType = CHARGER_TYPE_WIRELESS;
                break;
            default:
                chargeType = CHARGER_TYPE_NONE;
                break;
        }

        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.CHARGER_TYPE_KEY, chargeType);
        onChange(contextElement);

    }
}
