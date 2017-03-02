package org.emailresume.common;

import javax.mail.Message;
import java.sql.*;
import javax.mail.internet.InternetAddress;
import  javax.mail.*;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.text.SimpleDateFormat;
import org.emailresume.common.*;

public class EmailInfo {
    public String m_host;
    public int m_port = 995;
    public String m_popServer = "";
    public boolean m_ssl = true;

    public String m_username;
    public String m_password;

    public String m_subject;
    public String m_sendDate;
    public String m_from;
    public StringBuffer m_bodyText = null;
    public boolean m_isContainAttach = false;
    public String m_suffix = "";

    public void getServerInfo(String host){
        m_host = host;
        m_popServer = "pop." + m_host;
        Connection conn = null;
        String sql;
        String url = "jdbc:mysql://192.168.6.65:3306/emailresume?"
                + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            sql = "select * from host where host = '" + host + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                m_host = rs.getString(1);
                m_popServer = rs.getString(2);
                m_port = rs.getInt(3);
                m_ssl = rs.getBoolean(4);
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

    }

    public void parseMessage(Message msg)throws Exception{
        m_subject =  MimeUtility.decodeText(msg.getSubject());

        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        m_from = from;

        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
        m_sendDate = sFormat.format(msg.getSentDate());

        //m_isContainAttach = isContainAttach((Part)msg);

    }

    public void exportEmail(Message msg, String fileName) throws Exception{
        m_bodyText = new StringBuffer();

        getMailContent(msg);

        File saveFolder = new File("G:/" + m_username);
        if(!saveFolder.exists()){
            saveFolder.mkdirs();
        }
        Utils.writeFile(m_bodyText.toString(), saveFolder.getPath() + "/" + fileName + m_suffix);
        m_bodyText = null;
    }

    public void getMailContent(Part part) throws Exception {
        String contenttype = part.getContentType();
        int nameindex = contenttype.indexOf("name");
        boolean conname = false;
        if (nameindex != -1)
            conname = true;

        if (part.isMimeType("text/plain") && !conname) {
            m_bodyText.append((String) part.getContent());
            m_suffix = ".txt";
        } else if (part.isMimeType("text/html") && !conname) {
            m_bodyText.append((String) part.getContent());
            m_suffix = ".html";
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        }
    }

    public boolean isContainAttach(Part part) throws Exception {
        boolean attachflag = false;
        String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE))))
                    attachflag = true;
                else if (mpart.isMimeType("multipart/*")) {
                    attachflag = isContainAttach(mpart);
                } else {
                    String contype = mpart.getContentType();
                    if (contype.toLowerCase().indexOf("application") != -1)
                        attachflag = true;
                    if (contype.toLowerCase().indexOf("name") != -1)
                        attachflag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachflag = isContainAttach((Part) part.getContent());
        }
        return attachflag;
    }

}
