package com.algo.common;

/**
 * 判断这一天是这一年的第几天
 *
 *
 * @author Administrator
 * @version 1.0
 * @date  2017年7月8日 下午4:00:58
 * @see 
 * @since
 */
public class DayOfYear {
    public static void main(String[] args) {
        year(2017, 6, 5);
    }

    /**
     * 
     * 平年2月28天,闰年2月29天.
    * 年份数（末两位不是00）能被4整除的是闰年（如1988、1996）、年份数（末两位数是00的）能被400整除是也是闰年（如2000年,4000年）.其余为平年.
     * 
     * 
     * 
     * @param year
     * @param month
     * @param day
     */
    public static void year(int year, int month, int day) {
        int d = 0;
        int days = 0;
        if (year < 0 || month < 0 || month > 12 || day < 0 || day > 31) {
            System.out.println("输入错误，请重新输入！");
        }
        for (int i = 1; i < month; i++) {
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;
                    break;
                case 2:
                    if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
                        days = 29;
                    } else {
                        days = 28;
                    }
                    break;
            }
            d += days;
        }
        System.out.println(year + "年" + month + "月" + day + "日是" + year + "的第" + (d + day) + "天。");
    }
}
