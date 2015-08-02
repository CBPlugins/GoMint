/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
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
