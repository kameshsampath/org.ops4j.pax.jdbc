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

package org.ops4j.pax.jdbc.hikaricp.impl.ds;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author by kameshs
 */
public class HikariCPPooledDataSourceFactory implements DataSourceFactory {

    private static final Logger LOG = LoggerFactory.getLogger(HikariCPPooledDataSourceFactory.class);

    private DataSourceFactory dsFactory;

    public HikariCPPooledDataSourceFactory(DataSourceFactory dsFactory){
        this.dsFactory=dsFactory;
    }

    @Override
    public DataSource createDataSource(Properties props) throws SQLException {
        try {
            DataSource ds = dsFactory.createDataSource(props);
            HikariConfig hikariConfig = new HikariConfig(props);
            if(!props.containsKey("registerMbeans")){
                hikariConfig.setRegisterMbeans(true);
            }
            hikariConfig.setDataSource(ds);
            return new HikariDataSource(hikariConfig);
        }
        catch (Throwable e) {
            LOG.error("Error creating pooled datasource" + e.getMessage(), e);
            if (e instanceof SQLException) {
                throw (SQLException) e;
            }
            else if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    @Override
    public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
        throw new SQLException("Not supported");
    }

    @Override
    public XADataSource createXADataSource(Properties props) throws SQLException {
        throw new SQLException("Not supported");
    }

    @Override
    public Driver createDriver(Properties props) throws SQLException {
        throw new SQLException("Not supported");
    }
}
