/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.collector;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.motwin.context.model.ContextAwareConstants;
import com.motwin.context.model.ContextElement;

/**
 * The Class DeviceIdCollector.
 * 
 * Collects the deviceId from the UserInfo map. deviceId is the unique
 * identifier of the device hosting the application which provided the UserInfo
 * map.
 * 
 */
public class DeviceIdCollector extends AbstractUserInfoCollector {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.context.collector.AbstractUserInfoCollector#collect(java.util
     * .Map)
     */
    @Override
    protected ContextElement<?> collect(Map<String, String> aUserInfo) {
        Preconditions.checkNotNull(aUserInfo, "aUserInfo cannot be null");

        String deviceId = aUserInfo.get("deviceId");

        ContextElement<String> deviceIdElement;
        deviceIdElement = new ContextElement<String>();
        deviceIdElement.setType(ContextAwareConstants.DEVICE_ID_KEY);
        deviceIdElement.setData(deviceId);

        return deviceIdElement;

    }
}
