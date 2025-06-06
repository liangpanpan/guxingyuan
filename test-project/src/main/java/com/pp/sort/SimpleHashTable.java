package com.pp.sort;

import java.util.LinkedList;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/5       create this file
 * </pre>
 */
public class SimpleHashTable<K, V> {

    private static class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    private LinkedList<HashNode<K, V>>[] table;
    private int capacity = 10;
    private int size = 0;

    public SimpleHashTable(int capacity) {
        this.capacity = capacity;
        table = new LinkedList[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (HashNode<K, V> node : table[index]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        // 添加新的节点
        table[index].add(new HashNode<>(key, value));
        size++;
    }


    public V get(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }

        for (HashNode<K, V> node : table[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        for (HashNode<K, V> node : table[index]) {
            if (node.key.equals(key)) {
                table[index].remove(node);
                size--;
                return node.value;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }


    public static void main(String[] args) {
        SimpleHashTable<String, Integer> hashTable = new SimpleHashTable<>(10);
        hashTable.put("apple", 5);
        hashTable.put("banana", 3);
        hashTable.put("orange", 7);

        System.out.println("Size: " + hashTable.size());
        System.out.println("Value for 'apple': " + hashTable.get("apple"));
        System.out.println("Value for 'banana': " + hashTable.get("banana"));
        System.out.println("Value for 'orange': " + hashTable.get("orange"));
        System.out.println("Value for 'grape': " + hashTable.get("grape"));
        System.out.println("Removed value for 'banana': " + hashTable.remove("banana"));
    }








}
