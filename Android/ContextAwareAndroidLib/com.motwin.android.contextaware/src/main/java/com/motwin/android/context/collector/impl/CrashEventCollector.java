/**
 * 
 */
package com.motwin.android.context.collector.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Crash event collector.
 * 
 */
public class CrashEventCollector extends AbstractOnChangeCollector<String> implements UncaughtExceptionHandler {

    private final UncaughtExceptionHandler defaultExceptionHandler;
    private UncaughtExceptionHandler       exceptionHandler;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aExceptionHandler
     *            A custom exception handler. Can be null.
     */
    public CrashEventCollector(ClientChannel aClientChannel, Context aApplicationContext,
            UncaughtExceptionHandler aExceptionHandler) {
        super(aClientChannel, aApplicationContext, 0);

        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        if (aExceptionHandler == null) {
            exceptionHandler = defaultExceptionHandler;
        } else {
            exceptionHandler = aExceptionHandler;
        }
    }

    @Override
    public void start() {
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void stop() {
        Thread.setDefaultUncaughtExceptionHandler(defaultExceptionHandler);
    }

    @Override
    public void uncaughtException(Thread aThread, Throwable anEx) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        anEx.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();
        ContextElement<String> contextElement = new ContextElement<String>(ContextAware.CRASH_EVENT_KEY, stacktrace);
        onChange(contextElement);

        exceptionHandler.uncaughtException(aThread, anEx);
    }

    /**
     * Get exception handler
     * 
     * @return The exception handler
     */
    public UncaughtExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }
}
