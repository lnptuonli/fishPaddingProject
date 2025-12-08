import java.util.*;

/**
 * LeetCode 236. 二叉树的最近公共祖先 - Lowest Common Ancestor of a Binary Tree
 * 
 * 题目描述：
 * 给定一个二叉树，找到该树中两个指定节点的最近公共祖先。
 * 
 * 百度百科中最近公共祖先的定义为："对于有根树 T 的两个节点 p、q，
 * 最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。"
 * 
 * 示例 1：
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 *             3
 *            / \
 *           5   1
 *          / \ / \
 *         6  2 0  8
 *           / \
 *          7   4
 * 输出：3
 * 解释：节点 5 和节点 1 的最近公共祖先是节点 3
 * 
 * 示例 2：
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * 输出：5
 * 解释：节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
 * 
 * 示例 3：
 * 输入：root = [1,2], p = 1, q = 2
 * 输出：1
 * 
 * 提示：
 * - 树中节点数目在范围 [2, 10^5] 内
 * - -10^9 <= Node.val <= 10^9
 * - 所有 Node.val 互不相同
 * - p != q
 * - p 和 q 均存在于给定的二叉树中
 * 
 * 核心理解：
 * 
 * 1. 最近公共祖先（LCA）的定义：
 *    - p 和 q 都在该节点的子树中
 *    - 该节点是满足条件的最深的节点
 *    - 一个节点可以是它自己的祖先
 * 
 * 2. LCA 的三种情况：
 *    - 情况1：p 和 q 分别在当前节点的左右子树中 → 当前节点就是LCA
 *    - 情况2：p 或 q 就是当前节点，另一个在其子树中 → 当前节点就是LCA
 *    - 情况3：p 和 q 都在左子树或都在右子树中 → LCA在子树中
 * 
 * 3. 递归思路：
 *    - 如果当前节点是 p 或 q，返回当前节点
 *    - 递归查找左右子树
 *    - 如果左右子树都找到了，当前节点就是LCA
 *    - 如果只有一侧找到了，返回那一侧的结果
 * 
 * 解题思路：
 * 
 * 方法1：递归（推荐⭐⭐⭐⭐⭐）
 * - 后序遍历（左右根）的思想
 * - 递归返回值的含义：当前子树中是否包含 p 或 q
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)（递归栈）
 * 
 * 算法流程：
 * 
 * 1. 递归函数：lowestCommonAncestor(root, p, q)
 *    - 递归出口1：root == null，返回 null
 *    - 递归出口2：root == p 或 root == q，返回 root
 *    - 递归查找左子树：left = lowestCommonAncestor(root.left, p, q)
 *    - 递归查找右子树：right = lowestCommonAncestor(root.right, p, q)
 *    - 判断：
 *      * 如果 left != null && right != null：
 *        说明 p 和 q 分别在左右子树，当前节点是LCA，返回 root
 *      * 如果 left != null && right == null：
 *        说明 p 和 q 都在左子树，返回 left
 *      * 如果 left == null && right != null：
 *        说明 p 和 q 都在右子树，返回 right
 *      * 如果 left == null && right == null：
 *        说明都没找到，返回 null
 * 
 * 2. 简化判断：
 *    - return left != null ? left : right
 *    - 或者：return left == null ? right : (right == null ? left : root)
 * 
 * 易错点：
 * 
 * 1. 递归返回值的理解（重要❗）：
 *    - 返回值不是"是否找到"，而是"找到的节点"
 *    - null 表示没找到
 *    - 非null 表示找到了 p、q 或它们的LCA
 * 
 * 2. 节点可以是自己的祖先：
 *    - 如果 p 是 q 的祖先，返回 p
 *    - 不需要特殊处理，递归逻辑已经覆盖
 * 
 * 3. 后序遍历的思想：
 *    - 先处理左右子树，再处理当前节点
 *    - 这样才能判断 p 和 q 的位置关系
 * 
 * 4. 左右子树都找到的情况：
 *    - 说明 p 和 q 分别在左右子树
 *    - 当前节点一定是LCA
 * 
 * 可视化示例：
 * 
 * 示例1：
 *             3
 *            / \
 *           5   1
 *          / \ / \
 *         6  2 0  8
 *           / \
 *          7   4
 * 
 * 查找 p=5, q=1 的LCA：
 * 
 * 1. lowestCommonAncestor(3, 5, 1)
 *    - 递归左子树：lowestCommonAncestor(5, 5, 1)
 *      * root == p，返回 5
 *    - 递归右子树：lowestCommonAncestor(1, 5, 1)
 *      * root == q，返回 1
 *    - left = 5, right = 1，都不为null
 *    - 返回 root = 3 ✓
 * 
 * 示例2：
 * 查找 p=5, q=4 的LCA：
 * 
 * 1. lowestCommonAncestor(3, 5, 4)
 *    - 递归左子树：lowestCommonAncestor(5, 5, 4)
 *      * root == p，返回 5
 *    - 递归右子树：lowestCommonAncestor(1, 5, 4)
 *      * 左右子树都没找到，返回 null
 *    - left = 5, right = null
 *    - 返回 left = 5 ✓
 * 
 * 关键技巧：
 * 
 * 1. 递归的优雅性：
 *    - 不需要显式记录路径
 *    - 不需要额外的数据结构
 *    - 一次遍历即可找到答案
 * 
 * 2. 返回值的多重含义：
 *    - null：没找到 p 或 q
 *    - p 或 q：找到了其中一个
 *    - LCA：找到了最近公共祖先
 * 
 * 3. 后序遍历的应用：
 *    - 先处理子树，再处理当前节点
 *    - 自底向上传递信息
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 235. 二叉搜索树的最近公共祖先（利用BST性质优化）
 * - LeetCode 1644. 二叉树的最近公共祖先 II（p或q可能不存在）
 * - LeetCode 1650. 二叉树的最近公共祖先 III（有父指针）
 * - LeetCode 1676. 二叉树的最近公共祖先 IV（多个节点）
 */
