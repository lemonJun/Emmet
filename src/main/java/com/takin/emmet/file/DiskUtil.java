package com.takin.emmet.file;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import com.takin.emmet.string.StringUtil;

/**
 * 磁盘操作工具类
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-22
 * @version v0.1
 */
public abstract class DiskUtil {

    /**
     * @return 当前账户的主目录全路径
     */
    public static String home() {
        return System.getProperty("user.home");
    }

    /**
     * @param path
     *            相对用户主目录的路径
     * @return 相对用户主目录的全路径
     */
    public static String home(String path) {
        return home() + path;
    }

    /**
     * 获取一个路径的绝对路径
     * 
     * @param path
     *            路径
     * @return 绝对路径
     */
    public static String absolute(String path) {
        return absolute(path, FileUtil.class.getClassLoader(), CodingUtil.getDefaultEncoding());
    }

    /**
     * 获取一个路径的绝对路径
     * 
     * @param path
     *            路径
     * @param klassLoader
     *            参考 ClassLoader
     * @param enc
     *            路径编码方式
     * @return 绝对路径
     */
    public static String absolute(String path, ClassLoader klassLoader, String enc) {
        path = normalize(path, enc);
        if (StringUtil.isEmpty(path))
            return null;

        File f = new File(path);
        if (!f.exists()) {
            URL url = klassLoader.getResource(path);
            if (null == url)
                url = Thread.currentThread().getContextClassLoader().getResource(path);
            if (null == url)
                url = ClassLoader.getSystemResource(path);
            if (null != url)
                return normalize(url.getPath(), CodingUtil.UTF8);// 通过URL获取String,一律使用UTF-8编码进行解码
            return null;
        }
        return path;
    }

    /**
     * 让路径变成正常路径，将 ~ 替换成用户主目录
     * 
     * @param path
     *            路径
     * @return 正常化后的路径
     */
    public static String normalize(String path) {
        return normalize(path, CodingUtil.getDefaultEncoding());
    }

    /**
     * 让路径变成正常路径，将 ~ 替换成用户主目录
     * 
     * @param path
     *            路径
     * @param enc
     *            路径编码方式
     * @return 正常化后的路径
     */
    public static String normalize(String path, String enc) {
        if (StringUtil.isEmpty(path))
            return null;
        if (path.charAt(0) == '~')
            path = DiskUtil.home() + path.substring(1);
        try {
            return URLDecoder.decode(path, enc);
        } catch (Exception e) {
            return null;
        }

    }
}
