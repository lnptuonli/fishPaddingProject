/**
 * LeetCode 109. 有序链表转换为二叉搜索树 - Convert Sorted List to Binary Search Tree
 * 
 * 题目描述：
 * 给定一个单链表的头节点 head，其中的元素按升序排序，将其转换为平衡二叉搜索树。
 * 
 * 示例 1：
 * 输入：head = [-10,-3,0,5,9]
 * 输出：[0,-3,9,-10,null,5]
 * 解释：一个可能的答案是 [0,-3,9,-10,null,5]，它表示所示的高度平衡的BST
 * 
 * 示例 2：
 * 输入：head = []
 * 输出：[]
 * 
 * 提示：
 * - 链表中的节点数在 [0, 2 * 10^4] 范围内
 * - -10^5 <= Node.val <= 10^5
 * 
 * 核心理解：
 * 
 * 1. 与数组版本的区别：
 *    - 数组可以 O(1) 访问中间元素
 *    - 链表需要 O(n) 遍历才能找到中间元素
 * 
 * 2. 解决方案：
 *    - 方法1：转换为数组（简单但需要额外空间）
 *    - 方法2：快慢指针找中点（推荐）
 *    - 方法3：中序遍历模拟（最优）
 * 
 * 3. 快慢指针技巧：
 *    - 快指针每次走2步，慢指针每次走1步
 *    - 快指针到达末尾时，慢指针在中点
 *    - 需要记录慢指针的前驱节点，用于断开链表
 * 
 * 解题思路：
 * 
 * 方法1：转换为数组（推荐⭐⭐⭐，简单直接）
 * - 遍历链表，将所有值存入数组
 * - 调用数组版本的构建方法
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)
 * 
 * 方法2：快慢指针（推荐⭐⭐⭐⭐，空间优化）
 * - 使用快慢指针找到链表中点
 * - 将链表从中点断开
 * - 递归构建左右子树
 * - 时间复杂度：O(n log n)
 * - 空间复杂度：O(log n)
 * 
 * 方法3：中序遍历模拟（最优⭐⭐⭐⭐⭐）
 * - 先计算链表长度
 * - 使用中序遍历的顺序构建BST
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(log n)
 * 
 * 算法流程（快慢指针版）：
 * 
 * 1. 主函数：sortedListToBST(head)
 *    - 调用递归函数：buildBST(head)
 * 
 * 2. 递归函数：buildBST(head)
 *    - 如果 head == null，返回 null
 *    - 如果 head.next == null，返回 new TreeNode(head.val)
 *    - 使用快慢指针找到中点和前驱
 *    - 断开链表：prev.next = null
 *    - 创建根节点：root = new TreeNode(mid.val)
 *    - 递归构建左子树：root.left = buildBST(head)
 *    - 递归构建右子树：root.right = buildBST(mid.next)
 *    - 返回 root
 * 
 * 易错点：
 * 
 * 1. 快慢指针的初始化：
 *    - slow = head, fast = head, prev = null
 *    - 循环条件：fast != null && fast.next != null
 * 
 * 2. 断开链表：
 *    - 需要记录慢指针的前驱节点
 *    - prev.next = null 断开左半部分
 * 
 * 3. 单节点的处理：
 *    - head.next == null 时直接返回
 *    - 避免无限递归
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 108. 将有序数组转换为二叉搜索树（数组版本）
 * - LeetCode 876. 链表的中间结点（快慢指针）
 * - LeetCode 206. 反转链表（链表基础）
 */
public class SortedListToBST {
    
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
     * 树节点定义
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {}
        
