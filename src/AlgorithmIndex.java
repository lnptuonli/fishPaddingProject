/**
 * 算法题目索引
 * 
 * 使用方法：
 * 1. 按 Ctrl/Cmd + F 搜索题目中文名或编号
 * 2. 点击方法名跳转到对应的题解文件
 * 3. 或者直接 Ctrl/Cmd + 点击类名跳转
 * 
 * 索引格式：
 * [LeetCode编号] 题目中文名 - 题目英文名
 * 难度：★☆☆ (简单) / ★★☆ (中等) / ★★★ (困难)
 * 标签：数组、字符串、动态规划等
 * 
 * 更新日期：2025-11-03
 */
public class AlgorithmIndex {
    
    // ====================================
    // 字符串相关题目
    // ====================================
    
    /**
     * [LeetCode 6] Z字形变换 - Zigzag Conversion
     * 难度：★★☆ (中等，但实际偏难)
     * 标签：字符串、模拟
     * 
     * 题目简述：
     * 将字符串按Z字形排列后按行读取
     * 
     * 核心思路：
     * 1. 矩阵模拟法：创建矩阵填充字符
     * 2. 找规律法：周期 = 2*numRows-2
     * 
     * 个人笔记：
     * - 使用V字形理解更直观
     * - 注意边界条件：numRows=1
     */
    private void leetcode6_Z字形变换() {
        Z_convert solution = new Z_convert();
        // Ctrl/Cmd + 点击 Z_convert 即可跳转
    }
    
    /**
     * [LeetCode 7] 整数反转 - Reverse Integer
     * 难度：★☆☆ (简单，但溢出处理有难度)
     * 标签：数学、溢出检测
     * 
     * 题目简述：
     * 反转一个32位有符号整数，溢出返回0
     * 
     * 核心思路：
     * 1. 使用 %10 取最后一位，/10 去掉最后一位
     * 2. 在乘以10之前检测溢出
     * 
     * 个人笔记：
     * - 不能使用long的解法更能展示算法能力
     * - 溢出检测：if (result > Integer.MAX_VALUE/10)
     * - 边界值：MAX/10 = 214748364
     */
    private void leetcode7_整数反转() {
        ReverseInteger solution = new ReverseInteger();
        // Ctrl/Cmd + 点击 ReverseInteger 即可跳转
    }
    
    /**
     * [LeetCode 8] 字符串转换整数(atoi) - String to Integer (atoi)
     * 难度：★★☆ (中等，边界条件多)
     * 标签：字符串、模拟
     * 
     * 题目简述：
     * 实现atoi函数，将字符串转换为整数，处理空格、符号、溢出等
     * 
     * 核心思路：
     * 1. 跳过前导空格
     * 2. 检测符号（+/-）
     * 3. 逐位读取数字
     * 4. 溢出时截断（不是返回0！）
     * 
     * 个人笔记：
     * - 注意：这题溢出要截断到边界值，不是返回0
     * - 遇到非数字字符立即停止
     * - 符号后不能有空格
     */
    private void leetcode8_字符串转整数() {
        StringToInteger solution = new StringToInteger();
        // Ctrl/Cmd + 点击 StringToInteger 即可跳转
    }
    
    // ====================================
    // 数组相关题目
    // ====================================
    
    /**
     * [LeetCode 11] 盛最多水的容器 - Container With Most Water
     * 难度：★★☆ (中等)
     * 标签：数组、双指针、贪心
     * 
     * 题目简述：
     * 给定数组代表垂线高度，找两条线与x轴构成的容器能装最多水
     * 
     * 核心思路：
     * 双指针法：从两端向中间移动，每次移动较矮的那一边
     * 
     * 个人笔记：
     * - 面积 = 宽度 × 较矮的高度
     * - 为什么移动矮的？移动高的宽度↓高度不变，面积必↓
     * - 木桶效应：水的高度取决于较矮的板
     * - 时间复杂度O(n)，比暴力法O(n²)快得多
     */
    private void leetcode11_盛最多水的容器() {
        ContainerWithMostWater solution = new ContainerWithMostWater();
        // Ctrl/Cmd + 点击 ContainerWithMostWater 即可跳转
    }
    
    /**
     * [LeetCode 12] 整数转罗马数字 - Integer to Roman
     * 难度：★★☆ (中等)
     * 标签：数学、字符串、贪心
     * 
     * 题目简述：
     * 将整数(1-3999)转换为罗马数字
     * 
     * 核心思路：
     * 贪心算法：建立13个符号的映射表（7个基本+6个减法形式），从大到小匹配
     * 
     * 个人笔记：
     * - 必须包含减法形式：IV(4)、IX(9)、XL(40)、XC(90)、CD(400)、CM(900)
     * - 按值从大到小遍历：1000→900→500→...→1
     * - I/X/C/M最多连续3次，V/L/D不重复
     * - 时间复杂度O(1)，固定13次循环
     */
    private void leetcode12_整数转罗马数字() {
        IntegerToRoman solution = new IntegerToRoman();
        // Ctrl/Cmd + 点击 IntegerToRoman 即可跳转
    }
    
