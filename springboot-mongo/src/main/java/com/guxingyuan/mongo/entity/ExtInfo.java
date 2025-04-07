package com.guxingyuan.mongo.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

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
public class ExtInfo {

    private String address;

    private Integer age;

    private Integer height;

}
