package com.bittech.student.web;

import com.alibaba.fastjson.JSONObject;
import com.bittech.student.dao.GradeDao;
import com.bittech.student.dao.StudentDao;
import com.bittech.student.util.DbUtil;
import com.bittech.student.util.ResponseUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class GradeDeleteServlet extends HttpServlet {
    
    private DbUtil dbUtil = DbUtil.getInstance();
    
    private GradeDao gradeDao = new GradeDao();
    
    private StudentDao studentDao = new StudentDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String delIds = request.getParameter("delIds");
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            JSONObject result = new JSONObject();
            String str[] = delIds.split(",");
            for (int i = 0; i < str.length; i++) {
                boolean f = studentDao.getStudentByGradeId(con, str[i]);
                if (f) {
                    result.put("errorIndex", i);
                    result.put("errorMsg", "班级下面有学生，不能删除！");
                    ResponseUtil.write(response, result);
                    return;
                }
            }
            int delNums = gradeDao.gradeDelete(con, delIds);
            if (delNums > 0) {
                result.put("success", "true");
                result.put("delNums", delNums);
            } else {
                result.put("errorMsg", "删除失败");
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
