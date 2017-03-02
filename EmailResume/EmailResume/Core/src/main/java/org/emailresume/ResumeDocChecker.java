package org.emailresume;

import org.bson.Document;
import org.emailresume.common.*;

public class ResumeDocChecker {
    public static String m_lastError = "";
    public static boolean checkResumeData(Document doc){
        boolean result = true;
        try {
            m_lastError = "";
            Document info = (Document)doc.get("resume_info");
            String phone = info.get("mobilephone").toString().trim();
            if(phone.length() != 0 && !Utils.checkMobileNumber(phone)){
                result = false;
                m_lastError = "手机号不合法";
                System.err.println("手机号不合法");
                return result;
            }

            String email = info.get("email").toString().trim();
            if(email.length() != 0 && !Utils.checkEmail(email)){
                result = false;
                m_lastError = "邮箱不合法";
                System.err.println("邮箱不合法");
                return result;
            }

            String gender = info.get("gender_cnt").toString().trim();
            if(!gender.equals("男") && !gender.equals("女")){
                result = false;
                m_lastError = "性别不合法";
                System.err.println("性别不合法");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return result;
    }
}
