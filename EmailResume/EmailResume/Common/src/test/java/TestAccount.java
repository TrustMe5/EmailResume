
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sun.mail.pop3.POP3Folder;
import org.apache.commons.mail.util.MimeMessageParser;
import org.emailresume.common.*;

import javax.mail.Folder;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.PreencodedMimeBodyPart;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TestAccount {
    public static class Account{
        public String userName;
        public String password;
    }

    public static class MyRunnable implements Runnable{
        public void run(){
            try {
                while(!queue.isEmpty()){
                    Account a = queue.take();
                    test(a.userName, a.password);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static BlockingQueue<Account> queue = new LinkedBlockingQueue<Account>();

    public static void main(String [] args) {
        try {
            POP3Worker w = new POP3Worker();
            boolean b = w.connect("uuc@fenjianli.com", "sdhluuc123","");
            System.out.println(b);
            if(!b)
                System.out.println(w.getErrorMessage());
            else{
                POP3Folder folder = w.getFolder();
                int size = folder.getMessageCount();
                System.out.println(size);
                b = w.verify();
                if(!b){
                    System.out.println("验证失败");
                }
            }



//            String url = "jdbc:mysql://192.168.6.65:3306/emailresume?"
//                    + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection(url);
//            Statement stmt = conn.createStatement();
//            String sql = String.format("select email, password from emailstate where login = '-1'");
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()){
//                String userName = rs.getString(1);
//                String password = rs.getString(2);
//                if(userName.endsWith("qq.com")){
//                    Account a = new Account();
//                    a.userName = userName;
//                    a.password = password;
//                    queue.add(a);
//                }
//                //test(userName, password);
//
//            }
//
//            stmt.close();
//            conn.close();
//
//            for( int i = 0; i< 10; i++){
//                Thread t = new Thread(new MyRunnable());
//                t.start();
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void test(String userName, String password) throws Exception{
        System.out.println(String.format("%s", userName));
        POP3Worker worker = new POP3Worker();
        boolean b = worker.connect(userName, password, "");
        if(!b){
            save(userName, "0", worker.getExceptionMessage());
        }
//        POP3Folder folder = worker.getFolder();
//        int size = folder.getMessageCount();
//        int count = 0;
//        for(int i = size; i > 0; i--){
//            try{
//                MimeMessage msg = (MimeMessage) folder.getMessage(i);
//                MimeMessageParser parser = new MimeMessageParser(msg);
//                String subject = parser.getSubject();
//                String from = parser.getFrom();
//                if(Utils.isEmailResume(subject, from, new StringBuilder())){
//                    count++;
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            System.out.println(String.format("%s %s", i,count));
//        }
//
//        System.out.println(String.format(" %s %s %s", userName, size, count));
//        saveCount(userName, count, size);
        worker.clean();
    }

    public static void saveCount(String userName, int count, int emailCount){
        Connection conn = null;
        try {
            String url = "jdbc:mysql://192.168.6.65:3306/emailresume?"
                    + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = String.format("update emailstate set resume_count = %s, email_count = %s where email = '%s'", count, emailCount, userName);
            stmt.executeUpdate(sql);
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
    }

    public static void save(String userName, String login, String info){
        Connection conn = null;
        try {
            String url = "jdbc:mysql://192.168.6.65:3306/emailresume?"
                    + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = String.format("update emailstate set login = '%s', remark = '%s' where email = '%s'", login, info, userName);
            stmt.executeUpdate(sql);
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
    }
}
