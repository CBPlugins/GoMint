/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server;

import io.gomint.GoMint;
import io.gomint.plugin.PluginManager;
import io.gomint.server.plugin.SimplePluginManager;

/**
 * @author Fabian
 * @version 1.0
 */
public class GoMintServer implements GoMint {
    private PluginManager pluginManager;

    public GoMintServer() {
        this.pluginManager = new SimplePluginManager();

        TestPlugin plugin = new TestPlugin();
        plugin.setPluginManager( this.pluginManager );
        plugin.onInstall();
        this.pluginManager.disablePlugin( plugin );
    }
}
