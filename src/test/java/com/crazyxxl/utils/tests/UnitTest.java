package com.crazyxxl.utils.tests;

import com.crazyxxl.utils.Dao.MongoCommonDao;
import com.crazyxxl.utils.MongoDbUtils;
import org.junit.Before;
import org.junit.Test;

public class UnitTest {
    MongoCommonDao mongoCommonDao = null;
    @Before
    public void Init(){
        mongoCommonDao = MongoDbUtils.getDao();
    }

    @Test
    public void Test1(){
//        Map<String,Object> map = new HashMap<String,Object>();
//        JSONObject jsonObject = JSON.parseObject("{\"$gt\":2}");
//        map.put("level",jsonObject);
//        PageBean<Document> res =  mongoCommonDao.findBypage("testsystem","question",map,2,3);
//        while (res.getContent().iterator().hasNext()){
//            continue;
//        }
    }
}
