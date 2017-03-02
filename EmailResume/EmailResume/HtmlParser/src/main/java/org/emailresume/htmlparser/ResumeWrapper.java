package org.emailresume.htmlparser;

import java.util.ArrayList;
import java.util.List;

public class ResumeWrapper{

    public static class TResume_info{

        public static class TProjects_cnt{

            private	String	content;	/*2015.06 - 2016.01 年度创新 项目描述： 在酒吧经营过程中创造出新品，连续三个月销量位居榜首，为公司带来实际性利润，在年会上获得5000元奖励。 */

            public void setContent(String value){
                this.content = value;
            }
            public String getContent(){
                return this.content;
            }

        }
        private	List<TProjects_cnt>	projects_cnt = new ArrayList<TProjects_cnt>();	/*List<TProjects_cnt>*/
        public void setProjects_cnt(List<TProjects_cnt> value){
            this.projects_cnt = value;
        }
        public List<TProjects_cnt> getProjects_cnt(){
            return this.projects_cnt;
        }

        public static class TWork{

            private	Long stime;	/*1388505600*/
            private	String	im_subordinate;	/**/
            private	String	location;	/**/
            private	String	trade_cnt;	/*酒店/餐饮 */
            private	String	trade;	/*40*/
            private	String	reason_of_quit;	/**/
            private	String	department;	/**/
            private	String	company_size_cnt;	/**/
            private	String	company_type;	/**/
            private	String	job;	/*收银，主持，吧员*/
            private	String	salary_cnt;	/*4001-6000元/月*/
            private	String	report_to;	/**/
            private	String	salary_from;	/*4001*/
            private	String	achievements;	/**/
            private	String	company_disc;	/**/
            private	String	position_type;	/**/
            private	String	duty;	/**/
            private	Long	etime;	/*0*/
            private	String	company;	/*绿茉莉爱尔兰酒吧*/
            private	String	company_type_cnt;	/**/
            private	String	subordinate;	/**/
            private	String	salary_to;	/*6000*/
            private	String	job_description;	/*快速出餐出酒，完成每天销售额统计，账目盘点核对，主持每周举行的互动游戏及节日party.*/
            private	String	time_cnt;	/*2014.01 - 至今*/

            public void setStime(Long value){
                this.stime = value;
            }
            public Long getStime(){
                return this.stime;
            }

            public void setIm_subordinate(String value){
                this.im_subordinate = value;
            }
            public String getIm_subordinate(){
                return this.im_subordinate;
            }

            public void setLocation(String value){
                this.location = value;
            }
            public String getLocation(){
                return this.location;
            }

            public void setTrade_cnt(String value){
                this.trade_cnt = value;
            }
            public String getTrade_cnt(){
                return this.trade_cnt;
            }

            public void setTrade(String value){
                this.trade = value;
            }
            public String getTrade(){
                return this.trade;
            }

            public void setReason_of_quit(String value){
                this.reason_of_quit = value;
            }
            public String getReason_of_quit(){
                return this.reason_of_quit;
            }

            public void setDepartment(String value){
                this.department = value;
            }
            public String getDepartment(){
                return this.department;
            }

            public void setCompany_size_cnt(String value){
                this.company_size_cnt = value;
            }
            public String getCompany_size_cnt(){
                return this.company_size_cnt;
            }

            public void setCompany_type(String value){
                this.company_type = value;
            }
            public String getCompany_type(){
                return this.company_type;
            }

            public void setJob(String value){
                this.job = value;
            }
            public String getJob(){
                return this.job;
            }

            public void setSalary_cnt(String value){
                this.salary_cnt = value;
            }
            public String getSalary_cnt(){
                return this.salary_cnt;
            }

            public void setReport_to(String value){
                this.report_to = value;
            }
            public String getReport_to(){
                return this.report_to;
            }

            public void setSalary_from(String value){
                this.salary_from = value;
            }
            public String getSalary_from(){
                return this.salary_from;
            }

            public void setAchievements(String value){
                this.achievements = value;
            }
            public String getAchievements(){
                return this.achievements;
            }

            public void setCompany_disc(String value){
                this.company_disc = value;
            }
            public String getCompany_disc(){
                return this.company_disc;
            }

            public void setPosition_type(String value){
                this.position_type = value;
            }
            public String getPosition_type(){
                return this.position_type;
            }

            public void setDuty(String value){
                this.duty = value;
            }
            public String getDuty(){
                return this.duty;
            }

            public void setEtime(Long value){
                this.etime = value;
            }
            public Long getEtime(){
                return this.etime;
            }

            public void setCompany(String value){
                this.company = value;
            }
            public String getCompany(){
                return this.company;
            }

            public void setCompany_type_cnt(String value){
                this.company_type_cnt = value;
            }
            public String getCompany_type_cnt(){
                return this.company_type_cnt;
            }

            public void setSubordinate(String value){
                this.subordinate = value;
            }
            public String getSubordinate(){
                return this.subordinate;
            }

