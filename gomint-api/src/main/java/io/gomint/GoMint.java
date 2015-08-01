package io.gomint;

import io.gomint.plugin.PluginManager;

/**
 * @author Fabian
 * @version 1.0
 */
public interface GoMint {
    /**
     * Get the PluginManager used by this GoMint Server to load and manage Plugins.
     *
     * @return
     */
    PluginManager getPluginManager();
}
