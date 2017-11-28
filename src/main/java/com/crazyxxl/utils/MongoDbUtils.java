package com.crazyxxl.utils;

import com.alibaba.fastjson.JSONObject;
import com.crazyxxl.utils.Dao.MongoCommonDao;

public class MongoDbUtils{
    public static MongoCommonDao getDao(){
        return MongoCommonDao.getMongoDBDaoImplInstance();
    }
    public static JSONObject packageMongoValue(String key,Object value){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key,value);
        return jsonObject;
    }
}
