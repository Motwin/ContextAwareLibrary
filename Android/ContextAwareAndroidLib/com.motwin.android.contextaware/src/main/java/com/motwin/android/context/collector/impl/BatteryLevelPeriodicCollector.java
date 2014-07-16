/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractPeriodicCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Battery level periodic collector.
 * 
 */
public class BatteryLevelPeriodicCollector extends AbstractPeriodicCollector<String> {

    /**
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aPeriodInMilliSeconds
     *            The delay between context updates
     */
    public BatteryLevelPeriodicCollector(ClientChannel aClientChannel, Context aApplicationContext,
            long aPeriodInMilliSeconds) {
        super(aClientChannel, aApplicationContext, aPeriodInMilliSeconds);
    }

    @Override
    public ContextElement<String> collect() {
        Intent intent = getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        int level;
        level = getLevel(intent);

        int scale;
        scale = getScale(intent);

        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.BATTERY_LEVEL_KEY,
                String.format("%d/%d", level, scale));
        return contextElement;
    }

    /**
     * Get battery level from intent
     * 
     * @param aIntent
     *            The intent for Intent.ACTION_BATTERY_CHANGED
     * @return The battery level
     */
    protected int getLevel(Intent aIntent) {
        int level = 0;
        if (aIntent != null) {
            level = aIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        }
        return level;
    }

    /**
     * Get battery scale from intent
     * 
     * @param aIntent
     *            The intent for Intent.ACTION_BATTERY_CHANGED
     * @return The battery scale
     */
    protected int getScale(Intent aIntent) {
        int level = 0;
        if (aIntent != null) {
            level = aIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
        }
        return level;
    }

    @Override
    public void onStart() {
        // NOOP

    }

    @Override
    public void onStop() {
        // NOOP

    }

}
