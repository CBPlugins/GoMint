/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin.event;

import io.gomint.plugin.event.EventHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Fabian
 * @version 1.0
 */
@AllArgsConstructor
public class EventHandlerMethod {
    @Getter private final Object listener;
    @Getter private final Method method;
    @Getter private final EventHandler annotation;

    public void invoke( Object event ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        method.invoke( listener, event );
    }
}
