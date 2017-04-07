package com.takin.emmet.algo.first;

import java.util.ArrayList;
import java.util.List;

/**
 * 可借助于array 或 list 来实现 ，不需定自定义新的数据对象
 * 此数据结构可用于排序   故其构建时需要做很多比较，且根值不回定
 * 取下标i=0为根节点，则其左子节点为2i+1,2i+2
 * 已知一个节点i,则其父节点为 (i-1)/2
 * 
 * 如果说添加元素时是在构建堆，则删除最顶幸元素时，则是在排序
 * 大堆的特点是:父元素大于子元素，但同层级的子元素不一定大于另一元素的子元素，所以在取无素时，不能从两个子的中取出一个做根，
 *  因为这一过程可能会移动整个数组，而采用下移的方式则是比较好的
 * 
 * 第一次对树有一些了解用的就是堆  ，没想到再次写这个代码   还是同样在出问题
 * 
 * 原址排序  时间复杂度为nlogn  但是不稳
 * 
 * @since 
 * @see
 */
public class MaxHeap {

    private static List<Integer> list = new ArrayList<Integer>();

    public static void add(Integer data) {
        list.add(data);
        shiftUp(list.size() - 1);
    }

    /**
     * 第一次写  出错了
     * @param index
     */
    public static void shiftUp(int index) {
        int ivalue = list.get(index);
        while (index > 0) {
            int pindex = (index - 1) / 2;
            int pvalue = list.get(pindex);
            if (ivalue > pvalue) {
                list.set(index, pvalue);
                index = pindex;
            } else {
                break;
            }
        }
        list.set(index, ivalue);
    }

    public static Integer get() {
        if (list.size() < 1) {
            return null;
        }
        int i = list.get(0);
        int l = list.remove(list.size() - 1);
        if (list.isEmpty()) {
            return i;
        }
        list.set(0, l);
        shiftDown(0);
        return i;
    }

    public static void shiftDown(int index) {
        int ivalue = list.get(index);
        int l_index = 2 * index + 1;

        while (l_index < list.size()) {
            int max_index = l_index;
            int max_value = list.get(l_index);
            int r_index = l_index + 1;
            if (r_index < list.size()) {
                if (max_value < list.get(r_index)) {
                    max_index = r_index;
                    max_value = list.get(r_index);
                }
            }

            if (ivalue < max_value) {
                list.set(index, max_value);
                index = max_index;
                l_index = 2 * index + 1;
            } else {
                break;
            }
        }
        list.set(index, ivalue);
    }

    public static void main(String[] args) {
        try {

            MaxHeap mh = new MaxHeap();
            mh.add(3);
            mh.add(7);
            mh.add(10);
            mh.add(1);
            mh.add(5);
            mh.add(21);
            mh.add(6);

            for (int i : list) {
                System.out.print(i + " ");
            }
            System.out.println();

            int size = list.size();
            for (int i = 0; i < size; i++) {
                System.out.print(get() + " ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
