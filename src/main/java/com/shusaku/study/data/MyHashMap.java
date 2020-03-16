package com.shusaku.study.data;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author liuzi
 */
public class MyHashMap<K,V> extends AbstractMap<K,V> {

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
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public final boolean equals(Object o){
            if(o == this){
                return true;
            }
            if(o instanceof Map.Entry){
                Entry<?,?> entry = (Entry<?, ?>) o;
                if(Objects.equals(key,entry.getKey()) && Objects.equals(value,entry.getValue())){
                    return true;
                }
            }
            return false;
        }

    }



    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
