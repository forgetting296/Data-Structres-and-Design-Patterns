package com.shusaku.study.data;

/**
 *
 * 带有平衡条件的二叉查找树
 * 一颗AVL树的左子树和右子树的高度最多差1，一个AVL树的高度最多为1.44log(N + 2) - 1.328
 * 除了可能的插入外（我们假设删除为懒惰删除），所有的树操作都可以以时间O(log N)执行
 * 插入一个节点可能会破坏AVL树的特性，可以通过旋转，对树进行简单的修正，不平衡的情况有以下四种：
 *      a、对某节点的左儿子的左子树进行插入(单旋)
 *      b、对某节点的左儿子的右子树进行插入(双旋)
 *      c、对某节点的右儿子的左子树进行插入(双旋)
 *      d、对某节点的右儿子的右子树进行插入(单旋)
 *
 * @author liuzi
 */
public class MyAVLTree <T extends Comparable<? super T>> {

    private static class AvlNode<T>{
        T element;
        AvlNode<T> left;
        AvlNode<T> right;
        int height;

        AvlNode(T element,AvlNode<T> left,AvlNode<T> right){
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

        AvlNode(T element){
            this(element,null,null);
        }
    }

    /**
     * 获取某节点的高度
     * @param node
     * @return
     */
    private int height(AvlNode<T> node){
        return node == null ? -1:node.height;
    }

    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * 左子树进行单旋
     * @param
     * @return 返回的是新的根节点
     */
    private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2){
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left),height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left),height(k1.right)) + 1;
        return k1;
    }

    /**
     * 左子树进行双旋转
     * 实际是对该节点左子树进行 右单旋
     * 之后对该节点进行左单旋
     * @param k3
     * @return
     */
    private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3){
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     *  右子树进行单旋转
     * @param k1
     * @return
     */
    private AvlNode<T> rotateWithRightChild(AvlNode<T> k1){
        AvlNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k2.height = Math.max(height(k2.left),height(k2.right));
        k1.height = Math.max(height(k1.left),height(k1.right));
        return k2;
    }

    /**
     * 右子树进行双旋转
     * 实际是对该节点的 右子树进行左单旋转
     * 之后对该节点进行右单旋转
     * @param k3
     * @return
     */
    private AvlNode<T> doubleWithRightChild(AvlNode<T> k3){
        k3.left = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    public AvlNode<T> insert(T t,AvlNode<T> node){
        if(node == null){
            return new AvlNode<T>(t,null,null);
        }
        int compareResult = t.compareTo(node.element);
        if(compareResult > 0){
            node.right = insert(t,node.right);
        }else if(compareResult < 0){
            node.left = insert(t,node.left);
        }else{

        }
        return balance(node);
    }

    /**
     * 平衡方法
     * 如果某节点的左右子树高度相差超过1  需要进行平衡
     *      如果左子树的左子树高度 大于等于 左子树的右子树高度  进行单旋  等于的情况也算进去  确保单旋  因为等于时不进行双旋  否则进行双旋
     *      如果右子树的右子树高度 大于等于 右子树的左子树高度  进行单旋  等于的情况也算进去  确保单旋  因为等于时不进行双旋  否则进行双旋
     * @param node
     * @return
     */
    private AvlNode<T> balance(AvlNode<T> node) {

        if(node == null){
            return node;
        }
        //不平衡  左右子树高度差大于1
        if(height(node.left) - height(node.right) > ALLOWED_IMBALANCE){
            if(height(node.left.left) >= height(node.left.right)){
                //单旋
                rotateWithLeftChild(node);
            }else{
                //双旋
                doubleWithLeftChild(node);
            }
        }else if(height(node.right) - height(node.left) > ALLOWED_IMBALANCE){
            if(height(node.right.right) >= height(node.right.left)){
                //单旋
                rotateWithRightChild(node);
            }else{
                //双旋
                doubleWithRightChild(node);
            }
        }
        node.height = Math.max(height(node.left),height(node.right)) + 1;
        return node;
    }

    private AvlNode<T> remove(T t,AvlNode<T> node){
        if(node == null){
            return node;
        }
        int compareResulr = t.compareTo(node.element);
        if(compareResulr > 0){
            node.right = remove(t,node.right);
        }else if(compareResulr < 0){
            node.left = remove(t,node.left);
        }else if(node.left != null && node.right != null){
            node.element = findMin(node).element;
            node.right = remove(node.element,node.right);
        }else{
            node = node.left == null ? node.right : node.left;
        }
        return balance(node);
    }

    private AvlNode<T> findMin(AvlNode<T> node) {
        if(node == null){
            return node;
        }
        if(node.left == null){
            return node;
        }else{
            return findMin(node.left);
        }
    }

    private AvlNode<T> findMax(AvlNode<T> node){
        if(node == null){
            return node;
        }
        if(node.right == null){
            return node;
        }else{
            return findMax(node.right);
        }

    }


}
