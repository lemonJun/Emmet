package com.takin.emmet.bytes;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ByteArrayReader {
    static Charset utf8Charset = Charset.forName("UTF-8");
    byte[] data;
    int index;

    public ByteArrayReader(byte[] data, int index) {
        this.data = data;
        this.index = index;
    }

    public ByteArrayReader(byte[] data) {
        this.data = data;
        this.index = 0;
    }

    public short readShortLE() {
        short value = EndianUtility.bytesLittleToShort(data, index);
        index += 2;
        return value;
    }

    public int readIntLE() {
        int value = EndianUtility.bytesLittleToInt(data, index);
        index += 4;
        return value;
    }

    public long readLongLE() {
        long value = EndianUtility.bytesLittleToLong(data, index);
        index += 8;
        return value;
    }

    public float readFloatLE() {
        float value = EndianUtility.bytesLittleToFloat(data, index);
        index += 4;
        return value;
    }

    public double readDoubleLE() {
        double value = EndianUtility.bytesLittleToDouble(data, index);
        index += 8;
        return value;
    }

    public boolean readBoolean() {
        boolean value = data[index] != 0;
        index += 1;
        return value;
    }

    public byte readByte() {
        return data[index++];
    }

    public String readString(int len) {
        String value = utf8Charset.decode(ByteBuffer.wrap(data, index, len)).toString();
        index += len;
        return value;
    }
}
