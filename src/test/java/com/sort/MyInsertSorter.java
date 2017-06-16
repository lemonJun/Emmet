package com.sort;

/**
 * 数据规模小的时候很有用
 *
 * @author WangYazhou
 * @date  2017年6月16日 下午2:07:56
 * @see 
 * @param <E>
 */
public class MyInsertSorter<E extends Comparable<E>> extends MySort<E> {

    @Override
    public void sort(E[] array, int from, int len) {
        E tmp = null;
        for (int i = from + 1; i < from + len; i++) {
            tmp = array[i];
            int j = i;
            for (; j > from; j--) {
                if (tmp.compareTo(array[j - 1]) < 0) {
                    array[j] = array[j - 1];
                } else
                    break;
            }
            array[j] = tmp;
        }
    }

}
