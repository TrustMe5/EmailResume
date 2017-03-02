package org.emailresume.controller;

import com.sun.jndi.cosnaming.IiopUrl;
import com.sun.mail.pop3.POP3Folder;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.emailresume.bean.EmailListBean;
import org.emailresume.common.POP3Worker;
import org.emailresume.common.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.spel.ast.BooleanLiteral;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

@RestController
@RequestMapping(value = "/email")
public class EmailController {
    private static final Logger logger = Logger.getLogger(EmailController.class);
    private final String m_url = "jdbc:mysql://192.168.6.80:3306/email_resume?" + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";

    @RequestMapping(value = "/errorList", method = RequestMethod.GET)
    public String getErrorList(@RequestParam(value="channel") String channel){
        JSONObject jsonObject = new JSONObject();
        if(channel.equals("")){
            jsonObject.put("status", "0");
            jsonObject.put("message", "channel参数错误");
            return jsonObject.toString();
        }

        Connection conn = null;
        List<String> emailList = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(m_url);
            Statement stmt = conn.createStatement();

            String sql = String.format("select email from emailinfo where verify != '0' and channel = '%s'", channel);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                emailList.add(rs.getString(1));
            }
            stmt.close();
            jsonObject.put("status", "1");
        }catch (Exception e){
            logger.error(e.toString());
            jsonObject.put("status", "0");
            jsonObject.put("message", "服务器内部错误");
        }finally {
            try {
                if(conn != null)
                    conn.close();
            }catch (Exception e){
                logger.error(e.toString());
            }
        }
        jsonObject.put("count", emailList.size());
        jsonObject.put("email", JSONArray.fromObject(emailList));
        return jsonObject.toString();
    }

    //获取邮件列表（包含邮件正文）
    @RequestMapping(value = "/fetch", method = RequestMethod.POST)
    public String verify(@RequestParam(value="email") String email,
                         @RequestParam(value="password") String password,
                         @RequestParam(value="count") String count,
                         @RequestParam(value="anchor") String anchor,
                         @RequestParam(value="action") String action){

        logger.info(String.format("获取邮件列表 %s,%s,%s,%s,%s", email, password, count, anchor, action));
        EmailListBean bean = new EmailListBean();
        if(!anchor.equals("") && action.equals("")){
            bean.setError_code(2);
            bean.setMessage("有锚点但没有获取动作类型");
            return getJsonString(bean);
        }

        int needCount;
        if(count.equals("") || count.equals("0")){
            needCount = 20;
        }else{
            needCount = Integer.parseInt(count);
        }
        POP3Worker worker = new POP3Worker();
        int currentCount = 0;
        try {
            boolean b = worker.connect(email, password, "");
            if(!b){
                bean.setError_code(1);
            }else {
                POP3Folder folder = worker.getFolder();
                int size = folder.getMessageCount();
                bean.setTotal(size);
                if(size == 0){
                    bean.setIs_end(1);
                }else if(needCount > 0){
                    boolean fill = true;
                    if(action.equals("2")){
                        fill = false;
                    }
                    for(int i = size; i > 0; i--){
                        MimeMessage msg = (MimeMessage) folder.getMessage(i);
                        String msgUID = worker.getFolder().getUID(msg);
                        if(action.equals("1")){//获取比锚点更新的邮件
                            if(!anchor.equals("") && msgUID.equals(anchor)){
                                bean.setIs_end(1);
                                break;
                            }
                        } else if(action.equals("2")){//从锚点开始获取更早的邮件
                            if(!anchor.equals("") && msgUID.equals(anchor)){
                                fill = true;
                                continue;
                            }
                        }

                        if(fill){
                            EmailListBean.Data data = new EmailListBean.Data();
                            data.setEmail_id(msgUID);
                            if(fillData(msg, data)){
                                bean.getData().add(data);
                            }
                            currentCount++;
                            if(currentCount == needCount){
                                break;
                            }
                        }
                        if(i == 1){
                            bean.setIs_end(1);
                        }
                    }
                }

                bean.setCount(currentCount);
            }
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }

        return getJsonString(bean);
    }

    private String getJsonString(EmailListBean bean){
        JsonConfig jsonConfig = new JsonConfig();
        //jsonConfig.registerJsonValueProcessor(Long.class, new LongValueProcessor());
        JSONObject o = JSONObject.fromObject(bean, jsonConfig);
        String json = org.emailresume.common.Utils.jsonFormatter(o.toString());
        return json;
    }

    private int getIndex(String s){
        int i = 0;
        int count = 0;
        while (true){
            char c = s.charAt(i);
            i++;
            if(c == '?'){
                count++;
            }
            if(count == 3){
                break;
            }
        }
        return i;
    }

    private String processStr(String s){
        if(s.contains("?gb2312?")) {
            s = s.replace("?gb2312?", "?gbk?");
        }

        if(!s.contains("\r\n")){
            return s;
        }

        StringBuffer sb = new StringBuffer();
        String[] ss = s.split("\r\n");
        sb.append(ss[0].substring(0, ss[0].length() - 2));
        if(ss.length >= 3){
            for(int i = 1; i < ss.length - 1; i++){
                String tmp = ss[i];
                tmp = tmp.substring(0, tmp.length() - 2);
                tmp = tmp.substring(getIndex(tmp));
                sb.append(tmp);
            }
        }
        String lastStr = ss[ss.length - 1];
        sb.append(lastStr.substring(getIndex(lastStr)));

        return sb.toString();

    }

    private boolean fillData(MimeMessage msg, EmailListBean.Data data) {
        try{
            Long sentTimestamp = msg.getSentDate().getTime() / 1000;
            MimeMessageParser parser = new MimeMessageParser(msg);
            String subject = MimeUtility.decodeText(processStr(msg.getHeader("Subject", null)));
            String from = parser.getFrom();
            if(subject == null){
                return false;
            }

            data.setSend_date(sentTimestamp);
            data.setSubject(subject);
            data.setEmail_from(parser.getFrom());

            List<Address> addresses = parser.getTo();
            for (Address a : addresses){
                InternetAddress add = (InternetAddress)a;
                data.getEmail_to().add(add.getAddress());
            }
            StringBuilder channel = new StringBuilder();
            if(Utils.isEmailResume(subject, from, channel)){
                data.setEmail_type(1);
                data.setChannel(Integer.parseInt(channel.toString()));
            }else{
                data.setEmail_type(2);
            }

            parser.parse();
            String content = "";
            String ext = "";
            if(parser.hasHtmlContent()){
                content = parser.getHtmlContent();
                ext = "html";
            }else if(parser.hasPlainContent()){
                content = parser.getPlainContent();
                ext = "txt";
            }
            data.setContent_ext(ext);
            data.setContent(content);
        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
            return false;
        }

        return true;
    }
}
