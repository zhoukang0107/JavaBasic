package com.zack.basic.concurrent.base;

/**
 * 当一个java虚拟机中不存在非Daemon线程时，java虚拟机会退出，Daemon线程主要用作后台调度以及支持性工作
 * Daemon属性需要在启动线程之前设置，不能在启动线程之后设置
 * Java虚拟机退出时Daemon线程中的finally块不一定会执行，所以不能依靠finally块中的内容来确保执行关闭或清理资源
 *
 */
public class ThreadDaemon {

    public static void main(String[] args){
        Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    private static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try{
                SleepUtils.second(10);
            }finally {
                System.out.println("DaemonThread finally run.");
            }

        }
    }
}