    /**
     * [LeetCode 13] 罗马数字转整数 - Roman to Integer
     * 难度：★☆☆ (简单)
     * 标签：哈希表、数学、字符串
     * 
     * 题目简述：
     * 将罗马数字字符串转换为整数
     * 
     * 核心思路：
     * 从左到右遍历，比较相邻字符：当前值 < 下一个值时减去，否则加上
     * 
     * 个人笔记：
     * - 关键：小数字在大数字前面表示减法（如IV=4）
     * - 遍历时比较当前和下一个字符的大小关系
     * - 注意最后一个字符的边界处理
     * - 时间复杂度O(n)，与第12题互为逆向操作
     */
    private void leetcode13_罗马数字转整数() {
        RomanToInteger solution = new RomanToInteger();
        // Ctrl/Cmd + 点击 RomanToInteger 即可跳转
    }
    
    /**
     * [LeetCode 14] 最长公共前缀 - Longest Common Prefix
     * 难度：★☆☆ (简单)
     * 标签：字符串
     * 
     * 题目简述：
     * 查找字符串数组中的最长公共前缀
     * 
     * 核心思路：
     * 横向扫描：以第一个字符串为基准，逐字符与其他字符串比较
     * 
     * 个人笔记：
     * - 遇到不匹配或字符串结束立即返回
     * - 注意边界：空字符串、单个字符串
     * - 时间复杂度O(S)，S是所有字符总数
     * - 简单题，一分钟速通！
     */
    private void leetcode14_最长公共前缀() {
        LongestCommonPrefix solution = new LongestCommonPrefix();
        // Ctrl/Cmd + 点击 LongestCommonPrefix 即可跳转
    }
    
    /**
     * [LeetCode 15] 三数之和 - 3Sum
     * 难度：★★☆ (中等，经典题)
     * 标签：数组、双指针、排序
     * 
     * 题目简述：
     * 找出数组中所有和为0的不重复三元组
     * 
     * 核心思路：
     * 排序 + 固定一个数 + 双指针找另外两个数
     * 
     * 个人笔记：
     * - 先排序！这是关键
     * - 固定nums[i]，用双指针找两数之和=-nums[i]
     * - 三个地方去重：i、left、right（这是难点）
     * - 时间复杂度O(n²)，比暴力O(n³)快得多
     * - 去重逻辑要小心，容易出错
     */
    private void leetcode15_三数之和() {
        ThreeSum solution = new ThreeSum();
        // Ctrl/Cmd + 点击 ThreeSum 即可跳转
    }
    
    /**
     * [LeetCode 16] 最接近的三数之和 - 3Sum Closest
     * 难度：★★☆ (中等)
     * 标签：数组、双指针、排序
     * 
     * 题目简述：
     * 找出最接近target的三数之和
     * 
     * 核心思路：
     * 与第15题几乎完全相同，只是判断条件改为维护最小差距
     * 
     * 个人笔记：
     * - 和第15题一模一样的思路：排序+双指针
     * - 不需要去重（只要一个答案）
     * - 维护 |sum - target| 最小
     * - sum == target 时直接返回
     * - 这题真的一分钟！
     */
    private void leetcode16_最接近的三数之和() {
        ThreeSumClosest solution = new ThreeSumClosest();
        // Ctrl/Cmd + 点击 ThreeSumClosest 即可跳转
    }
    
    /**
     * [LeetCode 18] 四数之和 - 4Sum
     * 难度：★★☆ (中等，但比三数之和复杂)
     * 标签：数组、双指针、排序
     * 
     * 题目简述：
     * 找出数组中所有和为target的不重复四元组
     * 
     * 核心思路：
     * 三数之和的套娃版：固定两个数 + 双指针找另外两个数
     * 
     * 个人笔记：
     * - 就是三数之和外面再套一层循环
     * - 时间复杂度O(n³)，比三数之和多一层
     * - 4个地方去重：i、j、left、right
     * - ⚠️ 重点：整数溢出！必须用 long 类型
     * - nums[i] 范围 -10^9，四个数相加会超过 int 范围
     * - 剪枝优化可以提前结束循环
     */
    private void leetcode18_四数之和() {
        FourSum solution = new FourSum();
        // Ctrl/Cmd + 点击 FourSum 即可跳转
    }
    
