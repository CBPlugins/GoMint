/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.server.network.PacketData;
import io.gomint.server.network.PacketHandler;

/**
 * @author geNAZt
 * @version 1.0
 */
public class DisconnectPacket extends Packet {
    @Override
    public void read( PacketData packetData ) {

    }

    @Override
    public void write( PacketData packetData ) {

    }

    @Override
    public void handle( PacketHandler packetHandler ) {
        packetHandler.handle( this );
    }
}
