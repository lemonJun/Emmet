/*
 * Copyright (c) 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.takin.emmet.io.traffic.shm.queue;

import com.takin.emmet.io.traffic.util.Assert;
import com.takin.emmet.io.traffic.util.UNSAFE;
import com.takin.emmet.io.traffic.util.Util;

/**
 *
 *                  | ------ 4 bytes aligned ------ |
 *  +---------------+---------------+---------------+---------------+
 *  |0x1|0x2|0x3|0x4|0x1|0x2|0x3|0x4|0x1|0x2|0x3|   |0x1|0x2|0x3|0x4|
 *  +---------------+---------------+---------------+---------------+
 *  | --- length -- | ---------- payload ---------- | ---- ACK ---  |
 *  | --------------------------- block --------------------------- |
 *
 *
 * @author cuiyi
 */
public class Block {

    private static final int PADDING = Constant.INT_SIZE * 2;
    private static final int BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);

    private final int length;
    private final byte[] payload;

    public Block(byte[] payload) {
        Assert.notEmpty(payload);
        this.length = payload.length;
        this.payload = payload;
    }

    public static Block load(long capacity, long address, long offset) {
        long available = capacity - offset;
        if (available < Constant.INT_SIZE) {
            return load(address + Metadata.DATA_OFFSET);
        } else {
            int length = getInt(address + offset);
            offset += Constant.INT_SIZE;
            available -= Constant.INT_SIZE;

            byte[] payload = new byte[length];

            if (available >= length) {
                getBytes(address + offset, payload, length);
                return new Block(payload);
            } else {
                if (available > 0) {
                    getBytes(address + offset, payload, available);
                }
                getBytes(address + Metadata.DATA_OFFSET, payload, available, length - available);

                return new Block(payload);
            }
        }
    }

    public void append(long overflow, long capacity, long address, long offset) {
        if (overflow <= 0) {
            //no overflow
            append(address + offset);
            return;
        } else {
            if (overflow <= Constant.INT_SIZE) {
                //ack overflow
                putInt(address + offset, length);
                setBytes(payload, address + offset + Constant.INT_SIZE, length);
                putShort(address + Metadata.DATA_OFFSET, ACK.SEGMENT);
                return;
            } else if (overflow == sizeof() - Constant.INT_SIZE) {
                //append length only
                putInt(address + offset, length);
                setBytes(payload, address + Metadata.DATA_OFFSET, length);
                putShort(address + Metadata.DATA_OFFSET + align(length), ACK.SEGMENT);
                return;
            } else if (overflow >= sizeof()) {
                //whole block overflow
                append(address + Metadata.DATA_OFFSET);
                return;
            } else {
                //payload overflow
                long available = capacity - offset;

                putInt(address + offset, length);
                offset += Constant.INT_SIZE;
                available -= Constant.INT_SIZE;

                if (available > 0) {
                    setBytes(payload, address + offset, available);
                }
                setBytes(payload, available, address + Metadata.DATA_OFFSET, length - available);
                offset = Metadata.DATA_OFFSET + align(length - available);

                putShort(address + offset, ACK.SEGMENT);
                return;
            }
        }
    }

    private void append(long address) {
        putInt(address, length);
        address += Constant.INT_SIZE;

        setBytes(payload, address, length);
        address += align(length);

        putShort(address, ACK.SEGMENT);
    }

    private static Block load(long address) {
        int length = getInt(address);
        address += Constant.INT_SIZE;

        byte[] payload = new byte[length];
        getBytes(address, payload, length);

        return new Block(payload);
    }

    public void ack(long address, long offset) {
        UNSAFE.putUnsignedShort(address + offset, ACK.SEGMENT);
    }

    public long sizeof() {
        return cost(length);
    }

    public byte[] getPayload() {
        return payload;
    }

    private static long align(long length) {
        return Util.align(length, Constant.SIZE);
    }

    private static long cost(long length) {
        return align(length) + PADDING;
    }

    private static void putShort(long address, int value) {
        UNSAFE.putUnsignedShort(address + Constant.SHORT_SIZE, value);
    }

    private static void putInt(long address, int value) {
        UNSAFE.putInt(address, value);
    }

    private static int getInt(long address) {
        return UNSAFE.getInt(address);
    }

    private static void setBytes(Object src, long address, long length) {
        UNSAFE.copyMemory(src, BYTE_ARRAY_OFFSET, null, address, length);
    }

    private static void getBytes(long address, Object dst, long length) {
        UNSAFE.copyMemory(null, address, dst, BYTE_ARRAY_OFFSET, length);
    }

    private static void setBytes(Object src, long srcOffset, long address, long length) {
        UNSAFE.copyMemory(src, BYTE_ARRAY_OFFSET + srcOffset, null, address, length);
    }

    private static void getBytes(long address, Object dst, long dstOffset, long length) {
        UNSAFE.copyMemory(null, address, dst, BYTE_ARRAY_OFFSET + dstOffset, length);
    }

}
