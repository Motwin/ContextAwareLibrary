/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOneShotCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Sim state one shot collector
 * 
 */
public class SimStateCollector extends AbstractOneShotCollector<String> {

    public static final String     SIM_STATE_ABSENT         = "Absent";
    public static final String     SIM_STATE_NETWORK_LOCKED = "Network Locked";
    public static final String     SIM_STATE_PIN_REQUIRED   = "Pin Required";
    public static final String     SIM_STATE_PUK_REQUIRED   = "Puk Required";
    public static final String     SIM_STATE_READY          = "Ready";
    public static final String     SIM_STATE_UNKNOWN        = "Unknown";

    private final TelephonyManager telephonyManager;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public SimStateCollector(ClientChannel aClientChannel, Context aApplicationContext) {
        this(aClientChannel, aApplicationContext, (TelephonyManager) aApplicationContext
                .getSystemService(Context.TELEPHONY_SERVICE));
    }

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aTelephonyManager
     *            The telephony manager
     */
    protected SimStateCollector(ClientChannel aClientChannel, Context aApplicationContext,
            TelephonyManager aTelephonyManager) {
        super(aClientChannel, aApplicationContext);
        telephonyManager = Preconditions
                .checkNotNull(
                        aTelephonyManager,
                        "aTelephonyManager cannot be null. Check that permission \"android.permission.READ_PHONE_STATE\" is declared in your AndroidManifest.xml");
    }

    @Override
    public ContextElement<String> collect() {
        int simState = telephonyManager.getSimState();
        String simStatusValue = retrieveSimStatusValue(simState);

        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.SIM_STATE_KEY, simStatusValue);
        return contextElement;
    }

    /**
     * Retrive the sim status value
     * 
     * @param aSimStatus
     *            The sim status code
     * 
     * @return a String representing the sim status value
     */
    private String retrieveSimStatusValue(int aSimStatus) {
        String simStatusValue = null;
        switch (aSimStatus) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simStatusValue = SIM_STATE_ABSENT;
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simStatusValue = SIM_STATE_NETWORK_LOCKED;
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simStatusValue = SIM_STATE_PIN_REQUIRED;
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simStatusValue = SIM_STATE_PUK_REQUIRED;
                break;
            case TelephonyManager.SIM_STATE_READY:
                simStatusValue = SIM_STATE_READY;
                break;
            default:
                simStatusValue = SIM_STATE_UNKNOWN;
                break;

        }
        return simStatusValue;
    }

}
