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
 * The Class DeviceVersionCollector.
 * 
 * Collects the deviceVersion from the UserInfo map. deviceVersion is the
 * description of the device version which provided the UserInfo map.
 * 
 */
public class DeviceVersionCollector extends AbstractUserInfoCollector {

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

        String deviceVersion = aUserInfo.get(EndPointContext.USER_INFOS_DEVICE_VERSION);

        ContextElement<String> deviceVersionElement;
        deviceVersionElement = new ContextElement<String>();
        deviceVersionElement.setType(ContextAwareConstants.DEVICE_OS_VERSION_KEY);
        deviceVersionElement.setData(deviceVersion);

        return deviceVersionElement;

    }
}
