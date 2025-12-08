import java.util.*;

/**
 * LeetCode 105. 从前序与中序遍历序列构造二叉树 - Construct Binary Tree from Preorder and Inorder Traversal
 * 
 * 题目描述：
 * 给定两个整数数组 preorder 和 inorder，其中 preorder 是二叉树的前序遍历，inorder 是同一棵树的中序遍历，
 * 请构造二叉树并返回其根节点。
 * 
 * 示例 1：
 * 输入：preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * 输出：[3,9,20,null,null,15,7]
 * 解释：构造的二叉树为：
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 
 * 示例 2：
 * 输入：preorder = [-1], inorder = [-1]
 * 输出：[-1]
 * 
 * 提示：
 * - 1 <= preorder.length <= 3000
 * - inorder.length == preorder.length
 * - -3000 <= preorder[i], inorder[i] <= 3000
 * - preorder 和 inorder 均无重复元素
 * - inorder 均出现在 preorder
 * - preorder 保证为二叉树的前序遍历序列
 * - inorder 保证为二叉树的中序遍历序列
 * 
 * 核心理解：
 * 
 * 1. 前序遍历的特点：
 *    - 顺序：根节点 -> 左子树 -> 右子树
 *    - 第一个元素一定是根节点
 *    - preorder = [根, 左子树的前序, 右子树的前序]
 * 
 * 2. 中序遍历的特点：
 *    - 顺序：左子树 -> 根节点 -> 右子树
 *    - 根节点将数组分为左右两部分
 *    - inorder = [左子树的中序, 根, 右子树的中序]
 * 
 * 3. 构建思路：
 *    - 从前序遍历中取第一个元素作为根节点
 *    - 在中序遍历中找到根节点的位置
 *    - 根节点左侧是左子树，右侧是右子树
 *    - 递归构建左右子树
 * 
 * 解题思路：
 * 
 * 方法1：递归 + HashMap（推荐⭐⭐⭐⭐⭐）
 * - 使用HashMap存储中序遍历中每个值的索引，O(1)查找
 * - 递归构建：每次从前序遍历中取根节点，在中序遍历中定位
 * - 根据中序遍历中的位置，确定左右子树的范围
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)
 * 
 * 算法流程：
 * 
 * 1. 预处理：
 *    - 将中序遍历的值和索引存入HashMap
 *    - map.put(inorder[i], i)
 * 
 * 2. 递归函数：buildTree(preStart, preEnd, inStart, inEnd)
 *    - 递归出口：preStart > preEnd，返回 null
 *    - 从前序遍历中取根节点：rootVal = preorder[preStart]
 *    - 在中序遍历中找到根节点位置：rootIndex = map.get(rootVal)
 *    - 计算左子树的节点数：leftSize = rootIndex - inStart
 *    - 创建根节点：root = new TreeNode(rootVal)
 *    - 递归构建左子树：
 *      * 前序范围：[preStart+1, preStart+leftSize]
 *      * 中序范围：[inStart, rootIndex-1]
 *    - 递归构建右子树：
 *      * 前序范围：[preStart+leftSize+1, preEnd]
 *      * 中序范围：[rootIndex+1, inEnd]
 *    - 返回 root
 * 
 * 易错点：
 * 
 * 1. 左子树的大小计算（重要❗）：
 *    - leftSize = rootIndex - inStart
 *    - 不是 rootIndex，因为 inStart 可能不是0
 * 
 * 2. 前序遍历的范围划分：
 *    - 左子树前序：[preStart+1, preStart+leftSize]
 *    - 右子树前序：[preStart+leftSize+1, preEnd]
 *    - 注意 +1 的位置
 * 
 * 3. 中序遍历的范围划分：
 *    - 左子树中序：[inStart, rootIndex-1]
 *    - 右子树中序：[rootIndex+1, inEnd]
 *    - 根节点本身不包含在子树中
 * 
 * 4. 递归出口：
 *    - preStart > preEnd 或 inStart > inEnd
 *    - 两个条件等价，检查一个即可
 * 
 * 可视化示例：
 * 
 * preorder = [3, 9, 20, 15, 7]
 * inorder  = [9, 3, 15, 20, 7]
 * 
 * 步骤1：
 * - 根节点：preorder[0] = 3
 * - 在inorder中找到3的位置：index = 1
 * - 左子树：inorder[0..0] = [9]，preorder[1..1] = [9]
 * - 右子树：inorder[2..4] = [15,20,7]，preorder[2..4] = [20,15,7]
 * 
 * 步骤2（构建左子树）：
 * - 根节点：preorder[1] = 9
 * - 在inorder[0..0]中找到9：index = 0
 * - 左右子树都为空
 * 
 * 步骤3（构建右子树）：
 * - 根节点：preorder[2] = 20
 * - 在inorder[2..4]中找到20：index = 3
 * - 左子树：inorder[2..2] = [15]，preorder[3..3] = [15]
 * - 右子树：inorder[4..4] = [7]，preorder[4..4] = [7]
 * 
 * 最终构建的树：
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 
 * 关键技巧：
 * 
 * 1. HashMap优化：
 *    - 预先存储中序遍历的索引
 *    - 查找根节点位置从O(n)降到O(1)
 * 
 * 2. 范围传递：
 *    - 不创建新数组，只传递索引范围
 *    - 节省空间，提高效率
 * 
 * 3. 左子树大小是关键：
 *    - leftSize = rootIndex - inStart
 *    - 用于划分前序遍历的左右子树范围
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 106. 从中序与后序遍历序列构造二叉树（类似思路）
 * - LeetCode 889. 根据前序和后序遍历构造二叉树（更难）
 * - LeetCode 108/109. 有序数组/链表转BST（构建树的对比）
 */
