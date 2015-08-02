/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.plugin;

import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginManager;
import io.gomint.server.util.CallerDetectorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fabian
 * @version 1.0
 */
public class SimplePluginManager implements PluginManager {
    private final Logger logger = LoggerFactory.getLogger( SimplePluginManager.class );

    @Override
    public void disablePlugin( Plugin plugin ) {
        // Check for security
        if ( !CallerDetectorUtil.getCallerClassName( 2 ).equals( plugin.getClass().getName() ) ) {
            throw new SecurityException( "Plugins can only disable themselves" );
        }
    }

    @Override
    public String getBaseDirectory() {
        return null;
    }
}
