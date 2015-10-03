/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin.event;

import io.gomint.plugin.event.CancelableEvent;
import io.gomint.plugin.event.Event;
import io.gomint.plugin.event.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fabian
 * @version 1.0
 */
public class LocalEventBus {
    private final Map<Class<?>, Map<Byte, Map<Object, Method[]>>> byListenerAndPriority = new ConcurrentHashMap<>();
    private final Map<Class<?>, EventHandlerMethod[]> byEventBaked = new ConcurrentHashMap<>();
    private final Logger logger;

    public LocalEventBus( Logger logger ) {
        this.logger = ( logger == null ) ? Logger.getGlobal() : logger;
    }

    public <T extends Event> T post( T event ) {
        logger.finest( "Posting event " + event.toString() );

        EventHandlerMethod[] handlers = byEventBaked.get( event.getClass() );
        if ( handlers != null ) {
            for ( EventHandlerMethod method : handlers ) {
                if ( event instanceof CancelableEvent && method.getAnnotation().ignoreCancelled() && ( (CancelableEvent) event ).isCancelled() ) {
                    logger.finest( "Ignoring method " + method.getListener().getClass().getName() + "." + method.getMethod().getName() + " due to its ignoring Annotation" );
                    continue;
                }

                logger.finest( "Visiting method " + method.getListener().getClass().getName() + "." + method.getMethod().getName() );

                try {
                    method.invoke( event );
                } catch ( IllegalAccessException ex ) {
                    logger.log( Level.WARNING, "Method became inaccessible: " + event, ex );
                } catch ( IllegalArgumentException ex ) {
                    logger.log( Level.WARNING, "Method rejected target/argument: " + event, ex );
                } catch ( InvocationTargetException ex ) {
                    logger.log( Level.WARNING, MessageFormat.format( "Error dispatching event {0} to listener {1}", event, method.getListener() ), ex.getCause() );
                }

                logger.finest( "Event after visting: " + event.toString() );
            }
        }

        return event;
    }

    private Map<Class<?>, Map<Byte, Set<Method>>> findHandlers( Object listener ) {
        Map<Class<?>, Map<Byte, Set<Method>>> handler = new HashMap<>();
        for ( Method m : listener.getClass().getDeclaredMethods() ) {
            EventHandler annotation = m.getAnnotation( EventHandler.class );
            if ( annotation != null ) {
                Class<?>[] params = m.getParameterTypes();
                if ( params.length != 1 ) {
                    logger.log( Level.INFO, "Method {0} in class {1} annotated with {2} does not have single argument", new Object[]{ m, listener.getClass(), annotation } );
                    continue;
                }

                Map<Byte, Set<Method>> prioritiesMap = handler.get( params[0] );
                if ( prioritiesMap == null ) {
                    prioritiesMap = new HashMap<>();
                    handler.put( params[0], prioritiesMap );
                }

                Set<Method> priority = prioritiesMap.get( annotation.priority() );
                if ( priority == null ) {
                    priority = new HashSet<>();
                    prioritiesMap.put( annotation.priority(), priority );
                }

                priority.add( m );
            }
        }

        return handler;
    }

    public void register( Object listener ) {
        Map<Class<?>, Map<Byte, Set<Method>>> handler = findHandlers( listener );
        for ( Map.Entry<Class<?>, Map<Byte, Set<Method>>> e : handler.entrySet() ) {
            Map<Byte, Map<Object, Method[]>> prioritiesMap = byListenerAndPriority.get( e.getKey() );
            if ( prioritiesMap == null ) {
                prioritiesMap = new HashMap<>();
                byListenerAndPriority.put( e.getKey(), prioritiesMap );
            }

            for ( Map.Entry<Byte, Set<Method>> entry : e.getValue().entrySet() ) {
                Map<Object, Method[]> currentPriorityMap = prioritiesMap.get( entry.getKey() );
                if ( currentPriorityMap == null ) {
                    currentPriorityMap = new HashMap<>();
                    prioritiesMap.put( entry.getKey(), currentPriorityMap );
                }
                Method[] baked = new Method[entry.getValue().size()];
                currentPriorityMap.put( listener, entry.getValue().toArray( baked ) );
            }

            bakeHandlers( e.getKey() );
        }
    }

    public void unregister( Object listener ) {
        Map<Class<?>, Map<Byte, Set<Method>>> handler = findHandlers( listener );
        for ( Map.Entry<Class<?>, Map<Byte, Set<Method>>> e : handler.entrySet() ) {
            Map<Byte, Map<Object, Method[]>> prioritiesMap = byListenerAndPriority.get( e.getKey() );
            if ( prioritiesMap != null ) {
                for ( Byte priority : e.getValue().keySet() ) {
                    Map<Object, Method[]> currentPriority = prioritiesMap.get( priority );
                    if ( currentPriority != null ) {
                        currentPriority.remove( listener );
                        if ( currentPriority.isEmpty() ) {
                            prioritiesMap.remove( priority );
                        }
                    }
                }
                if ( prioritiesMap.isEmpty() ) {
                    byListenerAndPriority.remove( e.getKey() );
                }
            }
            bakeHandlers( e.getKey() );
        }
    }

    /**
     * Shouldn't be called without first locking the writeLock; intended for use
     * only inside {@link #register(Object)} or
     * {@link #unregister(Object)}.
     */
    private void bakeHandlers( Class<?> eventClass ) {
        Map<Byte, Map<Object, Method[]>> handlersByPriority = byListenerAndPriority.get( eventClass );
        if ( handlersByPriority != null ) {
            List<EventHandlerMethod> handlersList = new ArrayList<>( handlersByPriority.size() * 2 );

            byte value = Byte.MIN_VALUE;
            do {
                Map<Object, Method[]> handlersByListener = handlersByPriority.get( value );
                if ( handlersByListener != null ) {
                    for ( Map.Entry<Object, Method[]> listenerHandlers : handlersByListener.entrySet() ) {
                        for ( Method method : listenerHandlers.getValue() ) {
                            EventHandlerMethod ehm = new EventHandlerMethod( listenerHandlers.getKey(), method, method.getAnnotation( EventHandler.class ) );
                            handlersList.add( ehm );
                        }
                    }
                }
            } while ( value++ < Byte.MAX_VALUE );

            byEventBaked.put( eventClass, handlersList.toArray( new EventHandlerMethod[handlersList.size()] ) );
        } else {
            byEventBaked.remove( eventClass );
        }
    }
}