    /**
     * [LeetCode 3607] 电网维护 - Power Grid Maintenance
     * 难度：★★★ (中等偏难，涉及并查集)
     * 标签：并查集、哈希表、图论
     * 
     * 题目简述：
     * 动态查询电网中的在线电站，支持电站离线操作
     * 
     * 核心思路：
     * 并查集维护电网连通性 + TreeSet维护每个电网的最小在线电站
     * 
     * 个人笔记：
     * - 这是一道新类型题目：并查集！
     * - 并查集用于维护集合的连通性
     * - 路径压缩 + 按秩合并优化
     * - TreeSet自动排序，first()获取最小值
     * - HashMap映射根节点到在线电站集合
     * - 时间复杂度：O((n+q)*α(c))，α是反阿克曼函数
     * - 难度较高，需要先理解并查集的基本原理
     * - 这题是对前面"数组+双指针"系列的升级挑战！
     */
    private void leetcode3607_电网维护() {
        PowerGrid solution = new PowerGrid();
        // Ctrl/Cmd + 点击 PowerGrid 即可跳转
    }
    
    /**
     * [LeetCode 2528] 最大化城市的最小电量 - Maximize the Minimum Powered City
     * 难度：★★★ (困难！综合性极强)
     * 标签：二分答案、贪心、滑动窗口
     * 
     * 题目简述：
     * 在k个额外供电站的约束下，最大化所有城市中最小电量
     * 
     * 核心思路：
     * 二分答案 + 贪心验证 + 滑动窗口优化
     * 
     * 个人笔记：
     * - 这是"最大化最小值"问题 → 二分答案
     * - 验证时用贪心：在i+r位置建站（尽可能靠右）
     * - 滑动窗口快速计算每个城市的电量
     * - 时间复杂度：O(n * log(sum + k))
     * - 难度很高！综合了：二分、贪心、滑动窗口
     * - 需要先理解二分答案的思想
     * - 建议先做简单的二分答案题练手
     * - 这是每日一题的困难题，挑战性极强！
     */
    private void leetcode2528_最大化城市的最小电量() {
        MaxPowerStations solution = new MaxPowerStations();
        // Ctrl/Cmd + 点击 MaxPowerStations 即可跳转
    }
    
    /**
     * [LeetCode 2169] 得到 0 的操作数 - Count Operations to Obtain Zero
     * 难度：★☆☆ (简单，但要注意优化)
     * 标签：数学、模拟
     * 
     * 题目简述：
     * 每次用大的减小的，直到其中一个变成0，求操作次数
     * 
     * 核心思路：
     * 优化模拟：用除法代替多次减法（辗转相除法）
     * 
     * 个人笔记：
     * - 暴力：每次减1次，O(max(num1, num2))
     * - 优化：每次减多次，O(log(min(num1, num2)))
     * - 本质：欧几里得算法（GCD）的变形
     * - 关键：num1 / num2 表示可以减多少次
     * - 简单题，但体现了优化思想
     * - 从困难题回来做简单题，感觉轻松多了！
     */
    private void leetcode2169_得到0的操作数() {
        CountOperations solution = new CountOperations();
        // Ctrl/Cmd + 点击 CountOperations 即可跳转
    }
    
    /**
     * [LeetCode 95] 不同的二叉搜索树 II - Unique Binary Search Trees II
     * [LeetCode 94] 问题94：二叉树的中序遍历
     * [LeetCode 96] 不同的二叉搜索树 (仅返回数量，直接使用卡特兰数)
     * 难度：★★☆ (中等，经典递归题)
     * 标签：树、二叉搜索树、递归、回溯
     * 
     * 题目简述：
     * 给定 n，生成所有由 1 到 n 组成的不同二叉搜索树
     * 
     * 核心思路：
     * 递归生成：枚举根节点，递归生成左右子树，组合所有可能
     * 
     * 个人笔记：
     * - 关键：枚举根节点 i，左子树 [start, i-1]，右子树 [i+1, end]
     * - 组合：左子树的每一种 × 右子树的每一种（笛卡尔积）
     * - 递归出口：start > end 返回 [null]（空树）
     * - BST数量 = 卡特兰数：C(3)=5, C(4)=14, C(5)=42
     * - 时间复杂度：O(n * G(n))，G(n)是卡特兰数
     * - 这是树的递归的经典题目！
     * - 理解递归的本质：大问题分解为小问题
     * - 注意每次都要创建新节点，不能复用
     * - 加强树与图的练习，这题很有代表性！
     */
    private void leetcode95_不同的二叉搜索树II() {
        UniqueBST_II solution = new UniqueBST_II();
        // Ctrl/Cmd + 点击 UniqueBST_II 即可跳转
    }
    private void leetcode94_二叉树的中序遍历() {
        UniqueBST_II solution = new UniqueBST_II();
        UniqueBST_II.TreeNode node =new UniqueBST_II.TreeNode();
        solution.inorderTraversal(node);
        // Ctrl/Cmd + 点击 inorderTraversal 即可跳转
    }
    private void leetcode96_不同的二叉搜索树() {
        UniqueBST_II solution = new UniqueBST_II();
        solution.numTrees(4);
        // Ctrl/Cmd + 点击 numTrees 即可跳转
    }
    
