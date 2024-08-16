package com.panpan;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/4/10       create this file
 * </pre>
 */
public class Config {

    public static Map<Integer, Integer> threatEventIdToTypeIdMap = new LinkedHashMap<>();


    static {
        threatEventIdToTypeIdMap.put(1, 2);
        threatEventIdToTypeIdMap.put(2, 3);
        threatEventIdToTypeIdMap.put(3, 5);
        threatEventIdToTypeIdMap.put(4, 6);
        threatEventIdToTypeIdMap.put(5, 7);
        threatEventIdToTypeIdMap.put(6, 10);
        threatEventIdToTypeIdMap.put(7, 10);
        threatEventIdToTypeIdMap.put(8, 19);
        threatEventIdToTypeIdMap.put(9, 12);
        threatEventIdToTypeIdMap.put(10, 13);
        threatEventIdToTypeIdMap.put(11, 15);
        threatEventIdToTypeIdMap.put(12, 16);
        threatEventIdToTypeIdMap.put(13, 17);
        threatEventIdToTypeIdMap.put(14, 18);
        threatEventIdToTypeIdMap.put(15, 11);
        threatEventIdToTypeIdMap.put(16, 20);
        threatEventIdToTypeIdMap.put(17, 21);
        threatEventIdToTypeIdMap.put(18, 9);
        threatEventIdToTypeIdMap.put(19, 14);

    }


}
