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
public class MobileNetworkCodeCollectorTest extends MockitoTestCase {
    private SpyChannel                 channel;
    private MobileNetworkCodeCollector collector;
    @Mock
    private Context                    context;
    @Mock
    private TelephonyManager           telephonyManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new MobileNetworkCodeCollector(channel, context, telephonyManager);
    };

    public void testSendContextOnStart() throws InterruptedException {
        when(telephonyManager.getSimOperator()).thenReturn("abcdefgh");
        collector.start();

        verify(telephonyManager).getSimOperator();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.MOBILE_NETWORK_CODE_KEY, ctxElement.getType());
        assertEquals("defgh", ctxElement.getData());
    }
}