    /**
     * [LeetCode 98] 验证二叉搜索树 - Validate Binary Search Tree
     * 难度：★★☆ (中等，但有陷阱)
     * 标签：树、二叉搜索树、递归、DFS
     * 
     * 题目简述：
     * 判断一个二叉树是否是有效的二叉搜索树
     * 
     * 核心思路：
     * 递归 + 区间限制：每个节点都有合法的取值范围 [min, max]
     * 
     * 个人笔记：
     * - 常见错误：只检查 left < node < right ❌
     * - 正确做法：左子树的所有节点 < 根 < 右子树的所有节点
     * - 方法1：递归传递 [min, max] 范围
     * - 方法2：中序遍历，检查是否严格递增
     * - 注意：严格小于和严格大于，不能相等
     * - 边界值：使用 null 表示无边界（优于 Integer.MIN/MAX）
     * - 时间复杂度：O(n)
     * - 这是 BST 验证的经典题目！
     * - 很多人第一次做会掉进陷阱
     * - 理解后会对 BST 的性质有更深的认识
     */
    private void leetcode98_验证二叉搜索树() {
        ValidateBST solution = new ValidateBST();
        // Ctrl/Cmd + 点击 ValidateBST 即可跳转
    }
    
    /**
     * [LeetCode 108] 将有序数组转换为二叉搜索树 - Convert Sorted Array to Binary Search Tree
     * 难度：★☆☆ (简单)
     * 标签：树、二叉搜索树、递归、分治
     * 
     * 题目简述：
     * 将一个按升序排列的有序数组，转换为一棵平衡二叉搜索树
     * 
     * 核心思路：
     * 选择中间元素作为根，递归构建左右子树
     * 
     * 个人笔记：
     * - 关键：选择中间元素保证平衡性
     * - 左半部分构建左子树，右半部分构建右子树
     * - 递归出口：left > right 返回 null
     * - 中点计算：mid = left + (right - left) / 2
     * - 时间复杂度：O(n)
     * - 空间复杂度：O(log n)
     * - 这是 BST 构建的基础题！
     * - 理解递归构建的思想
     */
    private void leetcode108_将有序数组转换为二叉搜索树() {
        SortedArrayToBST solution = new SortedArrayToBST();
        // Ctrl/Cmd + 点击 SortedArrayToBST 即可跳转
    }
    
    /**
     * [LeetCode 109] 有序链表转换为二叉搜索树 - Convert Sorted List to Binary Search Tree
     * 难度：★★☆ (中等)
     * 标签：树、二叉搜索树、链表、快慢指针
     * 
     * 题目简述：
     * 将一个按升序排列的有序链表，转换为一棵平衡二叉搜索树
     * 
     * 核心思路：
     * 方法1：转换为数组；方法2：快慢指针找中点
     * 
     * 个人笔记：
     * - 与数组版本的区别：链表无法 O(1) 访问中间元素
     * - 方法1：转换为数组，O(n)空间，简单直接
     * - 方法2：快慢指针找中点，O(log n)空间，更优
     * - 快慢指针：快指针走2步，慢指针走1步
     * - 需要记录前驱节点，用于断开链表
     * - 时间复杂度：O(n log n)（快慢指针版）
     * - 这是链表+树的综合题！
     * - 掌握快慢指针找中点的技巧
     */
    private void leetcode109_有序链表转换为二叉搜索树() {
        SortedListToBST solution = new SortedListToBST();
        // Ctrl/Cmd + 点击 SortedListToBST 即可跳转
    }
    
    /**
     * [LeetCode 206] 反转链表 - Reverse Linked List
     * 难度：★☆☆ (简单，但必须掌握)
     * 标签：链表、迭代、递归
     * 
     * 题目简述：
     * 反转一个单链表
     * 
     * 核心思路：
     * 迭代：使用三个指针 prev, curr, next
     * 
     * 个人笔记：
     * - 链表操作的基础题，必须掌握！
     * - 迭代法：prev=null, curr=head，逐个反转指针
     * - 关键：先保存 next，再反转 curr.next
     * - 返回 prev（新的头节点）
     * - 时间复杂度：O(n)
     * - 空间复杂度：O(1)
     * - 这是链表面试的高频题！
     * - 也可以用递归实现，但迭代更简洁
     */
    private void leetcode206_反转链表() {
        ReverseLinkedList solution = new ReverseLinkedList();
        // Ctrl/Cmd + 点击 ReverseLinkedList 即可跳转
    }
    