            public void setSalary_to(String value){
                this.salary_to = value;
            }
            public String getSalary_to(){
                return this.salary_to;
            }

            public void setJob_description(String value){
                this.job_description = value;
            }
            public String getJob_description(){
                return this.job_description;
            }

            public void setTime_cnt(String value){
                this.time_cnt = value;
            }
            public String getTime_cnt(){
                return this.time_cnt;
            }

        }
        private	List<TWork>	work = new ArrayList<TWork>();	/*List<TWork>*/
        public void setWork(List<TWork> value){
            this.work = value;
        }
        public List<TWork> getWork(){
            return this.work;
        }

        public static class TSelf_assessment{

            private	String	content;	/*本人性格开朗，吃苦耐劳，头脑灵活，五官端正，工作适应能力强，自律性好，诚实守信。无不良嗜好及违法犯罪记录。擅长英语口语交流，有5年销售行业工作经验及一定管理能力。*/
            private	String	content_type;	/*自我评价*/

            public void setContent(String value){
                this.content = value;
            }
            public String getContent(){
                return this.content;
            }

            public void setContent_type(String value){
                this.content_type = value;
            }
            public String getContent_type(){
                return this.content_type;
            }

        }
        private	List<TSelf_assessment>	self_assessment = new ArrayList<TSelf_assessment>();	/*List<TSelf_assessment>*/
        public void setSelf_assessment(List<TSelf_assessment> value){
            this.self_assessment = value;
        }
        public List<TSelf_assessment> getSelf_assessment(){
            return this.self_assessment;
        }

        public static class TLanguage{
            private String language;
            private String lang_cnt;
            private String verbal;
            private String verbal_cnt;
            private String literacy;
            private String literacy_cnt;
            private String all_level;
            private String lang_level;

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getLang_cnt() {
                return lang_cnt;
            }

            public void setLang_cnt(String lang_cnt) {
                this.lang_cnt = lang_cnt;
            }

            public String getVerbal() {
                return verbal;
            }

            public void setVerbal(String verbal) {
                this.verbal = verbal;
            }

            public String getVerbal_cnt() {
                return verbal_cnt;
            }

            public void setVerbal_cnt(String verbal_cnt) {
                this.verbal_cnt = verbal_cnt;
            }

            public String getLiteracy() {
                return literacy;
            }

            public void setLiteracy(String literacy) {
                this.literacy = literacy;
            }

            public String getLiteracy_cnt() {
                return literacy_cnt;
            }

            public void setLiteracy_cnt(String literacy_cnt) {
                this.literacy_cnt = literacy_cnt;
            }

            public String getAll_level() {
                return all_level;
            }

            public void setAll_level(String all_level) {
                this.all_level = all_level;
            }

            public String getLang_level() {
                return lang_level;
            }

            public void setLang_level(String lang_level) {
                this.lang_level = lang_level;
            }
        }

        private List<TLanguage> language = new ArrayList<TLanguage>();

        public List<TLanguage> getLanguage() {
            return language;
        }

        public void setLanguage(List<TLanguage> languages) {
            this.language = languages;
        }



        public static class TProjects{

            private	String	position;	/**/
            private	String	dev_tools;	/**/
            private	String	achievement;	/**/
            private	String	project_name;	/*年度创新*/
            private	Long	stime;	/*1433088000*/
            private	String	env_hardware;	/**/
            private	String	duty;	/**/
            private	Long	etime;	/*1451577600*/
            private	String	description;	/*在酒吧经营过程中创造出新品，连续三个月销量位居榜首，为公司带来实际性利润，在年会上获得5000元奖励。*/
            private	String	env_sofeware;	/**/
            private	String	cur_company;	/**/
            private	String	time_cnt;	/*2015.06 - 2016.01*/

            public void setPosition(String value){
                this.position = value;
            }
            public String getPosition(){
                return this.position;
            }

            public void setDev_tools(String value){
                this.dev_tools = value;
            }
            public String getDev_tools(){
                return this.dev_tools;
            }

            public void setAchievement(String value){
                this.achievement = value;
            }
            public String getAchievement(){
                return this.achievement;
            }

            public void setProject_name(String value){
                this.project_name = value;
            }
            public String getProject_name(){
                return this.project_name;
            }

            public void setStime(Long value){
                this.stime = value;
            }
            public Long getStime(){
                return this.stime;
            }

            public void setEnv_hardware(String value){
                this.env_hardware = value;
            }
            public String getEnv_hardware(){
                return this.env_hardware;
            }

            public void setDuty(String value){
                this.duty = value;
            }
            public String getDuty(){
                return this.duty;
            }

            public void setEtime(Long value){
                this.etime = value;
            }
            public Long getEtime(){
                return this.etime;
            }

            public void setDescription(String value){
                this.description = value;
            }
            public String getDescription(){
                return this.description;
            }

