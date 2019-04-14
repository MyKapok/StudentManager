package com.bittech.student.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.bittech.student.listener.ApplicationConfigListener;

import javax.sql.DataSource;
import java.sql.Connection;

public class DbUtil {
    
    private static DbUtil dbUtil;
    
    private DataSource dataSource;
    
    private DbUtil() {
        ApplicationConfigListener.DbConfig dbConfig = ApplicationConfigListener.dbConfig;
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dbConfig.getJdbcUrl());
        druidDataSource.setPassword(dbConfig.getJdbcPassword());
        druidDataSource.setUsername(dbConfig.getJdbcUsername());
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        this.dataSource = druidDataSource;
    }
    
    public static DbUtil getInstance() {
        if (dbUtil == null) {
            synchronized(DbUtil.class) {
                if (dbUtil == null) {
                    dbUtil = new DbUtil();
                }
            }
        }
        return dbUtil;
    }
    
    
    public Connection getConnection() throws Exception {
        return this.dataSource.getConnection();
    }
    
    public void closeConnection(Connection con) throws Exception {
        if (con != null) {
            con.close();
        }
    }
}