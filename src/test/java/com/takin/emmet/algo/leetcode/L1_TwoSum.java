package com.takin.emmet.algo.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ����һ������  �� Ŀ����  Ҫ���������������֮��ΪĿ����  ֻ���һ���������
 * ss 
 * @since 
 * @see
 * Given an array of integers, find two numbers such that they add up to a specific target number.
   The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
   You may assume that each input would have exactly one solution.
   Input: numbers={2, 7, 11, 15}, target=9
   Output: index1=1, index2=2
 */
public class L1_TwoSum {

    public int[] twoSum(int[] numbers, int target) {
        int[] ret = new int[2];
        int[] back = new int[numbers.length];
        Map<Integer, List<Integer>> numbermap = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < numbers.length; i++) {
            back[i] = target - numbers[i];
            List<Integer> list = numbermap.get(numbers[i]);
            if (list == null) {
                list = new ArrayList<Integer>();
                numbermap.put(numbers[i], list);
            }
            list.add(i);
        }

        for (int i = 0; i < numbers.length; i++) {
            int value = back[i];
            if (numbermap.get(value) != null) {
                ret[0] = i + 1;
                List<Integer> list = numbermap.get(value);
                for (int j : list) {
                    if (j != i) {
                        ret[1] = j + 1;
                    }
                }
                break;
            } else {
                continue;
            }
        }

        if (ret.length > 1) {
            if (ret[0] > ret[1]) {
                int tmp = ret[0];
                ret[0] = ret[1];
                ret[1] = tmp;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        L1_TwoSum ts = new L1_TwoSum();
        int[] numbers = { 2, 1, 9, 4, 4, 56, 90, 3 };
        int target = 8;
        ts.twoSum(numbers, target);
    }
}