        TreeNode(int val) {
            this.val = val;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    /**
     * 主方法：将有序链表转换为平衡BST
     * 
     * @param head 链表头节点
     * @return BST的根节点
     */
    public TreeNode sortedListToBST(ListNode head) {
        // ============================================
        if(head==null)
            return null;
        if(head!=null && head.next==null)
        {
            TreeNode root =new TreeNode(head.val);
            return root;
        }

        ListNode slow=head;
        ListNode fast=head;
        ListNode slowprev=head;
        while(fast!=null && fast.next!=null){
            slowprev=slow;
            slow=slow.next;
            fast=fast.next.next;
        }

        if (slowprev!=null)
            slowprev.next=null;//如果中央链表的上一个有值，需要断开链表，防止子树创建时遍历到
        //创建根
        TreeNode root= new TreeNode(slow.val);
        //左节点
        root.left= sortedListToBST(head);
        //右节点
        root.right=sortedListToBST(slow.next);
        return root;
        // ============================================
        
        // 提示1：转换为数组（简单）
        // 
        // if (head == null) return null;
        // 
        // // 转换为数组
        // java.util.List<Integer> list = new java.util.ArrayList<>();
        // ListNode curr = head;
        // while (curr != null) {
        //     list.add(curr.val);
        //     curr = curr.next;
        // }
        // 
        // int[] nums = new int[list.size()];
        // for (int i = 0; i < list.size(); i++) {
        //     nums[i] = list.get(i);
        // }
        // 
        // // 调用数组版本的方法
        // return buildBST(nums, 0, nums.length - 1);
        
        // 提示2：快慢指针（推荐）
        // 
        // private TreeNode buildBST(ListNode head) {
        //     // 空链表
        //     if (head == null) {
        //         return null;
        //     }
        //     
        //     // 单节点
        //     if (head.next == null) {
        //         return new TreeNode(head.val);
        //     }
        //     
        //     // 快慢指针找中点
        //     ListNode slow = head;
        //     ListNode fast = head;
        //     ListNode prev = null;
        //     
        //     while (fast != null && fast.next != null) {
        //         prev = slow;
        //         slow = slow.next;
        //         fast = fast.next.next;
        //     }
        //     
        //     // 断开链表
        //     if (prev != null) {
        //         prev.next = null;
        //     }
        //     
        //     // 构建树
        //     TreeNode root = new TreeNode(slow.val);
        //     root.left = buildBST(head);
        //     root.right = buildBST(slow.next);
        //     
        //     return root;
        // }
        
        // 关键理解：
        // 1. 为什么链表比数组难？
        //    - 数组可以 O(1) 访问中间元素
        //    - 链表需要 O(n) 遍历
        // 
        // 2. 快慢指针如何找中点？
        //    - 快指针走2步，慢指针走1步
        //    - 快指针到末尾时，慢指针在中点
        // 
        // 3. 为什么要断开链表？
        //    - 左子树只需要左半部分
        //    - 右子树只需要右半部分
        //    - 断开避免重复处理
        
        // 在这里编写你的实现代码

        // ============================================
        // TODO: 实现结束
        // ============================================

    }
    public ListNode getmid(ListNode head){
        ListNode slow=head;
        ListNode fast=head;
        ListNode slowprev=head;
        while(fast!=null && fast.next!=null){
            slowprev=slow;
            slow=slow.next;
            fast=fast.next.next;
        }
        return slow;//注意如果有两个中间节点，会返回后一个
    }
    /**
     * 辅助方法：构建链表（用于测试）
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
     * 辅助方法：打印树的结构
     */
    private static void printTree(TreeNode root, String prefix, boolean isLeft) {
        if (root == null) {
            return;
        }
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.val);
        
        if (root.left != null || root.right != null) {
            if (root.left != null) {
                printTree(root.left, prefix + (isLeft ? "│   " : "    "), true);
            } else {
                System.out.println(prefix + (isLeft ? "│   " : "    ") + "├── null");
            }
            
            if (root.right != null) {
                printTree(root.right, prefix + (isLeft ? "│   " : "    "), false);
            } else {
                System.out.println(prefix + (isLeft ? "│   " : "    ") + "└── null");
            }
        }
    }
    
    /**
     * 辅助方法：计算树的高度
     */
    private static int getHeight(TreeNode root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }
    
    /**
     * 辅助方法：检查是否是平衡树
     */
    private static boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }
        
        return isBalanced(root.left) && isBalanced(root.right);
    }
    
    /**
     * 辅助方法：中序遍历（验证BST）
     */
    private static void inorderTraversal(TreeNode root, java.util.List<Integer> result) {
        if (root == null) return;
        inorderTraversal(root.left, result);
        result.add(root.val);
        inorderTraversal(root.right, result);
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        SortedListToBST solution = new SortedListToBST();
        
        System.out.println("=== LeetCode 109: 有序链表转换为二叉搜索树 ===");
        System.out.println("中等题，链表版本\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] values1 = {-10, -3, 0, 5, 9};
        ListNode head1 = buildList(values1);
        System.out.print("输入链表: ");
        printList(head1);
        TreeNode root1 = solution.sortedListToBST(head1);
        System.out.println("输出BST:");
        printTree(root1, "", false);
        System.out.println("是否平衡: " + isBalanced(root1));
        
        java.util.List<Integer> inorder1 = new java.util.ArrayList<>();
        inorderTraversal(root1, inorder1);
        System.out.println("中序遍历: " + inorder1);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] values2 = {};
        ListNode head2 = buildList(values2);
        System.out.print("输入链表: ");
        printList(head2);
        TreeNode root2 = solution.sortedListToBST(head2);
        System.out.println("输出BST:");
        printTree(root2, "", false);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        int[] values3 = {1, 2, 3, 4, 5, 6, 7};
        ListNode head3 = buildList(values3);
        System.out.print("输入链表: ");
        printList(head3);
        TreeNode root3 = solution.sortedListToBST(head3);
        System.out.println("输出BST:");
        printTree(root3, "", false);
        System.out.println("是否平衡: " + isBalanced(root3));
        System.out.println("树高: " + getHeight(root3));
        
        java.util.List<Integer> inorder3 = new java.util.ArrayList<>();
        inorderTraversal(root3, inorder3);
        System.out.println("中序遍历: " + inorder3);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 方法1：转换为数组（简单，O(n)空间）");
        System.out.println("2. 方法2：快慢指针找中点（推荐，O(log n)空间）");
        System.out.println("3. 快慢指针：快指针走2步，慢指针走1步");
        System.out.println("4. 需要记录前驱节点，用于断开链表");
        System.out.println("5. 时间复杂度：O(n log n)");
    }
}

