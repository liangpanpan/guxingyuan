package com.pp.sort;

import java.util.*;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/6       create this file
 * </pre>
 */
public class GetSecondExample {

    public static Integer getSecondHighest(int[] salaries) {
        if (salaries == null || salaries.length < 2) {
            return null;
        }
        Integer max = null, second = null;
        for (int salary : salaries) {
            if (max == null) {
                max = salary;
            } else if (salary > max) {
                second = max;
                max = salary;
            } else if (salary < max && (second == null || salary > second)) {
                second = salary;
            }
        }
        return second;
    }


    public static Integer getSecondHighest2(int[] salaries) {
        Set<Integer> set = new HashSet<>();
        for (int s : salaries) {
            set.add(s);
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Collections.reverseOrder());

        return list.size() >= 2 ? list.get(1) : null;
    }

    public static Integer getSecondHighest3(int[] salaries) {
        Optional<Integer> first = Arrays.stream(salaries).distinct().boxed().sorted(Comparator.reverseOrder()).skip(1).findFirst();
        return first.orElse(null);
    }

    public static void main(String[] args) {

        int[] salaries = {7000, 8000, 8000, 6000, 9000, 9000};
        Integer second = getSecondHighest(salaries);
        System.out.println("第一种方法：第二高薪水是: " + (second != null ? second : "不存在"));

        second = getSecondHighest2(salaries);
        System.out.println("第二种方法：第二高薪水是: " + (second != null ? second : "不存在"));

        second = getSecondHighest3(salaries);
        System.out.println("第三种方法：第二高薪水是: " + (second != null ? second : "不存在"));

    }


}
