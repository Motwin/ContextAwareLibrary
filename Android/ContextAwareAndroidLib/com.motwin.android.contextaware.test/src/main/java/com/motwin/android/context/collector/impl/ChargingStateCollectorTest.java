/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class ChargingStateCollectorTest extends MockitoTestCase {
    private SpyChannel             channel;
    private ChargingStateCollector collector;
    @Mock
    private Context                context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new ChargingStateCollector(channel, context, 1000);
    };

    public void testStartRegistersListener() {
        collector.start();

        verify(context).registerReceiver(any(BroadcastReceiver.class), argThat(new ArgumentMatcher<IntentFilter>() {

            @Override
            public boolean matches(Object aArgument) {
                boolean result;
                if (aArgument instanceof IntentFilter) {
                    IntentFilter intentFilter = (IntentFilter) aArgument;
                    result = intentFilter.hasAction(Intent.ACTION_BATTERY_CHANGED);
                } else {
                    result = false;
                }
                return result;
            }
        }));
    }

    public void testStopUnregistersListener() {
        collector.stop();

        verify(context).unregisterReceiver(any(BroadcastReceiver.class));
    }

    public void testHandleCharging() throws InterruptedException {
        Intent intent = mock(Intent.class);

        when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)).thenReturn(0);

        collector.handleBatterieChangeEvent(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> element = (ContextElement<?>) messages.get(0).getContent();

        assertEquals(ContextAware.CHARGING_STATE_KEY, element.getType());
        assertEquals(Boolean.FALSE, element.getData());
    }

    public void testHandleNotCharging() throws InterruptedException {
        Intent intent = mock(Intent.class);

        when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)).thenReturn(1);

        collector.handleBatterieChangeEvent(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> element = (ContextElement<?>) messages.get(0).getContent();

        assertEquals(ContextAware.CHARGING_STATE_KEY, element.getType());
        assertEquals(Boolean.TRUE, element.getData());
    }
}
