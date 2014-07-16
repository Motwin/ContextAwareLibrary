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
 * Roaming country code one shot collector
 * 
 */
public class RoamingCountryCodeCollector extends AbstractOneShotCollector<String> {
    public static final String     INTERNATIONAL_ROAMING = "International Roaming";
    public static final String     NATIONAL_ROAMING      = "National Roaming";
    public static final String     NOT_ROMAMING          = "N.A.";

    private final TelephonyManager telephonyManager;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public RoamingCountryCodeCollector(ClientChannel aClientChannel, Context aApplicationContext) {
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
    protected RoamingCountryCodeCollector(ClientChannel aClientChannel, Context aApplicationContext,
            TelephonyManager aTelephonyManager) {
        super(aClientChannel, aApplicationContext);
        telephonyManager = Preconditions
                .checkNotNull(
                        aTelephonyManager,
                        "aTelephonyManager cannot be null. Check that permission \"android.permission.READ_PHONE_STATE\" is declared in your AndroidManifest.xml");
    }

    @Override
    public ContextElement<String> collect() {
        boolean isRoaming = telephonyManager.isNetworkRoaming();

        String roamingValue;
        if (!isRoaming) {
            roamingValue = NOT_ROMAMING;
        } else if (telephonyManager.getSimCountryIso().equals(telephonyManager.getNetworkCountryIso())) {
            roamingValue = NATIONAL_ROAMING;
        } else {
            roamingValue = INTERNATIONAL_ROAMING;

        }
        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.ROAMING_COUNTRY_CODE_KEY,
                roamingValue);
        return contextElement;
    }

}