public class ConstructBinaryTreeFromPreorderAndInorder {
    
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
     * 主方法：从前序和中序遍历构造二叉树
     * 
     * @param preorder 前序遍历数组
     * @param inorder 中序遍历数组
     * @return 构造的二叉树根节点
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 提示：递归 + HashMap
        // 
        // // 构建HashMap，存储中序遍历中每个值的索引
        // Map<Integer, Integer> inorderMap = new HashMap<>();
        // for (int i = 0; i < inorder.length; i++) {
        //     inorderMap.put(inorder[i], i);
        // }
        // 
        // // 调用递归函数
        // return build(preorder, 0, preorder.length - 1,
        //              inorder, 0, inorder.length - 1,
        //              inorderMap);
        
        // 递归函数：
        // private TreeNode build(int[] preorder, int preStart, int preEnd,
        //                        int[] inorder, int inStart, int inEnd,
        //                        Map<Integer, Integer> inorderMap) {
        //     // 递归出口
        //     if (preStart > preEnd) {
        //         return null;
        //     }
        //     
        //     // 前序遍历的第一个元素是根节点
        //     int rootVal = preorder[preStart];
        //     TreeNode root = new TreeNode(rootVal);
        //     
        //     // 在中序遍历中找到根节点的位置
        //     int rootIndex = inorderMap.get(rootVal);
        //     
        //     // 计算左子树的节点数
        //     int leftSize = rootIndex - inStart;
        //     
        //     // 递归构建左子树
        //     root.left = build(preorder, preStart + 1, preStart + leftSize,
        //                       inorder, inStart, rootIndex - 1,
        //                       inorderMap);
        //     
        //     // 递归构建右子树
        //     root.right = build(preorder, preStart + leftSize + 1, preEnd,
        //                        inorder, rootIndex + 1, inEnd,
        //                        inorderMap);
        //     
        //     return root;
        // }
        
        // 关键理解：
        // 1. 为什么需要HashMap？
        //    - 快速查找根节点在中序遍历中的位置
        //    - 时间复杂度从O(n)降到O(1)
        // 
        // 2. 左子树大小为什么是 rootIndex - inStart？
        //    - inStart可能不是0（递归时范围会变化）
        //    - 左子树节点数 = 根节点位置 - 左边界
        // 
        // 3. 前序遍历的范围如何划分？
        //    - 根节点：preStart
        //    - 左子树：[preStart+1, preStart+leftSize]
        //    - 右子树：[preStart+leftSize+1, preEnd]
        // 
        // 4. 中序遍历的范围如何划分？
        //    - 左子树：[inStart, rootIndex-1]
        //    - 根节点：rootIndex
        //    - 右子树：[rootIndex+1, inEnd]
        
        // 在这里编写你的实现代码

