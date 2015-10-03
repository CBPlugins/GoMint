/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.raknet.Connection;
import io.gomint.server.network.NetworkHandler;
import io.gomint.server.network.PacketData;
import lombok.RequiredArgsConstructor;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class BatchPacket extends Packet {
    private final NetworkHandler networkHandler;
    private final Connection connection;
    private Inflater inflater = new Inflater();

    @Override
    public void read( PacketData packetData ) {
        // Read compressed payload
        int size = packetData.readInt();
        byte[] payload = packetData.readBytes( size );

        // Decompress it
        inflater.setInput( payload );
        byte[] decompressedPayload = new byte[1024 * 1024 * 64];
        int decompressedSize = 0;

        try {
            decompressedSize = inflater.inflate(decompressedPayload);
        } catch (DataFormatException ex) {
            return;
        }

        inflater.end();

        // Copy over real payload
        byte[] realPayload = new byte[decompressedSize];
        System.arraycopy( decompressedPayload, 0, realPayload, 0, decompressedSize );

        // Read out all Packets
        PacketData packetData1 = new PacketData( realPayload );
        while( !packetData1.isEOF() ) {
            int pktLength = packetData1.readInt();
            byte[] pktData = packetData1.readBytes( pktLength );
            networkHandler.handleIncoming( connection, pktData );
        }
    }

    @Override
    public void write( PacketData packetData ) {

    }
}
