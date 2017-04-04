package com.takin.emmet.algo.math;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import com.google.common.base.Preconditions;
import com.takin.emmet.algo.ArrayUtil;
import com.takin.emmet.algo.List;
import com.takin.emmet.algo.Set;
import com.takin.emmet.algo.collections.HashSet;
import com.takin.emmet.algo.collections.Vector;
import com.takin.emmet.algo.lang.Tuple2;

/**
 * Created by shenshijun on 14-12-21.
 */
public class MathUtil {
    private MathUtil() {

    }

    private static Random randor = new Random();

    public static int gcd(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    public static double max(double... vals) {
        Preconditions.checkNotNull(vals, "vals should not be null");
        Preconditions.checkArgument(vals.length > 0, "vals should not be empty");
        if (vals.length == 0) {
            return 0;
        }
        double max_value = vals[0];
        for (double cur : vals) {
            if (max_value < cur) {
                max_value = cur;
            }
        }
        return max_value;
    }

    public static <T extends Comparable<? super T>> T max(T... vals) {
        Preconditions.checkNotNull(vals, "vals should not be null");
        Preconditions.checkArgument(vals.length > 0, "vals should not be empty");

        T max_value = vals[0];
        for (T cur : vals) {
            if (max_value.compareTo(cur) < 0) {
                max_value = cur;
            }
        }
        return max_value;
    }

    public static int randInt(int start, int end) {
        Preconditions.checkArgument(start <= end);
        if (start == end) {
            return end;
        }
        randor.setSeed(System.currentTimeMillis());
        return randor.nextInt(end - start) + start;
    }

    /**
     * 获取从范围是start到end之间的整数数组，num是个数，但是不能超过end－start+1
     *
     * @param start 取值范围下界
     * @param end   取值范围上界
     * @param num   要获取的数字个数
     * @return 随机数组
     */
    public static int[] randUniqueInt(int start, int end, int num) {
        Preconditions.checkArgument(start <= end);
        Preconditions.checkArgument(num <= (end - start + 1));
        Set<Integer> set = new HashSet<>();
        while (set.size() < num) {
            set.add(randInt(start, end));
        }
        int[] result = new int[set.size()];
        int pos = 0;
        for (int i : set) {
            result[pos++] = i;
        }
        return result;
    }

    public static double pow(double base, int exponent) {
        Preconditions.checkArgument(!(doubleEqual(base, 0.0) && exponent < 0));
        double result = powCore(base, Math.abs(exponent));
        if (exponent < 0) {
            return 1.0 / result;
        } else {
            return result;
        }
    }

    private static double powCore(double base, int exponent) {
        Preconditions.checkArgument(exponent >= 0);
        if (exponent == 0) {
            return 1;
        } else if (exponent == 1) {
            return base;
        }
        double result = powCore(base, exponent >> 1);
        result *= result;
        if (exponent % 2 == 1) {
            result *= base;
        }
        return result;
    }

    public static boolean doubleEqual(double one, double two) {
        return Math.abs(one - two) < 0.0000001;
    }

    @SafeVarargs
    public static <T extends Comparable<? super T>> T min(T... arr) {
        Preconditions.checkNotNull(arr);
        if (arr.length == 0) {
            throw new ArithmeticException("too few argument");
        }

        T min_value = arr[0];
        for (T ele : arr) {
            if (min_value.compareTo(ele) > 0) {
                min_value = ele;
            }
        }
        return min_value;
    }

    /**
     * 最大和连续子数组
     *
     * @param arr
     * @return
     */
    public static Tuple2<Integer, Integer> maxSubArray(double[] arr) {
        Preconditions.checkNotNull(arr);
        double sum = -1, temp_sum = 0;
        int left = 0, right = 0;
        for (int i = 0; i < arr.length; i++) {
            if (temp_sum < 0) {
                temp_sum = arr[i];
                left = i;
            } else {
                temp_sum += arr[i];
            }

            if (temp_sum > sum) {
                right = i;
                sum = temp_sum;
            }
        }

        if (sum < 0) {
            return new Tuple2<>(-1, -1);
        }
        return new Tuple2<>(left, right);
    }

    /**
     * 计算从1到n中，1出现个数的总和。
     *
     * @param n
     * @return
     */
    public static int countNumberOne(int n) {
        Preconditions.checkArgument(n >= 0);
        return countNumberOneCore(String.valueOf(n));
    }

    private static int countNumberOneCore(String number) {
        int first = number.charAt(0) - '0';

        if (number.length() == 1) {
            if (first == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        int firstDigitCount;
        if (first > 0) {
            firstDigitCount = (int) Math.round(Math.pow(10, number.length() - 1));
        } else {
            firstDigitCount = Integer.valueOf(number.substring(1)) + 1;
        }

        int otherDigitCount = first * (number.length() - 1) * (int) Math.round(Math.pow(10, number.length() - 2));

        return firstDigitCount + otherDigitCount + countNumberOneCore(number.substring(1));
    }

    /**
     * 从一系列整数中拼接出最小的数字。
     * 证明：使用反证法。
     * 假设这样得到的序列并不是最小的，也就是说对于序列A1A2...Ax....Ay-1Ay...An来说，如果交换Ax和Ay。
     * 得到的序列A1A2...Ay...Ay-1Ax...An < A1A2...Ax....Ay-1Ay...An。现在分别交换Ax和Ay使得Ax和Ay靠在一起。
     * 不等式左边的交换，由于Ay<Ax+1....Ay-1，所以把Ay往前调的时候，得到A1A2....Ax+1...Ay-1AyAx...An < A1A2...Ay...Ay-1Ax...An
     * 同理：右边也是一样的：A1A2...Ax....Ay-1Ay...An < A1A2...Ax+1....Ay-1AxAy...An。
     * 综合三个等式得到：A1A2....Ax+1...Ay-1AyAx...An < A1A2...Ax+1....Ay-1AxAy...An。也就是AyAx < AxAy，这样
     * 显然和定义的比较规则相反，所以原假设不成立，证明了通过这样的排序规则得到的序列是最小的序列。
     * {{code shsi}}
     *
     * @param arr
     * @return
     */
    public static Integer[] combineMinNumber(Integer[] arr) {
        Preconditions.checkNotNull(arr);
        ArrayUtil.sort(arr, (one, other) -> {
            Preconditions.checkArgument(one >= 0);
            Preconditions.checkArgument(other >= 0);
            return (one.toString() + other.toString()).compareTo(other.toString() + one.toString());
        });
        return arr;
    }

    /**
     * 取第index个丑数，丑数是指仅可以分解成2，3，5的数。
     *
     * @param index
     * @return
     */
    public static int uglyNumber(int index) {
        Preconditions.checkArgument(index >= 1);
        int[] ugly_numbers = new int[index];
        ugly_numbers[0] = 1;
        int multiply2_index = 0, multiply3_index = 0, multiply5_index = 0;

        int cur_index = 1;
        while (cur_index < index) {
            ugly_numbers[cur_index] = min(ugly_numbers[multiply2_index] * 2, ugly_numbers[multiply3_index] * 3, ugly_numbers[multiply5_index] * 5);

            while (ugly_numbers[multiply2_index] * 2 <= ugly_numbers[cur_index]) {
                multiply2_index++;
            }

            while (ugly_numbers[multiply3_index] * 3 <= ugly_numbers[cur_index]) {
                multiply3_index++;
            }

            while (ugly_numbers[multiply5_index] * 5 <= ugly_numbers[cur_index]) {
                multiply5_index++;
            }

            cur_index++;
        }
        return ugly_numbers[index - 1];
    }

    public static int strToInt(String str) {
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(str.length() > 0);
        Preconditions.checkArgument(!(str.length() == 1 && (str.charAt(0) == '+' || str.charAt(0) == '-')));
        int result = 0;
        boolean is_negative = false;
        int num_start = 0;
        if (str.charAt(0) == '-') {
            is_negative = true;
            num_start = 1;
        } else if (str.charAt(0) == '+') {
            num_start = 1;
        }

        for (int i = num_start; i < str.length(); i++) {
            int this_digit = str.charAt(i) - '0';
            if (this_digit < 0 || this_digit > 9) {
                throw new ArithmeticException("wrong format");
            }
            result = Math.multiplyExact(result, 10);
            if (is_negative) {
                result = Math.subtractExact(result, this_digit);
            } else {
                result = Math.addExact(result, this_digit);
            }
        }
        return result;
    }

    public static long strToLong(String str) {
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(str.length() > 0);
        Preconditions.checkArgument(!(str.length() == 1 && (str.charAt(0) == '+' || str.charAt(0) == '-')));
        long result = 0;
        boolean is_negative = false;
        int num_start = 0;
        if (str.charAt(0) == '-') {
            is_negative = true;
            num_start = 1;
        } else if (str.charAt(0) == '+') {
            num_start = 1;
        }

        for (int i = num_start; i < str.length(); i++) {
            long this_digit = str.charAt(i) - '0';
            if (this_digit < 0 || this_digit > 9) {
                throw new ArithmeticException("wrong format");
            }
            result = Math.multiplyExact(result, 10L);
            if (is_negative) {
                result = Math.subtractExact(result, this_digit);
            } else {
                result = Math.addExact(result, this_digit);
            }
        }
        return result;
    }

    public int closestBig(int origion) {
        Preconditions.checkArgument(origion > 0);
        int first_one_index = BitUtil.firstBitOne(origion);

        if (first_one_index > 0) {
            for (int i = first_one_index; i >= 0; i--) {
                if (!BitUtil.testBit(origion, i)) {
                    return (int) BitUtil.moveBit(origion, first_one_index, i);
                }
            }
        }
        return -1;
    }

    public int closestLittle(int origion) {
        Preconditions.checkArgument(origion > 0);
        int first_one_index = BitUtil.firstBitOne(origion);

        if (first_one_index > 0) {
            for (int i = first_one_index; i < Integer.SIZE - 1; i++) {
                if (!BitUtil.testBit(origion, i)) {
                    return (int) BitUtil.moveBit(origion, first_one_index, i);
                }
            }
        }
        return -1;
    }

    public boolean isPrime(long value) {
        //TODO 素数检测
        return false;
    }

    public long[] cloestSum(long[] arr, long value) {
        //todo 寻找和为定值的两个数
        return null;
    }

    public static double maxSubList(double[] arr) {
        Preconditions.checkNotNull(arr, "arr should not be null");
        double max_value = 0;
        double this_max_value = 0;
        for (double cur : arr) {
            this_max_value += cur;
            if (this_max_value < 0) {
                this_max_value = 0;
            } else if (max_value < this_max_value) {
                max_value = this_max_value;
            }
        }
        return max_value;
    }

    /**
     * 来自《剑指offer》的一个题目，一个int数组中有一个唯一的元素，
     * 其他的元素都有两个，求出唯一的元素。
     *
     * @param arr
     * @return 唯一元素
     */
    public static int uniqueInt(int[] arr) {
        Preconditions.checkNotNull(arr);
        Preconditions.checkArgument(arr.length >= 1);
        int result = 0;
        for (int i : arr) {
            result ^= i;
        }
        if (!checkUnique(arr, result)) {
            throw new IllegalArgumentException("do not have unique element :" + Arrays.toString(arr));
        }
        return result;
    }

    public static Tuple2<Integer, Integer> uniqueTwoInt(int[] arr) {
        Preconditions.checkNotNull(arr);
        Preconditions.checkArgument(arr.length >= 2);
        int result_partition = 0;
        for (int i : arr) {
            result_partition ^= i;
        }

        int last_one = BitUtil.firstBitOne(result_partition);
        if (last_one == -1) {
            throw new IllegalArgumentException("do not have two unique element :" + Arrays.toString(arr));
        }

        int result1 = 0, result2 = 0;
        for (int i : arr) {
            if (BitUtil.testBit(i, last_one)) {
                result1 ^= i;
            } else {
                result2 ^= i;
            }
        }

        if (!(checkUnique(arr, result1) && checkUnique(arr, result2))) {
            throw new IllegalArgumentException("do not have two unique element :" + Arrays.toString(arr));
        }

        return new Tuple2<>(result1, result2);
    }

    private static boolean checkUnique(int[] arr, int ele) {
        Preconditions.checkNotNull(arr);
        int count = 0;
        for (int i : arr) {
            if (i == ele) {
                count++;
            }
        }
        return count == 1;
    }

    /**
     * 在一个排好序的int数组中查找连续子数组其和为指定的值。
     *
     * @param arr
     * @param value
     * @return
     */
    public static List<Tuple2<Integer, Integer>> findSumInOrder(int[] arr, int value) {
        Preconditions.checkNotNull(arr);
        Preconditions.checkArgument(arr.length > 1);
        List<Tuple2<Integer, Integer>> result = new Vector<>();
        int start = arr.length - 2;
        int end = arr.length - 1;
        int sum = arr[start] + arr[end];
        while (start >= 0) {
            if (sum == value) {
                result.add(new Tuple2<>(start, end));
                sum -= arr[end];
                end--;
            } else if (sum < value) {
                start--;
                if (start >= 0) {
                    sum += arr[start];
                }
            } else {
                sum -= arr[end];
                end--;
            }
        }
        return result;
    }

    /**
     * 从投色子问题来，一个色子有6个面，丢n个色子和的每种可能性和概率
     *
     * @param n
     * @param max
     * @return
     */
    public static double[] probability(int n, int max) {
        Preconditions.checkArgument(n >= 1);
        Preconditions.checkArgument(max > 0);

        int[] sum_count = probabilitySumCount(n, max);
        double sum = Math.round(pow(max, n));
        double[] result = new double[sum_count.length];
        for (int i = 0; i < sum_count.length; i++) {
            result[i] = sum_count[i] / sum;
        }
        return result;
    }

    public static int[] probabilitySumCount(int n, int max) {
        Preconditions.checkArgument(n >= 1);
        Preconditions.checkArgument(max > 0);

        int[] result = new int[n * max + 1];
        int[] result_tmp = new int[result.length];
        for (int i = 1; i <= max; i++) {
            result_tmp[i] = 1;
            result[i] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < result_tmp.length; j++) {
                result[j] = 0;
                for (int k = 1; k <= max && k < j; k++) {
                    result[j] += result_tmp[j - k];
                }
            }

            int[] tmp = result_tmp;
            result_tmp = result;
            result = tmp;
        }
        return result_tmp;
    }

    public static double sum(double[] arr) {
        Preconditions.checkNotNull(arr);
        double result = 0;
        for (double num : arr) {
            result += num;
        }
        return result;
    }

    public static int sum(int[] arr) {
        Preconditions.checkNotNull(arr);
        int result = 0;
        for (int num : arr) {
            result += num;
        }
        return result;
    }

    /**
     * 判断传递进来的int数组中保存的数字是不是顺子
     * 大小王使用0表示。其他的数必须大于0
     *
     * @param numbers
     * @return
     */
    public static boolean isContinuous(int[] numbers) {
        Preconditions.checkNotNull(numbers);
        Preconditions.checkArgument(numbers.length >= 5);

        numbers = littleSort(numbers);
        int normal_code = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < 0) {
                throw new IllegalArgumentException("code should be bigger than 0:" + numbers[i]);
            } else if (numbers[i] > 0) {
                normal_code = i;
                break;
            }
        }

        //按照扑克中的规则，一副牌中最多有两个王
        if (normal_code >= 2) {
            throw new IllegalArgumentException("too many guest");
        }
        int lord_count = normal_code;

        for (int i = normal_code + 1; i < numbers.length; i++) {
            if (numbers[i - 1] == numbers[i]) {
                return false;
            } else if ((numbers[i - 1] + 2) == numbers[i]) {
                if (lord_count > 0) {
                    lord_count--;
                } else {
                    return false;
                }
            } else if ((numbers[i - 1] + 1) != numbers[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 使用插入排序实现小数组的排序
     *
     * @param numbers
     * @return
     */
    private static int[] littleSort(int[] numbers) {
        Preconditions.checkNotNull(numbers);

        for (int i = 1; i < numbers.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (numbers[j + 1] < numbers[j]) {
                    ArrayUtil.swap(numbers, j, j + 1);
                } else {
                    break;
                }
            }
        }
        return numbers;
    }
}
