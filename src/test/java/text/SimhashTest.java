package text;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.takin.simhasher.SimHasher;

/**
* @author louxuezheng@hotmail.com
*/
public class SimhashTest {

    public static void main(String[] args) {
        try {
            String str1 = "D:/testin2.txt";
            SimHasher hash1 = new SimHasher(str1);
            System.out.println(hash1.getSignature());
            System.out.println("============================");

            String str2 = "D:/testin.txt";
            SimHasher hash2 = new SimHasher(str2);
            System.out.println(hash2.getSignature());
            System.out.println("============================");

            System.out.println(hash1.getHammingDistance(hash2.getSignature()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
