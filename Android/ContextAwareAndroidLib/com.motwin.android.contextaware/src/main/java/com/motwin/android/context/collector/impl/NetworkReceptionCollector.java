/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Network reception on change collector
 */
public class NetworkReceptionCollector extends AbstractOnChangeCollector<String> {

    private final TelephonyManager   telephonyManager;
    private final PhoneStateListener signalReceptionListener;

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
    public NetworkReceptionCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
        this(aClientChannel, aApplicationContext, aMinDelay, (TelephonyManager) aApplicationContext
                .getSystemService(Context.TELEPHONY_SERVICE));
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
     * @param aTelephonyManager
     *            The telephony manager
     */
    protected NetworkReceptionCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay,
            TelephonyManager aTelephonyManager) {
        super(aClientChannel, aApplicationContext, aMinDelay);

        telephonyManager = Preconditions
                .checkNotNull(
                        aTelephonyManager,
                        "aTelephonyManager cannot be null. Check that permission \"android.permission.READ_PHONE_STATE\" is declared in your manifest");
        signalReceptionListener = new SignalReceptionListener();

    }

    @Override
    public void start() {
        telephonyManager.listen(signalReceptionListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    @Override
    public void stop() {
        telephonyManager.listen(signalReceptionListener, PhoneStateListener.LISTEN_NONE);
    }

    /**
     * Handles signal strength changed event
     * 
     * @param aSignalStrength
     *            The new signal strength
     */
    protected void onSignalStrengthsChanged(int aSignalStrength) {
        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.NETWORK_RECEPTION_KEY,
                String.valueOf(aSignalStrength));
        onChange(contextElement);

    }

    /**
     * The Phone State Listener class
     * 
     */
    private class SignalReceptionListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength aSignalStrength) {
            super.onSignalStrengthsChanged(aSignalStrength);

            onSignalStrengthChanged(aSignalStrength.getGsmSignalStrength());

        }

    }

}
