package com.pp.sort.langzhi;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class LinkNode<T> {

    private T value;
    private LinkNode<T> parentNode;
    private LinkNode<T> leftNode;
    private LinkNode<T> rightNode;


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LinkNode<T> getParentNode() {
        return parentNode;
    }

    public void setParentNode(LinkNode<T> parentNode) {
        this.parentNode = parentNode;
    }

    public LinkNode<T> getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(LinkNode<T> leftNode) {
        this.leftNode = leftNode;
    }

    public LinkNode<T> getRightNode() {
        return rightNode;
    }

    public void setRightNode(LinkNode<T> rightNode) {
        this.rightNode = rightNode;
    }

    public LinkNode<Character> genFullTree(int deep) {
        if (deep < 1) {
            return null;
        }
        LinkNode<Character> linkNode = new LinkNode<>();
        int value = 1;
        linkNode.setValue(numFormatChar(value));
        setChildrenNode(linkNode, deep - 1, value);
        return linkNode;

    }

    private void setChildrenNode(LinkNode<Character> linkNode, int deep, int parValue) {
        if (deep < 1) {
            return;
        }
        LinkNode<Character> leftNode = new LinkNode<>();
        linkNode.setLeftNode(leftNode);
        int leftChildrenValue = 2 * parValue;
        leftNode.setValue(numFormatChar(leftChildrenValue));
        setChildrenNode(leftNode, deep - 1, leftChildrenValue);
        leftNode.setParentNode(linkNode);

        LinkNode<Character> rightNode = new LinkNode<>();
        linkNode.setRightNode(rightNode);
        rightNode.setValue(numFormatChar(leftChildrenValue + 1));
        setChildrenNode(rightNode, deep - 1, leftChildrenValue + 1);
        rightNode.setParentNode(linkNode);
    }

    public char numFormatChar(int value) {
        int aNum = 'A';
        int zNum = 'Z';
        char result = (char) ((value - 1) % (zNum - aNum + 1) + aNum);
        return result;
    }


    public void traverseLinkNode(LinkNode linkNode) {
        if (linkNode == null) {
            return;
        }

        LinkNode firstRootNode = traverseChild(linkNode, null);
        traverseParent(linkNode, linkNode.getParentNode(), firstRootNode);
    }

    public LinkNode traverseChild(LinkNode linkNode, LinkNode firstRootNode) {
        if (linkNode == null) {
            return null;
        }
        if (firstRootNode == null && linkNode.getValue().toString().equals("A")) {
            firstRootNode = linkNode;
        }
        System.out.println(linkNode.getValue());
        traverseChild(linkNode.getLeftNode(), firstRootNode);
        traverseChild(linkNode.getRightNode(), firstRootNode);
        return firstRootNode;
    }

    public void traverseParent(LinkNode preLinkNode, LinkNode linkNode, LinkNode firstRootNode) {
        if (linkNode == null) return;

        if (linkNode.getValue().toString().equals("A")) {
            if (firstRootNode == null) {
                firstRootNode = linkNode;
            } else if (firstRootNode == linkNode) {
                return;
            }
        }
        System.out.println(linkNode.getValue());
        if (preLinkNode == linkNode.getLeftNode()) {
            traverseChild(linkNode.getRightNode(), firstRootNode);
        } else if (preLinkNode == linkNode.getRightNode()) {
            traverseChild(linkNode.getLeftNode(), firstRootNode);
        } else {
            traverseChild(linkNode.getRightNode(), firstRootNode);
            traverseChild(linkNode.getLeftNode(), firstRootNode);

        }
        traverseParent(linkNode, linkNode.getParentNode(), firstRootNode);
    }


    public static void main(String[] args) {
        LinkNode linkNode = new LinkNode<Character>();
        LinkNode root1 = linkNode.genFullTree(4);
        LinkNode root2 = linkNode.genFullTree(4);
        LinkNode root3 = linkNode.genFullTree(4);

        root1.setParentNode(root2);
        root2.setParentNode(root3);
        root3.setParentNode(root1);

        linkNode.traverseLinkNode(root2.getLeftNode().getRightNode());

    }


}
