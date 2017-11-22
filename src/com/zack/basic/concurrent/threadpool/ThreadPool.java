package com.zack.basic.concurrent.threadpool;

/**
 * 线程池
 * 线程池的处理流程
 * 1、线程池判断核心线程池里面的线程是否都在执行任务，如果不是，则创建一个新的工作线程来执行任务。如果核心线程池里面的线程都在执行任务，则进入下个流程；
 * 2、线程池判断工作队列是否已满。如果工作队列没有满，则将新提交的任务存储在这个工作队列里面。如果工作队列满了，则进入下个流程
 * 3、线程池判断线程池的线程是否都处于工作状态。如果没有，则创建一个新的工作线程来执行任务。如果已经满了，则交给饱和策略来处理这个任务。
 *
 * ThreadPoolExecute执行execute方法：
 * 1、如果当前运行的线程少于corePoolSize，则创建新线程来执行任务（注意，执行这一步需要获取全局锁）
 * 2、如果运行的线程等于或者多于corePoolSize,则将任务加入BlockingQueue.
 * 3、如果无法将任务加入BlockingQueue(队列已满)，则创建新的线程来处理任务（注意，执行这一步需要获取全局锁）
 * 4、如果创建新线程将使当前运行的线程超出maximumPoolSize，任务将被拒绝，并调用RejectedExecutionHandler.rejectedExecution()方法
 * ThreadPoolExecutor采取上述步骤的总体设计思路，是为了在执行execute()方法时，尽可能地避免获取全局锁。在ThreadPoolExecutor完成
 * 预热之后（当前运行的线程数大于等于corePoolSize）,几乎所有的executor()方法调用都是执行步骤2，而步骤2不需要获取全局锁
 *
 * 线程池的创建:
 * ThreadPoolExecutor(
 * int corePoolSize,    //（线程池的基本大小）：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。如果调用了线程池的prestartAllCoreThreads()方法，线程池会提前创建并启动所有基本线程
 * int maximumPoolSize,    //（线程池最大数量）：线程池运行创建的最大线程数量。如果队列满了，并且已经创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。值得注意的是，如果使用了无界的任务队列这个参数就没有什么用了
 * long keepAliveTime,    //（线程活动保持时间）：线程池的工作线程空闲后，保持存活的时间。所以如果任务很多，并且每个任务执行的时间比较短，可以调大时间，提高线程的利用率
 * TimeUnit unit,    //线程活动保持时间的单位
 * BlockingQueue<Runnable> workQueue,    //（任务队列）：用于保存等待执行的任务的阻塞队列
 * ThreadFactory threadFactory,    //用于设置创建线程的工厂
 * RejectedExecutionHandler handler    //（饱和策略）：线程池已经关闭或者当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。这个策略默认使用AbortPolicy，表示无法处理新任务时抛出异常
 * )
 *
 * 任务队列可以选择：
 * ArrayBlockingQueue:一个基于数组结构的有界阻塞队列，此队列按FIFO原则对元素进行排序。
 * LinkedBlockingQueue:一个基于链表结构的阻塞队列，此队列按FIFO原则对元素进行排序，吞吐量通常要高于ArrayBlockingQueue.静态工程方法Executors.newFixedThreadPool()使用了这个队列
 * SynchronousQueue:一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue,静态方法Executors.newCachedThreadPool()使用这个队列
 * PriorityBlockingQueue:一个具有优先级的无限阻塞队列
 *
 * 饱和策略：
 * AbortPolicy:直接抛出异常
 * CallerRunsPolicy:只用调用者所在的线程来运行任务
 * DiscardOldestPolicy：不处理，丢弃掉
 * 也可以根据相应场景来实现RejectedExecutionHandler接口自定义策略。如记录日志或持久化不能处理的任务
 *
 * 向线程池提交任务：
 * execute():提交后不返回值的任务
 * submit():提交后需要返回值的任务，返回一个future类型的对象，通过future判断任务是否执行成功，future.get()获取返回值，get()方法会阻塞当前线程直到任务完成，也可使用get(long timeout,TimeUnit unit)
 *
 * 关闭线程池：
 * 遍历线程池中的工作线程，然后这个调用线程的interrupt方法来中断线程
 * shutdown()：将线程池的状态设置成SHUTDOWN状态，然后中断所有没有正在执行任务的线程
 * shutdownNow()：首先将线程池的状态设置成STOP，然后尝试执行所有的正在执行或暂停任务的线程，并返回等待执行任务的列表
 * 调用任意一个，isShutdown方法就会返回true,当所有的任务都已关闭后，才表示线程池关闭成功，这时调用isTerminaed方法会返回true。
 * 具体调用哪一种方法来关闭线程池，应该由提交到线程池的任务特性决定，通常调用shutdown方法来关闭线程池，如果任务不一定要执行完，则可以调用shutdownNow方法。
 *
 *
 * 合理地配置线程池：
 * 要想合理配置线程池，就必须分析任务特性
 * 任务的性质：CPU密集型任务，IO密集型任务和混合型任务
 * 任务的优先级：高、中和低
 * 任务的执行时间：长、中和短
 * 任务的依赖性：是否依赖其他系统资源，如数据库连接
 *
 * 性质不同的任务可以用不同规模的线程池分开处理
 * CPU密集型任务应配置尽可能小的线程，如配置N(cpu)+1个线程的线程池
 * 由于IO密集型任务线程并不是一直在执行任务，则应该配置尽可能多的线程，如2*N(cpu)
 * 混合型任务，如果可以拆分，将其拆分成一个CPU密集型任务和一个IO密集型任务，只要两个任务执行的时间相差不是太大，那么分解后执行的吞吐量将高于串行执行的吞吐量，如果两个任务执行时间相差太大，则没有必要进行分解。可以通过Runtime.getRuntime().availableProcessors()方法获得当前设备的CPU个数
 * 优先级不同的任务可以使用优先级队列PriorityBlockingQueue来处理
 * 执行时间不同的任务可以交给不同规模的线程池来处理，或者可以使用优先级队列，让执行时间段的任务先执行
 * 依赖数据库连接池的任务，应为线程提交SQL后需要等待数据库的返回结果，等待的时间越长，则CPU空闲的时间越长，那么线程数量应该设置的越大，这样才能更好地利用CPU。
 * 建议使用有界队列，可以增强系统的稳定性的预警能力
 *
 * 线程池的监控：
 * 可以通过线程池提供的参数进行监控，可以使用以下属性：
 * taskCount：线程池需要执行的任务数量
 * completedTaskCount:线程池在运行过程中已完成的任务数量，小于或等于taskCount
 * largestPoolSize:线程池里面曾经创建过的最大线程数量，通过这个数据可以知道线程是否曾经满过，如该数据等于线程池的最大大小，则表示线程池曾经满过
 * getPoolSize:线程池的线程数量
 * getActiveCount:获取活动的线程数
 * 通过扩展线程池进行监控，可以通过继承来自定义线程池，重新线程池的beforeExecute,faterExecute和terminated方法，也可以在任务执行前。执行后和线程池关闭前执行一些代码来监控，例如：监控任务的平均执行时间、最大执行时间和最小执行时间等、这几个方法在线程池里面是空方法。
 *
 *
 *
 */
public class ThreadPool {
}
