package com.pp.sort;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class LinkedListReversal {

    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode current = head;
        while (current != null) {
            // 暂存下一个节点
            ListNode next = current.next;
            // 当前节点的next指向前一个节点
            current.next = pre;
            // pre和cur都向后移动一位
            pre = current;
            current = next;
        }
        return pre;
    }

    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println("Original Linked List:");

        printList(head);

        ListNode reversedHead = reverseList(head);
        System.out.println("Reversed Linked List:");
        printList(reversedHead);
    }


}


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

