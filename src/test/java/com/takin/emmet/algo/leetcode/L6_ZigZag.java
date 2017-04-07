package com.takin.emmet.algo.leetcode;

/**
 * ��һ���ַ�����ָ��������   תΪZigZag��ʽ�����
 *
 * @since 
 * @see   
 * 
 * ��Ҫȷ��һЩ������
 * 1 ÿһ��zig�������ַ��ĸ�����size = nrows+nrows-2;
 * 2 ÿһ��zip�ڵ��ַ��ļ��Ϊ size-2i
 * 
 *  1 3 5 7
 *  2 4 6 8
 *  
 *  1   5   9
 *  2 4 6 8 a
 *  3   7   b
 *  
 *  1    7    
 *  2  6 8  
 *  3 5  9 b  
 *  4    a    
 *  
 *  0 6 1 5 7 
 *  
 */
public class L6_ZigZag {
    public String convert(String s, int nRows) {
        if (nRows < 2) {
            return s;
        }
        int size = 2 * nRows - 2;
        int zigs = (int) Math.ceil((double) s.length() / (double) size);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nRows; i++) {
            int gap = size - 2 * (i == nRows - 1 ? 0 : i);
            for (int j = 0; j < zigs; j++) {
                if (i + size * j < s.length()) {
                    sb.append(s.charAt(i + size * j));
                    if (gap != size) {
                        if (i + size * j + gap < s.length()) {
                            sb.append(s.charAt(i + size * j + gap));
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        L6_ZigZag ll = new L6_ZigZag();
        Long start = System.nanoTime();
        String s = "rafsrqibaodyinnjbygsccdbkfuyketdeavxtfyttcubphnqfvkhxokjvgihkdkqgfnzkmudqohfvuycrimoyyawfkdrpokvvzwglrlbfsjdojhftvwuuwqbgvuvlethepnriyvqtgtjrcrkypgulyvturqfwjmcbbtjcqzxwuinxzaxogrbfowbfnidyvhzybjctkzsfifejhbyqubxkyyrngvldclefwgbggtlqapziszaobxybvsodpzjtmnzitcpbvcrvutfosfdvcdwzvmfkmoeadfjwhaacetxymfnhkscnvborntdbjhcmonlvplxtgxstehaozedwhspvntyxccjrrumghmaolshpbjfcpjyxdouqjunlxxeqttxbhxpuryjsjqwyzuvckrvtmihlhnbbgycnxthqtskcjgakbypnrkhduqqcdsfksjzscjivbtzmbzxezosrabwurnywhdizmktqtcnuxmjyoidpwxgwyfsrsrpzuyajkubdypzxdivrqahmzpkxufqowgpsgqdqmfvmuujzdgrthaiirugozycxguqomteyazkwwvwzbpskpctgxbwyzzwgtoufjbfkcrgymcznruyiwtrvunutosbjgyopbvbdoieamfqgzqqwjhtdxnylhavnylfzjgexqkyfqqnridnrnhwkwuxeustugyvphcmxomegerymxndkwbwvwtzsouputklcozzdmglsxjfuzkgvmcqiyrcmorghcrjsskxesjzsueotovrczjmxdpjrgrakklddxajqjiiemwzdtsftesqhhcyptaael";
        System.out.println(s.length());
        String str = ll.convert(s, 118);
        System.out.println(str.length());
        System.out.println(str);
        System.out.println(System.nanoTime() - start);
    }
}
