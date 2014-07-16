/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.utils;

import org.springframework.beans.factory.BeanFactory;

import com.google.common.base.Preconditions;
import com.motwin.context.dispatcher.ContextDispatcher;

/**
 * The Class ContextUtils.
 * 
 * Utility Class providing static method.
 * 
 */
public final class ContextUtils {

    /** The Constant DEFAULT_CONTEXT_DISPATCHER. */
    private final static String DEFAULT_CONTEXT_DISPATCHER = "MotwinContextDispatcher";

    /**
     * Hide constructor to instantiate a new context utils.
     */
    private ContextUtils() {
    }

    /**
     * This method is used to get the ContextDispatcher bean named
     * "MotwinContextDispatcher".
     * 
     * @param aDispatcher
     *            the instance of the ContextDispatcher in the
     *            ContextDispatcherWrapper
     * @param aFactory
     *            the spring bean factory
     * @return the ContextDispatcher bean
     */
    public static ContextDispatcher getContextDispatcherOrDefault(ContextDispatcher aDispatcher, BeanFactory aFactory) {
        Preconditions.checkNotNull(aFactory, "aFactory cannot be null");

        if (aDispatcher == null) {
            return getBeanOfType(aFactory, DEFAULT_CONTEXT_DISPATCHER, ContextDispatcher.class);

        } else {
            return aDispatcher;
        }
    }

    /**
     * Gets the bean of type.
     * 
     * @param <T>
     *            the generic type
     * @param aBeanFactory
     *            the bean factory
     * @param aBeanName
     *            the bean name
     * @param aType
     *            the type
     * @return the bean of type
     */
    private static <T> T getBeanOfType(BeanFactory aBeanFactory, String aBeanName, Class<T> aType) {
        Preconditions.checkNotNull(aBeanFactory, "aBeanFactory cannot be null");
        Preconditions.checkNotNull(aBeanName, "aBeanName cannot be null");
        Preconditions.checkNotNull(aType, "aType cannot be null");

        if (!aBeanFactory.containsBean(aBeanName)) {
            return null;
        }
        return aBeanFactory.getBean(aBeanName, aType);
    }
}
