package com.pp.chat;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/7       create this file
 * </pre>
 */
public class LetterNumberConverter {

    /**
     * 将数字(1-26)转换为对应的大写字母(A-Z)
     *
     * @param number 要转换的数字(1=A, 2=B, ..., 26=Z)
     * @return 对应的大写字母
     * @throws IllegalArgumentException 如果数字不在1-26范围内
     */
    public static char numberToLetter(int number) {
        if (number < 1 || number > 26) {
            throw new IllegalArgumentException("数字必须在1-26范围内");
        }
        return (char) ('A' + number - 1);
    }

    /**
     * 将大写字母(A-Z)转换为对应的数字(1-26)
     *
     * @param letter 要转换的大写字母
     * @return 对应的数字(1 - 26)
     * @throws IllegalArgumentException 如果字符不是A-Z的大写字母
     */
    public static int letterToNumber(char letter) {
        if (letter < 'A' || letter > 'Z') {
            throw new IllegalArgumentException("字符必须是大写字母A-Z");
        }
        return letter - 'A' + 1;
    }

    public static void main(String[] args) {
        // 测试数字转字母
        System.out.println("数字转字母测试:");
        for (int i = 1; i <= 26; i++) {
            System.out.printf("%2d -> %c%n", i, numberToLetter(i));
        }

        // 测试字母转数字
        System.out.println("\n字母转数字测试:");
        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.printf("%c -> %2d%n", c, letterToNumber(c));
        }

        // 示例用法
        System.out.println("\n示例用法:");
        int num = 15;
        char letter = numberToLetter(num);
        System.out.println(num + " 对应的字母是: " + letter);

        char testLetter = 'M';
        int testNum = letterToNumber(testLetter);
        System.out.println(testLetter + " 对应的数字是: " + testNum);
    }


}
