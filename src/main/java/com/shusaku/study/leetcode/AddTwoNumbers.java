package com.shusaku.study.leetcode;

/**
 * @program: Java8Test
 * @description: 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。  如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。  来源：力扣（LeetCode） 链接：https://leetcode-cn.com/problems/add-two-numbers 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: Shusaku
 * @create: 2020-01-10 14:08
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode dummyHead = new ListNode(0);
        ListNode p = l1,q = l2,curr = dummyHead;
        int carry = 0;
        while(p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if(p != null) p = p.next;
            if(q != null) q = q.next;
        }
        if(carry > 0){
            curr.next = new ListNode(carry);
        }

        return dummyHead.next;
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        int i = 0;
        int j = 0;
        boolean flag = true;
        while (flag) {
            if(l1.next == null && l2.next == null) {
                flag = false;
            }
            if(l1.next != null){
                j += Math.pow(10,i) * l1.val;
                l1 = l1.next;
            }
            if(l2.next != null){
                j += Math.pow(10,i) * l2.val;
                l2 = l2.next;
            }
            i ++;
        }
        return new ListNode(j);
    }

    public class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
    }

}
