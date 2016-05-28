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

package org.ops4j.pax.jdbc.hikaricp.impl;

import org.ops4j.pax.jdbc.hikaricp.impl.ds.HikariCPPooledDataSourceFactory;
import org.ops4j.pax.jdbc.pool.common.impl.AbstractDataSourceFactoryTracker;
import org.osgi.framework.BundleContext;
import org.osgi.service.jdbc.DataSourceFactory;

import javax.transaction.TransactionManager;

/**
 * @author kameshs
 */
public class DataSourceFactoryTracker extends AbstractDataSourceFactoryTracker {

    public DataSourceFactoryTracker(BundleContext bundleContext) {
        this(bundleContext, null);
    }

    public DataSourceFactoryTracker(BundleContext bundleContext, TransactionManager tm) {
        super(bundleContext, tm);
    }

    @Override
    protected DataSourceFactory createPooledDatasourceFactory(DataSourceFactory dsf) {
        return new HikariCPPooledDataSourceFactory(dsf);
    }
}
