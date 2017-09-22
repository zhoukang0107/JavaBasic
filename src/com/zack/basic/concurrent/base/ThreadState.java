package com.zack.basic.concurrent.base;

/**
 * 线程状态：
 * 1、(NEW)线程创建
 * 2、(RUNNABLE)运行状态(包含线程就绪和运行)
 * 3、(BLOCKED)阻塞状态(等待锁等)
 * 4、(WAITING)等待状态(wait(),join()等，当前线程需要等待其他线程做出一些特定动作)
 * 5、(TIME_WAITING)超时等待状态(sleep(time),wait(time)，join(time)等，不同于等待状态，他可以在指定的时间自行返回)
 * 6、(TERMINATED)终止状态
 *
 *
 */
public class ThreadState {

    public static void main(String[] args){
        new Thread(new TimeWaiting(),"TimeWaitingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        //使用两个blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-1").start();
    }

    /**
     * 该线程不断的进行睡眠
     */
    private static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true){
                SleepUtils.second(100);
            }
        }
    }

    /**
     * 该线程再Waiting.class实例上等待
     */
    private static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 该线程获取Blocked.class锁后不会再释放
     */
    private static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class){
                while (true){
                    SleepUtils.second(100);
                }
            }
        }
    }


}
/**  jps -> jstack
 2017-09-20 16:54:56
 Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.141-b15 mixed mode):

 "DestroyJavaVM" #15 prio=5 os_prio=0 tid=0x0000000002478800 nid=0x2e0a4 waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 //BlockedThread-1线程处于超时等待状态
 "BlockedThread-1" #14 prio=5 os_prio=0 tid=0x000000001d95d000 nid=0x1dc8 waiting on condition [0x000000001ed5f000]
 java.lang.Thread.State: TIMED_WAITING (sleeping)
 at java.lang.Thread.sleep(Native Method)
 at java.lang.Thread.sleep(Thread.java:340)
 at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 at com.zack.basic.concurrent.base.ThreadState$SleepUtils.second(ThreadState.java:73)
 at com.zack.basic.concurrent.base.ThreadState$Blocked.run(ThreadState.java:64)
 - locked <0x000000076b722630> (a java.lang.Class for com.zack.basic.concurrent.base.ThreadState$Blocked)
 at java.lang.Thread.run(Thread.java:748)

 //BlockedThread-1线程处于阻塞状态
 "BlockedThread-1" #13 prio=5 os_prio=0 tid=0x000000001d95a000 nid=0x3ee8 waiting for monitor entry [0x000000001ebdf000]
 java.lang.Thread.State: BLOCKED (on object monitor)
 at com.zack.basic.concurrent.base.ThreadState$Blocked.run(ThreadState.java:64)
 - waiting to lock <0x000000076b722630> (a java.lang.Class for com.zack.basic.concurrent.base.ThreadState$Blocked)
 at java.lang.Thread.run(Thread.java:748)

 //WaitingThread处于等待状态
 "WaitingThread" #12 prio=5 os_prio=0 tid=0x000000001d957000 nid=0x31560 in Object.wait() [0x000000001d40f000]
 java.lang.Thread.State: WAITING (on object monitor)
 at java.lang.Object.wait(Native Method)
 - waiting on <0x000000076b71f3c8> (a java.lang.Class for com.zack.basic.concurrent.base.ThreadState$Waiting)
 at java.lang.Object.wait(Object.java:502)
 at com.zack.basic.concurrent.base.ThreadState$Waiting.run(ThreadState.java:47)
 - locked <0x000000076b71f3c8> (a java.lang.Class for com.zack.basic.concurrent.base.ThreadState$Waiting)
 at java.lang.Thread.run(Thread.java:748)

 //TimeWaitingThread处于超时等待状态
 "TimeWaitingThread" #11 prio=5 os_prio=0 tid=0x000000001d956000 nid=0x3c10 waiting on condition [0x000000001ea1f000]
 java.lang.Thread.State: TIMED_WAITING (sleeping)
 at java.lang.Thread.sleep(Native Method)
 at java.lang.Thread.sleep(Thread.java:340)
 at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 at com.zack.basic.concurrent.base.ThreadState$SleepUtils.second(ThreadState.java:73)
 at com.zack.basic.concurrent.base.ThreadState$TimeWaiting.run(ThreadState.java:33)
 at java.lang.Thread.run(Thread.java:748)

 "Service Thread" #10 daemon prio=9 os_prio=0 tid=0x000000001d896800 nid=0x13e24 runnable [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "C1 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x000000001d7ed800 nid=0x12148 waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x000000001d7eb800 nid=0x30844 waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x000000001d7eb000 nid=0xc920 waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x000000001d7be800 nid=0x109cc runnable [0x000000001d64e000]
 java.lang.Thread.State: RUNNABLE
 at java.net.SocketInputStream.socketRead0(Native Method)
 at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
 at java.net.SocketInputStream.read(SocketInputStream.java:171)
 at java.net.SocketInputStream.read(SocketInputStream.java:141)
 at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
 at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
 at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
 - locked <0x000000076b849be8> (a java.io.InputStreamReader)
 at java.io.InputStreamReader.read(InputStreamReader.java:184)
 at java.io.BufferedReader.fill(BufferedReader.java:161)
 at java.io.BufferedReader.readLine(BufferedReader.java:324)
 - locked <0x000000076b849be8> (a java.io.InputStreamReader)
 at java.io.BufferedReader.readLine(BufferedReader.java:389)
 at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:64)

 "Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000001d7a0800 nid=0xcf4c waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x000000001c248000 nid=0x26ec runnable [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "Finalizer" #3 daemon prio=8 os_prio=1 tid=0x000000001c22a800 nid=0x2955c in Object.wait() [0x000000001d78e000]
 java.lang.Thread.State: WAITING (on object monitor)
 at java.lang.Object.wait(Native Method)
 - waiting on <0x000000076b588ec8> (a java.lang.ref.ReferenceQueue$Lock)
 at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
 - locked <0x000000076b588ec8> (a java.lang.ref.ReferenceQueue$Lock)
 at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
 at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

 "Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x000000001c1e3800 nid=0x8340 in Object.wait() [0x000000001d53f000]
 java.lang.Thread.State: WAITING (on object monitor)
 at java.lang.Object.wait(Native Method)
 - waiting on <0x000000076b586b68> (a java.lang.ref.Reference$Lock)
 at java.lang.Object.wait(Object.java:502)
 at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
 - locked <0x000000076b586b68> (a java.lang.ref.Reference$Lock)
 at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

 "VM Thread" os_prio=2 tid=0x000000001c1dc000 nid=0x26dfc runnable

 "GC task thread#0 (ParallelGC)" os_prio=0 tid=0x000000000248d800 nid=0x1f918 runnable

 "GC task thread#1 (ParallelGC)" os_prio=0 tid=0x000000000248f000 nid=0x22ff8 runnable

 "GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000002490800 nid=0xd5b0 runnable

 "GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000002492000 nid=0xe250 runnable

 "VM Periodic Task Thread" os_prio=2 tid=0x000000001d8f6800 nid=0x2bb24 waiting on condition

 JNI global references: 33

*/