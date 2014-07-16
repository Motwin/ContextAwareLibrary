/**
 * 
 */
package com.motwin.android.context.impl;

import java.util.List;

import android.os.Handler;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.motwin.android.context.ContextCollectorRegistry;
import com.motwin.android.context.collector.Collector;
import com.motwin.android.log.Logger;

/**
 * This class is used to instantiate default collecter stored in the
 * configuration file in order to manage them
 * 
 * @author rihab
 * 
 */
public final class ContextCollectorRegistryImpl implements ContextCollectorRegistry {

    private final static String      TAG = "ContextCollectorRegistry";
    private final List<Collector<?>> contextCollectors;

    public ContextCollectorRegistryImpl(List<Collector<?>> aContextCollectors) {
        super();
        Preconditions.checkNotNull(aContextCollectors, "aContextCollectors cannot be null");

        contextCollectors = Lists.<Collector<?>> newArrayList(aContextCollectors);
    }

    @Override
    public void start() {
        final List<Collector<?>> collectorsList = ImmutableList.copyOf(contextCollectors);

        for (final Collector<?> collector : collectorsList) {
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    try {
                        collector.start();
                    } catch (Exception e) {
                        contextCollectors.remove(collector);
                        Logger.e(TAG, "Unable to start collector " + collector, e);

                    }

                };
            });
        }
    }

    @Override
    public void stop() {
        final List<Collector<?>> collectorsList = ImmutableList.copyOf(contextCollectors);

        for (final Collector<?> collector : collectorsList) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        collector.stop();
                    } catch (Exception e) {
                        contextCollectors.remove(collector);
                        Logger.e(TAG, "Unable to stop collector " + collector, e);
                    }

                };
            });
        }

    }

    @Override
    public void enableContextCollector(final Collector<?> aContextCollector) {
        Preconditions.checkNotNull(aContextCollector, "aContextCollector cannot be null");
        Preconditions.checkArgument(!contextCollectors.contains(aContextCollector), "%s already enabled",
                aContextCollector);

        try {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    aContextCollector.start();
                };
            });
            contextCollectors.add(aContextCollector);
        } catch (Exception e) {
            Logger.e(TAG, "Unable to start collector " + aContextCollector, e);
        }

    }

    @Override
    public void disableContextCollector(final Collector<?> aContextCollector) {
        Preconditions.checkNotNull(aContextCollector, "aContextCollector cannot be null");
        Preconditions.checkArgument(contextCollectors.contains(aContextCollector), "%s not enabled", aContextCollector);

        try {
            contextCollectors.remove(aContextCollector);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    aContextCollector.stop();
                };
            });
        } catch (Exception e) {
            Logger.e(TAG, "Unable to start collector " + aContextCollector, e);
        }

    }

}
