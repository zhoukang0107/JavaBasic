package com.zack.basic.concurrent.threadpool;

/**
 * Executor框架
 * Executor框架的成员：ThreadPoolExecutor/ScheduledThreadPoolExecutor/Future/Runnable/Callable/Executors
 * 1、ThreadPoolExecutor
 * ThreadPoolExecutor通常使用工具类Executors来创建，Executors可以创建三种类似的ThreadPoolExecutor
 * (1)创建使用可重用固定线程数的FixedThreadPool
 *  public static ExecutorService newFixedThreadPool(int nThreads) {
 *      return new ThreadPoolExecutor(nThreads, nThreads,
 *                                      0L, TimeUnit.MILLISECONDS,
 *                                      new LinkedBlockingQueue<Runnable>());
 *  }
 *
 *  public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
 *      return new ThreadPoolExecutor(nThreads, nThreads,
 *                                      0L, TimeUnit.MILLISECONDS,
 *                                      new LinkedBlockingQueue<Runnable>(),
 *                                      threadFactory);
 *  }
 *
 *  由于corePoolSize等于maximumPoolSize，所以keepAliveTime（非核心线程空闲keepAliveTime后被销毁）无效，
 *
 * 适用于满足资源管理的需求，而需要限制当前线程数量的应用场景，他适用负载比较重的服务器
 * (2)创建使用单个线程的SingleThreadExecutor
 *  public static ExecutorService newSingleThreadExecutor() {
 *  return new FinalizableDelegatedExecutorService
 *  (new ThreadPoolExecutor(1, 1,
 *  0L, TimeUnit.MILLISECONDS,
 *  new LinkedBlockingQueue<Runnable>()));
 *  }
 *
 *  public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
 *  return new FinalizableDelegatedExecutorService
 *  (new ThreadPoolExecutor(1, 1,
 *  0L, TimeUnit.MILLISECONDS,
 *  new LinkedBlockingQueue<Runnable>(),
 *  threadFactory));
 *  }
 * 适用于需要保证顺序执行各个任务，并且在任意时间点不会有多个线程时活动的应用场景
 * (3)创建一个会根据需要创建新线程的CachedThreadPool
 *  public static ExecutorService newCachedThreadPool() {
 *  return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
 *  60L, TimeUnit.SECONDS,
 *  new SynchronousQueue<Runnable>());
 *  }
 *
 *  public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
 *  return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
 *  60L, TimeUnit.SECONDS,
 *  new SynchronousQueue<Runnable>(),
 *  threadFactory);
 *  }
 * CachedThreadPool是大小无界的线程池，适用于执行很多的短期异步任务的小程序或者负载比较轻的服务器
 *
 * 2、ScheduledThreadPool
 * ScheduledThreadPool通常使用工厂类Executors来创建，Executors可以创建两种类型的ScheduledThreadpoolExecutor
 * (1)创建固定个数线程的ScheduledThreadPoolExecutor
 * public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
 *     return new ScheduledThreadPoolExecutor(corePoolSize);
 * }
 *
 * public static ScheduledExecutorService newScheduledThreadPool(
 *     int corePoolSize, ThreadFactory threadFactory) {
 *     return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
 * }
 * 适用于需要多个后台线程执行周期任务，同时为了满足资源管理的需求而需要限制后台线程的数量的应用场景
 * (2)创建单个线程的SingleThreadScheduledExecutor
 *  public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
 *     return new DelegatedScheduledExecutorService
 *           (new ScheduledThreadPoolExecutor(1));
 * }
 *
 * public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
 *  return new DelegatedScheduledExecutorService
 *           (new ScheduledThreadPoolExecutor(1, threadFactory));
 * }
 * 适用于需要单个后台线程执行周期性任务。同时需要保证顺序地执行各个任务的应用场景
 *
 * 3、Future接口
 * Future接口或实现Future接口的FutureTask类用来表示异步计算的结果。当我们将Runnable或者Callable的实现类提交给ThreadPoolExecutor或者ScheduledThreadPoolExecutor时
 * 会向我们返回一个FutureTask对象
 * public <T> Future<T> submit(Callable<T> task)
 * public Future<?> submit(Runnable task)
 *
 * 4、Runnable接口和Callable接口
 * 两个接口的实现类都可以被ScheduledThreadPool和ThreadPoolExecutor执行，区别是Runnable不会返回结果，Callable可以返回结果
 * 可以使用Executors将Runnable包装成一个Callable的API
 * public static Callable<Object> callable(Runnable task)
 *
 *
 *
 */
public class ThreadExecutor {
}
