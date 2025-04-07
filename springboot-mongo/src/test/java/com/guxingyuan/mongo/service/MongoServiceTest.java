package com.guxingyuan.mongo.service;

import com.alibaba.fastjson.JSONObject;
import com.guxingyuan.mongo.MongoApplication;
import com.guxingyuan.mongo.entity.ExtInfo;
import com.guxingyuan.mongo.entity.UserInfo;
import com.mongodb.client.*;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 如果使用junit进行测试，则必须添加@RunWith(SpringRunner.class)，如果使用springboot-test，则不需要
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/4/3       create this file
 * </pre>
 */
@Slf4j
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class MongoServiceTest {

    @Resource
    private MongoDatabase mongoDatabase;

    @Test
    public void listCollectionNames() {
        // 2. 查看所有数据库
        MongoIterable<String> listDatabaseNames = mongoDatabase.listCollectionNames();
        log.info("查询到的集合名称: ");
        for (String dbName : listDatabaseNames) {
            log.info(dbName);
        }
    }

    @Test
    public void handleCollection() {
        // 创建集合
        mongoDatabase.createCollection("c_demo1");
        System.out.println("创建集合: c_demo1");

        // 获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("c_demo1");
        String collectionName = collection.getNamespace().getCollectionName();
        System.out.println("切换到集合: " + collectionName);

        // 查看所有集合
        MongoIterable<String> listCollectionNames = mongoDatabase.listCollectionNames();
        System.out.println("集合列表: ");
        for (String collectionName1 : listCollectionNames) {
            System.out.println(collectionName1);
        }

        // 删除当前集合
        collection.drop();
        System.out.println("删除当前集合: " + collectionName);
    }

    @Test
    public void insertDocument() {
        // 获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("c_demo2");

        // 插入单个文档
        Document document = new Document("name", "John")
                .append("age", 30)
                .append("city", "New York");
        collection.insertOne(document);
        System.out.println("单个文档插入成功");

        // 插入多个文档
        Document document1 = new Document("name", "Alice").append("age", 25).append("city", "Los Angeles");
        Document document2 = new Document("name", "Bob").append("age", 35).append("city", "Chicago");
        List<Document> documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);
        collection.insertMany(documents);
        System.out.println("多个文档插入成功");

        // 嵌套文档插入
        ArrayList<Document> docList = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(80);
        scores.add(90);
        scores.add(75);
        Document doc1 = new Document("name", "zhangsan").append("info", new Document("age", 30).append("address", "yunnan").append("height", 176));
        Document doc2 = new Document("name", "lisi").append("info", new Document("age", 31).append("address", "guizhou").append("height", 175));
        Document doc3 = new Document("name", "wangwu").append("scores", scores);
        docList.add(doc1);
        docList.add(doc2);
        docList.add(doc3);
        InsertManyResult insertManyResult = collection.insertMany(docList);
        System.out.println("嵌套文档插入成功：" + insertManyResult.wasAcknowledged());
    }

    @Test
    public void insertOneByObject() {
        // 获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("c_demo2");

        ExtInfo extInfo = ExtInfo.builder()
                .address("莲花区")
                .age(36)
                .height(175).build();

        UserInfo userInfo = UserInfo.builder()
                .name("name001")
                .age(30)
                .city("保定")
                .info(extInfo)
                .scores(Arrays.asList(10, 24, 54, 68, 90))
                .build();

        InsertOneResult insertOneResult = collection.insertOne(new Document(JSONObject.parseObject(JSONObject.toJSONString(userInfo))));
        log.info("id:{}", insertOneResult.getInsertedId());
    }

    @Test
    public void find() {
        String collectionName = "c_demo2";
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

        FindIterable<Document> documents = collection.find();

        for (Document document : documents) {
            log.info("document info:{}", document.toString());

            String jsonStr = document.toJson();
            UserInfo userInfo = JSONObject.parseObject(jsonStr, UserInfo.class);

            log.info("user info:{}", userInfo);
        }
    }

    @Test
    public void queryDocuments() {
        // 获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("c_demo2");

        // 查询集合c_demo2中age等于 30 的所有文档
        Document filter = new Document("age", 30);
        FindIterable<Document> findIterable = collection.find(filter);
        System.out.println("查询集合c_demo2中age等于 30 的所有文档:");
        for (Document doc : findIterable) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中的所有文档
        System.out.println("查询所有文档:");
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中age大于等于 25 的所有文档
        Document filter1 = new Document("age", new Document("$gte", 25));
        FindIterable<Document> findIterable1 = collection.find(filter1);
        System.out.println("查询集合c_demo2中age大于等于 25 的所有文档:");
        for (Document doc : findIterable1) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中age大于 22 且小于 30 的所有文档
        Document filter2 = new Document("age", new Document("$gt", 22).append("$lt", 30));
        FindIterable<Document> findIterable2 = collection.find(filter2);
        System.out.println("查询集合c_demo2中age大于 22 且小于 30 的所有文档:");
        for (Document doc : findIterable2) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中 scores 数组中至少有一个元素大于 85 的文档。
        Document filter3 = new Document("scores", new Document("$elemMatch", new Document("$gt", 85)));
        FindIterable<Document> findIterable3 = collection.find(filter3);
        System.out.println("查询集合c_demo2中 scores 数组中至少有一个元素大于 85 的文档:");
        for (Document doc : findIterable3) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中 scores 数组长度为 3 的文档。
        Document filter4 = new Document("scores", new Document("$size", 3));
        FindIterable<Document> findIterable4 = collection.find(filter4);
        System.out.println("查询集合c_demo2中 scores 数组长度为 3 的文档:");
        for (Document doc : findIterable4) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中 name 和 city 字段，排除 _id 字段。
        Document filter5 = new Document("name", 1).append("city", 1).append("_id", 0);
        FindIterable<Document> findIterable5 = collection.find().projection(filter5);
        System.out.println("查询集合c_demo2中 name 和 city 字段，排除 _id 字段:");
        for (Document doc : findIterable5) {
            System.out.println(doc.toJson());
        }

        // 排除 _id 和 name 字段。
        Document filter6 = new Document("_id", 0).append("name", 0);
        FindIterable<Document> findIterable6 = collection.find().projection(filter6);
        System.out.println("排除 _id 和 name 字段:");
        for (Document doc : findIterable6) {
            System.out.println(doc.toJson());
        }

        // 返回第一个匹配的文档，而不是所有匹配的文档。
        Document filter7 = new Document("name", "new_name");
        Document doc7 = collection.find(filter7).first();
        System.out.println("返回第一个匹配的文档:");
        if (doc7 != null) {
            System.out.println(doc7.toJson());
        }

        // 查询集合c_demo2的前两个文档。
        FindIterable<Document> findIterable7 = collection.find().limit(2);
        System.out.println("查询集合c_demo2的前两个文档:");
        for (Document doc : findIterable7) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中跳过前3个文档的所有文档。
        FindIterable<Document> findIterable8 = collection.find().skip(3);
        System.out.println("查询集合c_demo2中跳过前3个文档的所有文档:");
        for (Document doc : findIterable8) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中的所有文档，并按age进行升序排序。
        FindIterable<Document> findIterable9 = collection.find().sort(new Document("age", 1));
        System.out.println("查询集合c_demo2中的所有文档，并按age进行升序排序:");
        for (Document doc : findIterable9) {
            System.out.println(doc.toJson());
        }

        // 查询集合c_demo2中name为zhangsan，且嵌套文档info中age为 30 和address为yunnan的第一个匹配的文档。
        Document filter10 = new Document("name", "zhangsan").append("info.age", 30).append("info.address", "yunnan");
        Document doc10 = collection.find(filter10).first();
        System.out.println("查询集合c_demo2中name为zhangsan，且嵌套文档info中age为 30 和address为yunnan的第一个匹配的文档:");
        if (doc10 != null) {
            System.out.println(doc10.toJson());
        }

        // 查询集合c_demo2中city的值以Los开头的所有文档。
        Document filter11 = new Document("city", new Document("$regex", "^Los"));
        FindIterable<Document> findIterable11 = collection.find(filter11);
        System.out.println("查询集合c_demo2中city的值以Los开头的所有文档:");
        for (Document doc : findIterable11) {
            System.out.println(doc.toJson());
        }
    }

    @Test
    public void testUpdate() {
        // 获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("c_demo2");

        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            log.info("{}", document.toJson());
        }

        Document queryDocument = new Document().append("name", "name001");
        Bson updates = Updates.combine(Updates.set("city", "shijiazhuang"), Updates.set("age", 40));

        UpdateOptions updateOptions = new UpdateOptions().upsert(true);

        UpdateResult updateResult = collection.updateMany(queryDocument, updates, updateOptions);
        System.out.println("Modified document count:" + updateResult.getModifiedCount());

        System.out.println("Upserted is:" + updateResult.getUpsertedId());


    }




    @Test
    public void aggregateDocuments() {
        // 获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("c_demo4");

        // 插入多个文档
        ArrayList<Document> arrayList = new ArrayList<>();
        arrayList.add(new Document().append("category", "A").append("value", 10));
        arrayList.add(new Document().append("category", "B").append("value", 20));
        arrayList.add(new Document().append("category", "A").append("value", 30));
        arrayList.add(new Document().append("category", "B").append("value", 40));
        arrayList.add(new Document().append("category", "C").append("value", 50));
        arrayList.add(new Document().append("category", "A").append("value", 10));
        arrayList.add(new Document().append("category", "B").append("value", 20));
        arrayList.add(new Document().append("category", "A").append("value", 30));
        arrayList.add(new Document().append("category", "B").append("value", 40));
        arrayList.add(new Document().append("category", "C").append("value", 50));
        InsertManyResult insertManyResult = collection.insertMany(arrayList);
        System.out.println("插入文档数量: " + insertManyResult.getInsertedIds().size());
        System.out.println("插入文档结果：" + insertManyResult.wasAcknowledged());

        // 使用 $group 计算每个类别的总和
        List<Document> pipeline = new ArrayList<>();
        pipeline.add(new Document("$group", new Document("_id", "$category").append("total", new Document("$sum", "$value"))));
        AggregateIterable<Document> aggregateIterable = collection.aggregate(pipeline);
        System.out.println("计算每个类别值的总和:");
        for (Document doc : aggregateIterable) {
            System.out.println(doc.toJson());
        }

        // 使用 $match 过滤出类别为A的文档。
        List<Document> pipeline1 = new ArrayList<>();
        pipeline1.add(new Document("$match", new Document("category", "A")));
        AggregateIterable<Document> aggregateIterable1 = collection.aggregate(pipeline1);
        System.out.println("过滤出类别为A的文档:");
        for (Document doc : aggregateIterable1) {
            System.out.println(doc.toJson());
        }

        // 使用 $sort 根据value的值对文档降序排序。
        List<Document> pipeline2 = new ArrayList<>();
        pipeline2.add(new Document("$sort", new Document("value", -1)));
        AggregateIterable<Document> aggregateIterable2 = collection.aggregate(pipeline2);
        System.out.println("根据value的值对文档降序排序:");
        for (Document doc : aggregateIterable2) {
            System.out.println(doc.toJson());
        }

        // 使用 $project 修改文档结构，不显示_id。
        List<Document> pipeline3 = new ArrayList<>();
        pipeline3.add(new Document("$project", new Document("_id", 0)));
        AggregateIterable<Document> aggregateIterable3 = collection.aggregate(pipeline3);
        System.out.println("使用 $project 修改文档结构，不显示_id:");
        for (Document doc : aggregateIterable3) {
            System.out.println(doc.toJson());
        }

        // 使用 $skip 和 $limit 分页查询。
        List<Document> pipeline4 = new ArrayList<>();
        pipeline4.add(new Document("$skip", 2));
        pipeline4.add(new Document("$limit", 2));
        AggregateIterable<Document> aggregateIterable4 = collection.aggregate(pipeline4);
        System.out.println("使用 $skip 和 $limit 分页查询:");
        for (Document doc : aggregateIterable4) {
            System.out.println(doc.toJson());
        }

        // 使用 $avg 计算平均值，_id 为 null 表示不分组，即把整个集合视为一个单一的组。
        List<Document> pipeline5 = new ArrayList<>();
        pipeline5.add(new Document("$group", new Document("_id", null).append("average", new Document("$avg", "$value"))));
        AggregateIterable<Document> aggregateIterable5 = collection.aggregate(pipeline5);
        System.out.println("使用 $avg 计算平均值:");
        for (Document doc : aggregateIterable5) {
            System.out.println(doc.toJson());
        }

        // 使用 $min 和 $max 找出最小值和最大值。
        List<Document> pipeline6 = new ArrayList<>();
        pipeline6.add(new Document("$group", new Document("_id", null).append("min", new Document("$min", "$value")).append("max", new Document("$max", "$value"))));
        AggregateIterable<Document> aggregateIterable6 = collection.aggregate(pipeline6);
        System.out.println("使用 $min 和 $max 找出最小值和最大值:");
        for (Document doc : aggregateIterable6) {
            System.out.println(doc.toJson());
        }

        // 使用 $push 收集数组中的元素。
        List<Document> pipeline7 = new ArrayList<>();
        pipeline7.add(new Document("$group", new Document("_id", "$category").append("values", new Document("$push", "$value"))));
        AggregateIterable<Document> aggregateIterable7 = collection.aggregate(pipeline7);
        System.out.println("使用 $push 收集数组中的元素:");
        for (Document doc : aggregateIterable7) {
            System.out.println(doc.toJson());
        }

        // 使用 $first 和 $last 返回数组中的第一个和最后一个元素。
        List<Document> pipeline8 = new ArrayList<>();
        pipeline8.add(new Document("$group", new Document("_id", "$category").append("first", new Document("$first", "$value")).append("last", new Document("$last", "$value"))));
        AggregateIterable<Document> aggregateIterable8 = collection.aggregate(pipeline8);
        System.out.println("使用 $first 和 $last 返回数组中的第一个和最后一个元素:");
        for (Document doc : aggregateIterable8) {
            System.out.println(doc.toJson());
        }
    }


    @Test
    public void delete() {
        String collectionName = "c_demo2";
        MongoCollection<Document> demoCollection = mongoDatabase.getCollection(collectionName);

        // 查询
        FindIterable<Document> documents = demoCollection.find();

        log.info("删除前全部数据：");
        for (Document document : documents) {
            log.info(document.toJson());
        }

        log.info("删除集合:{}所有数据", collectionName);
        demoCollection.drop();

        log.info("删除后数据：");
        for (Document document : demoCollection.find()) {
            log.info(document.toJson());
        }
    }


}
