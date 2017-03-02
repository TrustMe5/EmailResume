package org.emailresume.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;

public class Utils {

    private static String m_localIP = "";
    public static void writeFile(String content, String fileName){
        try{
            File file =new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fileName,true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(content);
            bufferWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String readFileByBytes(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String s = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                s += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return s;
    }
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    public static boolean checkMobileNumber(String mobileNumber){
        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
        Matcher m = p.matcher(mobileNumber);
        return m.matches();
    }

    public static String encodeBase64(byte[]input){
        return new sun.misc.BASE64Encoder().encode(input);
    }

    public static String jsonFormatter(String uglyJSONString){
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }

    public static String getCurrentDateTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String getCurrentDateTime2(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return df.format(new Date());
    }

    public static String formatTime(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Long getCurrentTimestamp(){
        return System.currentTimeMillis() / 1000;
    }

    public static boolean isZhiLianSyncEmail(String subject, String from){
        return from.startsWith("b") && from.endsWith(".service@zhaopinmail.com") && subject.startsWith("(Zhaopin.com) 应聘");
    }

    public static boolean is51SyncEmail(String subject, String from){
        return from.equals("resume@quickmail.51job.com") && subject.startsWith("(51job.com)申请贵公司");
    }

    public static boolean isLiePinSyncEmail(String subject, String from){
        return from.startsWith("service@mail") && from.endsWith(".lietou-edm.com") && subject.endsWith("来自猎聘网的候选人");
    }

    //判断是否是简历邮件并返回渠道ID标识
    public static boolean isEmailResume(String subject, String from){
        if(Utils.isZhiLianSyncEmail(subject, from)){
            return true;
        }else if(Utils.is51SyncEmail(subject, from)){
            return true;
        }else if(Utils.isLiePinSyncEmail(subject, from)){
            return true;
        }

        return false;
    }

    //判断是否是简历邮件并返回渠道ID标识
    public static boolean isEmailResume(String subject, String from, StringBuilder channel){
        if(Utils.isZhiLianSyncEmail(subject, from)){
            channel.append("1");
            return true;
        }else if(Utils.is51SyncEmail(subject, from)){
            channel.append("2");
            return true;
        }else if(Utils.isLiePinSyncEmail(subject, from)){
            channel.append("3");
            return true;
        }

        return false;
    }

    public static String getExceptionString(Exception e){
        String sOut = "";
        sOut += "\tat " + e.toString() + "\r\n";
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        return sOut;
    }

    public static String getIP() throws SocketException{
       if(m_localIP.equals("")){
           Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
           for (NetworkInterface netint : Collections.list(nets)){
               Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
               for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                   if(inetAddress.getHostAddress().indexOf(":")==-1 && !inetAddress.isLoopbackAddress()){
                       m_localIP = inetAddress.getHostAddress();
                   }
               }
           }
       }
        return m_localIP;
    }

    public static String getSaveEmailFolder(){
        return "/email/file/collection";
    }

    public static String getErrorFolder() {
        return "/email/file/error";
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(s)));
        return sd;
    }
}
