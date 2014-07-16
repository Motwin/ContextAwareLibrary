/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class DockStateCollectorTest extends MockitoTestCase {
    private SpyChannel         channel;
    private DockStateCollector collector;
    @Mock
    private Context            context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new DockStateCollector(channel, context, 0);
    };

    public void testStartRegistersReceiver() {
        collector.start();

        verify(context).registerReceiver(any(BroadcastReceiver.class), argThat(new ArgumentMatcher<IntentFilter>() {

            @Override
            public boolean matches(Object aArgument) {
                boolean result;
                if (aArgument instanceof IntentFilter) {
                    IntentFilter intentFilter = (IntentFilter) aArgument;
                    result = intentFilter.hasAction(Intent.ACTION_DOCK_EVENT);
                } else {
                    result = false;
                }
                return result;
            }
        }));
    }

    public void testStopUnregistersReceiver() {
        collector.stop();

        verify(context).unregisterReceiver(any(BroadcastReceiver.class));

    }

    public void testHandleDockChangeUndocked() throws InterruptedException {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_DOCK_STATE, Intent.EXTRA_DOCK_STATE_UNDOCKED);

        collector.handleDockChange(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.DOCK_SATE_KEY, ctxElement.getType());
        assertEquals(Boolean.FALSE, ctxElement.getData());
    }

    public void testHandleDockChangeDockedCar() throws InterruptedException {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_DOCK_STATE, Intent.EXTRA_DOCK_STATE_CAR);

        collector.handleDockChange(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.DOCK_SATE_KEY, ctxElement.getType());
        assertEquals(Boolean.TRUE, ctxElement.getData());
    }

    public void testHandleDockChangeDockedDesk() throws InterruptedException {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_DOCK_STATE, Intent.EXTRA_DOCK_STATE_DESK);

        collector.handleDockChange(intent);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.DOCK_SATE_KEY, ctxElement.getType());
        assertEquals(Boolean.TRUE, ctxElement.getData());
    }

}
