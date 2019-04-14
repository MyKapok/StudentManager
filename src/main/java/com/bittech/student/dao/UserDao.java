package com.bittech.student.dao;

import com.bittech.student.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDao {
    
    /**
     * 登录验证
     *
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public User login(Connection con, User user) throws Exception {
        User resultUser = null;
        String sql = "select id,userName,password from t_user where userName=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            resultUser = new User();
            resultUser.setUserName(rs.getString("userName"));
            resultUser.setPassword(rs.getString("password"));
        }
        return resultUser;
    }
    
    public User register(Connection con, User user) throws Exception {
        String sql = "insert  into t_user (userName,password) values (?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        int effect = pstmt.executeUpdate();
        return effect == 1 ? user : null;
    }
}
