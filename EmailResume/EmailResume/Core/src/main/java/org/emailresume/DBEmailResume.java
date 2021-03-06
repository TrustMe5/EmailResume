package org.emailresume;

import org.emailresume.common.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBEmailResume {

    private static DBEmailResume m_instance = new DBEmailResume();
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DBEmailResume.class);
    private List<String> m_tableNameList = new ArrayList<String>();
    final private String m_datebaseUrl = "jdbc:mysql://192.168.6.80:3306/email_process?"
            + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";

    final private String m_tablePrifix = "email_";
    public static DBEmailResume getInstance() {
        return m_instance;
    }

    public void saveOneRecord(String fileName, String collectTime, String fastdfsTime, String parseTime, String fastdfsUrl, String mongoID, String email, String mongoTime, String sentTime){
        Connection conn = null;
        try {
            String currentTime = getCurrentTime();
            String tableName = m_tablePrifix + currentTime;
            if (!m_tableNameList.contains(tableName)) {
                if (!tableExist(tableName)) {
                    createTable(tableName);
                }
                m_tableNameList.add(tableName);
            }

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(m_datebaseUrl);
            Statement stmt = conn.createStatement();
            String sql = String.format("insert into %s (filename, collect_time, fastdfs_time, parse_time, fastdfs_url, objid, email, server, mongo_time, send_time)" +
                                        "values ('%s','%s','%s','%s','%s','%s','%s', '%s', '%s', '%s')",
                                    tableName, fileName, collectTime, fastdfsTime, parseTime, fastdfsUrl, mongoID, email, Utils.getIP(), mongoTime, sentTime);
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                try {
                    conn.close();
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private boolean tableExist(String tableName) {
        boolean result = false;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(m_datebaseUrl);
            Statement stmt = conn.createStatement();
            String sql = String.format("SELECT table_name FROM information_schema.TABLES WHERE table_name ='%s'", tableName);
            ResultSet rs = stmt.executeQuery(sql);
            result = rs.next();
            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            if(conn != null){
                try {
                    conn.close();
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }
        return result;
    }

    private void createTable(String tableName) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(m_datebaseUrl);
            Statement stmt = conn.createStatement();
            String sql = "create table " + tableName +
                    " (id int primary key auto_increment ," +
                    "filename varchar(255) DEFAULT ''," +
                    "collect_time varchar(20) DEFAULT ''," +
                    "send_time varchar(20) DEFAULT ''," +
                    "fastdfs_time varchar(20) DEFAULT ''," +
                    "fastdfs_url varchar(100) DEFAULT '', " +
                    "parse_time varchar(20) DEFAULT ''," +
                    "mongo_time varchar(20) DEFAULT ''," +
                    "objid varchar(50) DEFAULT ''," +
                    "email varchar(100) DEFAULT ''," +
                    "server varchar(30) DEFAULT '')";
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            if(conn != null){
                try {
                    conn.close();
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(System.currentTimeMillis());
    }

    public String getDateFormat(){
        return "yyyy-MM-dd HH:mm:ss";
    }
}
