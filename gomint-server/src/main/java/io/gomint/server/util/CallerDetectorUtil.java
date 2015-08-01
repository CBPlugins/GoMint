package io.gomint.server.util;

/**
 * @author Fabian
 * @version 1.0
 */
public class CallerDetectorUtil {
    private static CallerDetector callerDetector;

    static {
        try {
            Class.forName( "sun.reflect.Reflection" );
            callerDetector = new SunReflectionCallerDetector();
        } catch ( ClassNotFoundException e ) {
            callerDetector = new SecurityManagerCallerDetector();
        }
    }

    /**
     * Get the class name of the Caller
     *
     * @param callDepth depth at which we want to look
     * @return string of class which called
     */
    public static String getCallerClassName( int callDepth ) {
        return callerDetector.getCallerClassName( callDepth + 3 );
    }
}
