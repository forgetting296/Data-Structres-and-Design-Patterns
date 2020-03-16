package com.shusaku.study.leetcode;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @program: Java8Test
 * @description: 按照 0 奇数 偶数 的顺序进行打印  输入3  打印 010203
 * @author: Shusaku
 * @create: 2020-01-11 15:52
 */
public class PrintZeroOddEven {

    private int n;

    public PrintZeroOddEven(int n) {
        this.n = n;
    }

    private int i = 1;
    private Semaphore zeroSemaphore = new Semaphore(0);
    private Semaphore oddSemaphore = new Semaphore(1);
    private Semaphore evenSemaphore = new Semaphore(0);

    public void zero(IntConsumer printNumber) throws InterruptedException {
        while(true) {
            zeroSemaphore.acquire();
            if(i > n){
                return;
            }
            printNumber.accept(0);
            oddSemaphore.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while(i <= n) {
            oddSemaphore.acquire();  //这才是真的开始方法  获取到信号量之后  直接调用zero的信号量
            zeroSemaphore.release();
            oddSemaphore.acquire();  //阻塞  等待获取信号量
            if((i & 1) != 0) {
                printNumber.accept(i);
            } else {
                evenSemaphore.release();
                oddSemaphore.acquire();
            }
            i ++;
            oddSemaphore.release();
        }
        zeroSemaphore.release();
        evenSemaphore.release();
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while(true) {
            evenSemaphore.acquire();
            if(i > n) {
                return;
            }
            printNumber.accept(i);
            oddSemaphore.release();//打印完偶数之后  调用打印奇数的semaphore
        }
    }

    public static void main(String[] args) {
        PrintZeroOddEven pzoe = new PrintZeroOddEven(10);
        new Thread(() -> {
            try {
                pzoe.zero4(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                pzoe.even4(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                pzoe.odd4(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //================================================Semaphore的另一种实现方式====================================================================

    private Semaphore z = new Semaphore(1);
    private Semaphore o = new Semaphore(0);
    private Semaphore e = new Semaphore(0);

    public void zero2(IntConsumer printNumber) throws InterruptedException {
        for(int i = 0;i < n;i ++){
            z.acquire();
            printNumber.accept(0);
            if((i & 1) == 0) {
                o.release();
            }else {
                e.release();
            }
        }
    }

    public void odd2(IntConsumer printNumber) throws InterruptedException {
        for(int i = 1;i <= n;i += 2) {
            o.acquire();
            printNumber.accept(i);
            z.release();
        }
    }

    public void even2(IntConsumer printNumber) throws InterruptedException {
        for(int i = 2;i <= n;i += 2) {
            e.acquire();
            printNumber.accept(i);
            z.release();
        }
    }

    //=================================================凡是信号量可以解决的问题 都可以用管程模型来解决===================================================================

    private Lock lock = new ReentrantLock();
    private Condition zc = lock.newCondition();
    private Condition num = lock.newCondition();
    volatile boolean zTurn = true;
    volatile int zIndex = 0;

    public void zero3(IntConsumer printNumber) throws InterruptedException {
        while(zIndex < n) {
            lock.lock();
            try {
                while(!zTurn) {
                    zc.await();
                }
                printNumber.accept(0);
                zTurn = false;
                num.signalAll();
                zIndex ++;
            }finally {
                lock.unlock();
            }
        }
    }

    public void even3(IntConsumer printNumber) throws InterruptedException {
        for(int i = 2;i < n;i += 2) {
            lock.lock();
            try {
                while(zTurn || (zIndex & 1) == 1){
                    num.await();
                }
                printNumber.accept(i);
                zTurn = true;
                zc.signal();
            }finally {
                lock.unlock();
            }
        }
    }

    public void odd3(IntConsumer printNumber) throws InterruptedException {
        for(int i = 1;i <= n;i += 2) {
            lock.lock();
            try {
                while(zTurn || (zIndex & 1) == 0) {
                    num.await();
                }
                printNumber.accept(i);
                zTurn = true;
                zc.signal();
            }finally {
                lock.unlock();
            }
        }
    }

    //========================================================无锁实现方式===============================================================
    volatile int stage = 0;
    public void zero4(IntConsumer printNumber) {
        for(int i = 0;i < n;i ++) {
            while(stage > 0){
                Thread.yield();
            }
            printNumber.accept(0);
            if((i & 1) == 0) {
                stage = 1;
            }else {
                stage = 2;
            }
        }
    }

    public void even4(IntConsumer printNumber) {
        for(int i = 2;i <= n;i +=2) {
            while(stage != 2) {
                Thread.yield();
            }
            printNumber.accept(i);
            stage = 0;
        }
    }

    public void odd4(IntConsumer printNumber) {
        for(int i = 1;i <= n;i +=2) {
            while(stage != 1) {
                Thread.yield();
            }
            printNumber.accept(i);
            stage = 0;
        }
    }

}