            public void setEnv_sofeware(String value){
                this.env_sofeware = value;
            }
            public String getEnv_sofeware(){
                return this.env_sofeware;
            }

            public void setCur_company(String value){
                this.cur_company = value;
            }
            public String getCur_company(){
                return this.cur_company;
            }

            public void setTime_cnt(String value){
                this.time_cnt = value;
            }
            public String getTime_cnt(){
                return this.time_cnt;
            }

        }
        private	List<TProjects>	projects = new ArrayList<TProjects>();	/*List<TProjects>*/
        public void setProjects(List<TProjects> value){
            this.projects = value;
        }
        public List<TProjects> getProjects(){
            return this.projects;
        }

        public static class TWork_cnt{

            private	String	content;	/*2014.01 - 至今 绿茉莉爱尔兰酒吧 （2年6个月） 收银，主持，吧员 | 4001-6000元/月 酒店/餐饮 工作描述： 快速出餐出酒，完成每天销售额统计，账目盘点核对，主持每周举行的互动游戏及节日party. */

            public void setContent(String value){
                this.content = value;
            }
            public String getContent(){
                return this.content;
            }

        }
        private	List<TWork_cnt>	work_cnt = new ArrayList<TWork_cnt>();	/*List<TWork_cnt>*/
        public void setWork_cnt(List<TWork_cnt> value){
            this.work_cnt = value;
        }
        public List<TWork_cnt> getWork_cnt(){
            return this.work_cnt;
        }

        public static class TEducation{

            private	String	unified_enrollment;	/**/
            private	String	degree_cnt;	/**/
            private	String	degree;	/**/
            private	String	school;	/*陕西省职业技术学院*/
            private	Long	stime;	/*1212249600*/
            private	String	speciality;	/*金融学*/
            private	Long	etime;	/*1306857600*/
            private	String	backgd;	/*5*/
            private	String	description;	/**/
            private	String	backgd_cnt;	/*大专*/
            private	String	time_cnt;	/*2008.06 - 2011.06*/

            public void setUnified_enrollment(String value){
                this.unified_enrollment = value;
            }
            public String getUnified_enrollment(){
                return this.unified_enrollment;
            }

            public void setDegree_cnt(String value){
                this.degree_cnt = value;
            }
            public String getDegree_cnt(){
                return this.degree_cnt;
            }

            public void setDegree(String value){
                this.degree = value;
            }
            public String getDegree(){
                return this.degree;
            }

            public void setSchool(String value){
                this.school = value;
            }
            public String getSchool(){
                return this.school;
            }

            public void setStime(Long value){
                this.stime = value;
            }
            public Long getStime(){
                return this.stime;
            }

            public void setSpeciality(String value){
                this.speciality = value;
            }
            public String getSpeciality(){
                return this.speciality;
            }

            public void setEtime(Long value){
                this.etime = value;
            }
            public Long getEtime(){
                return this.etime;
            }

            public void setBackgd(String value){
                this.backgd = value;
            }
            public String getBackgd(){
                return this.backgd;
            }

            public void setDescription(String value){
                this.description = value;
            }
            public String getDescription(){
                return this.description;
            }

            public void setBackgd_cnt(String value){
                this.backgd_cnt = value;
            }
            public String getBackgd_cnt(){
                return this.backgd_cnt;
            }

            public void setTime_cnt(String value){
                this.time_cnt = value;
            }
            public String getTime_cnt(){
                return this.time_cnt;
            }

        }
        private	List<TEducation>	education = new ArrayList<TEducation>();	/*List<TEducation>*/
        public void setEducation(List<TEducation> value){
            this.education = value;
        }
        public List<TEducation> getEducation(){
            return this.education;
        }

