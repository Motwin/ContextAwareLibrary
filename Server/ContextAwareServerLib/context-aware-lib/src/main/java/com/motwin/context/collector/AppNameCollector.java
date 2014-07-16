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
 * The Class AppNameCollector.
 * 
 * Collects the appName from the UserInfo map. appName is the name of the
 * application which provided the UserInfo map.
 * 
 * @author lorie
 */
public class AppNameCollector extends AbstractUserInfoCollector {

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

        String appName = aUserInfo.get(EndPointContext.USER_INFOS_APP_NAME);

        ContextElement<String> appNameElement;
        appNameElement = new ContextElement<String>();
        appNameElement.setType(ContextAwareConstants.APP_NAME_KEY);
        appNameElement.setData(appName);

        return appNameElement;

    }
}
