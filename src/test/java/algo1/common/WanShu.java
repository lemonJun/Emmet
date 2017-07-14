package algo1.common;

/**
 * 一个数如果恰好等于它的因子之和，这个数就称为"完数"。例如6=1＋2＋3。编程找出1000以内的所有完数。
 *
 *
 * @author Administrator
 * @version 1.0
 * @date  2017年7月8日 下午4:21:05
 * @see 
 * @since
 */
public class WanShu {
    public static void main(String[] args) {
        number();
    }

    public static void number() {
        int count = 0;
        for (int i = 1; i <= 1000; i++) {
            int t = 0;
            for (int j = 1; j <= i / 2; j++) {
                if (i % j == 0) {
                    t = t + j;
                }
            }
            if (t == i) {
                System.out.print(i + "\t");
                count++;
            }
        }
        System.out.println("\n共有" + count + "个完数。");
    }

}
