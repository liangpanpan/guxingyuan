package com.pp.sort.cycle;

/**
 * 检测一个单链表中是否有环，
 * 就是用两个指针，一个指针每次移动一个节点，一个指针每次移动两个节点，如果两个指针相遇，说明有环。
 * 如果两个指针不相遇，说明没有环。或者第二个指针最后到达链表末尾时为null，说明没有环。
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/6       create this file
 * </pre>
 */
public class LinedListCycleDetection {

    public static boolean hasCycle(listNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        // 慢指针初始化为头节点
        listNode slow = head;
        // 快指针初始化为头节点的下一个节点
        listNode fast = head;

        // 遍历链表
        int i = 0;
        while (fast != null && fast.next != null) {
            // 慢指针每次移动一个节点
            slow = slow.next;
            // 快指针每次移动两个节点
            fast = fast.next.next;

            i++;
            // 如果慢指针和快指针相遇，说明有环
            if  (slow == fast) {
                System.out.println("循环" + i + "次查找到环");
                return true;
            }
        }
        // 如果遍历结束，并且没有相遇，说明没有环
        return false;
    }


    public static void main(String[] args) {
        listNode head = new listNode(1);
        head.next = new listNode(2);
        head.next.next = new listNode(3);
        head.next.next.next = new listNode(4);
        head.next.next.next.next = new listNode(5);

        head.next.next.next.next.next = head.next;

        if (hasCycle(head)) {
            System.out.println("The linked list has a cycle.");
        } else {
            System.out.println("The linked list does not have a cycle.");
        }
    }
}


class listNode {
    int val;
    listNode next;

    listNode(int x) {
        val = x;
        next = null;
    }
}

