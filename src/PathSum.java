/**
 * LeetCode 112. 路径总和 - Path Sum
 * 
 * 题目描述：
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum。
 * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum。
 * 如果存在，返回 true；否则，返回 false。
 * 
 * 叶子节点 是指没有子节点的节点。
 * 
 * 示例 1：
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
 *             5
 *            / \
 *           4   8
 *          /   / \
 *         11  13  4
 *        /  \      \
 *       7    2      1
 * 输出：true
 * 解释：等于目标和的根节点到叶子节点路径如图所示（5 -> 4 -> 11 -> 2 = 22）
 * 
 * 示例 2：
 * 输入：root = [1,2,3], targetSum = 5
 *       1
 *      / \
 *     2   3
 * 输出：false
 * 解释：树中不存在根节点到叶子节点的路径，使得总和等于目标和 5
 * 
 * 示例 3：
 * 输入：root = [], targetSum = 0
 * 输出：false
 * 解释：由于树是空的，所以不存在根节点到叶子节点的路径
 * 
 * 提示：
 * - 树中节点的数目在范围 [0, 5000] 内
 * - -1000 <= Node.val <= 1000
 * - -1000 <= targetSum <= 1000
 * 
 * 核心理解：
 * 
 * 1. 路径的定义：
 *    - 必须从根节点开始
 *    - 必须到叶子节点结束
 *    - 不能在中间节点停止
 * 
 * 2. 叶子节点的定义：
 *    - left == null && right == null
 *    - 必须是没有子节点的节点
 * 
 * 3. 解题关键：
 *    - 递归时，targetSum 不断减去当前节点的值
 *    - 到达叶子节点时，检查剩余的 targetSum 是否等于叶子节点的值
 *    - 或者检查 targetSum - node.val 是否等于 0
 * 
 * 解题思路：
 * 
 * 方法1：DFS递归（推荐⭐⭐⭐⭐⭐）
 * - 递归遍历每条从根到叶子的路径
 * - 每次递归，targetSum 减去当前节点的值
 * - 到达叶子节点时，判断剩余的 targetSum 是否为 0
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)（递归栈）
 * 
 * 方法2：BFS（层序遍历）
 * - 使用队列，存储节点和对应的剩余路径和
 * - 遇到叶子节点时，检查剩余路径和是否为 0
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)
 * 
 * 算法流程（DFS递归版）：
 * 
 * 1. 主函数：hasPathSum(root, targetSum)
 *    - 如果 root == null，返回 false
 *    - 如果 root 是叶子节点：
 *      * 返回 targetSum == root.val
 *    - 否则，递归检查左右子树：
 *      * 左子树：hasPathSum(root.left, targetSum - root.val)
 *      * 右子树：hasPathSum(root.right, targetSum - root.val)
 *      * 返回 左 || 右（只要有一条路径满足即可）
 * 
 * 算法流程（BFS层序遍历版）：
 * 
 * 1. 初始化队列，加入根节点和初始路径和
 * 2. 层序遍历：
 *    - 取出当前节点和对应的剩余路径和
 *    - 如果是叶子节点且剩余路径和为 0，返回 true
 *    - 否则将左右子节点加入队列，更新剩余路径和
 * 3. 遍历完所有节点都没找到，返回 false
 * 
 * 易错点：
 * 
 * 1. 叶子节点的判断（重要❗）：
 *    - 必须是 left == null && right == null
 *    - 不能只判断一侧
 *    - 不能在非叶子节点处就返回 true
 * 
 * 2. 路径必须到叶子节点：
 *    - 错误：在中间节点发现 targetSum == 0 就返回 true
 *    - 正确：必须到达叶子节点才能判断
 * 
 * 3. 空树的处理：
 *    - root == null，返回 false（不是 true）
 *    - 空树不存在任何路径
 * 
 * 4. targetSum 为负数的情况：
 *    - 节点值可以是负数
 *    - 不能提前剪枝
 * 
 * 可视化示例：
 * 
 * 示例1：
 *             5
 *            / \
 *           4   8
 *          /   / \
 *         11  13  4
 *        /  \      \
 *       7    2      1
 * 
 * targetSum = 22
 * 
 * DFS过程：
 * - hasPathSum(5, 22)
 *   - 不是叶子节点，继续递归
 *   - hasPathSum(4, 17)
 *     - 不是叶子节点，继续递归
 *     - hasPathSum(11, 13)
 *       - 不是叶子节点，继续递归
 *       - hasPathSum(7, 2)：叶子节点，2 != 7，返回 false
 *       - hasPathSum(2, 2)：叶子节点，2 == 2，返回 true ✓
 *       - 返回 true
 *     - 返回 true
 *   - 返回 true
 * 
 * 示例2（易错）：
 *       1
 *      / \
 *     2   3
 * 
 * targetSum = 1
 * 
 * DFS过程：
 * - hasPathSum(1, 1)
 *   - 不是叶子节点（有子节点），不能返回 true
 *   - hasPathSum(2, 0)：叶子节点，0 != 2，返回 false
 *   - hasPathSum(3, 0)：叶子节点，0 != 3，返回 false
 *   - 返回 false ✓
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 113. 路径总和 II（返回所有路径）
 * - LeetCode 437. 路径总和 III（路径不必从根开始）
 * - LeetCode 257. 二叉树的所有路径（类似的路径遍历）
 * - LeetCode 111. 二叉树的最小深度（类似的叶子节点判断）
 */
