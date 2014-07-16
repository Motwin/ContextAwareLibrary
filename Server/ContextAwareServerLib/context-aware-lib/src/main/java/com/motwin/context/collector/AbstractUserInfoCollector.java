/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.collector;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Preconditions;
import com.motwin.context.dispatcher.ContextDispatcher;
import com.motwin.context.model.ContextAwareConstants;
import com.motwin.context.model.ContextElement;
import com.motwin.context.model.MobileContextElement;
import com.motwin.context.utils.ContextUtils;
import com.motwin.sdk.application.channel.Channel;
import com.motwin.sdk.application.channel.ChannelInterceptor;
import com.motwin.sdk.application.channel.event.ChannelEvent;
import com.motwin.sdk.application.channel.event.StateEvent;

/**
 * The Class AbstractUserInfoCollector.
 * 
 * This is the abstrac class for all other User info collectors.
 * 
 */
public abstract class AbstractUserInfoCollector implements ChannelInterceptor, BeanFactoryAware, InitializingBean {

    /** The bean factory. */
    private BeanFactory       beanFactory;

    /** The context dispatcher to forward events to. */
    private ContextDispatcher contextDispatcher;

    /*
     * (non-Javadoc)
     * 
     * @see com.motwin.sdk.application.channel.ChannelInterceptor#
     * interceptDownStreamChannelEvent
     * (com.motwin.sdk.application.channel.event.ChannelEvent)
     */
    @Override
    public boolean interceptDownStreamChannelEvent(ChannelEvent aEvent) throws Exception {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.motwin.sdk.application.channel.ChannelInterceptor#
     * interceptUpStreamChannelEvent
     * (com.motwin.sdk.application.channel.event.ChannelEvent)
     */
    @Override
    public boolean interceptUpStreamChannelEvent(ChannelEvent aEvent) throws Exception {
        Preconditions.checkNotNull(aEvent, "aEvent cannot be null");

        if (aEvent instanceof StateEvent && ((StateEvent) aEvent).isActive()) {
            injectInstalledAppId(aEvent.getSource());

        }
        return true;
    }

    /**
     * Inject installed app id.
     * 
     * Installation Id is automatically injected in the ContextElement if
     * present in the Channel
     * 
     * @param aChannel
     *            the channel
     */
    private void injectInstalledAppId(final Channel aChannel) {
        Preconditions.checkNotNull(aChannel, "aChannel cannot be null");

        Map<String, String> userInfo;
        userInfo = aChannel.getEndPointContext().getUserInfos();

        ContextElement<?> contextElement = collect(userInfo);
        MobileContextElement<?> mobileContextElement = MobileContextElement.copyOf(contextElement);

        if (aChannel.containsAttribute(ContextAwareConstants.KEY_INSTALLATION_ID)) {
            String installationId = aChannel.getAttribute(ContextAwareConstants.KEY_INSTALLATION_ID, String.class);
            mobileContextElement.setInstallationId(installationId);
        }

        contextDispatcher.reportEvent(mobileContextElement);
    }

    /**
     * Collect. Abstract method to be implemented in order to collect a specific
     * ContextElement from the UserInfo map
     * 
     * @param aUserInfo
     *            the UserInfo map
     * @return ContextElement<?> the context element collected
     */
    protected abstract ContextElement<?> collect(Map<String, String> aUserInfo);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        contextDispatcher = ContextUtils.getContextDispatcherOrDefault(contextDispatcher, beanFactory);

        Preconditions.checkNotNull(contextDispatcher, "contextDispatcher cannot be null");

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org
     * .springframework.beans.factory.BeanFactory)
     */
    @Override
    public void setBeanFactory(BeanFactory aBeanFactory) throws BeansException {
        beanFactory = Preconditions.checkNotNull(aBeanFactory, "aBeanFactory cannot be null");

    }
}
