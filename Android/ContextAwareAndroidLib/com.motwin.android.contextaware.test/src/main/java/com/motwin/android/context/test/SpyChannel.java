/**
 * 
 */
package com.motwin.android.context.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.motwin.android.network.clientchannel.ClientChannel;
import com.motwin.android.network.clientchannel.Message;
import com.motwin.android.network.clientchannel.MessageImpl;
import com.motwin.android.network.clientchannel.MessageListener;
import com.motwin.android.network.request.Request;
import com.motwin.android.network.request.Request.Callback;

/**
 * @author lorie
 * 
 */
public class SpyChannel implements ClientChannel {

    private final Map<String, String>                userInfos;
    private final BiMap<String, Class<?>>            serializables;
    private final LinkedBlockingQueue<Message<?>>    sentMessages;
    private final LinkedBlockingQueue<Request<?, ?>> sentRequests;

    public SpyChannel() {
        userInfos = Maps.newHashMap();
        serializables = HashBiMap.<String, Class<?>> create();
        sentMessages = new LinkedBlockingQueue<Message<?>>();
        sentRequests = new LinkedBlockingQueue<Request<?, ?>>();
    }

    @Override
    public void connect() {
        // NOOP

    }

    @Override
    public void disconnect() {
        // NOOP

    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public void setAutoReconnect(boolean aAutoreconnect) {
        // NOOP

    }

    @Override
    public void addUserInfos(Map<String, String> aUserInfos) {
        userInfos.putAll(aUserInfos);

    }

    @Override
    public void addSerializables(BiMap<String, Class<?>> aSerializables) {
        serializables.putAll(aSerializables);
    }

    @Override
    public void sendMessage(Message<?> aMessage) {
        sentMessages.add(aMessage);

    }

    @Override
    public void sendMessage(Message<?> aMessage, DeliveryMode aDeliveryMode, long aTtl) {
        sendMessage(aMessage);
    }

    @SuppressWarnings("hiding")
    @Override
    public <Q, R> int sendRequest(Request<Q, R> aRequest, Callback<Q, R> aRequestListener, long aTimeout) {
        sentRequests.add(aRequest);
        return 0;
    }

    @SuppressWarnings("hiding")
    @Override
    public <Q, R> int sendRequest(Request<Q, R> aRequest, Callback<Q, R> aRequestListener, long aTimeout,
                                  DeliveryMode aDeliveryMode, long aTtl) {
        return sendRequest(aRequest, aRequestListener, aTimeout);
    }

    @Override
    public void cancelRequest(int aRequestIdentifier) {
        // NOOP

    }

    @Override
    public void registerMessageProcessor(String aMessageType, MessageListener<? extends Object> aProcessor) {
        // NOOP

    }

    @Override
    public void unregisterMessageProcessor(String aMessageType, MessageListener<? extends Object> aProcessor) {
        // NOOP

    }

    @Override
    public void unregisterForAllMessageTypesMessageProcessor(MessageListener<?> aProcessor) {
        // NOOP

    }

    public List<Message<?>> waitMessageSent(int aNbMessage, long aTimeOut, TimeUnit aTimeUnit)
            throws InterruptedException {
        List<Message<?>> messages = Lists.newArrayList();
        for (int i = 0; i < aNbMessage; i++) {
            Message<?> message;
            message = sentMessages.poll(aTimeOut, aTimeUnit);
            Preconditions.checkNotNull(message, "Expected %s message, but received %s", aNbMessage, i);
            messages.add(message);
        }
        return messages;
    }

    public BiMap<String, Class<?>> getSerializables() {
        return serializables;
    }

    public int getPendingMessagesSize() {
        return sentMessages.size();
    }

    public int getPendingRequestsSize() {
        return sentRequests.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.android.network.clientchannel.ClientChannel#sendMessage(java
     * .lang.String, java.lang.Object)
     */
    @Override
    public <T> void sendMessage(String aMessageType, T aMessageContent) {

        Message<T> message = new MessageImpl<T>(aMessageType, aMessageContent);
        sentMessages.add(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.android.network.clientchannel.ClientChannel#sendMessage(java
     * .lang.String, java.lang.Object,
     * com.motwin.android.network.clientchannel.ClientChannel.DeliveryMode,
     * long)
     */
    @Override
    public <T> void sendMessage(String aMessageType, T aMessageContent, DeliveryMode aDeliveryMode, long aTtl) {
        sendMessage(aMessageType, aMessageContent);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.android.network.clientchannel.ClientChannel#sendRequest(com
     * .motwin.android.network.request.Request,
     * com.motwin.android.network.request.Request.Callback)
     */
    @Override
    public <Q, R> int sendRequest(Request<Q, R> aRequest, Callback<Q, R> aRequestListener) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.android.network.clientchannel.ClientChannel#getUserInfos()
     */
    @Override
    public Map<String, String> getUserInfos() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.android.network.clientchannel.ClientChannel#getServerUrl()
     */
    @Override
    public String getServerUrl() {
        // TODO Auto-generated method stub
        return null;
    }
}
