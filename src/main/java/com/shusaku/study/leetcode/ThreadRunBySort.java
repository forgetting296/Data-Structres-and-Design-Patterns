package com.shusaku.study.leetcode;

/**
 * @program: Java8Test
 * @description: 多线程按顺序方法   按照顺序执行
 * @author: Shusaku
 * @create: 2020-01-11 10:09
 */
public class ThreadRunBySort {

    public ThreadRunBySort() {

    }

    private boolean firstFinished = false;
    private boolean secondFinished = false;
    private Object lock = new Object();

    public void first(Runnable printFirst) throws InterruptedException {

        synchronized (lock) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            firstFinished = true;
            lock.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {

        synchronized (lock) {

            while(!firstFinished) {
                lock.wait();        //wait()不会导致死锁  会主动释放锁
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            secondFinished = true;
            lock.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {

        synchronized (lock) {
            while(!secondFinished) {
                lock.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

}
