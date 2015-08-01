package io.gomint.scheduler;

import java.util.concurrent.TimeUnit;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public interface Scheduler {
    /**
     * Run the runnable in another Thread
     *
     * @param runnable which should be executed
     * @return the created and scheduled Task
     */
    Task execute( Runnable runnable );

    /**
     * Executes a runnable with a delay. It blocks the Thread for the time until the delay arrives
     *
     * @param runnable which should be executed
     * @param delay amount of timeUnit which should be used for waiting
     * @param timeUnit which should be used to multiply the delay
     * @return the created and scheduled Task
     */
    Task schedule( Runnable runnable, long delay, TimeUnit timeUnit );

    /**
     * Executes a runnable with a delay. It blocks the Thread for the time until the delay arrives. After the
     * execution of the Runnable it gets rescheduled again in an infinite Loop. You can cancel the returned Task
     * to stop that. It uses the same Thread over and over for rescheduling the Task.
     *
     * @param runnable which should be executed
     * @param delay amount of timeUnit which should be used for waiting
     * @param period amount of timeUnit which should be used for rescheduling the runnable
     * @param timeUnit which should be used to multiply the delay / period
     * @return the created and scheduled Task
     */
    Task schedule( Runnable runnable, long delay, long period, TimeUnit timeUnit );
}
