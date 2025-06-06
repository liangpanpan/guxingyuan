package com.pp.sort;

/**
 * 找出两个链表相交的点
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/6       create this file
 * </pre>
 */
public class LinkedListIntersection {

    public static IntersectListNode getIntersectionNode(IntersectListNode headA, IntersectListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        // 指针p1和p2初始化为链表A和链表B的头结点
        IntersectListNode p1 = headA;
        IntersectListNode p2 = headB;

        while (p1 != p2) {
            // 如果p1遍历到链表A的尾部，则重新定位到链表B的头结点
            if (p1 == null) {
                p1 = headB;
            } else {
                p1 = p1.next;
            }

            // 如果p2遍历到链表B的尾部，则重新定位到链表A的头结点
            if (p2 == null) {
                p2 = headA;
            } else {
                p2 = p2.next;
            }
        }
        return p1;
    }

    public static void main(String[] args) {
        IntersectListNode headA = new IntersectListNode(1);
        headA.next = new IntersectListNode(2);
        headA.next.next = new IntersectListNode(3);
        headA.next.next.next = new IntersectListNode(4);

        IntersectListNode headB = new IntersectListNode(5);
        headB.next = new IntersectListNode(6);
        headB.next.next = new IntersectListNode(7);


        IntersectListNode  intersectionNode = new IntersectListNode(9);
        intersectionNode.next = new IntersectListNode(10);

        headA.next.next.next.next = intersectionNode;
        headB.next.next.next = intersectionNode;


        IntersectListNode result = getIntersectionNode(headA, headB);

        if (result != null ) {
            System.out.println("The instersection node's value is:" + result.val);
        } else {
            System.out.println("The two linked lists do not intersect.");
        }

    }

}


class IntersectListNode {
    int val;
    IntersectListNode next;

    IntersectListNode(int x) {
        val = x;
        next = null;
    }
}