    /**
     * [LeetCode 876] 链表的中间结点 - Middle of the Linked List
     * 难度：★☆☆ (简单)
     * 标签：链表、快慢指针
     * 
     * 题目简述：
     * 找到链表的中间结点（偶数个节点返回第二个）
     * 
     * 核心思路：
     * 快慢指针：快指针走2步，慢指针走1步
     * 
     * 个人笔记：
     * - 快慢指针的经典应用！
     * - slow=head, fast=head
     * - while (fast != null && fast.next != null)
     * - 快指针到末尾时，慢指针在中间
     * - 偶数个节点时，返回第二个中间结点
     * - 时间复杂度：O(n)
     * - 空间复杂度：O(1)
     * - 这是 LeetCode 109 的基础！
     * - 掌握快慢指针技巧
     */
    private void leetcode876_链表的中间结点() {
        MiddleOfLinkedList solution = new MiddleOfLinkedList();
        // Ctrl/Cmd + 点击 MiddleOfLinkedList 即可跳转
    }
    
    /**
     * [LeetCode 110] 平衡二叉树 - Balanced Binary Tree
     * 难度：★☆☆ (简单)
     * 标签：树、DFS、递归、自底向上
     * 
     * 题目简述：
     * 判断一个二叉树是否是高度平衡的（每个节点的左右子树高度差 ≤ 1）
     * 
     * 核心思路：
     * 自底向上计算高度，用 -1 表示不平衡
     * 
     * 个人笔记：
     * - 自底向上优化，避免重复计算高度
     * - getHeight()：返回高度或-1（不平衡）
     * - 如果左子树不平衡，返回-1
     * - 如果右子树不平衡，返回-1
     * - 如果高度差 > 1，返回-1
     * - 否则返回 max(left, right) + 1
     * - 时间复杂度：O(n)（自底向上）vs O(n^2)（自顶向下）
     * - 空间复杂度：O(n)
     * - 这是自底向上优化的经典案例！
     */
    private void leetcode110_平衡二叉树() {
        BalancedBinaryTree solution = new BalancedBinaryTree();
        // Ctrl/Cmd + 点击 BalancedBinaryTree 即可跳转
    }
    
    /**
     * [LeetCode 111] 二叉树的最小深度 - Minimum Depth of Binary Tree
     * 难度：★☆☆ (简单，但有易错点)
     * 标签：树、DFS、BFS、递归
     * 
     * 题目简述：
     * 找到从根节点到叶子节点的最短路径的长度
     * 
     * 核心思路：
     * DFS递归，注意只有一侧子树的情况
     * 
     * 个人笔记：
     * - 叶子节点：left == null && right == null
     * - 易错点：只有一侧子树时，不能用 min(0, depth)
     * - 如果左子树为空，返回右子树深度 + 1
     * - 如果右子树为空，返回左子树深度 + 1
     * - 如果都存在，返回 min(left, right) + 1
     * - BFS更适合求最小深度（遇到第一个叶子节点即返回）
     * - 时间复杂度：O(n)
     * - 空间复杂度：O(n)
     * - 注意与最大深度的区别！
     */
    private void leetcode111_二叉树的最小深度() {
        MinDepthOfBinaryTree solution = new MinDepthOfBinaryTree();
        // Ctrl/Cmd + 点击 MinDepthOfBinaryTree 即可跳转
    }
    
    /**
     * [LeetCode 112] 路径总和 - Path Sum
     * 难度：★☆☆ (简单)
     * 标签：树、DFS、递归、叶子节点判断
     * 
     * 题目简述：
     * 判断是否存在根到叶子的路径，使得路径和等于 targetSum
     * 
     * 核心思路：
     * DFS递归，每次减去当前节点值，到叶子节点时判断
     * 
     * 个人笔记：
     * - 路径必须到叶子节点（left == null && right == null）
     * - 每次递归：targetSum - root.val
     * - 到叶子节点时：targetSum == root.val
     * - 空树返回 false，即使 targetSum == 0
     * - 不能在中间节点停止
     * - 时间复杂度：O(n)
     * - 空间复杂度：O(n)
     * - 叶子节点判断是关键！
     */
    private void leetcode112_路径总和() {
        PathSum solution = new PathSum();
        // Ctrl/Cmd + 点击 PathSum 即可跳转
    }
    
    /**
     * [LeetCode 105] 从前序与中序遍历序列构造二叉树 - Construct Binary Tree from Preorder and Inorder Traversal
     * 难度：★★☆ (中等，经典必会)
     * 标签：树、递归、HashMap、数组分割
     * 
     * 题目简述：
     * 给定前序和中序遍历数组，构造二叉树
     * 
     * 核心思路：
     * 前序第一个元素是根，在中序中找到根，划分左右子树，递归构建
     * 
     * 个人笔记：
     * - 前序遍历：根 -> 左 -> 右，第一个元素是根
     * - 中序遍历：左 -> 根 -> 右，根节点划分左右子树
     * - HashMap存储中序索引，O(1)查找根位置
     * - 左子树大小 = rootIndex - inStart（关键！）
     * - 前序左子树：[preStart+1, preStart+leftSize]
     * - 前序右子树：[preStart+leftSize+1, preEnd]
     * - 中序左子树：[inStart, rootIndex-1]
     * - 中序右子树：[rootIndex+1, inEnd]
     * - 时间复杂度：O(n)
     * - 空间复杂度：O(n)
     * - 这是树构建的经典问题！
     */
    private void leetcode105_从前序与中序遍历序列构造二叉树() {
        ConstructBinaryTreeFromPreorderAndInorder solution = new ConstructBinaryTreeFromPreorderAndInorder();
        // Ctrl/Cmd + 点击 ConstructBinaryTreeFromPreorderAndInorder 即可跳转
    }
    
