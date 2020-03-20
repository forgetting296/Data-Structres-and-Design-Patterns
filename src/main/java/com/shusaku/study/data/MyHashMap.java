package com.shusaku.study.data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author liuzi
 */
public class MyHashMap<K,V> extends AbstractMap<K,V> implements Map<K,V> , Cloneable, Serializable {

    /**
     * 初始化大小　16　　HashMap的长度总是２的幂
     */
    static final int DEFAULT_INITAL_CAPACITY = 1 << 4;

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final float DEFAULT_LOAD_FACTORY = 0.75f;

    /**
     * jdk8新增的　如果链表节点数量超过8　　将数组+链表的数据形式转为红黑树
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 小于TREEIFY_THRESHOLD　并且至多为6的时候　　将红黑树转为链表+数组的形式
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     *  数组长度超过64的时候将转为红黑树
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * 基本的散列节点　
     * @param <K>
     * @param <V>
     */
    static class Node<K,V> implements Entry<K,V>{

        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash,K key,V vlaue,Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = vlaue;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }



        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public final boolean equals(Object o){
            if(o == this) {
                return true;
            }
            if(o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                if(Objects.equals(e.getKey(),this.key) && Objects.equals(e.getValue(),this.value)){
                    return true;
                }
            }
            return false;
        }
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); //右移16位　前面补0
    }

    /**
     * 如果实现了　Comparable　接口　　否则返回null
     * @param x
     * @return
     */
    static Class<?> comparableClassFor(Object x) {
        if(x instanceof Comparable) {
            Class<?> c;
            Type[] ts, as;
            Type t;
            ParameterizedType p;
            //bypass checks
            if((c = x.getClass()) == String.class) {
                return c;
            }
            if((ts = c.getGenericInterfaces()) != null) {
                for(int i = 0;i < ts.length;i ++) {
                    if(((t = ts[i]) instanceof ParameterizedType) && ((p = (ParameterizedType)t).getRawType() == Comparable.class) && (as = p.getActualTypeArguments()) != null && as.length == 1 && as[0] == c) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    /**
     * return k.compareTo(x)
     * 如果　x 和 k 是同一类型　并且k是Comparable的子类　返回比较的值　否则返回 0
     * @param kc
     * @param k
     * @param x
     * @return
     */
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc) ? 0 : ((Comparable) k).compareTo(x);
    }

    /**
     * 根据给定目标容量　返回　对应的tableSize
     * @return
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1; //n = n | n >>> 1
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    //=============================================================================Fields========================================================================================

    transient Node<K, V>[] table;

    transient Set<Map.Entry<K, V>> entrySet;

    /**
     * HashMap中　kv对的数量　　链表和树中kv的总和
     */
    transient int size;

    transient int modCount;

    /**
     * 如果HaashMap的size()超过threshold　将会执行resize()　threshold = capacity * loadFactor
     */
    transient int threshold;

    /**
     * 负载因子 size/capicity　达到这个值了　将会执行resize()
     */
    transient float loadFactor;

    //=============================================================================publish operations========================================================================================

    public MyHashMap(int initialCapacity, float loadFactor) {
        if(initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if(initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if(loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.loadFactor = loadFactor;
        //要保证　Map的容量始终为2的幂
        this.threshold = tableSizeFor(initialCapacity);
    }

    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTORY;
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity,DEFAULT_LOAD_FACTORY);
    }

    public MyHashMap(Map<? extends K, ? extends V> m) {
        this.threshold = DEFAULT_INITAL_CAPACITY;
        pubMapEntries(m,false);
    }

    /**
     *
     * @param m
     * @param b
     */
    final void pubMapEntries(Map<? extends K, ? extends V> m, boolean b) {
        int s = m.size();
        if(s > 0) {
            if(table == null) {
                //初始化table  s / loadFactor + 1 得到的结果　是　不发生resize()的最小size
                float ft = ((float) s/loadFactor) + 1.0F;
                int t = ((ft < (float) MAXIMUM_CAPACITY) ? (int) ft : MAXIMUM_CAPACITY);
                if(t > threshold) {
                    threshold = tableSizeFor(t);
                }
            } else if(s > threshold) {
                resize();
            }
            for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                putVal(hash(key),key,value,false,b);
            }
        }
    }

    @Override
    public V put(K key, V value) {
        return putVal(hash(key),key,value,false,true);
    }

    //todo 明天继续
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K, V>[] tab; Node<K, V> p; int n, i;
        if((tab = table) == null || ((n = tab.length) == 0)) {
            n = (tab = resize()).length;
        }
        if((p = tab[i = (n - 1) & hash]) == null) {
            tab[i] = newNode(hash,key,value,null);
        } else {
            Node<K, V> e;K k;
            if(p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            } else if(p instanceof TreeNode) {
                e = ((TreeNode) p).putTreeVal(this,tab,hash,key,value);
            } else {

            }
        }

        return null;
    }

    private Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash,key,value,next);
    }

    /**
     *
     * @return
     */
    final Node<K, V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        //有数据状态进行扩容　旧有的桶的数量
        if(oldCap > 0) {
            //旧的桶　长度大于等于桶上限2^30　　将threshold置为Integer.MAX_VALUE  无法再达到扩容的阈值　返回当前的tab
            if(oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            //扩容之后的容量在最大容量之内　　并且大于默认大小
            else if((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITAL_CAPACITY) {
                newThr = oldThr << 1;
            }
        }
        //旧有的桶数量为０　　但是resize()阈值　大于零　默认值为16 * 0.75 = 12
        //初始容量设为阈值
        else if(oldThr > 0) {
            newCap = oldCap;
        }
        //初始值使用默认值
        else{
            newCap = DEFAULT_INITAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTORY * DEFAULT_INITAL_CAPACITY);
        }
        if(newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft <(float) MAXIMUM_CAPACITY ? (int) ft : Integer.MAX_VALUE);
        }
        threshold = newThr;

        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;
        //进行rehash  因为capacity 变化导致之前的hashcode需要重新计算
        if(oldTab != null) {
            for(int i = 0;i < oldTab.length; i++) {
                Node<K, V> e;
                if((e = oldTab[i]) != null) {
                    oldTab[i] = null;
                    if(e.next == null) {
                        newTab[e.hash & (newCap - 1)] = e;
                    } else if(e instanceof TreeNode) {
                        //todo 树结构的rehash
                        //((TreeNode<K, V>) e).split(this, newTab, i, newCap);
                    }
                    //next关联了Node rehash之后　next关联的节点需要重新标记
                    else {
                        Node<K, V> loHead = null,loTail = null;
                        Node<K, V> hiHead = null,hiTail = null;
                        Node<K, V> next = null;
                        do{
                            //todo next节点定位
                            next = e.next;

                        }while((e = next) != null);
                    }
                }
            }
        }

        return null;
    }


    @Override
    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key),key)) == null ? null : e.value;
    }

    final Node<K, V> getNode(int hash, Object key) {

        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
        if((tab = table) != null && (n = tab.length) > 0 &&
                //tab[0]不是第一个元素吗　要按位与 (n - 1)
                (first = tab[(n - 1) & hash]) != null) {
            //确定一个元素　要比较hashcode & key   key 通过 == 或者　equals　进行比较
            if(first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k)))) {
                return first;
            }
            if((e = first.next) != null) {
                //如果已经转为红黑树
                if(first instanceof TreeNode) {
                    return ((TreeNode<K, V>) first).getTreeNode(hash,key);
                }
                //未进行树化
                do{
                    if(e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))){
                        return e;
                    }
                }while ((e = e.next) != null);
            }
        }

        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(hash(key),key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    static final class TreeNode<K,V> extends MyHashMap.Node<K,V> {

        boolean red;
        TreeNode<K, V> parent;
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;

        final TreeNode<K, V> getTreeNode(int hash, Object k) {
            return ((parent != null) ? root() : this).find(hash, k, null);
        }

        final TreeNode<K, V> root() {
            TreeNode<K, V> r = this;

            while(r.parent != null){
                r = r.parent;
            }
            return r;

        }

        final TreeNode<K, V> find(int h,Object k, Class<?> kc) {

            TreeNode<K, V> p = this;
            do{
                int ph,dir; K pk;
                TreeNode<K, V> pl = p.left, pr = p.right, q;
                //目标节点的hashcode　小于当前节点的hashcode 那么目标节点在当前节点的左边
                if((ph = p.hash) > h){
                    p = pl;
                }
                //目标节点的hashcode　大于当前节点的hashcode 那么目标节点在当前节点的右边
                else if(ph < h) {
                    p = pr;
                }
                //命中节点　　判断key的值是否相等  == || equals
                else if((pk = p.key) == k || (pk != null && pk.equals(k))) {
                    return p;
                }
                //命中节点之后　　key的值并不相等　　继续探索
                else if(pl == null) {
                    p = pr;
                }
                //命中节点之后　　key的值并不相等　　继续探索
                else if(pr == null) {
                    p = pl;
                }
                //
                else if((kc != null || (kc = comparableClassFor(k)) != null) && (dir = compareComparables(kc,k,pk)) != 0) {
                    p = (dir < 0) ? pl : pr;
                }
                //从右侧探索目标节点　　如果没有命中　　从左侧继续探索
                else if((q = pr.find(h, k , kc)) != null) {
                    return q;
                } else {
                    p = pl;
                }
            }while(p != null);

            return null;
        }

        TreeNode(int hash, K key, V vlaue, Node<K, V> next) {
            super(hash, key, vlaue, next);
        }

        //TODO 今天头晕了　回头继续
        final Node<K, V> putTreeVal(MyHashMap kvMyHashMap, Node<K, V>[] tab, int hash, K key, V value) {
            return null;
        }
    }

    //todo　实现接口必须重写　　待实现
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
