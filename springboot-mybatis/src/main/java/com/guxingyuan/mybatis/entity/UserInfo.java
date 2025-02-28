package com.guxingyuan.mybatis.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/2/24       create this file
 * </pre>
 */
@Data
@ToString
public class UserInfo {

    private Integer id;

    private String userName;

    private String password;

    private String realName;

    private String email;

    private String phone;

    private boolean state;

    private int userRole;

    private Date createTime;

    private boolean isActive;

}
