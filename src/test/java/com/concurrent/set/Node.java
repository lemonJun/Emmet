package com.concurrent.set;

public class Node<T> {

    T item;
    Node<T> next;
    int key;

    public Node(T item) {
        this.item = item;
        this.key = item.hashCode();
    }

    public Node() {

    }

}
