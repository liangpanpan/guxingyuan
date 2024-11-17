package com.panpan;

import com.panpan.util.CommandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        System.out.println("Hello world!");

        String commandFile = "/home/test/init-tpa-data";

        if (args != null && args.length > 0 && args[0] != null && !"".equals(args[0])) {
            commandFile = args[0];
        }

        logger.info("exec file:{}", commandFile);

        List<String> strings = CommandUtil.execOne(commandFile);

        if (strings == null) {
            logger.info("exec result is null:{}", (strings == null));
        } else {
            strings.stream().forEach(System.out::println);
        }
    }
}