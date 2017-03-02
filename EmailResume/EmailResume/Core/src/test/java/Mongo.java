import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.emailresume.MongoHelper;
import org.emailresume.common.Utils;
import org.emailresume.htmlparser.LongValueProcessor;
import org.emailresume.htmlparser.ParserFactory;
import org.emailresume.htmlparser.ResumeWrapper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Mongo {

    private static final Logger logger = Logger.getLogger(Mongo.class);
    public static void main(String [] args) throws Exception{
        //MongoHelper.clearCollection("emaildb", "email_resume");
        //MongoHelper.createCollection("emaildb", "temp");
        //MongoHelper.deleteCollection("emaildb", "tmp");
        //MongoHelper.insert("{ \"key\": { \"$numberLong\": \"4611686018427387904\" }}");
        //DFSHelper.uploadFile("");

        String url = "jdbc:mysql://192.168.6.80:3306/email_process?"
                + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url);
        String sql = String.format("select objid from email_20160908");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            String objid = rs.getString(1);
            if(objid == null || objid.equals("")){
                continue;
            }
            MongoHelper.deleteWithObjid("emaildb", "email_resume", objid);

        }

//        File folder = new File("G:/resume");
//        Long time = new Date().getTime() / 1000;
//        String[] fileNameList = folder.list();
//        String url = "jdbc:mysql://192.168.6.65:3306/emailresume?"
//                + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection conn = DriverManager.getConnection(url);
//
//        int i = 0, j = 0;
//        for(String name : fileNameList){
//            i++;
//            String filePath = folder.getPath() + "/" + name;
//            ResumeWrapper wrapper = new ResumeWrapper();
//            String s = name.substring(0, 1);
//            int channel = Integer.parseInt(s);
//            System.out.println("开始解析 " + name);
//            boolean b = ParserFactory.getParser(channel).parse(Utils.readFileByBytes(filePath), wrapper);
//            System.out.println("解析完毕" + b);
//            if(b){
//                j++;
//                JsonConfig jsonConfig = new JsonConfig();
//                jsonConfig.registerJsonValueProcessor(Long.class, new LongValueProcessor());
//                wrapper.setLast_date(time);
//                wrapper.setVersion_date(time);
//                ResumeWrapper.TDeliver_info info = wrapper.getDeliver_info().get(0);
//                info.setDisp_time(time);
//                info.setUpdate_time(time);
//                JSONObject o = JSONObject.fromObject(wrapper);
//                String json = org.emailresume.common.Utils.jsonFormatter(o.toString());
//                System.out.println("开始插入mongo" + b);
//                String objid = MongoHelper.insert(json, "test2", "ceshi2");
//                System.out.println("插入完毕" + b);
//                Statement stmt = conn.createStatement();
//                String sql = String.format("insert into checker (filename, objid) values ('%s', '%s')", name, objid);
//                stmt.executeUpdate(sql);
//                stmt.close();
//
//            }
//            File f = new File(filePath);
//            f.delete();
//            System.out.println(i + "  " + j);
//        }
    }
}
