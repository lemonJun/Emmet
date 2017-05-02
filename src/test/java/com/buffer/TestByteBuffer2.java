package com.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TestByteBuffer2 {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(1024);

        int iRet = buffer.getInt(0);
        System.out.println("in 1024(BIG_ENDIAN),out " + iRet);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(1024);
        iRet = buffer.getInt(4);
        System.out.println("in 1024(LITTLE_ENDIAN),out " + iRet);

        buffer.clear();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        Float fvalue = 1111.1234F;
        buffer.putFloat(fvalue);
        Double dvalue = 1111.1234D;
        Short svalue = 1234;
        buffer.putDouble(dvalue);
        buffer.putShort(svalue);
        buffer.flip();
        System.out.println("pos=" + buffer.position() + ",limit=" + buffer.limit());

        for (int i = 0; i < 4; i++) {
            System.out.printf("0x%x\t", buffer.get());
        }
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.printf("0x%x\t", buffer.get());
        }
        System.out.println();
        for (int i = 0; i < 2; i++) {
            System.out.printf("0x%x\t", buffer.get());
        }
        System.out.println();

        buffer.clear();
        buffer.order(ByteOrder.BIG_ENDIAN);
        System.out.println("pos=" + buffer.position() + ",limit=" + buffer.limit());
        buffer.putFloat(fvalue);
        System.out.println("pos=" + buffer.position() + ",limit=" + buffer.limit());
        buffer.putDouble(dvalue);
        System.out.println("pos=" + buffer.position() + ",limit=" + buffer.limit());
        int curPos = buffer.position();
        buffer.putShort(svalue);
        System.out.println("pos=" + buffer.position() + ",limit=" + buffer.limit());
        buffer.position(curPos);
        System.out.println("pos=" + buffer.position() + ",limit=" + buffer.limit());
        buffer.putShort(svalue);

        buffer.flip();
        System.out.println("pos=" + buffer.position() + ",limit=" + buffer.limit());

        for (int i = 0; i < 4; i++) {
            System.out.printf("0x%x\t", buffer.get());
        }
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.printf("0x%x\t", buffer.get());
        }
        System.out.println();
        for (int i = 0; i < 2; i++) {
            System.out.printf("0x%x\t", buffer.get());
        }
        System.out.println();
    }

}
