package com.takin.emmet.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.common.base.Preconditions;
import com.takin.emmet.algo.Queue;
import com.takin.emmet.algo.collections.HashSet;
import com.takin.emmet.algo.collections.LinkedList;

/**
 * 算算自己写了多少行代码
 * 遍历的过程其实就是广度遍历
 * Created by shenshijun on 15/2/27.
 */
public class LineCounter {
    private String dir;
    private HashSet<String> suffixs;

    public static void main(String[] args) throws FileNotFoundException {
        LineCounter counter = new LineCounter("C:\\Users\\wangyazhou\\git\\Emmet\\src", new String[] { "java" });
        System.out.println(counter.count());

    }

    public LineCounter(String dir, String[] suffixs) {
        this.dir = dir;
        this.suffixs = new HashSet<>();
        for (String s : suffixs) {
            this.suffixs.add(s);
        }
    }

    public void addSuffix(String suffix) {
        Preconditions.checkNotNull(suffix);
        suffixs.add(suffix);
    }

    public int count() throws FileNotFoundException {
        File root = new File(dir);
        int result = 0;
        if (root.exists()) {
            Queue<File> queue = new LinkedList<>();
            queue.appendTail(root);
            while (!queue.isEmpty()) {
                File cur_file = queue.removeHead();
                if (cur_file.isDirectory() && cur_file.listFiles() != null) {
                    for (File f : cur_file.listFiles()) {
                        queue.appendTail(f);
                    }
                } else if (cur_file.isFile()) {
                    for (String suffix : suffixs) {
                        if (cur_file.getName().endsWith("." + suffix)) {
                            result += countFile(cur_file);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    private int countFile(File file) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.isFile());

        int result = 0;
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            result++;
            sc.nextLine();
        }
        return result;
    }
}
