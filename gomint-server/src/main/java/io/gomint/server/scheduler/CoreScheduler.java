/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.scheduler;

import io.gomint.scheduler.Scheduler;
import io.gomint.scheduler.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabian
 * @version 1.0
 */
public class CoreScheduler implements Scheduler {
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public Task execute( Runnable runnable ) {
        Task task = new ScheduledTask( runnable, 0, 0, TimeUnit.MILLISECONDS );


        return task;
    }

    @Override
    public Task schedule( Runnable runnable, long delay, TimeUnit timeUnit ) {
        return null;
    }

    @Override
    public Task schedule( Runnable runnable, long delay, long period, TimeUnit timeUnit ) {
        return null;
    }
}
