package io.gomint.server.util;

/**
 * @author Fabian
 * @version 1.0
 */
public class SunReflectionCallerDetector implements CallerDetector {
    @Override
    public String getCallerClassName( int callDepth ) {
        return sun.reflect.Reflection.getCallerClass( callDepth ).getName();
    }
}
