/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.motwin.context.namespace.parser.ContextBeanDefinitionParser;

/**
 * The Class ContextNamespaceHandler.
 * 
 * This class enables the ContextAware namespace by instantiating the
 * ContextAware bean definition parser.
 * 
 */
public class ContextNamespaceHandler extends NamespaceHandlerSupport {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
     */
    @Override
    public void init() {
        registerBeanDefinitionParser("handler", new ContextBeanDefinitionParser());
    }
}
