/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Fabian
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = false )
@Data
@ToString( callSuper = true )
public class CancelableEvent extends Event {
    private boolean cancelled = false;
}
