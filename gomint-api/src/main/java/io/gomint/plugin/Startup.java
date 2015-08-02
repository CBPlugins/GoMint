/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface Startup {
    StartupPriority value();
}
