package com.zack.basic.concurrent.threadpool;

import java.util.concurrent.*;


/**
 * Created by Zack on 2017/6/11.
 */

public class ThreadPoolManager {
    public static final String TAG = "ThreadPoolManager";

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static ThreadPoolManager instance = new ThreadPoolManager();

    private LinkedBlockingQueue<Future<?>> taskQueue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor threadPoolExecutor;

    public static ThreadPoolManager getInstance(){
        return instance;
    }

    private ThreadPoolManager(){
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),handler);
        threadPoolExecutor.execute(runnable);
    }

    public <T> void excute(FutureTask<T> futureTask) throws InterruptedException {
        taskQueue.put(futureTask);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                FutureTask futureTask = null;
                try {
                    /**
                     * 阻塞式函数
                     */
                    System.out.println(TAG+"等待队列："+taskQueue.size());
                    futureTask = (FutureTask) taskQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureTask!=null){
                    threadPoolExecutor.execute(futureTask);
                }
            }
        }
    };

    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                taskQueue.put(new FutureTask<Object>(r,null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


}
