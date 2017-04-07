package com.takin.emmet.algo.collections;

import java.util.Arrays;
import java.util.Iterator;

import com.google.common.base.Preconditions;
import com.takin.emmet.algo.SearchTree;
import com.takin.emmet.algo.lang.Tuple2;

/**
 * 在B树中支持重复元素比较复杂，因此暂时不要把重复元素插入进去
 * Created by shenshijun on 15/2/5.
 */
public class BTree<T extends Comparable<? super T>> implements SearchTree<T> {
    private static final int DEFAULT_DEGREE = 4;
    private int tree_degree;
    private BTreeNode<T> root;
    private int tree_size;

    private BTree(int degree) {
        Preconditions.checkArgument(degree >= 2);
        tree_degree = degree;
        tree_size = 0;
        root = new BTreeNode<>(tree_degree);
        root.setLeaf(true);
    }

    public static <T extends Comparable<? super T>> BTree<T> degreeOf(int degree) {
        return new BTree<>(degree);
    }

    public static <T extends Comparable<? super T>> BTree<T> defaultDegree() {
        return new BTree<>(DEFAULT_DEGREE);
    }

    @Override
    public void add(T ele) {
        Preconditions.checkNotNull(ele);

        BTreeNode<T> leaf_node = root;
        while (!leaf_node.isLeaf()) {
            BTreeNode<T> tmp = leaf_node.searchChild(ele);

            if (leaf_node.isValueFull()) {
                splitNode(leaf_node, null);
            }
            leaf_node = tmp;
        }

        if (leaf_node.isValueFull()) {
            splitNode(leaf_node, ele);
        } else {
            leaf_node.appendValue(ele);
        }
        tree_size++;
    }

    private Tuple2<BTreeNode<T>, BTreeNode<T>> splitNode(BTreeNode<T> node, T insert_ele) {
        Preconditions.checkNotNull(node);

        BTreeNode<T> right_node = new BTreeNode<>(tree_degree);
        BTreeNode<T> parent_node = node.getParent();
        if (node == root) {
            parent_node = new BTreeNode<>(tree_degree);
            root = parent_node;
        }
        right_node.setParent(parent_node);
        node.setParent(parent_node);

        int mid_index = parent_node.appendValue(node.getValue(node.size() / 2));
        parent_node.setChild(mid_index - 1, node);
        parent_node.setChild(mid_index + 1, right_node);
        node.splitNode(right_node);

        if (insert_ele != null) {
            if (parent_node.getValue(mid_index).compareTo(insert_ele) > 0) {
                node.appendValue(insert_ele);
            } else {
                right_node.appendValue(insert_ele);
            }
        }

        return new Tuple2<>(node, right_node);
    }

    @Override
    public int size() {
        return tree_size;
    }

    @Override
    public Iterator<T> preIterator() {
        return null;
    }

    @Override
    public Iterator<T> postIterator() {
        return null;
    }

    @Override
    public void delete(T ele) {

    }

    @Override
    public T successor(T value) {
        return null;
    }

    @Override
    public T predecessor(T value) {
        return null;
    }

    @Override
    public T max() {
        return null;
    }

    @Override
    public T min() {
        return null;
    }

    @Override
    public boolean contains(T ele) {
        for (BTreeNode<T> cur_node = root; cur_node != null;) {
            if (cur_node.contains(ele)) {
                return true;
            } else {
                cur_node = cur_node.searchChild(ele);
            }
        }
        return false;
    }

