package com.crazyxxl.utils.Dao;

import com.crazyxxl.utils.Model.PageBean;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoCommonDao {
    /**
     * 创建mongoClient
     */
    private MongoClient mongoClient = null;

    /**
     * 构造函数，构造数据数据库连接
     */
    private MongoCommonDao() {
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectionsPerHost(50);
        build.threadsAllowedToBlockForConnectionMultiplier(50);
        build.maxWaitTime(1000 * 60 * 2);
        build.connectTimeout(1000 * 60 * 1);
        MongoClientOptions Options = build.build();
        try {
            mongoClient = new MongoClient("127.0.0.1", Options);
        }catch (MongoException e){
            e.printStackTrace();
        }
    }

    /**
     * 单例创建
     */
    private static final MongoCommonDao mongoDBDaoImpl = new MongoCommonDao();

    public static MongoCommonDao getMongoDBDaoImplInstance(){
        return mongoDBDaoImpl;
    }

    /**
     * 按条件查询
     * @param dbName
     * @param collectionName
     * @param filter
     * @return
     */
    public MongoCursor<Document> find(String dbName,String collectionName, Map<String,Object> filter) {
        MongoCollection collection = getCollection(dbName, collectionName);
        BasicDBObject basicDBObject = new BasicDBObject();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            basicDBObject.put(entry.getKey(), entry.getValue());
        }
        return collection.find(basicDBObject).iterator();
    }

    /**
     * 按条件分页查询
     * @param dbName
     * @param collectionName
     * @param filter
     * @param page
     * @param size
     * @return
     */
    public PageBean<Document> findBypage(String dbName,String collectionName, Map<String,Object> filter,Integer page, Integer size){
        MongoCollection collection = getCollection(dbName, collectionName);
        BasicDBObject basicDBObject = new BasicDBObject();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            basicDBObject.put(entry.getKey(), entry.getValue());
        }
        Long total = collection.count(basicDBObject);
        MongoCursor<Document> documents = collection.find(basicDBObject).skip((page-1)*size).limit(size).iterator();
        List<Document> content = new ArrayList<Document>();
        while (documents.hasNext()){
            content.add(documents.next());
        }
        PageBean<Document> pageBean = new PageBean<Document>();
        pageBean.setContent(content);
        pageBean.setPage(page);
        pageBean.setSize(size);
        pageBean.setTotal(total);
        return pageBean;
    }


    /**
     * 获取数据库collection
     * @param dbName
     * @param collectionName
     * @return
     */
    private MongoCollection<Document> getCollection(String dbName, String collectionName) {
        if (null == collectionName || "".equals(collectionName)) {
            return null;
        }
        if (null == dbName || "".equals(dbName)) {
            return null;
        }
        MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collectionName);
        return collection;
    }

}
