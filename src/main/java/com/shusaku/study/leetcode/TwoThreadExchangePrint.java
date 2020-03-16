package com.shusaku.study.leetcode;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-01-11 11:21
 */
public class TwoThreadExchangePrint {

    private int n;

    private volatile int flag = 0;

    public TwoThreadExchangePrint(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) {
        for(int i = 0;i < n;i ++) {
            while (flag != 0) {
                Thread.yield();
            }
            printFoo.run();
            flag = 1;
        }
    }

    public void bar(Runnable printBar) {
        for(int i = 0;i < n;i ++) {
            while (flag != 1) {
                Thread.yield();
            }
            printBar.run();
            flag = 0;
        }
    }

    //===============================================使用公平锁  Lock Condition============================================================

    private ReentrantLock lock = new ReentrantLock();
    private Condition fooCondition = lock.newCondition();
    private Condition barCondition = lock.newCondition();
    private boolean fooRun = true;

    public void foo2(Runnable printFoo) {
        try {
            lock.lock();
            for(int i = 0;i < n;i ++) {
                if(!fooRun) {
                    fooCondition.await();
                    barCondition.signal();
                }
                printFoo.run();
                fooRun = false;
                barCondition.signal();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void bar2(Runnable printBar) {
        try {
            lock.lock();
            for(int i = 0;i < n;i ++) {
                if(fooRun) {
                    fooCondition.signal();
                    barCondition.await();
                }
                printBar.run();
                fooRun = true;
                fooCondition.signal();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //===============================================使用公平锁  synchronized wait notify============================================================
    private Object obj = new Object();
    private boolean fooFlag = true;

    public void foo3(Runnable printFoo) throws InterruptedException {
        synchronized (obj) {
            for(int i = 0;i < n;i ++) {
                if(!fooFlag) {
                    obj.wait();
                }
                printFoo.run();
                fooFlag = false;
                obj.notifyAll();
            }
        }
    }

    public void bar3(Runnable printFoo) throws InterruptedException {
        synchronized (obj) {
            for(int i = 0;i < n;i ++) {
                if(fooFlag) {
                    obj.wait();
                }
                printFoo.run();
                fooFlag = true;
                obj.notifyAll();
            }
        }
    }

    //===============================================使用 semaphore============================================================

    private Semaphore foo = new Semaphore(1);
    private Semaphore bar = new Semaphore(0);//  permits为0  保证不会先打印 bar  一定是先在打印foo的线程里release之后 才会acquire

    public void foo4(Runnable printFoo) throws InterruptedException {
        for(int i = 0;i < n;i ++) {
            foo.acquire();
            printFoo.run();
            bar.release();
        }
    }

    public void bar4(Runnable printFoo) throws InterruptedException {
        for(int i = 0;i < n;i ++) {
            bar.acquire();
            printFoo.run();
            foo.release();
        }
    }

    //===============================================使用 CyclicBarrier============================================================
    private CyclicBarrier cb = new CyclicBarrier(2);
    volatile boolean fin = true;

    public void foo5(Runnable printFoo) throws BrokenBarrierException, InterruptedException {
        for(int i = 0;i < n;i ++) {
            while (!fin) {

            }
            printFoo.run();
            fin = false;
            cb.await();
        }
    }

    public void bar5(Runnable printBar) throws BrokenBarrierException, InterruptedException {
        for(int i = 0;i < n;i ++) {
            cb.await();
            printBar.run();
            fin = true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*Semaphore semaphore = new Semaphore(0);
        new Thread(() -> {
            for(int i = 0;i < 10;i ++) {
                try {
                    semaphore.acquire();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis());
                semaphore.release();
            }
        }).start();

        new Thread(() -> {
            System.out.println("start:");
            semaphore.release();

        }).start();*/

        TwoThreadExchangePrint ttep = new TwoThreadExchangePrint(10);
        new Thread(() -> {
            try {
                ttep.foo5(() -> {
                    System.out.print(" foo");
                });
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                ttep.bar5(() -> {
                    System.out.print("bar ");
                });
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
