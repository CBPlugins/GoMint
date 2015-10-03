/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.server.network.PacketData;
import lombok.Getter;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class LoginPacket extends Packet {
    private String username;
    private int networkProtocol;
    private int iDontKnowWhatProtocol;
    private long clientID;
    private UUID userUUID;

    @Override
    public void read( PacketData packetData ) {

    }

    @Override
    public void write( PacketData packetData ) {

    }
}
