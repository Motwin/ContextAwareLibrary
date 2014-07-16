/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class SoundModeCollectorTest extends MockitoTestCase {
    private SpyChannel         channel;
    private SoundModeCollector collector;
    @Mock
    private Context            context;
    @Mock
    private AudioManager       audioManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new SoundModeCollector(channel, context, 100, audioManager);
    };

    public void testRegisterListenerOnStart() throws InterruptedException {
        collector.start();

        verify(context).registerReceiver(any(BroadcastReceiver.class), argThat(new ArgumentMatcher<IntentFilter>() {

            @Override
            public boolean matches(Object aArgument) {
                boolean res;
                if (aArgument instanceof IntentFilter) {
                    res = ((IntentFilter) aArgument).hasAction(AudioManager.RINGER_MODE_CHANGED_ACTION);
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

    public void testSendContextOnChangeSilent() throws InterruptedException {
        when(audioManager.getRingerMode()).thenReturn(AudioManager.RINGER_MODE_SILENT);
        collector.onRingerModeChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_MODE_KEY, ctxElement.getType());
        assertEquals(SoundModeCollector.RINGER_MODE_SILENT, ctxElement.getData());
    }

    public void testSendContextOnChangeVibrate() throws InterruptedException {
        when(audioManager.getRingerMode()).thenReturn(AudioManager.RINGER_MODE_VIBRATE);
        collector.onRingerModeChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_MODE_KEY, ctxElement.getType());
        assertEquals(SoundModeCollector.RINGER_MODE_VIBRATE, ctxElement.getData());
    }

    public void testSendContextOnChangeNormal() throws InterruptedException {
        when(audioManager.getRingerMode()).thenReturn(AudioManager.RINGER_MODE_NORMAL);
        collector.onRingerModeChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_MODE_KEY, ctxElement.getType());
        assertEquals(SoundModeCollector.RINGER_MODE_NORMAL, ctxElement.getData());
    }

    public void testSendContextOnChangeUnknwonn() throws InterruptedException {
        when(audioManager.getRingerMode()).thenReturn(-1);
        collector.onRingerModeChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_MODE_KEY, ctxElement.getType());
        assertEquals(SoundModeCollector.RINGER_MODE_UNKNOWN, ctxElement.getData());
    }
}
