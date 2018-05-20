package algo1;

/**
 * 这是一个递归问题
 *
 *
 * @author zhou
 * @version 1.0
 * @date  2018年5月20日 上午11:25:45
 * @see 
 * @since
 */
public class Fibonacci {

    public static void main(String[] args) {
        System.out.println(fibonacci(2));
    }

    public static int fibonacci(int n) {
        if (n <= 0) {
            return -1;
        } else if (n == 1 || n == 2) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }

    }
}
