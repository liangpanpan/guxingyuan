package com.pp.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/7/14       create this file
 * </pre>
 */
public class MapTest {

    @Test
    public void test01() {
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.put("" + i, i);
        }
        System.out.println(map.getOrDefault("1", 0));
        System.out.println(map.getOrDefault("10", 0));

        System.out.println(map.putIfAbsent("10", 11));
        System.out.println(map.putIfAbsent("1", 2));

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }


    @Test
    public void test02() {
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.put("" + i, i);
        }

        // 如果key不在map中，则执行后面的函数，并且把新值放入到map中，否则返回key对应的值
        map.computeIfAbsent("10", k -> 11);

        // 如果key在map中，则执行后面的函数，如果函数返回不为空，则放入到map中，返回为空，则移除map；否则返回null,
        map.computeIfPresent("1", (s, i) -> (i + 1));

        // 用于根据指定键获取该键对应的值，并使用指定的函数对该值进行修改或生成新值，然后将新值存储回 Map 中。
        // 如果函数返回为空，则移除该键对应的值，并返回null；否则返回新值
        map.compute("10", (s, i) -> null);
        printMap(map);
    }


    private void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
