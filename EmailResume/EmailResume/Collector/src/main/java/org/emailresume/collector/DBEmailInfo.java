package org.emailresume.collector;

import org.apache.log4j.*;
import org.emailresume.common.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBEmailInfo {

    private static final Logger logger = Logger.getLogger(DBEmailInfo.class);
    final String m_url = "jdbc:mysql://192.168.6.80:3306/email_resume?" + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
    Connection m_conn = null;

    public DBEmailInfo() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        m_conn = DriverManager.getConnection(m_url);
    }

    public void saveStockAnchor(String userName, String stockAnchor){
        try {
            Statement stmt = m_conn.createStatement();
            String sql = String.format("update emailinfo set stock_anchor = '%s' where email = '%s'", stockAnchor, userName);
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }

    public void saveLastCollectionTime(String userName, String lastCollectionTime){
        try {
            Statement stmt = m_conn.createStatement();
            String sql = String.format("update emailinfo set last_collect_time = '%s' where email = '%s'", lastCollectionTime, userName);
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }

    public void saveIncrementEnd(String userName, String incrementEnd){
        try {
            Statement stmt = m_conn.createStatement();
            String sql = String.format("update emailinfo set increment_end = '%s' where email = '%s'", incrementEnd, userName);
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }

    public void saveIncrementStart(String userName, String incrementStart){
        try {
            Statement stmt = m_conn.createStatement();
            String sql = String.format("update emailinfo set increment_start = '%s' where email = '%s'", incrementStart, userName);
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }

    public void saveIncrementAnchor(String userName, String incrementAnchor){
        try {
            Statement stmt = m_conn.createStatement();
            String sql = String.format("update emailinfo set increment_anchor = '%s' where email = '%s'", incrementAnchor, userName);
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }

    public void updateVerifyInfo(String userName, String verify, String verifyInfo){
        try {
            Statement stmt = m_conn.createStatement();
            String sql = String.format("update emailinfo set verify = '%s', verify_info = '%s', verify_time ='%s' where email = '%s'", verify, verifyInfo, Utils.getCurrentDateTime(), userName);
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }

    public void clean(){
        try {
            if(m_conn != null){
                m_conn.close();
            }
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }

    public String getDateFormat(){
        return "yyyy-MM-dd HH:mm:ss";
    }
}
