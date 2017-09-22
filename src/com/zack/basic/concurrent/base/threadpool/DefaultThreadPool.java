package com.zack.basic.concurrent.base.threadpool;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    //线程池最大限制数
    private static final int MAX_WORKER_NUMBERS = 10;

    //线程池默认的数量
    private static final int DEFAULT_WORKER_NUMBERS = 5;

    //线程池最小的数量
    private static final int MIN_WORKER_NUMBERS = 1;

    //任务列表
    private final LinkedList<Job> jobs = new LinkedList<>();

    //工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

    //工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;

    //线程编号生成
    private AtomicInteger threadNum = new AtomicInteger();

    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        workerNum = num>MAX_WORKER_NUMBERS?MAX_WORKER_NUMBERS:(num<MIN_WORKER_NUMBERS?MIN_WORKER_NUMBERS:num);
        initializeWorkers(workerNum);
    }

    @Override
    public void execute(Job job) {
        if (job!=null){
            //添加一个任务，然后通知线程池去执行
            synchronized (jobs){
                jobs.add(job);
                jobs.notify();
            }
        }
    }

    //初始化线程
    private void initializeWorkers(int defaultWorkerNumbers) {
        for (int i=0;i<defaultWorkerNumbers;i++){
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker:workers){
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            //限制新增worker数量不能超过最大Worker数量
            if (workerNum+num>MAX_WORKER_NUMBERS){
                num = MAX_WORKER_NUMBERS - workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs){
            if (num >= workerNum){
                throw new IllegalArgumentException("beyond workNum");
            }
            //按照给定数量停止worker
            int count = 0;
            while (count<num){
                Worker worker = workers.get(count);
                if (workers.remove(worker)){
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= num;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


    class Worker implements Runnable{
        private volatile boolean running = true;
        @Override
        public void run() {
            while (running){
                Job job = null;
                synchronized (jobs){
                    //如果工作者列表是空的，那么就wait
                    while (jobs.isEmpty()){
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //感知到外部对WorkThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.getFirst();
                }
                if (job!=null){
                    try{
                        job.run();
                    }catch (Exception e){
                     //忽略Job执行中的Exception
                    }
                }
            }

        }

        public void shutdown() {
            running = false;
        }
    }
}
