package com.zack.basic;

/**
 * 单例模式：
 * 一种对象创建模式，用于产生一个对象的具体事例，可以确保系统中一个类只产生一个实例，他可以确保系统中一个类只产生一个实例
 * 好处：
 * 1、对于频繁使用的对象，可以省略创建对象所花费的时间，对于那些重量级的对象是一笔非常可观的系统开销
 * 2、new操作次数减少，因而对系统内存的使用频率也会降低，这将减轻GC压力，缩短GC停顿时间
 *
 */
//饿汉模式   不足：无法对instance做延时加载
class HungrySingle{
    private static HungrySingle instance = new HungrySingle();
    private HungrySingle(){}
    public static HungrySingle getInstance(){
        return instance;
    }
}

//懒汉模式  线程不安全
class LazySingle{
    private static LazySingle instance = null;
    private LazySingle(){}
    // 多线程并发可能产生多个对象
    public static LazySingle getInstance(){
        if (instance==null){
            instance = new LazySingle();
        }
        return instance;
    }
}
//懒汉模式  线程安全方式  每次获取单例都会加锁，性能损耗
class LazySafeSingle{
    private static LazySafeSingle instance = null;
    private LazySafeSingle(){}
    //线程安全方式
    public static synchronized LazySafeSingle getSafeInstance(){
        if (instance==null){
            instance = new LazySafeSingle();
        }
        return instance;
    }

}

//双检查检验机制  单例
class DCLSingle{
    private static volatile DCLSingle instance = null;
    private DCLSingle(){}
    public static DCLSingle getInstance(){
        if(instance==null){
            synchronized (DCLSingle.class){
                if (instance==null){
                    instance = new DCLSingle();  //该句由多条指令组成，会有指令重排问题
                }
            }
        }
        return instance;
    }
}

//静态内部类实现单例  jvm本身机制保证了线程安全，没有性能缺陷
class StaticInnerSingle{
    private StaticInnerSingle(){}
    public static StaticInnerSingle getInstance(){
        return StaticInnerHolder.sInstance;
    }

    private static class StaticInnerHolder{
        private static final StaticInnerSingle sInstance = new StaticInnerSingle();
    }
}

//枚举实现单例  写法简单、线程安全



public class DesignPattern {
}
