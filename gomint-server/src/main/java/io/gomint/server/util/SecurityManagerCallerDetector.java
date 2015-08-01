package io.gomint.server.util;

/**
 * @author Fabian
 * @version 1.0
 */
public class SecurityManagerCallerDetector implements CallerDetector {
    public String getCallerClassName( int callDepth ) {
        return mySecurityManager.getCallerClassName( callDepth );
    }

    /**
     * A custom security manager that exposes the getClassContext() information
     */
    static class MySecurityManager extends SecurityManager {
        public String getCallerClassName( int callStackDepth ) {
            return getClassContext()[callStackDepth].getName();
        }
    }

    private final static MySecurityManager mySecurityManager = new MySecurityManager();
}
