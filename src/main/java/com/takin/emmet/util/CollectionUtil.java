package com.takin.emmet.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author WangYazhou
 * @date  2017年3月27日 下午2:58:15
 * @see
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Map map) {
        return map != null && map.size() > 0;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Map map) {
        return !isNotEmpty(map);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return !isNotEmpty(collection);
    }

    @SuppressWarnings("rawtypes")
    public static int sizeOf(Collection collection) {
        if (isEmpty(collection)) {
            return 0;
        }
        return collection.size();
    }

    /**
     * 返回第一个列表中比第二个多出来的元素
     *
     * @param list1
     * @param list2
     * @return
     */
    public static <T> List<T> getLeftDiff(List<T> list1, List<T> list2) {
        if (isEmpty(list2)) {
            return list1;
        }
        List<T> list = new ArrayList<T>();
        if (isNotEmpty(list1)) {
            for (T o : list1) {
                if (!list2.contains(o)) {
                    list.add(o);
                }
            }
        }
        return list;
    }
}
