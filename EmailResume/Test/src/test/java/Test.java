import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class Test {

    public static String GetNumFromString(String str) {      //将字符串中的所有数字
        String regEx = "\\D";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()){
            return m.replaceAll("").trim();
//            return m.group();
        }else{
            return "";
        }
    }
    public static void main(String [] args){
        String str = "(ID:9840nihao8439328)";
        System.out.println(str.replaceAll("I",""));
        System.out.print(GetNumFromString(str));
    }
}
