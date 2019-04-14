package com.bittech.student.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bittech.student.dao.StudentDao;
import com.bittech.student.model.PageBean;
import com.bittech.student.model.Student;
import com.bittech.student.util.DbUtil;
import com.bittech.student.util.JsonUtil;
import com.bittech.student.util.ResponseUtil;
import com.bittech.student.util.StringUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class StudentListServlet extends HttpServlet {
    
    private DbUtil dbUtil = DbUtil.getInstance();
    
    private StudentDao studentDao = new StudentDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String stuNo = request.getParameter("stuNo");
        String stuName = request.getParameter("stuName");
        String sex = request.getParameter("sex");
        String bbirthday = request.getParameter("bbirthday");
        String ebirthday = request.getParameter("ebirthday");
        String gradeId = request.getParameter("gradeId");
        
        Student student = new Student();
        if (stuNo != null) {
            student.setStuNo(stuNo);
            student.setStuName(stuName);
            student.setSex(sex);
            if (StringUtil.isNotEmpty(gradeId)) {
                student.setGradeId(Integer.parseInt(gradeId));
            }
        }
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            JSONObject result = new JSONObject();
            JSONArray jsonArray = JsonUtil.formatRsToJsonArray(studentDao.studentList(con, pageBean, student, bbirthday, ebirthday));
            int total = studentDao.studentCount(con, student, bbirthday, ebirthday);
            result.put("rows", jsonArray);
            result.put("total", total);
            ResponseUtil.write(response, result);
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
