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

@WebServlet(name = "UserLoginServlet", urlPatterns = {"/login"})
public class UserLoginServlet extends HttpServlet {
    
    private DbUtil dbUtil = DbUtil.getInstance();
    
    private UserDao userDao = new UserDao();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        request.setAttribute("userName", userName);
        request.setAttribute("password", password);
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            request.setAttribute("error", "用户名和密码不能为空");
            
            //转发 index.jsp
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        User user = new User(userName, password);
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            User currentUser = userDao.login(con, user);
            if (currentUser == null) {
                request.setAttribute("error", "用户名或密码错误");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", currentUser);
                //重定向
                response.sendRedirect("main.jsp");
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
