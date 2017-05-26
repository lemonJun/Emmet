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

import com.takin.emmet.io.traffic.util.UNSAFE;

/**
 *
 *  | --------------------------------------------------------- metadata -------------------------------------------------------- |
 *  +-------------+---------------+---------------+---------------+-------------------------------+-------------------------------+---------------+
 *  |0x414E|0x4E41|0x1|0x2|0x3|0x4|0x1|0x2|0x1|0x2|0x1|0x2|0x3|0x4|0x1|0x2|0x3|0x4|0x5|0x6|0x7|0x8|0x1|0x2|0x3|0x4|0x5|0x6|0x7|0x8|0x1|0x2|0x3|0x4|
 *  +-------------+---------------+---------------+---------------+-------------------------------+-------------------------------+---------------+
 *  |-magic number|-minor-|-major-| ----- id ---- | --- index --- | -------- read cursor -------- | -------- write cursor-------- | --- origin -- |
 *  |             | -- version -- |               |               |                               |                               |               |
 *  0             4               8               12              16                              24                              32              36
 *
 * @author cuiyi
 */
public class Metadata {

    private static final int MAGIC_NUMBER = 0x414E4E41;
    private static final int MAGIC_NUMBER_OFFSET = 0;
    private static final int MINOR_VERSION_OFFSET = 4;
    private static final int MAJOR_VERSION_OFFSET = 6;
    private static final int ID_OFFSET = 8;
    private static final int INDEX_OFFSET = 12;
    private static final int READ_OFFSET = 16;
    private static final int WRITE_OFFSET = 24;
    private static final int ORIGIN_OFFSET = 32;

    private static final int READ_INITIAL_VALUE = ORIGIN_OFFSET; // 32
    private static final int WRITE_INITIAL_VALUE = ORIGIN_OFFSET + Constant.INT_SIZE; // 36

    public static final int DATA_OFFSET = ORIGIN_OFFSET; // 32

    private final long address;
    private final Cursor read;
    private final Cursor write;

    public Metadata(long address) {
        this.address = address;
        this.read = new Cursor(this.address, READ_OFFSET);
        this.write = new Cursor(this.address, WRITE_OFFSET);
    }

    public void initialize(int id, int index) {
        UNSAFE.putInt(address + MAGIC_NUMBER_OFFSET, MAGIC_NUMBER);

        if (setId(id) && setIndex(index) && read.update(0, Metadata.READ_INITIAL_VALUE) && write.update(0, Metadata.WRITE_INITIAL_VALUE)) {

            originate();
        }
    }

    private void originate() {
        UNSAFE.putUnsignedShort(address + ORIGIN_OFFSET + Constant.SHORT_SIZE, ACK.SEGMENT);
    }

    public boolean setId(int id) {
        return UNSAFE.compareAndSwapInt(address + ID_OFFSET, 0, id);
    }

    public int getId() {
        return UNSAFE.getIntVolatile(address + ID_OFFSET);
    }

    public boolean setIndex(int index) {
        return UNSAFE.compareAndSwapInt(address + INDEX_OFFSET, 0, index);
    }

    public int getIndex() {
        return UNSAFE.getIntVolatile(address + INDEX_OFFSET);
    }

    public String getVersion() {
        int minor = UNSAFE.getUnsignedShort(address + MINOR_VERSION_OFFSET);
        int major = UNSAFE.getUnsignedShort(address + MAJOR_VERSION_OFFSET);
        return major + "." + minor;
    }

    public Cursor readCursor() {
        return read;
    }

    public Cursor writeCursor() {
        return write;
    }
}
