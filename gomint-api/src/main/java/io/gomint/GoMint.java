/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint;

/**
 * @author Fabian
 * @version 1.0
 */
public interface GoMint {
    /**
     * Get the current set MOTD
     *
     * @return the current MOTD
     */
    String getMOTD();

    /**
     * Set a new MOTD which gets sent while pinging
     *
     * @param motd which should be used
     */
    void setMOTD( String motd );
}
