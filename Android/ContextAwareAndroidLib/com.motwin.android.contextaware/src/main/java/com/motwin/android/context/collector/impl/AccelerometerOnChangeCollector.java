/**
 * 
 */
package com.motwin.android.context.collector.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.google.common.base.Preconditions;
import com.motwin.android.context.ContextAware;
import com.motwin.android.context.collector.AbstractOnChangeCollector;
import com.motwin.android.context.model.AccelerometerData;
import com.motwin.android.context.model.ContextElement;
import com.motwin.android.network.clientchannel.ClientChannel;

/**
 * Accelerometer on change collector.
 */
public class AccelerometerOnChangeCollector extends AbstractOnChangeCollector<AccelerometerData> implements
        SensorEventListener {

    private final SensorManager sensorManager;

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between context updates
     */
    public AccelerometerOnChangeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay) {
        this(aClientChannel, aApplicationContext, aMinDelay, (SensorManager) aApplicationContext
                .getSystemService(Context.SENSOR_SERVICE));
    }

    /**
     * Constructor
     * 
     * @param aClientChannel
     *            The ClientChannel that will be used to send context elements
     * @param aApplicationContext
     *            The application context
     * @param aMinDelay
     *            The minimum delay between context updates
     * @param aSensorManager
     *            The sensor manager
     */
    protected AccelerometerOnChangeCollector(ClientChannel aClientChannel, Context aApplicationContext, long aMinDelay,
            SensorManager aSensorManager) {
        super(aClientChannel, aApplicationContext, aMinDelay);
        sensorManager = Preconditions.checkNotNull(aSensorManager, "aSensorManager cannot be null");

    }

    @Override
    public void start() {
        Sensor accelerometerSensor;
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        boolean supportedSensor;
        supportedSensor = sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Preconditions.checkState(supportedSensor, "This device does not support accelerometer sensor");

    }

    @Override
    public void stop() {
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent anEvent) {
        onSensorChanged(anEvent.sensor, anEvent.values);

    }

    /**
     * Handle sensor change event
     * 
     * @param aSensor
     *            The sensor that reported new values
     * @param aValues
     *            The values reported by the sensor
     */
    protected void onSensorChanged(Sensor aSensor, float[] aValues) {
        Preconditions.checkNotNull(aSensor, "aSensor cannot be null");
        Preconditions.checkNotNull(aValues, "aValues cannot be null");

        if (aSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = aValues[0];
            float y = aValues[1];
            float z = aValues[2];

            AccelerometerData accelometerData = new AccelerometerData(x, y, z);

            ContextElement<AccelerometerData> accelerometerContexElement = new ContextElement<AccelerometerData>(
                    ContextAware.ACCELEROMETER_KEY, accelometerData);

            onChange(accelerometerContexElement);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor aSensor, int aAccuracy) {
        // NOOP
    }

}
