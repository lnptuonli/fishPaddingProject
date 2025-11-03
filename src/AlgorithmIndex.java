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
     * 
     * 中等题目列表：
     * - [2] 两数相加
     * - [3] 无重复字符的最长子串
     * - [5] 最长回文子串
     * - [6] Z字形变换
     * - [8] 字符串转换整数(atoi)
     * - [11] 盛最多水的容器
     * - [12] 整数转罗马数字
     * 
     * 困难题目列表：
     * (暂无)
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
     * 
     * 数组专题：
     * - [1] 两数之和
     * - [11] 盛最多水的容器
     * 
     * 链表专题：
     * - [2] 两数相加
     * 
     * 数学专题：
     * - [7] 整数反转
     * - [12] 整数转罗马数字
     * 
     * 双指针专题：
     * - [11] 盛最多水的容器
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
        System.out.println("当前已完成题目：9道");
        System.out.println("- 简单：2道");
        System.out.println("- 中等：7道");
        System.out.println("- 困难：0道");
        System.out.println();
        System.out.println("加油！继续刷题！💪");
    }
}

