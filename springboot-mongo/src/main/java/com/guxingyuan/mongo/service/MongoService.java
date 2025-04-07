package com.guxingyuan.mongo.service;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/4/3       create this file
 * </pre>
 */
@Service
public class MongoService {

    @Resource
    private MongoDatabase mongoDatabase;


    public void updateOne(String collectionName, Document updateOne, UpdateOptions updateOptions) {
//        mongoDatabase.getCollection(collectionName).updateOne()
    }

    public void updateData(String collectionName) {
//        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
//
//        Document queryDocument = new Document("rid", sicIocsBean.getRid());
//        Document updateDocument = new Document(j);
//        UpdateOptions options = new UpdateOptions();
//        options.upsert(true);
//
//        new ReplaceOneModel<Document>(queryDocument, updateDocument, options);
//
//        ReplaceOneModel<Document> uom = new ReplaceOneModel<>(queryDocument, updateDocument, options);
//
//
//        collection.bulkWrite()

    }


}
