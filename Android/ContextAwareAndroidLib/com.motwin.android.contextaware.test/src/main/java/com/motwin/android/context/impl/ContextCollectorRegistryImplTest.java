/**
 * 
 */
package com.motwin.android.context.impl;

import static org.mockito.Mockito.mock;

import java.util.List;

import android.test.AndroidTestCase;

import com.google.common.collect.Lists;
import com.motwin.android.context.collector.Collector;

/**
 * @author lorie
 * 
 */
public class ContextCollectorRegistryImplTest extends AndroidTestCase {

    private List<Collector<?>>           collectors;
    private ContextCollectorRegistryImpl registry;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        collectors = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            collectors.add(mock(Collector.class, "Collector-" + i));
        }
        registry = new ContextCollectorRegistryImpl(collectors);
    }

    public void testStartCollectorsOnStart() {
        registry.start();
        // TODO : check that all collectors are started : cannot do this because
        // they are started in a handler
    }

    public void testStopCollectorsOnStop() {
        registry.stop();
        // TODO : check that all collectors are stopped : cannot do this because
        // they are started in a handler
    }

    public void testStartConnectorOnEnable() {
        final Collector<?> mockedCollector = mock(Collector.class, getName());
        registry.enableContextCollector(mockedCollector);

        // TODO : check that mockedCollector is started : cannot do this because
        // it is started in a handler

    }

    public void testStopConnectorOnDisable() {
        final Collector<?> mockedCollector = mock(Collector.class, getName());
        registry.enableContextCollector(mockedCollector);

        registry.disableContextCollector(mockedCollector);
        // TODO : check that mockedCollector is stopped : cannot do this because
        // it is started in a handler

    }

    public void testCannotStopDisabledCollector() {
        final Collector<?> mockedCollector = mock(Collector.class, getName());
        try {
            registry.disableContextCollector(mockedCollector);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected exception
        }

    }

    public void testCannotStartCollectorTwice() {
        final Collector<?> mockedCollector = mock(Collector.class, getName());
        registry.enableContextCollector(mockedCollector);

        try {
            registry.enableContextCollector(mockedCollector);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected exception
        }

    }
}
