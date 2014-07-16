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
import android.content.res.Configuration;
import android.hardware.Sensor;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class ScreenOrientationCollectorTest extends MockitoTestCase {

    private SpyChannel                 channel;
    private ScreenOrientationCollector collector;
    @Mock
    private Context                    context;
    @Mock
    private Sensor                     sensor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new ScreenOrientationCollector(channel, context, 100);
    }

    public void testRegisterListenerOnStart() {
        collector.start();

        verify(context).registerReceiver(any(BroadcastReceiver.class), argThat(new ArgumentMatcher<IntentFilter>() {

            @Override
            public boolean matches(Object aArgument) {
                boolean res;
                if (aArgument instanceof IntentFilter) {
                    IntentFilter filter = (IntentFilter) aArgument;
                    res = filter.hasAction(Intent.ACTION_CONFIGURATION_CHANGED);
                } else {
                    res = false;
                }
                return res;
            }
        }));
    }

    public void testUnregisterListenerOnStop() {
        collector.stop();

        verify(context).unregisterReceiver(any(BroadcastReceiver.class));
    }

    public void testForwardContextOnConfigurationChangedLandscape() throws InterruptedException {
        collector.onConfigurationChanged(Configuration.ORIENTATION_LANDSCAPE);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        ContextElement<?> ctx = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SCREEN_ORIENTATION_KEY, ctx.getType());
        assertTrue(ctx.getData() instanceof String);
        assertEquals(ScreenOrientationCollector.ORIENTATION_LANDSCAPE, ctx.getData().toString());
    }

    public void testForwardContextOnConfigurationChangedPortrait() throws InterruptedException {
        collector.onConfigurationChanged(Configuration.ORIENTATION_PORTRAIT);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        ContextElement<?> ctx = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SCREEN_ORIENTATION_KEY, ctx.getType());
        assertTrue(ctx.getData() instanceof String);
        assertEquals(ScreenOrientationCollector.ORIENTATION_PORTRAIT, ctx.getData().toString());
    }

    public void testForwardContextOnConfigurationChangedUnknown() throws InterruptedException {
        collector.onConfigurationChanged(Configuration.ORIENTATION_UNDEFINED);

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        ContextElement<?> ctx = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SCREEN_ORIENTATION_KEY, ctx.getType());
        assertTrue(ctx.getData() instanceof String);
        assertEquals(ScreenOrientationCollector.ORIENTATION_UNKNOWN, ctx.getData().toString());
    }
}
