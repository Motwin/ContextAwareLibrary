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
public class ChargerTypeCollectorTest extends MockitoTestCase {
    private SpyChannel           channel;
    private ChargerTypeCollector collector;
    @Mock
    private Context              context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new ChargerTypeCollector(channel, context, 1000);
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

    public void testHandleBatteryOnUsb() throws InterruptedException {
        Intent intent = mock(Intent.class);

        when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)).thenReturn(BatteryManager.BATTERY_PLUGGED_USB);

        collector.handleBatterieChangeEvent(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> element = (ContextElement<?>) messages.get(0).getContent();

        assertEquals(ContextAware.CHARGER_TYPE_KEY, element.getType());
        assertTrue(element.getData() instanceof String);
        assertEquals(ChargerTypeCollector.CHARGER_TYPE_USB, element.getData().toString());
    }

    public void testHandleBatteryOnAc() throws InterruptedException {
        Intent intent = mock(Intent.class);

        when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)).thenReturn(BatteryManager.BATTERY_PLUGGED_AC);

        collector.handleBatterieChangeEvent(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> element = (ContextElement<?>) messages.get(0).getContent();

        assertEquals(ContextAware.CHARGER_TYPE_KEY, element.getType());
        assertTrue(element.getData() instanceof String);
        assertEquals(ChargerTypeCollector.CHARGER_TYPE_AC, element.getData().toString());
    }

    public void testHandleBatteryOnWireless() throws InterruptedException {
        Intent intent = mock(Intent.class);

        when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)).thenReturn(
                ChargerTypeCollector.BATTERY_PLUGGED_WIRELESS);

        collector.handleBatterieChangeEvent(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> element = (ContextElement<?>) messages.get(0).getContent();

        assertEquals(ContextAware.CHARGER_TYPE_KEY, element.getType());
        assertTrue(element.getData() instanceof String);
        assertEquals(ChargerTypeCollector.CHARGER_TYPE_WIRELESS, element.getData().toString());
    }

    public void testHandleBatteryNone() throws InterruptedException {
        Intent intent = mock(Intent.class);

        when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)).thenReturn(0);

        collector.handleBatterieChangeEvent(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> element = (ContextElement<?>) messages.get(0).getContent();

        assertEquals(ContextAware.CHARGER_TYPE_KEY, element.getType());
        assertTrue(element.getData() instanceof String);
        assertEquals(ChargerTypeCollector.CHARGER_TYPE_NONE, element.getData().toString());
    }
}
