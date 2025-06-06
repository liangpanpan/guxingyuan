package com.pp.sort.lz1;

/**
 * 有双向链表，再更改成树，就类似于数据库的索引树
 *
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class LangZhiTree {

    /**
     * 创建深度的树
     *
     * @param deep
     * @return
     */
    public static Node<Integer> genFullTree(int deep) {
        if (deep < 1) {
            return null;
        }

        Node<Integer> node = new Node<>(1);

        // 补充节点
        setChildrenNode(node, deep - 1);
        return node;
    }

    private static void setChildrenNode(Node<Integer> parent, int deep) {
        if (deep < 1) {
            // 如果节点深度小于1，则返回
            return;
        }

        Node<Integer> leftNode = new Node<>(2 * parent.value);
        Node<Integer> rightNode = new Node<>(2 * parent.value + 1);


        parent.left = leftNode;
        parent.right = rightNode;
        leftNode.parent = parent;
        rightNode.parent = parent;

        leftNode.next = rightNode;
        rightNode.prev = leftNode;

        int index = 1;
        Node<Integer> tmp = rightNode;
        Node<Integer> tmpNext = parent;
        while (tmpNext.next != null) {
            tmp = addOtherRightNode(tmpNext.next, tmp, deep, index++);
            tmpNext = tmpNext.next;
        }

        setChildrenNode(leftNode, deep - 1);
    }

    /**
     * 补充右侧的链表数据
     *
     * @param node
     * @param leftLinkNode
     * @param deep
     * @param index
     */
    private static Node<Integer> addOtherRightNode(Node<Integer> node, Node<Integer> leftLinkNode, int deep, int index) {
        Node<Integer> leftNode = new Node<>(2 * node.value);
        Node<Integer> rightNode = new Node<>(2 * node.value + 1);

        node.left = leftNode;
        node.right = rightNode;

        leftLinkNode.next = leftNode;
        leftNode.prev = leftLinkNode;
        leftNode.next = rightNode;

        rightNode.prev = leftNode;
        return rightNode;
    }

    public static void printTree(Node<Integer> root, int deep) {
        System.out.print("第" + deep + "层数据：");
        Node<Integer> tmp = root;
        System.out.print("   " + tmp.value);
        while (tmp.next != null) {
            tmp = tmp.next;
            System.out.print("   " + tmp.value);
        }
        System.out.println();

        if (root.left == null) {
            return;
        }
        printTree(root.left, deep + 1);
    }


    public static void main(String[] args) {
        Node<Integer> node = genFullTree(5);

        printTree(node, 1);

    }


}

class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;
    Node<T> parent;

    // 双向链表
    Node<T> prev;
    Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}


