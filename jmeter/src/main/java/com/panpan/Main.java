package com.panpan;

import org.apache.logging.log4j.core.util.UuidUtil;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        String s = UUID.randomUUID().toString().replaceAll("-", "");

        System.out.println(s);

    }
}