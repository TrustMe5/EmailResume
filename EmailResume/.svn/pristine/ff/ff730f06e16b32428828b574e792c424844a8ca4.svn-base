package org.emailresume;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBEmail {
    private static DBEmail m_instance = new DBEmail();

    public static DBEmail getInstance() {
        return m_instance;
    }

    public void saveOneRecord(String email, int resumeCount, int emailCount){
        try {
            String url = "jdbc:mysql://192.168.6.65:3306/emailresume?"
                    + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = String.format("select count(*) from email where account = '%s'", email);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                sql = String.format("update email set count = %i, resume_count = %i where account = '%s'", emailCount, resumeCount, email);

            }else{
                sql = String.format("insert into email (account, count, resume_count) values ('%s', %i, %i)", email, emailCount, resumeCount);
            }

            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
