package org.emailresume;

import java.util.ArrayList;
import java.util.logging.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import org.emailresume.common.*;

public class MongoHelper {
    public static boolean isEnglishResule(String objID)
    {
        Logger log = Logger.getLogger("org.mongodb.driver");
        log.setLevel(Level.OFF);

        boolean b = false;
        String json = new String();
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "192.168.6.97" , 40000 );
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("zhengliangdb");
            MongoCollection<Document> collection = mongoDatabase.getCollection("zhengliang");
            FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(objID)));
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while(mongoCursor.hasNext()){
                Document d = mongoCursor.next();
                Document info = (Document)d.get("resume_info");
                String r = info.get("is_english").toString();
                if(r == "1")
                {
                    b = true;
                }
            }
            mongoCursor.close();
            mongoClient.close();

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }

        return b;
    }

    public static String getResumeJsonWithOBJID(String objID){
        String json = new String();
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "192.168.6.97" , 40000 );
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("zhengliangdb");
            MongoCollection<Document> collection = mongoDatabase.getCollection("newzengliang");
            FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(objID)));
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while(mongoCursor.hasNext()){
                Document d = mongoCursor.next();
                json =  JSON.serialize(d);
            }
            mongoCursor.close();
            mongoClient.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return json;
    }

    public static void moveWithObjid(String dataBase, String collectionName, String objid, String tCollectionName){
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "192.168.6.97" , 40000 );
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBase);
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
            MongoCollection<Document> tCollection = mongoDatabase.getCollection(tCollectionName);
            FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(objid)));
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            if(mongoCursor.hasNext()){
                Document d = mongoCursor.next();
                tCollection.insertOne(d);
                System.out.println(objid);
            }
            mongoCursor.close();
            mongoClient.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public static void deleteWithObjid(String dataBase, String collectionName, String objid){
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "192.168.6.97" , 40000 );
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBase);
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
            FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(objid)));
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            if(mongoCursor.hasNext()){
                collection.deleteOne(mongoCursor.next());
            }
            mongoCursor.close();
            mongoClient.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public static void createCollection(String dateBase, String collectionName){
        MongoClient mongoClient = new MongoClient( "192.168.6.97" , 40000 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dateBase);
        mongoDatabase.createCollection(collectionName);
        mongoClient.close();
    }

    public static void clearCollection(String dateBase, String collectionName){
        MongoClient mongoClient = new MongoClient( "192.168.6.97" , 40000 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dateBase);
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        collection.deleteMany(new Document());
        mongoClient.close();
    }

    public static void deleteCollection(String dataBase, String collectionName){
        MongoClient mongoClient = new MongoClient( "192.168.6.97" , 40000 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        collection.drop();
        mongoClient.close();
    }

    public static String insert(String json, String datebase, String collectionName){
        return insertDocument(Document.parse(json), datebase, collectionName);
    }

    public static String insertDocument(Document doc, String datebase, String collectionName ){
        try {
            MongoClient mongoClient = new MongoClient( "192.168.6.97" , 40000 );
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(datebase);
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
            collection.insertOne(doc);
            mongoClient.close();
            return doc.get("_id").toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }
}
