import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 95. 不同的二叉搜索树 II - Unique Binary Search Trees II
 * <p>
 * 题目描述：
 * 给你一个整数 n，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同二叉搜索树。
 * 可以按任意顺序返回答案。
 * <p>
 * 示例 1：
 * 输入：n = 3
 * 输出：[[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
 * 解释：以上输出对应以下 5 种不同结构的二叉搜索树：
 * 1         1           2           3       3
 * \         \         / \         /       /
 * 2         3       1   3       1       2
 * \       /                     \     /
 * 3     2                       2   1
 * <p>
 * 示例 2：
 * 输入：n = 1
 * 输出：[[1]]
 * <p>
 * 提示：
 * 1 <= n <= 8
 * <p>
 * 核心理解：
 * <p>
 * 1. 什么是二叉搜索树（BST）？
 * - 左子树所有节点值 < 根节点值
 * - 右子树所有节点值 > 根节点值
 * - 左右子树也都是BST
 * <p>
 * 2. 这道题的本质：
 * - 不是判断是否是BST
 * - 而是生成所有可能的BST
 * - 这是一个组合问题
 * <p>
 * 3. 关键观察：
 * - 对于区间 [start, end]，可以选择任意一个数 i 作为根节点
 * - 左子树：由 [start, i-1] 的所有BST组成
 * - 右子树：由 [i+1, end] 的所有BST组成
 * - 组合：左子树的每一种 × 右子树的每一种
 * <p>
 * 4. 递归思想：
 * - 问题：生成 [start, end] 的所有BST
 * - 子问题：生成 [start, i-1] 和 [i+1, end] 的所有BST
 * - 递归出口：start > end 时，返回 null（空树）
 * <p>
 * 5. 卡特兰数：
 * - n 个节点的BST数量 = 第 n 个卡特兰数
 * - C(0) = 1
 * - C(n) = C(0)*C(n-1) + C(1)*C(n-2) + ... + C(n-1)*C(0)
 * - 例如：n=3 时，共有 5 种BST
 * <p>
 * 解题思路：
 * <p>
 * 思路1：递归生成（推荐⭐⭐⭐）
 * - 定义递归函数 generateTrees(start, end)
 * - 遍历 i 从 start 到 end，作为根节点
 * - 递归生成左子树：generateTrees(start, i-1)
 * - 递归生成右子树：generateTrees(i+1, end)
 * - 组合所有可能：每个左子树 × 每个右子树
 * - 时间复杂度：O(n * G(n))，G(n)是第n个卡特兰数
 * - 空间复杂度：O(n * G(n))
 * <p>
 * 思路2：动态规划 + 记忆化（优化）
 * - 使用 Map 缓存已计算的结果
 * - 避免重复计算相同的子问题
 * - 时间复杂度：优化后接近 O(n * G(n))
 * - 空间复杂度：O(n * G(n))
 * <p>
 * 算法流程（递归版）：
 * <p>
 * 1. 主函数：generateTrees(n)
 * - 如果 n == 0，返回空列表
 * - 调用 generateTrees(1, n)
 * <p>
 * 2. 递归函数：generateTrees(start, end)
 * - 如果 start > end，返回包含 null 的列表（表示空树）
 * - 创建结果列表 allTrees
 * - 遍历 i 从 start 到 end：
 * * 递归生成左子树列表：leftTrees = generateTrees(start, i-1)
 * * 递归生成右子树列表：rightTrees = generateTrees(i+1, end)
 * * 组合所有可能：
 * - 遍历 leftTrees 中的每个左子树
 * - 遍历 rightTrees 中的每个右子树
 * - 创建新节点 root，值为 i
 * - root.left = 左子树
 * - root.right = 右子树
 * - 将 root 加入 allTrees
 * - 返回 allTrees
 * <p>
 * 易错点：
 * <p>
 * 1. 空树的处理：
 * - start > end 时，要返回包含 null 的列表
 * - 不能返回空列表，否则组合时会出错
 * <p>
 * 2. 节点的创建：
 * - 每次组合都要创建新节点
 * - 不能复用节点，否则会导致树结构错误
 * <p>
 * 3. 递归边界：
 * - start == end 时，只有一个节点
 * - start > end 时，是空树
 * <p>
 * 4. 组合逻辑：
 * - 左子树和右子树的笛卡尔积
 * - 每个左子树都要和每个右子树组合
 * <p>
 * 为什么是卡特兰数？
 * <p>
 * 以 n = 3 为例：
 * <p>
 * 选择 1 作为根：
 * - 左子树：[] (0个节点，1种)
 * - 右子树：[2,3] (2个节点，2种)
 * - 组合：1 × 2 = 2 种
 * <p>
 * 选择 2 作为根：
 * - 左子树：[1] (1个节点，1种)
 * - 右子树：[3] (1个节点，1种)
 * - 组合：1 × 1 = 1 种
 * <p>
 * 选择 3 作为根：
 * - 左子树：[1,2] (2个节点，2种)
 * - 右子树：[] (0个节点，1种)
 * - 组合：2 × 1 = 2 种
 * <p>
 * 总共：2 + 1 + 2 = 5 种
 * <p>
 * 这正是卡特兰数的递推公式：
 * C(3) = C(0)*C(2) + C(1)*C(1) + C(2)*C(0)
 * = 1*2 + 1*1 + 2*1
 * = 5
 * <p>
 * 可视化示例（n = 3）：
 * <p>
 * 根节点 = 1:
 * 1              1
 * \              \
 * 2              3
 * \            /
 * 3          2
 * <p>
 * 根节点 = 2:
 * 2
 * / \
 * 1   3
 * <p>
 * 根节点 = 3:
 * 3            3
 * /            /
 * 1            2
 * \          /
 * 2        1
 * <p>
 * 关键技巧：
 * <p>
 * 1. 递归三要素：
 * - 递归函数定义：生成 [start, end] 的所有BST
 * - 递归出口：start > end 返回 [null]
 * - 递归逻辑：枚举根节点，组合左右子树
 * <p>
 * 2. 组合技巧：
 * - 使用双重循环遍历左右子树
 * - 每次都创建新的根节点
 * - 将左右子树连接到根节点
 * <p>
 * 3. 优化技巧：
 * - 可以使用记忆化避免重复计算
 * - 但对于 n <= 8，直接递归也足够快
 * <p>
 * 与相关题目的联系：
 * <p>
 * - LeetCode 96. 不同的二叉搜索树（只求数量，不生成树）
 * - LeetCode 894. 所有可能的满二叉树（类似的生成问题）
 * - LeetCode 241. 为运算表达式设计优先级（类似的递归分治）
 */
public class UniqueBST_II {

    /**
     * 树节点定义
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

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
     * 主方法：生成所有不同的BST
     *
     * @param n 节点数量
     * @return 所有可能的BST的根节点列表
     */
    public List<TreeNode> generateTrees(int n) {
        // ============================================
        //处理一下边界
        if (n == 0) {
            return new ArrayList<>();
        }

        // ============================================


        return getTrees(1,n);  // 记得返回结果
    }

    private List<TreeNode> getTrees(int start, int end) {
        List<TreeNode> treeList = new ArrayList<>();
        //每个节点都可能成为根，遍历从开始到结束的所有根
        // 递归出口：空树
        if (start > end) {
            treeList.add(null);
            return treeList;
        }
        for (int i = start; i <= end; i++) {
            // 以i为根，递归生成所有左子树
            List<TreeNode> leftTrees = getTrees(start, i - 1);
            // 以i为根，递归生成所有右子树
            List<TreeNode> rightTrees = getTrees(i + 1, end);
            //对于每颗树，递归到叶子结点后，给叶子结点加左右空结点；当递归返回上层时，这个子树只有一个节点，加上两个空叶子
            for (TreeNode left : leftTrees) {
                //leftTrees的大小：左子树有多少种情况，就有多大，空树当然也算一种。
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    treeList.add(root);
                }
            }
        }

        return treeList;
    }

    //插曲：问题94：中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result= new ArrayList<>();
        if (root==null)
            return result;
        inorderTraversal(root.left);
        result.add(root.val);
        inorderTraversal(root.right);
        return result;
    }
    //插曲：问题96：不同的二叉搜索树(仅返回数量，直接使用卡特兰数)
    private int treeNum;
    public int numTrees(int n) {
        if(n==0){
            return 0;
        }
        return getNumBSTTrees(n);
    }
    public int getNumBSTTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1; // 空树也是一种结构

        for (int nodes = 1; nodes <= n; nodes++) {
            for (int root = 1; root <= nodes; root++) {
                dp[nodes] += dp[root - 1] * dp[nodes - root];
            }
        }

        return dp[n];
    }

    // ============================================

    // 提示：递归生成
    //
    // 边界条件：
    // if (n == 0) {
    //     return new ArrayList<>();
    // }
    //
    // 调用递归函数：
    // return generateTrees(1, n);

    // 递归函数的实现：
    // private List<TreeNode> generateTrees(int start, int end) {
    //     List<TreeNode> allTrees = new ArrayList<>();
    //
    //     // 递归出口：空树
    //     if (start > end) {
    //         allTrees.add(null);
    //         return allTrees;
    //     }
    //
    //     // 枚举根节点
    //     for (int i = start; i <= end; i++) {
    //         // 生成所有左子树
    //         List<TreeNode> leftTrees = generateTrees(start, i - 1);
    //
    //         // 生成所有右子树
    //         List<TreeNode> rightTrees = generateTrees(i + 1, end);
    //
    //         // 组合左右子树
    //         for (TreeNode left : leftTrees) {
    //             for (TreeNode right : rightTrees) {
    //                 // 创建根节点
    //                 TreeNode root = new TreeNode(i);
    //                 root.left = left;
    //                 root.right = right;
    //                 allTrees.add(root);
    //             }
    //         }
    //     }
    //
    //     return allTrees;
    // }

    // 关键理解：
    // 1. 为什么 start > end 要返回 [null]？
    //    - 表示空树
    //    - 如果返回空列表，组合时会出错
    //
    // 2. 为什么每次都要创建新节点？
    //    - 不同的树需要不同的节点对象
    //    - 如果复用节点，会导致树结构混乱
    //
    // 3. 为什么是双重循环？
    //    - 左子树的每一种 × 右子树的每一种
    //    - 这是笛卡尔积的思想

    // 在这里编写你的实现代码


    /**
     * 辅助方法：打印树的结构（层序遍历）
     */
    private static String treeToString(TreeNode root) {
        if (root == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.remove(0);

            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(",");
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        // 移除末尾的 null
        String result = sb.toString();
        while (result.endsWith(",null")) {
            result = result.substring(0, result.lastIndexOf(",null"));
        }

        return result + "]";
    }

    /**
     * 辅助方法：打印树的结构（更直观的方式）
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
     * 辅助方法：验证是否是BST
     */
    private static boolean isBST(TreeNode root, Integer min, Integer max) {
        if (root == null) {
            return true;
        }

        if ((min != null && root.val <= min) || (max != null && root.val >= max)) {
            return false;
        }

        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        UniqueBST_II solution = new UniqueBST_II();

        System.out.println("=== LeetCode 95: 不同的二叉搜索树 II ===");
        System.out.println("中等题，经典的递归+组合问题\n");

        // 测试用例1：n = 1
        System.out.println("测试用例1:");
        System.out.println("输入: n = 1");
        List<TreeNode> result1 = solution.generateTrees(1);
        System.out.println("输出: " + result1.size() + " 种BST");
        System.out.println("预期: 1 种");
        for (int i = 0; i < result1.size(); i++) {
            System.out.println("\nBST " + (i + 1) + ":");
            printTree(result1.get(i), "", false);
            System.out.println("是否为BST: " + isBST(result1.get(i), null, null));
        }
        System.out.println("通过: " + (result1.size() == 1));
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例2：n = 2
        System.out.println("测试用例2:");
        System.out.println("输入: n = 2");
        List<TreeNode> result2 = solution.generateTrees(2);
        System.out.println("输出: " + result2.size() + " 种BST");
        System.out.println("预期: 2 种");
        for (int i = 0; i < result2.size(); i++) {
            System.out.println("\nBST " + (i + 1) + ":");
            printTree(result2.get(i), "", false);
            System.out.println("是否为BST: " + isBST(result2.get(i), null, null));
        }
        System.out.println("通过: " + (result2.size() == 2));
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例3：n = 3
        System.out.println("测试用例3:");
        System.out.println("输入: n = 3");
        List<TreeNode> result3 = solution.generateTrees(3);
        System.out.println("输出: " + result3.size() + " 种BST");
        System.out.println("预期: 5 种");
        for (int i = 0; i < result3.size(); i++) {
            System.out.println("\nBST " + (i + 1) + ":");
            printTree(result3.get(i), "", false);
            System.out.println("是否为BST: " + isBST(result3.get(i), null, null));
        }
        System.out.println("通过: " + (result3.size() == 5));
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例4：n = 4
        System.out.println("测试用例4:");
        System.out.println("输入: n = 4");
        int numTrees= solution.numTrees(4);
        List<TreeNode> result4 = solution.generateTrees(4);
        System.out.println("输出: " + result4.size() + " 种BST");
        System.out.println("输出: " + numTrees + " 种BST，仅统计数量");
        System.out.println("预期: 14 种（第4个卡特兰数）");
        System.out.println("通过: " + (result4.size() == 14));

        // 验证所有树都是BST
        boolean allBST = true;
        for (TreeNode tree : result4) {
            if (!isBST(tree, null, null)) {
                allBST = false;
                break;
            }
        }
        System.out.println("所有树都是BST: " + allBST);
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例5：n = 5
        System.out.println("测试用例5:");
        System.out.println("输入: n = 5");
        List<TreeNode> result5 = solution.generateTrees(5);
        System.out.println("输出: " + result5.size() + " 种BST");
        System.out.println("预期: 42 种（第5个卡特兰数）");
        System.out.println("通过: " + (result5.size() == 42));
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 卡特兰数对照表
        System.out.println("=== 卡特兰数对照表 ===");
        System.out.println("n | BST数量 | 卡特兰数");
        System.out.println("--|---------|----------");
        System.out.println("0 |    1    |    1");
        System.out.println("1 |    1    |    1");
        System.out.println("2 |    2    |    2");
        System.out.println("3 |    5    |    5");
        System.out.println("4 |   14    |   14");
        System.out.println("5 |   42    |   42");
        System.out.println("6 |  132    |  132");
        System.out.println("7 |  429    |  429");
        System.out.println("8 | 1430    | 1430");
        System.out.println();

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 这是经典的递归+组合问题");
        System.out.println("2. 关键：枚举根节点，递归生成左右子树");
        System.out.println("3. 组合：左子树的每一种 × 右子树的每一种");
        System.out.println("4. 递归出口：start > end 返回 [null]");
        System.out.println("5. BST数量 = 卡特兰数");
        System.out.println("\n提示：");
        System.out.println("- 理解递归的本质：大问题分解为小问题");
        System.out.println("- 理解组合的思想：笛卡尔积");
        System.out.println("- 注意每次都要创建新节点");
        System.out.println("- 这题是树的递归的经典题目！");
    }
}



