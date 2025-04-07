package com.guxingyuan.mongo.service;

import com.guxingyuan.mongo.MongoApplication;
import com.guxingyuan.mongo.entity.ExtInfo;
import com.guxingyuan.mongo.entity.UserInfo;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/4/3       create this file
 * </pre>
 */
@Slf4j
@SpringBootTest(classes = MongoApplication.class)
public class MongoTemplateTest {

    @Resource
    private MongoTemplate mongoTemplate;

    //添加
    @Test
    public void createUser() {
        UserInfo user = new UserInfo();
//        user.setId(UUID.randomUUID().toString());
        user.setAge(20);
        user.setName("test");
        user.setEmail("4932201@qq.com");

        ExtInfo extInfo = ExtInfo.builder().age(30).height(175).address("长安区").build();
        user.setInfo(extInfo);

        user.setScores(Arrays.asList(1, 2,4,5,7,8));

        UserInfo user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    //查询所有
    @Test
    public void findUser() {
        List<UserInfo> userList = mongoTemplate.findAll(UserInfo.class);
        System.out.println(userList);
    }

    //根据id查询
    @Test
    public void getById() {
        UserInfo user = mongoTemplate.findById("67ee5e09246a7b38c26bbaa7", UserInfo.class);
        System.out.println(user);
    }

    //条件查询
    @Test
    public void findUserList() {
        Query query = new Query(Criteria
                .where("name").is("test")
                .and("age").is(20));
        List<UserInfo> userList = mongoTemplate.find(query, UserInfo.class);
        System.out.println(userList);
    }

    //模糊查询
    @Test
    public void findUsersLikeName() {
        String name = "est";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<UserInfo> userList = mongoTemplate.find(query, UserInfo.class);
        System.out.println(userList);
    }

    //分页查询
    @Test
    public void findUsersPage() {
        String name = "est";
        int pageNo = 1;
        int pageSize = 10;

        Query query = new Query();
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        int totalCount = (int) mongoTemplate.count(query, UserInfo.class);
        List<UserInfo> userList = mongoTemplate.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), UserInfo.class);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("list", userList);
        pageMap.put("totalCount", totalCount);
        System.out.println(pageMap);
    }

    //修改
    @Test
    public void updateUser() {
        UserInfo user = mongoTemplate.findById("67f32cb15e7b1556f24ccc42", UserInfo.class);

        if (user == null) {
            log.info("query userInfo is null");
            return;
        }

        user.setName("test_1");
        user.setAge(25);
        user.setEmail("493220990@qq.com");
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("age", user.getAge());
        update.set("email", user.getEmail());
        UpdateResult result = mongoTemplate.upsert(query, update, UserInfo.class);
        long count = result.getModifiedCount();
        System.out.println(count);
    }

    //删除操作
    @Test
    public void delete() {
        Query query =
                new Query(Criteria.where("_id").is("67f32cb15e7b1556f24ccc42"));
        DeleteResult result = mongoTemplate.remove(query, UserInfo.class);
        long count = result.getDeletedCount();
        System.out.println(count);
    }

}
