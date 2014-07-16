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
 * The Class UserAgentCollector.
 * 
 * Collects the userAgent from the UserInfo map. userAgent is a string
 * describing the user agent of the device which provided the UserInfo map.
 * 
 */
public class UserAgentCollector extends AbstractUserInfoCollector {

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

        String userAgent = aUserInfo.get("userAgent");

        ContextElement<String> userAgentElement;
        userAgentElement = new ContextElement<String>();
        userAgentElement.setType(ContextAwareConstants.USER_AGENT_KEY);
        userAgentElement.setData(userAgent);

        return userAgentElement;

    }
}
