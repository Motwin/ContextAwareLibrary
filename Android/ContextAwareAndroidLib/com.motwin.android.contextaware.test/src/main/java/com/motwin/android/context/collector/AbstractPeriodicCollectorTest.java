/**
 * 
 */
package com.motwin.android.context.collector;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.test.AndroidTestCase;

import com.google.common.collect.Lists;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class AbstractPeriodicCollectorTest extends AndroidTestCase {

    /**
     * 
     */
    private static final String               ONE_SHOT_CONTEXT_ELEMENT = "PeriodicContextElement";
    private AbstractPeriodicCollector<String> periodicCollector;
    private SpyChannel                        clientChannel;
    private List<ContextElement<String>>      mockElements;
    private boolean                           isOnStartCalled;
    private boolean                           isOnStopCalled;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        clientChannel = new SpyChannel();
        mockElements = Lists.newArrayList();
        isOnStartCalled = false;
        isOnStopCalled = false;
        periodicCollector = new AbstractPeriodicCollector<String>(clientChannel, getContext(), 100) {
            int i = 0;

            @Override
            public ContextElement<String> collect() {
                ContextElement<String> newElement = new ContextElement<String>();
                newElement.setType(ONE_SHOT_CONTEXT_ELEMENT);
                newElement.setData(getName() + i++);
                mockElements.add(newElement);
                return newElement;
            }

            @Override
            public void onStart() {
                isOnStartCalled = true;

            }

            @Override
            public void onStop() {
                isOnStopCalled = true;

            }

        };

    }

    public void testSendContextElementOnStart() throws InterruptedException {
        periodicCollector.start();

        assertTrue(isOnStartCalled);
        List<Message<?>> messages = clientChannel.waitMessageSent(10, 1, TimeUnit.SECONDS);
        assertEquals(10, messages.size());
        for (int i = 0; i < 10; i++) {
            Message<?> message = messages.get(i);
            assertEquals(ContextAware.MESSAGE_TYPE, message.getType());
            assertEquals(mockElements.get(i), message.getContent());
        }
    }

    public void testCustomOnStopCalledOnStop() throws InterruptedException {
        periodicCollector.start();
        periodicCollector.stop();

        assertTrue(isOnStopCalled);
    }

    public void testCannotStopNotStarted() throws InterruptedException {
        try {
            periodicCollector.stop();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
            // expected exception
        }

    }
}
