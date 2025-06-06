package com.pp.sort.graph;

import java.util.*;

/**
 * 实现一个基于图数据结构，并实现深度优先搜索（DFS）和广度优先搜索（BFS）算法。
 * 实现一个级别的图数据结构可以使用邻接表来表示图，
 * 邻接表是一种常用的图表示方法，其中每个节点都有一个列表，存储与相邻的节点。
 *
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/6       create this file
 * </pre>
 */
public class GraphExample {

    private Map<Integer, List<Integer>> adjList; // 邻接表

    public GraphExample() {
        adjList = new HashMap<>();
    }

    public void addEdge(int source, int destination) {
        adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
        adjList.computeIfAbsent(destination, k -> new ArrayList<>()).add(source);
    }

    // 深度优先搜索
    public void dfs(int start) {
        Set<Integer> visited = new HashSet<>();
        dfsUtil(start, visited);
    }

    private void dfsUtil(int vertex, Set<Integer> visited) {
        visited.add(vertex);
        System.out.print(vertex + " ");

        for (int neighbor : adjList.getOrDefault(vertex, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfsUtil(neighbor, visited);
            }
        }
    }


    /**
     * 广度优先搜索
     * 使用队列实现
     * 初始化一个Set用于存储已经访问的节点和一个Queue用于存储待访问的节点。
     * 访问一个节点时，将其加入已访问的节点集合中，并将其添加到队列中。
     * @param start
     */
    public void bfs(int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");

            for (int neighbor : adjList.getOrDefault(vertex, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
    }



    public static void main(String[] args) {
        GraphExample graph = new GraphExample();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        System.out.println("Depth First Search starting from node 0:");
        graph.dfs(0);


        System.out.println("\nBreadth First Search starting from node 0:");
        graph.bfs(0);
    }
}
