/**
 * 
 */
package com.motwin.android.context;

import com.motwin.android.context.collector.Collector;

/**
 * ContextCollectorRegistry class. Defines a registry of Context Collectors
 * 
 */
public interface ContextCollectorRegistry {

    /**
     * Starts the context collector registry. This will cause all default
     * context collectors to be started.
     */
    void start();

    /**
     * Stops the context collector registry. This will cause all default context
     * collectors to be stopped.
     */
    void stop();

    /**
     * Enables a new context collector.
     * 
     * @param aContextCollector
     *            The context collector to be enabled
     */
    void enableContextCollector(Collector<?> aContextCollector);

    /**
     * Disables the context collector.
     * 
     * @param aContextCollector
     *            The context collector to be disabled
     */
    void disableContextCollector(Collector<?> aContextCollector);
}