    /**
     * [LeetCode 236] 二叉树的最近公共祖先 - Lowest Common Ancestor of a Binary Tree
     * 难度：★★☆ (中等，面试超高频)
     * 标签：树、DFS、递归、后序遍历
     * 
     * 题目简述：
     * 找到二叉树中两个节点的最近公共祖先
     * 
     * 核心思路：
     * 后序遍历，找到p或q就返回，左右都找到则当前节点是LCA
     * 
     * 个人笔记：
     * - 后序遍历思想：先处理左右子树，再处理当前节点
     * - 递归出口：root==null 或 root==p 或 root==q
     * - 递归查找左右子树
     * - 如果左右都不为null，当前节点是LCA
     * - 如果只有一侧不为null，返回那一侧
     * - 节点可以是自己的祖先
     * - 返回值含义：找到的节点或LCA
     * - 时间复杂度：O(n)
     * - 空间复杂度：O(n)
     * - 这是树的经典问题，面试必考！
     */
    private void leetcode236_二叉树的最近公共祖先() {
        LowestCommonAncestor solution = new LowestCommonAncestor();
        // Ctrl/Cmd + 点击 LowestCommonAncestor 即可跳转
    }
    
    /**
     * [LeetCode 1] 两数之和 - Two Sum
     * 难度：★☆☆ (简单)
     * 标签：数组、哈希表
     * 
     * 题目简述：
     * 给定数组和目标值，找出和为目标值的两个数的索引
     * 
     * 核心思路：
     * 使用HashMap，时间复杂度O(n)
     * 
     * 个人笔记：
     * - 一次遍历即可
     * - key存值，value存索引
     */
    private void leetcode1_两数之和() {
        sumoftheTwoNoOrder solution = new sumoftheTwoNoOrder();
        // Ctrl/Cmd + 点击 sumoftheTwoNoOrder 即可跳转
    }
    
    // ====================================
    // 链表相关题目
    // ====================================
    
    /**
     * [LeetCode 2] 两数相加 - Add Two Numbers
     * 难度：★★☆ (中等)
     * 标签：链表、数学
     * 
     * 题目简述：
     * 两个逆序存储的链表表示的数字相加
     * 
     * 核心思路：
     * 逐位相加，处理进位
     * 
     * 个人笔记：
     * - 注意进位的处理
     * - 最后可能还有进位
     */
    private void leetcode2_两数相加() {
        getPlusTwoNums solution = new getPlusTwoNums();
        // Ctrl/Cmd + 点击 getPlusTwoNums 即可跳转
    }
    
    // ====================================
    // 动态规划相关题目
    // ====================================
    
    /**
     * [LeetCode 5] 最长回文子串 - Longest Palindromic Substring
     * 难度：★★☆ (中等)
     * 标签：字符串、动态规划、中心扩展
     * 
     * 题目简述：
     * 找出字符串中最长的回文子串
     * 
     * 核心思路：
     * 1. 中心扩展法：O(n^2)时间，O(1)空间
     * 2. 动态规划：O(n^2)时间，O(n^2)空间
     * 3. Manacher算法：O(n)时间
     * 
     * 个人笔记：
     * - 注意奇数和偶数长度的回文
     * - 中心扩展法最直观
     */
    private void leetcode5_最长回文子串() {
        longestSubPalindrome solution = new longestSubPalindrome();
        // Ctrl/Cmd + 点击 longestSubPalindrome 即可跳转
    }
    
    /**
     * [LeetCode 3] 无重复字符的最长子串 - Longest Substring Without Repeating Characters
     * 难度：★★☆ (中等)
     * 标签：字符串、滑动窗口、哈希表
     * 
     * 题目简述：
     * 找出不含重复字符的最长子串的长度
     * 
     * 核心思路：
     * 滑动窗口 + HashSet/HashMap
     * 
     * 个人笔记：
     * - 滑动窗口的经典应用
     * - 双指针维护窗口
     */
    private void leetcode3_无重复字符的最长子串() {
        longestSubString solution = new longestSubString();
        // Ctrl/Cmd + 点击 longestSubString 即可跳转
    }
    
    // ====================================
    // 按难度分类
    // ====================================
    
