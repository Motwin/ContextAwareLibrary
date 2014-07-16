/**
 * 
 */
package com.motwin.android.context.collector;

import com.motwin.android.context.model.ContextElement;

/**
 * Collector interface.
 * 
 * @param <T>
 *            The type of Context Elements that will be collected
 * 
 */
public interface Collector<T> {

    /**
     * Starts the context collector.
     */
    void start();

    /**
     * Stops the context collector.
     * 
     */
    void stop();

    /**
     * Send the context element to the server
     * 
     * @param aContextElement
     *            the context element
     */
    void sendContextElement(ContextElement<T> aContextElement);

}
