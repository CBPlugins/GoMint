/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.server.network.PacketData;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Packet {
    public abstract void read(PacketData packetData);
    public abstract void write(PacketData packetData);
}
