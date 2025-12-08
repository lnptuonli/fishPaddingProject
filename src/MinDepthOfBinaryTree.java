/**
 * LeetCode 111. 二叉树的最小深度 - Minimum Depth of Binary Tree
 * 
 * 题目描述：
 * 给定一个二叉树，找出其最小深度。
 * 
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 
 * 说明：叶子节点是指没有子节点的节点。
 * 
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 输出：2
 * 解释：最短路径是 3 -> 9，深度为 2
 * 
 * 示例 2：
 * 输入：root = [2,null,3,null,4,null,5,null,6]
 *     2
 *      \
 *       3
 *        \
 *         4
 *          \
 *           5
 *            \
 *             6
 * 输出：5
 * 
 * 提示：
 * - 树中节点数的范围在 [0, 10^5] 内
 * - -1000 <= Node.val <= 1000
 * 
 * 核心理解：
 * 
 * 1. 深度 vs 高度：
 *    - 深度：从根节点到当前节点的路径长度
 *    - 高度：从当前节点到叶子节点的路径长度
 *    - 本题求的是"最小深度"，但思路类似"最小高度"
 * 
 * 2. 叶子节点的定义：
 *    - 没有左子节点，也没有右子节点
 *    - left == null && right == null
 * 
 * 3. 易错点：
 *    - 如果一个节点只有左子树或只有右子树
 *    - 不能简单地取 min(左深度, 右深度)
 *    - 必须沿着存在的子树继续搜索
 * 
 * 解题思路：
 * 
 * 方法1：DFS（深度优先搜索，递归）
 * - 递归计算左右子树的最小深度
 * - 特殊处理：只有一侧子树存在的情况
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)（递归栈）
 * 
 * 方法2：BFS（广度优先搜索，推荐⭐⭐⭐⭐⭐）
 * - 层序遍历，遇到第一个叶子节点就返回
 * - 适合找"最小"深度（更高效）
 * - 时间复杂度：O(n)（但平均情况更快）
 * - 空间复杂度：O(n)
 * 
 * 算法流程（DFS递归版）：
 * 
 * 1. 主函数：minDepth(root)
 *    - 如果 root == null，返回 0
 *    - 如果 root 是叶子节点，返回 1
 *    - 如果只有左子树，返回 minDepth(root.left) + 1
 *    - 如果只有右子树，返回 minDepth(root.right) + 1
 *    - 如果左右子树都存在，返回 min(minDepth(left), minDepth(right)) + 1
 * 
 * 算法流程（BFS层序遍历版）：
 * 
 * 1. 初始化队列，加入根节点，深度为 1
 * 2. 层序遍历：
 *    - 遍历当前层的所有节点
 *    - 如果遇到叶子节点，立即返回当前深度
 *    - 否则将子节点加入队列
 *    - 深度 +1，继续下一层
 * 
 * 易错点：
 * 
 * 1. 只有一侧子树的情况（重要❗）：
 *    - 错误：return min(左深度, 右深度) + 1
 *    - 正确：如果左子树为空，返回右深度 + 1
 *    - 正确：如果右子树为空，返回左深度 + 1
 * 
 * 2. 叶子节点的判断：
 *    - 必须是 left == null && right == null
 *    - 不能只判断一侧
 * 
 * 3. 空树的处理：
 *    - root == null，返回 0
 * 
 * 可视化示例：
 * 
 * 示例1：
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 
 * DFS过程：
 * - minDepth(9)：叶子节点，返回 1
 * - minDepth(15)：叶子节点，返回 1
 * - minDepth(7)：叶子节点，返回 1
 * - minDepth(20)：左右子树深度都是1，返回 min(1,1)+1 = 2
 * - minDepth(3)：左子树深度1，右子树深度2，返回 min(1,2)+1 = 2
 * 
 * 示例2（易错）：
 *     2
 *      \
 *       3
 *        \
 *         4
 * 
 * DFS过程：
 * - minDepth(4)：叶子节点，返回 1
 * - minDepth(3)：只有右子树，返回 minDepth(4)+1 = 2
 * - minDepth(2)：只有右子树，返回 minDepth(3)+1 = 3
 * 
 * 如果错误地使用 min：
 * - minDepth(3)：左子树深度0，右子树深度1，错误返回 min(0,1)+1 = 1 ✗
 * - 正确应该返回 2 ✓
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 104. 二叉树的最大深度（对比题）
 * - LeetCode 110. 平衡二叉树（高度计算）
 * - LeetCode 102. 二叉树的层序遍历（BFS基础）
 * - LeetCode 112. 路径总和（类似的叶子节点判断）
 */
