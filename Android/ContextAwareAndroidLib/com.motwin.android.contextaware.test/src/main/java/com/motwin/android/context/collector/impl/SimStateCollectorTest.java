/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.Mock;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class SimStateCollectorTest extends MockitoTestCase {
    private SpyChannel        channel;
    private SimStateCollector collector;
    @Mock
    private Context           context;
    @Mock
    private TelephonyManager  telephonyManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new SimStateCollector(channel, context, telephonyManager);
    };

    public void testSendContextOnStartSimAbsent() throws InterruptedException {
        when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_ABSENT);
        collector.start();

        verify(telephonyManager).getSimState();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SIM_STATE_KEY, ctxElement.getType());
        assertEquals(SimStateCollector.SIM_STATE_ABSENT, ctxElement.getData());
    }

    public void testSendContextOnStartNetworkLocked() throws InterruptedException {
        when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_NETWORK_LOCKED);
        collector.start();

        verify(telephonyManager).getSimState();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SIM_STATE_KEY, ctxElement.getType());
        assertEquals(SimStateCollector.SIM_STATE_NETWORK_LOCKED, ctxElement.getData());
    }

    public void testSendContextOnStartPinRequired() throws InterruptedException {
        when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_PIN_REQUIRED);
        collector.start();

        verify(telephonyManager).getSimState();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SIM_STATE_KEY, ctxElement.getType());
        assertEquals(SimStateCollector.SIM_STATE_PIN_REQUIRED, ctxElement.getData());
    }

    public void testSendContextOnStartPukRequired() throws InterruptedException {
        when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_PUK_REQUIRED);
        collector.start();

        verify(telephonyManager).getSimState();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SIM_STATE_KEY, ctxElement.getType());
        assertEquals(SimStateCollector.SIM_STATE_PUK_REQUIRED, ctxElement.getData());
    }

    public void testSendContextOnStartReady() throws InterruptedException {
        when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_READY);
        collector.start();

        verify(telephonyManager).getSimState();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SIM_STATE_KEY, ctxElement.getType());
        assertEquals(SimStateCollector.SIM_STATE_READY, ctxElement.getData());
    }

    public void testSendContextOnStartUnknown() throws InterruptedException {
        when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_UNKNOWN);
        collector.start();

        verify(telephonyManager).getSimState();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SIM_STATE_KEY, ctxElement.getType());
        assertEquals(SimStateCollector.SIM_STATE_UNKNOWN, ctxElement.getData());
    }
}
