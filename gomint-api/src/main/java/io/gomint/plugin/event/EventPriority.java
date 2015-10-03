/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Fabian
 * @version 1.0
 *
 * Importance of the {@link EventHandler}. When executing an Event, the handlers
 * are called in order of their Priority.
 */
@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class EventPriority {
    public static final byte LOWEST = -64;
    public static final byte LOW = -32;
    public static final byte NORMAL = 0;
    public static final byte HIGH = 32;
    public static final byte HIGHEST = 64;
}
