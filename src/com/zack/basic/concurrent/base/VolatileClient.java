package com.zack.basic.concurrent.base;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * volatile:
 * 特性：
 * 1、保证此变量对所有线程的可见性，这里的“可见性”是指当一条线程修改了这个变量的值，新值对其他线程来说是可以立即得知的。
 *    而普通变量做不到这一点，普通变量在线程间传递均需要通过主存来完成，例如，线程A修改一个普通变量的值，然后想主内存进行回写，
 *    另一个线程B在线程A回写完成之后再从主内存进行读取操作，新变量值才会对线程B可见。
 *
 *  由于Volatile变量只能保证可见性，在不符合一下两条规则运算场景总我们任然要通过加锁来保证原子性。
 *  运算结果不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值
 *  变量不需要与其他的状态变量共同参与不变约束
 *
 * 2、禁止指令重排序优化，普通变量仅仅会保证在该方法的执行过程中所有以来赋值结果的地方都能获取到正确的结果
 *    而不能保证变量赋值操作顺序与程序代码中的执行顺序一致，因为在一个线程的方法执行过程中无法感知到这点。
 *
 *
 *
 *
 *
 */
public class VolatileClient {

    public static void main(String[] args){

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false,false);
        for (ThreadInfo threadInfo:threadInfos){
            System.out.println("thread id:"+threadInfo.getThreadId()+" thread name:"+threadInfo.getThreadName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true);

    }
}
