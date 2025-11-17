/**
 * LeetCode 206. 反转链表 - Reverse Linked List
 * 
 * 题目描述：
 * 给你单链表的头节点 head，请你反转链表，并返回反转后的链表。
 * 
 * 示例 1：
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 * 
 * 示例 2：
 * 输入：head = [1,2]
 * 输出：[2,1]
 * 
 * 示例 3：
 * 输入：head = []
 * 输出：[]
 * 
 * 提示：
 * - 链表中节点的数目范围是 [0, 5000]
 * - -5000 <= Node.val <= 5000
 * 
 * 核心理解：
 * 
 * 1. 反转的本质：
 *    - 将每个节点的 next 指针指向前一个节点
 *    - 原来：1 -> 2 -> 3 -> null
 *    - 反转：null <- 1 <- 2 <- 3
 * 
 * 2. 迭代思路：
 *    - 需要三个指针：prev, curr, next
 *    - prev：前一个节点（初始为 null）
 *    - curr：当前节点（初始为 head）
 *    - next：下一个节点（用于保存 curr.next）
 * 
 * 3. 递归思路：
 *    - 递归到链表末尾
 *    - 回溯时反转指针
 * 
 * 解题思路：
 * 
 * 方法1：迭代（推荐⭐⭐⭐）
 * - 使用三个指针：prev, curr, next
 * - 遍历链表，逐个反转指针
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(1)
 * 
 * 方法2：递归
 * - 递归到链表末尾
 * - 回溯时反转指针
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)（递归栈）
 * 
 * 算法流程（迭代版）：
 * 
 * 1. 初始化指针：
 *    - prev = null（前一个节点）
 *    - curr = head（当前节点）
 * 
 * 2. 遍历链表：
 *    - while (curr != null)
 *      * next = curr.next（保存下一个节点）
 *      * curr.next = prev（反转指针）
 *      * prev = curr（移动 prev）
 *      * curr = next（移动 curr）
 * 
 * 3. 返回 prev（新的头节点）
 * 
 * 易错点：
 * 
 * 1. 忘记保存 next：
 *    - 必须先保存 curr.next
 *    - 否则反转后无法访问后续节点
 * 
 * 2. 返回值错误：
 *    - 应该返回 prev，不是 curr
 *    - 循环结束时 curr 为 null
 * 
 * 3. 空链表处理：
 *    - head 为 null 时直接返回 null
 * 
 * 可视化示例：
 * 
 * 原链表：1 -> 2 -> 3 -> null
 * 
 * 初始状态：
 * prev = null
 * curr = 1
 * 
 * 第1步：
 * next = 2（保存）
 * 1.next = null（反转）
 * prev = 1, curr = 2
 * 结果：null <- 1   2 -> 3 -> null
 * 
 * 第2步：
 * next = 3（保存）
 * 2.next = 1（反转）
 * prev = 2, curr = 3
 * 结果：null <- 1 <- 2   3 -> null
 * 
 * 第3步：
 * next = null（保存）
 * 3.next = 2（反转）
 * prev = 3, curr = null
 * 结果：null <- 1 <- 2 <- 3
 * 
 * 返回 prev = 3
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 92. 反转链表 II（反转部分链表）
 * - LeetCode 25. K 个一组翻转链表（分组反转）
 * - LeetCode 24. 两两交换链表中的节点（相邻节点交换）
 * - LeetCode 109. 有序链表转换为二叉搜索树（链表基础）
 */
public class ReverseLinkedList {
    
    /**
     * 链表节点定义（单向链表）
     */
    public static class ListNode {
        int val;
        ListNode next;
        
        ListNode() {}
        
        ListNode(int val) {
            this.val = val;
        }
        
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    
    /**
     * 主方法：反转链表
     * 
     * @param head 链表头节点
     * @return 反转后的链表头节点
     */
    public ListNode reverseList(ListNode head) {
        // ============================================
        ListNode prev=null;
        ListNode curr=head;
        while(curr!=null){
            ListNode nextnode=curr.next;//保存下一节点
            curr.next=prev;//反向连接
            prev=curr;//前序节点后移
            curr=nextnode;//当前节点后移
        }
        // ============================================
        return prev;//curr已经后移，所以返回前序
        // 提示：迭代（推荐）
        // 
        // ListNode prev = null;
        // ListNode curr = head;
        // 
        // while (curr != null) {
        //     ListNode next = curr.next;  // 保存下一个节点
        //     curr.next = prev;            // 反转指针
        //     prev = curr;                 // 移动 prev
        //     curr = next;                 // 移动 curr
        // }
        // 
        // return prev;  // prev 是新的头节点
        
        // 关键理解：
        // 1. 为什么需要三个指针？
        //    - prev：记录前一个节点
        //    - curr：当前要反转的节点
        //    - next：保存下一个节点，避免丢失
        // 
        // 2. 为什么返回 prev？
        //    - 循环结束时 curr 为 null
        //    - prev 指向原链表的最后一个节点
        //    - 也就是新链表的头节点
        // 
        // 3. 反转的顺序：
        //    - 先保存 next
        //    - 再反转 curr.next
        //    - 最后移动 prev 和 curr
        
        // 在这里编写你的实现代码
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // ============================================
        // TODO: 实现结束
        // ============================================

    }
    
    /**
     * 辅助方法：构建链表
     */
    private static ListNode buildList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        
        return dummy.next;
    }
    
    /**
     * 辅助方法：打印链表
     */
    private static void printList(ListNode head) {
        System.out.print("[");
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null) {
                System.out.print(",");
            }
            curr = curr.next;
        }
        System.out.println("]");
    }
    
    /**
     * 辅助方法：将链表转为数组（用于验证）
     */
    private static int[] listToArray(ListNode head) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        ReverseLinkedList solution = new ReverseLinkedList();
        
        System.out.println("=== LeetCode 206: 反转链表 ===");
        System.out.println("简单题，链表基础必会题\n");
        
        // 测试用例1：多个节点
        System.out.println("测试用例1: 多个节点");
        int[] values1 = {1, 2, 3, 4, 5};
        ListNode head1 = buildList(values1);
        System.out.print("输入: ");
        printList(head1);
        ListNode result1 = solution.reverseList(head1);
        System.out.print("输出: ");
        printList(result1);
        System.out.println("预期: [5,4,3,2,1]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2：两个节点
        System.out.println("测试用例2: 两个节点");
        int[] values2 = {1, 2};
        ListNode head2 = buildList(values2);
        System.out.print("输入: ");
        printList(head2);
        ListNode result2 = solution.reverseList(head2);
        System.out.print("输出: ");
        printList(result2);
        System.out.println("预期: [2,1]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：空链表
        System.out.println("测试用例3: 空链表");
        ListNode head3 = null;
        System.out.print("输入: ");
        printList(head3);
        ListNode result3 = solution.reverseList(head3);
        System.out.print("输出: ");
        printList(result3);
        System.out.println("预期: []");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：单节点
        System.out.println("测试用例4: 单节点");
        int[] values4 = {1};
        ListNode head4 = buildList(values4);
        System.out.print("输入: ");
        printList(head4);
        ListNode result4 = solution.reverseList(head4);
        System.out.print("输出: ");
        printList(result4);
        System.out.println("预期: [1]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 使用三个指针：prev, curr, next");
        System.out.println("2. 先保存 next，再反转 curr.next");
        System.out.println("3. 返回 prev（新的头节点）");
        System.out.println("4. 时间复杂度：O(n)");
        System.out.println("5. 空间复杂度：O(1)");
        System.out.println("\n反转链表是链表操作的基础！");
    }
}



