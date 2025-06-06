package com.pp.sort.langzhi;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class LangZhiTree {

    public static void main(String[] args) {

        Node root = new Node();

        for  (int i = 0; i < 5; i++) {






        }




    }


}

class Node {
    Node prev;
    Node next;

    Node parent;
    Node left;
    Node right;

    Node() {
    }

    Node(Node prev, Node next) {
        this.next = next;
        this.prev = prev;
    }

    public Node(Node prev, Node next, Node parent) {
        this.prev = prev;
        this.next = next;
        this.parent = parent;
    }
}
