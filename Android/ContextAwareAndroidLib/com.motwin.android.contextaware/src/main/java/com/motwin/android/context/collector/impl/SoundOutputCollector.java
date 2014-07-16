/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Sound output on change collector
 * 
 */
public class SoundOutputCollector extends AbstractOnChangeCollector<String> {
    public static final String      SOUND_OUTPUT_SPEAKERS        = "Speakers";
    public static final String      SOUND_OUTPUT_MUSIC           = "Music";
    public static final String      SOUND_OUTPUT_MICROPHONE_MUTE = "Microphone Mute";
    public static final String      SOUND_OUTPUT_BLUETOOTH       = "Bluetooth";
    public static final String      SOUND_OUTPUT_UNKNOWN         = "Unknown";

    private final AudioManager      audioManager;
    private final BroadcastReceiver receiver;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between location updates
     */
    public SoundOutputCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
        this(aClientChannel, aApplicationContext, aMinDelay, (AudioManager) aApplicationContext
                .getSystemService(Context.AUDIO_SERVICE));
    }

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between location updates
     * @param aAudioManager
     *            The audio manager
     */
    protected SoundOutputCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay,
            AudioManager aAudioManager) {
        super(aClientChannel, aApplicationContext, aMinDelay);
        audioManager = Preconditions.checkNotNull(aAudioManager, "aAudioManager cannot be null");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onSoundOutputChange();
            }
        };

    }

    @Override
    public void start() {
        getApplicationContext().registerReceiver(receiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
    }

    @Override
    public void stop() {
        getApplicationContext().unregisterReceiver(receiver);
    }

    /**
     * Handles sound output change
     */
    protected void onSoundOutputChange() {
        String soundOutput;
        if (audioManager.isBluetoothScoOn()) {
            soundOutput = SOUND_OUTPUT_BLUETOOTH;
        } else if (audioManager.isMicrophoneMute()) {
            soundOutput = SOUND_OUTPUT_MICROPHONE_MUTE;
        } else if (audioManager.isMusicActive()) {
            soundOutput = SOUND_OUTPUT_MUSIC;
        } else if (audioManager.isSpeakerphoneOn()) {
            soundOutput = SOUND_OUTPUT_SPEAKERS;
        } else {
            soundOutput = SOUND_OUTPUT_UNKNOWN;
        }

        ContextElement<String> contextEelement = new ContextElement<String>(ContextAware.SOUND_OUTPUT_KEY, soundOutput);
        onChange(contextEelement);

    }

}
