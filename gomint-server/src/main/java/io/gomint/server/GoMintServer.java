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
import io.gomint.server.config.ListenerConfig;
import io.gomint.server.config.ServerConfig;
import io.gomint.server.network.NetworkHandler;
import io.gomint.server.network.PacketData;
import io.gomint.server.util.NativeSearchPathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        // Executor Initialization
        // ------------------------------------ //
        ExecutorService executorService = Executors.newCachedThreadPool();

        // ------------------------------------ //
        // Configuration Initialization
        // ------------------------------------ //
        ServerConfig serverConfig = new ServerConfig();

        try {
            serverConfig.initialize( new File( "server.cfg" ) );
        } catch ( IOException e ) {
            logger.error( "server.cfg is corrupted: ", e );
            System.exit( -1 );
        }

        // Add default listener
        if ( serverConfig.getListener().size() == 0 ) {
            serverConfig.getListener().add( new ListenerConfig( "0.0.0.0", 19132 ) );
        }

        /*try {
            serverConfig.write( new FileWriter( new File( "server.cfg" ) ) );
        } catch ( IOException e ) {
            logger.warn( "Could not save server.cfg: ", e );
        }*/

        // Startup the Networkhandler
        NetworkHandler networkHandler = new NetworkHandler( executorService );

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

        // Remap the config to corect SocketDescriptors
        SocketDescriptor[] socketDescriptors = new SocketDescriptor[serverConfig.getListener().size()];
        int index = 0;

        for ( ListenerConfig listenerConfig : serverConfig.getListener() ) {
            socketDescriptors[index] = new SocketDescriptor( listenerConfig.getPort(), listenerConfig.getIp() );
            index++;
        }

        // Startup the RakNet Natives
        this.peerInterface = RakPeerInterface.getInstance();
        StartupResult result = this.peerInterface.startup( 512, socketDescriptors, -1 );
        logger.info( "Peer interface started up with return code: " + result.toString() );
        this.peerInterface.setMaximumIncomingConnections( 512 );

        PacketDispatcher dispatcher = new PacketDispatcher() {
            @Override
            public void jniReceiveOnlinePacket( Connection connection, byte[] data ) {
                networkHandler.handleIncoming( connection, data );
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
