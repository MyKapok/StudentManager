package com.bittech.student.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bittech.student.dao.GradeDao;
import com.bittech.student.model.Grade;
import com.bittech.student.model.PageBean;
import com.bittech.student.util.DbUtil;
import com.bittech.student.util.JsonUtil;
import com.bittech.student.util.ResponseUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class GradeListServlet extends HttpServlet {
    
    private DbUtil dbUtil = DbUtil.getInstance();
    
    private GradeDao gradeDao = new GradeDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String gradeName = request.getParameter("gradeName");
        if (gradeName == null) {
            gradeName = "";
        }
        Grade grade = new Grade();
        grade.setGradeName(gradeName);
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            JSONObject result = new JSONObject();
            JSONArray jsonArray = JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, pageBean, grade));
            int total = gradeDao.gradeCount(con, grade);
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
