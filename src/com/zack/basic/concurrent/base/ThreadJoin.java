package com.zack.basic.concurrent.base;

import java.util.concurrent.TimeUnit;

/**
 * 如果一个线程执行了Thread.join(),其含义就是：当前线程A等待thread线程终止之后
 * 才从thread.join()返回
 */
public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread previous = Thread.currentThread();
        for (int i=0;i<10;i++){
            Thread thread = new Thread(new Domino(previous),String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName()+"terminate");

    }

    private static class Domino implements Runnable {
        Thread thread;
        public Domino(Thread previous) {
            thread = previous;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"terminate");

        }
    }
}
