package com.bittech.student.web;

import com.bittech.student.dao.UserDao;
import com.bittech.student.model.User;
import com.bittech.student.util.DbUtil;
import com.bittech.student.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "UserRegisterServlet", urlPatterns = {"/register"})
public class UserRegisterServlet extends HttpServlet {
    
    private DbUtil dbUtil = DbUtil.getInstance();
    
    private UserDao userDao = new UserDao();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        request.setAttribute("userName", userName);
        request.setAttribute("password", password);
        
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password) || StringUtil.isEmpty(passwordRepeat)) {
            request.setAttribute("error", "用户名和密码不能为空");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(passwordRepeat)) {
            request.setAttribute("error", "两次输入的密码不一致");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        User user = new User(userName, password);
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            User currentUser = userDao.register(con, user);
            if (currentUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", currentUser);
                response.sendRedirect("main.jsp");
            } else {
                request.setAttribute("error", "注册失败，稍后重试");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
