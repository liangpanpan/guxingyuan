package com.pp.sort;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/28       create this file
 * </pre>
 */
public class UniqueElements {

    public static void main(String[] args) {
        int[] num = {1, 1, 2, 2, 3, 4, 4};
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        // 计算每个数字出现的次数
        for (int n : num) {
            frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
        }
        // 找出只出现一次的数字
        System.out.println("只出现一次的数字有：");
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
            }
        }


        int x = 4;
        System.out.println("value is" + ((x > 4) ? 99.9 : 9));
    }
}
