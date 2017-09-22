package com.zack.basic.concurrent.base;

import java.util.concurrent.TimeUnit;

/**
 * 中断可以理解为线程的一个标识位属性，他标识一个运行中的线程是否被其他线程进行了中断操作，
 * 中断好比其他线程对该线程打了一个招呼，其他线程通过调用该线程的interrupt()方法对其进行中
 * 断操作。
 *
 * 线程通过isInterrupted()来进行判断是否被中断，也可以调用静态方法Thread.interrupted()对当前
 * 线程的中断标识进行复位；如果该线程已经处于终结状态，即使该线程被中断过，在调用该线程对象的
 * isInterrupted()依旧会返回false；
 * 许多声明抛出InterruptedException的方法，这些方法在抛出InterruptedException之前，Java虚拟机会先将
 * 该线程的中断标识位清除，然后抛出InterruptedException，此时isInterrupted()依旧会返回false；
 *
 *
 *
 *
 */
public class ThreadInterrupted {
    public static void main(String[] args) throws InterruptedException {
        //sleepThread不停地尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(),"SleepThread");
        sleepThread.setDaemon(true);
        sleepThread.start();
        //不停地运行
        Thread busyThread = new Thread(new BusyRunner(),"busyThread");
        busyThread.setDaemon(true);
        busyThread.start();
        System.out.println("sleepThread init interrupted is "+sleepThread.isInterrupted());
        System.out.println("busyThread init interrupted is "+busyThread.isInterrupted());
        //休眠5秒，让sleepThread和busyThread充分运行
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("sleepThread interrupted is "+sleepThread.isInterrupted());
        System.out.println("busyThread interrupted is "+busyThread.isInterrupted());
        //防止sleepThread和busyThread立刻退出
        SleepUtils.second(2);

    }

    private static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true){
                SleepUtils.second(10);
            }
        }
    }

    private static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true){

            }
        }
    }
}
/**运行结果
 sleepThread init interrupted is false
 busyThread init interrupted is false
 java.lang.InterruptedException: sleep interrupted
 sleepThread interrupted is false
 at java.lang.Thread.sleep(Native Method)
 busyThread interrupted is true
 */