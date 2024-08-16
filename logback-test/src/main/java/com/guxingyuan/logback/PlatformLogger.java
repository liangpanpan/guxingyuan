package com.guxingyuan.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 *
 * 该类是对Logger进行封装，封装后，打印log的时候，行号会是该类具体的info行号，为了打印具体类的行号，
 * 需要使用org.slf4j.spi.LocationAwareLogger来实现，具体可以看该类接口的描述。使用方式如下：
 * private static final String FQCN = PlatformLogger.class.getName();
 *
 * public void info(String msg) {
 *     if (this.logger instanceof LocationAwareLogger) {
 *         ((LocationAwareLogger) this.logger).log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(msg),
 *                 null, null);
 *         return;
 *     }
 *     this.logger.info(msg);
 * }
 *
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2023/9/1       create this file
 * </pre>
 */
public class PlatformLogger {

    private Logger logger;

    private static final String FQCN = PlatformLogger.class.getName();

    private PlatformLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public static PlatformLogger getLogger(Class<?> clazz) {
        return new PlatformLogger(clazz);
    }

    public void info(String msg) {
        if (this.logger instanceof LocationAwareLogger) {
            ((LocationAwareLogger) this.logger).log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(msg),
                    null, null);
            return;
        }
        this.logger.info(msg);
    }


    public void info(String format, Object... objects) {
        if (this.logger instanceof LocationAwareLogger) {
            ((LocationAwareLogger) this.logger).log(null, FQCN, LocationAwareLogger.INFO_INT, format,
                    objects, null);
            return;
        }

        this.logger.info(format, objects);
    }


}
