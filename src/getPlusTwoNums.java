
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public class getPlusTwoNums {
    /**
     * 单向链表节点定义
     * 这是LeetCode中常用的链表节点结构
     */
    public static class ListNode {
        //这里是类的静态上下文部分
        int val;        // 节点存储的整数值
        ListNode next;  // 指向下一个节点的指针。
        //这是一个自引用类型，在进行编译时，编译器知道这是一个类名，并且已经在当前作用域中声明，并且next是一个引用变量
        //能够指向ListNode类型的对象。这样虽然整个类型还没有定义结束，但类名已经可用
        //在C语言中，这就相当于设计了一个指向自己的指针
        
        /**
         * 默认构造函数
         * 创建一个值为0的节点
         */
        ListNode() {
            this.val = 0;
            this.next = null;
        }
        
        /**
         * 带值构造函数
         * @param val 节点要存储的整数值
         */
        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
        
        /**
         * 完整构造函数
         * @param val 节点要存储的整数值
         * @param next 指向下一个节点的指针
         */
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
        
        /**
         * 重写toString方法，便于调试和输出
         * 重写toString方法在工业上也是推荐的，这能够提供有意义的字符串输出，防止暴露敏感信息并且定制化输出长度限制，避免不必要的性能开销
         * @return 链表的字符串表示
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            ListNode current = this;//this指向调用这个toString方法的对象实例，即某个链表节点（通常为头结点）
            //this的作用域是当前类的实例方法内部，在这个例子中，即为toString方法
            //此外构造方法，实例方法中可以使用this，但静态方法和类的静态部分不能使用
            while (current != null) {
                sb.append(current.val);
                //实际上，这个重写方法的效果，就比自带的多了个箭头
                if (current.next != null) {
                    sb.append(" -> ");
                }
                //将指针指向下一个节点对象
                current = current.next;
            }
            return sb.toString();
        }
    }

    /**
     * 两数相加的解决方案
     * 题目：给定两个非空的链表，表示两个非负的整数。
     * 它们的每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
     * 请将两个数相加，并以相同形式返回一个表示和的链表。
     */
    class Solution {
        /**
         * 两数相加主方法
         * @param l1 第一个数字的链表表示（逆序）
         * @param l2 第二个数字的链表表示（逆序）
         * @return 两数相加结果的链表表示（逆序）
         */
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            // 创建虚拟头节点，简化边界处理
            ListNode dummyHead = new ListNode(0);
            ListNode current = dummyHead;
            int carry = 0; // 进位值
            
            // 当l1、l2不为空或还有进位时继续循环
            while (l1 != null || l2 != null || carry != 0) {
                // 获取当前位的值，如果节点为空则视为0
                int val1 = (l1 != null) ? l1.val : 0;
                int val2 = (l2 != null) ? l2.val : 0;
                
                // 计算当前位的和（包括进位）
                int sum = val1 + val2 + carry;
                
                // 计算新的进位值
                carry = sum / 10;
                
                // 创建新节点存储当前位的值
                current.next = new ListNode(sum % 10);
                current = current.next;
                
                // 移动到下一个节点
                if (l1 != null) l1 = l1.next;
                if (l2 != null) l2 = l2.next;
            }
            
            // 返回结果链表的头节点（跳过虚拟头节点）
            return dummyHead.next;
        }
    }
    
    /**
     * 辅助方法：创建链表
     * @param values 要存储的整数值数组
     * @return 创建的链表头节点
     */
    public static ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        // 创建测试用例
        // 示例1: l1 = [2,4,3], l2 = [5,6,4]
        // 表示 342 + 465 = 807，结果应该是 [7,0,8]
        int[] arr1 = {2, 4, 3};
        int[] arr2 = {5, 6, 4};
        
        ListNode l1 = createList(arr1);
        ListNode l2 = createList(arr2);
        
        System.out.println("链表1: " + l1);
        System.out.println("链表2: " + l2);
        
        // 执行相加操作
        Solution solution = new getPlusTwoNums().new Solution();
        ListNode result = solution.addTwoNumbers(l1, l2);
        
        System.out.println("相加结果: " + result);
        
        // 验证：342 + 465 = 807
        System.out.println("验证: 342 + 465 = 807");
    }
}
