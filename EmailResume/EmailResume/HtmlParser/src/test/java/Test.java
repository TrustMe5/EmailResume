import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.emailresume.common.Utils;
import org.emailresume.htmlparser.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.Iterator;


public class Test {
    public static void main(String[] args){
        String folderPath = "E:/qiaodazhao/resume/liepin_fail";
        File folder = new File(folderPath);
        String[] fileNameList = folder.list();
        for(String filename : fileNameList){
            String filePath = folderPath + "/" +filename;
            ResumeWrapper wrapper = new ResumeWrapper();
            boolean b = ParserFactory.getParser(3).parse(filePath, wrapper);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Long.class, new LongValueProcessor());
            JSONObject o = JSONObject.fromObject(wrapper);
            String json = org.emailresume.common.Utils.jsonFormatter(o.toString());
            System.out.println(json);
//            break;
        }
        //MongoHelper.insert(json);
    }
}