public class LowestCommonAncestor {
    
    /**
     * 树节点定义
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int x) {
            val = x;
        }
    }
    
    /**
     * 主方法：找到二叉树中两个节点的最近公共祖先
     * 
     * @param root 树的根节点
     * @param p 第一个节点
     * @param q 第二个节点
     * @return 最近公共祖先节点
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        if (root == null) {
            return null;
        }
        if (root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            // p 和 q 分别在左右子树，当前节点是LCA
            return root;
        }
        return left != null ? left : right;
        // 提示：递归（后序遍历思想）
        // 
        // // 递归出口1：空节点
        // if (root == null) {
        //     return null;
        // }
        // 
        // // 递归出口2：找到了 p 或 q
        // if (root == p || root == q) {
        //     return root;
        // }
        // 
        // // 递归查找左右子树
        // TreeNode left = lowestCommonAncestor(root.left, p, q);
        // TreeNode right = lowestCommonAncestor(root.right, p, q);
        // 
        // // 判断返回值
        // if (left != null && right != null) {
        //     // p 和 q 分别在左右子树，当前节点是LCA
        //     return root;
        // }
        // 
        // // 返回非空的那一侧（或者都为空返回null）
        // return left != null ? left : right;
        
        // 关键理解：
        // 1. 为什么找到 p 或 q 就返回？
        //    - 如果另一个节点在其子树中，当前节点就是LCA
        //    - 如果另一个节点不在其子树中，会在上层找到LCA
        // 
        // 2. 为什么 left != null && right != null 时返回 root？
        //    - 说明 p 和 q 分别在左右子树
        //    - 当前节点是它们的分叉点，即LCA
        // 
        // 3. 为什么返回非空的那一侧？
        //    - 说明 p 和 q 都在同一侧子树
        //    - 那一侧已经找到了LCA，向上传递
        // 
        // 4. 后序遍历的思想：
        //    - 先递归左右子树（后序的"左右"）
        //    - 再处理当前节点（后序的"根"）
        //    - 自底向上传递信息


    }
    
    /**
     * 辅助方法：构建二叉树（层序数组）
     */
    private static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
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
     * 辅助方法：在树中查找值为val的节点
     */
    private static TreeNode findNode(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        
        TreeNode left = findNode(root.left, val);
        if (left != null) {
            return left;
        }
        
        return findNode(root.right, val);
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
        LowestCommonAncestor solution = new LowestCommonAncestor();
        
        System.out.println("=== LeetCode 236: 二叉树的最近公共祖先 ===");
        System.out.println("中等题，面试超高频\n");
        
        // 测试用例1：p 和 q 在不同子树
        System.out.println("测试用例1: p 和 q 在不同子树");
        Integer[] values1 = {3, 5, 1, 6, 2, 0, 8, null, null, 7, 4};
        TreeNode root1 = buildTree(values1);
        System.out.println("输入树:");
        printTree(root1, "", false);
        TreeNode p1 = findNode(root1, 5);
        TreeNode q1 = findNode(root1, 1);
        System.out.println("p = " + p1.val + ", q = " + q1.val);
        TreeNode result1 = solution.lowestCommonAncestor(root1, p1, q1);
        System.out.println("输出: " + (result1 != null ? result1.val : "null"));
        System.out.println("预期: 3");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2：p 是 q 的祖先
        System.out.println("测试用例2: p 是 q 的祖先");
        TreeNode p2 = findNode(root1, 5);
        TreeNode q2 = findNode(root1, 4);
        System.out.println("p = " + p2.val + ", q = " + q2.val);
        TreeNode result2 = solution.lowestCommonAncestor(root1, p2, q2);
        System.out.println("输出: " + (result2 != null ? result2.val : "null"));
        System.out.println("预期: 5（节点可以是自己的祖先）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：两个节点的树
        System.out.println("测试用例3: 两个节点的树");
        Integer[] values3 = {1, 2};
        TreeNode root3 = buildTree(values3);
        System.out.println("输入树:");
        printTree(root3, "", false);
        TreeNode p3 = findNode(root3, 1);
        TreeNode q3 = findNode(root3, 2);
        System.out.println("p = " + p3.val + ", q = " + q3.val);
        TreeNode result3 = solution.lowestCommonAncestor(root3, p3, q3);
        System.out.println("输出: " + (result3 != null ? result3.val : "null"));
        System.out.println("预期: 1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：p 和 q 在同一子树的不同分支
        System.out.println("测试用例4: p 和 q 在同一子树的不同分支");
        TreeNode p4 = findNode(root1, 6);
        TreeNode q4 = findNode(root1, 4);
        System.out.println("p = " + p4.val + ", q = " + q4.val);
        TreeNode result4 = solution.lowestCommonAncestor(root1, p4, q4);
        System.out.println("输出: " + (result4 != null ? result4.val : "null"));
        System.out.println("预期: 5");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：p 和 q 是叶子节点
        System.out.println("测试用例5: p 和 q 是叶子节点");
        TreeNode p5 = findNode(root1, 7);
        TreeNode q5 = findNode(root1, 4);
        System.out.println("p = " + p5.val + ", q = " + q5.val);
        TreeNode result5 = solution.lowestCommonAncestor(root1, p5, q5);
        System.out.println("输出: " + (result5 != null ? result5.val : "null"));
        System.out.println("预期: 2");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 后序遍历思想：先处理左右子树，再处理当前节点");
        System.out.println("2. 找到 p 或 q 就返回当前节点");
        System.out.println("3. 左右子树都找到 → 当前节点是LCA");
        System.out.println("4. 只有一侧找到 → 返回那一侧的结果");
        System.out.println("5. 节点可以是自己的祖先");
        System.out.println("\n这是树的经典问题，面试必考！");
    }
}





