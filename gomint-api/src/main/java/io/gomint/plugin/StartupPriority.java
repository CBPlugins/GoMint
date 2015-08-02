/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.plugin;

import lombok.Getter;

/**
 * Set of possible priorities of plugin initialization order.
 * The default is {@link #LOAD}.
 *
 * @author BlackyPaw
 * @version 1.0
 */
@Getter
public enum StartupPriority implements Comparable<StartupPriority> {
    /**
     * Load a plugin on startup
     */
    STARTUP( 0 ),

    /**
     * Load a plugin on load of the startup ones. The plugins with this StartupPriority get loaded after {@link #STARTUP}
     */
    LOAD( 1 );

    private final int order;

    /**
     * Just a enum Construtor
     *
     * @param order which defines the sort order (ASC)
     */
    StartupPriority( final int order ) {
        this.order = order;
    }
}
