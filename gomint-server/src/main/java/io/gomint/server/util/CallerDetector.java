/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.util;

/**
 * @author Fabian
 * @version 1.0
 */
public interface CallerDetector {
    /**
     * Get the class name of the Caller
     *
     * @param callDepth depth at which we want to look
     * @return string of class which called
     */
    String getCallerClassName( int callDepth );
}



