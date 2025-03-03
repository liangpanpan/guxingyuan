package com.guxingyuan.mybatis.mapper;

import com.guxingyuan.mybatis.MyBatisApplication;
import com.guxingyuan.mybatis.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/2/28       create this file
 * </pre>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisApplication.class)
public class UserInfoMapperTest {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private DataSource dataSource;


    @Test
    public void searchAllUserInfo() {
        List<UserInfo> userInfoList = userInfoMapper.list();

        for (UserInfo userInfo : userInfoList) {
            log.info(userInfo.toString());
        }
    }


    public void testDataSource() {
        String string = dataSource.toString();
    }

}
