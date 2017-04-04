package com.takin.emmet.algo;

import java.util.Iterator;
import java.util.function.Consumer;

import com.google.common.base.Function;
import com.takin.emmet.algo.collections.Vector;

/**
 * Created by shenshijun on 15/2/7.
 */
public interface Graph<T> {
    //TODO 实现有向图
    void addEdge(T from, T to);

    Iterator<T> nodes();

    void dfs(Consumer<? super T> func);

    <R> Vector<R> dfs(Function<? super T, ? extends R> func);

    void bfs(Consumer<? super T> func);

    <R> Vector<R> bfs(Function<? super T, ? extends R> func);

    List<T> next(T ele);

    void topologicalSort(Consumer<? super T> func);

    <R> List<R> topologicalSort(Function<? super T, ? extends R> func);

    int size();

    public default boolean isEmpty() {
        return size() <= 0;
    }

    List<T> shortestPath(T from, T to);
}
