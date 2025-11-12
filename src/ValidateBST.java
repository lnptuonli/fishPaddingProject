/**
 * LeetCode 98. 验证二叉搜索树 - Validate Binary Search Tree
 * 
 * 题目描述：
 * 给你一个二叉树的根节点 root，判断其是否是一个有效的二叉搜索树。
 * 
 * 有效二叉搜索树定义如下：
 * - 节点的左子树只包含 严格小于 当前节点的数
 * - 节点的右子树只包含 严格大于 当前节点的数
 * - 所有左子树和右子树自身必须也是二叉搜索树
 * 
 * 示例 1：
 * 输入：root = [2,1,3]
 *       2
 *      / \
 *     1   3
 * 输出：true
 * 
 * 示例 2：
 * 输入：root = [5,1,4,null,null,3,6]
 *       5
 *      / \
 *     1   4
 *        / \
 *       3   6
 * 输出：false
 * 解释：根节点的值是 5，但是右子树节点值是 4，不满足 4 > 5
 * 
 * 示例 3：
 * 输入：root = [5,4,6,null,null,3,7]
 *       5
 *      / \
 *     4   6
 *        / \
 *       3   7
 * 输出：false
 * 解释：虽然 6 > 5，但是 6 的左子树节点 3 < 5，违反了 BST 的定义
 * 
 * 提示：
 * - 树中节点数目范围在 [1, 10^4] 内
 * - -2^31 <= Node.val <= 2^31 - 1
 * 
 * 核心理解：
 * 
 * 1. 常见的错误思路 ❌
 *    - 只检查每个节点是否满足 left.val < node.val < right.val
 *    - 这是不够的！因为 BST 的定义是：
 *      * 左子树的所有节点 < 当前节点
 *      * 右子树的所有节点 > 当前节点
 * 
 * 2. 为什么错误？
 *    例如：
 *         5
 *        / \
 *       1   6
 *          / \
 *         3   7
 *    
 *    - 如果只检查父子关系：5>1 ✓, 6>5 ✓, 6>3 ✓, 7>6 ✓
 *    - 但是 3 在 5 的右子树中，应该 3 > 5，实际 3 < 5
 *    - 所以这不是 BST！
 * 
 * 3. 正确的思路
 *    - 需要维护每个节点的取值范围 [min, max]
 *    - 左子树的所有节点：取值范围 [min, node.val)
 *    - 右子树的所有节点：取值范围 (node.val, max]
 *    - 递归验证
 * 
 * 4. 关键点
 *    - 使用 Long.MIN_VALUE 和 Long.MAX_VALUE 作为初始边界
 *    - 或者使用 null 表示无边界（更优雅）
 *    - 注意是 严格小于 和 严格大于，不能等于
 * 
 * 解题思路：
 * 
 * 思路1：递归 + 区间限制（推荐⭐⭐⭐）
 * - 定义递归函数 isValid(node, min, max)
 * - 检查 node.val 是否在 (min, max) 范围内
 * - 递归检查左子树：isValid(node.left, min, node.val)
 * - 递归检查右子树：isValid(node.right, node.val, max)
 * - 时间复杂度：O(n)，每个节点访问一次
 * - 空间复杂度：O(h)，递归栈深度为树高
 * 
 * 思路2：中序遍历
 * - BST 的中序遍历结果是严格递增的
 * - 进行中序遍历，记录上一个节点的值
 * - 检查当前节点值是否严格大于上一个节点值
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(h)
 * 
 * 思路3：迭代 + 栈
 * - 用栈模拟中序遍历
 * - 维护上一个访问的节点值
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(h)
 * 
 * 算法流程（递归版）：
 * 
 * 1. 主函数：isValidBST(root)
 *    - 调用递归函数：isValid(root, null, null)
 * 
 * 2. 递归函数：isValid(node, min, max)
 *    - 如果 node == null，返回 true（空树是 BST）
 *    - 如果 min != null && node.val <= min，返回 false
 *    - 如果 max != null && node.val >= max，返回 false
 *    - 递归检查左子树：isValid(node.left, min, node.val)
 *    - 递归检查右子树：isValid(node.right, node.val, max)
 *    - 返回 left && right
 * 
 * 易错点：
 * 
 * 1. 只检查父子关系 ❌
 *    - 错误：只检查 left < node < right
 *    - 正确：检查左子树的所有节点 < node < 右子树的所有节点
 * 
 * 2. 边界值处理 ⚠️
 *    - 节点值可能是 Integer.MIN_VALUE 或 Integer.MAX_VALUE
 *    - 使用 Long 或 Integer 作为边界时要小心
 *    - 推荐使用 null 表示无边界
 * 
 * 3. 等于的情况 ⚠️
 *    - BST 定义是 严格小于 和 严格大于
 *    - 不能有相等的节点值
 *    - 检查时要用 <= 和 >=
 * 
 * 4. 空树的处理
 *    - 空树被认为是有效的 BST
 * 
 * 为什么要传递 min 和 max？
 * 
 * 例子：
 *         10
 *        /  \
 *       5    15
 *           /  \
 *          6   20
 * 
 * - 检查节点 10：范围 (-∞, +∞)，10 在范围内 ✓
 * - 检查节点 5：范围 (-∞, 10)，5 在范围内 ✓
 * - 检查节点 15：范围 (10, +∞)，15 在范围内 ✓
 * - 检查节点 6：范围 (10, 15)，6 不在范围内 ❌
 * 
 * 如果不传递范围，只检查 15 > 6，就会误判为 true！
 * 
 * 中序遍历思路：
 * 
 * BST 的中序遍历（左-根-右）结果是严格递增的：
 * 
 * 例如：
 *       2
 *      / \
 *     1   3
 * 
 * 中序遍历：1 -> 2 -> 3（严格递增）
 * 
 * 如果不是 BST：
 *       5
 *      / \
 *     1   4
 *        / \
 *       3   6
 * 
 * 中序遍历：1 -> 5 -> 3 -> 4 -> 6（不是严格递增！）
 * 
 * 关键技巧：
 * 
 * 1. 递归参数设计：
 *    - 使用 Long 或 null 表示边界
 *    - null 表示无边界（更优雅）
 * 
 * 2. 边界更新：
 *    - 左子树：上界更新为 node.val
 *    - 右子树：下界更新为 node.val
 * 
 * 3. 中序遍历：
 *    - 维护前驱节点（prev）
 *    - 检查 prev < current
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 94. 二叉树的中序遍历（基础题）
 * - LeetCode 95/96. 不同的二叉搜索树（生成/计数）
 * - LeetCode 99. 恢复二叉搜索树（进阶题）
 * - LeetCode 108. 将有序数组转换为二叉搜索树
 * - LeetCode 230. 二叉搜索树中第K小的元素
 */
