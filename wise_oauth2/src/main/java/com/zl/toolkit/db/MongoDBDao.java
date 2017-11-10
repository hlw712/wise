package com.zl.toolkit.db;

import com.mongodb.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hlw on 2017/8/2.
 */
public class MongoDBDao extends AbstractBaseDao {

    private static final String IP = "127.0.0.1";
    private static final String BB_NAME = "dhwl";
    private static final String COLLECTION_NAME = "rainfall_2016";

    /**
     * 连接数据库，拿到集合firstCollection
     *
     * @return
     */
    private DBCollection getDBCollection(String collectionName) {
        MongoClient mongoClient = new MongoClient(IP);
        DB db = mongoClient.getDB(BB_NAME);
        return db.getCollection(collectionName);
    }

    /**
     * 添加
     *
     * @param object
     * @return
     */
    public int inset(BasicDBObject object) {

        DBCollection collection = this.getDBCollection(COLLECTION_NAME);
        WriteResult writeResult = collection.insert(object);
        return writeResult.getN();
    }

    /**
     * 新建
     *
     * @param rainfalls
     * @return
     */
    public int inset(List<BasicDBObject> rainfalls) {

        DBCollection collection = this.getDBCollection(COLLECTION_NAME);
        List<DBObject> objs = new LinkedList<DBObject>();
        for (BasicDBObject basicDBObject : rainfalls){
            objs.add(basicDBObject);
        }
        WriteResult writeResult = collection.insert(objs);
        return writeResult.getN();
    }

}