public class PathSum {
    
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
     * 主方法：判断是否存在根到叶子的路径和等于 targetSum
     * 
     * @param root 树的根节点
     * @param targetSum 目标和
     * @return 是否存在满足条件的路径
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：DFS递归（推荐）
        // 
        // // 空树
        // if (root == null) {
        //     return false;
        // }
        // 
        // // 叶子节点：检查路径和
        // if (root.left == null && root.right == null) {
        //     return targetSum == root.val;
        // }
        // 
        // // 递归检查左右子树
        // boolean left = false;
        // boolean right = false;
        // 
        // if (root.left != null) {
        //     left = hasPathSum(root.left, targetSum - root.val);
        // }
        // 
        // if (root.right != null) {
        //     right = hasPathSum(root.right, targetSum - root.val);
        // }
        // 
        // return left || right;
        
        // 或者更简洁的写法：
        // if (root == null) {
        //     return false;
        // }
        // 
        // if (root.left == null && root.right == null) {
        //     return targetSum == root.val;
        // }
        // 
        // return hasPathSum(root.left, targetSum - root.val) || 
        //        hasPathSum(root.right, targetSum - root.val);
        
        // 关键理解：
        // 1. 为什么必须到叶子节点？
        //    - 题目要求路径必须到叶子节点
        //    - 不能在中间节点停止
        // 
        // 2. 为什么用 targetSum - root.val？
        //    - 每次递归，减去当前节点的值
        //    - 到叶子节点时，检查剩余值是否等于叶子节点的值
        // 
        // 3. 为什么用 || （或）？
        //    - 只要有一条路径满足条件即可
        //    - 不需要所有路径都满足
        // 
        // 4. 空树为什么返回 false？
        //    - 空树不存在任何路径
        //    - 即使 targetSum == 0 也返回 false
        
        // 在这里编写你的实现代码

        // ============================================
        // TODO: 实现结束
        // ============================================
        if(root==null)
        {
            return false;
        }
        return getPathSum(root, targetSum);  // 记得返回结果
    }

    public boolean getPathSum(TreeNode root, int targetSum){
        if(root==null)
        {
            return false;
        }
        if(root.left==null&&root.right==null){//如果是叶子结点
            return targetSum==root.val;
        }
        boolean left = false;
        boolean right = false;
        //如果有左叶子或者右叶子，路径-root值
        if(root.left!=null){
            left=getPathSum(root.left,targetSum-root.val);
        }
        if(root.right!=null){
            right=getPathSum(root.right,targetSum-root.val);
        }
        return left||right;
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
        PathSum solution = new PathSum();
        
        System.out.println("=== LeetCode 112: 路径总和 ===");
        System.out.println("简单题，注意必须到叶子节点\n");
        
        // 测试用例1：存在满足条件的路径
        System.out.println("测试用例1: 存在满足条件的路径");
        Integer[] values1 = {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1};
        TreeNode root1 = buildTree(values1);
        int targetSum1 = 22;
        System.out.println("输入树:");
        printTree(root1, "", false);
        System.out.println("targetSum = " + targetSum1);
        boolean result1 = solution.hasPathSum(root1, targetSum1);
        System.out.println("输出: " + result1);
        System.out.println("预期: true（路径: 5 -> 4 -> 11 -> 2 = 22）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2：不存在满足条件的路径
        System.out.println("测试用例2: 不存在满足条件的路径");
        Integer[] values2 = {1, 2, 3};
        TreeNode root2 = buildTree(values2);
        int targetSum2 = 5;
        System.out.println("输入树:");
        printTree(root2, "", false);
        System.out.println("targetSum = " + targetSum2);
        boolean result2 = solution.hasPathSum(root2, targetSum2);
        System.out.println("输出: " + result2);
        System.out.println("预期: false");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：空树
        System.out.println("测试用例3: 空树");
        TreeNode root3 = null;
        int targetSum3 = 0;
        System.out.println("输入树: []");
        System.out.println("targetSum = " + targetSum3);
        boolean result3 = solution.hasPathSum(root3, targetSum3);
        System.out.println("输出: " + result3);
        System.out.println("预期: false（空树不存在路径）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：易错-根节点等于targetSum但不是叶子节点
        System.out.println("测试用例4: 易错-根节点等于targetSum但不是叶子节点");
        Integer[] values4 = {1, 2};
        TreeNode root4 = buildTree(values4);
        int targetSum4 = 1;
        System.out.println("输入树:");
        printTree(root4, "", false);
        System.out.println("targetSum = " + targetSum4);
        boolean result4 = solution.hasPathSum(root4, targetSum4);
        System.out.println("输出: " + result4);
        System.out.println("预期: false（必须到叶子节点）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：只有一个节点
        System.out.println("测试用例5: 只有一个节点");
        Integer[] values5 = {5};
        TreeNode root5 = buildTree(values5);
        int targetSum5 = 5;
        System.out.println("输入树:");
        printTree(root5, "", false);
        System.out.println("targetSum = " + targetSum5);
        boolean result5 = solution.hasPathSum(root5, targetSum5);
        System.out.println("输出: " + result5);
        System.out.println("预期: true（单节点就是叶子节点）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例6：负数路径
        System.out.println("测试用例6: 负数路径");
        Integer[] values6 = {1, -2, -3};
        TreeNode root6 = buildTree(values6);
        int targetSum6 = -1;
        System.out.println("输入树:");
        printTree(root6, "", false);
        System.out.println("targetSum = " + targetSum6);
        boolean result6 = solution.hasPathSum(root6, targetSum6);
        System.out.println("输出: " + result6);
        System.out.println("预期: true（路径: 1 -> -2 = -1）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 路径必须到叶子节点（left == null && right == null）");
        System.out.println("2. 每次递归，targetSum 减去当前节点的值");
        System.out.println("3. 到叶子节点时，检查 targetSum == node.val");
        System.out.println("4. 空树返回 false，即使 targetSum == 0");
        System.out.println("5. 时间复杂度：O(n)");
        System.out.println("\n注意叶子节点的判断，这是本题的关键！");
    }
}





