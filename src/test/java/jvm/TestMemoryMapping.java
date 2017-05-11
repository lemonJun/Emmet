package jvm;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TestMemoryMapping {
    public static void main(String[] args) {
        try {
            File f = File.createTempFile("D:/Test", null);
            f.deleteOnExit();
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            raf.setLength(1024);
            FileChannel channel = raf.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
            channel.close();
            raf.close();
            buffer = null;
            //            System.gc();
            if (f.delete())
                System.out.println("Temporary filedeleted: " + f);
            else
                System.out.println("Not yet deleted: " + f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
