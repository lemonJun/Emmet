package com.takin.algo.common;

//猴子吃桃问题：猴子第一天摘下若干个桃子，当即吃了一半，还不过瘾，又多吃了一个；
//第二天早上又将剩下的桃子吃掉一半，而且又多吃了一个。
//以后每天早上都吃了前一天剩下的一半零一个。到第10天早上想再吃时，就只剩下一个桃子了。求第一天共摘了多少个桃子。
public class MonkyTao {
    public static void main(String[] args) {
        int sum = peach(1);
        System.out.println("第一天共摘了" + sum + "个桃子");
    }

    public static int peach(int day) {
        if (day == 10) {
            return 1;
        } else {
            return (peach(day + 1) + 1) * 2;
        }
    }
}
