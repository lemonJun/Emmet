package com.algo.common;

//输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数。
public class Statics {
    public static void main(String[] args) {
        String s = "Hello World! BeiJing AoYun 2008。";
        number(s);
    }
         
    public static void number(String s) {
        int digital = 0;
        int character = 0;
        int other = 0;
        int blank = 0;
        char[] ch = s.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] >= '0' && ch[i] <= '9') {
                digital++;
            } else if ((ch[i] >= 'a' && ch[i] <= 'z') || ch[i] > 'A' && ch[i] <= 'Z') {
                character++;
            } else if (ch[i] == ' ') {
                blank++;
            } else {
                other++;
            }
        }
        System.out.println("要测试的字符串为: " + s);
        System.out.println("数字个数: " + digital + "\n英文字母个数: " + character + "\n空格个数: " + blank + "\n其他字符个数:" + other);
    }
}
