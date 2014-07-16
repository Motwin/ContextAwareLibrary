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
 * @author lorie
 * 
 */
public class AbstractOneShotCollectorTest extends AndroidTestCase {

    /**
     * 
     */
    private static final String              ONE_SHOT_CONTEXT_ELEMENT = "OneShotContextElement";
    private AbstractOneShotCollector<String> oneShotCollector;
    private SpyChannel                       clientChannel;
    ContextElement<String>                   mockElement;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        clientChannel = new SpyChannel();

        oneShotCollector = new AbstractOneShotCollector<String>(clientChannel, getContext()) {
            @Override
            public ContextElement<String> collect() {
                return mockElement;
            }
        };
        mockElement = new ContextElement<String>();
        mockElement.setType(ONE_SHOT_CONTEXT_ELEMENT);
        mockElement.setData(getName());

    }

    public void testSendContextElementOnStart() throws InterruptedException {
        oneShotCollector.start();

        List<Message<?>> messages = clientChannel.waitMessageSent(1, 1, TimeUnit.SECONDS);
        assertEquals(1, messages.size());
        Message<?> message = messages.get(0);
        assertEquals(ContextAware.MESSAGE_TYPE, message.getType());
        assertEquals(mockElement, message.getContent());
    }
}