        // ============================================
        //前序的第一个元素就是当前子树的根节点
        //在中序中找到这个根节点的位置,中序左边部分是左子树，中序右边部分是右子树
        //根据左子树的大小，确定前序中左子树和右子树的范围
        //递归构造左右子树
        Map<Integer,Integer> inorderMap=new HashMap<>();
        //建立中序哈希表：key：节点值；value：节点下标
        for (int i=0;i<inorder.length;i++){
            inorderMap.put(inorder[i],i);
        }
        // ============================================
        return buildaNode(preorder,inorder,0,preorder.length-1,0,inorder.length-1,inorderMap);  // 记得返回结果
    }
    public TreeNode buildaNode(int[] preorder, int[] inorder,int preorderStrat,
                               int preorderEnd, int inorderStart, int inorderEnd,Map<Integer,Integer> inorderMap){
        if(preorderStrat>preorderEnd)
            return null;
        //preorderStart:当前的根节点
        TreeNode root=new TreeNode(preorder[preorderStrat]);
        //获取中序根节点下标
        int rootIndexInorder=inorderMap.get(preorder[preorderStrat]);
        //左子树中序范围：传入中序起点--根节点（不含）
        int leftinorderstart=inorderStart;
        int leftinorderend=rootIndexInorder-1;
        //右子树中序范围：根节点（不含）--传入中序终点
        int rightinorderstart=rootIndexInorder+1;
        int rightinorderend=inorderEnd;
        //左子树前序范围：传入中序起点（不含）--起点+左子树大小
        int leftpreorderstart=preorderStrat+1;
        int leftpreorderend=preorderStrat+(leftinorderend-leftinorderstart+1);
        //右子树前序范围：左子树前序范围+1--起点+右子树大小
        int rightpreorderstart=leftpreorderend+1;
        int rightpreorderend=preorderEnd;
        root.left=buildaNode(preorder, inorder,leftpreorderstart,
                leftpreorderend, leftinorderstart, leftinorderend,inorderMap);
        root.right=buildaNode(preorder, inorder,rightpreorderstart,
                rightpreorderend, rightinorderstart, rightinorderend,inorderMap);
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
     * 辅助方法：前序遍历（验证）
     */
    private static void preorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) return;
        result.add(root.val);
        preorderTraversal(root.left, result);
        preorderTraversal(root.right, result);
    }
    
    /**
     * 辅助方法：中序遍历（验证）
     */
    private static void inorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) return;
        inorderTraversal(root.left, result);
        result.add(root.val);
        inorderTraversal(root.right, result);
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        ConstructBinaryTreeFromPreorderAndInorder solution = new ConstructBinaryTreeFromPreorderAndInorder();
        
        System.out.println("=== LeetCode 105: 从前序与中序遍历序列构造二叉树 ===");
        System.out.println("中等题，经典的树构建问题\n");
        
        // 测试用例1：标准二叉树
        System.out.println("测试用例1: 标准二叉树");
        int[] preorder1 = {3, 9, 20, 15, 7};
        int[] inorder1 = {9, 3, 15, 20, 7};
        System.out.println("前序遍历: " + Arrays.toString(preorder1));
        System.out.println("中序遍历: " + Arrays.toString(inorder1));
        TreeNode root1 = solution.buildTree(preorder1, inorder1);
        System.out.println("构造的树:");
        printTree(root1, "", false);
        
        List<Integer> preResult1 = new ArrayList<>();
        List<Integer> inResult1 = new ArrayList<>();
        preorderTraversal(root1, preResult1);
        inorderTraversal(root1, inResult1);
        System.out.println("验证前序: " + preResult1);
        System.out.println("验证中序: " + inResult1);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2：单节点
        System.out.println("测试用例2: 单节点");
        int[] preorder2 = {-1};
        int[] inorder2 = {-1};
        System.out.println("前序遍历: " + Arrays.toString(preorder2));
        System.out.println("中序遍历: " + Arrays.toString(inorder2));
        TreeNode root2 = solution.buildTree(preorder2, inorder2);
        System.out.println("构造的树:");
        printTree(root2, "", false);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：只有左子树
        System.out.println("测试用例3: 只有左子树");
        int[] preorder3 = {1, 2, 3};
        int[] inorder3 = {3, 2, 1};
        System.out.println("前序遍历: " + Arrays.toString(preorder3));
        System.out.println("中序遍历: " + Arrays.toString(inorder3));
        TreeNode root3 = solution.buildTree(preorder3, inorder3);
        System.out.println("构造的树:");
        printTree(root3, "", false);
        
        List<Integer> preResult3 = new ArrayList<>();
        List<Integer> inResult3 = new ArrayList<>();
        preorderTraversal(root3, preResult3);
        inorderTraversal(root3, inResult3);
        System.out.println("验证前序: " + preResult3);
        System.out.println("验证中序: " + inResult3);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：只有右子树
        System.out.println("测试用例4: 只有右子树");
        int[] preorder4 = {1, 2, 3};
        int[] inorder4 = {1, 2, 3};
        System.out.println("前序遍历: " + Arrays.toString(preorder4));
        System.out.println("中序遍历: " + Arrays.toString(inorder4));
        TreeNode root4 = solution.buildTree(preorder4, inorder4);
        System.out.println("构造的树:");
        printTree(root4, "", false);
        
        List<Integer> preResult4 = new ArrayList<>();
        List<Integer> inResult4 = new ArrayList<>();
        preorderTraversal(root4, preResult4);
        inorderTraversal(root4, inResult4);
        System.out.println("验证前序: " + preResult4);
        System.out.println("验证中序: " + inResult4);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：完全二叉树
        System.out.println("测试用例5: 完全二叉树");
        int[] preorder5 = {1, 2, 4, 5, 3, 6, 7};
        int[] inorder5 = {4, 2, 5, 1, 6, 3, 7};
        System.out.println("前序遍历: " + Arrays.toString(preorder5));
        System.out.println("中序遍历: " + Arrays.toString(inorder5));
        TreeNode root5 = solution.buildTree(preorder5, inorder5);
        System.out.println("构造的树:");
        printTree(root5, "", false);
        
        List<Integer> preResult5 = new ArrayList<>();
        List<Integer> inResult5 = new ArrayList<>();
        preorderTraversal(root5, preResult5);
        inorderTraversal(root5, inResult5);
        System.out.println("验证前序: " + preResult5);
        System.out.println("验证中序: " + inResult5);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 前序遍历的第一个元素是根节点");
        System.out.println("2. 在中序遍历中找到根节点，划分左右子树");
        System.out.println("3. 左子树大小 = rootIndex - inStart");
        System.out.println("4. 使用HashMap优化查找，O(1)时间");
        System.out.println("5. 递归构建左右子树，注意范围划分");
        System.out.println("\n这是树构建的经典问题，必须掌握！");
    }
}





