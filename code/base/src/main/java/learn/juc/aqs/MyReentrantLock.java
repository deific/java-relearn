
package learn.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 重新写ReentrantLock，以观察重入锁ReentrantLock的实现原理和执行过程
 * 实现了Lock接口，Lock接口定义了锁应该具有的所有方法
 */
public class MyReentrantLock implements Lock, java.io.Serializable{

    private final MyReentrantLock.Sync sync;

    public MyReentrantLock(boolean fair) {
        this.sync = new NonfairSync();
    }

    /**
     * 自定义抽象内部类 Sync继承 抽象队列同步器(AQS)，这个锁利用的AQS的独占式资源共享模式，也就是利用AQS来实现锁
     * ReentrantLock的锁实现分为公平锁和非公平锁
     * 公平锁就是排队，先到先得，不能插队
     * 非公平锁就是竞争，谁抢到算谁的
     */
    abstract static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -5179523762034025860L;

        /**
         * 执行Lock#lock方法，对于公平锁和非公平锁的实现是不一样的
         */
        abstract void lock();

        /**
         * 非公平方式尝试获得共享资源（锁）
         * 非公平锁方式的原理就是当前现场首先尝试通过cas设置state值，
         * 如果成功了，则获取成功
         * 如果失败了，返回失败
         * 该方法不能被子类重写
         * @param acquires
         * @return
         */
        final boolean nonfairTryAcquire(int acquires) {
            // 获取当前线程
            final Thread current = Thread.currentThread();
            // 获取aqs中的state值
            int c = getState();
            // 如果c==0,说明锁未被其他线程获取，则尝试获取锁
            if (c == 0) {
                // 不管队列中有没有节点，直接抢占式尝试通过cas原子操作设置state变量值，由0->1
                if (compareAndSetState(0, acquires)) {
                    // 如果成功，则说明获得锁成功,将拥有者线程设置为当前线程
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            // 如果c != 0 ，说明已有线程已经获得锁了，判断是否是独占当前线程自己
            else if (current == getExclusiveOwnerThread()) {
                // 如果是当前线程自己，则state值累加1，也为获取锁成功，也就是重入锁
                int nextc = c + acquires;
                // 如果累加之后<0，说明锁的累加次数已经超出int的最大值，报异常
                if (nextc < 0) {
                    throw new Error("Maximum lock count exceeded");
                }
                // 设置state的新值
                setState(nextc);
                return true;
            }
            // 如果是其他线程已获得锁，则尝试获取锁失败
            return false;
        }

        /**
         * 尝试释放锁
         * 释放锁逻辑就是state的值减1，当减到0时，就是释放状态
         * @param releases
         * @return
         */
        @Override
        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            // 如果当前线程不是锁拥有者，为错误的调用，报错
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }

            boolean free = false;
            // 已经到0,完成锁释放，返回成功，清空拥有者
            if (c == 0) {
                free = true;
                // 清空拥有者
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }

        /**
         * 判断是否是当前线程独占资源
         * @return
         */
        @Override
        protected final boolean isHeldExclusively() {
            // While we must in general read state before owner,
            // we don't need to do so to check if current thread is owner
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        /**
         * 获取锁拥有线程
         * @return
         */
        final Thread getOwner() {
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        /**
         * 获取当前线程持锁次数
         * @return
         */
        final int getHoldCount() {
            return isHeldExclusively() ? getState() : 0;
        }

        /**
         * 判断是否已锁
         * @return
         */
        final boolean isLocked() {
            return getState() != 0;
        }

        /**
         * 读序列化
         * @param s
         * @throws java.io.IOException
         * @throws ClassNotFoundException
         */
        private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    /**
     * 非公平锁实现
     */
    static final class NonfairSync extends MyReentrantLock.Sync {

        @Override
        void lock() {
            // 非公平锁，每次调用时，直接抢占式尝试获取锁->非公平之处
            if(compareAndSetState(0, 1)) {
                // 如果成功,设置拥有者线程为自己
                setExclusiveOwnerThread(Thread.currentThread());
            } else {
                // 交由AQS来处理获取锁
                // 实现步骤：
                // 1、调用tryAcquire尝试加锁
                // 2、如果加锁失败，调用addWaiter将当前线程节点加入队列
                // 2.1 addWaiter实现，
                //   a,首先将当前线程封装为Node节点，
                //   b,判断当前队列尾节点是否==null,
                //      如果!=null,说明队列有等待，将当前线程的节点设置为尾节点，并返回
                //      如果 == null,则说明队列为空，调用enq()插入队列（包含初始化队列）
                          // 初始化逻辑：
                              // for死循环自旋实现
                                  // ① 获取队列尾结点，如果为空，调用compareAndSetHead先初始化一个头节点，并将头结点赋值给尾节点
                                  // ② 再次循环，将当前线程追加到尾结点后，调用compareAndSetTail设置当前线程节点为尾结点，直到成功后返回

                // 2.2 调用acquireQueued再次尝试获取锁
                //    a,通过for死循环，自旋方式尝试获取错，直到成功或异常
                       // ① 获取当前线程节点的前一个节点，如果已是头节点（队列有一个空的头节点，已是头结点，也就是当前线程节点为最前节点），则再次tryAcquire尝试获取锁
                       //     如果获取锁成功，则将头结点设置为自己，并清空线程信息
                       //     如果前一个节点不是头节点或者上一步获取锁失败，
                        //     则调用shouldParkAfterFailedAcquire检查前一个节点的等待状态
                        //      如果前1节点等待状态=SIGNAL，说明前节点已释放锁，可以唤醒
                        //      如果前节点等待状态 > 0,说明已取消，则跳过前节点，将前节点摘除队列
                        //      如果前节点等待状态 < 0,compareAndSetWaitStatus设置前节点的信号
                acquire(1);
            }
        }

        /**
         * 尝试获取共享资源
         * 非公平锁通过实现该方法，尝试获取
         * @param acquires
         * @return
         */
        @Override
        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }

    }

    /**
     * 公平锁的实现
     */
    static final class FairSync extends MyReentrantLock.Sync {

        @Override
        void lock() {
            // 加入队列排队获取锁
            // 公平锁的公平性也体现在这里
            acquire(1);
        }

        @Override
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                // 如果队列中没有前向节点时，直接尝试获取锁，否则返回失败，插入队列排队
                if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }
    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        // 如果主动调用尝试锁的时候，实际上无论公平锁还是非公平锁，都是抢占式
        return sync.nonfairTryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}