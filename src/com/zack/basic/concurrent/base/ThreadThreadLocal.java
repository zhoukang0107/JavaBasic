package com.zack.basic.concurrent.base;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal：线程本地变量
 * 是一个以ThreadLocal为键、任意对象为值得存储结构,这个结构被附带在线程上，
 * 也就是说，一个线程可以根据一个ThreadLocal对象查询到绑定在这个线程上的一个值。
 *
 *
 */
public class ThreadThreadLocal {
    //第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadThreadLocal.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost:"+ThreadThreadLocal.end()+" mills");
    }
}
