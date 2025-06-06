package com.pp.sort;

/**
 * 判断平衡二叉树
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class BalanceBinaryTree {

    public static boolean isBalance(TreeNode root) {
        return getHeight(root) != -1;
    }

    public static int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = getHeight(root.right);
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println("Is the tree balanced? " + isBalance(root));


        TreeNode unBalancedRoot = new TreeNode(1);
        unBalancedRoot.left = new TreeNode(2);
        unBalancedRoot.left.left = new TreeNode(3);

        System.out.println("Is the tree balanced? " + isBalance(unBalancedRoot));



        unBalancedRoot = new TreeNode(1);
        unBalancedRoot.left = new TreeNode(2);
        unBalancedRoot.right = new TreeNode(3);
        unBalancedRoot.left.left = new TreeNode(4);
        unBalancedRoot.left.right = new TreeNode(5);

        unBalancedRoot.left.left.left = new TreeNode(6);
        unBalancedRoot.left.left.right = new TreeNode(7);

        System.out.println("Is the tree balanced? " + isBalance(unBalancedRoot));


    }


}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
        this.val = value;
        this.left = null;
        this.right = null;
    }
}

