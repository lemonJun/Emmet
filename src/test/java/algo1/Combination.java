package algo1;

import java.util.ArrayList;
import java.util.List;

public class Combination {
    public static void combiantion(char chs[]) {
        if (chs == null || chs.length == 0) {
            return;
        }
        List<Character> list = new ArrayList();
        for (int i = 1; i <= chs.length; i++) {
            combine(chs, 0, i, list);
        }
    }

    //从字符数组中第begin个字符开始挑选number个字符加入list中  
    public static void combine(char[] cs, int begin, int number, List<Character> list) {
        if (number == 0) {
            System.out.println(list.toString());
            return;
        }
        if (begin == cs.length) {
            return;
        }
        list.add(cs[begin]);
        combine(cs, begin + 1, number - 1, list);
        list.remove((Character) cs[begin]);
        combine(cs, begin + 1, number, list);
    }

    public static void main(String args[]) {
        char chs[] = { 'a', 'b', 'c' };
        combiantion(chs);
    }
}
