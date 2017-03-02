package org.emailresume.collector;

import com.sun.mail.pop3.POP3Folder;
import javax.mail.internet.MimeMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.log4j.Logger;
import org.emailresume.common.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Worker {

    private static final Logger logger = Logger.getLogger(Worker.class);

    private String m_userName;

    private DBEmailInfo m_dbCollectionInfo = null;
    private POP3Worker m_POP3Worker = null;
    public Worker() throws Exception{
            m_dbCollectionInfo = new DBEmailInfo();
            m_POP3Worker = new POP3Worker();
    }

    public boolean connectMail(String userName, String password, String host){
        m_userName = userName;

        if(!m_POP3Worker.connect(userName, password, host)){
            logger.error("无法登陆");
            m_dbCollectionInfo.updateVerifyInfo(userName, m_POP3Worker.getErrorCode(), m_POP3Worker.getErrorMessage());
            return false;
        }
        if(!m_POP3Worker.verify()){
            m_dbCollectionInfo.updateVerifyInfo(userName, m_POP3Worker.getErrorCode(), m_POP3Worker.getErrorMessage());
            return false;
        }

        m_dbCollectionInfo.updateVerifyInfo(userName, m_POP3Worker.getErrorCode(), m_POP3Worker.getErrorMessage());
        return true;
    }

    public void beginWork(String stockAnchor, String incrementStart, String incrementAnchor, String incrementEnd){
        beginWorkStock(stockAnchor);
        beginWorkIncrement(incrementStart, incrementAnchor, incrementEnd);
    }

    private void beginWorkIncrement(String start, String anchor, String end){
        if(end.equals("")){
            logger.info("不需要进行增量采集");
            return;
        }
        String collectionTime = Utils.getCurrentDateTime();
        m_dbCollectionInfo.saveLastCollectionTime(m_userName, collectionTime);
        try{
            POP3Folder folder = m_POP3Worker.getFolder();
            int size = folder.getMessageCount();
            logger.info("打开收件箱 " + size);

            if(start.equals("")){
                //第一次增量采集,记录增量开始时间
                logger.info("进行增量采集");
                MimeMessage msg = (MimeMessage) folder.getMessage(size);
                String incrementStart = Utils.formatTime(msg.getSentDate(), m_dbCollectionInfo.getDateFormat());
                m_dbCollectionInfo.saveIncrementStart(m_userName, incrementStart);

                //从最近一封邮件开始遍历
                for(int i = size; i > 0; i--){
                    msg = (MimeMessage) folder.getMessage(i);
                    Long sentTimestamp = msg.getSentDate().getTime() / 1000;
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date endDate = df.parse(end);
                    if(sentTimestamp <= endDate.getTime() / 1000){
                        logger.info(String.format("到达上次增量终点 %s  %s", end, df.format(msg.getSentDate())));
                        break;
                    }

                    saveEmailToLocal(msg, "2");
                    m_dbCollectionInfo.saveIncrementAnchor(m_userName, df.format(msg.getSentDate()));
                }

                //增量扫描完毕
                m_dbCollectionInfo.saveIncrementAnchor(m_userName, "");
                m_dbCollectionInfo.saveIncrementEnd(m_userName, incrementStart);
                m_dbCollectionInfo.saveIncrementStart(m_userName, "");
                return;
            }

            if(!anchor.equals("")){
                //先从锚点开始采集到上次记录的终点，并且把上次的起点置为终点
                logger.info(String.format("从锚点开始增量采集 %s  %s", anchor, end));
                for(int i = size; i > 0; i--) {
                    MimeMessage msg = (MimeMessage) folder.getMessage(i);
                    Long sentTimestamp = msg.getSentDate().getTime() / 1000;
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date anchorDate = df.parse(anchor);
                    if (sentTimestamp > anchorDate.getTime() / 1000) {
                        logger.info(String.format("没有到锚点 %s  %s  %s", anchor, df.format(msg.getSentDate()), msg.getSubject()));
                        continue;
                    }
                    Date endDate = df.parse(end);
                    if (sentTimestamp <= endDate.getTime() / 1000) {
                        logger.info(String.format("到达上次增量终点 %s  %s  %s", endDate, df.format(msg.getSentDate()), msg.getSubject()));
                        break;
                    }
                    saveEmailToLocal(msg, "2");
                    m_dbCollectionInfo.saveIncrementAnchor(m_userName, df.format(msg.getSentDate()));

                }

                //增量锚点扫描完毕
                m_dbCollectionInfo.saveIncrementAnchor(m_userName, "");
                m_dbCollectionInfo.saveIncrementEnd(m_userName, start);
                m_dbCollectionInfo.saveIncrementStart(m_userName, "");
            }

            beginWorkIncrement("", "", start);

        }catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }

    }

    private void beginWorkStock(String anchor){
        if(anchor.equals("-1")){
            logger.info("不需要存量采集");
            return;
        }
        String collectionTime = Utils.getCurrentDateTime();
        m_dbCollectionInfo.saveLastCollectionTime(m_userName, collectionTime);
        try {
            POP3Folder folder = m_POP3Worker.getFolder();
            int size = folder.getMessageCount();
            logger.info("打开收件箱 " + size);

            if(anchor.equals("")){
                //如果锚点是空，说明是第一次扫描该邮箱,记录增量终点即第一封信时间
                MimeMessage msg = (MimeMessage) folder.getMessage(size);
                String incrementEnd = Utils.formatTime(msg.getSentDate(), m_dbCollectionInfo.getDateFormat());
                m_dbCollectionInfo.saveIncrementEnd(m_userName, incrementEnd);
            }
            //从最近一封邮件开始遍历
            for(int i = size; i > 0; i--){
                try{
                    MimeMessage msg = (MimeMessage) folder.getMessage(i);
                    Long sentTimestamp = msg.getSentDate().getTime() / 1000;
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(!anchor.equals("")){
                        Date anchorDate = df.parse(anchor);
                        if(sentTimestamp > anchorDate.getTime() / 1000){
                            logger.info(String.format("没有到锚点 %s  %s", anchor, df.format(msg.getSentDate())));
                            continue;
                        }
                    }
                    saveEmailToLocal(msg, "1");
                    m_dbCollectionInfo.saveStockAnchor(m_userName, df.format(msg.getSentDate()));

                }catch (Exception e){
                    logger.error(Utils.getExceptionString(e));
                }

            }

            //存量扫描完毕
            m_dbCollectionInfo.saveStockAnchor(m_userName, "-1");

        } catch (Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }

    private void saveEmailToLocal(MimeMessage msg, String category) throws Exception {
        Long sentTimestamp = msg.getSentDate().getTime() / 1000;
        String msgUID = m_POP3Worker.getFolder().getUID(msg);
        MimeMessageParser parser = new MimeMessageParser(msg);
        String subject = parser.getSubject();
        String from = parser.getFrom();
        if(subject == null){
            return;
        }
        StringBuilder channel = new StringBuilder();
        //如果是邮件简历保存邮件
        if(Utils.isEmailResume(subject, from, channel)){
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
            File targetFile = new File(getSaveFolder() + "/" + getTargetFileName(category, msgUID, sentTimestamp.toString(), channel.toString(), ext));
            DBEmailCollector.getInstance().saveOneRecord(targetFile.getName(), Utils.getCurrentDateTime(), m_userName);
            FileUtils.writeByteArrayToFile(targetFile, content.getBytes("UTF-8"));
            logger.info(String.format("采集邮件 %s, 文件名 %s", subject, targetFile.getName()));

//                        String target = "";
//                        try{
//                            target = DFSHelper.uploadFile(targetFile);
//                        }catch (Exception e){
//                            logger.error("上传到fastDFS出错 " + targetFile.getAbsolutePath());
//                            target = getErrorFolder() + "/" + targetFile.getName();
//                            FileUtils.copyFile(targetFile, new File(target));
//                        }
        }else{
            //logger.warn(String.format("不是简历 %s  %s ", subject, from));
        }
    }

    public void clean(){
        if (m_POP3Worker != null) {
            m_POP3Worker.clean();
        }
        if(m_dbCollectionInfo != null){
            m_dbCollectionInfo.clean();
        }
    }

    private String getErrorFolder(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String folder = Utils.getErrorFolder() + "/" + df.format(new Date());
        return folder;
    }

    private String getSaveFolder(){
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //String folder = Utils.getSaveEmailFolder() + "/" + df.format(new Date());
        //return folder;
        return Utils.getSaveEmailFolder();
    }

    private String getTargetFileName(String category, String emailID, String sendTime, String channel, String ext){
        //名字规则 采集时间_采集类型（增量还是存量）_渠道_来源邮箱_邮件发送时间戳_邮件id.文件后缀
        //采集类型 1存量  2增量
        return String.format("time_%s_category_%s_channel_%s_email_%s_sentTime_%s_emailID_%s.%s",Utils.getCurrentTimestamp().toString() , category, channel, m_userName
                            ,sendTime, emailID, ext);
    }

}
