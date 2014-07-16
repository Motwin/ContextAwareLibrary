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
import com.motwin.sdk.application.channel.Channel.EndPointContext;

/**
 * The Class DeviceTypeCollector.
 * 
 * Collects the deviceType from the UserInfo map. deviceType is the description
 * of the device type (iPhone, iPad, Android, Others ...) which provided the
 * UserInfo map.
 * 
 */
public class DeviceTypeCollector extends AbstractUserInfoCollector {

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

        String deviceType = aUserInfo.get(EndPointContext.USER_INFOS_DEVICE_TYPE);

        ContextElement<String> deviceTypeElement;
        deviceTypeElement = new ContextElement<String>();
        deviceTypeElement.setType(ContextAwareConstants.DEVICE_TYPE_KEY);
        deviceTypeElement.setData(deviceType);

        return deviceTypeElement;

    }
}