        private	String	skills;	/**/
        private	String	awards_in_schl;	/**/
        private	String	cur_city_cnt;	/*西安 雁塔区*/
        private	Integer	work_abroad;	/*0*/
        private	String	marriage_cnt;	/**/
        private	Integer	backgd;	/*5*/
        private	String	native_cnt;	/*西安*/
        private	Integer	height;	/*0*/
        private	String	interest;	/**/
        private	String	doc_id_cnt;	/**/
        private	Integer	gender;	/*1*/
        private	String	training_cnt;	/**/
        private	String	highest_shool;	/*陕西省职业技术学院*/
        private	String	fst_job_date_cnt;	/*2011.12*/
        private	String	certification_cnt;	/**/
        private	String	nickname;	/**/
        private	Object	recommendation;	/*Object*/
        private	Long	fst_job_date;	/*1322668800*/
        private	String	postcode;	/**/
        private	String	education_cnt;	/* 2008.06 - 2011.06 陕西省职业技术学院 金融学 大专 */
        private	String	nation;	/**/
        private	String	email;	/*wxp228@21cn.com*/
        private	String	logo_path;	/**/
        private	Integer	doc_id;	/*0*/
        private	Integer	study_abroad;	/*0*/
        private	String	gender_cnt;	/*男*/
        private	Long	birthday;	/*633801600*/
        private	String	highest_speciality;	/*金融学*/
        private	Integer	weight;	/*0*/
        private	Integer	child_count;	/*0*/
        private	Integer	manage_skill;	/*0*/
        private	String	rname;	/*智联简历*/
        private	String	nationality_cnt;	/*中国*/
        private	String	backgd_cnt;	/*大专*/
        private	String	hukou_cnt;	/*西安*/
        private	String	ext_message;	/**/
        private	String	mobilephone;	/*13772519371*/
        private	String	weixin;	/**/
        private	String	hands_on_exp;	/**/
        private	String	name;	/*王小鹏*/
        private	Integer	is_english;	/*0*/
        private	String	driving_lic;	/**/
        private	String	qq;	/**/
        private	Integer	native_place;	/*610100*/
        private	String	skills_cnt;	/**/
        private	Long	lst_job_date;	/*0*/
        private	String	website;	/**/
        private	Integer	cur_city_id;	/*610100*/
        private	String	nationality;
        private	String	nation_cnt;	/**/
        private	Integer	hukou;	/*610100*/
        private	String	language_cnt;	/**/
        private	String	ext_url;	/**/
        private	String	doc_id_type;	/**/
        private	String	appendix;	/**/
        private	Integer	marriage_stat;	/*0*/
        private	String	lst_job_date_cnt;	/* 至今*/
        private	String	telephone;	/**/
        private	String	birthday_cnt;	/*1990年2月*/

        public void setSkills(String value){
            this.skills = value;
        }
        public String getSkills(){
            return this.skills;
        }

        public void setAwards_in_schl(String value){
            this.awards_in_schl = value;
        }
        public String getAwards_in_schl(){
            return this.awards_in_schl;
        }

        public void setCur_city_cnt(String value){
            this.cur_city_cnt = value;
        }
        public String getCur_city_cnt(){
            return this.cur_city_cnt;
        }

        public void setWork_abroad(Integer value){
            this.work_abroad = value;
        }
        public Integer getWork_abroad(){
            return this.work_abroad;
        }

        public void setMarriage_cnt(String value){
            this.marriage_cnt = value;
        }
        public String getMarriage_cnt(){
            return this.marriage_cnt;
        }

        public void setBackgd(Integer value){
            this.backgd = value;
        }
        public Integer getBackgd(){
            return this.backgd;
        }

        public void setNative_cnt(String value){
            this.native_cnt = value;
        }
        public String getNative_cnt(){
            return this.native_cnt;
        }

        public void setHeight(Integer value){
            this.height = value;
        }
        public Integer getHeight(){
            return this.height;
        }

        public void setInterest(String value){
            this.interest = value;
        }
        public String getInterest(){
            return this.interest;
        }

        public void setDoc_id_cnt(String value){
            this.doc_id_cnt = value;
        }
        public String getDoc_id_cnt(){
            return this.doc_id_cnt;
        }

        public void setGender(Integer value){
            this.gender = value;
        }
        public Integer getGender(){
            return this.gender;
        }

        public void setTraining_cnt(String value){
            this.training_cnt = value;
        }
        public String getTraining_cnt(){
            return this.training_cnt;
        }

        public void setHighest_shool(String value){
            this.highest_shool = value;
        }
        public String getHighest_shool(){
            return this.highest_shool;
        }

        public void setFst_job_date_cnt(String value){
            this.fst_job_date_cnt = value;
        }
        public String getFst_job_date_cnt(){
            return this.fst_job_date_cnt;
        }

        public void setCertification_cnt(String value){
            this.certification_cnt = value;
        }
        public String getCertification_cnt(){
            return this.certification_cnt;
        }

        public static class TCertification{
            private String cer_name;
            private String issued;
            private String description;

            public String getCer_name() {
                return cer_name;
            }

            public void setCer_name(String cer_name) {
                this.cer_name = cer_name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIssued() {
                return issued;
            }

            public void setIssued(String issued) {
                this.issued = issued;
            }
        }
        private	List<TCertification>	certification = new ArrayList<TCertification>();
        public void setCertification(List<TCertification> value){
            this.certification = value;
        }
        public List<TCertification> getCertification(){
            return this.certification;
        }

        public void setNickname(String value){
            this.nickname = value;
        }
        public String getNickname(){
            return this.nickname;
        }

        public void setRecommendation(Object value){
            this.recommendation = value;
        }
        public Object getRecommendation(){
            return this.recommendation;
        }

        public void setFst_job_date(Long value){
            this.fst_job_date = value;
        }
        public Long getFst_job_date(){
            return this.fst_job_date;
        }

        public void setPostcode(String value){
            this.postcode = value;
        }
        public String getPostcode(){
            return this.postcode;
        }

        public void setEducation_cnt(String value){
            this.education_cnt = value;
        }
        public String getEducation_cnt(){
            return this.education_cnt;
        }

