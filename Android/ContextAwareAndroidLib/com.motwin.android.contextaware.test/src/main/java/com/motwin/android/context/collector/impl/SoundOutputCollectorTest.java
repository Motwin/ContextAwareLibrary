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
public class SoundOutputCollectorTest extends MockitoTestCase {
    private SpyChannel           channel;
    private SoundOutputCollector collector;
    @Mock
    private Context              context;
    @Mock
    private AudioManager         audioManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new SoundOutputCollector(channel, context, 100, audioManager);
    };

    public void testRegisterListenerOnStart() throws InterruptedException {
        collector.start();

        verify(context).registerReceiver(any(BroadcastReceiver.class), argThat(new ArgumentMatcher<IntentFilter>() {

            @Override
            public boolean matches(Object aArgument) {
                boolean res;
                if (aArgument instanceof IntentFilter) {
                    res = ((IntentFilter) aArgument).hasAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
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

    public void testSendContextOnChangeBluetooth() throws InterruptedException {
        when(audioManager.isBluetoothScoOn()).thenReturn(true);
        collector.onSoundOutputChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_OUTPUT_KEY, ctxElement.getType());
        assertEquals(SoundOutputCollector.SOUND_OUTPUT_BLUETOOTH, ctxElement.getData());
    }

    public void testSendContextOnChangeMute() throws InterruptedException {
        when(audioManager.isBluetoothScoOn()).thenReturn(false);
        when(audioManager.isMicrophoneMute()).thenReturn(true);
        collector.onSoundOutputChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_OUTPUT_KEY, ctxElement.getType());
        assertEquals(SoundOutputCollector.SOUND_OUTPUT_MICROPHONE_MUTE, ctxElement.getData());
    }

    public void testSendContextOnChangeMusic() throws InterruptedException {
        when(audioManager.isBluetoothScoOn()).thenReturn(false);
        when(audioManager.isMicrophoneMute()).thenReturn(false);
        when(audioManager.isMusicActive()).thenReturn(true);
        collector.onSoundOutputChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_OUTPUT_KEY, ctxElement.getType());
        assertEquals(SoundOutputCollector.SOUND_OUTPUT_MUSIC, ctxElement.getData());
    }

    public void testSendContextOnChangeSpeakers() throws InterruptedException {
        when(audioManager.isBluetoothScoOn()).thenReturn(false);
        when(audioManager.isMicrophoneMute()).thenReturn(false);
        when(audioManager.isMusicActive()).thenReturn(false);
        when(audioManager.isSpeakerphoneOn()).thenReturn(true);
        collector.onSoundOutputChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_OUTPUT_KEY, ctxElement.getType());
        assertEquals(SoundOutputCollector.SOUND_OUTPUT_SPEAKERS, ctxElement.getData());
    }

    public void testSendContextOnChangeUnknown() throws InterruptedException {
        when(audioManager.isBluetoothScoOn()).thenReturn(false);
        when(audioManager.isMicrophoneMute()).thenReturn(false);
        when(audioManager.isMusicActive()).thenReturn(false);
        when(audioManager.isSpeakerphoneOn()).thenReturn(false);
        collector.onSoundOutputChange();

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.SOUND_OUTPUT_KEY, ctxElement.getType());
        assertEquals(SoundOutputCollector.SOUND_OUTPUT_UNKNOWN, ctxElement.getData());
    }

}
