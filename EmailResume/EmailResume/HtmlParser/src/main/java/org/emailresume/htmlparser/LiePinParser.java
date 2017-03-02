package org.emailresume.htmlparser;

import org.emailresume.common.TransferStringToTimeStamp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */

public class LiePinParser implements Parser {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LiePinParser.class);
    static ResumeWrapper rWrapper = null;
    static String fst_job_date_cnt = "";
    static long fst_job_date = 0;
    static String lst_job_date_cnt = "";
    static long lst_job_date = 0;

    private static void getJobIntention(Document doc) {
        String salary = "";
        String state = "";
        String job_nature = "";
        String arrival_time = "";
        String resumeId = "";
        String trade;
        String hope_salary_from = "";
        String hope_salary_to = "";
        String cur_salary_cnt = "";
        String cur_salary_from = "";
        String cur_salary_to = "";
        List<String> city_list = new ArrayList<String>();
        List<String> cityId_list = new ArrayList<String>();
        List<String> job_list = new ArrayList<String>();
        List<String> jobId_list = new ArrayList<String>();
        List<String> trade_cnt_list = new ArrayList<String>();
        List<String> trade_list = new ArrayList<String>();
        Elements tableinfo = doc.select("table").get(4).select("tr");
        int num_tr = tableinfo.size();
        for (int i = 0; i < num_tr; i++) {
            if (tableinfo.get(i).text().contains("求职意向")) {
                for (int j = i + 1; j < num_tr - 1; j++) {
                    if (tableinfo.get(j).child(0).hasAttr("colspan") && tableinfo.get(j).child(0).attr("colspan").equals("4")) {
                        break;
                    } else {
                        String row_content = tableinfo.get(j).text().replace("\u00a0", "");
                        String lable = row_content.split("：")[0];
                        String content = "";
                        if(row_content.split("：").length>1){
                            content = row_content.split("：")[1];
                        }
                        if (lable.contains("期望年薪")) {
                            salary = content.trim();
                            if(!salary.equals("面议")) {
                            hope_salary_from = salary.split("（")[1].split("元/月")[0];
                            }
                        } else if (lable.contains("期望从事行业")) {
                            for (String trad : content.split(";")) {
                                trade_cnt_list.add(trad.trim());
                                trade_list.add(Utils.getJobTradeCodeFromString(trad.trim()));
                            }
                        } else if (lable.contains("期望从事职业")) {
                            for (String job : content.split(";")) {
                                job_list.add(job.trim());
                                jobId_list.add(Utils.getJobCodeFromString(job.trim()));
                            }
                        } else if (lable.contains("期望工作地点")) {
                            for (String city : content.split(";")) {
                                city_list.add(city.trim());
                                cityId_list.add(Utils.getCityCodeFromString(city.trim()).toString());
                            }
                        }
                    }
                }
                break;
            }
        }
        Elements td_assem = doc.select("td");
        for (int k = 0; k < td_assem.size(); k++) {
            if (td_assem.get(k).text().equals("职业状态：")) {
                state = td_assem.get(k).nextElementSibling().text();
                break;
            }
        }
        Elements p_assem = doc.select("p");
        for (int m = 0; m < p_assem.size(); m++) {
            if (p_assem.get(m).text().contains("简历编号")) {
                resumeId = p_assem.get(m).text().split("：")[1].replace("\u00a0", " ").split(" ")[0];
                break;
            }
        }
        Elements td = doc.select("td");
        for(int k=0;k<td.size();k++){
            if(td.get(k).text().equals("目前年薪：")){
                cur_salary_cnt = td.get(k+1).text();
                if(!cur_salary_cnt.equals("保密")) {
                cur_salary_from = cur_salary_cnt.split("（")[1].split("元/月")[0];
                }
                break;
            }
        }
        ResumeWrapper.TDeliver_info.TJob_intension tjob_intention = new ResumeWrapper.TDeliver_info.TJob_intension();
        tjob_intention.setHope_salary_cnt(salary);
        tjob_intention.setHope_salary_from(hope_salary_from);
        tjob_intention.setHope_salary_to(hope_salary_to);
        tjob_intention.setState_cnt(state);
        tjob_intention.setCity_cnt(city_list);
        tjob_intention.setCity(cityId_list);
        tjob_intention.setJob_cnt(job_list);
        tjob_intention.setJob(jobId_list);
        tjob_intention.setTrade_cnt(trade_cnt_list);
        tjob_intention.setTrade(trade_list);
        tjob_intention.setCur_salary_cnt(cur_salary_cnt);
        tjob_intention.setCur_salary_from(cur_salary_from);
        tjob_intention.setCur_salary_to(cur_salary_to);
        ResumeWrapper.TDeliver_info deliverInfo = new ResumeWrapper.TDeliver_info();
        deliverInfo.setJob_intension(tjob_intention);
        deliverInfo.setSrid(resumeId);
        deliverInfo.setSid(3);
        rWrapper.getDeliver_info().add(deliverInfo);
    }

    private static void getWorkExperience(Document doc) throws ParseException {
        String stime;
        String etime;
        String time_cnt;   // 工作时间原文
        String company;
        String location;
        String achievements;
        String job;
        String responsibility ;
        String report_to ; // 汇报对象
        String work_content;
        Elements tableinfo = doc.select("table").get(4).select("tr");
        int num_tr = tableinfo.size();
        int work_num = 0;
        for (int i = 0; i < num_tr; i++) {
            if (tableinfo.get(i).text().equals("工作经历")) {
                for (int j = i + 1; j < num_tr; j++) {
                    if (tableinfo.get(j).child(0).hasAttr("colspan") && tableinfo.get(j).child(0).attr("colspan").equals("4")) {
                        work_num = j - i - 1;
                        rWrapper.getMeta_info().setWorks(work_num);
                        break;
                    }
                }
                break;
            }
        }
        int r = 1;
        for (int i = 0; i < num_tr; i++) {
            if (tableinfo.get(i).text().contains("工作经历")) {
                for (int j = i + 1; j < num_tr; j++) {
                    if (tableinfo.get(j).child(0).hasAttr("colspan") && tableinfo.get(j).child(0).attr("colspan").equals("4")) {
                        break;
                    } else {
                        report_to = "";
                        location = "";
                        responsibility = "";
                        achievements = "";
                        work_content = tableinfo.get(j).text();             //每一份工作的全部内容
                        time_cnt = tableinfo.get(j).child(0).text();
                        stime = time_cnt.split("-")[0];
                        etime = time_cnt.split("-")[1];
                        long stimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(stime);
                        long etimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(etime);
                        company = tableinfo.get(j).select("h3").get(0).text();
                        String jobcontent = "";
                        for(int k=0; k < tableinfo.get(j).select("h3").get(1).text().split("-")[0].split(" ").length-1; k++){
                            jobcontent += tableinfo.get(j).select("h3").get(1).text().split("-")[0].split(" ")[k];
                        }
                        job = jobcontent;
                        int num_p = tableinfo.get(j).select("p").size();
                        for (int k = 0; k < num_p; k++) {
                            String p_content = tableinfo.get(j).select("p").get(k).text();
                            if (p_content.contains("汇报对象")) {
                                report_to = p_content.split("：")[1];
                            }else if (p_content.contains("所在地区")) {
                                location = p_content.split("：")[1];
                            } else if (p_content.contains("工作职责")) {
                                if (tableinfo.get(j).select("p").size() > k + 1) {
                                    responsibility = tableinfo.get(j).select("p").get(k + 1).text();
                                }
                            }
                            else if (p_content.contains("工作业绩")) {
                                if (tableinfo.get(j).select("p").size() > k + 1) {
                                    achievements = tableinfo.get(j).select("p").get(k + 1).text();
                                }
                            }
                        }
                        if (r == 1) {
                            lst_job_date_cnt = etime;
                            lst_job_date = etimestamp;
                        }
                        if (r == work_num) {
                            fst_job_date_cnt = stime;
                            fst_job_date = stimestamp;
                        }
                        r++;
                        ResumeWrapper.TResume_info.TWork tWork = new ResumeWrapper.TResume_info.TWork();
                        tWork.setTime_cnt(time_cnt);
                        tWork.setStime(stimestamp);
                        tWork.setEtime(etimestamp);
                        tWork.setCompany(company);
                        tWork.setLocation(location);
                        tWork.setJob(job);
                        tWork.setJob_description(responsibility);
                        tWork.setReport_to(report_to);
                        tWork.setAchievements(achievements);
                        rWrapper.getResume_info().getWork().add(tWork);
                        ResumeWrapper.TResume_info.TWork_cnt tWork_cnt = new ResumeWrapper.TResume_info.TWork_cnt();
                        tWork_cnt.setContent(work_content);
                        rWrapper.getResume_info().getWork_cnt().add(tWork_cnt);
                    }
                }
                break;
            }
        }
    }

    private static void getProjectExperience(Document doc) throws ParseException {
        String stime;
        String etime;
        String time_cnt;
        String project_name;    //项目名字
        String description;   //项目描述
        String duty;      //职责描述
        String achievement;   //项目业绩
        String project_content;
        String position;   //项目职务
        String cur_company;      //所在公司
        int project_num = 0;
        Elements tableinfo = doc.select("table").get(4).select("tr");
        int num_tr = tableinfo.size();
        for (int i = 0; i < num_tr; i++) {
            if (tableinfo.get(i).text().contains("项目经历")) {
                for (int j = i + 1; j < num_tr; j++) {
                    if (tableinfo.get(j).child(0).hasAttr("colspan") && tableinfo.get(j).child(0).attr("colspan").equals("4")) {
                        break;
                    } else {
                        position = "";
                        cur_company = "";
                        description = "";
                        duty = "";
                        achievement = "";
                        project_content = tableinfo.get(j).text();         //每一份项目经历的全部内容
                        time_cnt = tableinfo.get(j).child(0).text();
                        stime = time_cnt.split("-")[0];
                        etime = time_cnt.split("-")[1];
                        long stimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(stime);
                        long etimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(etime);
                        project_name = tableinfo.get(j).select("h3").get(0).text();
                        int num_p = tableinfo.get(j).select("p").size();
                        for (int k = 0; k < num_p; k++) {
                            String p_content = tableinfo.get(j).select("p").get(k).text();
                            if (p_content.contains("项目职务")) {
                                position = p_content.split("：")[1];
                            } else if (p_content.contains("所在公司")) {
                                cur_company = p_content.split("：")[1];
                            } else if (p_content.contains("项目简介")) {
                                if (tableinfo.get(j).select("p").size() > k + 1) {
                                    description = tableinfo.get(j).select("p").get(k + 1).text();
                                }
                            }else if (p_content.contains("项目职责")) {
                                if (tableinfo.get(j).select("p").size() > k + 1) {
                                    duty = tableinfo.get(j).select("p").get(k + 1).text();
                                }
                            }else if (p_content.contains("项目业绩")) {
                                if (tableinfo.get(j).select("p").size() > k + 1) {
                                    achievement = tableinfo.get(j).select("p").get(k + 1).text();
                                }
                            }
                        }
                        ResumeWrapper.TResume_info.TProjects tProjects = new ResumeWrapper.TResume_info.TProjects();
                        tProjects.setProject_name(project_name);
                        tProjects.setTime_cnt(time_cnt);
                        tProjects.setStime(stimestamp);
                        tProjects.setEtime(etimestamp);
                        tProjects.setDuty(duty);
                        tProjects.setAchievement(achievement);
                        tProjects.setDescription(description);
                        tProjects.setCur_company(cur_company);
                        tProjects.setPosition(position);
                        rWrapper.getResume_info().getProjects().add(tProjects);
                        ResumeWrapper.TResume_info.TProjects_cnt tProjects_cnt = new ResumeWrapper.TResume_info.TProjects_cnt();
                        tProjects_cnt.setContent(project_content);
                        rWrapper.getResume_info().getProjects_cnt().add(tProjects_cnt);
                        project_num ++;
                    }
                }
                rWrapper.getMeta_info().setProjects(project_num);
                break;
            }
        }
    }

    private static void getEducationExperience(Document doc) throws ParseException {
        String stime;
        String etime;
        long stimestamp;
        long etimestamp;
        String time_cnt;
        String school;
        String speciality;
        String degree_cnt;
        String description = "";
        String education_cnt = "";
        Elements tableinfo = doc.select("table").get(4).select("tr");
        int num_tr = tableinfo.size();
        for (int i = 0; i < num_tr; i++) {
            if (tableinfo.get(i).text().contains("教育经历")) {
                for (int j = i + 1; j < num_tr; j++) {
                    if (tableinfo.get(j).child(0).hasAttr("colspan") && tableinfo.get(j).child(0).attr("colspan").equals("4")) {
                        break;
                    } else {
                        education_cnt += tableinfo.get(j).text();
                        time_cnt = tableinfo.get(j).child(0).text();
                        stime = time_cnt.split("-")[0];
                        etime = time_cnt.split("-")[1];
                        stimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(stime);
                        etimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(etime);
                        school = tableinfo.get(j).select("h3").get(0).text();
                        speciality = tableinfo.get(j).select("p").get(0).text().split("：")[1];
                        degree_cnt = tableinfo.get(j).select("p").get(1).text().split("：")[1];
                        ResumeWrapper.TResume_info.TEducation tEducation = new ResumeWrapper.TResume_info.TEducation();
                        tEducation.setTime_cnt(time_cnt);
                        tEducation.setStime(stimestamp);
                        tEducation.setEtime(etimestamp);
                        tEducation.setSchool(school);
                        tEducation.setSpeciality(speciality);
                        tEducation.setBackgd_cnt(degree_cnt);
                        tEducation.setBackgd(Utils.getBackgdCodeFromString(degree_cnt).toString());
                        rWrapper.getResume_info().getEducation().add(tEducation);
                    }
                }
                rWrapper.getResume_info().setEducation_cnt(education_cnt);
                break;
            }
        }
    }

    private static void getResumeInfo(Document doc) throws ParseException {
        String name = "";
        String gender = "";
        int age = 0;
        String birthday = "";
        String live_city = "";
        String phone = "";
        String email = "";
        String degree = "";
        String nationality = "";
        String self_assessment = "";
        String resumeId;
        String hukou_cnt = "";
        int hukou = 0;
        long birthdaytimestamp = 0;
        Elements tableinfo = doc.select("table").get(4).select("tr");
        int num_tr = tableinfo.size();
        for (int i = 0; i < num_tr; i++) {
            if (tableinfo.get(i).text().contains("基本信息")) {
                for (int j = i + 1; j < num_tr; j++) {
                    if (tableinfo.get(j).child(0).hasAttr("colspan") && tableinfo.get(j).child(0).attr("colspan").equals("4")) {
                        break;
                    } else {
                        if (tableinfo.get(j).child(0).text().contains("姓名")) {
                            name = tableinfo.get(j).child(1).text();
                        } else if (tableinfo.get(j).child(0).text().contains("联系电话")) {
                            phone = tableinfo.get(j).child(1).text();
                        } else if (tableinfo.get(j).child(0).text().contains("电子邮件")) {
                            email = tableinfo.get(j).child(1).text();
                        } else if (tableinfo.get(j).child(0).text().contains("教育程度")) {
                            degree = tableinfo.get(j).child(1).text();
                        } else if (tableinfo.get(j).child(0).text().contains("国籍")) {
                            nationality = tableinfo.get(j).child(1).text();
                        }
                        if (tableinfo.get(j).children().size() > 3 && tableinfo.get(j).child(2).text().contains("性 别")) {
                            gender = tableinfo.get(j).child(3).text();
                        } else if (tableinfo.get(j).children().size() > 3 && tableinfo.get(j).child(2).text().contains("年 龄")) {
                            age = Integer.parseInt(tableinfo.get(j).child(3).text());
                            birthday = Integer.toString(2016 - age) + "." + String.valueOf((int) (Math.random() * 12) + 1) + "." + String.valueOf((int) (Math.random() * 30) + 1);
                            birthdaytimestamp = TransferStringToTimeStamp.transferStringToTimeStamp(birthday);
                        } else if (tableinfo.get(j).children().size() > 3 && tableinfo.get(j).child(2).text().contains("户籍")) {
                            hukou_cnt = tableinfo.get(j).child(3).text();
                            hukou = Utils.getCityCodeFromString(hukou_cnt);
                        }
                    }
                }
            } else if (tableinfo.get(i).text().contains("目前职位概况")) {
                for (int j = i + 1; j < num_tr; j++) {
                    if (tableinfo.get(j).child(0).hasAttr("colspan") && tableinfo.get(j).child(0).attr("colspan").equals("4")) {
                        break;
                    } else {
                        if (tableinfo.get(j).child(0).text().contains("工作地点")) {
                            live_city = tableinfo.get(j).child(1).text();
                        }
                    }
                }
                break;
            }
        }
        rWrapper.getResume_info().setName(name);
        rWrapper.getResume_info().setGender_cnt(gender);
        rWrapper.getResume_info().setGender(Utils.getGenderCodeFromString(gender));
        rWrapper.getResume_info().setMobilephone(phone);
        rWrapper.getResume_info().setBackgd_cnt(degree);
        rWrapper.getResume_info().setBackgd(Utils.getBackgdCodeFromString(degree));
        rWrapper.getResume_info().setEmail(email);
        rWrapper.getResume_info().setBirthday_cnt(birthday);
        rWrapper.getResume_info().setBirthday(birthdaytimestamp);
        rWrapper.getResume_info().setHukou_cnt(hukou_cnt);
        rWrapper.getResume_info().setHukou(hukou);
        rWrapper.getResume_info().setCur_city_cnt(live_city);
        rWrapper.getResume_info().setCur_city_id(Utils.getCityCodeFromString(live_city));
        rWrapper.getResume_info().setNationality(nationality);
        rWrapper.getResume_info().setNationality_cnt(nationality);
        rWrapper.getResume_info().setFst_job_date_cnt(fst_job_date_cnt);
        rWrapper.getResume_info().setFst_job_date(fst_job_date);
        rWrapper.getResume_info().setLst_job_date_cnt(lst_job_date_cnt);
        rWrapper.getResume_info().setLst_job_date(lst_job_date);
        rWrapper.getMeta_info().getResume_tags().setEmail(email);
        rWrapper.getMeta_info().getResume_tags().setName_mobile(name + "+" + phone);
        for (int k = 0; k < num_tr; k++) {
            if (tableinfo.get(k).text().contains("自我评价")) {
                self_assessment = tableinfo.get(k + 1).text();
                break;
            }
        }
        ResumeWrapper.TResume_info.TSelf_assessment tSelf_assessment = new ResumeWrapper.TResume_info.TSelf_assessment();
        tSelf_assessment.setContent_type("自我评价");
        tSelf_assessment.setContent(self_assessment);
        rWrapper.getResume_info().getSelf_assessment().add(tSelf_assessment);
        Elements p_assem = doc.select("p");
        for (int m = 0; m < p_assem.size(); m++) {
            if (p_assem.get(m).text().contains("简历编号")) {
                resumeId = p_assem.get(m).text().split("：")[1].replace("\u00a0", " ").split(" ")[0];
                rWrapper.getMeta_info().getResume_tags().setSrid(resumeId);
                break;
            }
        }
    }

    public boolean parse(String filePath, ResumeWrapper resume) {
        boolean result, result2;
        ResumeWrapper resume2 = new ResumeWrapper();
        result2 = parse2(filePath, resume2);

        if(result2){
            result = parse2(filePath, resume);
        }else{
            result = new LiePinParser_1().parse(filePath, resume);
        }

        return result;
    }

    public boolean parse2(String filePath, ResumeWrapper resume) {
        try {
            String htmlContent = org.emailresume.common.Utils.readFileByBytes(filePath);
            rWrapper = resume;
            Document doc = Jsoup.parse(htmlContent);
            parseHtml(doc);
            if(rWrapper.getResume_info().getWork().isEmpty() || rWrapper.getResume_info().getEducation().isEmpty()) {
                if (rWrapper.getResume_info().getWork().isEmpty() && rWrapper.getResume_info().getEducation().isEmpty()) {
                    // 如果工作和教育都没有解析出来，则将该简历信息插入数据库
//                    String server_ip = org.emailresume.common.Utils.getIP();
//                    String cur_time = org.emailresume.common.Utils.getCurrentDateTime();
//                    InsertDataToDB.insertDataToDB(cur_time, server_ip, filePath, "3");
                    logger.error("file error: " + filePath);
                    return false;
                }else{
                    logger.warn("该简历缺少工作或教育字段：" + filePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
    }
}