        public void setNation(String value){
            this.nation = value;
        }
        public String getNation(){
            return this.nation;
        }

        public void setEmail(String value){
            this.email = value;
        }
        public String getEmail(){
            return this.email;
        }

        public void setLogo_path(String value){
            this.logo_path = value;
        }
        public String getLogo_path(){
            return this.logo_path;
        }

        public void setDoc_id(Integer value){
            this.doc_id = value;
        }
        public Integer getDoc_id(){
            return this.doc_id;
        }

        public void setStudy_abroad(Integer value){
            this.study_abroad = value;
        }
        public Integer getStudy_abroad(){
            return this.study_abroad;
        }

        public void setGender_cnt(String value){
            this.gender_cnt = value;
        }
        public String getGender_cnt(){
            return this.gender_cnt;
        }

        public void setBirthday(Long value){
            this.birthday = value;
        }
        public Long getBirthday(){
            return this.birthday;
        }

        public void setHighest_speciality(String value){
            this.highest_speciality = value;
        }
        public String getHighest_speciality(){
            return this.highest_speciality;
        }

        public void setWeight(Integer value){
            this.weight = value;
        }
        public Integer getWeight(){
            return this.weight;
        }

        public void setChild_count(Integer value){
            this.child_count = value;
        }
        public Integer getChild_count(){
            return this.child_count;
        }

        public void setManage_skill(Integer value){
            this.manage_skill = value;
        }
        public Integer getManage_skill(){
            return this.manage_skill;
        }

        public void setRname(String value){
            this.rname = value;
        }
        public String getRname(){
            return this.rname;
        }

        public void setNationality_cnt(String value){
            this.nationality_cnt = value;
        }
        public String getNationality_cnt(){
            return this.nationality_cnt;
        }

        public void setBackgd_cnt(String value){
            this.backgd_cnt = value;
        }
        public String getBackgd_cnt(){
            return this.backgd_cnt;
        }

        public void setHukou_cnt(String value){
            this.hukou_cnt = value;
        }
        public String getHukou_cnt(){
            return this.hukou_cnt;
        }

        public void setExt_message(String value){
            this.ext_message = value;
        }
        public String getExt_message(){
            return this.ext_message;
        }


        public static class TTraining{

            private	String	agency;
            private	String	cer_name;
            private	String	course;
            private	String	address;
            private	String	description;
            private	Long	stime;	/*1433088000*/
            private	Long	etime;	/*1451577600*/
            private	String	time_cnt;	/*2015.06 - 2016.01*/

            public void setAgency(String value){
                this.agency = value;
            }
            public String getAgency(){
                return this.agency;
            }

            public void setCer_name(String value){
                this.cer_name = value;
            }
            public String getCer_name(){
                return this.cer_name;
            }

            public void setCourse(String value){
                this.course = value;
            }
            public String getCourse(){
                return this.course;
            }

            public void setAddress(String value){
                this.address = value;
            }
            public String getAddress(){
                return this.address;
            }

            public void setStime(Long value){
                this.stime = value;
            }
            public Long getStime(){
                return this.stime;
            }

            public void setEtime(Long value){
                this.etime = value;
            }
            public Long getEtime(){
                return this.etime;
            }

            public void setDescription(String value){
                this.description = value;
            }
            public String getDescription(){
                return this.description;
            }

            public void setTime_cnt(String value){
                this.time_cnt = value;
            }
            public String getTime_cnt(){
                return this.time_cnt;
            }

        }
        private	List<TTraining>	trainings = new ArrayList<TTraining>();
        public void setTrainings(List<TTraining> value){
            this.trainings = value;
        }
        public List<TTraining> getTrainings(){
            return this.trainings;
        }


        public void setMobilephone(String value){
            this.mobilephone = value;
        }
        public String getMobilephone(){
            return this.mobilephone;
        }

        public void setWeixin(String value){
            this.weixin = value;
        }
        public String getWeixin(){
            return this.weixin;
        }

        public void setHands_on_exp(String value){
            this.hands_on_exp = value;
        }
        public String getHands_on_exp(){
            return this.hands_on_exp;
        }

        public void setName(String value){
            this.name = value;
        }
        public String getName(){
            return this.name;
        }

        public void setIs_english(Integer value){
            this.is_english = value;
        }
        public Integer getIs_english(){
            return this.is_english;
        }

        public void setDriving_lic(String value){
            this.driving_lic = value;
        }
        public String getDriving_lic(){
            return this.driving_lic;
        }

        public void setQq(String value){
            this.qq = value;
        }
        public String getQq(){
            return this.qq;
        }

        public void setNative_place(Integer value){
            this.native_place = value;
        }
        public Integer getNative_place(){
            return this.native_place;
        }

        public void setSkills_cnt(String value){
            this.skills_cnt = value;
        }
        public String getSkills_cnt(){
            return this.skills_cnt;
        }

