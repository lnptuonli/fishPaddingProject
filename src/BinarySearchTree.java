// 定义二叉搜索树的节点结构
class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

// 定义 BST 操作类
public class BinarySearchTree {

    private TreeNode root;

    // 插入节点（构建 BST）
    public void insert(int val) {
        root = insertRecursive(root, val);
    }

    private TreeNode insertRecursive(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val); // 找到插入位置
        }
        if (val < node.val) {
            node.left = insertRecursive(node.left, val);
        } else if (val > node.val) {
            node.right = insertRecursive(node.right, val);
        }
        // 如果 val == node.val，通常不插入重复值
        return node;
    }

    // 查询节点（搜索值）
    public TreeNode search(int target) {
        return searchRecursive(root, target);
    }

    private TreeNode searchRecursive(TreeNode node, int target) {
        //边界处理+返回目标
        if (node == null || node.val == target) {
            return node;
        }
        if (target < node.val) {
            return searchRecursive(node.left, target);
        } else {
            return searchRecursive(node.right, target);
        }
    }

    // 中序遍历（升序输出）
    public void inOrderTraversal() {
        inOrderRecursive(root);
        System.out.println(); // 换行
    }

    private void inOrderRecursive(TreeNode node) {
        if (node == null) return;
        inOrderRecursive(node.left);
        System.out.print(node.val + " ");
        inOrderRecursive(node.right);
    }

    // 主函数示例
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        // 构建 BST
        int[] values = {5, 3, 7, 2, 4, 6, 8};
        for (int val : values) {
            bst.insert(val);
        }

        // 中序遍历（输出升序序列）
        System.out.print("中序遍历结果: ");
        bst.inOrderTraversal(); // 输出：2 3 4 5 6 7 8

        // 查询节点
        int target = 4;
        TreeNode result = bst.search(target);
        if (result != null) {
            System.out.println("找到节点: " + result.val);
        } else {
            System.out.println("未找到节点: " + target);
        }
    }
}