package com.sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArraySort {
    public static void main(String args[]) {
        File f = new File("D:/sample.txt");
        List arrayList = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));

            String str = null;
            while ((str = reader.readLine()) != null) {
                arrayList.add(Integer.valueOf(str));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long startTime = System.currentTimeMillis();
        Collections.sort(arrayList);
        long endTime = System.currentTimeMillis();

        System.out.println("排序所花时间:" + (endTime - startTime) + "ms");

        File f2 = new File("D:/original_sorted.txt");
        FileWriter writer2;
        try {
            writer2 = new FileWriter(f2, false);

            for (int i = 0; i < arrayList.size(); i++) {
                writer2.write(String.valueOf(arrayList.get(i)));
                writer2.write("\r\n");
            }

            writer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