        public void setLst_job_date(Long value){
            this.lst_job_date = value;
        }
        public Long getLst_job_date(){
            return this.lst_job_date;
        }

        public void setWebsite(String value){
            this.website = value;
        }
        public String getWebsite(){
            return this.website;
        }

        public void setCur_city_id(Integer value){
            this.cur_city_id = value;
        }
        public Integer getCur_city_id(){
            return this.cur_city_id;
        }

        public void setNationality(String value){
            this.nationality = value;
        }
        public String getNationality(){
            return this.nationality;
        }

        public void setNation_cnt(String value){
            this.nation_cnt = value;
        }
        public String getNation_cnt(){
            return this.nation_cnt;
        }

        public void setHukou(Integer value){
            this.hukou = value;
        }
        public Integer getHukou(){
            return this.hukou;
        }

        public void setLanguage_cnt(String value){
            this.language_cnt = value;
        }
        public String getLanguage_cnt(){
            return this.language_cnt;
        }

        public void setExt_url(String value){
            this.ext_url = value;
        }
        public String getExt_url(){
            return this.ext_url;
        }

        public void setDoc_id_type(String value){
            this.doc_id_type = value;
        }
        public String getDoc_id_type(){
            return this.doc_id_type;
        }

        public void setAppendix(String value){
            this.appendix = value;
        }
        public String getAppendix(){
            return this.appendix;
        }

        public void setMarriage_stat(Integer value){
            this.marriage_stat = value;
        }
        public Integer getMarriage_stat(){
            return this.marriage_stat;
        }

        public void setLst_job_date_cnt(String value){
            this.lst_job_date_cnt = value;
        }
        public String getLst_job_date_cnt(){
            return this.lst_job_date_cnt;
        }

        public void setTelephone(String value){
            this.telephone = value;
        }
        public String getTelephone(){
            return this.telephone;
        }

        public void setBirthday_cnt(String value){
            this.birthday_cnt = value;
        }
        public String getBirthday_cnt(){
            return this.birthday_cnt;
        }

    }
    public static class TMeta_info{

        public static class TResume_tags{

            private	String	srid;	/*xXQ2HZA1YfJQnK1faTM7Lg*/
            private	String	class_d;	/**/
            private	String	class_c;	/**/
            private	String	class_b;	/**/
            private	String	email;	/*wxp228@21cn.com*/
            private	String	name_mobile;	/*王小鹏+13772519371*/

            public void setSrid(String value){
                this.srid = value;
            }
            public String getSrid(){
                return this.srid;
            }

            public void setClass_d(String value){
                this.class_d = value;
            }
            public String getClass_d(){
                return this.class_d;
            }

            public void setClass_c(String value){
                this.class_c = value;
            }
            public String getClass_c(){
                return this.class_c;
            }

            public void setClass_b(String value){
                this.class_b = value;
            }
            public String getClass_b(){
                return this.class_b;
            }

            public void setEmail(String value){
                this.email = value;
            }
            public String getEmail(){
                return this.email;
            }

            public void setName_mobile(String value){
                this.name_mobile = value;
            }
            public String getName_mobile(){
                return this.name_mobile;
            }

        }
        private	String	org_name;	/**/
        private	String	biz_code;	/*tp-id*/
        private	TResume_tags	resume_tags = new TResume_tags();	/*TResume_tags*/
        private	Integer	file_size;	/*7613*/
        private	String	file_path;	/*group1/M02/FE/9D/wKgGMVeYUAeAJ2w1AAAdvd_FW3s46.html*/
        private	Integer	parsed_count;	/*8*/
        private	Integer	edus;	/*1*/
        private	Integer	sys_ver;	/*2015*/
        private	String	file_type;	/*html*/
        private	Integer	works;	/*2*/
        private	Integer	projects;	/*1*/
        private	String	template_name;	/*ZL991505DC01html*/
        private	Double	rate;	/*128.29*/
        private	String	biz_id;	/**/
        private	Integer	resume_size;	/*1964*/
        private	String	org_file_path;	/**/

        public void setOrg_name(String value){
            this.org_name = value;
        }
        public String getOrg_name(){
            return this.org_name;
        }

        public void setBiz_code(String value){
            this.biz_code = value;
        }
        public String getBiz_code(){
            return this.biz_code;
        }

        public void setResume_tags(TResume_tags value){
            this.resume_tags = value;
        }
        public TResume_tags getResume_tags(){
            return this.resume_tags;
        }

        public void setFile_size(Integer value){
            this.file_size = value;
        }
        public Integer getFile_size(){
            return this.file_size;
        }

        public void setFile_path(String value){
            this.file_path = value;
        }
        public String getFile_path(){
            return this.file_path;
        }

        public void setParsed_count(Integer value){
            this.parsed_count = value;
        }
        public Integer getParsed_count(){
            return this.parsed_count;
        }

        public void setEdus(Integer value){
            this.edus = value;
        }
        public Integer getEdus(){
            return this.edus;
        }

