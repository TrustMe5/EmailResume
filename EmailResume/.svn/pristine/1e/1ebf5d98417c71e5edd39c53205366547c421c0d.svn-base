package org.emailresume.htmlparser;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String getHtmlFromDocument(Document doc){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult streamResult = new StreamResult(bos);
            transformer.transform(source, streamResult);
            return bos.toString("UTF-8");
        }catch (Exception e){

        }
        return "";
    }

    public static Long parseZhiLianTime(String time) throws ParseException {
        return parseTime(time, "yyyy/MM");
    }

    public static Long parseTime(String time, String formatStr) throws ParseException {
        if(time.equals("至今") || time.equals("")){
            return new Long(0);
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = format.parse(time);
        Long timeStemp = date.getTime() / 1000;
        return timeStemp;
    }

    //全职:1 兼职:2  实习:3  全/兼职:4
    public static String getJobNatureCodeFromString(String jobNature){
        jobNature = jobNature.trim();
        if(jobNature.equals("全职")){
            return "1";
        }
        if(jobNature.equals("兼职")){
            return "2";
        }
        if(jobNature.equals("实习")){
            return "3";
        }
        if(jobNature.equals("全/兼职")){
            return "4";
        }
        return "";
    }

    //// 初中:1 高中:2 	中技:3  中专:4 大专:5 本科:6 MBA&EMBA:7 硕士:8 博士:9  	其他:10
    public static Integer getBackgdCodeFromString(String backgd){
        if(backgd.contains("本科")){
            return 6;
        }
        if(backgd.contains("初中")){
            return 1;
        }
        if(backgd.contains("高中")){
            return 2;
        }
        if(backgd.contains("中技")){
            return 3;
        }
        if(backgd.contains("中专")){
            return 4;
        }
        if(backgd.contains("大专")){
            return 5;
        }
        if(backgd.contains("MBA") || backgd.contains("EMBA")){
            return 7;
        }
        if(backgd.contains("硕士")){
            return 8;
        }
        if(backgd.contains("博士")){
            return 9;
        }
        if(backgd.contains("其它")){
            return 10;
        }
        return -1;
    }

    // 男:1  	女:2
    public static Integer getGenderCodeFromString(String gender){
        if(gender.contains("男")){
            return 1;
        }
        if(gender.contains("女")){
            return 2;
        }
        return -1;
    }

    public static Integer getCityCodeFromString(String city){
        if(city.equals("")){
            return 0;
        }
        Integer code = new Integer(0);
        try {
            String url = "jdbc:mysql://192.168.6.81:3306/hr_dict_center?"
                    + "user=miaohr&password=miaohr1qaz&useUnicode=true&characterEncoding=UTF8";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = String.format("select code, name from hr_area where name like '%%%s%%'", city);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                code = rs.getInt(1);
            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return code;
    }

    public static String GetNumFromString(String str) {
        str=str.trim();
        String str2="";
        if(str != null && !"".equals(str)){
            for(int i=0;i<str.length();i++){
                if(str.charAt(i)>=48 && str.charAt(i)<=57){
                    str2+=str.charAt(i);
                }
            }
        }
        return str2;
    }

    public static String getCityCodeFromStringList(String [] citylist){

        return "";
    }

    public static String getJobCodeFromString(String job){
        if(job.equals("")){
            return "";
        }
        String code = "";
        try {
            String url = "jdbc:mysql://192.168.6.81:3306/hr_dict_center?"
                    + "user=miaohr&password=miaohr1qaz&useUnicode=true&characterEncoding=UTF8";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = String.format("select id from hr_job where name = '%s'", job);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                code = rs.getString(1);
            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return code;
    }

    public static String getJobTradeCodeFromString(String trade){
        if(trade.equals("")){
            return "";
        }
        String code = "";
        Connection conn = null;
        try {
            String url = "jdbc:mysql://192.168.6.81:3306/hr_dict_center?"
                    + "user=miaohr&password=miaohr1qaz&useUnicode=true&characterEncoding=UTF8";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = String.format("select id from hr_trade where name = '%s'", trade);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                code = rs.getString(1);
            }
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (Exception e){

            }
        }

        return code;
    }

    public static Long getCurrentTimestamp(){
        return System.currentTimeMillis() / 1000;
    }

}
