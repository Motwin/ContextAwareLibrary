/**
 * 
 */
package com.motwin.android.context.collector.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.Mock;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.motwin.android.context.ContextAware;
import com.motwin.android.context.model.AccelerometerData;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.context.test.MockitoTestCase;
import com.motwin.android.context.test.SpyChannel;
import com.motwin.android.network.clientchannel.Message;

/**
 * @author lorie
 * 
 */
public class AccelerometerPeriodicCollectorTest extends MockitoTestCase {

    private SpyChannel                     channel;
    private AccelerometerOnChangeCollector collector;
    @Mock
    private SensorManager                  sensorManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        channel = new SpyChannel();
        collector = new AccelerometerOnChangeCollector(channel, getContext(), 1000, sensorManager);
    }

    public void testAddListenerOnStart() {
        Sensor sensor = mock(Sensor.class);
        when(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)).thenReturn(sensor);
        when(sensorManager.registerListener(collector, sensor, SensorManager.SENSOR_DELAY_NORMAL)).thenReturn(true);

        collector.start();

        verify(sensorManager).getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        verify(sensorManager).registerListener(collector, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void testThrowExceptionOnStartIfSensorNotSupported() {
        Sensor sensor = mock(Sensor.class);
        when(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)).thenReturn(sensor);
        when(sensorManager.registerListener(collector, sensor, SensorManager.SENSOR_DELAY_NORMAL)).thenReturn(false);

        try {
            collector.start();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
            // expected exception
            verify(sensorManager).getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            verify(sensorManager).registerListener(collector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void testRemoveListenerOnStop() {
        collector.stop();

        verify(sensorManager).unregisterListener(collector);
    }

    public void testForwardContextElementOnSensorChanged() throws InterruptedException {
        Sensor sensor = mock(Sensor.class);

        float x, y, z;
        x = 1.1f;
        y = 2.2f;
        z = 3.3f;
        AccelerometerData expectedData = new AccelerometerData(x, y, z);

        when(sensor.getType()).thenReturn(Sensor.TYPE_ACCELEROMETER);

        collector.onSensorChanged(sensor, new float[] { x, y, z });

        List<Message<?>> messages = channel.waitMessageSent(1, 500, MILLISECONDS);
        assertEquals(1, messages.size());
        Message<?> message = messages.get(0);
        assertEquals(ContextAware.MESSAGE_TYPE, message.getType());
        Object messageContent = message.getContent();
        assertTrue(messageContent instanceof ContextElement);
        assertEquals(ContextAware.ACCELEROMETER_KEY, ((ContextElement<?>) messageContent).getType());
        Object contextData = ((ContextElement<?>) messageContent).getData();
        assertTrue(contextData instanceof AccelerometerData);
        assertEquals(expectedData, contextData);

    }
}
