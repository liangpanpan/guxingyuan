package com.basic01;

import com.basic01.server.IUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * <pre>
 * @Version 1.0
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2020/9/7       create this file
 * </pre>
 */
public class SpringTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("com/basic01/spring.xml");

        IUserService userService =
                applicationContext.getBean(IUserService.class);
        userService.getUser();


        File file = new File("");
        file.mkdirs();
    }

}