    /**
     * 简单题目列表：
     * - [1] 两数之和
     * - [7] 整数反转
     * - [13] 罗马数字转整数
     * - [14] 最长公共前缀
     * - [108] 将有序数组转换为BST
     * - [110] 平衡二叉树 ⭐ (自底向上经典)
     * - [111] 二叉树的最小深度 (易错点)
     * - [112] 路径总和
     * - [206] 反转链表 ⭐ (链表基础必会)
     * - [876] 链表的中间结点
     * - [2169] 得到0的操作数
     * 
     * 中等题目列表：
     * - [2] 两数相加
     * - [3] 无重复字符的最长子串
     * - [5] 最长回文子串
     * - [6] Z字形变换
     * - [8] 字符串转换整数(atoi)
     * - [11] 盛最多水的容器
     * - [12] 整数转罗马数字
     * - [15] 三数之和 ⭐
     * - [16] 最接近的三数之和
     * - [18] 四数之和 ⭐
     * - [95] 不同的二叉搜索树 II ⭐⭐ (树的递归经典题)
     * - [98] 验证二叉搜索树 ⭐ (经典陷阱题)
     * - [105] 从前序与中序构造二叉树 ⭐⭐ (经典必会)
     * - [109] 有序链表转换为BST ⭐ (链表+树)
     * - [236] 二叉树的最近公共祖先 ⭐⭐⭐ (面试超高频)
     * - [3607] 电网维护 ⭐⭐ (并查集入门题)
     * 
     * 困难题目列表：
     * - [2528] 最大化城市的最小电量 ⭐⭐⭐ (二分答案+贪心+滑动窗口)
     */
    
    // ====================================
    // 按标签分类
    // ====================================
    
    /**
     * 字符串专题：
     * - [3] 无重复字符的最长子串
     * - [5] 最长回文子串
     * - [6] Z字形变换
     * - [8] 字符串转换整数(atoi)
     * - [12] 整数转罗马数字
     * - [13] 罗马数字转整数
     * - [14] 最长公共前缀
     * 
     * 数组专题：
     * - [1] 两数之和
     * - [11] 盛最多水的容器
     * - [15] 三数之和 ⭐
     * - [16] 最接近的三数之和
     * - [18] 四数之和 ⭐
     * 
     * 链表专题：
     * - [2] 两数相加
     * 
     * 数学专题：
     * - [7] 整数反转
     * - [12] 整数转罗马数字
     * - [13] 罗马数字转整数
     * - [2169] 得到0的操作数（欧几里得算法变形）
     * 
     * 哈希表专题：
     * - [1] 两数之和
     * - [13] 罗马数字转整数
     * 
     * 双指针专题：
     * - [11] 盛最多水的容器
     * - [15] 三数之和 ⭐
     * - [16] 最接近的三数之和
     * - [18] 四数之和 ⭐
     * 
     * 排序专题：
     * - [15] 三数之和
     * - [16] 最接近的三数之和
     * - [18] 四数之和
     * 
     * 滑动窗口专题：
     * - [3] 无重复字符的最长子串
     * 
     * 动态规划专题：
     * - [5] 最长回文子串
     * 
     * 贪心算法专题：
     * - [11] 盛最多水的容器
     * - [12] 整数转罗马数字
     * 
     * 树专题：
     * - [95] 不同的二叉搜索树 II ⭐⭐ (递归生成BST)
     * - [98] 验证二叉搜索树 ⭐ (BST验证)
     * - [105] 从前序与中序构造二叉树 ⭐⭐ (树构建经典)
     * - [108] 将有序数组转换为BST (BST构建)
     * - [109] 有序链表转换为BST ⭐ (链表+BST)
     * - [110] 平衡二叉树 ⭐ (高度计算)
     * - [111] 二叉树的最小深度 (深度计算)
     * - [112] 路径总和 (路径遍历)
     * - [236] 二叉树的最近公共祖先 ⭐⭐⭐ (LCA经典)
     * 
     * 递归专题：
     * - [95] 不同的二叉搜索树 II ⭐⭐ (经典递归+组合)
     * - [98] 验证二叉搜索树 ⭐ (递归+区间限制)
     * - [105] 从前序与中序构造二叉树 ⭐⭐ (递归+范围划分)
     * - [108] 将有序数组转换为BST (递归构建)
     * - [109] 有序链表转换为BST ⭐ (递归+快慢指针)
     * - [236] 二叉树的最近公共祖先 ⭐⭐⭐ (后序遍历递归)
     * 
     * 二叉搜索树专题：
     * - [95] 不同的二叉搜索树 II ⭐⭐ (生成所有BST)
     * - [98] 验证二叉搜索树 ⭐ (BST性质验证)
     * - [108] 将有序数组转换为BST (BST构建基础)
     * - [109] 有序链表转换为BST ⭐ (BST构建进阶)
     * 
     * DFS专题：
     * - [98] 验证二叉搜索树 ⭐ (深度优先搜索)
     * - [110] 平衡二叉树 ⭐ (自底向上DFS)
     * - [111] 二叉树的最小深度 (DFS递归)
     * - [112] 路径总和 (DFS路径遍历)
     * - [236] 二叉树的最近公共祖先 ⭐⭐⭐ (DFS后序遍历)
     * 
     * 链表专题：
     * - [2] 两数相加
     * - [109] 有序链表转换为BST ⭐ (链表+树)
     * - [206] 反转链表 ⭐ (链表基础必会)
     * - [876] 链表的中间结点 (快慢指针)
     * 
     * 快慢指针专题：
     * - [109] 有序链表转换为BST ⭐ (找链表中点)
     * - [876] 链表的中间结点 (快慢指针基础)
     * 
     * 并查集专题：
     * - [3607] 电网维护 ⭐⭐ (并查集入门)
     * 
     * 图论专题：
     * - [3607] 电网维护
     * 
     * 二分查找专题：
     * - [2528] 最大化城市的最小电量 ⭐⭐⭐ (二分答案)
     */
    
