package com.guxingyuan.json;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/8/16       create this file
 * </pre>
 */
public class JsonFormat {

    public static void main(String[] args) throws IOException {

        // 读取文件
        InputStream inputStream = JsonFormat.class.getResourceAsStream("/NeedFormatJson.json");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder jsonBuilder = new StringBuilder();

        String content = null;
        while ((content = bufferedReader.readLine()) != null) {
            jsonBuilder.append(content);
        }

        bufferedReader.close();
        inputStream.close();

        JSONObject jsonObject = JSONObject.parseObject(jsonBuilder.toString());


        // 对Json进行排序
        List<String> sortedKeys = new ArrayList<>(jsonObject.keySet());

        Collections.sort(sortedKeys);

        // 传参为true,表示按照key放入的顺序进行排序，否则会按照Hash
        JSONObject sortedJson = new JSONObject(true);

        for (String key : sortedKeys) {
            sortedJson.put(key, jsonObject.get(key));
        }

        System.out.println("排序前的Json对象：" + jsonObject.toJSONString());
        System.out.println("排序后的Json对象：" + sortedJson.toJSONString());


    }
}
