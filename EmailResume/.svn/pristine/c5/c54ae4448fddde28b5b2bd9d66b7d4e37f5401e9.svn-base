package org.emailresume;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.io.FileUtils;
import org.emailresume.common.DFSHelper;
import org.emailresume.common.Utils;
import org.emailresume.htmlparser.LongValueProcessor;
import org.emailresume.htmlparser.ParserFactory;
import org.emailresume.htmlparser.ResumeWrapper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.IOException;

public class ParseTask implements Runnable {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ParseTask.class);

    private String getInfoFromFileName(String fileName, String key1, String key2) {
        String value;
        if (key1.equals("") || key2.equals("")) {
            return "";
        }
        if (!key2.equals(".")) {
            String regEx = key1 + "_.*_" + key2 + "_";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(fileName);
            if (m.find()) {
                value = m.group().substring(key1.length() + 1, m.group().length() - key2.length() - 2);
                return value;
            } else {
                return "";
            }
        } else {
            Pattern p = Pattern.compile(key1 + "_.*\\.");
            Matcher m = p.matcher(fileName);
            if (m.find()) {
                value = m.group().substring(key1.length() + 1, m.group().length() - 1);
                return value;
            } else {
                return "";
            }
        }
    }

    public void run() {
        try {
            File emailFolder = new File(Utils.getSaveEmailFolder());
            String[] fileNames = emailFolder.list();
            for (String name : fileNames) {
                String filePath = emailFolder.getPath() + "/" + name;
                //解析名字获取各个字段信息
                String channel = getInfoFromFileName(name, "channel", "email");
                String email = getInfoFromFileName(name, "email", "sentTime");
                String cTime = getInfoFromFileName(name, "time", "category");
                String sTime = getInfoFromFileName(name, "sentTime", "emailID");
                Long sentTime = Long.parseLong(sTime);
                File f = new File(filePath);
                String fastdfsTime = "";
                String fastdfsUrl = "";
                String parseTime = "";
                String mongoTime = "";
                String objid = "";
                try {
                    //上传到fastdfs
                    fastdfsUrl = DFSHelper.uploadFile(f);
                    if (fastdfsUrl.equals("") || fastdfsUrl == null) {
                        //上传失败移动到失败目录
                        logger.error("上传到fastdfs失败 " + name);
                        moveToErrorFolder(f);
                        DBEmailResume.getInstance().saveOneRecord(name, Utils.stampToDate(cTime + "000"),
                                fastdfsTime, parseTime, fastdfsUrl, objid, email, mongoTime, sTime);

                        continue;
                    }
                    fastdfsTime = Utils.getCurrentDateTime();
                    ResumeWrapper wrapper = new ResumeWrapper();
                    logger.info("解析 " + filePath);
                    boolean b = ParserFactory.getParser(Integer.parseInt(channel)).parse(filePath, wrapper);
                    logger.info("解析完成 " + b + " " + filePath);
                    if (b) {
                        //设置额外属性，更新时间，file_path等
                        wrapper.getMeta_info().setFile_path(fastdfsUrl);
                        wrapper.setLast_date(sentTime);
                        wrapper.setVersion_date(sentTime);
                        wrapper.setCreate_time(Utils.getCurrentTimestamp());
                        wrapper.setServer_ip(Utils.getIP());
                        ResumeWrapper.TDeliver_info info = wrapper.getDeliver_info().get(0);
                        info.setDisp_time(sentTime);
                        info.setUpdate_time(sentTime);

                        JsonConfig jsonConfig = new JsonConfig();
                        jsonConfig.registerJsonValueProcessor(Long.class, new LongValueProcessor());
                        JSONObject o = JSONObject.fromObject(wrapper, jsonConfig);
                        String json = org.emailresume.common.Utils.jsonFormatter(o.toString());
                        parseTime = Utils.getCurrentDateTime();
                        objid = MongoHelper.insert(json, "emaildb", "temp");
                        mongoTime = Utils.getCurrentDateTime();
                    } else {
                        //解析失败移动到错误目录
                        logger.error("解析失败 " + name);
                        moveToErrorFolder(f);
                    }
                    if (f.exists())
                        f.delete();
                } catch (Exception e) {
                    logger.error("处理失败 " + name + "  " + Utils.getExceptionString(e));
                    moveToErrorFolder(f);
                }

                DBEmailResume.getInstance().saveOneRecord(name, Utils.stampToDate(cTime + "000"),
                        fastdfsTime, parseTime, fastdfsUrl, objid, email, mongoTime, sTime);
            }
            System.gc();
        } catch (Exception e) {
            logger.error(Utils.getExceptionString(e));
        }

    }

    private void moveToErrorFolder(File f) throws IOException {
        FileUtils.copyFile(f, new File(Utils.getErrorFolder() + "/" + f.getName()));
        f.delete();
    }
}
