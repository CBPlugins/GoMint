/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.server.network.packet.LoginPacket;
import io.gomint.server.network.packet.Packet;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
public enum Protocol {
    LOGIN( (byte) 0x8F, LoginPacket.class );

    private byte packetID;
    private Class<? extends Packet> packetClass;

    private static final Map<Byte, Class<? extends Packet>> packetIDLookup = new HashMap<>();
    static {
        for ( Protocol protocol : values() ) {
            packetIDLookup.put( protocol.packetID, protocol.packetClass );
        }
    }

    public static Class<? extends Packet> getPacketClass( byte packetID ) {
        return packetIDLookup.get( packetID );
    }
}
