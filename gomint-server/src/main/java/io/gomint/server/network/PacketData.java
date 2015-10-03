/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketData {
    private byte[] packetData;
    private int cursor;

    // Used to create the initial Buffer. Also used to realloc a new Buffer if needed
    private int preallocSize;

    public PacketData( byte[] packetData ) {
        this.packetData = packetData;
    }

    public PacketData( int preallocSize ) {
        this.packetData = new byte[preallocSize];
    }

    private void ensureWrite( int needed ) {
        if ( packetData.length < this.cursor + needed ) {
            byte[] realloc = new byte[this.cursor + this.preallocSize];
            System.arraycopy( this.packetData, 0, realloc, 0, this.packetData.length );
            this.packetData = realloc;
        }
    }

    private void ensureRead( int needed ) {
        if ( packetData.length < this.cursor + needed ) {
            throw new IllegalStateException( "Reading packet behind its size; Size: " + packetData.length + "; Cursor: " + this.cursor + "; Needed: " + needed );
        }
    }

    public int readInt() {
        ensureRead( 4 );

        int value = 0;
        value += (readByte() & 0xFF) << 24;
        value += (readByte() & 0xFF) << 16;
        value += (readByte() & 0xFF) << 8;
        value += (readByte() & 0xFF);

        return value;
    }

    public String readString() {
        short strLength = readShort();
        byte[] bytes = readBytes( strLength );
        return new String( bytes );
    }

    public int readTriad() {
        ensureRead( 3 );

        int value = 0;
        value += (readByte() & 0xFF);
        value += (readByte() & 0xFF) << 8;
        value += (readByte() & 0xFF) << 16;

        return value;
    }

    public short readShort() {
        ensureRead( 2 );

        short value = 0;
        value += (readByte() & 0xFF) << 8;
        value += (readByte() & 0xFF);
        return value;
    }

    public byte[] readBytes() {
        byte size = readByte();
        return readBytes( size );
    }

    public byte readByte() {
        ensureRead( 1 );
        return this.packetData[this.cursor++];
    }

    public byte[] readBytes( int size ) {
        ensureRead( size );

        byte[] bytes = new byte[size];
        for ( int i = 0; i < size; i++ ) {
            bytes[i] = readByte();
        }

        return bytes;
    }

    public int getPacketSize() {
        return this.packetData.length;
    }

    public boolean isEOF() {
        return this.cursor == this.packetData.length;
    }

    public void resetCursor() {
        this.cursor = 0;
    }

    public long readLong() {
        ensureRead( 8 );

        long value = 0;
        value += (readByte() & 0xFF) << 56;
        value += (readByte() & 0xFF) << 48;
        value += (readByte() & 0xFF) << 40;
        value += (readByte() & 0xFF) << 32;
        value += (readByte() & 0xFF) << 24;
        value += (readByte() & 0xFF) << 16;
        value += (readByte() & 0xFF) << 8;
        value += (readByte() & 0xFF);

        return value;
    }

    public UUID readUUID() {
        return new UUID( readLong(), readLong() );
    }

    public boolean readBoolean() {
        return (readByte() & 0xF) > 0;
    }
}
