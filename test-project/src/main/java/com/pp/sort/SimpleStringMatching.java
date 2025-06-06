package com.pp.sort;

/**
 * 简单字符串匹配算法
 *
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class SimpleStringMatching {

    /**
     * 字符串匹配方法
     * @param text
     * @param pattern
     * @return
     */
    public static int simpleStringMatch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        for (int i = 0; i <= n - m; i++) {
            int j;
            // 尝试匹配模式字符串
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            // 匹配成功，返回匹配的首位置
            if (j == m) {
                return i;
            }
        }
        // 匹配失败，返回-1
        return -1;
    }


    public static void main(String[] args) {
        String text = "abcdefghijklmnopqrstuvwxyz";
        String pattern = "ijkl";
        int index = simpleStringMatch(text, pattern);
        if (index != -1) {
            System.out.println("匹配成功，匹配位置为：" + index);
        } else {
            System.out.println("匹配失败");
        }
    }




}
