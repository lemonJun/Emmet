package com.takin.algo;

//排列
//http://www.cnblogs.com/longhs/archive/2013/06/14/3135433.html
public class Permutation {
    
    public static void permutation(char[] ss, int i) {
        if (ss == null || i < 0 || i > ss.length) {
            return;
        }
        if (i == ss.length) {
            System.out.println(new String(ss));
        }
        for (int j = i; j < ss.length; j++) {
            char temp = ss[j];//交换前缀,使之产生下一个前缀  
            ss[j] = ss[i];
            ss[i] = temp;
            permutation(ss, i + 1);
            temp = ss[j]; //将前缀换回来,继续做上一个的前缀排列.  
            ss[j] = ss[i];
            ss[i] = temp;
        }
    }

    public static void main(String args[]) {
        char[] ss = { 'a', 'b' };
        permutation(ss, 0);
    }
}
