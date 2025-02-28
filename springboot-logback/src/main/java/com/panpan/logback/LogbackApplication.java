package com.panpan.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 测试情况说明：
 * 1. 如果启动脚本有指定配置文件和log文件，则使用指定信息
 *  指定方式为：nohup $JAVA_HOME -jar -Dspring.config.location=./config/application.properties -Dlogging.config=./config/logback.xml $APP_NAME > /dev/null 2>&1 &
 * 2. 如果没有指定，则使用resource中。
 * 3. 如果启动脚本没有指定，但是yml文件中指定了，则使用yml文件中的配置信息。
 * 4. 在yml文件中，可以指定level，如果指定了level，则yml级别比logback.xml中更高
 *
 *
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2021/8/7       create this file
 * </pre>
 */
//@EnableScheduling
@SpringBootApplication
public class LogbackApplication {


    private static final Logger logger = LoggerFactory.getLogger(LogbackApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LogbackApplication.class, args);

        RuntimeException exception = new RuntimeException("异常测试");

        logger.error("保存error", exception);

        logger.info("info test");

        logger.debug("debug test 0011");

        logger.warn("log warn test");

        logger.error("error test");
    }

}
