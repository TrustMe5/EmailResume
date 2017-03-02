package org.emailresume.common;

import org.emailresume.common.*;
import net.mikesu.fastdfs.*;
import java.io.File;
import java.io.FileWriter;

public class DFSHelper {
    static final FastdfsClient fastdfsClient = FastdfsClientFactory.getFastdfsClient();

    public static String uploadFile(String content, String fileName) throws Exception{
        File f = new File(fileName);
        FileWriter writer = new FileWriter(f);
        writer.write(content);
        writer.flush();
        writer.close();
        return uploadFile(f);

    }

    public static String uploadFile(File file) throws Exception{
        String fileId = fastdfsClient.upload(file);
        //System.out.println("fileId:"+fileId);
        String url = fastdfsClient.getUrl(fileId);
        //System.out.println("url:"+url);
        //fastdfsClient.close();
        return url;

    }

    private static String uploadToFastDFS(String url, String fileContent, String fileName){
        return HttpRequest.postFileAsFormTable(url, fileContent, fileName);
    }

}
