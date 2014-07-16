package com.motwin.android.context;

import java.util.List;

import android.content.Context;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import com.motwin.android.context.collector.Collector;
import com.motwin.android.context.impl.ContextCollectorRegistryImpl;
import com.motwin.android.context.model.AccelerometerData;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.model.GeoLocData;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Factory for ContextCollectorRegistry
 * 
 */
public class ContextCollectorRegistryFactory {

    // Hide constructor
    private ContextCollectorRegistryFactory() {

    }

    /**
     * Build a new instance of Context Collector Registry.
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send the context
     *            elements
     * @param aApplicationContext
     *            The application context
     * @param aContextCollectors
     *            The list of context collectors
     * @return the ContextCollectorRegistry instance
     */
    public static ContextCollectorRegistry build(ClientChannel aClientChannel, Context aApplicationContext,
                                                 List<Collector<?>> aContextCollectors) {
        Preconditions.checkNotNull(aClientChannel, "aClientChannel cannot be null");
        Preconditions.checkNotNull(aContextCollectors, "aContextCollectors cannot be null");

        /* @formatter:off */
		aClientChannel.addSerializables(
		        ImmutableBiMap.<String, Class<?>> of(
		                "ContextElement", ContextElement.class,
		                "GeoLocData", GeoLocData.class, 
		                "AccelerometerData", AccelerometerData.class));
		/* @formatter:on */
        return new ContextCollectorRegistryImpl(aContextCollectors);
    }
}
