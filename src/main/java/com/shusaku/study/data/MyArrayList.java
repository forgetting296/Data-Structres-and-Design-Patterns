package com.shusaku.study.data;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author liuzi
 */
public class MyArrayList<T> {

    /**
     * 容器默认大小
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 默认的空容器
     */
    private static final Object[] DEFAULTCAPATITY_ENPTY_ELEMENTDATA = {};

    /**
     * 空容器   用于空实例的空数组容器
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    private Object[] elementData;
    private int theSize;
    private int modCount;

    /**
     * 初始化一个指定容量的容器
     * @param initalCapacity
     */
    public MyArrayList(int initalCapacity){
        if(initalCapacity < 0) {
            throw new IllegalArgumentException();
        }else if(initalCapacity == 0){
            this.elementData = EMPTY_ELEMENTDATA;
        }else{
            this.elementData = new Object[initalCapacity];
        }
    }

    /**
     * 初始化一个容量为空的数组  返回默认空数组
     */
    public MyArrayList(){
        this.elementData = DEFAULTCAPATITY_ENPTY_ELEMENTDATA;
    }

    /**
     * 根据传入的Collection创建对应的容器  并将Collection中的元素传入容器数组中
     * @param c
     */
    public MyArrayList(Collection<? extends T> c){
        this.elementData = c.toArray();
        if((theSize = elementData.length) != 0){
            if(elementData.getClass() != Object[].class){
                elementData = Arrays.copyOf(elementData,theSize,Object[].class);
            }
        }else{
            elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * 修改当前list为当前size的大小
     */
    public void trimToSize(){
        if(theSize < elementData.length){
            elementData = (theSize == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData,theSize);
        }
    }

    /**
     * 如果必要  增加此ArrayList的容量 以保证可以保存minCapicity指定的  元素数量
     * @param minCapicity
     */
    public void ensureCapicity(int minCapicity){
        int minExpand = (elementData != DEFAULTCAPATITY_ENPTY_ELEMENTDATA) ? 0 : DEFAULT_CAPACITY;
        if(minExpand < minCapicity){
            ensureExplicitCapicity(minCapicity);
        }
    }

    public int size(){
        return theSize;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public boolean contains(Object o){
        return indexOf(0) >= 0;
    }

    public int indexOf(Object o) {

        if(o == null){
            for(int i = 0;i < theSize;i ++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        }else{
            for(int i = 0;i < theSize;i ++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public T get(int index){
        rangeCheck(index);
        return elementData(index);
    }

    public T set(int index,T t){
        rangeCheck(index);

        T oldElement = elementData(index);
        elementData[index] = t;
        return oldElement;
    }

    public boolean add(T t){
        ensureCapicityInternal(theSize + 1);
        elementData[theSize ++] = t;
        return true;
    }

    public boolean clean(){

        for(int i = 0;i < theSize;i ++){
            elementData[i] = null;
        }
        theSize = 0;
        return true;
    }

    /**
     * 在指定索引位置添加元素
     * 模拟
     * @param index
     * @param t
     * @return
     */
    public boolean add(int index,T t){
        ensureCapicityInternal(theSize + 1);

        if(index >= theSize || index < 0){
            throw new IndexOutOfBoundsException();
        }

        for(int i = index;i < theSize - 1;i ++){
            elementData[i + 1] = elementData[i];
        }
        elementData[index] = t;
        theSize ++;
        return true;
    }

    public T remove(int index){
        checkIndex(index);
        T o = (T)elementData[index];
        for(int i = index;i < theSize - 1;i ++){
            elementData[index] = elementData[index + 1];
        }
        theSize --;
        return  o;
    }

    public boolean remove(Object o){

        if(o == null){
            for(int i = 0;i < theSize;i ++){
                if(elementData[i] == null){
                    fastRemove(i);
                    return true;
                }
            }
        }else{
            for(int i = 0;i < theSize;i ++){
                if(o.equals(elementData[i])){
                    fastRemove(i);
                    return true;
                }
            }
        }

        return false;
    }

    private void fastRemove(int index) {

        int numMove = theSize - index - 1;
        if(numMove > 0){
            /**
             * 参数：
             *  目标数组1
             *  目标数组1的开始位置
             *  目标数组2
             *  目标数组2的开始位置
             *  范围
             *
             *  将原始数组起始位置开始的元素  复制到  目标数组的起始位置  并将之前位置的元素覆盖
             */
            System.arraycopy(elementData,index + 1,elementData,index,numMove);
        }
        //处理变动范围之外的元素   都置为null
        elementData[theSize--] = null;
    }

    protected void removeRange(int fromIndex,int toIndex){

        int numMove = toIndex - fromIndex;
        System.arraycopy(elementData,toIndex,elementData,fromIndex,numMove);

        int newSize = theSize - (toIndex - fromIndex);
        for(int i = newSize;i < theSize;i ++){
            elementData[i] = null;
        }

        theSize = newSize;
    }

    private void checkIndex(int index){
        if(index >= theSize || index < 0){
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * JDK8实现方式
     * @param index
     * @param t
     * @return
     */
    public boolean add2(int index,T t){
        ensureCapicityInternal(theSize + 1);

        if(index >= theSize || index < 0){
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(elementData,index,elementData,index + 1,theSize - index);
        elementData[index] = t;
        theSize ++;
        return true;
    }

    public T remove2(int index){
        checkIndex(index);
        T t = elementData(index);
        int numMove = theSize - index - 1;
        if(numMove > 0){
            System.arraycopy(elementData,index + 1,elementData,index,numMove);
        }
        theSize --;
        return t;
    }

    /**
     * 确保内部容量
     * @param minCapicity
     */
    private void ensureCapicityInternal(int minCapicity) {
        //精准容量  根据传入的minCapicity  如果小于elementData的长度  扩容
        ensureExplicitCapicity(calculateCapicity(elementData,minCapicity));
    }

    /**
     * 计算容量
     * @param elementData
     * @param minCapicity
     * @return
     */
    private int calculateCapicity(Object[] elementData, int minCapicity) {
        if(DEFAULTCAPATITY_ENPTY_ELEMENTDATA == elementData){
            return Integer.max(DEFAULT_CAPACITY,minCapicity);
        }
        return minCapicity;
    }

    private void rangeCheck(int index){
        if(index > elementData.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    T elementData(int index){
        return (T) elementData[index];
    }

    /**
     * 明确容量  如果minCapicity大于容器现在容量  进行扩容
     * @param minCapicity
     */
    private void ensureExplicitCapicity(int minCapicity) {

        if(minCapicity - elementData.length > 0){
            grow(minCapicity);
        }
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 扩容方法
     * 扩容大小为当前容量的1.5倍  再次与minCapicity比较
     * 如果小于minCapicity 直接扩容到minCapicity的大小
     * 还要与MAX_ARRAY_SIZE进行比较
     * 最后完成扩容  Arrays.copyOf(Object[],size)
     * @param minCapicity
     */
    private void grow(int minCapicity) {

        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        if(newCapacity < minCapicity) {
            newCapacity = minCapicity;
        }
        if(newCapacity > MAX_ARRAY_SIZE) {
            newCapacity = hugeCapicity(minCapicity);
        }

        elementData = Arrays.copyOf(elementData,newCapacity);

    }

    private int hugeCapicity(int minCapicity) {
        if(minCapicity < 0) {
            throw new OutOfMemoryError();
        }
        return minCapicity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    /**
     *
     */
    private class Itr implements Iterator<T>{

        private int cursor;             //要返回的下一个元素的索引
        private int lastSet = -1;       //要返回的元素的索引  如果没有  返回-1
        private int expectedModCount = modCount;

        Itr(){}
        @Override
        public boolean hasNext() {
            return cursor != theSize;
        }

        @Override
        public T next() {
            //校验迭代中是否进行增删操作
            checkComodification();
            int  i = cursor;
            if(i >= theSize){
                throw new NoSuchElementException();
            }
            //获取MyArrayList中的数组elementData
            Object[] elementData = MyArrayList.this.elementData;
            if(i >= elementData.length){
                throw new ConcurrentModificationException();
            }
            cursor = i + 1;
            lastSet = i;
            return (T)elementData[lastSet];
            //return elementData[lastSet = i];
        }

        @Override
        public void remove() {
            if(lastSet < 0){
                throw new IllegalStateException();
            }
            checkComodification();
            try {
                MyArrayList.this.remove(lastSet);
                cursor = lastSet;
                lastSet = -1;
                expectedModCount = modCount;
            }catch (IndexOutOfBoundsException e){
                throw new ConcurrentModificationException();
            }
        }

        @Override
        //@SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);


        }

        /**
         * 保证迭代期间  不会有增删操作
         */
        private void checkComodification(){
            if(modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }
        }
    }


}
