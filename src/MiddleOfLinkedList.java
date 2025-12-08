/**
 * LeetCode 876. 链表的中间结点 - Middle of the Linked List
 * 
 * 题目描述：
 * 给你单链表的头结点 head，请你找出并返回链表的中间结点。
 * 如果有两个中间结点，则返回第二个中间结点。
 * 
 * 示例 1：
 * 输入：head = [1,2,3,4,5]
 * 输出：[3,4,5]
 * 解释：链表只有一个中间结点，值为 3
 * 
 * 示例 2：
 * 输入：head = [1,2,3,4,5,6]
 * 输出：[4,5,6]
 * 解释：有两个中间结点，值分别为 3 和 4，返回第二个结点
 * 
 * 提示：
 * - 链表的结点数范围是 [1, 100]
 * - 1 <= Node.val <= 100
 * 
 * 核心理解：
 * 
 * 1. 快慢指针技巧：
 *    - 慢指针每次走1步
 *    - 快指针每次走2步
 *    - 当快指针到达末尾时，慢指针正好在中间
 * 
 * 2. 为什么有效？
 *    - 快指针速度是慢指针的2倍
 *    - 快指针走完全程时，慢指针走了一半
 * 
 * 3. 两个中间结点的处理：
 *    - 偶数个节点时，有两个中间结点
 *    - 题目要求返回第二个
 *    - 循环条件：fast != null && fast.next != null
 * 
 * 解题思路：
 * 
 * 快慢指针（推荐⭐⭐⭐）
 * - 初始化：slow = head, fast = head
 * - 循环：当 fast != null && fast.next != null
 *   * slow = slow.next（走1步）
 *   * fast = fast.next.next（走2步）
 * - 返回 slow
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(1)
 * 
 * 算法流程：
 * 
 * 1. 初始化快慢指针：
 *    - slow = head
 *    - fast = head
 * 
 * 2. 移动指针：
 *    - while (fast != null && fast.next != null)
 *      * slow = slow.next
 *      * fast = fast.next.next
 * 
 * 3. 返回 slow
 * 
 * 易错点：
 * 
 * 1. 循环条件：
 *    - 必须检查 fast != null 和 fast.next != null
 *    - 避免空指针异常
 * 
 * 2. 奇偶数节点的处理：
 *    - 奇数个节点：fast 最终指向最后一个节点
 *    - 偶数个节点：fast 最终为 null
 * 
 * 可视化示例：
 * 
 * 奇数个节点 [1,2,3,4,5]：
 * 初始: slow=1, fast=1
 * 步骤1: slow=2, fast=3
 * 步骤2: slow=3, fast=5
 * 步骤3: fast.next=null，循环结束
 * 返回: slow=3 ✓
 * 
 * 偶数个节点 [1,2,3,4,5,6]：
 * 初始: slow=1, fast=1
 * 步骤1: slow=2, fast=3
 * 步骤2: slow=3, fast=5
 * 步骤3: slow=4, fast=null，循环结束
 * 返回: slow=4 ✓（第二个中间结点）
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 109. 有序链表转换为二叉搜索树（应用快慢指针）
 * - LeetCode 141. 环形链表（快慢指针判环）
 * - LeetCode 142. 环形链表 II（快慢指针找环入口）
 * - LeetCode 19. 删除链表的倒数第N个结点（双指针）
 */
public class MiddleOfLinkedList {
    
    /**
     * 链表节点定义
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
     * 主方法：找到链表的中间结点
     * 
     * @param head 链表头节点
     * @return 中间结点
     */
    public ListNode middleNode(ListNode head) {
        // ============================================
        //快慢节点，慢走一步，快走两步
        ListNode slow=head;
        ListNode fast=head;
        while(fast != null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        return slow;
        // ============================================
        
        // 提示：快慢指针
        // 
        // ListNode slow = head;
        // ListNode fast = head;
        // 
        // while (fast != null && fast.next != null) {
        //     slow = slow.next;       // 慢指针走1步
        //     fast = fast.next.next;  // 快指针走2步
        // }
        // 
        // return slow;
        
        // 关键理解：
        // 1. 为什么快指针走2步？
        //    - 快指针速度是慢指针的2倍
        //    - 快指针走完时，慢指针走了一半
        // 
        // 2. 循环条件为什么是 fast != null && fast.next != null？
        //    - fast != null：防止空链表
        //    - fast.next != null：防止 fast.next.next 空指针
        // 
        // 3. 为什么返回第二个中间结点？
        //    - 循环条件保证了偶数个节点时，slow 指向第二个中间结点
        
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
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null) {
                System.out.print(" -> ");
            }
            curr = curr.next;
        }
        System.out.println();
    }
    
    /**
     * 辅助方法：从某个节点开始打印链表
     */
    private static void printListFrom(ListNode node) {
        System.out.print("[");
        while (node != null) {
            System.out.print(node.val);
            if (node.next != null) {
                System.out.print(",");
            }
            node = node.next;
        }
        System.out.println("]");
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        MiddleOfLinkedList solution = new MiddleOfLinkedList();
        
        System.out.println("=== LeetCode 876: 链表的中间结点 ===");
        System.out.println("简单题，快慢指针经典应用\n");
        
        // 测试用例1：奇数个节点
        System.out.println("测试用例1: 奇数个节点");
        int[] values1 = {1, 2, 3, 4, 5};
        ListNode head1 = buildList(values1);
        System.out.print("输入链表: ");
        printList(head1);
        ListNode result1 = solution.middleNode(head1);
        System.out.print("中间结点开始: ");
        printListFrom(result1);
        System.out.println("预期: [3,4,5]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2：偶数个节点
        System.out.println("测试用例2: 偶数个节点");
        int[] values2 = {1, 2, 3, 4, 5, 6};
        ListNode head2 = buildList(values2);
        System.out.print("输入链表: ");
        printList(head2);
        ListNode result2 = solution.middleNode(head2);
        System.out.print("中间结点开始: ");
        printListFrom(result2);
        System.out.println("预期: [4,5,6]（第二个中间结点）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：单节点
        System.out.println("测试用例3: 单节点");
        int[] values3 = {1};
        ListNode head3 = buildList(values3);
        System.out.print("输入链表: ");
        printList(head3);
        ListNode result3 = solution.middleNode(head3);
        System.out.print("中间结点开始: ");
        printListFrom(result3);
        System.out.println("预期: [1]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：两个节点
        System.out.println("测试用例4: 两个节点");
        int[] values4 = {1, 2};
        ListNode head4 = buildList(values4);
        System.out.print("输入链表: ");
        printList(head4);
        ListNode result4 = solution.middleNode(head4);
        System.out.print("中间结点开始: ");
        printListFrom(result4);
        System.out.println("预期: [2]（第二个中间结点）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 快慢指针：快指针走2步，慢指针走1步");
        System.out.println("2. 循环条件：fast != null && fast.next != null");
        System.out.println("3. 偶数个节点时，返回第二个中间结点");
        System.out.println("4. 时间复杂度：O(n)");
        System.out.println("5. 空间复杂度：O(1)");
        System.out.println("\n快慢指针是链表问题的经典技巧！");
    }
}





