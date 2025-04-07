package com.guxingyuan.mongo.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/4/3       create this file
 * </pre>
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document("user_info_test")
public class UserInfo {

//    @Id
    @MongoId(FieldType.OBJECT_ID)
//    @Field("_id")
    private String id;

    private String name;

    private int age;

    private String city;

    private List<Integer> scores;

    private ExtInfo info;

    @Field(name = "E-Mail")
    private String email;

}