public class ValidateBST {
    
    /**
     * 树节点定义
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {}//enmpy node
        
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
     * 主方法：验证二叉搜索树
     * 
     * @param root 二叉树的根节点
     * @return 是否是有效的 BST
     */
    public boolean isValidBST(TreeNode root) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================

        // 提示1：递归 + 区间限制（推荐）

        // 
        // return isValid(root, null, null);
        // 
        // 辅助函数：
        // private boolean isValid(TreeNode node, Integer min, Integer max) {
        //     // 空树是有效的 BST
        //     if (node == null) {
        //         return true;
        //     }
        //     
        //     // 检查当前节点值是否在 (min, max) 范围内
        //     if (min != null && node.val <= min) {
        //         return false;
        //     }
        //     if (max != null && node.val >= max) {
        //         return false;
        //     }
        //     
        //     // 递归检查左右子树
        //     // 左子树：所有节点 < node.val，所以上界是 node.val
        //     // 右子树：所有节点 > node.val，所以下界是 node.val
        //     return isValid(node.left, min, node.val) && 
        //            isValid(node.right, node.val, max);
        // }
        
        // 提示2：中序遍历
        // 
        // private Integer prev = null;  // 记录前驱节点的值
        // 
        // public boolean isValidBST(TreeNode root) {
        //     if (root == null) {
        //         return true;
        //     }
        //     
        //     // 检查左子树
        //     if (!isValidBST(root.left)) {
        //         return false;
        //     }
        //     
        //     // 检查当前节点
        //     if (prev != null && root.val <= prev) {
        //         return false;
        //     }
        //     prev = root.val;  // 更新前驱
        //     
        //     // 检查右子树
        //     return isValidBST(root.right);
        // }
        
        // 关键理解：
        // 1. 为什么不能只检查父子关系？
        //    - 因为 BST 要求左子树的所有节点 < 根 < 右子树的所有节点
        //    - 不仅仅是直接的父子关系
        // 
        // 2. 为什么要传递 min 和 max？
        //    - 每个节点都有一个合法的取值范围
        //    - 左子树的范围：(min, node.val)
        //    - 右子树的范围：(node.val, max)
        // 
        // 3. 为什么使用 null 而不是 Integer.MIN/MAX？
        //    - 节点值可能就是 Integer.MIN_VALUE 或 Integer.MAX_VALUE
        //    - 使用 null 表示无边界更安全
        // 
        // 4. 为什么中序遍历可以验证？
        //    - BST 的中序遍历是严格递增的
        //    - 如果遍历过程中发现不递增，就不是 BST
        
        // 在这里编写你的实现代码

