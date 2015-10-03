/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server;

import io.gomint.GoMint;
import io.gomint.plugin.PluginManager;
import io.gomint.raknet.*;
import io.gomint.server.util.NativeSearchPathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Fabian
 * @version 1.0
 */
public class GoMintServer implements GoMint {
    private final Logger logger = LoggerFactory.getLogger( GoMintServer.class );

    private RakPeerInterface peerInterface;
    private PluginManager pluginManager;

    public GoMintServer() {
        // ------------------------------------ //
        // RakNet Initialization
        // ------------------------------------ //

        // We always look for natives inside a natives folder relative to the CWD:
        try {
            File nativesFolder = new File( "natives/" );
            if ( !nativesFolder.exists() ) {
                if ( !nativesFolder.mkdir() ) {
                    throw new AssertionError( "Failed to create natives directory; please double-check your filesystem permissions!" );
                }
            }

            NativeSearchPathUtil.addLibraryPath( "natives" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        // Check for windows and 64bit for correct natives
        boolean isWindows = ( System.getProperty( "os.name" ).toLowerCase().contains( "win" ) );
        boolean isX64 = ( System.getProperty( "sun.arch.data.model" ).equals( "64" ) );
        logger.info( "Using JNI Settings: Windows -> " + isWindows + "; 64bit -> " + isX64 );

        this.peerInterface = RakPeerInterface.getInstance();
        StartupResult result = this.peerInterface.startup( 1, new SocketDescriptor[] { new SocketDescriptor( 19132, "0.0.0.0" ) }, -1 );
        logger.info( "Peer interface started up with return code: " + result.toString() );
        this.peerInterface.setMaximumIncomingConnections( 512 );

        PacketDispatcher dispatcher = new PacketDispatcher() {
            @Override
            public void jniReceiveOnlinePacket( Connection connection, byte[] data ) {

            }
        };

        this.peerInterface.setDispatcher( dispatcher );

        long t = 0;
        while ( true ) {
            setMOTD( "CPU Usage: " + t++ );

            try {
                Thread.sleep( 50 );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }

        //this.peerInterface.close();

        // ------------------------------------ //
        // Plugin Management
        // ------------------------------------ //
        //this.pluginManager = new SimplePluginManager();
    }

    @Override
    public String getMOTD() {
        return this.peerInterface.getMotd();
    }

    @Override
    public void setMOTD( String motd ) {
        this.peerInterface.setMotd( motd );
    }
}
