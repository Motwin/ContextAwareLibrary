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
 * Sound mode on change collector
 * 
 */
public class SoundModeCollector extends AbstractOnChangeCollector<String> {
    public static final String      RINGER_MODE_NORMAL  = "Normal";
    public static final String      RINGER_MODE_VIBRATE = "Vibrate";
    public static final String      RINGER_MODE_SILENT  = "Silent";
    public static final String      RINGER_MODE_UNKNOWN = "Unknown";
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
    public SoundModeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
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
    protected SoundModeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay,
            AudioManager aAudioManager) {
        super(aClientChannel, aApplicationContext, aMinDelay);
        audioManager = Preconditions.checkNotNull(aAudioManager, "aAudioManager cannot be null");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onRingerModeChange();
            }
        };
    }

    @Override
    public void start() {
        getApplicationContext().registerReceiver(receiver, new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION));
    }

    @Override
    public void stop() {
        getApplicationContext().unregisterReceiver(receiver);
    }

    /**
     * Handles ringer mode changes
     */
    protected void onRingerModeChange() {
        String ringerMode;
        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                ringerMode = RINGER_MODE_SILENT;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                ringerMode = RINGER_MODE_VIBRATE;
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                ringerMode = RINGER_MODE_NORMAL;
                break;
            default:
                ringerMode = RINGER_MODE_UNKNOWN;
                break;
        }
        ContextElement<String> contextEelement = new ContextElement<String>(ContextAware.SOUND_MODE_KEY, ringerMode);
        onChange(contextEelement);

    }

}
