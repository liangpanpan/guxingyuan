package com.panpan.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 本地调用命令(测试, 生产环境)
 * @Author: zhaoaolin
 * @Date: 2021/6/25 15:39
 */
public class LocalCommandUtil {

    private static final Logger log = LoggerFactory.getLogger(LocalCommandUtil.class);

    private static final long WAIT_TIMEOUT = 15000;


    public static List<String> execPcap(String command, String charset) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Process process = null;
        processBuilder.redirectErrorStream(true);
        InputStream inputStream = null;
        try {
            String[] finalCommand = new String[]{"/bin/bash", "-c", command};
            processBuilder.command(finalCommand);
            process = processBuilder.start();
            if (null != process.getErrorStream()) {
                // 处理失败流
                log.error("失败信息: 下载pcap异常！");
            }
            // 处理正常流
            inputStream = process.getInputStream();
            List<String> list = IOUtils.readLines(inputStream, charset);
            process.waitFor(WAIT_TIMEOUT, TimeUnit.MILLISECONDS);
            int exitValue = process.exitValue();
            if (0 != exitValue) {
                log.error("执行={" + command + "}, exitValue:" + exitValue);
                return null;
            }
            log.debug("执行={}", command);
            return list;
        } catch (Exception e) {
            log.error("执行={" + command + "}", e);
        } finally {
            try {
                IOUtils.close(inputStream);
            } catch (IOException e) {
                log.error("执行={" + command + "}关闭流失败", e);
            }
            if (process != null) {
                process.destroy();
            }
        }
        return null;
    }

    /**
     * 执行命令
     *
     * @param command
     * @param charset
     * @return
     */
    public List<String> exec(String command, String charset) {
        Process process = null;
        InputStream inputStream = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String[] finalCommand = new String[]{"/bin/bash", "-c", command};
            process = runtime.exec(finalCommand);

            if (null != process.getErrorStream()) {
                // 处理失败流
                printErrorResult(process.getErrorStream(), command);
//                new Thread(new ErrorHandle(process.getErrorStream())).start();
            }

//            printErrorResult(process.getErrorStream());

            // 处理正常流
            inputStream = process.getInputStream();
            List<String> list = IOUtils.readLines(inputStream, charset);
            process.waitFor(WAIT_TIMEOUT, TimeUnit.MILLISECONDS);
            int exitValue = process.exitValue();
            if (0 != exitValue) {
                log.error("执行={" + command + "}, exitValue:" + exitValue);
                return null;
            }
            log.debug("执行={}", command);
            return list;
        } catch (Exception e) {
            log.error("执行={" + command + "}", e);
        } finally {
            try {
                IOUtils.close(inputStream);
            } catch (IOException e) {
                log.error("执行={" + command + "}关闭流失败", e);
            }
            if (process != null) {
                process.destroy();
            }
        }
        return null;
    }

    /**
     * 打印错误信息
     *
     * @param errorStream
     */
    private void printErrorResult(InputStream errorStream, String command) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(errorStream);
            byte[] buffer = new byte[1024];
            StringBuilder errorStr = new StringBuilder();
            while ((bufferedInputStream.read(buffer)) != -1) {
                errorStr.append(new String(buffer, "UTF-8"));
            }
            if (!"".equals(errorStr.toString())) {
                log.error("命令: {}; 失败信息: {}", command, errorStr);
            }

            /*List<String> errorList = new ArrayList<>();
            LineIterator lineIterator = IOUtils.lineIterator(errorStream, Constants.ENCODING);
            while (lineIterator.hasNext()) {
                String s = lineIterator.nextLine();
                errorList.add(s);
            }
//            List<String> list = IOUtils.readLines(errorStream, Constants.ENCODING);
            if (TPAStringUtils.isNotEmpty(errorList)) {
                log.error("失败信息: " + JSON.toJSONString(errorList));
            }*/
        } catch (IOException e) {
            log.error("处理失败流异常", e);
        } finally {
            if (null != errorStream) {
                try {
                    errorStream.close();
                } catch (IOException e) {
                    log.error("关闭流失败", e);
                }
            }
        }
    }

}
