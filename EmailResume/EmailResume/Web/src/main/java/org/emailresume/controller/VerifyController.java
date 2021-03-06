package org.emailresume.controller;

import org.apache.log4j.Logger;
import org.emailresume.common.POP3Worker;
import org.emailresume.common.Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
public class VerifyController {
    private static final Logger logger = Logger.getLogger(VerifyController.class);
    private final String m_url = "jdbc:mysql://192.168.6.80:3306/email_resume?" + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";


    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String verify(@RequestParam(value="email") String email,
                         @RequestParam(value="password") String password,
                         @RequestParam(value="channel") String channel,
                         @RequestParam(value="host") String host) {


        logger.info(String.format("验证 %s, %s, %s, %s", email, password, channel, host));
        if(email.equals("")){
            return getResultJson(email, password, channel, host, "0", "邮箱不能为空");
        }

        if(password.equals("")){
            return getResultJson(email, password, channel, host, "0", "密码不能为空");
        }

        if(channel.equals("")){
            return getResultJson(email, password, channel, host, "0", "渠道不能为空");
        }

        if(!host.isEmpty() && !host.startsWith("pop")){
            return getResultJson(email, password, channel, host, "0", "host参数错误");
        }

        String resultJson;
        POP3Worker worker = new POP3Worker();
        boolean b = worker.connect(email, password, host);
        if(!b){
            resultJson = getResultJson(email, password, channel, host, worker.getErrorCode(), worker.getErrorMessage());
        }else {
            if(!channel.equals("UUtuijian") && !worker.verify()){
                resultJson = getResultJson(email, password, channel, host, worker.getErrorCode(), worker.getErrorMessage());
            }
            else{
                resultJson = getResultJson(email, password, channel, host, "1", "验证成功");
            }
        }
        worker.clean();
        return resultJson;
    }

    private String getResultJson(String email, String password, String channel, String host, String status, String msg){
        logger.info(String.format("返回 %s, %s", status, msg));
        if(!email.equals("")){
            //将数据保存到数据库
            Connection conn = null;
            Connection connLog = null;
            try {
                String currentTime = Utils.getCurrentDateTime();
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(m_url);
                connLog = DriverManager.getConnection(m_url);
                Statement stmt = conn.createStatement();

                String sql = String.format("select email from emailinfo where email = '%s'", email, status);
                ResultSet rs = stmt.executeQuery(sql);
                sql = "";
                //如果验证通过并且有该条数据，就更新，否则插入新数据
                if(rs.next()){
                    if(status.equals("1")){
                        sql = String.format("update emailinfo set password = '%s', verify = '%s', verify_info = '%s', verify_time ='%s', channel = '%s', host = '%s' where email = '%s'",
                                password, status, msg, currentTime, channel, host, email);
                    }
                }else{
                    sql = String.format("insert into emailinfo (email, password, verify, verify_info, verify_time, channel, create_time, host) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                            email, password, status, msg, currentTime, channel, currentTime, host);
                }
                if(!sql.equals("")){
                    stmt.executeUpdate(sql);
                }
                stmt.close();

                stmt = connLog.createStatement();
                sql = String.format("insert into verifylog(email, password, verify, verify_info, verify_time, channel) values ('%s', '%s', '%s', '%s', '%s', '%s')", email, password, status, msg, currentTime, channel);
                stmt.executeUpdate(sql);
                stmt.close();
            }catch (Exception e){
                logger.error(e.toString());
            }finally {
                try {
                    if(conn != null)
                        conn.close();
                    if(connLog != null)
                        connLog.close();
                }catch (Exception e){
                    logger.error(e.toString());
                }
            }
        }

        return String.format("{\"status\" : \"%s\", \"message\" : \"%s\"}", status, msg);
    }
}
