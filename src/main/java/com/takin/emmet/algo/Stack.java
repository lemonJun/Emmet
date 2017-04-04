package com.takin.emmet.algo;

import java.util.Comparator;

import com.takin.emmet.algo.collections.LinkedStack;

/**
 * Created by shenshijun on 15/2/4.
 */
public interface Stack<T> {
    void push(T ele);

    T pop();

    int size();

    public default boolean isEmpty() {
        return size() <= 0;
    }

    T head();

    public default Stack<T> sortStack(Comparator<? super T> comparator) {
        LinkedStack<T> result = new LinkedStack<>();
        while (!isEmpty()) {
            T cur_ele = pop();
            while (!result.isEmpty() && comparator.compare(result.head(), cur_ele) > 0) {
                push(result.pop());
            }
            result.push(cur_ele);
        }
        return result;
    }
}
