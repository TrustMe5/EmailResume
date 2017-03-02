package org.emailresume.htmlparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class InsertDataToDB {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InsertDataToDB.class);

    public static void insertDataToDB(String cur_time, String serverIP, String filePath, String sid) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.6.65/emailresume", "root", "root");
            Statement stmt = conn.createStatement();
            String sql = String.format("insert into parse_failure_info(time, server_ip, file_path, sid) values ('%s', '%s', '%s', '%s')", cur_time, serverIP, filePath, sid);
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            if(conn != null){
                try {
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
