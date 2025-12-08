/**
 * LeetCode 108. 将有序数组转换为二叉搜索树 - Convert Sorted Array to Binary Search Tree
 * 
 * 题目描述：
 * 给你一个整数数组 nums，其中元素已经按升序排列，请你将其转换为一棵平衡二叉搜索树。
 * 
 * 平衡二叉树：每个节点的左右两个子树的高度差的绝对值不超过 1。
 * 
 * 示例 1：
 * 输入：nums = [-10,-3,0,5,9]
 * 输出：[0,-3,9,-10,null,5]
 * 解释：[0,-10,5,null,-3,null,9] 也是正确答案
 *       0              0
 *      / \            / \
 *    -3   9        -10   5
 *    /   /           \    \
 *  -10  5            -3    9
 * 
 * 示例 2：
 * 输入：nums = [1,3]
 * 输出：[3,1] 或 [1,null,3]
 * 
 * 提示：
 * - 1 <= nums.length <= 10^4
 * - -10^4 <= nums[i] <= 10^4
 * - nums 按严格递增顺序排列
 * 
 * 核心理解：
 * 
 * 1. 为什么选择中间元素作为根？
 *    - 有序数组的中间元素可以保证左右子树节点数量相近
 *    - 这样构建出的树是平衡的
 * 
 * 2. 递归思想：
 *    - 选择中间元素作为根节点
 *    - 左半部分构建左子树
 *    - 右半部分构建右子树
 * 
 * 3. 关键点：
 *    - 数组已经有序，中序遍历就是数组本身
 *    - 选择中点保证平衡性
 *    - 递归出口：left > right 返回 null
 * 
 * 解题思路：
 * 
 * 递归构建（推荐⭐⭐⭐）
 * - 选择区间 [left, right] 的中点 mid
 * - 创建根节点 root = new TreeNode(nums[mid])
 * - 递归构建左子树：buildBST(nums, left, mid-1)
 * - 递归构建右子树：buildBST(nums, mid+1, right)
 * - 时间复杂度：O(n)，每个元素访问一次
 * - 空间复杂度：O(log n)，递归栈深度
 * 
 * 算法流程：
 * 
 * 1. 主函数：sortedArrayToBST(nums)
 *    - 调用递归函数：buildBST(nums, 0, nums.length - 1)
 * 
 * 2. 递归函数：buildBST(nums, left, right)
 *    - 如果 left > right，返回 null（空树）
 *    - 计算中点：mid = left + (right - left) / 2
 *    - 创建根节点：root = new TreeNode(nums[mid])
 *    - 递归构建左子树：root.left = buildBST(nums, left, mid - 1)
 *    - 递归构建右子树：root.right = buildBST(nums, mid + 1, right)
 *    - 返回 root
 * 
 * 易错点：
 * 
 * 1. 中点计算：
 *    - 使用 mid = left + (right - left) / 2 避免溢出
 *    - 不要用 (left + right) / 2，可能溢出
 * 
 * 2. 递归边界：
 *    - left > right 时返回 null
 *    - 注意是 > 不是 >=
 * 
 * 3. 左右子树的范围：
 *    - 左子树：[left, mid-1]
 *    - 右子树：[mid+1, right]
 *    - mid 已经作为根节点，不要重复使用
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 109. 有序链表转换为二叉搜索树（链表版本）
 * - LeetCode 98. 验证二叉搜索树（验证）
 * - LeetCode 95/96. 不同的二叉搜索树（生成/计数）
 */
public class SortedArrayToBST {
    
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
     * 主方法：将有序数组转换为平衡BST
     * 
     * @param nums 有序数组
     * @return BST的根节点
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：递归构建
        // 
        // return buildBST(nums, 0, nums.length - 1);
        // 
        // 辅助函数：
        // private TreeNode buildBST(int[] nums, int left, int right) {
        //     // 递归出口
        //     if (left > right) {
        //         return null;
        //     }
        //     
        //     // 选择中间元素作为根
        //     int mid = left + (right - left) / 2;
        //     TreeNode root = new TreeNode(nums[mid]);
        //     
        //     // 递归构建左右子树
        //     root.left = buildBST(nums, left, mid - 1);
        //     root.right = buildBST(nums, mid + 1, right);
        //     
        //     return root;
        // }
        
        // 关键理解：
        // 1. 为什么选择中间元素？
        //    - 保证左右子树节点数量相近
        //    - 构建出的树是平衡的
        // 
        // 2. 为什么是 O(n) 时间复杂度？
        //    - 每个元素只访问一次
        //    - 递归调用次数 = 节点数 = n
        
        // 在这里编写你的实现代码
        // 初始化根为数组中央


        
        // ============================================
        // TODO: 实现结束
        // ============================================
        
        return buildBSTwithArray(nums,0,nums.length-1);  // 记得返回结果
    }
    private static TreeNode buildBSTwithArray(int[] nums,int left,int right){
        if (left>right)//应当可以相等，相等的话就说明只有一片叶子
            return null;
        //建根、建左子树、建右子树
        int mid=left+(right - left)/2;
        TreeNode root=new TreeNode(nums[mid]);
        //建左子树时，右边界是能确定的，但左边界只能沿用传入的左边界，不好说用此次循环的哪个变量推算
        root.left=buildBSTwithArray(nums,left,mid-1);
        //建右子树时，左边界同理
        root.right=buildBSTwithArray(nums,mid+1,right);
        return root;
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
        SortedArrayToBST solution = new SortedArrayToBST();
        
        System.out.println("=== LeetCode 108: 将有序数组转换为二叉搜索树 ===");
        System.out.println("简单题，递归构建\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] nums1 = {-10, -3, 0, 5, 9};
        System.out.println("输入: [-10,-3,0,5,9]");
        TreeNode root1 = solution.sortedArrayToBST(nums1);
        printTree(root1, "", false);
        System.out.println("是否平衡: " + isBalanced(root1));
        
        java.util.List<Integer> inorder1 = new java.util.ArrayList<>();
        inorderTraversal(root1, inorder1);
        System.out.println("中序遍历: " + inorder1);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] nums2 = {1, 3};
        System.out.println("输入: [1,3]");
        TreeNode root2 = solution.sortedArrayToBST(nums2);
        printTree(root2, "", false);
        System.out.println("是否平衡: " + isBalanced(root2));
        
        java.util.List<Integer> inorder2 = new java.util.ArrayList<>();
        inorderTraversal(root2, inorder2);
        System.out.println("中序遍历: " + inorder2);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        int[] nums3 = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("输入: [1,2,3,4,5,6,7]");
        TreeNode root3 = solution.sortedArrayToBST(nums3);
        printTree(root3, "", false);
        System.out.println("是否平衡: " + isBalanced(root3));
        System.out.println("树高: " + getHeight(root3));
        
        java.util.List<Integer> inorder3 = new java.util.ArrayList<>();
        inorderTraversal(root3, inorder3);
        System.out.println("中序遍历: " + inorder3);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 选择中间元素作为根节点");
        System.out.println("2. 递归构建左右子树");
        System.out.println("3. 保证构建出的树是平衡的");
        System.out.println("4. 时间复杂度：O(n)");
        System.out.println("5. 空间复杂度：O(log n)");
    }
}





