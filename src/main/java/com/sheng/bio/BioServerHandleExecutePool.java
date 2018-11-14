package com.sheng.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by sheng on 12/11/2018
 *
 * @author sheng
 */
public class BioServerHandleExecutePool {

    private ExecutorService executorService;

    public BioServerHandleExecutePool(int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute (Runnable task) {
        executorService.execute(task);
    }
}
