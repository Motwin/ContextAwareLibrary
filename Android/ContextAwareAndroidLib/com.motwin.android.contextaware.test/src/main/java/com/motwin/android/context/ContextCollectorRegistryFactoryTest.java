/**
 * 
 */
package com.motwin.android.context;

import static org.mockito.Mockito.mock;

import java.util.List;

import android.test.AndroidTestCase;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;
import com.motwin.android.context.collector.Collector;
import com.motwin.android.context.model.AccelerometerData;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.model.GeoLocData;
import com.motwin.android.context.test.SpyChannel;

/**
 * @author lorie
 * 
 */
public class ContextCollectorRegistryFactoryTest extends AndroidTestCase {

    private SpyChannel         channel;
    private List<Collector<?>> collectors;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        channel = new SpyChannel();
        collectors = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            collectors.add(mock(Collector.class, "Collector" + i));
        }
    }

    public void testAddSerializablesOnBuildCollectorRegistry() {
        ContextCollectorRegistryFactory.build(channel, getContext(), collectors);

        BiMap<String, Class<?>> serializables;
        serializables = ImmutableBiMap.<String, Class<?>> of("ContextElement", ContextElement.class, "GeoLocData",
                GeoLocData.class, "AccelerometerData", AccelerometerData.class);
        assertEquals(serializables, channel.getSerializables());
    }
}
