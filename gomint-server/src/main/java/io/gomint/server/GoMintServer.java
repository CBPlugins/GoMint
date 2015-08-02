/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server;

import io.gomint.GoMint;
import io.gomint.plugin.PluginManager;
import io.gomint.raknet.RakPeerInterface;
import io.gomint.raknet.SocketDescriptor;
import io.gomint.raknet.StartupResult;
import io.gomint.server.plugin.SimplePluginManager;
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

        this.peerInterface = RakPeerInterface.getInstance();
        StartupResult result = this.peerInterface.startup( 1, new SocketDescriptor[0], -1 );
        logger.info( "Peer interface started up with return code: " + result.toString() );
        this.peerInterface.setMaximumIncomingConnections( 1 );
        this.peerInterface.close();

        // ------------------------------------ //
        // Plugin Management
        // ------------------------------------ //
        this.pluginManager = new SimplePluginManager();
    }
}
