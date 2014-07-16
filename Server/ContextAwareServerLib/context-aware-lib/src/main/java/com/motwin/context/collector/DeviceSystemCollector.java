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
 * The Class DeviceSystemCollector.
 * 
 * Collects the deviceSystem from the UserInfo map. deviceSystem is the
 * description of the operation system of the device which provided the UserInfo
 * map.
 * 
 */
public class DeviceSystemCollector extends AbstractUserInfoCollector {

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

        String deviceSystem = aUserInfo.get(EndPointContext.USER_INFOS_DEVICE_SYSTEM);

        ContextElement<String> deviceSystemElement;
        deviceSystemElement = new ContextElement<String>();
        deviceSystemElement.setType(ContextAwareConstants.DEVICE_SYSTEM_KEY);
        deviceSystemElement.setData(deviceSystem);

        return deviceSystemElement;

    }
}
