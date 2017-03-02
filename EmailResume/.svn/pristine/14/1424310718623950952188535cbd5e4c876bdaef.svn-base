package org.emailresume.common;

import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Message;
import com.sun.mail.pop3.POP3Store;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class POP3Worker {
    public boolean m_qiyeLoginResult = false;

    class LoginQiYeThread extends Thread{

        private List<String> m_hosts;
        private String m_username;
        private String m_password;
        LoginQiYeThread(List<String> hosts, String username, String password){
            this.m_hosts = hosts;
            this.m_username = username;
            this.m_password = password;
        }
        @Override
        public void run(){
            for(String host : m_hosts){
                try{
                    synchronized (this){
                        if(m_qiyeLoginResult){
                            break;
                        }
                    }
                    Properties props = new Properties();
                    props.put("mail.pop3.ssl.enable", false);
                    props.put("mail.pop3.host", host);
                    props.put("mail.pop3.port", 110);
                    props.put("mail.pop3.connectiontimeout", 10000);//10秒超时

                    Session session = Session.getInstance(props);
                    POP3Store store = (POP3Store) session.getStore("pop3");
                    store.connect(m_username, m_password);
                    synchronized (this){
                        Connection conn = null;
                        String url = "jdbc:mysql://192.168.6.80:3306/email_resume?" + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(url);
                            Statement stmt = conn.createStatement();
                            String sql = String.format("REPLACE into host (host, server) values('%s', '%s')", m_username.split("@")[1], host);
                            stmt.executeQuery(sql);
                            stmt.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            try{
                                conn.close();
                            }catch (Exception e){

                            }
                        }
                        m_qiyeLoginResult = true;
                    }
                    m_store = store;
                    break;
                }catch (Exception ex){
                    //ex.printStackTrace();
                }
            }
        }
    }

    class CustomMessageException extends Exception{
        CustomMessageException(String message){
            super(message);
        }
    }

    private POP3Store m_store = null;
    private POP3Folder m_folder = null;
    final private static int m_minEmailCount = 20;

    private String m_errorMessage;
    private String m_exceptionMessage;
    private String m_errorCode;

    public String getErrorCode() {
        return m_errorCode;
    }

    public String getExceptionMessage() {
        try {
            m_exceptionMessage= new String(m_exceptionMessage.getBytes("iso8859_1"), "GBK");
        }catch (Exception e1){

        }
        return m_exceptionMessage;
    }

    public String getErrorMessage(){
        return m_errorMessage;
    }

    public POP3Store getStore(){
        return m_store;
    }

    public POP3Folder getFolder() throws MessagingException{
        if(m_folder == null){
            m_folder = (POP3Folder) m_store.getFolder("INBOX");
            m_folder.open(Folder.READ_ONLY);
        }
        return m_folder;
    }

    private String getHostFromEmail(String email){
        String suffix = email.split("@")[1];
        if(suffix.equals("qq.com")){
            return "pop.qq.com";
        }

        if(suffix.equals("sina.com") || suffix.equals("sina.cn") || suffix.equals("vip.sina.com") ||
                suffix.equals("sohu.com") || suffix.equals("live.com") || suffix.equals("263.net")){
            return "pop3." + suffix;
        }

        if(suffix.equals("aliyun.com")){
            return "pop3.mail.aliyun.com";
        }
        if(suffix.equals("hotmail.com")){
            return "pop3.live.com";
        }
        if(suffix.contains("ee-post")){
            return "pop.ee-post.com";
        }

        String server = "pop." + suffix;
        Connection conn = null;
        String url = "jdbc:mysql://192.168.6.80:3306/email_resume?" + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = "select server from host where host = '" + suffix + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                server = rs.getString(1);
                break;
            }
            stmt.close();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
            }catch (Exception e){

            }
        }

        return server;
    }

    public boolean connect(String username, String password, String host){
        boolean result = false;
        try {
            String protocol = "pop3";

            String finalHost = host;
            if(host.equals("")){
                finalHost = getHostFromEmail(username);
            }

            Properties props = new Properties();
            props.put("mail.pop3.ssl.enable", false);
            props.put("mail.pop3.host", finalHost);
            props.put("mail.pop3.port", 110);
            props.put("mail.pop3.connectiontimeout", 10000);//10秒超时

            if(username.endsWith("qq.com") || username.endsWith("hotmail.com")){
                props.put("mail.pop3.ssl.enable", true);
                props.put("mail.pop3.port", 995);
            }

            Session session = Session.getInstance(props);
            //session.setDebug(true);
            m_store = (POP3Store) session.getStore(protocol);
            m_store.connect(username, password);
            result = true;
        } catch (AuthenticationFailedException e){
            m_exceptionMessage = e.getMessage();
            if(m_exceptionMessage.endsWith("authorization failed")){
                m_errorMessage = "账号或密码错误，或者登录网页版邮箱开启POP3协议";
                m_errorCode = "03";
            }else{
                m_errorMessage = "账号或密码错误";
                m_errorCode = "01";
            }
        } catch (MessagingException e){
            m_exceptionMessage = e.getMessage();
            Exception nextException = e.getNextException();
            if(nextException instanceof UnknownHostException){
                m_errorMessage = "无法识别邮箱服务器";
                m_errorCode = "05";
            }else if(nextException instanceof ConnectException || nextException instanceof SocketTimeoutException){
                m_errorMessage = "连接对方的服务器失败，建议登录其他邮箱";
                m_errorCode = "02";

            }else{
                m_errorMessage = "账号或密码错误，或者需要登录网页版邮箱开启POP3协议";
                m_errorCode = "03";
            }
        } catch (Exception e){
            m_exceptionMessage = e.getMessage();
            m_errorMessage = "账号或密码错误，或者需要登录网页版邮箱开启POP3协议";
            m_errorCode = "03";
        }

        if(!result && (m_errorCode.equals("05") || m_errorCode.equals("02") || m_errorCode.equals("01"))){
            if(host.equals("")){
                ArrayList<String> list = new ArrayList<String>();
                //阿里企业邮
                list.add("pop.alibaba.com");
                //腾讯企业邮
                list.add("pop.exmail.qq.com");
                //网易企业邮
                list.add("pop.qiye.163.com");

                ArrayList<String> list2 = new ArrayList<String>();
                //exchange
                //list.add("pop.euchost.com");
                //21CN企业邮箱
                list2.add("pop-ent.21cn.com");
                //sina
                list2.add("pop3.sina.net");
                //263
                list2.add("pop.263.net");

                ArrayList<String> list3 = new ArrayList<String>();
                //139
                list3.add("pop.mail.139.com");
                //时代互联
                list3.add("mx650.now.net.cn");
                //晶格
                list3.add("pop.ee-post.com");

                ArrayList<String> list4 = new ArrayList<String>();
                //搜狐企业邮箱
                list.add("mail.sohu.net");
                //269
                list4.add("pop3.net269.cn");
                //亿邮
                list4.add("mail.hitsz.edu.cn");

                //如果没有传入host，服务器无法识别，尝试使用企业邮箱服务器登陆
                try {
                    LoginQiYeThread t1 = new LoginQiYeThread(list, username, password);
                    LoginQiYeThread t2 = new LoginQiYeThread(list2, username, password);
                    LoginQiYeThread t3 = new LoginQiYeThread(list3, username, password);
                    LoginQiYeThread t4 = new LoginQiYeThread(list4, username, password);
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    if(t1.isAlive())
                        t1.join();
                    if(t2.isAlive())
                        t2.join();
                    if(t3.isAlive())
                        t3.join();
                    if(t4.isAlive())
                        t4.join();
                    result = m_qiyeLoginResult;
                }catch (Exception e){

                }

            }
        }

        return result;
    }

    //查看7天内的邮件是否有20封是简历
    public boolean verify(){
        boolean result = false;
        m_errorMessage = "验证失败";
        m_errorCode = "04";

        try {
            if(m_store == null){
                throw new CustomMessageException("验证邮箱失败，获取Store失败");
            }
            POP3Folder folder = getFolder();
            if(folder == null){
                throw new CustomMessageException("验证邮箱失败，获取Inbox Folder失败");
            }
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Long time = format.parse(format.format(cal.getTime())).getTime() / 1000;
            int count = 0;
            int size = folder.getMessageCount();
            for(int i = size; i > 0; i--){
                MimeMessage msg = (MimeMessage) folder.getMessage(i);
                Long sendTime = msg.getSentDate().getTime() / 1000;
                if(sendTime < time){
                    break;
                }
                MimeMessageParser parser = new MimeMessageParser(msg);
                String subject = parser.getSubject();
                String from = parser.getFrom();
                if(Utils.isEmailResume(subject, from)){
                    count++;
                    if(count >= m_minEmailCount){
                        m_errorMessage = "验证成功";
                        m_errorCode = "1";
                        result = true;
                        break;
                    }
                }
            }
        }catch (CustomMessageException e){
            m_errorMessage = e.getMessage();
            m_exceptionMessage = e.getMessage();
            m_errorCode = "04";
        } catch (Exception e){
            m_errorMessage = "验证失败";
            m_exceptionMessage = e.getMessage();
            m_errorCode = "04";
        }

        return result;
    }

    public void clean(){
        try {
            if(m_store != null)
                m_store.close();
            if(m_folder != null)
                m_store.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
