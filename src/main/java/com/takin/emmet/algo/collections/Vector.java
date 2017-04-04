package com.takin.emmet.algo.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import com.google.common.base.Preconditions;
import com.takin.emmet.algo.ArrayUtil;
import com.takin.emmet.algo.Collection;
import com.takin.emmet.algo.List;

/**
 * 遵守C++的命名规则，和ArrayList类似
 * Created by shenshijun on 15/2/1.
 */
public class Vector<T> implements List<T> {
    private Object[] _values;
    private int _cur_pointer;
    private static final int DEFAULT_SIZE = 10;

    public Vector(int capacity) {
        Preconditions.checkArgument(capacity >= 0);
        _values = new Object[capacity];
        _cur_pointer = -1;
    }

    public Vector() {
        this(DEFAULT_SIZE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        Preconditions.checkPositionIndex(index, size());
        return (T) _values[index];
    }

    @Override
    public void set(int index, T ele) {
        Preconditions.checkNotNull(ele);
        Preconditions.checkPositionIndex(index, size());
        _values[index] = ele;
    }

    @Override
    public void remove(int index) {
        System.arraycopy(_values, index + 1, _values, index, size() - 1 - index);
        _cur_pointer--;
    }

    public T binarySearch(T ele, Comparator<? super T> comparator) {
        return binarySearch(ele, 0, size() - 1, comparator);
    }

    public T binarySearch(T ele, int from, int to, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(ele);
        Preconditions.checkNotNull(comparator);
        @SuppressWarnings("unchecked")
        int ele_index = Arrays.binarySearch((T[]) _values, from, to, ele, comparator);
        if (ele_index == -1) {
            return null;
        }
        return get(ele_index);
    }

    @Override
    public List<T> partition(T par_ele, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(par_ele);
        Preconditions.checkNotNull(comparator);
        int less_par = 0;
        int equal_par = 0;
        int cur_index = 0;
        while (cur_index < size()) {
            int c = comparator.compare(par_ele, get(cur_index));
            if (c > 0) {
                ArrayUtil.swap(_values, equal_par, cur_index);
                ArrayUtil.swap(_values, less_par, equal_par);
                less_par++;
                equal_par++;
            } else if (c == 0) {
                ArrayUtil.swap(_values, equal_par, cur_index);
                equal_par++;
            }
            cur_index++;
        }
        return this;
    }

    @Override
    public Iterator<T> reverse() {
        return new ListReverseItr(size());
    }

    @Override
    public void add(T ele) {
        ensureCapacity();
        _values[++_cur_pointer] = ele;
    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        return new Vector<>(size);
    }

    @Override
    public int size() {
        return _cur_pointer + 1;
    }

    @Override
    public int capacity() {
        return _values.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListItr(size());
    }

    private void ensureCapacity() {
        if (size() >= capacity()) {
            _values = Arrays.copyOf(_values, extendSize());
        }
    }

    private int extendSize() {
        return (capacity() + 1) * 2;
    }

    public Vector<T> sortThis(Comparator<? super T> comparator) {
        @SuppressWarnings("unchecked")
        T[] new_arr = (T[]) _values;
        ArrayUtil.sort(new_arr, comparator);
        _values = new_arr;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        this.forEach((ele) -> sb.append(ele + ","));
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        @SuppressWarnings("unchecked")
        Vector vector = (Vector) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(_values, vector._values))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return _values != null ? Arrays.hashCode(_values) : 0;
    }

    public void swap(int start, int end) {
        ArrayUtil.swap(_values, start, end);
    }

    private class ListReverseItr implements Iterator<T> {

        int _size;
        int _cur_pointer;

        public ListReverseItr(int size) {
            _size = size;
            _cur_pointer = Vector.this.size();
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify();
            _cur_pointer--;
            return _cur_pointer >= 0;
        }

        @Override
        public T next() {
            checkCurrencyModify();
            return get(_cur_pointer);
        }

        @Override
        public void remove() {
            checkCurrencyModify();
            Vector.this.remove(_cur_pointer++);
            _size--;
        }

        private void checkCurrencyModify() {
            if (_size != Vector.this.size()) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class ListItr implements Iterator<T> {

        int _size;
        int _cur_pointer;

        public ListItr(int size) {
            _size = size;
            _cur_pointer = -1;
        }

        @Override
        public boolean hasNext() {
            this.checkCurrencyModify();
            return ++_cur_pointer < Vector.this.size();
        }

        @Override
        public T next() {
            this.checkCurrencyModify();
            return get(_cur_pointer);
        }

        @Override
        public void remove() {
            this.checkCurrencyModify();
            Vector.this.remove(_cur_pointer--);
            _size--;
        }

        private void checkCurrencyModify() {
            if (_size != Vector.this.size()) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
