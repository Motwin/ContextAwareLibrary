/**
 * 
 */
package com.motwin.android.context.test;

import org.mockito.MockitoAnnotations;

import android.test.AndroidTestCase;

/**
 * @author lorie
 * 
 */
public class MockitoTestCase extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }
}