        // ============================================
        // TODO: 实现结束
        // ============================================
        return isValid(root, null, null);//根节点没有限制 → 所以传入 min = null, max = null
        // 记得返回结果
    }
    private boolean isValid(TreeNode node,Integer min,Integer max){
        if (node==null){
            return true;
        }
        if (min!=null&&node.val<=min){
            return false;
        }
        if (max!=null&&node.val>=max){
            return false;
        }
        return isValid(node.left,min,node.val)&&isValid(node.right,node.val,max);
    }
    /**
     * 辅助方法：构建二叉树（用于测试）
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
        ValidateBST solution = new ValidateBST();
        
        System.out.println("=== LeetCode 98: 验证二叉搜索树 ===");
        System.out.println("中等题，但有不少陷阱\n");
        
        // 测试用例1：有效的 BST
        System.out.println("测试用例1: 有效的 BST");
        TreeNode root1 = buildTree(new Integer[]{2, 1, 3});
        System.out.println("输入: [2,1,3]");
        printTree(root1, "", false);
        boolean result1 = solution.isValidBST(root1);
        System.out.println("输出: " + result1);
        System.out.println("预期: true");
        System.out.println("通过: " + (result1 == true));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2：无效的 BST（根节点的右子树包含小于根的节点）
        System.out.println("测试用例2: 无效的 BST（右子树有小于根的节点）");
        TreeNode root2 = buildTree(new Integer[]{5, 1, 4, null, null, 3, 6});
        System.out.println("输入: [5,1,4,null,null,3,6]");
        printTree(root2, "", false);
        boolean result2 = solution.isValidBST(root2);
        System.out.println("输出: " + result2);
        System.out.println("预期: false");
        System.out.println("解释: 节点 3 在根节点 5 的右子树中，但 3 < 5");
        System.out.println("通过: " + (result2 == false));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：无效的 BST（只检查父子关系会误判）
        System.out.println("测试用例3: 无效的 BST（经典陷阱）");
        TreeNode root3 = buildTree(new Integer[]{5, 4, 6, null, null, 3, 7});
        System.out.println("输入: [5,4,6,null,null,3,7]");
        printTree(root3, "", false);
        boolean result3 = solution.isValidBST(root3);
        System.out.println("输出: " + result3);
        System.out.println("预期: false");
        System.out.println("解释: 节点 3 在根节点 5 的右子树中，但 3 < 5");
        System.out.println("如果只检查父子关系，会误判为 true！");
        System.out.println("通过: " + (result3 == false));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：边界值测试
        System.out.println("测试用例4: 边界值测试");
        TreeNode root4 = buildTree(new Integer[]{Integer.MAX_VALUE});
        System.out.println("输入: [" + Integer.MAX_VALUE + "]");
        printTree(root4, "", false);
        boolean result4 = solution.isValidBST(root4);
        System.out.println("输出: " + result4);
        System.out.println("预期: true");
        System.out.println("通过: " + (result4 == true));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：相等值的情况
        System.out.println("测试用例5: 相等值的情况");
        TreeNode root5 = buildTree(new Integer[]{1, 1});
        System.out.println("输入: [1,1]");
        printTree(root5, "", false);
        boolean result5 = solution.isValidBST(root5);
        System.out.println("输出: " + result5);
        System.out.println("预期: false");
        System.out.println("解释: BST 不允许相等的值");
        System.out.println("通过: " + (result5 == false));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例6：较大的有效 BST
        System.out.println("测试用例6: 较大的有效 BST");
        TreeNode root6 = buildTree(new Integer[]{10, 5, 15, 3, 7, 13, 18});
        System.out.println("输入: [10,5,15,3,7,13,18]");
        printTree(root6, "", false);
        boolean result6 = solution.isValidBST(root6);
        System.out.println("输出: " + result6);
        System.out.println("预期: true");
        System.out.println("通过: " + (result6 == true));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例7：负数的 BST
        System.out.println("测试用例7: 负数的 BST");
        TreeNode root7 = buildTree(new Integer[]{0, -5, 5});
        System.out.println("输入: [0,-5,5]");
        printTree(root7, "", false);
        boolean result7 = solution.isValidBST(root7);
        System.out.println("输出: " + result7);
        System.out.println("预期: true");
        System.out.println("通过: " + (result7 == true));
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 不能只检查父子关系！");
        System.out.println("2. 要检查左子树的所有节点 < 根 < 右子树的所有节点");
        System.out.println("3. 方法1：递归传递 [min, max] 范围");
        System.out.println("4. 方法2：中序遍历，检查是否严格递增");
        System.out.println("5. 注意边界值和相等值的处理");
        System.out.println("\n易错点：");
        System.out.println("- ❌ 只检查 left < node < right");
        System.out.println("- ❌ 使用 Integer.MIN/MAX 作为边界");
        System.out.println("- ❌ 允许相等的值");
        System.out.println("- ✅ 传递范围，递归检查");
        System.out.println("- ✅ 使用 null 表示无边界");
        System.out.println("- ✅ 严格小于和严格大于");
    }
}

