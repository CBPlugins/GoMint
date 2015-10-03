/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Fabian
 * @version 1.0
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface EventHandler {
    /**
     * Define the priority of the event handler.
     * <p/>
     * Event handlers are called in order of priority:
     * <ol>
     * <li>LOWEST</li>
     * <li>LOW</li>
     * <li>NORMAL</li>
     * <li>HIGH</li>
     * <li>HIGHEST</li>
     * </ol>
     */
    byte priority() default EventPriority.NORMAL;

    /**
     * Define to true if you don't want to get cancelled Events
     */
    boolean ignoreCancelled() default false;
}
