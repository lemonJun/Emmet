package algo2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 滑动窗口
 *
 *
 * @author zhou
 * @version 1.0
 * @date  2018年5月20日 下午5:54:04
 * @see 
 * @since
 */
public class MaxInWindows {

    public static void main(String[] args) {

        int[] arrays = { 2, 3, 4, 2, 6, 2, 5, 1 };
        int size = 3;
        System.out.println("滑动窗口的最大值为：");
        System.out.println(new MaxInWindows().maxInWindows(arrays, size));
    }

    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> ret = new ArrayList<>();
        if (num == null) {
            return ret;
        }
        if (num.length < size || size < 1) {
            return ret;
        }

        LinkedList<Integer> indexDeque = new LinkedList<>(); //用于保存滑动窗口中的数字  

        /* 
        滑动窗口内部，用于判断窗口中的最大值 
        */
        for (int i = 0; i < size - 1; i++) {
            while (!indexDeque.isEmpty() && num[i] > num[indexDeque.getLast()]) { //getLast为插入端  
                indexDeque.removeLast(); //将前面比K小的直接移除队列，因为不可能成为滑动窗口的最大值  
            }
            indexDeque.addLast(i); //将数字存入滑动窗口中  
        }

        /* 
        滑动整个窗口 
        */
        for (int i = size - 1; i < num.length; i++) {
            while (!indexDeque.isEmpty() && num[i] > num[indexDeque.getLast()]) { //getLast为插入端，队尾  
                indexDeque.removeLast(); //将前面比K小的直接移除队列，因为不可能成为滑动窗口的最大值  
            }
            indexDeque.addLast(i);
            //System.out.println("indexDeque = " + indexDeque.getFirst() + ",i = " + i);                //getFirst为允许删除端，队头  
            if (i - indexDeque.getFirst() + 1 > size) {
                indexDeque.removeFirst();
            }

            ret.add(num[indexDeque.getFirst()]); //每次添加的是num[indexDeque.getFirst()],而不是indexDeque.getFirst().  
        }
        return ret;
    }
}