        public void setSys_ver(Integer value){
            this.sys_ver = value;
        }
        public Integer getSys_ver(){
            return this.sys_ver;
        }

        public void setFile_type(String value){
            this.file_type = value;
        }
        public String getFile_type(){
            return this.file_type;
        }

        public void setWorks(Integer value){
            this.works = value;
        }
        public Integer getWorks(){
            return this.works;
        }

        public void setProjects(Integer value){
            this.projects = value;
        }
        public Integer getProjects(){
            return this.projects;
        }

        public void setTemplate_name(String value){
            this.template_name = value;
        }
        public String getTemplate_name(){
            return this.template_name;
        }

        public void setRate(Double value){
            this.rate = value;
        }
        public Double getRate(){
            return this.rate;
        }

        public void setBiz_id(String value){
            this.biz_id = value;
        }
        public String getBiz_id(){
            return this.biz_id;
        }

        public void setResume_size(Integer value){
            this.resume_size = value;
        }
        public Integer getResume_size(){
            return this.resume_size;
        }

        public void setOrg_file_path(String value){
            this.org_file_path = value;
        }
        public String getOrg_file_path(){
            return this.org_file_path;
        }

    }
    public static class TDeliver_info{

        public static class TJob_intension{

            private	List<String>	city_cnt = new ArrayList<String>();	/*List<String>*/
            public void setCity_cnt(List<String> value){
                this.city_cnt = value;
            }
            public List<String> getCity_cnt(){
                return this.city_cnt;
            }

            private	List<String>	trade = new ArrayList<String>();	/*List<String>*/
            public void setTrade(List<String> value){
                this.trade = value;
            }
            public List<String> getTrade(){
                return this.trade;
            }

            private	List<String>	trade_cnt = new ArrayList<String>();	/*List<String>*/
            public void setTrade_cnt(List<String> value){
                this.trade_cnt = value;
            }
            public List<String> getTrade_cnt(){
                return this.trade_cnt;
            }

            private	List<String>	job = new ArrayList<String>();	/*List<String>*/
            public void setJob(List<String> value){
                this.job = value;
            }
            public List<String> getJob(){
                return this.job;
            }

            private	List<String>	city = new ArrayList<String>();	/*List<String>*/
            public void setCity(List<String> value){
                this.city = value;
            }
            public List<String> getCity(){
                return this.city;
            }

            private	List<String>	job_cnt = new ArrayList<String>();	/*List<String>*/
            public void setJob_cnt(List<String> value){
                this.job_cnt = value;
            }
            public List<String> getJob_cnt(){
                return this.job_cnt;
            }

            private	String	hope_salary_cnt;	/*4001-6000元/月*/
            private	String	cur_salary_cnt;	/**/
            private	String	hope_salary_to;	/*6000*/
            private	String	hope_salary_from;	/*4001*/
            private	String	cur_salary_to;	/**/
            private	String	job_nature;	/*1*/
            private	String	company_type;	/**/
            private	String	state;	/*1*/
            private	String	arrival_time;	/**/
            private	String	cur_salary_from;	/**/
            private	String	arrival_cnt;	/**/
            private	String	company_type_cnt;	/**/
            private	String	state_cnt;	/*我目前处于离职状态，可立即上岗*/
            private	String	job_nature_cnt;	/*全职*/

            public void setHope_salary_cnt(String value){
                this.hope_salary_cnt = value;
            }
            public String getHope_salary_cnt(){
                return this.hope_salary_cnt;
            }

            public void setCur_salary_cnt(String value){
                this.cur_salary_cnt = value;
            }
            public String getCur_salary_cnt(){
                return this.cur_salary_cnt;
            }

            public void setHope_salary_to(String value){
                this.hope_salary_to = value;
            }
            public String getHope_salary_to(){
                return this.hope_salary_to;
            }

            public void setHope_salary_from(String value){
                this.hope_salary_from = value;
            }
            public String getHope_salary_from(){
                return this.hope_salary_from;
            }

            public void setCur_salary_to(String value){
                this.cur_salary_to = value;
            }
            public String getCur_salary_to(){
                return this.cur_salary_to;
            }

            public void setJob_nature(String value){
                this.job_nature = value;
            }
            public String getJob_nature(){
                return this.job_nature;
            }

            public void setCompany_type(String value){
                this.company_type = value;
            }
            public String getCompany_type(){
                return this.company_type;
            }

            public void setState(String value){
                this.state = value;
            }
            public String getState(){
                return this.state;
            }

            public void setArrival_time(String value){
                this.arrival_time = value;
            }
            public String getArrival_time(){
                return this.arrival_time;
            }

            public void setCur_salary_from(String value){
                this.cur_salary_from = value;
            }
            public String getCur_salary_from(){
                return this.cur_salary_from;
            }

            public void setArrival_cnt(String value){
                this.arrival_cnt = value;
            }
            public String getArrival_cnt(){
                return this.arrival_cnt;
            }

