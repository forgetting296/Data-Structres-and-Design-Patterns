package com.shusaku.study.data;

/**
 *
 * 二叉堆
 * 结构性、堆序性
 * 结构性：二叉堆是一颗被完全填满的二叉树、例外的地方应该是在最后一层
 * 堆序性：任意一个节点X的元素应该小于他的所有后裔
 * 我们可以用数组实现二叉堆  索引从1开始，任意节点的子节点索引为该节点的索引index * 2 和index * 2 + 1
 *
 * @author liuzi
 */
public class MyBinaryHeap <T extends Comparable<? super T>>{

    int currentSize = 0;
    T[] array;

    /**
     * 二叉堆，使用数组实现，插入元素  进行排序
     * 上滤
     * 将一个元素X插入堆中，我们可以在下一个可用的位置创建一个空穴，
     * 如果该元素可以放在该空穴中而不破坏堆序性，插入完成
     * 否则，我们需要将父节点的元素放到空穴中，这样，空穴就朝着根的方向，上冒了一步
     * 继续该过程，直到X能被放到空穴中为止
     * @param t
     */
    public void insert(T t){
        if(currentSize == array.length - 1){
            //扩容
        }
        int hole = ++currentSize;
        for(array[0] = t;t.compareTo(array[hole/2]) < 0; hole /= 2){
            array[hole] = array[hole/2];
        }
        array[hole/2] = t;
    }

    /**
     * 删除最小元素
     * 根节点是最小元素  在根节点处建立空穴，进行下滤
     *
     * 当删除一个最小元素时，要在根节点位置建立一个空穴。
     * 由于现在少了一个元素，因此堆中最后一个元素X必须要移动到该堆得某个地方，
     * 如果X可以直接放到空穴中，删除完成。不过这一般不太可能，我们需要将空穴的子节点中较小的放到空穴
     * 这样空穴就向下移动了一步，重复该步骤，直到X可以被放到空穴中。
     *
     * 在堆得实现中，经常发生的错误是，当堆中存在偶数个元素的时候，将遇到一个节点只有一个儿子的情况。
     * 我们必须保证节点不总有两个儿子的前提，这就涉及到一个附加的测试。我们始终把一个节点看成有两个儿子
     * 所以我们加了一个判断child != currentSize && array[child + 1].compareTo(array[child]) < 0
     * 首先保证child ！= currentSize，这样索引不会越界  后面的比较  无所谓
     */
    public void deleteMin(){

        //判空  isEmpty()
        if(false){
            //抛出异常
        }
        T minItem = findMin();
        array[1] = array[currentSize --];
        percolateDown(1);
    }

    /**
     * 下滤
     * 找到最小元素，hole是父节点的数组索引，
     * child = hole * 2;比较 child 和 child + 1 位置的元素，
     * 小于的话  child ++；然后与父节点比较，如果子节点小于父节点，调换位置
     * @param hole
     */
    private void percolateDown(int hole){
        int child;
        T temp = array[hole];
        for(;hole * 2 <= currentSize;hole = child){
            child = hole * 2;
            if(child != currentSize && array[child + 1].compareTo(array[child]) < 0){
                child ++;
            }
            if(array[child].compareTo(temp) < 0){
                array[hole] = array[child];
            }else{
                break;
            }
        }
        array[hole] = temp;
    }

    public T findMin(){

        return null;
    }

    /**
     * 由N项初始元素的集合，构建一个二叉堆
     * @param arr
     */
    public MyBinaryHeap(T[] arr){
        currentSize = arr.length;
        //新建一个指定大小的容器
        array = (T[]) new Comparable[(currentSize + 2) * 11 / 10];
        int i = 1;
        //将元素从索引1开始，放到数组容器中
        for(T t : arr){
            array[i ++] = t;
        }
        //循环下滤
        buildHeap();
    }

    public void buildHeap(){
        for(int i = currentSize / 2;i > 0; i --){
            percolateDown(i);
        }
    }

}
