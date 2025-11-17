/**
 * LeetCode 110. 平衡二叉树 - Balanced Binary Tree
 * 
 * 题目描述：
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * 
 * 本题中，一棵高度平衡二叉树定义为：
 * 一个二叉树每个节点的左右两个子树的高度差的绝对值不超过 1。
 * 
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 输出：true
 * 
 * 示例 2：
 * 输入：root = [1,2,2,3,3,null,null,4,4]
 *          1
 *         / \
 *        2   2
 *       / \
 *      3   3
 *     / \
 *    4   4
 * 输出：false
 * 
 * 示例 3：
 * 输入：root = []
 * 输出：true
 * 
 * 提示：
 * - 树中的节点数在范围 [0, 5000] 内
 * - -10^4 <= Node.val <= 10^4
 * 
 * 核心理解：
 * 
 * 1. 平衡二叉树的定义：
 *    - 每个节点的左右子树高度差 ≤ 1
 *    - 左右子树也都是平衡二叉树（递归定义）
 * 
 * 2. 高度的定义：
 *    - 空树的高度为 0
 *    - 叶子节点的高度为 1
 *    - 非叶子节点的高度 = max(左子树高度, 右子树高度) + 1
 * 
 * 3. 解题关键：
 *    - 需要判断每个节点的左右子树高度差
 *    - 同时需要递归判断左右子树是否平衡
 *    - 可以在计算高度的同时判断平衡性
 * 
 * 解题思路：
 * 
 * 方法1：自顶向下（暴力，不推荐）
 * - 对每个节点，分别计算左右子树高度
 * - 判断高度差是否 ≤ 1
 * - 递归判断左右子树是否平衡
 * - 时间复杂度：O(n^2)（每个节点都要重新计算高度）
 * - 空间复杂度：O(n)
 * 
 * 方法2：自底向上（推荐⭐⭐⭐⭐⭐）
 * - 从叶子节点开始，向上计算高度
 * - 如果发现不平衡，立即返回 -1
 * - 否则返回正常的高度值
 * - 时间复杂度：O(n)（每个节点只访问一次）
 * - 空间复杂度：O(n)（递归栈）
 * 
 * 算法流程（自底向上）：
 * 
 * 1. 主函数：isBalanced(root)
 *    - 调用辅助函数：return getHeight(root) != -1
 * 
 * 2. 辅助函数：getHeight(node)
 *    - 如果 node == null，返回 0（空树高度为0）
 *    - 递归计算左子树高度：leftHeight = getHeight(node.left)
 *    - 如果 leftHeight == -1，说明左子树不平衡，直接返回 -1
 *    - 递归计算右子树高度：rightHeight = getHeight(node.right)
 *    - 如果 rightHeight == -1，说明右子树不平衡，直接返回 -1
 *    - 如果 |leftHeight - rightHeight| > 1，说明当前节点不平衡，返回 -1
 *    - 否则返回当前节点的高度：max(leftHeight, rightHeight) + 1
 * 
 * 易错点：
 * 
 * 1. 高度的定义：
 *    - 空树高度为 0，不是 -1
 *    - 叶子节点高度为 1
 * 
 * 2. 平衡性的递归判断：
 *    - 不仅要判断当前节点的高度差
 *    - 还要保证左右子树都是平衡的
 * 
 * 3. 返回值的含义：
 *    - -1 表示不平衡
 *    - 非负数表示高度
 * 
 * 可视化示例：
 * 
 * 平衡树：
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 
 * 计算过程（自底向上）：
 * - getHeight(9)：左右子树为null，高度为1
 * - getHeight(15)：高度为1
 * - getHeight(7)：高度为1
 * - getHeight(20)：左右子树高度都是1，高度差0，高度为2
 * - getHeight(3)：左子树高度1，右子树高度2，高度差1，高度为3
 * 返回 true ✓
 * 
 * 不平衡树：
 *          1
 *         / \
 *        2   2
 *       / \
 *      3   3
 *     / \
 *    4   4
 * 
 * 计算过程：
 * - getHeight(4)：高度为1
 * - getHeight(3)：左右子树高度都是1，高度为2
 * - getHeight(2)：左子树高度2，右子树高度2，高度为3
 * - getHeight(另一个2)：空树，高度为1
 * - getHeight(1)：左子树高度3，右子树高度1，高度差2 > 1
 * 返回 false ✗
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 104. 二叉树的最大深度（计算高度）
 * - LeetCode 111. 二叉树的最小深度（类似思路）
 * - LeetCode 543. 二叉树的直径（类似的自底向上思路）
 * - LeetCode 108/109. 有序数组/链表转平衡BST（构建平衡树）
 */
