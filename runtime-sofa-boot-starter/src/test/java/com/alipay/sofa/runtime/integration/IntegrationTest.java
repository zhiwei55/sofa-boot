/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.runtime.integration;

import com.alipay.sofa.healthcheck.core.HealthChecker;
import com.alipay.sofa.runtime.integration.base.AbstractTestBase;
import com.alipay.sofa.runtime.integration.features.AwareTest;
import com.alipay.sofa.runtime.spi.SofaFrameworkHolder;
import com.alipay.sofa.runtime.spi.component.SofaRuntimeContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.ApplicationContext;

/**
 * @author qilong.zql
 * @since 2.3.1
 */
public class IntegrationTest extends AbstractTestBase {

    @Autowired
    private AwareTest awareTest;

    @Test
    public void testSofaClientFactoryAnnotationTest() {
        Assert.assertNotNull(awareTest);
        Assert.assertNotNull(awareTest.getClientFactoryAware());
        Assert.assertNotNull(awareTest.getClientFactory());
        Assert.assertNotNull(awareTest.getReferenceClient());
        Assert.assertNotNull(awareTest.getServiceClient());
    }

    @Test
    public void testSofaRuntimeAwareTest() {
        Assert.assertNotNull(awareTest);
        Assert.assertNotNull(awareTest.getSofaRuntimeContext());

        SofaRuntimeContext sofaRuntimeContext = awareTest.getSofaRuntimeContext();
        Assert.assertTrue(sofaRuntimeContext.getAppName().equals("runtime-test"));
        Assert.assertTrue(sofaRuntimeContext.getClientFactory()
            .equals(awareTest.getClientFactory()));
        Assert.assertTrue(sofaRuntimeContext.equals(SofaFrameworkHolder.getSofaFramework()
            .getSofaRuntimeContext("runtime-test")));

    }

    @Test
    public void testHealthChecker() {
        Assert.assertNotNull(awareTest.getApplicationContext());

        ApplicationContext context = awareTest.getApplicationContext();
        Assert.assertNotNull(context.getBean("componentHealthChecker"));

        HealthChecker healthChecker = (HealthChecker) context.getBean("componentHealthChecker");
        Assert.assertTrue(healthChecker.isHealthy().getStatus().equals(Status.UP));
    }

}