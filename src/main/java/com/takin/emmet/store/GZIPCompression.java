package com.takin.emmet.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.takin.emmet.util.Closer;

public class GZIPCompression {

    protected final InputStream inputStream;

    protected final OutputStream outputStream;

    public GZIPCompression(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void close() {
        Closer.closeQuietly(inputStream);
        Closer.closeQuietly(outputStream);
    }

    public int read(byte[] b) throws IOException {
        return inputStream.read(b);
    }

    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

}
