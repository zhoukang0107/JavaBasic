package com.zack.basic.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Atomic包中一共提供了13个类，属于4种类型的原子更新方式
 * 原子更新基本类型
 * 原子更新数组
 * 原子更新引用
 * 原子更新属性
 *
 *
 * 原子更新基本类型
 * int addAndGet(int delta):以原子的方式将输入的数值与实例的值相加，并返回结果
 * boolean compareAndSet(int expect, int update):如果输入的数值等于预期值，则以原子方式将该值设置为输入的值
 * int getAndIncrement():以原子方式将当前值加1，注意这里返回的是自增前的值。
 * void lazySet(int newValue):最终会设置成newValue，使用lazySet设置值后。可能导致其他线程在之后的一小段时间内还是可以读到旧的值。
 * int getAndSet(int newValue):以原子的方式设置为newValue,并返回旧值
 *
 * 原子更新数组
 * 通过原子的方式更新数组里的某个元素
 *
 * AtomicIntegerArray:原子更新整形数组里面的元素
 * AtomicLongArray:原子更新长整型数组里的元素
 * AtomicReferenceArray:原子更新引用类型数组里的元素
 *
 *
 * AtomicIntegerArray：主要提供了原子的方式更新数组里面的整型
 * int addAndGet(int i.int delta):以原子的方式将输入值与数组中索引i的元素相加
 * boolean compareAndSet(int i,int expect,int update):如果当前值等于预期值，则以原子方式将数组位置i的元素设置成update值
 *
 *
 * 原子更新引用类型
 * 原子更新基本类型AtomicInteger,只能改下一个变量，如果原子要更新更多的变量就需要使用这个原子更新引用类型提供的类
 * AtomicReference:原子更新引用类型
 * AtomicReferenceFieldUpdate:原子更新引用类型里的字段
 * AtomicMarkableReference:原子更新带有标记位的引用类型。可以原子更新一个布尔类型的标记位和引用类型。构造方法是AtomicMarkableReference(V initialRef,boolean initialMark).
 *
 * 原子更新字段类
 *
 *如果需要原子更新某个类里面的某个字段时，就需要使用原子更新字段类，Atomic包提供了：
 * AtomicIntegerFieldUpdate:原子更新整型的字段的更新器
 * AtomicLongFieldUpdate:原子更新长整型字段的更新器
 * AtomicStampedReference:原子更新带有版本号的引用类型，该类将整数值与引用关联起来，可用于原子的更新数据和数据的版本，可以解决使用CAS进行原子更新时出现的ABA问题。
 * 要想使用原子更新字段类需要两步：
 * 1、因为原子更新字段类都是抽象类，每次使用的时候必须使用静态方法newUpdate()创建一个更新器，并且需要设置想要更新的类和属性
 * 2、更新类的字段(属性)必须使用public volatitle修饰符。
 *
 *
 */
public class ThreadAtomic {

    static int[] value = new int[]{1,2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    static AtomicReference<User> atomicUserRef = new AtomicReference<User>();

    static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class,"time");
    public static void main(String[] args){
       /* AtomicInteger ai = new AtomicInteger(1);
        ai.getAndIncrement();*/

        ai.getAndSet(0,3);
        System.out.println(ai.get(0));  //3
        System.out.println(value[0]);   //1


        User user = new User("conan",15);
        atomicUserRef.set(user);
        User updateUser = new User("shinichi",17);
        atomicUserRef.compareAndSet(user,updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getOld());

        //设置时间是10
        User user1 = new User();
        user1.setTime(10);
        //设置时间加1,返回旧的时间
        System.out.println(a.getAndIncrement(user1));
        //输出增加后的时间
        System.out.println(a.get(user1));

    }

    private static class User {
        private String name;
        private int old;

        private int time;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public User() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOld() {
            return old;
        }

        public void setOld(int old) {
            this.old = old;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
