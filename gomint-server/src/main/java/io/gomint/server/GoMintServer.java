package io.gomint.server;

import io.gomint.GoMint;
import io.gomint.plugin.PluginManager;
import io.gomint.server.plugin.SimplePluginManager;
import lombok.Getter;

/**
 * @author Fabian
 * @version 1.0
 */
public class GoMintServer implements GoMint {
    @Getter private PluginManager pluginManager;

    public GoMintServer() {
        this.pluginManager = new SimplePluginManager();

        TestPlugin plugin = new TestPlugin();
        plugin.setPluginManager( this.pluginManager );
        plugin.onInstall();
        this.pluginManager.disablePlugin( plugin );
    }
}
