package com.pp.sort;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class StackUsingQueues {
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;

    public StackUsingQueues() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    public void push(int x) {
        // 始终在非空队列中进行push
        if (!queue1.isEmpty()) {
            queue1.offer(x);
        } else {
            queue2.offer(x);
        }
    }

    public int pop() {
        // 始终在非空队列中进行pop
        if (!queue1.isEmpty()) {
            while (queue1.size() > 1) {
                queue2.offer(queue1.poll());
            }
            return queue1.poll();
        } else {
            while (queue2.size() > 1) {
                queue1.offer(queue2.poll());
            }
            return queue2.poll();
        }
    }

    public int peek() {
        int topElement = 0;
        if (!queue1.isEmpty()) {
            while (!queue1.isEmpty()) {
                topElement = queue1.poll();
                queue2.offer(topElement);
            }
        } else {
            while (!queue2.isEmpty()) {
                topElement = queue2.poll();
                queue1.offer(topElement);
            }
        }
        return topElement;
    }


    public static void main(String[] args) {
        StackUsingQueues stack = new StackUsingQueues();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        System.out.println("Top element is :" + stack.peek());
        System.out.println("Popped element is :" + stack.pop());
        System.out.println("Top element after pop is :" + stack.peek());

    }

}
