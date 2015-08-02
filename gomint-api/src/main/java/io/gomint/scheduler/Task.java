/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.scheduler;

import io.gomint.util.ExceptionHandler;

/**
 * @author Fabian
 * @version 1.0
 */
public interface Task {
    /**
     * Cancel the Task. This interrupts the Thread which is executing the Task
     */
    void cancel();

    /**
     * Register a new exceptionHandler to fetch Exceptions
     *
     * @param exceptionHandler which should be used to handle Exceptions
     */
    void onException( ExceptionHandler exceptionHandler );
}
