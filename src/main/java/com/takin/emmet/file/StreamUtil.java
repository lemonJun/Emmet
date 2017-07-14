package com.takin.emmet.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.google.common.base.Charsets;
import com.takin.emmet.util.BaseRuntimeException;

/**
 * 流操作帮助工具
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-22
 * @version v0.1
 */
public abstract class StreamUtil {

    private static final int BUF_SIZE = 8192;

    /**
     * 从一个文本输入流读取所有内容，并将该流关闭
     * 
     * @param reader
     *            文本输入流
     * @return 输入流所有内容
     */
    public static String readAll(Reader reader) {
        if (!(reader instanceof BufferedReader))
            reader = new BufferedReader(reader);
        try {
            StringBuilder sb = new StringBuilder();

            char[] data = new char[64];
            int len;
            while (true) {
                if ((len = reader.read(data)) == -1)
                    break;
                sb.append(data, 0, len);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new BaseRuntimeException("IO异常：", e);
        } finally {
            safeClose(reader);
        }
    }

    /**
     * 将一段字符串写入一个文本输出流，并将该流关闭
     * 
     * @param writer
     *            文本输出流
     * @param str
     *            字符串
     */
    public static void writeAll(Writer writer, String str) {
        try {
            writer.write(str);
            writer.flush();
        } catch (IOException e) {
            throw new BaseRuntimeException("IO异常：", e);
        } finally {
            safeClose(writer);
        }
    }

    /**
     * 判断两个输入流是否严格相等
     * @param sA
     * @param sB
     * @return
     * @throws IOException
     */
    public static boolean equals(InputStream sA, InputStream sB) throws IOException {
        int dA;
        while ((dA = sA.read()) != -1) {
            if (dA != sB.read())
                return false;
        }
        return sB.read() == -1;
    }

    /**
     * 将一段文本全部写入一个writer。
     * <p>
     * <b style=color:red>注意</b>，它不会关闭输出流
     * 
     * @param writer
     * @param cs 文本
     * @throws IOException
     */
    public static void write(Writer writer, CharSequence cs) throws IOException {
        if (null != cs && null != writer) {
            writer.write(cs.toString());
            writer.flush();
        }
    }

    /**
     * 将一段文本全部写入一个writer，并同时关闭输出流。
     * 
     * @param writer  输出流
     * @param cs  文本
     */
    public static void writeAndClose(Writer writer, CharSequence cs) {
        try {
            write(writer, cs);
        } catch (IOException e) {
            throw new BaseRuntimeException("IO异常：", e);
        } finally {
            safeClose(writer);
        }
    }

    /**
     * 将输出流写入一个输出流。块大小为 8192
     * <p>
     * <b style=color:red>注意</b>，它并不会关闭输入/出流
     * 
     * @param ops
     *            输出流
     * @param ins
     *            输入流
     * @throws IOException
     */
    public static void write(OutputStream ops, InputStream ins) throws IOException {
        if (null == ops || null == ins)
            return;

        byte[] buf = new byte[BUF_SIZE];
        int len;
        while (-1 != (len = ins.read(buf))) {
            ops.write(buf, 0, len);
        }
    }

    /**
     * 将输出流写入一个输出流。块大小为 8192
     * <p>
     * <b style=color:red>注意</b>，它会关闭输入/出流
     * 
     * @param ops
     *            输出流
     * @param ins
     *            输入流
     */
    public static void writeAndClose(OutputStream ops, InputStream ins) {
        try {
            write(ops, ins);
        } catch (IOException e) {
            throw new BaseRuntimeException("IO异常：", e);
        } finally {
            safeClose(ops);
            safeClose(ins);
        }
    }

    /**
     * 将文本输出流写入一个文本输出流。块大小为 8192
     * <p>
     * <b style=color:red>注意</b>，它并不会关闭输入/出流
     * 
     * @param writer
     *            输出流
     * @param reader
     *            输入流
     * @throws IOException
     */
    public static void write(Writer writer, Reader reader) throws IOException {
        if (null == writer || null == reader)
            return;

        char[] cbuf = new char[BUF_SIZE];
        int len;
        while (-1 != (len = reader.read(cbuf))) {
            writer.write(cbuf, 0, len);
        }
    }

    /**
     * 将文本输出流写入一个文本输出流。块大小为 8192
     * <p>
     * <b style=color:red>注意</b>，它会关闭输入/出流
     * 
     * @param writer
     *            输出流
     * @param reader
     *            输入流
     */
    public static void writeAndClose(Writer writer, Reader reader) {
        try {
            write(writer, reader);
        } catch (IOException e) {
            throw new BaseRuntimeException("IO异常：", e);
        } finally {
            safeClose(writer);
            safeClose(reader);
        }
    }

    /**
     * 将一个字节数组写入一个输出流。
     * <p>
     * <b style=color:red>注意</b>，它并不会关闭输出流
     * 
     * @param ops
     *            输出流
     * @param bytes
     *            字节数组
     * @throws IOException
     */
    public static void write(OutputStream ops, byte[] bytes) throws IOException {
        if (null == ops || null == bytes)
            return;
        ops.write(bytes);
    }

    /**
     * 将一个字节数组写入一个输出流。
     * <p>
     * <b style=color:red>注意</b>，它会关闭输出流
     * 
     * @param ops
     *            输出流
     * @param bytes
     *            字节数组
     */
    public static void writeAndClose(OutputStream ops, byte[] bytes) {
        try {
            write(ops, bytes);
        } catch (IOException e) {
            throw new BaseRuntimeException("IO异常：", e);
        } finally {
            safeClose(ops);
        }
    }

    /**
     * 从一个文本流中读取全部内容并返回
     * <p>
     * <b style=color:red>注意</b>，它并不会关闭输出流
     * 
     * @param reader
     *            文本输出流
     * @return 文本内容
     * @throws IOException
     */
    public static StringBuilder read(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] cbuf = new char[BUF_SIZE];
        int len;
        while (-1 != (len = reader.read(cbuf))) {
            sb.append(cbuf, 0, len);
        }
        return sb;
    }