public class BalancedBinaryTree {
    
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
     * 主方法：判断是否是平衡二叉树
     * 
     * @param root 树的根节点
     * @return 是否平衡
     */
    public boolean isBalanced(TreeNode root) {
        // ============================================

        // ============================================
        
        // 提示：自底向上（推荐）
        // 
        // return getHeight(root) != -1;
        
        // 辅助函数：
        // private int getHeight(TreeNode node) {
        //     // 空树高度为 0
        //     if (node == null) {
        //         return 0;
        //     }
        //     
        //     // 递归计算左子树高度
        //     int leftHeight = getHeight(node.left);
        //     if (leftHeight == -1) {
        //         return -1;  // 左子树不平衡
        //     }
        //     
        //     // 递归计算右子树高度
        //     int rightHeight = getHeight(node.right);
        //     if (rightHeight == -1) {
        //         return -1;  // 右子树不平衡
        //     }
        //     
        //     // 判断当前节点是否平衡
        //     if (Math.abs(leftHeight - rightHeight) > 1) {
        //         return -1;  // 当前节点不平衡
        //     }
        //     
        //     // 返回当前节点的高度
        //     return Math.max(leftHeight, rightHeight) + 1;
        // }
        
        // 关键理解：
        // 1. 为什么用 -1 表示不平衡？
        //    - 高度是非负数，-1 可以明确表示异常状态
        //    - 一旦发现不平衡，立即向上传递 -1
        // 
        // 2. 为什么自底向上更优？
        //    - 每个节点只访问一次
        //    - 避免重复计算高度
        //    - 时间复杂度从 O(n^2) 降到 O(n)
        // 
        // 3. 递归的三要素：
        //    - 递归函数：计算高度 or 返回-1
        //    - 递归出口：node == null，返回 0
        //    - 递归逻辑：计算左右高度，判断高度差
        
        // 在这里编写你的实现代码
        return getDepth(root)>=0 ? true :false;

    }
    public int getDepth(TreeNode root){
        int leftDepth;
        int rightDepth;
        if (root==null){
            return 0;
        }
        //计算左右子树的高度
        leftDepth= getDepth(root.left);
        if(leftDepth==-1)//左子树不平衡，直接传递异常，不要继续计算（第一次没写出来）
            return -1;
        rightDepth=getDepth(root.right);
        if(rightDepth==-1)//右子树同理
            return -1;
        //如果发现不平衡，马上返回异常，这样即使上层getDepth获取到，也至少能在上层依旧显示不平衡
        if(Math.abs(leftDepth-rightDepth)>1)
            return -1;
        //计算高度时，别忘了给这一层+1
        return (Math.max(leftDepth,rightDepth))+1;
    }
    
    /**
     * 辅助方法：构建二叉树（层序数组）
     */
    private static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            // 左子节点
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;
            
            // 右子节点
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }
        
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
    private static int getTreeHeight(TreeNode root) {
        if (root == null) return 0;
        return Math.max(getTreeHeight(root.left), getTreeHeight(root.right)) + 1;
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        BalancedBinaryTree solution = new BalancedBinaryTree();
        
        System.out.println("=== LeetCode 110: 平衡二叉树 ===");
        System.out.println("简单题，自底向上经典应用\n");
        
        // 测试用例1：平衡树
        System.out.println("测试用例1: 平衡树");
        Integer[] values1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = buildTree(values1);
        System.out.println("输入树:");
        printTree(root1, "", false);
        boolean result1 = solution.isBalanced(root1);
        System.out.println("输出: " + result1);
        System.out.println("预期: true");
        System.out.println("树高: " + getTreeHeight(root1));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2：不平衡树
        System.out.println("测试用例2: 不平衡树");
        Integer[] values2 = {1, 2, 2, 3, 3, null, null, 4, 4};
        TreeNode root2 = buildTree(values2);
        System.out.println("输入树:");
        printTree(root2, "", false);
        boolean result2 = solution.isBalanced(root2);
        System.out.println("输出: " + result2);
        System.out.println("预期: false");
        System.out.println("树高: " + getTreeHeight(root2));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：空树
        System.out.println("测试用例3: 空树");
        TreeNode root3 = null;
        System.out.println("输入树: []");
        boolean result3 = solution.isBalanced(root3);
        System.out.println("输出: " + result3);
        System.out.println("预期: true");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：只有左子树的不平衡树
        System.out.println("测试用例4: 只有左子树");
        Integer[] values4 = {1, 2, null, 3, null, 4};
        TreeNode root4 = buildTree(values4);
        System.out.println("输入树:");
        printTree(root4, "", false);
        boolean result4 = solution.isBalanced(root4);
        System.out.println("输出: " + result4);
        System.out.println("预期: false");
        System.out.println("树高: " + getTreeHeight(root4));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：完全二叉树
        System.out.println("测试用例5: 完全二叉树");
        Integer[] values5 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root5 = buildTree(values5);
        System.out.println("输入树:");
        printTree(root5, "", false);
        boolean result5 = solution.isBalanced(root5);
        System.out.println("输出: " + result5);
        System.out.println("预期: true");
        System.out.println("树高: " + getTreeHeight(root5));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 自底向上计算高度");
        System.out.println("2. 用 -1 表示不平衡");
        System.out.println("3. 高度差 > 1 时立即返回 -1");
        System.out.println("4. 时间复杂度：O(n)");
        System.out.println("5. 空间复杂度：O(n)");
        System.out.println("\n自底向上是优化递归的经典技巧！");
    }
}



