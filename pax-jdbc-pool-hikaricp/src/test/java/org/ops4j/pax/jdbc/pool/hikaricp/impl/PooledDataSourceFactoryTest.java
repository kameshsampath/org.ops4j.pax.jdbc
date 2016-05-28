/*
 *
 *    Copyright (c) 2016. Kamesh Sampath.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied.
 *
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package org.ops4j.pax.jdbc.pool.hikaricp.impl;

import com.zaxxer.hikari.HikariDataSource;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Test;
import org.ops4j.pax.jdbc.hikaricp.impl.ds.HikariCPPooledDataSourceFactory;
import org.osgi.service.jdbc.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author by kameshs
 */
public class PooledDataSourceFactoryTest {

    @Test
    public void testCreateDataSource() throws SQLException {

        IMocksControl c = EasyMock.createControl();
        DataSourceFactory dsf = c.createMock(DataSourceFactory.class);
        DataSource exds = c.createMock(DataSource.class);
        EasyMock.expect(dsf.createDataSource(EasyMock.anyObject(Properties.class))).andReturn(exds).atLeastOnce();

        HikariCPPooledDataSourceFactory hcpDsf = new HikariCPPooledDataSourceFactory(dsf);
        c.replay();

        DataSource ds = hcpDsf.createDataSource(createValidDSProps());
        c.verify();

        Assert.assertEquals(HikariDataSource.class, ds.getClass());

    }

    private Properties createValidDSProps() {
        Properties properties = new Properties();
        //to run App without valid DB connection
        properties.put("initializationFailFast",false);
        return properties;
    }

}
