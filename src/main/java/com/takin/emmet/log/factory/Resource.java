package com.takin.emmet.log.factory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.takin.emmet.string.StringUtil;

public class Resource {

    public final static InputStream getResource(String fileName) {
        InputStream inputStream = null;
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
                inputStream = Resource.class.getResourceAsStream(fileName);
            } catch (Exception e) {
                ;
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
            }
        }
        if (path == null) {
            try {
                path = Resource.class.getResource(fileName).getPath();
            } catch (Exception e) {
            }
        }

        if (StringUtil.isNullOrEmpty(path)) {
            throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
        }
        return URLDecoder.decode(path, "UTF-8");
    }
}
