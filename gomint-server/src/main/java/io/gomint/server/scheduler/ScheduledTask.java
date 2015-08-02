/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.scheduler;

import io.gomint.scheduler.Task;
import io.gomint.util.ExceptionHandler;
import lombok.Setter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Fabian
 * @version 1.0
 */
public class ScheduledTask implements Task, Runnable {
    private final Runnable task;

    private final long delay;
    private final long period;
    private final AtomicBoolean running = new AtomicBoolean( true );

    @Setter private ExceptionHandler exceptionHandler;
    private Thread executingThread;

    /**
     * Constructs a new ScheduledTask. It needs to be executed via a normal {@link java.util.concurrent.ExecutorService}
     *
     * @param task runnable which should be executed
     * @param delay of this execution
     * @param period delay after execution to run the runnable again
     * @param unit of time
     */
    public ScheduledTask( Runnable task, long delay, long period, TimeUnit unit ) {
        this.task = task;
        this.delay = unit.toMillis( delay );
        this.period = unit.toMillis( period );
    }

    @Override
    public void cancel() {
        this.running.set( false );
        this.executingThread.interrupt();
    }

    @Override
    public void onException( ExceptionHandler exceptionHandler ) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run() {
        this.executingThread = Thread.currentThread();

        if ( delay > 0 ) {
            try {
                Thread.sleep( delay );
            } catch ( InterruptedException ex ) {
                this.executingThread.interrupt();
            }
        }

        while ( running.get() ) {
            try {
                task.run();
            } catch ( Exception e ) {
                if ( exceptionHandler != null ) {
                    if ( !exceptionHandler.onException( e ) ) {
                        return;
                    }
                } else {
                    e.printStackTrace();
                }
            }

            // If we have a period of 0 or less, only run once
            if ( period <= 0 ) {
                break;
            }

            try {
                Thread.sleep( period );
            } catch ( InterruptedException ex ) {
                this.executingThread.interrupt();
            }
        }
    }
}