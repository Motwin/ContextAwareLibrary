/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import android.content.Intent;
import android.os.BatteryManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;

/**
 * @author lorie
 * 
 */
public class BatteryLevelPeriodicCollectorTest extends MockitoTestCase {

    private SpyChannel                    channel;
    private BatteryLevelPeriodicCollector collector;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        channel = new SpyChannel();
        collector = new BatteryLevelPeriodicCollector(channel, getContext(), 100);
    }

    public void testGetLevelCollect() {
        ContextElement<String> result = collector.collect();
        assertEquals(ContextAware.BATTERY_LEVEL_KEY, result.getType());
    }

    public void testGetLevel() {
        Intent intent = mock(Intent.class);

        int level = 10;
        when(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)).thenReturn(level);

        int result = collector.getLevel(intent);
        assertEquals(level, result);

    }
}
