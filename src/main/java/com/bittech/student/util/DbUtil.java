package com.bittech.student.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.bittech.student.listener.ApplicationConfigListener;

import javax.sql.DataSource;
import java.sql.Connection;

//数据源操作
public class DbUtil {
    
    //单例
    private static DbUtil dbUtil;
    
    private DataSource dataSource;
    
    private DbUtil() {
        //从监听器里取得数据源需要的参数
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
    
    //取得连接
    public Connection getConnection() throws Exception {
        return this.dataSource.getConnection();
    }
    //关闭连接
    public void closeConnection(Connection con) throws Exception {
        if (con != null) {
            con.close();
        }
    }
}
