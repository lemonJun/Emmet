package text;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.simhasher.SimHasher;

/**
* @author louxuezheng@hotmail.com
*/
public class SimhashTest {

    public static void main(String[] args) {
        try {
            String str1 = readAllFile("D:/testin2.txt");
            SimHasher hash1 = new SimHasher(str1);
            System.out.println(hash1.getSignature());
            System.out.println("============================");

            String str2 = readAllFile("D:/testin.txt");
            SimHasher hash2 = new SimHasher(str2);
            System.out.println(hash2.getSignature());
            System.out.println("============================");

            System.out.println(hash1.getHammingDistance(hash2.getSignature()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试用
     * @param filename 名字
     * @return
     */
    public static String readAllFile(String filename) {
        String everything = "";
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            everything = IOUtils.toString(inputStream);
            inputStream.close();
        } catch (IOException e) {
        }

        return everything;
    }
}
