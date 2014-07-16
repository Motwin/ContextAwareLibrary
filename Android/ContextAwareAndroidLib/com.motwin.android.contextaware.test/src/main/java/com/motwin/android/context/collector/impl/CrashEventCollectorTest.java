/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import org.mockito.Mock;

import android.content.Context;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class CrashEventCollectorTest extends MockitoTestCase {
    private SpyChannel               channel;
    private CrashEventCollector      collector;
    @Mock
    private Context                  context;
    @Mock
    private UncaughtExceptionHandler exceptionHandler;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new CrashEventCollector(channel, context, exceptionHandler);
    };

    public void testStartStopChangesExceptionHandler() {
        UncaughtExceptionHandler defaultHandler;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        collector.start();

        assertEquals(collector, Thread.getDefaultUncaughtExceptionHandler());

        collector.stop();

        assertEquals(defaultHandler, Thread.getDefaultUncaughtExceptionHandler());

    }

    public void testExceptionCaught() throws InterruptedException {
        Thread thread = mock(Thread.class);
        Exception exception = mock(Exception.class);

        collector.uncaughtException(thread, exception);

        verify(exceptionHandler).uncaughtException(thread, exception);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.CRASH_EVENT_KEY, ctxElement.getType());
        assertTrue(ctxElement.getData() instanceof String);
    }
}
