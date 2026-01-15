package com.pp.list;


import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2021/11/18       create this file
 * </pre>
 */
public class TestList {

    public static void main(String[] args) {

        ParentList sonList = new SonList();
        sonList.setTestList(new ArrayList<>());

        Person person = new Person();
        person.setName("name");

        sonList.getTestList().add(person);


        sonList.getTestList().stream().peek(so -> so.setName("111")).collect(Collectors.toList());


        sonList.getTestList().forEach(System.out::println);




    }

}