    // ====================================
    // 统计信息
    // ====================================
    
    /**
     * 已完成题目统计
     * 
     * 总计：29 题 🎉
     * 
     * 按难度分类：
     * - 简单：11 题
     * - 中等：16 题
     * - 困难：2 题
     * 
     * 树专题完成度：9 题
     * - BST构建与验证：4题 (95, 98, 108, 109)
     * - 树的遍历与构建：2题 (105, 236)
     * - 树的深度与平衡：3题 (110, 111, 112)
     */
    
    // ====================================
    // 个人总结
    // ====================================
    
    /**
     * 易错点总结：
     * 
     * 1. 溢出处理：
     *    - [7] 整数反转：溢出返回0
     *    - [8] atoi：溢出要截断到边界值
     *    - 关键：在计算前检测，不是计算后
     * 
     * 2. 边界条件：
     *    - [6] Z字形：numRows=1 要特殊处理
     *    - [8] atoi：空格、符号、非数字字符的处理
     * 
     * 3. 字符串处理：
     *    - charAt(i) 获取字符
     *    - c - '0' 字符转数字
     *    - trim() vs 手动跳过空格
     * 
     * 4. 数据结构选择：
     *    - HashMap：快速查找
     *    - StringBuilder：字符串拼接
     *    - 滑动窗口：连续子串问题
     * 
     * 5. 树的易错点：
     *    - [111] 最小深度：只有一侧子树时不能用min(0, depth)
     *    - [112] 路径总和：必须到叶子节点，不能在中间停止
     *    - [105] 树构建：左子树大小 = rootIndex - inStart
     *    - [236] LCA：后序遍历，左右都找到时当前节点是LCA
     */
    
    /**
     * 解题技巧总结：
     * 
     * 1. 时间复杂度优化：
     *    - O(n^2) → O(n)：使用哈希表
     *    - 双层循环 → 滑动窗口
     * 
     * 2. 空间复杂度优化：
     *    - 原地修改 vs 创建新对象
     *    - 滚动数组优化DP
     * 
     * 3. 模拟类题目：
     *    - 先理解规律
     *    - 画图辅助理解
     *    - 考虑边界条件
     */
    
    // ====================================
    // 使用说明
    // ====================================
    
    /**
     * 快速查找方法：
     * 
     * 方法1：按编号查找
     * - Ctrl/Cmd + F 搜索 "[LeetCode 6]"
     * 
     * 方法2：按中文名查找
     * - Ctrl/Cmd + F 搜索 "Z字形变换"
     * 
     * 方法3：按英文名查找
     * - Ctrl/Cmd + F 搜索 "Zigzag Conversion"
     * 
     * 方法4：按标签查找
     * - 查看"按标签分类"部分
     * 
     * 方法5：按难度查找
     * - 查看"按难度分类"部分
     * 
     * 跳转到题解：
     * - Ctrl/Cmd + 点击类名（如 Z_convert）
     * - 或在方法内部点击类名跳转
     */
    
    /**
     * 维护建议：
     * 
     * 每次完成新题目后，在这里添加：
     * 1. 题目基本信息（编号、中英文名、难度）
     * 2. 核心思路简述
     * 3. 个人笔记和易错点
     * 4. 更新分类索引
     * 
     * 格式统一，便于后续查找和回顾
     */
    
    public static void main(String[] args) {
        System.out.println("===== LeetCode 题目索引 =====");
        System.out.println();
        System.out.println("使用方法：");
        System.out.println("1. 在此文件中按 Ctrl/Cmd + F 搜索题目名或编号");
        System.out.println("2. Ctrl/Cmd + 点击类名跳转到对应题解");
        System.out.println("3. 查看分类索引快速定位相关题目");
        System.out.println();
        System.out.println("当前已完成题目：27道");
        System.out.println("- 简单：11道");
        System.out.println("- 中等：14道");
        System.out.println("- 困难：2道");
        System.out.println();
        System.out.println("加油！继续刷题！💪");
    }
}

