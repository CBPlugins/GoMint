package io.gomint.server.scheduler;

import io.gomint.plugin.Plugin;
import io.gomint.scheduler.Scheduler;
import io.gomint.scheduler.Task;
import io.gomint.util.ExceptionHandler;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Fabian
 * @version 1.0
 */
@AllArgsConstructor
public class PluginScheduler implements Scheduler {
    private Plugin plugin;
    private CoreScheduler coreScheduler;

    private Set<Task> runningTasks = new HashSet<>();
    private ReentrantLock lock = new ReentrantLock( true );

    @Override
    public Task execute( Runnable runnable ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        lock.lock();

        try {
            Task task = coreScheduler.execute( runnable );
            task.onException( new ExceptionHandler() {
                @Override
                public boolean onException( Exception e ) {
                    plugin.getLogger().warn( "A task thrown a Exception", e );
                    return true;
                }
            } );

            this.runningTasks.add( task );
            return task;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Task schedule( Runnable runnable, long delay, TimeUnit timeUnit ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        lock.lock();

        try {
            Task task = coreScheduler.schedule( runnable, delay, timeUnit );
            task.onException( new ExceptionHandler() {
                @Override
                public boolean onException( Exception e ) {
                    plugin.getLogger().warn( "A task thrown a Exception", e );
                    return true;
                }
            } );

            this.runningTasks.add( task );
            return task;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Task schedule( Runnable runnable, long delay, long period, TimeUnit timeUnit ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        lock.lock();

        try {
            Task task = coreScheduler.schedule( runnable, delay, period, timeUnit );
            task.onException( new ExceptionHandler() {
                @Override
                public boolean onException( Exception e ) {
                    plugin.getLogger().warn( "A task thrown a Exception", e );
                    return true;
                }
            } );

            this.runningTasks.add( task );
            return task;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Internal Method for cleaning up all Tasks
     */
    public void cleanup() {
        for ( Task runningTask : this.runningTasks ) {
            runningTask.cancel();
        }

        this.runningTasks.clear();
        this.plugin = null;
        this.coreScheduler = null;
    }
}
