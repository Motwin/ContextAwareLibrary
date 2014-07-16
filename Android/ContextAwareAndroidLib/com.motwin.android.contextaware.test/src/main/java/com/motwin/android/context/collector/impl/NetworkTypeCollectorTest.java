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
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class NetworkTypeCollectorTest extends MockitoTestCase {
    private SpyChannel           channel;
    private NetworkTypeCollector collector;
    @Mock
    private Context              context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new NetworkTypeCollector(channel, context, 100);
    };

    public void testRegisterListenerOnStart() throws InterruptedException {
        collector.start();

        verify(context).registerReceiver(any(BroadcastReceiver.class), argThat(new ArgumentMatcher<IntentFilter>() {

            @Override
            public boolean matches(Object aArgument) {
                boolean res;
                if (aArgument instanceof IntentFilter) {
                    res = ((IntentFilter) aArgument).hasAction(ConnectivityManager.CONNECTIVITY_ACTION);
                } else {
                    res = false;
                }
                return res;
            }
        }));
    }

    public void testUnregisterListenerOnStop() throws InterruptedException {
        collector.stop();

        verify(context).unregisterReceiver(any(BroadcastReceiver.class));
    }

    public void testSendContextOnChangeWifi() throws InterruptedException {
        NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(true);
        when(networkInfo.getTypeName()).thenReturn("WIFI");
        collector.onNetworkTypeChange(networkInfo);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.NETWORK_TYPE_KEY, ctxElement.getType());
        assertEquals("WIFI", ctxElement.getData());
    }

    public void testSendContextOnChangeMobile() throws InterruptedException {
        NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(true);
        when(networkInfo.getTypeName()).thenReturn("MOBILE");
        collector.onNetworkTypeChange(networkInfo);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.NETWORK_TYPE_KEY, ctxElement.getType());
        assertEquals("MOBILE", ctxElement.getData());
    }

    public void testSendContextOnChangeOff() throws InterruptedException {
        NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(false);
        collector.onNetworkTypeChange(networkInfo);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.NETWORK_TYPE_KEY, ctxElement.getType());
        assertEquals(NetworkTypeCollector.NETWORK_TYPE_OFF, ctxElement.getData());
    }
}
