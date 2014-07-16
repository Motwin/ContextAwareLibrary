/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.List;

import org.mockito.Mock;

import android.content.Context;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.context.utils.TimeZoneHelper;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class TimeZoneCollectorTest extends MockitoTestCase {
    private SpyChannel        channel;
    private TimeZoneCollector collector;
    @Mock
    private Context           context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new TimeZoneCollector(channel, context);
    };

    public void testSendContextOnStart() throws InterruptedException {
        collector.start();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.TIME_ZONE_KEY, ctxElement.getType());
        assertEquals(TimeZoneHelper.retriveTimeZoneValue(), ctxElement.getData());
    }

}
