package com.takin.emmet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ResourceUtils {

    public final static InputStream getResource(String fileName) {
        InputStream inputStream = null;
        try {
            File file = new File(fileName);
            if (!file.isAbsolute()) {
                file = new File(System.getProperty("user.dir"), fileName);
            }
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (inputStream == null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (inputStream == null) {
                try {
                    inputStream = loader.getResourceAsStream(fileName);
                } catch (Exception e) {
                    ;
                }
            }
            if (inputStream == null) {
                try {
                    inputStream = ResourceUtils.class.getResourceAsStream(fileName);
                } catch (Exception e) {
                    ;
                }
            }
        }
        return inputStream;
    }

    public final static String getPath(String fileName) throws UnsupportedEncodingException {
        String path = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (path == null) {
            try {
                path = loader.getResource(fileName).getPath();
            } catch (Exception e) {
                ;
            }
        }
        if (path == null) {
            try {
                path = ResourceUtils.class.getResource(fileName).getPath();
            } catch (Exception e) {
                ;
            }
        }
        return path != null ? URLDecoder.decode(path, "UTF-8") : path;
    }

}