            public void setCompany_type_cnt(String value){
                this.company_type_cnt = value;
            }
            public String getCompany_type_cnt(){
                return this.company_type_cnt;
            }

            public void setState_cnt(String value){
                this.state_cnt = value;
            }
            public String getState_cnt(){
                return this.state_cnt;
            }

            public void setJob_nature_cnt(String value){
                this.job_nature_cnt = value;
            }
            public String getJob_nature_cnt(){
                return this.job_nature_cnt;
            }

        }
        public static class TPosition_info{

            private	String	position;	/**/
            private	Integer	location;	/*0*/
            private	String	company;	/**/
            private	String	location_cnt;	/**/

            public void setPosition(String value){
                this.position = value;
            }
            public String getPosition(){
                return this.position;
            }

            public void setLocation(Integer value){
                this.location = value;
            }
            public Integer getLocation(){
                return this.location;
            }

            public void setCompany(String value){
                this.company = value;
            }
            public String getCompany(){
                return this.company;
            }

            public void setLocation_cnt(String value){
                this.location_cnt = value;
            }
            public String getLocation_cnt(){
                return this.location_cnt;
            }

        }
        private	Long	refresh_time;	/*0*/
        private	TJob_intension	job_intension = new TJob_intension();	/*TJob_intension*/
        private	String	srid;	/*xXQ2HZA1YfJQnK1faTM7Lg*/
        private	Integer	sid;	/*1*/
        private	TPosition_info	position_info = new TPosition_info();	/*TPosition_info*/
        private	Long	appoint_time;	/*1404057600*/
        private	Long	update_time;	/*1469030400*/
        private	Long	disp_time;	/*0*/
        private	String	recruit_path;	/**/

        public void setRefresh_time(Long value){
            this.refresh_time = value;
        }
        public Long getRefresh_time(){
            return this.refresh_time;
        }

        public void setJob_intension(TJob_intension value){
            this.job_intension = value;
        }
        public TJob_intension getJob_intension(){
            return this.job_intension;
        }

        public void setSrid(String value){
            this.srid = value;
        }
        public String getSrid(){
            return this.srid;
        }

        public void setSid(Integer value){
            this.sid = value;
        }
        public Integer getSid(){
            return this.sid;
        }

        public void setPosition_info(TPosition_info value){
            this.position_info = value;
        }
        public TPosition_info getPosition_info(){
            return this.position_info;
        }

        public void setAppoint_time(Long value){
            this.appoint_time = value;
        }
        public Long getAppoint_time(){
            return this.appoint_time;
        }

        public void setUpdate_time(Long value){
            this.update_time = value;
        }
        public Long getUpdate_time(){
            return this.update_time;
        }

        public void setDisp_time(Long value){
            this.disp_time = value;
        }
        public Long getDisp_time(){
            return this.disp_time;
        }

        public void setRecruit_path(String value){
            this.recruit_path = value;
        }
        public String getRecruit_path(){
            return this.recruit_path;
        }

    }
    private	List<TDeliver_info>	deliver_info = new ArrayList<TDeliver_info>();	/*List<TDeliver_info>*/
    public void setDeliver_info(List<TDeliver_info> value){
        this.deliver_info = value;
    }
    public List<TDeliver_info> getDeliver_info(){
        return this.deliver_info;
    }

    private	TResume_info	resume_info = new TResume_info();	/*TResume_info*/
    private	TMeta_info	meta_info = new TMeta_info();	/*TMeta_info*/
    private	Integer	person_id;	/*0*/
    private	String	server_ip;	/*192.168.6.96*/
    private	Long	create_time;	/*1469599757*/
    private	Long	version_date;	/*1469030400*/
    private	Long	last_date;	/*1469030400*/
    private	String	resume_id;	/**/

    public void setResume_info(TResume_info value){
        this.resume_info = value;
    }
    public TResume_info getResume_info(){
        return this.resume_info;
    }

    public void setMeta_info(TMeta_info value){
        this.meta_info = value;
    }
    public TMeta_info getMeta_info(){
        return this.meta_info;
    }

    public void setPerson_id(Integer value){
        this.person_id = value;
    }
    public Integer getPerson_id(){
        return this.person_id;
    }

    public void setServer_ip(String value){
        this.server_ip = value;
    }
    public String getServer_ip(){
        return this.server_ip;
    }

    public void setCreate_time(Long value){
        this.create_time = value;
    }
    public Long getCreate_time(){
        return this.create_time;
    }

    public void setVersion_date(Long value){
        this.version_date = value;
    }
    public Long getVersion_date(){
        return this.version_date;
    }

    public void setLast_date(Long value){
        this.last_date = value;
    }
    public Long getLast_date(){
        return this.last_date;
    }

    public void setResume_id(String value){
        this.resume_id = value;
    }
    public String getResume_id(){
        return this.resume_id;
    }

}