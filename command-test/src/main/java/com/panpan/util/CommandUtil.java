package com.panpan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Description: 调用命令主入口
 * @Author: zhaoaolin
 * @Date: 2021/6/25 17:08
 */
public class CommandUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    /**
     * 开发环境
     */
    private static final LocalCommandUtil commandSer;

    private static final String charset_floweye = "GB2312";

    private static final String charset_freeBSD = "UTF-8";

    public static void main(String[] args) {
        String property = System.getProperty("os.name");
        System.out.println(property);
    }

    /**
     * 测试,生产环境
     */
//    private static final CommandService commandSer = new LocalCommandUtil();

    static {
        commandSer = new LocalCommandUtil();
        logger.info("Init LocalCommand Success");

    }

    /**
     * 命令只执行一次
     *
     * @param command
     * @return
     */
    public static List<String> execOne(String command) {
        try {
            return commandSer.exec(command, getCharset(command));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 执行命令，失败后会执行多次
     *
     * @param command
     * @return
     */
    public static List<String> exec(String command) {
        try {
            return commandSer.exec(command, getCharset(command));
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 执行命令 下载pcap
     *
     * @param command
     * @return
     */
    public static List<String> execPcap(String command) {
        try {
            return LocalCommandUtil.execPcap(command, getCharset(command));
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 通过命令获得编码格式
     *
     * @param command
     * @return
     */
    private static String getCharset(String command) {
        if (command.startsWith("floweye")) {
            return charset_floweye;
        } else {
            return charset_freeBSD;
        }
    }

}
