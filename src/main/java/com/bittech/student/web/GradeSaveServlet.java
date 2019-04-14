package com.bittech.student.web;

import com.alibaba.fastjson.JSONObject;
import com.bittech.student.dao.GradeDao;
import com.bittech.student.model.Grade;
import com.bittech.student.util.DbUtil;
import com.bittech.student.util.ResponseUtil;
import com.bittech.student.util.StringUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class GradeSaveServlet extends HttpServlet {
    
    private DbUtil dbUtil = DbUtil.getInstance();
    
    private GradeDao gradeDao = new GradeDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        this.doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("utf-8");
        String gradeName = request.getParameter("gradeName");
        String gradeDesc = request.getParameter("gradeDesc");
        String id = request.getParameter("id");
        Grade grade = new Grade(gradeName, gradeDesc);
        if (StringUtil.isNotEmpty(id)) {
            grade.setId(Integer.parseInt(id));
        }
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            int saveNums = 0;
            JSONObject result = new JSONObject();
            if (StringUtil.isNotEmpty(id)) {
                saveNums = gradeDao.gradeModify(con, grade);
            } else {
                saveNums = gradeDao.gradeAdd(con, grade);
            }
            if (saveNums > 0) {
                result.put("success", "true");
            } else {
                result.put("success", "true");
                result.put("errorMsg", "保存失败");
            }
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
