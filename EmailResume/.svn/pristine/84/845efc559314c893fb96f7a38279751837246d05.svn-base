package org.emailresume.htmlparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.emailresume.common.TransferStringToTimeStamp;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QianChengParser implements Parser {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(QianChengParser.class);
    static ResumeWrapper rWrapper = null;
    static String fst_job_date_cnt = "";
    static long fst_job_date = 0;
    static String lst_job_date_cnt = "";
    static long lst_job_date = 0;

    private static String ReadResumeContent(String filePath) throws IOException {
        String encoding = "utf-8";
        File file = new File(filePath);
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            StringBuffer sb = new StringBuffer();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt);
            }
            read.close();
            return sb.toString();
        }
        return "";
    }

    private static String GetNumFromString(String str) {
        str=str.trim();
        String str2="";
        if(str != null && !"".equals(str)){
            for(int i=0;i<str.length();i++){
                if(str.charAt(i)>=48 && str.charAt(i)<=57){
                    str2+=str.charAt(i);
                }
            }
        }
        return str2;
    }

    private static String GetGenderFromString(String str) { // 从字符串中抽出性别
        String gender;
        Pattern p = Pattern.compile("\\|.*\\|");
        Matcher m = p.matcher(str.replace("\u00a0",""));
        if (m.find()) {
            gender = m.group().substring(1, 2);
        } else {
            gender = "";
        }
        return gender;
    }

    private static String RemoveChineseWordFromString(String str) {
        String regEx = "[^\u4e00-\u9fa5]+";
        Pattern pat = Pattern.compile(regEx);
        Matcher m = pat.matcher(str);
        if (m.find()) {
            return m.group();
        } else {
            return "至今";
        }
    }

    private static String GetBirthdayFromString(String str) { // 从字符串中抽出生日
        String birthday;
        Pattern p = Pattern.compile("\\(.*\\)");
        Matcher m = p.matcher(str);
        if (m.find()) {
            birthday = m.group().substring(1, m.group().length() - 1);
            if (birthday.charAt(6) == '月') {
                birthday = birthday.substring(0, 5) + '0' + birthday.substring(5);
            }
            if (birthday.charAt(9) == '日') {
                birthday = birthday.substring(0, 8) + '0' + birthday.substring(8);
            }
        } else {
            birthday = "";
        }
        return birthday;
    }

    private static List GetWorkInfoFromString(String str) { // 抽取出一份工作经历的一些信息，包括入职时间、离职时间、公司名字、公司性质等
        String stime;
        String etime = "";
        String company = "";
        String company_type = "";
        Pattern p = Pattern.compile(".*--");
        Matcher m = p.matcher(str);
        if (m.find()) {
            stime = m.group().substring(0, m.group().length() - 2);
        } else {
            stime = "";
        }
        Pattern p1 = Pattern.compile("--.*：");
        Matcher m1 = p1.matcher(str);
        if (m1.find()) {
            etime = m1.group().substring(2, m1.group().length() - 1);
        }
        Pattern p2 = Pattern.compile("：.*\\(");
        Matcher m2 = p2.matcher(str);
        if (m2.find()) {
            company = m2.group().substring(1, m2.group().length() - 1);
        }
        Pattern p3 = Pattern.compile("\\(.*\\)");
        Matcher m3 = p3.matcher(str);
        if (m3.find()) {
            company_type = m3.group().substring(1, m3.group().length() - 1);
        }
        List<String> list = new ArrayList<String>();
        list.add(stime);
        list.add(etime);
        list.add(company);
        list.add(company_type);
        return list;
    }

    private static long TransStringToTimeStamp(String str) throws ParseException {    //将"201308"或者"20120912"这种类型的字符串转成时间戳
        if (str.length() > 0) {
            if (str.length() == 6) {
                str = str + "01";
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date date = format.parse(str);
            return date.getTime() / 1000;
        } else {
            return 0;
        }
    }
    private static Map getLableContent(String lable,String content){
        Map<String,String> map = new HashMap<String, String>();
        if (lable.equals("开发工具：")) {
            map.put("dev_tools",content);
        } else if (lable.equals("项目描述：")) {
            map.put("description",content);
        } else if (lable.equals("责任描述：")) {
            map.put("duty",content);
        } else if (lable.equals("软件环境：")) {
            map.put("env_software",content);
        }else if (lable.equals("硬件环境：")) {
            map.put("env_hardware",content);
        }
        return map;
    }

    private static void getResumeInfo(Document doc) throws ParseException { // 从document中抽取出简历信息
        String name;
        String gender;
        String birthday;
        String live_city = "";
        String phone = "";
        String email = "";
        String self_assessment = "";
        String marriage_cnt;
        String hukou_cnt;
        String resumeId;
        String backgd;
        int hukou;
        long birthdaytimestamp;
        name = doc.select("strong").first().text();
        String info = doc.select("span.blue1").first().text();
        if(info.contains("已婚")){
            marriage_cnt = "已婚";
        }else if(info.contains("未婚")){
            marriage_cnt = "未婚";
        }else{
            marriage_cnt = "保密";
        }
        if(info.contains("男")){
            gender = "男";
        }else if(info.contains("女")){
            gender = "女";
        }else {
            gender = "";
        }
        birthday = GetBirthdayFromString(info);
        birthdaytimestamp = TransStringToTimeStamp(GetNumFromString(birthday));
        Elements resumetags = doc.select("span.blue1").first().parents().parents().parents().select("td");
        for (int i = 0; i < resumetags.size(); i++) {
            if (resumetags.get(i).text().equals("居住地：") && resumetags.size() > i + 1) {
                live_city = resumetags.get(i + 1).text();
            } else if (resumetags.get(i).text().equals("电　话：") && resumetags.size() > i + 1) {
                phone = resumetags.get(i + 1).text();
                phone = GetNumFromString(phone);
            } else if (resumetags.get(i).text().equals("E-mail：") && resumetags.size() > i + 1) {
                email = resumetags.get(i + 1).text();
            } else if (resumetags.get(i).text().equals("户　口：") && resumetags.size() > i + 1) {
                hukou_cnt = resumetags.get(i + 1).text();
                hukou = Utils.getCityCodeFromString(hukou_cnt);
                rWrapper.getResume_info().setHukou_cnt(hukou_cnt);
                rWrapper.getResume_info().setHukou(hukou);
            }
        }
        String[] cityList = live_city.split("-");
        for (String city : cityList) {
            rWrapper.getResume_info().setCur_city_id(Utils.getCityCodeFromString(city));
        }
        Elements div = doc.select("div.titleLineB");
        for (int j = 0; j < div.size(); j++) {
            if (div.get(j).text().equals("自我评价")) {
                self_assessment = div.get(j).parent().parent().nextElementSibling().text();
                break;
            }
        }
        Elements td = doc.select("td");
        for(int j=0;j<td.size();j++){
            if(td.get(j).text().equals("学　历：") && td.size()>j+1){
                backgd = td.get(j+1).text();
                rWrapper.getResume_info().setBackgd_cnt(backgd);
                rWrapper.getResume_info().setBackgd(Utils.getBackgdCodeFromString(backgd));
                break;
            }
        }
        rWrapper.getResume_info().setName(name);
        rWrapper.getResume_info().setFst_job_date_cnt(fst_job_date_cnt);
        rWrapper.getResume_info().setFst_job_date(fst_job_date);
        rWrapper.getResume_info().setLst_job_date_cnt(lst_job_date_cnt);
        rWrapper.getResume_info().setLst_job_date(lst_job_date);
        rWrapper.getResume_info().setGender(Utils.getGenderCodeFromString(gender));
        rWrapper.getResume_info().setGender_cnt(gender);
        rWrapper.getResume_info().setBirthday_cnt(birthday);
        rWrapper.getResume_info().setBirthday(birthdaytimestamp);
        rWrapper.getResume_info().setMarriage_cnt(marriage_cnt);
        rWrapper.getResume_info().setMobilephone(phone);
        rWrapper.getResume_info().setEmail(email);
        rWrapper.getResume_info().setCur_city_cnt(live_city);
        ResumeWrapper.TResume_info.TSelf_assessment tSelf_assessment = new ResumeWrapper.TResume_info.TSelf_assessment();
        tSelf_assessment.setContent_type("自我评价");
        tSelf_assessment.setContent(self_assessment);
        rWrapper.getResume_info().getSelf_assessment().add(tSelf_assessment);
        resumeId = doc.select("span.blue1").first().parents().parents().select("td").get(1).select("p").text();   //获取resumeId
        resumeId = GetNumFromString(resumeId);
        rWrapper.getMeta_info().getResume_tags().setEmail(email);
        rWrapper.getMeta_info().getResume_tags().setName_mobile(name + "+" + phone);
        rWrapper.getMeta_info().getResume_tags().setSrid(resumeId);
    }


    private static void getJobIntention(Document doc) { // 从document中抽取出求职意向
        String city;
        String salary = "";
        String state = "";
        String job;
        String job_nature = "";
        String arrival_time = "";
        String resumeId;
        String trade;
        String hope_salary_from = "";
        String hope_salary_to = "";
        String cur_salart_cnt = "";
        String cur_salary_from = "";
        String cur_salary_to = "";
        List<String> city_list = new ArrayList<String>();
        List<String> cityId_list = new ArrayList<String>();
        List<String> job_list = new ArrayList<String>();
        List<String> jobId_list = new ArrayList<String>();
        List<String> trade_cnt_list = new ArrayList<String>();
        List<String> trade_list = new ArrayList<String>();
        Elements jobdiv = doc.select("div.titleLineB");
        ResumeWrapper.TDeliver_info deliverInfo = new ResumeWrapper.TDeliver_info();
        resumeId = doc.select("span.blue1").first().parents().parents().select("td").get(1).select("p").text();   //获取resumeId
        resumeId = GetNumFromString(resumeId);
        deliverInfo.setSrid(resumeId);
        deliverInfo.setSid(2);
        for (int m = 0; m < jobdiv.size(); m++) {
            if (jobdiv.get(m).text().contains("求职意向")) {
                Element jobintention = jobdiv.get(m).parent().parent().parent();
                int size = jobintention.select("tr").size();
                for (int i = 1; i < size - 1; i++) {
                    if (jobintention.select("tr").get(i).select("td").size() > 0) {
                        String attr = jobintention.select("tr").get(i).select("td").get(0).text();
                        if (attr.equals("到岗时间：")) {
                            arrival_time = jobintention.select("tr").get(i).select("td").get(1).text();
                        } else if (attr.equals("工作性质：")) {
                            job_nature = jobintention.select("tr").get(i).select("td").get(1).text();
                        } else if (attr.equals("希望行业：")) {
                            trade = jobintention.select("tr").get(i).select("td").get(1).text();
                            String[] trade1 = trade.split("，");
                            for (String tra : trade1) {
                                trade_cnt_list.add(tra);
                                trade_list.add(Utils.getJobTradeCodeFromString(tra));
                            }
                        } else if (attr.equals("目标地点：")) {
                            city = jobintention.select("tr").get(i).select("td").get(1).text();
                            String[] ss = city.split("，");
                            for (String s : ss) {
                                city_list.add(s);
                                cityId_list.add(Utils.getCityCodeFromString(s).toString());
                            }
                        } else if (attr.equals("期望薪资：") || attr.equals("期望月薪：")) {
                            salary = jobintention.select("tr").get(i).select("td").get(1).text().replace("\u00a0","");
                        } else if (attr.equals("求职状态：")) {
                            state = jobintention.select("tr").get(i).select("td").get(1).text();
                        } else if (attr.equals("目标职能：")) {
                            job = jobintention.select("tr").get(i).select("td").get(1).text();
                            if (job.split("，").length > 1) {
                                for (int j = 0; j < job.split("，").length; j++) {
                                    job_list.add(job.split("，")[j]);
                                    jobId_list.add(Utils.getJobCodeFromString(job.split("，")[j]));
                                }
                            } else {
                                job_list.add(job);
                                jobId_list.add(Utils.getJobCodeFromString(job));
                            }
                        }
                    }
                }
                if(salary.contains("元/年") || salary.contains("年薪")){                 //期望薪资
                    if(salary.contains("-")) {
                        hope_salary_from = String.valueOf(Math.round(Float.parseFloat(GetNumFromString(salary.split("-")[0])) * 10000 / 12));
                        hope_salary_to = String.valueOf(Math.round(Float.parseFloat(GetNumFromString(salary.split("-")[1])) * 10000 / 12));
                    }else if(salary.contains("以下")){
                        hope_salary_from = "";
                        hope_salary_to = "1666";
                    }else if(salary.contains("以上")){
                        hope_salary_from = "83333";
                        hope_salary_to = "";
                    }
                } else if(salary.contains("元/月") || salary.contains("月薪")){
                    if(salary.contains("-")) {
                        hope_salary_from = GetNumFromString(salary.split("-")[0]);
                        hope_salary_to =  GetNumFromString(salary.split("-")[1]);
                    }else if(salary.contains("以下")){
                        hope_salary_from = "";
                        hope_salary_to = "1500";
                    }else if(salary.contains("以上")){
                        hope_salary_from = "100000";
                        hope_salary_to = "";
                    }
                }
                Elements salart_td = doc.select("td");
                for(int n=0;n<salart_td.size();n++){
                    if(salart_td.get(n).text().equals("目前薪资：") && salart_td.size() > n+1){
                       cur_salart_cnt = salart_td.get(n+1).text().replace("\u00a0","");
                        if(cur_salart_cnt.contains("年薪")){               //当前薪资
                            if(cur_salart_cnt.contains("-")) {
                                cur_salary_from = String.valueOf(Math.round(Float.parseFloat(GetNumFromString(cur_salart_cnt.split("-")[0])) * 10000 / 12));
                                cur_salary_to = String.valueOf(Math.round(Float.parseFloat(GetNumFromString(cur_salart_cnt.split("-")[1])) * 10000 / 12));
                            }else if(cur_salart_cnt.contains("以下")){
                                cur_salary_from = "";
                                cur_salary_to = "1666";
                            }else if(cur_salart_cnt.contains("以上")){
                                cur_salary_from = "83333";
                                cur_salary_to = "";
                            }
                        }else if(cur_salart_cnt.contains("月薪")){
                            if(cur_salart_cnt.contains("-")) {
                                cur_salary_from = GetNumFromString(cur_salart_cnt.split("-")[0]);
                                cur_salary_to = GetNumFromString(cur_salart_cnt.split("-")[1]);
                            }else if(cur_salart_cnt.contains("以下")){
                                cur_salary_from = "";
                                cur_salary_to = "1500";
                            }else if(cur_salart_cnt.contains("以上")){
                                cur_salary_from = "100000";
                                cur_salary_to = "";
                            }
                        }
                        break;
                    }
                }
                ResumeWrapper.TDeliver_info.TJob_intension jobIntension = new ResumeWrapper.TDeliver_info.TJob_intension();
                jobIntension.setCity_cnt(city_list);
                jobIntension.setCity(cityId_list);
                jobIntension.setArrival_time(arrival_time);
                jobIntension.setJob_nature_cnt(job_nature);
                jobIntension.setJob_nature(Utils.getJobNatureCodeFromString(job_nature));
                jobIntension.setJob_cnt(job_list);
                jobIntension.setJob(jobId_list);
                jobIntension.setState_cnt(state);
                jobIntension.setTrade(trade_list);
                jobIntension.setHope_salary_from(hope_salary_from);
                jobIntension.setHope_salary_to(hope_salary_to);
                jobIntension.setHope_salary_cnt(salary);
                jobIntension.setCur_salary_cnt(cur_salart_cnt);
                jobIntension.setCur_salary_from(cur_salary_from);
                jobIntension.setCur_salary_to(cur_salary_to);
                jobIntension.setTrade_cnt(trade_cnt_list);
                deliverInfo.setJob_intension(jobIntension);
                break;
            }
        }
        rWrapper.getDeliver_info().add(deliverInfo);
    }

    private static void getWorkExperience(Document doc) throws ParseException { // 从document中抽取出工作经历
        String stime;
        String etime;
        String stime_p;    //该变量作用只是用于传递值
        String etime_p;
        String time_cnt;   // 工作时间原文
        String company;
        String company_type;
        String company_size_cnt;
        String location = "";
        String department; // 部门
        String trade;
        String job;
        String job_description = "";
        String report_to = ""; // 汇报对象
        String work_content;
        Elements workexper1 = doc.select("div.titleLineB");
        for (int k = 0; k < workexper1.size(); k++) {
            if (workexper1.get(k).text().equals("工作经验")) {
                Elements workexper = workexper1.get(k).parent().parent().nextElementSibling().select("tbody").get(0).select("tr");
                int tr_num = workexper.size();
                int work_num = 0;
                for (int j = 0; j < tr_num; j++) {
                    if (workexper.get(j).text().contains("职位名称：") || workexper.get(j).text().contains("职位：")) {
                        work_num++;
                    }
                }
                rWrapper.getMeta_info().setWorks(work_num);
                int r = 1;
                for (int j = 0; j < tr_num; j++) {
                    if (workexper.get(j).text().contains("职位名称：") || workexper.get(j).text().contains("职位：")) {
                        long stimestamp = 0;
                        long etimestamp = 0;
                        company = "";
                        company_type = "";
                        company_size_cnt = "";
                        job = "";
                        department = "";
                        trade = "";
                        job_description = "";
                        String row1 = workexper.get(j - 1).text();
                        stime = (String) GetWorkInfoFromString(row1).get(0);
                        stime_p = GetNumFromString(stime);
                        if (stime_p.length() == 5) {
                            stime_p = stime.substring(0, 4) + "0" + stime_p.substring(4);
                        }

                        stimestamp = TransStringToTimeStamp(stime_p);
                        if (r == work_num) {
                            fst_job_date_cnt = stime;
                            fst_job_date = stimestamp;
                        }
                        etime = (String) GetWorkInfoFromString(row1).get(1);
                        etime_p = GetNumFromString(etime);
                        if (etime_p.length() == 5) {
                            etime_p = etime_p.substring(0, 4) + "0" + etime_p.substring(4);
                        }

                        if (etime_p.length() == 6) {
                            etimestamp = TransStringToTimeStamp(etime_p);
                        }
                        if (r == 1) {
                            lst_job_date_cnt = etime;
                            lst_job_date = etimestamp;
                        }
                        r++;
                        if (row1.split(" ").length>2 && row1.split(" ")[2].length() > 2) {
                            company = row1.split("：")[1].split(" ")[0];
                        }
                        if(row1.split("：")[1].split(" ").length>1) {
                            if (row1.split("：")[1].split(" ")[1].split("、").length > 1) {
                                company_size_cnt = row1.split("：")[1].split(" ")[1].split("、")[0].replace("（", "");
                                company_type = row1.split("：")[1].split(" ")[1].split("、")[1].replace("）", "");
                            } else {
                                String cont = row1.split("：")[1].split(" ")[1].replace("（", "").replace("）", "").replace("[", "");
                                if (cont.contains("0")) {
                                    company_size_cnt = cont;
                                } else {
                                    company_type = cont;
                                }
                            }
                        }
                        String row2 = workexper.get(j).text();
                        if (row2.contains("职位名称：")) {
                            job = row2.split(" ")[0].substring(5).replace("\u00a0", "");
                        } else if (row2.contains("职位：")) {
                            job = row2.split(" ")[0].substring(3).replace("\u00a0", "");
                        }
                        if (row2.contains("部门：")) {
                            department = row2.split(" ")[1].substring(3);
                        }
                        String row3 = "";
                        if (workexper.size() > (j + 1)) {
                            row3 = workexper.get(j + 1).text();
                            if (row3.contains("行业：")) {
                                trade = row3.substring(3);
                            } else if (workexper.get(j + 1).child(0).attr("id").equals("Cur_Val")) {
                                job_description = row3;
                            }
                        }
                        String row4 = "";
                        if (workexper.size() > (j + 2) && workexper.get(j + 2).child(0).attr("id").equals("Cur_Val")) {
                            row4 = workexper.get(j + 2).text();
                            if (workexper.get(j + 2).child(0).attr("id").equals("Cur_Val")) {
                                job_description = row4;
                            }
                        }
                        time_cnt = stime + "--" + etime;
                        work_content = row1 + row2 + row3 + row4;
                        ResumeWrapper.TResume_info.TWork tWork = new ResumeWrapper.TResume_info.TWork();
                        tWork.setStime(stimestamp);
                        tWork.setEtime(etimestamp);
                        tWork.setTime_cnt(time_cnt);
                        tWork.setCompany(company);
                        tWork.setCompany_type_cnt(company_type);
                        tWork.setCompany_size_cnt(company_size_cnt);
                        tWork.setLocation(location);
                        tWork.setDepartment(department);
                        tWork.setTrade_cnt(trade);
                        tWork.setTrade(Utils.getJobTradeCodeFromString(trade));
                        tWork.setJob(job);
                        tWork.setJob_description(job_description);
                        tWork.setReport_to(report_to);
                        rWrapper.getResume_info().getWork().add(tWork);
                        ResumeWrapper.TResume_info.TWork_cnt tWork_cnt = new ResumeWrapper.TResume_info.TWork_cnt();
                        tWork_cnt.setContent(work_content);
                        rWrapper.getResume_info().getWork_cnt().add(tWork_cnt);
                    }
                }
            }
        }
    }

    private static void getEducationExperience(Document doc) throws ParseException { // 从document中抽取出教育经历
        String stime;
        String etime;
        long stimestamp;
        long etimestamp;
        String time_cnt;
        String school;
        String speciality;
        String degree_cnt;
        String description;
        String education_cnt;
        Elements div = doc.select("div.titleLineB");
        for (int i = 0; i < div.size(); i++) {
            if (div.get(i).text().equals("教育经历")) {
                Elements educontent = div.get(i).parent().parent().nextElementSibling().select("tbody").get(0).select("tr");
                for (int j = 0; j < educontent.size(); j++) {
                    if (educontent.get(j).child(0).attr("width").equals("17%")) {
                        school = "";
                        speciality = "";
                        degree_cnt = "";
                        description = "";
                        time_cnt = educontent.get(j).child(0).text();
                        stime = time_cnt.split("--")[0].replace(" ", "");
                        etime = time_cnt.split("--")[1].replace(" ", "");
                        stimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(stime);
                        etimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(etime);
                        if (educontent.get(j).children().size() > 1) {
                            school = educontent.get(j).child(1).text();
                        }
                        if (educontent.get(j).children().size() > 2) {
                            speciality = educontent.get(j).child(2).text();
                        }
                        if (educontent.get(j).children().size() > 3) {
                            degree_cnt = educontent.get(j).child(3).text();
                        }
                        if (educontent.size() > j + 1) {
                            if (educontent.get(j + 1).child(0).attr("id").equals("Cur_Val")) {
                                description = educontent.get(j + 1).text();
                            }
                        }
                        ResumeWrapper.TResume_info.TEducation tEducation = new ResumeWrapper.TResume_info.TEducation();
                        tEducation.setStime(stimestamp);
                        tEducation.setEtime(etimestamp);
                        tEducation.setTime_cnt(time_cnt);
                        tEducation.setSchool(school);
                        tEducation.setSpeciality(speciality);
                        tEducation.setBackgd_cnt(degree_cnt);        //学历放在Backgd_cnt
                        tEducation.setBackgd(Utils.getBackgdCodeFromString(degree_cnt).toString());
                        tEducation.setDescription(description);
                        rWrapper.getResume_info().getEducation().add(tEducation);
                    }
                }
                education_cnt = div.get(i).parent().parent().nextElementSibling().text();
                rWrapper.getResume_info().setEducation_cnt(education_cnt);
                break;
            }
        }
    }


    private static void getProjectExperience(Document doc) throws ParseException {           //抽取出项目经历
        String stime;
        String etime;
        long stimestamp;
        long etimestamp;
        String time_cnt;
        String project_name;    //项目名字
        String description;   //项目描述
        String duty;      //职责描述
        String dev_tools;  //开发工具
        String env_software; //软件环境
        String env_hardware;   //硬件环境
        String project_cnt;
        String row1;
        int project_num = 0;
        Elements div = doc.select("div.titleLineB");
        for (int i = 0; i < div.size(); i++) {
            if (div.get(i).text().equals("项目经验")) {
                Elements projectexp = div.get(i).parent().parent().nextElementSibling().select("tbody").get(0).select("tr");
                for (int j = 0; j < projectexp.size(); j++) {
                    if (projectexp.get(j).child(0).attr("colspan").equals("2") && projectexp.get(j).child(0).attr("class").equals("text_left")) {
                        dev_tools = "";
                        project_name = "";
                        description = "";
                        duty = "";
                        env_software = "";
                        env_hardware = "";
                        project_cnt = "";
                        row1 = projectexp.get(j).child(0).text();
                        if (row1.contains("\u00a0\u00a0")) {
                            time_cnt = row1.replace("\u00a0", " ").split("  ")[0].replace(" ", "");
                            if (row1.replace("\u00a0", " ").split("  ").length > 1) {
                                project_name = row1.replace("\u00a0", " ").split("  ")[1].replace(" ", "");
                            }
                        } else if (row1.contains("至今")) {
                            time_cnt = row1.split(" ")[0] + row1.split(" ")[1];
                            if (row1.split(" ").length > 2) {
                                project_name = row1.split(" ")[2];
                            }
                        } else {
                            time_cnt = row1.split(" ")[0] + row1.split(" ")[1] + row1.split(" ")[2];
                            if (row1.split(" ").length > 3) {
                                project_name = row1.split(" ")[3];
                            }
                        }
                        if(time_cnt.contains("-至今")){
                            time_cnt = time_cnt.split("--")[0] + "--" + "至今";
                            stime = time_cnt.split("--")[0];
                            etime = "至今";
                        }else{
                            stime = time_cnt.split("--")[0];
                            etime = time_cnt.split("--")[1];
                            if(etime.length()>6 && Character.isDigit(etime.charAt(6))){
                                time_cnt = stime + "--" + etime.substring(0,7);
                                etime = etime.substring(0,7);
                            }else{
                                time_cnt = stime + "--" + etime.substring(0,6);
                                etime = etime.substring(0,6);
                            }
                        }
                        stimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(stime);
                        if (!etime.equals("至今")) {
                            etime = RemoveChineseWordFromString(etime);
                            if(etime.length()>7){
                                etime = etime.substring(0,7);
                            }
                        }
                        etimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(etime);
                        String label, content;
                        for(int k=1;k<6;k++) {
                            if (projectexp.size() > j + k) {
                                project_cnt += projectexp.get(j + k).text();
                                if(projectexp.get(j + k).children().size()>0) {
                                    label = projectexp.get(j + k).child(0).text();
                                }else{
                                    label = "";
                                }
                                if(projectexp.get(j + k).children().size()>1) {
                                content = projectexp.get(j + k).child(1).text();
                                }else{
                                content = "";
                                }
                                if (label.equals("开发工具：")) {
                                    dev_tools = content;
                                } else if (label.equals("项目描述：")) {
                                    description = content;
                                } else if (label.equals("责任描述：")) {
                                    duty = content;
                                } else if (label.equals("软件环境：")) {
                                    env_software = content;
                                } else if (label.equals("硬件环境：")) {
                                    env_hardware = content;
                                }
                            }
                        }
                        ResumeWrapper.TResume_info.TProjects tProjects = new ResumeWrapper.TResume_info.TProjects();
                        tProjects.setStime(stimestamp);
                        tProjects.setEtime(etimestamp);
                        tProjects.setTime_cnt(time_cnt);
                        tProjects.setDev_tools(dev_tools);
                        tProjects.setDescription(description);
                        tProjects.setDuty(duty);
                        tProjects.setEnv_sofeware(env_software);
                        tProjects.setEnv_hardware(env_hardware);
                        tProjects.setProject_name(project_name);
                        rWrapper.getResume_info().getProjects().add(tProjects);
                        ResumeWrapper.TResume_info.TProjects_cnt tProjects_cnt = new ResumeWrapper.TResume_info.TProjects_cnt();
                        tProjects_cnt.setContent(row1 + project_cnt);
                        rWrapper.getResume_info().getProjects_cnt().add(tProjects_cnt);
                        project_num += 1;
                    }
                }
                rWrapper.getMeta_info().setProjects(project_num);
                break;
            }
        }
    }

    private static void getTrainingExperience(Document doc) throws ParseException{
        String stime;
        String etime;
        String time_cnt;
        String agency;    //机构名字
        String cer_name;   //证书名字
        String course;    //课程名字
        String description;
        String address;
        String training_cnt;
        Elements div = doc.select("div.titleLineB");
        for(int i=0;i < div.size();i++){
            if (div.get(i).text().equals("培训经历")) {
                training_cnt = div.get(i).parent().parent().nextElementSibling().text();
                rWrapper.getResume_info().setTraining_cnt(training_cnt);
                Elements training_tr = div.get(i).parent().parent().nextElementSibling().select("tbody").get(0).select("tr");
                for(int j=0;j<training_tr.size();j++){
                    time_cnt = "";
                    agency = "";
                    cer_name = "";
                    course = "";
                    description = "";
                    if(training_tr.get(j).children().size() == 4){
                        time_cnt = training_tr.get(j).child(0).text();
                        stime = time_cnt.split("--")[0];
                        etime = time_cnt.split("--")[1];
                        long stimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(stime);
                        long etimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(etime);
                        agency = training_tr.get(j).child(1).text();
                        course = training_tr.get(j).child(2).text();
                        cer_name = training_tr.get(j).child(3).text();
                        if(training_tr.size()>j+1 && training_tr.get(j+1).child(0).attr("id").equals("Cur_Val")){
                            description = training_tr.get(j+1).child(0).text();
                        }
                        ResumeWrapper.TResume_info.TTraining tTraining = new ResumeWrapper.TResume_info.TTraining();
                        tTraining.setTime_cnt(time_cnt);
                        tTraining.setStime(stimestamp);
                        tTraining.setEtime(etimestamp);
                        tTraining.setAgency(agency);
                        tTraining.setCourse(course);
                        tTraining.setCer_name(cer_name);
                        tTraining.setDescription(description);
                        rWrapper.getResume_info().getTrainings().add(tTraining);
                    }
                }
                break;
            }
        }
    }

    private static void getLanguageSkill(Document doc) throws ParseException {
        String language_cnt = "";
        String lang_cnt = "";
        String verbal = ""; //听说能力
        String verbal_cnt;
        String literacy = ""; //读写能力
        String literacy_cnt;
        String all_level = "";
        String lang_level = "";
        Elements div = doc.select("div.titleLineB");
        for (int i = 0; i < div.size(); i++) {
            if (div.get(i).text().equals("语言能力")) {
                Elements langu_content = div.get(i).parent().parent().nextElementSibling().select("tbody").get(1).select("tr[height=25]");
                for(int j=0;j<langu_content.size();j++){
                    lang_cnt = "";
                    verbal_cnt = "";
                    literacy_cnt = "";
                    if(langu_content.get(j).children().size()>0) {
                        lang_cnt = langu_content.get(j).child(0).text().split("（")[0];
                    }
                    if(langu_content.get(j).children().size()>1 && langu_content.get(j).child(1).text().split("，")[0].split("（").length>1) {
                        verbal_cnt = langu_content.get(j).child(1).text().split("，")[0].split("（")[1].replace("）", "");
                        if(langu_content.get(j).child(1).text().split("，").length > 1 && langu_content.get(j).child(1).text().split("，")[1].split("（").length>1) {
                            literacy_cnt = langu_content.get(j).child(1).text().split("，")[1].split("（")[1].replace("）", "");
                        }
                    }
                    ResumeWrapper.TResume_info.TLanguage tLanguage = new ResumeWrapper.TResume_info.TLanguage();
                    tLanguage.setLang_cnt(lang_cnt);
                    tLanguage.setVerbal_cnt(verbal_cnt);
                    tLanguage.setLiteracy_cnt(literacy_cnt);
                    rWrapper.getResume_info().getLanguage().add(tLanguage);
                }
                language_cnt = langu_content.text();
                rWrapper.getResume_info().setLanguage_cnt(language_cnt);
                break;
            }
        }
    }

    private static void getCertificate(Document doc) throws ParseException {
        String cer_name;
        String issued ;
        String description;
        String certification_cnt;
        Elements div = doc.select("div.titleLineB");
        for (int i = 0; i < div.size(); i++) {
            if (div.get(i).text().equals("证书")) {
                certification_cnt = div.get(i).parent().parent().nextElementSibling().text();
                rWrapper.getResume_info().setCertification_cnt(certification_cnt);
                Elements certificate = div.get(i).parent().parent().nextElementSibling().select("tbody").get(0).select("tr");
                for(int j=0; j<certificate.size();j++){
                    if(certificate.get(j).child(0).attr("width").equals("17%")) {
                        issued = certificate.get(j).child(0).text();
                        if(certificate.get(j).children().size()>1){
                            cer_name = certificate.get(j).child(1).text();
                        }else{
                            cer_name = "";
                        }
                        description = certificate.get(j).text();
                        ResumeWrapper.TResume_info.TCertification tCertification = new ResumeWrapper.TResume_info.TCertification();
                        tCertification.setIssued(issued);
                        tCertification.setCer_name(cer_name);
                        tCertification.setDescription(description);
                        rWrapper.getResume_info().getCertification().add(tCertification);
                    }
                }
            }
        }
    }



    public boolean parse(String filePath, ResumeWrapper resume) {
        boolean result, result2, result3;
        ResumeWrapper resume2 = new ResumeWrapper();
        result2 = new QianChengParser_2().parse(filePath, resume2);
        if(result2){
            result = new QianChengParser_2().parse(filePath, resume);
        }else{
            result3 = new QianChengParser_1().parse(filePath, resume2);
            if(result3){
                result = new QianChengParser_1().parse(filePath, resume);
            }else{
                result = parse2(filePath, resume);
            }
        }
        return result;
    }
    private boolean parse2(String filePath, ResumeWrapper resume) {
        boolean result = true;
        try {
            String htmlContent = org.emailresume.common.Utils.readFileByBytes(filePath);
            rWrapper = resume;
            Document doc = Jsoup.parse(htmlContent);
            parseHtml(doc);
            if(rWrapper.getResume_info().getWork().isEmpty() || rWrapper.getResume_info().getEducation().isEmpty()) {
                if (rWrapper.getResume_info().getWork().isEmpty() && rWrapper.getResume_info().getEducation().isEmpty()) {
                    String server_ip = org.emailresume.common.Utils.getIP();
                    // 如果工作和教育都没有解析出来，则将该简历信息插入数据库
                    String cur_time = org.emailresume.common.Utils.getCurrentDateTime();
                    InsertDataToDB.insertDataToDB(cur_time, server_ip, filePath, "2");
                    logger.error("file error: " + filePath);
                    result = false;
                }else{
                    logger.warn("该简历缺少工作或教育字段：" + filePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private static void parseHtml(Document doc) throws ParseException {

        // 获取求职意向
        getJobIntention(doc);

        // 获取工作经历
        getWorkExperience(doc);

        // 获得教育经历
        getEducationExperience(doc);

        // 获取简历信息
        getResumeInfo(doc);

        // 获取项目经历
        getProjectExperience(doc);

        //获取语言能力
        getLanguageSkill(doc);

        // 获取证书内容
        getCertificate(doc);

        // 获取培训经历
        getTrainingExperience(doc);

    }
}
