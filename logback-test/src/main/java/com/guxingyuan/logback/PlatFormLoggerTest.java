package com.guxingyuan.logback;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2023/9/1       create this file
 * </pre>
 */
public class PlatFormLoggerTest {

    public static void main(String[] args) {
        PlatformLogger platformLogger = PlatformLogger.getLogger(PlatFormLoggerTest.class);
        platformLogger.info("Hello");
        platformLogger.info("123");
        platformLogger.info("123:{}, {}", "aaa", "bbb");
    }
}
