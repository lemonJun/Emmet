package algo1.common;

/**
 * 两个乒乓球队进行比赛，各出三人。甲队为a，b，c三人，乙队为x，y，z三人，以抽签决定比赛名单。 
 * 有人向队员打听比赛的名单：a说他不和x比，c说他不和x、z比。请编程序找出三队赛手的名单。
 * 
 *
 */
public class Match {
    public static void main(String[] args) {
        vs();
    }

    public static void vs() {
        char[] m = { 'a', 'b', 'c' };
        char[] n = { 'x', 'y', 'z' };
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < n.length; j++) {
                if (m[i] == 'a' && n[j] == 'x') {
                    continue;
                } else if (m[i] == 'a' && n[j] == 'y') {
                    continue;
                } else if ((m[i] == 'c' && n[j] == 'x') || (m[i] == 'c' && n[j] == 'z')) {
                    continue;
                } else if ((m[i] == 'b' && n[j] == 'z') || (m[i] == 'b' && n[j] == 'y')) {
                    continue;
                } else {
                    System.out.println(m[i] + " vs " + n[j]);
                }
            }
        }
    }
}
