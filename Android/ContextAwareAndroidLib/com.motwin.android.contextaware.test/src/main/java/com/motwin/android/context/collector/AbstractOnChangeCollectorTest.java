/**
 * 
 */
package com.motwin.android.context.collector;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.test.AndroidTestCase;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author rihab
 * 
 */
public class AbstractOnChangeCollectorTest extends AndroidTestCase {
    private static String                     NETWORK_TYPE_KEY = "dataNetworkType";

    private AbstractOnChangeCollector<String> onChangeCollector;
    private SpyChannel                        clientChannel;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        clientChannel = new SpyChannel();
        onChangeCollector = new AbstractOnChangeCollector<String>(clientChannel, getContext(), 1000) {

            @Override
            public void stop() {
                // NOOP

            }

            @Override
            public void start() {
                // NOOP

            }
        };

    }

    public void testOnChange() throws InterruptedException {
        ContextElement<String> contextElement = new ContextElement<String>(NETWORK_TYPE_KEY, "WIFI");
        onChangeCollector.onChange(contextElement);

        List<Message<?>> messages = clientChannel.waitMessageSent(1, 1, TimeUnit.SECONDS);
        assertEquals(1, messages.size());
        Message<?> message = messages.get(0);
        assertEquals(ContextAware.MESSAGE_TYPE, message.getType());
        assertEquals(contextElement, message.getContent());
    }

    public void testOnChangeDoNotForwardIfBeforeMinDelay() throws InterruptedException {
        ContextElement<String> contextElement = new ContextElement<String>(NETWORK_TYPE_KEY, "WIFI");
        onChangeCollector.onChange(contextElement);

        List<Message<?>> messages = clientChannel.waitMessageSent(1, 500, TimeUnit.MILLISECONDS);
        assertEquals(1, messages.size());
        Message<?> message = messages.get(0);
        assertEquals(ContextAware.MESSAGE_TYPE, message.getType());
        assertEquals(contextElement, message.getContent());

        ContextElement<String> newContextElement = new ContextElement<String>(NETWORK_TYPE_KEY, "WIFI2");
        onChangeCollector.onChange(newContextElement);
        assertEquals(0, clientChannel.getPendingMessagesSize());
    }

    public void testOnChangeDoNotForwardIfSameData() throws InterruptedException {
        ContextElement<String> contextElement = new ContextElement<String>(NETWORK_TYPE_KEY, "WIFI");
        onChangeCollector.onChange(contextElement);

        List<Message<?>> messages = clientChannel.waitMessageSent(1, 500, TimeUnit.MILLISECONDS);
        assertEquals(1, messages.size());
        Message<?> message = messages.get(0);
        assertEquals(ContextAware.MESSAGE_TYPE, message.getType());
        assertEquals(contextElement, message.getContent());

        Thread.sleep(1000);

        ContextElement<String> newContextElement = new ContextElement<String>(NETWORK_TYPE_KEY, "WIFI");
        onChangeCollector.onChange(newContextElement);
        assertEquals(0, clientChannel.getPendingMessagesSize());
    }

    public void testOnChangeForwardIfDifferentData() throws InterruptedException {
        ContextElement<String> contextElement = new ContextElement<String>(NETWORK_TYPE_KEY, "WIFI");
        onChangeCollector.onChange(contextElement);

        List<Message<?>> messages = clientChannel.waitMessageSent(1, 500, TimeUnit.MILLISECONDS);
        assertEquals(1, messages.size());
        Message<?> message = messages.get(0);
        assertEquals(ContextAware.MESSAGE_TYPE, message.getType());
        assertEquals(contextElement, message.getContent());

        Thread.sleep(1000);

        ContextElement<String> newContextElement = new ContextElement<String>(NETWORK_TYPE_KEY, "WIFI2");
        onChangeCollector.onChange(newContextElement);
        messages = clientChannel.waitMessageSent(1, 500, TimeUnit.MILLISECONDS);
        assertEquals(1, messages.size());
        message = messages.get(0);
        assertEquals(ContextAware.MESSAGE_TYPE, message.getType());
        assertEquals(newContextElement, message.getContent());
    }
}
