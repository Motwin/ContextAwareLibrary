/**
 * Copyright Motwin Inc. - 2013
 * http://www.motwin.com
 * 
 * All rights reserved.
 */
package com.motwin.context.extension.namespace.parser.test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.motwin.context.dispatcher.ContextDispatcher;
import com.motwin.context.model.AccelerometerData;
import com.motwin.context.model.ContextElement;
import com.motwin.context.model.GeoLocData;
import com.motwin.context.model.MobileContextElement;
import com.motwin.platform.test.OsgiContextLoader;

/**
 * The Class ContextBeanDefinitionParserTest.
 * 
 * @author abdelbaki
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/context-test.xml" }, loader = OsgiContextLoader.class)
@Ignore
public class ContextBeanDefinitionParserTest {

    /** The Constant DELTA. */
    private static final double       DELTA           = 1e-15;

    /** The Constant INSTALLATION_ID. */
    private static final String       INSTALLATION_ID = "testInstallationId";

    /** The Constant TEST_KEY. */
    private static final String       TEST_KEY        = "testKey";

    /** The Constant TEST_DATA. */
    private static final String       TEST_DATA       = "testData";

    /** The context dispatcher. */
    @Autowired
    private ContextDispatcher         contextDispatcher;

    /** The context handler1. */
    @Autowired
    // @Qualifier("contextHandlerId1")
    private DummyContextHandler       contextHandler1;

    /** The context handler2. */
    @Autowired
    // @Qualifier("contextHandlerId2")
    private DummyMobileContextHandler contextHandler2;

    /**
     * Test default context element.
     * 
     * @throws InterruptedException
     *             the interrupted exception
     */
    @Test
    public void testDefaultContextElement() throws InterruptedException {
        Assert.assertNotNull("ContextDispatcher cannot be null", contextDispatcher);

        MobileContextElement<String> contextElement;
        contextElement = new MobileContextElement<String>();
        contextElement.setType(TEST_KEY);
        contextElement.setData(TEST_DATA);

        contextElement.setInstallationId(INSTALLATION_ID);
        contextDispatcher.reportEvent(contextElement);

        assertions(contextHandler1.getReceivedContextElement().poll(500, MILLISECONDS));
        assertions(contextHandler2.getReceivedContextElement().poll(500, MILLISECONDS));
    }

    /**
     * Test geoloc context element.
     * 
     * @throws InterruptedException
     *             the interrupted exception
     */
    @Test
    public void testGeolocContextElement() throws InterruptedException {
        Assert.assertNotNull("ContextDispatcher cannot be null", contextDispatcher);

        GeoLocData geoLocData = new GeoLocData();
        geoLocData.setLatitude(10.10);
        geoLocData.setLongitude(20.20);
        geoLocData.setTimestamp(123456789);

        ContextElement<GeoLocData> contextElement;
        contextElement = new ContextElement<GeoLocData>();
        contextElement.setType("testKey");
        contextElement.setData(geoLocData);

        contextDispatcher.reportEvent(contextElement);

        assertions(contextHandler1.getReceivedContextElement().poll(500, MILLISECONDS));
        assertNull(contextHandler2.getReceivedContextElement().poll(500, MILLISECONDS));
    }

    /**
     * Test accelerometer context element.
     * 
     * @throws InterruptedException
     *             the interrupted exception
     */
    @Test
    public void testAccelerometerContextElement() throws InterruptedException {
        Assert.assertNotNull("ContextDispatcher cannot be null", contextDispatcher);

        AccelerometerData accelerometerData = new AccelerometerData();
        accelerometerData.setX(1);
        accelerometerData.setY(2);
        accelerometerData.setZ(3);

        ContextElement<AccelerometerData> contextElement;
        contextElement = new ContextElement<AccelerometerData>();
        contextElement.setType("testKey");
        contextElement.setData(accelerometerData);

        contextDispatcher.reportEvent(contextElement);

        assertions(contextHandler1.getReceivedContextElement().poll(500, MILLISECONDS));
        assertNull(contextHandler2.getReceivedContextElement().poll(500, MILLISECONDS));
    }

    /**
     * Assertions.
     * 
     * @param aContextElement
     *            the context element
     */
    private void assertions(final ContextElement<?> aContextElement) {
        Assert.assertNotNull(aContextElement);

        String installationId;
        if (aContextElement instanceof MobileContextElement<?>) {
            installationId = ((MobileContextElement<?>) aContextElement).getInstallationId();
            Assert.assertNotNull(installationId);
            Assert.assertEquals(INSTALLATION_ID, installationId);
        }

        Object contextElementData = aContextElement.getData();
        Assert.assertNotNull(contextElementData);

        String contextElementType = aContextElement.getType();
        Assert.assertNotNull(contextElementType);
        Assert.assertEquals(TEST_KEY, contextElementType);

        if (contextElementData instanceof String) {
            Assert.assertEquals(TEST_DATA, contextElementData);

        } else if (contextElementData instanceof GeoLocData) {
            GeoLocData geoLocData = (GeoLocData) contextElementData;
            double latitude = geoLocData.getLatitude();
            double longitude = geoLocData.getLongitude();
            long timestamp = geoLocData.getTimestamp();

            Assert.assertEquals(latitude, 10.10, DELTA);
            Assert.assertEquals(longitude, 20.20, DELTA);
            Assert.assertEquals(timestamp, 123456789);

        } else if (contextElementData instanceof AccelerometerData) {
            AccelerometerData accelerometerData = (AccelerometerData) contextElementData;
            double x = accelerometerData.getX();
            double y = accelerometerData.getY();
            double z = accelerometerData.getZ();

            Assert.assertEquals(x, 1, DELTA);
            Assert.assertEquals(y, 2, DELTA);
            Assert.assertEquals(z, 3, DELTA);
        }

    }
}
