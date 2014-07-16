/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.dispatcher.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Preconditions;
import com.motwin.context.dispatcher.ContextDispatcher;
import com.motwin.context.utils.ContextUtils;

/**
 * The Class ContextHandlerWrapper.
 * 
 */
public class ContextHandlerWrapper implements BeanFactoryAware, InitializingBean, DisposableBean {

    /** The delegate. */
    private final Object      delegate;

    /** The bean factory. */
    private BeanFactory       beanFactory;

    /** The dispatcher. */
    private ContextDispatcher dispatcher;

    /**
     * Instantiates a new context handler wrapper.
     * 
     * @param aDelegate
     *            the delegate
     */
    public ContextHandlerWrapper(Object aDelegate) {
        super();
        delegate = aDelegate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(beanFactory, "beanFactory cannot be null");

        dispatcher = ContextUtils.getContextDispatcherOrDefault(dispatcher, beanFactory);
        Preconditions.checkNotNull(dispatcher, "dispatcher cannot be null");

        dispatcher.subscribe(delegate);
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

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        Preconditions.checkNotNull(dispatcher, "dispatcher cannot be null");

        dispatcher.unsubscribe(delegate);
    }
}
