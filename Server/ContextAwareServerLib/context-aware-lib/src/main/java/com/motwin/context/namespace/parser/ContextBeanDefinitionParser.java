/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.namespace.parser;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.motwin.context.dispatcher.impl.ContextHandlerWrapper;
import com.motwin.sdk.application.namespace.parser.AbstractBeanDefinitionParser;

/**
 * The Class ContextBeanDefinitionParser.
 * 
 * This class implements the standard AbstractBeanDefinitionParser defined in
 * motwin SDK for spring namespace bean parsers.
 */
public class ContextBeanDefinitionParser extends AbstractBeanDefinitionParser {

    /** The reference attribute. */
    private static String REF_ATTRIBUTE   = "ref";
    /** The class attribute. */
    private static String CLASS_ATTRIBUTE = "class";
    /** The bean tag attribute. */
    private static String BEAN_ATTRIBUTE  = "bean";

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.
     * w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
     */
    @Override
    public BeanDefinition parse(Element aElement, ParserContext aParserContext) {
        Preconditions.checkNotNull(aElement, "The dom element aElement cannot be null");
        Preconditions.checkNotNull(aParserContext, "The ParserContext cannot be null");

        Object handlerReference = getHandlerReference(aElement, aParserContext);

        BeanDefinitionBuilder builder;
        builder = BeanDefinitionBuilder.genericBeanDefinition(ContextHandlerWrapper.class);
        builder.addConstructorArgValue(handlerReference);

        aParserContext.getReaderContext().registerWithGeneratedName(builder.getBeanDefinition());

        return null;
    }

    /**
     * This method analyses the Context Handler attributes to get a reference by
     * parsing the dom element aElement.
     * 
     * @param anElement
     *            the an element
     * @param aParserContext
     *            the spring parser context
     * @return a handler reference
     */
    private Object getHandlerReference(final Element anElement, final ParserContext aParserContext) {

        Object handlerRef = null;

        String refAttribute;
        String classAttribute;

        refAttribute = anElement.getAttribute(REF_ATTRIBUTE);
        classAttribute = anElement.getAttribute(CLASS_ATTRIBUTE);

        if (!Strings.isNullOrEmpty(refAttribute)) {
            handlerRef = new RuntimeBeanReference(refAttribute);

        } else if (!Strings.isNullOrEmpty(classAttribute)) {
            BeanDefinitionBuilder builder;
            String beanName;

            builder = BeanDefinitionBuilder.genericBeanDefinition(classAttribute);
            builder.getRawBeanDefinition().setSource(aParserContext.extractSource(anElement));

            beanName = aParserContext.getReaderContext().registerWithGeneratedName(builder.getBeanDefinition());

            handlerRef = new RuntimeBeanReference(beanName);

        } else {
            handlerRef = createBeanReference(anElement, aParserContext);
        }
        return handlerRef;
    }

    /**
     * This method is used to parse the dom element to get a reference to the
     * handler bean defined in the beans tag (beans:bean or beans:ref).
     * 
     * @param anElement
     *            the dom element to parse
     * @param aParserContext
     *            the spring parser context
     * @return the handler reference creating by parsing the spring beans tag
     *         (beans:bean or beans:ref)
     */
    private Object createBeanReference(final Element anElement, final ParserContext aParserContext) {

        Object bean = null;

        List<Element> childElements;
        childElements = DomUtils.getChildElements(anElement);

        for (Element element : childElements) {
            if (DomUtils.nodeNameEquals(element, REF_ATTRIBUTE) || DomUtils.nodeNameEquals(element, BEAN_ATTRIBUTE)) {

                BeanDefinitionBuilder beanDefinitionBuilder;
                beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();

                bean = aParserContext.getDelegate().parsePropertySubElement(element,
                        beanDefinitionBuilder.getBeanDefinition());

                if (BeanDefinitionHolder.class.isAssignableFrom(bean.getClass())) {
                    /**
                     * This code will be reached when
                     * DomUtils.nodeNameEquals(element, BEAN_ATTRIBUTE) because
                     * the method parsePropertySubElement will return a
                     * BeanDefinitionHolder in this case. In case of
                     * DomUtils.nodeNameEquals(element, REF_ATTRIBUTE) the
                     * method parsePropertySubElement will return a
                     * RuntimeBeanReference, so there is no need to register the
                     * bean and get a reference
                     */
                    String generatedName;
                    generatedName = aParserContext.getReaderContext().registerWithGeneratedName(
                            ((BeanDefinitionHolder) bean).getBeanDefinition());
                    bean = new RuntimeBeanReference(generatedName);
                }
            }
        }
        return bean;
    }
}
