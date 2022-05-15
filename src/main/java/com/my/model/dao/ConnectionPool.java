package com.my.model.dao;

import org.apache.commons.dbcp.BasicDataSource;
import javax.sql.DataSource;

public class ConnectionPool {

    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPool.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName("com.mysql.jdbc.Driver");
                    ds.setUrl("jdbc:mysql://localhost:3306/exhibitions_db");
                    ds.setUsername("root");
                    ds.setPassword("123456");
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
