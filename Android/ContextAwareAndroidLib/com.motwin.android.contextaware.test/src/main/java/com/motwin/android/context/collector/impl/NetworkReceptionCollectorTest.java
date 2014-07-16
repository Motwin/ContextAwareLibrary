/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.mockito.Mock;

import android.telephony.PhoneStateListener;
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
public class NetworkReceptionCollectorTest extends MockitoTestCase {
    private SpyChannel                channel;
    private NetworkReceptionCollector collector;
    @Mock
    private TelephonyManager          telephonyManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new NetworkReceptionCollector(channel, getContext(), 0, telephonyManager);
    };

    public void testRegisterListenerOnStart() throws InterruptedException {
        collector.start();

        verify(telephonyManager).listen(any(PhoneStateListener.class), eq(PhoneStateListener.LISTEN_SIGNAL_STRENGTHS));
    }

    public void testUnregisterListenerOnStop() throws InterruptedException {
        collector.stop();

        verify(telephonyManager).listen(any(PhoneStateListener.class), eq(PhoneStateListener.LISTEN_NONE));
    }

    public void testSendElementOnSignalChange() throws InterruptedException {
        collector.onSignalStrengthsChanged(123);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.NETWORK_RECEPTION_KEY, ctxElement.getType());
        assertEquals("123", ctxElement.getData());
    }
}