    @Override
    public boolean isBalance() {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private static class BTreeNode<T extends Comparable<? super T>> {
        private final int DEGREE;
        private final int HALF_CAPACITY;
        private final int CAPACITY;
        //奇数位置上存储子节点，偶数位置上存储关键字。
        private Object[] values;
        private BTreeNode<T> parent;
        private int value_index = -1;
        private boolean isLeaf;

        public BTreeNode(int degree) {
            this.DEGREE = degree;
            HALF_CAPACITY = 2 * DEGREE - 1;
            CAPACITY = 4 * DEGREE - 1;
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public void setLeaf(boolean isLeaf) {
            this.isLeaf = isLeaf;
        }

        public BTreeNode<T> getParent() {
            return parent;
        }

        public void setParent(BTreeNode<T> parent) {
            this.parent = parent;
        }

        private void extend(int expect) {
            if (expect >= HALF_CAPACITY) {
                Object[] new_values = new Object[CAPACITY];
                if (values != null) {
                    System.arraycopy(values, 0, new_values, 0, values.length);
                }
                values = new_values;
            } else if (values == null) {
                values = new Object[HALF_CAPACITY];
            }
        }

        private void fullLeaf(int index) {
            if (values[index] == null && !isLeaf()) {
                BTreeNode<T> new_child = new BTreeNode<>(DEGREE);
                new_child.setParent(this);
                new_child.setLeaf(true);
                values[index] = new_child;
            }
        }

        @SuppressWarnings("unchecked")
        public BTreeNode<T> getChild(int index) {
            Preconditions.checkPositionIndex(index, CAPACITY);
            Preconditions.checkArgument(index % 2 == 0, "wrong index");

            extend(index);
            return (BTreeNode<T>) values[index];
        }

        @SuppressWarnings("unchecked")
        public T getValue(int index) {
            Preconditions.checkPositionIndex(index, CAPACITY);
            Preconditions.checkArgument(index % 2 == 1, "wrong index");

            extend(index);
            return (T) values[index];
        }

        public int capacity() {
            extend(1);
            return values.length;
        }

        public int size() {
            return (value_index + 2);
        }

        public boolean isValueFull() {
            return capacity() == CAPACITY && value_index == CAPACITY - 2;
        }

        public boolean isValueEmpty() {
            return value_index < 0;
        }

        /**
         * 按照排序的顺序把元素插入数组中
         *
         * @param value
         * @return 插入的位置，这样好确定子节点的位置
         */
        public int appendValue(T value) {
            if (isValueFull())
                return -1;
            extend(value_index + 2);

            int insert_index = value_index + 2;
            for (int i = value_index; i >= 1 && getValue(i).compareTo(value) >= 0; i -= 2, insert_index -= 2) {
                if (value_index == i) {
                    values[i + 3] = values[i + 1];
                }
                values[i + 2] = values[i];
                values[i + 1] = values[i - 1];
            }
            values[insert_index] = value;
            value_index += 2;

            return insert_index;
        }

        public void setChild(int index, BTreeNode<T> child) {
            Preconditions.checkPositionIndex(index, CAPACITY);
            Preconditions.checkArgument(index % 2 == 0, "wrong index");
            extend(index);

            values[index] = child;
        }

        public T popValue() {
            T result = getValue(value_index);
            values[value_index] = null;
            value_index -= 2;
            return result;
        }

        public BTreeNode<T> deleteChild(int index) {
            BTreeNode<T> result = getChild(index);
            setChild(index, null);
            return result;
        }

        /**
         * 使用修改过的二分查找来提高查找性能。
         * 基本原理就是查找第一个比给定元素大的元素
         *
         * @param value
         * @return
         */
        public BTreeNode<T> searchChild(T value) {
            Preconditions.checkNotNull(value);

            //节点为空，返回第一个子节点
            if (isValueEmpty()) {
                fullLeaf(0);
                return getChild(0);
            } else if (getValue(size() - 2).compareTo(value) < 0) {
                //如果非空并且比最后一个节点大
                fullLeaf(size() - 1);
                return getChild(size() - 1);
            }

            for (int start = 1, end = size() - 2; start <= end;) {
                int mid = (start + end) / 2;
                if (mid % 2 == 0) {
                    mid--;
                }

                int com_res = getValue(mid).compareTo(value);

                if (com_res == 0) {
                    throw new IllegalArgumentException("dump ele");
                } else if (com_res > 0) {
                    if (mid - 2 > 0 && getValue(mid - 2).compareTo(value) >= 0) {
                        end = mid - 2;
                    } else {
                        fullLeaf(mid - 1);
                        return getChild(mid - 1);
                    }
                } else {
                    start = mid + 2;
                }

            }
            return null;
        }

        /**
         * 把一半元素移动到新到节点中
         *
         * @param other
         */
        public void splitNode(BTreeNode<T> other) {
            Preconditions.checkNotNull(other);
            other.extend(HALF_CAPACITY);
            System.arraycopy(values, CAPACITY / 2 + 1, other.values, 0, CAPACITY / 2);
            Arrays.fill(values, CAPACITY / 2, CAPACITY, null);
            value_index = CAPACITY / 2 - 2;
            other.value_index = CAPACITY / 2 - 2;
            other.setLeaf(isLeaf());
            for (int i = 0; i < other.size(); i += 2) {
                if (other.getChild(i) != null) {
                    other.getChild(i).setParent(other);
                }
            }
        }

        @Override
        public String toString() {
            com.takin.emmet.algo.string.StringBuilder sb = new com.takin.emmet.algo.string.StringBuilder("[");
            for (int i = 1; i < size(); i += 2) {
                sb.append(getValue(i));
                if (i != size() - 2) {
                    sb.append(",");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        public boolean contains(T value) {
            Preconditions.checkNotNull(value);

            for (int start = 1, end = size() - 2; start <= end;) {
                int mid = (start + end) / 2;
                if (mid % 2 == 0) {
                    mid--;
                }
                int com_res = getValue(mid).compareTo(value);
                if (com_res == 0) {
                    return true;
                } else if (com_res > 0) {
                    end = mid - 2;
                } else {
                    start = mid + 2;
                }
            }
            return false;
        }
    }
}
