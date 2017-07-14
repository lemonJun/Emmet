package algo1.common;

/**
 * 判断101-2000之间有多少个素数，并输出所有素数。
 *  质数（prime number）又称素数，有无限个。质数定义为在大于1的自然数中，
 *  除了1和它本身以外不再有其他因数的数称为质数。
 *
 */
public class Shusu {
    public static void main(String[] args) {
        prime();
    }

    public static void prime() {
        int count = 0;
        for (int i = 101; i < 2000; i += 2) {
            boolean flag = false;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
            if (flag) {
                count++;
                System.out.print(i + " ");
                if (count % 10 == 0) {
                    System.out.println();//显示换行处理
                }
            }
        }
        System.out.println("\n共有" + count + "个素数。");
    }
}
