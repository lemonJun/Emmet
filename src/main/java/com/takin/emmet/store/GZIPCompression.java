package com.takin.emmet.store;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPCompression {

    public static byte[] compress(byte[] src) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        GZIPOutputStream gzipStream = new GZIPOutputStream(bout);

        gzipStream.write(src);
        gzipStream.flush();
        gzipStream.close();
        bout.flush();

        return bout.toByteArray();
    }

    public static byte[] uncompress(byte[] src) throws IOException {
        byte[] buffer = new byte[1024]; // 1k buffer
        ByteArrayInputStream bin = new ByteArrayInputStream(src);
        GZIPInputStream gzipIn = new GZIPInputStream(bin);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while (gzipIn.available() > 0) {
            int len = gzipIn.read(buffer);
            if (len <= 0)
                break;
            if (len < buffer.length) {
                bout.write(buffer, 0, len);
            } else {
                bout.write(buffer);
            }
        }
        bout.flush();
        return bout.toByteArray();
    }

}
