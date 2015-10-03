/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.server.network.packet.DisconnectPacket;
import io.gomint.server.network.packet.LoginPacket;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketHandler {
    public void handle( LoginPacket loginPacket ) {
        System.out.println( loginPacket.toString() );
    }

    public void handle( DisconnectPacket disconnectPacket ) {

    }
}