    /**
     * 从一个文本流中读取全部内容并返回
     * <p>
     * <b style=color:red>注意</b>，它会关闭输出流
     * 
     * @param reader
     *            文本输出流
     * @return 文本内容
     * @throws IOException
     */
    public static String readAndClose(Reader reader) {
        try {
            return read(reader).toString();
        } catch (IOException e) {
            throw new BaseRuntimeException("IO异常：", e);
        } finally {
            safeClose(reader);
        }
    }

    /**
     * 关闭一个可关闭对象，可以接受 null。如果成功关闭，返回 true，发生异常 返回 false
     * 
     * @param cb
     *            可关闭对象
     * @return 是否成功关闭
     */
    public static boolean safeClose(Closeable cb) {
        if (null != cb)
            try {
                cb.close();
            } catch (IOException e) {
                return false;
            }
        return true;
    }

    public static void safeFlush(Flushable fa) {
        if (null != fa)
            try {
                fa.flush();
            } catch (IOException e) {

            }
    }

    /**
     * 为一个输入流包裹一个缓冲流。如果这个输入流本身就是缓冲流，则直接返回
     * 
     * @param ins
     *            输入流。
     * @return 缓冲输入流
     */
    public static BufferedInputStream buff(InputStream ins) {
        if (ins instanceof BufferedInputStream)
            return (BufferedInputStream) ins;
        return new BufferedInputStream(ins);
    }

    /**
     * 为一个输出流包裹一个缓冲流。如果这个输出流本身就是缓冲流，则直接返回
     * 
     * @param ops
     *            输出流。
     * @return 缓冲输出流
     */
    public static BufferedOutputStream buff(OutputStream ops) {
        if (ops instanceof BufferedOutputStream)
            return (BufferedOutputStream) ops;
        return new BufferedOutputStream(ops);
    }

    /**
     * 为一个文本输入流包裹一个缓冲流。如果这个输入流本身就是缓冲流，则直接返回
     * 
     * @param reader
     *            文本输入流。
     * @return 缓冲文本输入流
     */
    public static BufferedReader buffr(Reader reader) {
        if (reader instanceof BufferedReader)
            return (BufferedReader) reader;
        return new BufferedReader(reader);
    }

    /**
     * 为一个文本输出流包裹一个缓冲流。如果这个文本输出流本身就是缓冲流，则直接返回
     * 
     * @param ops
     *            文本输出流。
     * @return 缓冲文本输出流
     */
    public static BufferedWriter buffw(Writer ops) {
        if (ops instanceof BufferedWriter)
            return (BufferedWriter) ops;
        return new BufferedWriter(ops);
    }

    /**
     * 根据一个文件路径建立一个输入流
     * 
     * @param path
     *            文件路径
     * @return 输入流
     */
    public static InputStream fileIn(String path) {
        InputStream ins = FileUtil.findFileAsStream(path);
        if (null == ins) {
            File f = FileUtil.findFile(path);
            if (null != f)
                try {
                    ins = new FileInputStream(f);
                } catch (FileNotFoundException e) {
                }
        }
        return buff(ins);
    }

    /**
     * 根据一个文件路径建立一个输入流
     * 
     * @param file
     *            文件
     * @return 输入流
     */
    public static InputStream fileIn(File file) {
        try {
            return buff(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new BaseRuntimeException("IO异常：", e);
        }
    }

    /**
     * 根据一个文件路径建立一个 UTF-8文本输入流
     * 
     * @param path
     *            文件路径
     * @return 文本输入流
     */
    public static Reader fileInr(String path) {
        return new InputStreamReader(fileIn(path), Charsets.UTF_8);
    }

    /**
     * 根据一个文件路径建立一个 UTF-8 文本输入流
     * 
     * @param file
     *            文件
     * @return 文本输入流
     */
    public static Reader fileInr(File file) {
        return new InputStreamReader(fileIn(file), Charsets.UTF_8);
    }

    /**
     * 根据一个文件路径建立一个输出流
     * 
     * @param path
     *            文件路径
     * @return 输出流
     */
    public static OutputStream fileOut(String path) {
        return fileOut(FileUtil.findFile(path));
    }

    /**
     * 根据一个文件建立一个输出流
     * 
     * @param file
     *            文件
     * @return 输出流
     */
    public static OutputStream fileOut(File file) {
        try {
            return buff(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new BaseRuntimeException("IO异常：", e);
        }
    }

    /**
     * 根据一个文件路径建立一个 UTF-8 文本输出流
     * 
     * @param path
     *            文件路径
     * @return 文本输出流
     */
    public static Writer fileOutw(String path) {
        return fileOutw(FileUtil.findFile(path));
    }

    /**
     * 根据一个文件建立一个 UTF-8 文本输出流
     * 
     * @param file
     *            文件
     * @return 输出流
     */
    public static Writer fileOutw(File file) {
        return new OutputStreamWriter(fileOut(file), Charsets.UTF_8);
    }
}