public class MinDepthOfBinaryTree {
    
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
     * 主方法：计算二叉树的最小深度
     * 
     * @param root 树的根节点
     * @return 最小深度
     */
    public int minDepth(TreeNode root) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：DFS递归（注意处理只有一侧子树的情况）
        // 
        // // 空树
        // if (root == null) {
        //     return 0;
        // }
        // 
        // // 叶子节点
        // if (root.left == null && root.right == null) {
        //     return 1;
        // }
        // 
        // // 只有右子树
        // if (root.left == null) {
        //     return minDepth(root.right) + 1;
        // }
        // 
        // // 只有左子树
        // if (root.right == null) {
        //     return minDepth(root.left) + 1;
        // }
        // 
        // // 左右子树都存在
        // return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
        
        // 关键理解：
        // 1. 为什么不能直接用 min(左深度, 右深度)？
        //    - 如果一侧子树为空，深度为0
        //    - 但这不是到叶子节点的路径
        //    - 必须沿着存在的子树继续搜索
        // 
        // 2. 什么是叶子节点？
        //    - left == null && right == null
        //    - 必须两侧都为空
        // 
        // 3. 为什么 BFS 更适合求最小值？
        //    - BFS 层序遍历，遇到第一个叶子节点就是答案
        //    - DFS 需要遍历所有路径才能找到最小值
        //    - 平均情况下 BFS 更快
        
        // 在这里编写你的实现代码

        
        return getDepth(root);  // 记得返回结果
    }
    public int getDepth(TreeNode root){
        if(root==null){
            return 0;
        }
        int leftDepth=getDepth(root.left);
        int rightDepth=getDepth(root.right);
        //左侧没有节点，不取左侧深度
        if(leftDepth==0)
            return rightDepth+1;
        //右侧没有节点，同理
        if(rightDepth==0)
            return leftDepth+1;
        //否则说明两侧都有节点，返回二者较小的
        return Math.min(leftDepth,rightDepth)+1;
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
     * 测试方法
     */
    public static void main(String[] args) {
        MinDepthOfBinaryTree solution = new MinDepthOfBinaryTree();
        
        System.out.println("=== LeetCode 111: 二叉树的最小深度 ===");
        System.out.println("简单题，注意只有一侧子树的情况\n");
        
        // 测试用例1：平衡树
        System.out.println("测试用例1: 平衡树");
        Integer[] values1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = buildTree(values1);
        System.out.println("输入树:");
        printTree(root1, "", false);
        int result1 = solution.minDepth(root1);
        System.out.println("输出: " + result1);
        System.out.println("预期: 2（路径: 3 -> 9）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2：只有右子树（易错）
        System.out.println("测试用例2: 只有右子树（易错）");
        Integer[] values2 = {2, null, 3, null, 4, null, 5, null, 6};
        TreeNode root2 = buildTree(values2);
        System.out.println("输入树:");
        printTree(root2, "", false);
        int result2 = solution.minDepth(root2);
        System.out.println("输出: " + result2);
        System.out.println("预期: 5（必须到叶子节点）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：空树
        System.out.println("测试用例3: 空树");
        TreeNode root3 = null;
        System.out.println("输入树: []");
        int result3 = solution.minDepth(root3);
        System.out.println("输出: " + result3);
        System.out.println("预期: 0");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：只有一个节点
        System.out.println("测试用例4: 只有一个节点");
        Integer[] values4 = {1};
        TreeNode root4 = buildTree(values4);
        System.out.println("输入树:");
        printTree(root4, "", false);
        int result4 = solution.minDepth(root4);
        System.out.println("输出: " + result4);
        System.out.println("预期: 1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：只有左子树
        System.out.println("测试用例5: 只有左子树");
        Integer[] values5 = {1, 2, null, 3, null, 4};
        TreeNode root5 = buildTree(values5);
        System.out.println("输入树:");
        printTree(root5, "", false);
        int result5 = solution.minDepth(root5);
        System.out.println("输出: " + result5);
        System.out.println("预期: 4");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 叶子节点：left == null && right == null");
        System.out.println("2. 只有一侧子树时，不能用 min(0, depth)");
        System.out.println("3. 必须沿着存在的子树继续搜索");
        System.out.println("4. BFS 更适合求最小深度（遇到第一个叶子节点即返回）");
        System.out.println("5. 时间复杂度：O(n)");
        System.out.println("\n注意区分最大深度和最小深度的处理差异！");
    }
}

