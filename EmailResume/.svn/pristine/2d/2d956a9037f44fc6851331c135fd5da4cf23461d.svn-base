package org.emailresume.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/28 0028.
 */
public class TransferStringToTimeStamp {
    private static long TransStringToTimeStamp(String str) throws ParseException {    //将"201308"或者"20120912"这种类型的字符串转成时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = simpleDateFormat.parse(str);
        return date.getTime() / 1000;
    }

    public  static long transferStringToTimeStamp(String str) throws ParseException{
        String timestring;
        long timestamp;
        str = str.replace(" ","").replace("\u00a0","");
        int length = str.length();
        switch (length)
        {
            case 6:
                timestring = str.substring(0,4) + '0' + str.charAt(5) + "01";
                timestamp = TransStringToTimeStamp(timestring);
                return timestamp;
            case 7:
                if(Character.isDigit(str.charAt(6))) {
                    timestring = str.substring(0, 4) + str.substring(5, 7) + "01";
                    timestamp = TransStringToTimeStamp(timestring);
                }else{
                    timestring = str.substring(0,4) + '0' +str.charAt(5) + "01";
                    timestamp = TransStringToTimeStamp(timestring);
                }
                return timestamp;
            case 8:
                if(Character.isDigit(str.charAt(7))) {
                    timestring = str.substring(0, 4) + '0' + str.charAt(5) + '0' + str.charAt(7);
                    timestamp = TransStringToTimeStamp(timestring);
                }else{
                    timestring = str.substring(0,4) + str.substring(5,7) +"01";
                    timestamp = TransStringToTimeStamp(timestring);
                }
                return timestamp;
            case 9:
                if(Character.isDigit(str.charAt(8))) {
                    if (Character.isDigit(str.charAt(6))) {
                        timestring = str.substring(0, 4) + str.substring(5, 7) + '0' + str.charAt(8);
                        timestamp = TransStringToTimeStamp(timestring);
                    } else {
                        timestring = str.substring(0, 4) + '0' + str.charAt(5) + str.substring(7);
                        timestamp = TransStringToTimeStamp(timestring);
                    }
                }else{
                    if (Character.isDigit(str.charAt(6))) {
                        timestring = str.substring(0, 4) + str.substring(5, 7) + '0' + str.charAt(8);
                        timestamp = TransStringToTimeStamp(timestring);
                    } else {
                        timestring = str.substring(0, 4) + '0' + str.charAt(5) + '0' + str.charAt(7);
                        timestamp = TransStringToTimeStamp(timestring);
                    }
                }
                return timestamp;
            case 10:
                if(Character.isDigit(str.charAt(6))) {
                    if(Character.isDigit(str.charAt(9))){
                        timestring = str.substring(0, 4) + str.substring(5, 7) + str.substring(8);
                        timestamp = TransStringToTimeStamp(timestring);
                    }else {
                        timestring = str.substring(0, 4) + str.substring(5, 7) + '0' + str.charAt(8);
                        timestamp = TransStringToTimeStamp(timestring);
                    }
                }else{
                    timestring = str.substring(0,4) + '0' + str.charAt(5) + str.substring(7,9);
                    timestamp = TransStringToTimeStamp(timestring);
                }
                return timestamp;
            case 11:
                timestring = str.substring(0,4) + str.substring(5,7) + str.substring(8,10);
                timestamp = TransStringToTimeStamp(timestring);
                return timestamp;
            default :
                return 0;
        }
    }
//    public static void main(String[] args) throws ParseException{
//        String str1 = "2012年2月3日";
//        String str2 = "2012年2月03日";
//        String str3 = "2012年02月3日";
//        String str4 = "2012年02月03日";
//        String str5 = "2012年2月11日";
//        String str6 = "2012年02月11日";
//        String str7 = "2012年02月11";
//        String str8 = "2012年2月11";
//        String str9 = "2012年02月11日";
//        String str10 = "2012.2.11";
//        String str11 = "2012.02.11";
//        String str12 = "2012.2.4";
//        String str13 = "2012.2.04";
//        String str14 = "2012.02.4";
//        String str15 = "2012/02.04";
//        String str16 = "至今";
//        String str17 = "2012.2";
//        String str18 = "2012.01";
//        System.out.print(transferStringToTimeStamp(str16));
//    }
}
