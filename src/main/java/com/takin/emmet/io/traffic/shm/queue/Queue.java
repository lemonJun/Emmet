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

import java.io.Closeable;
import java.io.IOException;

import com.takin.emmet.io.traffic.shm.file.MappedFile;
import com.takin.emmet.io.traffic.util.Assert;
import com.takin.emmet.io.traffic.util.UNSAFE;

/**
 * @author cuiyi
 */
public class Queue implements Closeable {

    private final MappedFile mappedFile;
    private final int id;
    private final int index;
    private final long capacity;
    private final long address;
    private final Metadata metadata;
    private final Cursor readCursor;
    private final Cursor writeCursor;

    private Queue(MappedFile mappedFile, int id, int index) {
        this.mappedFile = mappedFile;
        this.id = id;
        this.index = index;
        this.capacity = mappedFile.getSize();
        this.address = mappedFile.getAddress();
        this.metadata = new Metadata(this.address);
        this.readCursor = this.metadata.readCursor();
        this.writeCursor = this.metadata.writeCursor();
    }

    public static Queue map(String file, long size, int id, int index) {
        MappedFile mappedFile = MappedFile.with(file, size);
        return new Queue(mappedFile, id, index);
    }

    public void init() {
        metadata.initialize(id, index);
    }

    @Override
    public void close() throws IOException {
        if (mappedFile != null) {
            this.mappedFile.unmap();
        }
    }

    public Block poll() {
        long offset = readCursor.offset();
        int ack = UNSAFE.getIntVolatile(address + offset);

        if (ack == ACK.DATA) {
            return read(offset);
        }
        return null;
    }

    public boolean add(Block block) {
        if (offer(block)) {
            return true;
        } else {
            throw new IllegalStateException("Queue is full");
        }
    }

    public boolean offer(Block block) {
        Assert.notNull(block);
        Assert.notNull(block.getPayload());

        long offset = writeCursor.offset();
        long overflow = offset + block.sizeof() - capacity;
        if (overflow > 0) {
            return false;
        } else {
            return write(offset, block);
        }
    }

    public boolean put(Block block) {
        Assert.notNull(block);
        Assert.notNull(block.getPayload());

        long write_offset = writeCursor.offset();
        long read_offset = readCursor.offset();

        if (read_offset > write_offset && read_offset - write_offset - block.sizeof() <= 0) {
            return false;
        }
        return write(write_offset, block);
    }

    private Block read(long offset) {
        Block block = Block.load(capacity, address, offset + Constant.INT_SIZE);
        long shift = offset + block.sizeof();
        if (shift >= capacity) {
            shift = Metadata.DATA_OFFSET + shift % capacity;
        }
        if (readCursor.update(offset, shift)) {
            return block;
        }
        return null;
    }

    private boolean write(long offset, Block block) {
        long overflow = offset + block.sizeof() - capacity;
        long shift = overflow > 0 ? Metadata.DATA_OFFSET + overflow : offset + block.sizeof();
        if (!writeCursor.update(offset, shift)) {
            return false;
        }
        block.append(overflow, capacity, address, offset);
        block.ack(address, offset - Constant.INT_SIZE);
        return true;
    }

}
