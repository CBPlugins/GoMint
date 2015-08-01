package io.gomint.util;

/**
 * @author Fabian
 * @version 1.0
 *
 * Interface which should be used to catch Exceptions thrown. This is mainly used in the Scheduler for Exception handling
 */
public interface ExceptionHandler {
    /**
     * Fired when a exception has been thrown. The return value of the call decides if the Code gets executed further
     * or not
     *
     * @param e the thrown Exception
     * @return true when the code should be executed further, false if not
     */
    boolean onException( Exception e );
}
