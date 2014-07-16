/**
 * 
 */
package com.motwin.android.context.collector;

import android.content.Context;

import com.google.common.base.Preconditions;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Abstract on change collector. This type of collector will automatically send
 * context elements as soon as the value is changing. To manage update throttle,
 * a minimum delay between context updates can be defined.
 * 
 */
public abstract class AbstractOnChangeCollector<T> extends AbstractCollector<T> {

    private final long        minDelay;
    private long              lastSendTimestamp;
    private ContextElement<T> contextValue;

    /**
     * Abstract constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send the context
     *            elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between two context updates. Must be >= 0.
     *            If 0, the context value will be sent as soon as it changes.
     */
    public AbstractOnChangeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
        super(aClientChannel, aApplicationContext);
        Preconditions.checkArgument(aMinDelay >= 0, "aMinDelay must be >= 0");
        minDelay = aMinDelay;
    }

    /**
     * Notifies that the context element have changed. <br>
     * The new context value will be sent to server if
     * <ul>
     * <li>the value of the new context element is different from the previous
     * value.</li>
     * <li>the last context element was sent more that aMinDelay ago.</li>
     * </ul>
     * 
     * @param aContextElement
     *            The new value of the context element.
     */
    public final void onChange(ContextElement<T> aContextElement) {
        Preconditions.checkNotNull(aContextElement, "aContextElement cannot be null");

        if (aContextElement.equals(contextValue)) {
            // same value : do not forward
        } else if (lastSendTimestamp + minDelay > System.currentTimeMillis()) {
            // value have been sent less that minDelay ms ago : don't send
        } else {
            sendContextElement(aContextElement);
            contextValue = aContextElement;
            lastSendTimestamp = System.currentTimeMillis();
        }
    }

    /**
     * Get the min delay between context updates
     * 
     * @return The min delay between context updates
     */
    public long getMinDelay() {
        return minDelay;
    }
}
