/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.Mock;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class RoamingCountryCodeCollectorTest extends MockitoTestCase {
    private SpyChannel                  channel;
    private RoamingCountryCodeCollector collector;
    @Mock
    private Context                     context;
    @Mock
    private TelephonyManager            telephonyManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new RoamingCountryCodeCollector(channel, context, telephonyManager);
    };

    public void testSendContextOnStartNationalRoaming() throws InterruptedException {
        when(telephonyManager.isNetworkRoaming()).thenReturn(true);
        when(telephonyManager.getSimCountryIso()).thenReturn("abcdefgh");
        when(telephonyManager.getNetworkCountryIso()).thenReturn("abcdefgh");
        collector.start();

        verify(telephonyManager).isNetworkRoaming();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.ROAMING_COUNTRY_CODE_KEY, ctxElement.getType());
        assertEquals(RoamingCountryCodeCollector.NATIONAL_ROAMING, ctxElement.getData());
    }

    public void testSendContextOnStartNotRoaming() throws InterruptedException {
        when(telephonyManager.isNetworkRoaming()).thenReturn(false);
        collector.start();

        verify(telephonyManager).isNetworkRoaming();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.ROAMING_COUNTRY_CODE_KEY, ctxElement.getType());
        assertEquals(RoamingCountryCodeCollector.NOT_ROMAMING, ctxElement.getData());
    }

    public void testSendContextOnStartInternationalRoaming() throws InterruptedException {
        when(telephonyManager.isNetworkRoaming()).thenReturn(true);
        when(telephonyManager.getSimCountryIso()).thenReturn("abcdefgh");
        when(telephonyManager.getNetworkCountryIso()).thenReturn("ijklmno");
        collector.start();

        verify(telephonyManager).isNetworkRoaming();
        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertTrue(messages.get(0).getContent() instanceof ContextElement);
        ContextElement<?> ctxElement = (ContextElement<?>) messages.get(0).getContent();
        assertEquals(ContextAware.ROAMING_COUNTRY_CODE_KEY, ctxElement.getType());
        assertEquals(RoamingCountryCodeCollector.INTERNATIONAL_ROAMING, ctxElement.getData());
    }
}
