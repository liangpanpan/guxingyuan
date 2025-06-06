package com.pp.sort;

/**
 * 冒泡排序
 * 两个循环，外层循环控制轮数，内层循环控制比较和交换
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class BubbleSort {

    public static void optimizedBubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true; // 标记发生交换
                }
            }
            if (!swapped) {
                break; // 如果本轮无交换，提前结束
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        optimizedBubbleSort(arr);
        System.out.println("排序后的数组:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        // 输出: 11 12 22 25 34 64 90
    }

}
