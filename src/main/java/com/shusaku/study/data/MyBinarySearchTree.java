package com.shusaku.study.data;

import java.util.LinkedList;

/**
 *
 * 二叉查找树
 * 要求所有的项都能够排序，树中的两项都能够用compareTo进行比较
 * 节点的左子树中的元素肯定比父节点的元素小，右子树中的所有元素都比父节点的元素大
 *
 * @author liuzi
 */
public class MyBinarySearchTree<T extends Comparable<? super T>> {

    public static void main(String[] args){
        MyBinarySearchTree<Integer> tree = new MyBinarySearchTree<>();
        tree.insert(3);
        tree.insert(1);
        tree.insert(4);
        tree.insert(6);
        tree.insert(9);
        tree.insert(2);
        tree.insert(5);
        tree.insert(7);
        tree.printTree();
    }

    private static class BinaryNode<T>{

        T element;
        BinaryNode<T> left;
        BinaryNode<T> right;

        BinaryNode(T element,BinaryNode<T> left,BinaryNode<T> right){
            this.element = element;
            this.left = left;
            this.right = right;
        }

        BinaryNode(T element){
            this(element,null,null);
        }
    }

    private BinaryNode<T> root;

    public MyBinarySearchTree(){
        root = null;
    }

    public void makeEmpty(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public boolean contains(T t){
        return contains(t,root);
    }

    public T findMin(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        return findMin(root).element;
    }

    public T findMax(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        return findMax(root).element;
    }

    public BinaryNode<T> findMax(BinaryNode<T> root){
        if(root == null){
            return null;
        }else if(root.right == null){
            return root;
        }else{
            return findMax(root.right);
        }
    }

    public void insert(T t){
        root = insert(t,root);
    }

    public void remove(T t){
        root = remove(t,root);
    }

    public void printTree(){
        if(isEmpty()){
            throw new NullPointerException();
        }else{
            printTree(root);
        }
    }

    /**
     * 此种方式为树的中序遍历
     * 一个中序遍历的一般方法是首先处理左子树，然后是当前节点，最后处理右子树。总运行时间为O（N）
     *
     * 后序遍历：先处理两个子树再处理当前节点（一般用于计算节点高度）
     * 先序遍历：先处理当前节点在处理两个子树（一般用于计算节点深度）
     *
     * @param root
     */
    public void printTree(BinaryNode<T> root){
        if(root != null){
            printTree(root.left);
            System.out.println(root.element);
            printTree(root.right);
        }
    }

    //计算节点的高度
    public int height(BinaryNode<T> root){
        if(root == null){
            return -1;
        }else{
            return 1 + Math.max(height(root.left),height(root.right));
        }
    }




    public BinaryNode<T> remove(T t, BinaryNode<T> root){
        if(root == null){
            return root;
        }
        int compareResult = t.compareTo(root.element);
        if(compareResult > 0){
            root.right = remove(t,root.right);
        }else if(compareResult < 0){
            root.left = remove(t,root.left);
        }else if(root.left != null && root.right != null) {
            //匹配到被删除节点  被删除节点有两个子节点  那么需要右子树的最小元素代替被删除元素  然后递归删除该元素
            root.element = findMin(root.right).element;
            root.right = remove(root.element,root.right);
        }else{
            //匹配到被删除节点  被删除节点只有单个子节点或没有子节点
            root = (root.left == null) ? root.right : root.left;
        }
        return root;
    }


    public BinaryNode<T> insert(T t,BinaryNode<T> root){
        //true insert
        if(root == null){
            return new BinaryNode<T>(t,null,null);
        }

        int compareResult = t.compareTo(root.element);

        if(compareResult > 0){
            root.right = insert(t,root.right);
        }else if(compareResult < 0){
            root.left = insert(t,root.left);
        }else{

        }
        return root;
    }


    public BinaryNode<T> findMin(BinaryNode<T> root){
        if(root == null){
            return null;
        }else if(root.left == null){
            return root;
        }else{
            return findMin(root.left);
        }
    }

    public boolean contains(T t,BinaryNode<T> root){
        if(root == null){
            return false;
        }
        if(t.compareTo(root.element) > 0){
            return contains(t,root.right);
        }else if(t.compareTo(root.element) < 0){
            return contains(t,root.left);
        }else{
            return true;
        }
    }

    /**
     * 非递归方式中序遍历树
     * @param node
     */
    public void printTreeMidUnrecur(BinaryNode<T> node){
        BinaryNode<T> n = node;
        LinkedList<BinaryNode> stack = new LinkedList<>();
        while (n != null || !stack.isEmpty()){
            if(n != null){
                //当前节点n不为null，压入栈中，并将当前节点赋值给左儿子
                stack.push(n);
                n = n.left;
            }else{
                /**
                 * 当前节点为null
                 * 当n指向左儿子时，此时栈顶元素必然是他的父节点
                 * 当n指向右儿子时，此时栈顶元素必然是他的爷爷节点
                 * 取出，并访问栈顶元素  赋值为right
                 */
                n = stack.pop();
                System.out.println(n.element);
                n = n.right;
            }
        }
    }

    private void printTreePreUnrecur(BinaryNode<T> node){
        BinaryNode<T> n = node;
        LinkedList<BinaryNode> stack = new LinkedList<>();
        while(n != null || !stack.isEmpty()){
            if(n != null){
                stack.push(n);
                System.out.println(n.element);
                n = n.left;
            }else{
                n = stack.pop();
                n = n.right;
            }
        }
    }

    private void printTreeBacUnrecur(BinaryNode<T> node){

        class NodeFlag<T>{
            BinaryNode<T> node;
            char tag;
            public NodeFlag(BinaryNode<T> node,char tag){
                super();
                this.node = node;
                this.tag = tag;
            }
        }
        NodeFlag<T> bt;
        BinaryNode<T> n = node;
        LinkedList<NodeFlag<T>> stack = new LinkedList<>();
        //节点不为空或者栈不为空时循环
        while(n != null && !stack.isEmpty()){
            //
            while(n != null){

            }
            while(!stack.isEmpty() && stack.getFirst().tag == 'R'){

            }
            if(!stack.isEmpty()){

            }
        }



    }



}
