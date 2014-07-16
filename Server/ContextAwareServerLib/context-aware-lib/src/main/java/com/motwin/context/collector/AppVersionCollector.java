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
 * The Class AppVersionCollector.
 * 
 * Collects the appVersion from the UserInfo map. appVersion in the version of
 * the application providing the UserInfo map.
 * 
 */
public class AppVersionCollector extends AbstractUserInfoCollector {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.context.collector.AbstractUserInfoCollector#collect(java.util
     * .Map)
     */
    @Override
    protected ContextElement<?> collect(final Map<String, String> aUserInfo) {
        Preconditions.checkNotNull(aUserInfo, "aUserInfo cannot be null");

        String appVersion = aUserInfo.get(EndPointContext.USER_INFOS_APP_VERSION);

        ContextElement<String> appVersionElement;
        appVersionElement = new ContextElement<String>();
        appVersionElement.setType(ContextAwareConstants.APP_VERSION_KEY);
        appVersionElement.setData(appVersion);

        return appVersionElement;

    }
}
