package com.shusaku.study.data;

/**
 * @author liuzi
 */
public class MyLeftistHeap <T extends Comparable<? super T>>{

    private static class Node<T>{
        T element;
        Node<T> left;
        Node<T> right;
        int npl;        //null path length

        Node(T element,Node<T> left,Node<T> right){
            this.element = element;
            this.left = left;
            this.right = right;
            this.npl = 0;
        }
        Node(T element){
            this(element,null,null);
        }
    }

    private Node<T> root;

    public MyLeftistHeap(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void makeEmpty(){
        root = null;
    }

    public void merge(MyLeftistHeap<T> rhs){
        if(this == rhs){
            return;
        }
        root = merge(root,rhs.root);
        rhs.root = null;
    }

    private Node<T> merge(Node<T> node1 ,Node<T> node2){
        if(node1 == null){
            return node2;
        }
        if(node2 == null){
            return node1;
        }

        if(node1.element.compareTo(node2.element) < 0){
            return merge1(node1,node2);
        }else{
            return merge1(node2,node1);
        }
    }

    private Node<T> merge1(Node<T> node1,Node<T> node2){
        if(node1.left == null){
            node1.left = node2;
        }else{
            node1.right = merge1(node1.right,node2);
            if(node1.left.npl < node1.right.npl){
                //进行左右子树调换
            }
            node1.npl = node1.right.npl + 1;
        }
        return node1;
    }

}
