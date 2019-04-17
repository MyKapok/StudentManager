package com.bittech.student.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;


public class JsonUtil {
    //对传出数据库的结果集Json的格式化
    public static JSONArray formatRsToJsonArray(ResultSet rs) throws Exception {
        //创建ResultSetMetaDate对象，一个对象，可用于获取关于ResultSet对象中列的类型和属性的信息。
        ResultSetMetaData md = rs.getMetaData();
        //统计有多少列
        int num = md.getColumnCount();
        JSONArray array = new JSONArray();
        while (rs.next()) {
            JSONObject mapOfColValues = new JSONObject();
            for (int i = 1; i <= num; i++) {
                //获取当前指定列的值
                Object o = rs.getObject(i);
                if (o instanceof Date) {
                    //如果该列是时间，返回显示给定列的表的目录名，再将时间格式化，一起放入Json对象中
                    mapOfColValues.put(md.getColumnName(i), DateUtil.formatDate((Date) o, "yyyy-MM-dd"));
                } else {
                    //如果不是时间，直接放入
                    mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
                }
            }
            //再将其放入array中
            array.add(mapOfColValues);
        }
        return array;
    }
}
