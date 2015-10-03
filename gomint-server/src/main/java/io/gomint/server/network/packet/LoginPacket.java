/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.server.network.PacketData;
import io.gomint.server.network.PacketHandler;
import lombok.Getter;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@ToString
@Getter
public class LoginPacket extends Packet {
    private String username;
    private int networkProtocol;
    private int iDontKnowWhatProtocol;
    private long clientID;
    private long raknetID;
    private long raknetID2;
    private String serverAddress;
    private String clientSecret;
    private boolean slim;
    private byte[] skin;

    @Override
    public void read( PacketData packetData ) {
        this.username = packetData.readString();
        this.networkProtocol = packetData.readInt();
        this.iDontKnowWhatProtocol = packetData.readInt();
        this.clientID = packetData.readLong();
        this.raknetID = packetData.readLong();
        this.raknetID2 = packetData.readLong();
        this.serverAddress = packetData.readString();
        this.clientSecret = packetData.readString();
        this.slim = packetData.readBoolean();

        int skinLength = packetData.readShort();
        this.skin = packetData.readBytes( skinLength );
    }

    @Override
    public void write( PacketData packetData ) {

    }

    @Override
    public void handle( PacketHandler packetHandler ) {
        packetHandler.handle( this );
    }
}
