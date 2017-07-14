package com.takin.algo.first;

import java.util.HashMap;

public class DoubleArray {
    private static HashMap<String, Integer> keyIndexMap;
    private int[] base;
    private int[] check;

    static {
        keyIndexMap = new HashMap<String, Integer>();
        keyIndexMap.put("啊", 1);
        keyIndexMap.put("阿", 2);
        keyIndexMap.put("唉", 3);
        keyIndexMap.put("根", 4);
        keyIndexMap.put("胶", 5);
        keyIndexMap.put("拉", 6);
        keyIndexMap.put("及", 7);
        keyIndexMap.put("廷", 8);
        keyIndexMap.put("伯", 9);
        keyIndexMap.put("人", 10);
        keyIndexMap.put("\0", 11);
    }

    public DoubleArray() {
        base = new int[30];
        check = new int[30];
        for (int i = 0; i < 30; i++) {
            base[i] = 0;
            check[i] = 0;
        }
    }

    public void addWord(String word) {
        int lastIndex = base[0];
        for (int i = 0, len = word.length(); i < len - 1; i++) {
            int index = keyIndexMap.get(word.substring(i, i + 1));
            if (base[lastIndex + index] == 0 && check[lastIndex + index] == 0) {
                // 位置为空
                base[lastIndex + index] = lastIndex + index;
                check[lastIndex + index] = lastIndex;
                lastIndex = base[lastIndex + index];
            } else if (base[lastIndex + index] != 0) {
                System.out.println();
                System.out.println(word.substring(i, i + 1));
                this.display();
                System.out.println();
                if (check[Math.abs(base[lastIndex]) + index] == lastIndex) {
                    // 已经存在前缀
                    lastIndex = Math.abs(base[lastIndex]) + index;
                } else {
                    // 冲突消解
                    int step = this.clearUpConflict(lastIndex, index);
                    base[lastIndex + index + step] = lastIndex + index + step;
                    check[lastIndex + index + step] = lastIndex;

                    lastIndex = lastIndex + index + step;
                }
            }

        }
        // 对于最后一个字符的处理
        int index = keyIndexMap.get(word.substring(word.length() - 1));
        if (base[lastIndex + index] != 0) {
            if (check[lastIndex + index] == lastIndex) {
                base[lastIndex + index] = -1 * (lastIndex + index);
                check[lastIndex + index] = lastIndex;
            } else {
                int step = this.clearUpConflict(lastIndex, index);
                base[lastIndex + index + step] = -1 * (lastIndex + index + step);
                check[lastIndex + index + step] = lastIndex;
            }
        } else if (base[lastIndex + index] == 0) {
            base[lastIndex + index] = -1 * (lastIndex + index);
            check[lastIndex + index] = lastIndex;
        }

    }

    // 冲突消解
    private int clearUpConflict(int lastIndex, int index) {

        int k = lastIndex;
        int step = 0;

        for (; k < 30; k++)
            if (base[k + index] != 0)
                step++;
            else
                break;
        for (int i = 1; i < 30; i++) {// 调整lastIndex的所有孩子的base值
            if (check[i] == lastIndex) {
                if (base[i] > 0)
                    base[i] -= step;
                else {
                    base[i] = -(Math.abs(base[i]) - step);
                }
            }
        }
        if (base[lastIndex] < 0)
            base[lastIndex] = -base[lastIndex] - step;// 保持一致
        else
            base[lastIndex] = base[lastIndex] + step;
        return step;

    }

    public void display() {
        for (int i = 0; i < 30; i++)
            System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < 30; i++)
            System.out.print(base[i] + " ");
        System.out.println();
        for (int i = 0; i < 30; i++)
            System.out.print(check[i] + " ");
    }

    public static void main(String[] args) {
        DoubleArray doubleArray = new DoubleArray();

        doubleArray.addWord("阿根廷");
        doubleArray.display();
        doubleArray.addWord("阿胶");
        System.out.println();
        doubleArray.display();
        doubleArray.addWord("根阿拉");
        System.out.println();
        doubleArray.display();
    }
}
