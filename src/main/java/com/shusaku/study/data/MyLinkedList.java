package com.shusaku.study.data;

import java.util.*;

/**
 * @author liuzi
 */
public class MyLinkedList<T>{

    public static void main(String[] args){
        /*for(long time = 1,love = 0;love < time;++ time,++ love){
            if(love < 0){break;}//-9223372036854775808
        }*/
        long love = 9223372036854775807L + 1L;
        System.out.println(love);
        LinkedList<String> linked = new LinkedList<>();
    }

    /**
     * 用于记录链表长度
     */
    transient int theSize = 0;

    /**
     * 用于记录链表首端节点
     */
    transient Node<T> first;

    /**
     * 用于记录链表尾端节点
     */
    transient Node<T> last;

    /**
     * 用于记录链表修改次数  用来检测迭代时是否有增删操作
     */
    transient int modCount;

    public MyLinkedList(){}

    public MyLinkedList(Collection<? extends T> c){
        this();
        addAll(c);
    }

    public void clean(){
        for(Node<T> node = first; node != null;){
            Node<T> next = node.next;
            node.prev = null;
            node.next = null;
            node.theItem = null;
            node = next;
        }
    }

    public int indexOf(Object o){
        int index = 0;
        if(o == null){
            for(Node<T> x = first;x != null;x = x.next){
                if(x == null){
                    return index;
                }
                index ++;
            }
        }else{
            for(Node<T> x = first;x != null;x = x.next){
                if(o.equals(x)){
                    return index;
                }
                index ++;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o){
        int index = theSize;
        if(o == null){
            for(Node<T> x = last;x != null;x = x.prev){
                if(x == null){
                    return index;
                }
                index --;
            }
        }else{
            for(Node<T> x = last;x != null;x = x.prev){
                if(o.equals(x)){
                    return index;
                }
                index --;
            }
        }
        return -1;
    }

    public boolean addAll(Collection<? extends T> c) {
        return addAll(theSize,c);
    }

    public boolean addAll(int size, Collection<? extends T> c) {
        checkPositionIndex(size);

        Object[] objs = c.toArray();
        int newSize = objs.length;
        if(newSize == 0){
            return false;
        }

        Node<T> begin,end;
        if(size == theSize){
            begin = last;
            end = null;
        }else{
            end = node(size);
            begin = end.prev;
        }

        for(Object obj:objs){
            T t = (T) obj;
            Node<T> node = new Node<>(t,begin,null);
            if(begin == null){
                first = node;
            }else{
                begin.next = node;
            }
            begin = node;
        }

        if(end == null){
            last = begin;
        }else{
            begin.next = end;
            end.prev = begin;
        }
        theSize += newSize;
        modCount ++;
        return true;
    }

    public ListIterator<T> MyListIterator(int index){
        checkPositionIndex(index);
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<T>{

        private Node<T> lastReturned;
        private Node<T> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        ListItr(int index){
            next = (index == theSize) ? null:node(index);
            nextIndex = index;
        }

        final void checkForComofication(){
            if(expectedModCount != modCount){
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            return nextIndex < theSize;
        }

        @Override
        public T next() {
            checkForComofication();
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.theItem;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public T previous() {
            checkForComofication();
            if(!hasPrevious()){
                throw new NoSuchElementException();
            }
            lastReturned = (next == null) ? last : next.prev;
            nextIndex --;
            return lastReturned.theItem;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            checkForComofication();
            if(lastReturned == null){
                throw new IllegalStateException();
            }
            Node<T> lastNext = lastReturned.next;
            unLink(lastNext);
            expectedModCount = modCount;
        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }

    public T getFirst(){
        Node<T> f = first;
        if(f == null){
            throw new NoSuchElementException();
        }
        return f.theItem;
    }

    public T getLast(){
        Node<T> l = last;
        if(l == null){
            throw new NoSuchElementException();
        }
        return l.theItem;
    }

    public T removeLast(){
        Node<T> l = last;
        if(l == null){
            throw new NoSuchElementException();
        }
        return unLink(l);
    }

    public T removeFirst(){
        Node<T> l = first;
        if(l == null){
            throw new NoSuchElementException();
        }
        return unLink(l);
    }

    private void linkFirst(T t){
        Node<T> f = first;
        Node<T> node = new Node<>(t,null,f);
        first = node;
        if(f == null){
            last = node;
        }else{
            f.prev = node;
        }
        theSize ++;
        modCount ++;
    }

    private void linkLast(T t){
        Node<T> l = last;
        Node<T> node = new Node<>(t,l,null);
        if(l == null){
            last = node;
        }else{
            l.next = node;
        }
        theSize ++;
        modCount ++;
    }

    private T unLink(Node<T> lastNext) {

        Node<T> prev = lastNext.prev;
        Node<T> next = lastNext.next;
        T theItem = lastNext.theItem;

        if(prev == null){
            first = next;
        }else{
            prev.next = next;
            lastNext.prev = null;
        }
        if(next == null){
            last = prev;
        }else{
            next.prev = prev;
            lastNext.next = null;
        }
        lastNext.theItem = null;
        theSize --;
        modCount ++;
        return theItem;
    }

    private Node<T> node(int size) {

        if(size < (theSize >> 1)){
            Node<T> n = first;
            for(int i = 0;i < size;i ++){
                n = n.next;
            }
            return n;
        }else{
            Node<T> n = last;
            for(int i = theSize - 1;i > size;i --){
                n = n.prev;
            }
            return n;
        }
    }

    private void checkPositionIndex(int index){
        if(!isPositionIndex(index)){
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= theSize;
    }

    private static class Node<T> {
        T theItem;
        Node<T> prev;
        Node<T> next;
        Node (T t,Node<T> prev,Node<T> next){
            this.next = next;
            this.theItem = t;
            this.prev = prev;
        }
    }


}
