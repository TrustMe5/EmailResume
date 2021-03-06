package org.emailresume;

import com.sun.mail.pop3.POP3Folder;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

import com.sun.mail.pop3.POP3Store;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.mail.util.MimeMessageParser;
import org.emailresume.common.*;
import org.emailresume.htmlparser.Parser;
import org.emailresume.htmlparser.ResumeWrapper;

public class EmailCrawling {

    public static void beginWork(String username, String password, String channel, int countForDownload){

        POP3Folder folder = null;
        int currentEmail = 0;
        int count = 0;
        int parseRight = 0;
        POP3Worker worker = new POP3Worker();
        try {
            POP3Store store = null;
            MyLogger.getInstance().writeInfo("获取邮件" + username);
            if(worker.connect(username, password, "")){
                store = worker.getStore();
            }else{
                System.err.println("无法登陆");
                return;
            }

            folder = (POP3Folder) store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            int size = folder.getMessageCount();
            for(int i = size; i > 0; i--){
                currentEmail++;
                try{
                    MimeMessage msg = (MimeMessage) folder.getMessage(i);
                    String msgID = folder.getUID(msg);
                    MimeMessageParser parser = new MimeMessageParser(msg);
                    String subject = parser.getSubject();
                    String from = parser.getFrom();
                    if(subject == null){
                        continue;
                    }
                    StringBuilder channelBuilder = new StringBuilder();
                    if(Utils.isEmailResume(subject, from, channelBuilder)){
                        if(!channel.equals(channelBuilder.toString())){
                            continue;
                        }
                        count++;
                        if(count >= countForDownload && countForDownload != -1){
                            break;
                        }
                        parser.parse();
                        String content = "";
                        String fileName = "";
                        if(parser.hasHtmlContent()){
                            content = parser.getHtmlContent();
                            fileName = "_resume.html";
                        }else if(parser.hasPlainContent()){
                            content = parser.getPlainContent();
                            fileName = "_resume.txt";
                        }

                        org.emailresume.common.Utils.writeFile(content, "G:/resume/" + channel + "_" + msgID + fileName);
//                        ResumeWrapper wrapper = new ResumeWrapper();
//                        wrapper.setVersion_date(timestamp);
//                        boolean result = Parser.parse(content, 1, wrapper);
//                        if(!result){
//                            org.emailresume.common.MyLogger.getInstance().writeWarning("文件 G:/resume2/" + msgID + fileName);
//                            org.emailresume.common.Utils.writeFile(content, "G:/resume2/" + msgID + fileName);
//                        }
//                        else{
//                            parseRight++;
//                            String fastdfsUrl = DFSHelper.uploadFile(content, fileName);
//                            wrapper.getMeta_info().setFile_path(fastdfsUrl);
//                            wrapper.getDeliver_info().get(0).setDisp_time(timestamp);
//                            wrapper.setLast_date(timestamp);
//
//                            JsonConfig jsonConfig = new JsonConfig();
//                            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
//                            JSONObject o = JSONObject.fromObject(wrapper, jsonConfig);
//                            String json = org.emailresume.common.Utils.jsonFormatter(o.toString());
//                            MongoHelper.insert(json);
//                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                System.out.println(currentEmail + "  " + count + "  " + parseRight);
            }

        } catch (Exception e){
            e.fillInStackTrace();
        } finally {
            try {
                if (folder != null) {
                    folder.close(false);
                }
                if(worker != null){
                    worker.clean();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("接收完毕！ 一共 " + count);
    }
}
