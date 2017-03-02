package org.emailresume.bean;

import java.util.ArrayList;
import java.util.List;

public class EmailListBean {
    private int error_code = 0;

    private String message = "" ;

    private int is_end = 0;

    private int count = 0;

    private int total = 0;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setError_code(int error_code){
        this.error_code = error_code;
    }
    public int getError_code(){
        return this.error_code;
    }
    public void setMessage (String message ){
        this.message = message ;
    }
    public String getMessage (){
        return this.message ;
    }
    public void setIs_end(int is_end){
        this.is_end = is_end;
    }
    public int getIs_end(){
        return this.is_end;
    }

    static public class Data {
        private String email_id;

        private int email_type;

        private String subject;

        private String content;

        private String email_from;

        private int channel = 0;

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        private List<String> email_to = new ArrayList<String>();

        private Long send_date;

        public String getContent_ext() {
            return content_ext;
        }

        public void setContent_ext(String content_ext) {
            this.content_ext = content_ext;
        }

        private String content_ext;

        public void setEmail_id(String email_id){
            this.email_id = email_id;
        }
        public String getEmail_id(){
            return this.email_id;
        }
        public void setEmail_type(int email_type){
            this.email_type = email_type;
        }
        public int getEmail_type(){
            return this.email_type;
        }
        public void setSubject(String subject){
            this.subject = subject;
        }
        public String getSubject(){
            return this.subject;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setEmail_from(String email_from){
            this.email_from = email_from;
        }
        public String getEmail_from(){
            return this.email_from;
        }
        public void setEmail_to(List<String> email_to){
            this.email_to = email_to;
        }
        public List<String> getEmail_to(){
            return this.email_to;
        }
        public void setSend_date(Long send_date){
            this.send_date = send_date;
        }
        public Long getSend_date(){
            return this.send_date;
        }

    }

    private List<Data> data = new ArrayList<Data>();

    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }
}
