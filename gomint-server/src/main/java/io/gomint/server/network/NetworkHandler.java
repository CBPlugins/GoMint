/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.raknet.Connection;
import io.gomint.server.network.packet.BatchPacket;
import io.gomint.server.network.packet.DisconnectPacket;
import io.gomint.server.network.packet.Packet;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author geNAZt
 * @version 1.0
 */
public class NetworkHandler {
    private final Logger logger = LoggerFactory.getLogger( NetworkHandler.class );

    private Map<Long, PacketHandler> packetHandlers = new ConcurrentHashMap<>();
    private AtomicBoolean running = new AtomicBoolean( true );
    private BlockingQueue<DataToHandle> dataToHandleQueue = new LinkedBlockingQueue<>();

    public NetworkHandler( Executor executor ) {
        this.startDecodeLoop( executor );
    }

    private void startDecodeLoop( Executor executor ) {
        executor.execute( new Runnable() {
            @Override
            public void run() {
                while( running.get() ) {
                    try {
                        DataToHandle dataToHandle = dataToHandleQueue.poll( 500, TimeUnit.MILLISECONDS );
                        if ( dataToHandle != null ) {
                            decodePacket( dataToHandle.connection, new PacketData( dataToHandle.data ) );
                        }
                    } catch ( InterruptedException e ) {
                        // Ignored
                    }
                }
            }
        } );
    }

    public void decodePacket( Connection connection, PacketData packetData ) {
        byte packetID = packetData.readByte();

        // Special case batch packets
        if ( packetID == (byte) 0x92 ) {
            BatchPacket batchPacket = new BatchPacket( this, connection );
            batchPacket.read( packetData );
        } else if ( packetID == (byte) 0x13 ) {
            packetHandlers.put( connection.getGuid(), new PacketHandler() );
        } else if ( packetID == (byte) 0x15 ) {
            PacketHandler packetHandler = packetHandlers.remove( connection.getGuid() );
            if ( packetHandler != null ) {
                packetHandler.handle( new DisconnectPacket() );
            }
        } else {
            Class<? extends Packet> packetClass = Protocol.getPacketClass( packetID );
            if ( packetClass == null ) return;

            try {
                Packet packet = packetClass.newInstance();
                packet.read( packetData );
                packet.handle( packetHandlers.get( connection.getGuid() ) );
            } catch ( InstantiationException | IllegalAccessException e ) {
                e.printStackTrace();
            }
        }
    }

    public void handleIncoming( Connection connection, byte[] data ) {
        dataToHandleQueue.offer( new DataToHandle( connection, data ) );
        logger.info( connection.getIp() + ": " + bytesToHex( data ) );
    }

    @AllArgsConstructor
    private final class DataToHandle {
        private Connection connection;
        private byte[] data;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public String bytesToHex( byte[] bytes ) {
        char[] hexChars = new char[bytes.length * 3];

        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }

        return new String(hexChars);
    }
}
