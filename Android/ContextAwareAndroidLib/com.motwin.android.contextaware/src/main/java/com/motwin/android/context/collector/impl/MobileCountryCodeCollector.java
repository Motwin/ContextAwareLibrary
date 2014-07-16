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
 * Mobile country code one shot collector
 * 
 */
public class MobileCountryCodeCollector extends AbstractOneShotCollector<String> {

    private final TelephonyManager telephonyManager;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     */
    public MobileCountryCodeCollector(ClientChannel aClientChannel, Context aApplicationContext) {
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
    protected MobileCountryCodeCollector(ClientChannel aClientChannel, Context aApplicationContext,
            TelephonyManager aTelephonyManager) {
        super(aClientChannel, aApplicationContext);
        telephonyManager = Preconditions
                .checkNotNull(
                        aTelephonyManager,
                        "aTelephonyManager cannot be null. Check that permission \"android.permission.READ_PHONE_STATE\" is declared in your AndroidManifest.xml");
    }

    @Override
    public ContextElement<String> collect() {
        /**
         * networkCode cann be null if the SIM state is not SIM_STATE_READY
         */
        String networkOperator = telephonyManager.getSimOperator();
        String mobileCountryCode;
        if (networkOperator != null && !(networkOperator.length() == 0)) {
            mobileCountryCode = retrieveMobileCode(networkOperator);
        } else {
            mobileCountryCode = "Unknown";
        }

        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.MOBILE_COUNTRY_CODE_KEY,
                mobileCountryCode);
        return contextElement;
    }

    /**
     * Retrive the mobile country code from the NetworkOperator which Returns
     * the numeric name (MCC+MNC) of current registered operator.
     * 
     * @param aNetworkOperator
     * @return mobile code
     */
    private String retrieveMobileCode(String aNetworkOperator) {
        String mobileCode = aNetworkOperator.substring(0, 3);
        return mobileCode;
    }

}
