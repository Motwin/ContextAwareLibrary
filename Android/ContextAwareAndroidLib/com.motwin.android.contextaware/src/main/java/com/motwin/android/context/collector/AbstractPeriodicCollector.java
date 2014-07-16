/**
 * 
 */
package com.motwin.android.context.collector;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Context;

import com.google.common.base.Preconditions;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Abstract periodic collector. This type of collector will periodically send
 * context elements.
 * 
 */
public abstract class AbstractPeriodicCollector<T> extends AbstractCollector<T> {
    private final ScheduledExecutorService scheduler;
    protected final long                   delay;
    private ScheduledFuture<?>             taskFuture;

    /**
     * Abstract constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aPeriodInMilliSeconds
     *            The delay between context updates
     */
    public AbstractPeriodicCollector(ClientChannel aClientChannel, Context aApplicationContext,
            long aPeriodInMilliSeconds) {
        super(aClientChannel, aApplicationContext);
        Preconditions.checkArgument(aPeriodInMilliSeconds > 0, "aPeriodInMilliSeconds must be > 0");

        scheduler = new ScheduledThreadPoolExecutor(1);
        delay = aPeriodInMilliSeconds;
    }

    @Override
    public final void start() {
        onStart();

        taskFuture = scheduler.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                ContextElement<T> contextElement = collect();
                if (contextElement != null) {
                    sendContextElement(contextElement);
                }

            }
        }, 0, delay, TimeUnit.MILLISECONDS);

    }

    @Override
    public final void stop() {
        Preconditions.checkState(taskFuture != null, "Collector cannot be stopped before it have been started");
        taskFuture.cancel(false);

        onStop();
    }

    /**
     * Get the delay between context updates
     * 
     * @return the delay between context updates
     */
    public long getDelay() {
        return delay;
    }

    /**
     * Abstract method to define custom behavior before collector starts
     */
    public abstract void onStart();

    /**
     * Abstract method to define custom behavior after collector stops
     */
    public abstract void onStop();

    /**
     * Abstract method that must be implemented to retreive the ContextElement
     * to be forwarded to server on start.
     * 
     * @return The ContextElement. If null, nothing will be sent to the server.
     */
    public abstract ContextElement<T> collect();

}
