package com.bittech.student.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bittech.student.util.DbUtil;
import com.bittech.student.util.DbUtil;
import com.bittech.student.util.JsonUtil;
import com.bittech.student.util.ResponseUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class GradeComboListServlet extends HttpServlet {
    
    private DbUtil dbUtil = DbUtil.getInstance();
    
    private GradeDao gradeDao = new GradeDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "");
            jsonObject.put("gradeName", "请选择...");
            jsonArray.add(jsonObject);
            jsonArray.addAll(JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, null, null)));
            ResponseUtil.write(response, jsonArray);
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
