/**
 * LeetCode 11. 盛最多水的容器 - Container With Most Water
 * 
 * 题目描述：
 * 给定一个长度为 n 的整数数组 height。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i])。
 * 
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 
 * 说明：你不能倾斜容器。
 * 
 * 图解示例：
 * 输入：height = [1,8,6,2,5,4,8,3,7]
 * 
 *     |
 *   8 |   *       *
 *   7 |   *       *   *
 *   6 |   * *     *   *
 *   5 |   * *   * *   *
 *   4 |   * * * * * * *
 *   3 |   * * * * * * * *
 *   2 |   * * * * * * * *
 *   1 | * * * * * * * * *
 *   0 +-------------------
 *     0 1 2 3 4 5 6 7 8
 * 
 * 输出：49
 * 解释：选择索引1(高度8)和索引8(高度7)，面积 = (8-1) * min(8,7) = 7 * 7 = 49
 * 
 * 示例 1：
 * 输入：height = [1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。
 *      在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * 
 * 示例 2：
 * 输入：height = [1,1]
 * 输出：1
 * 解释：只有两条线，面积 = (1-0) * min(1,1) = 1
 * 
 * 示例 3：
 * 输入：height = [4,3,2,1,4]
 * 输出：16
 * 解释：选择索引0(高度4)和索引4(高度4)，面积 = (4-0) * min(4,4) = 4 * 4 = 16
 * 
 * 示例 4：
 * 输入：height = [1,2,1]
 * 输出：2
 * 解释：选择索引0(高度1)和索引1(高度2)，面积 = (1-0) * min(1,2) = 1 * 1 = 1
 *      选择索引1(高度2)和索引2(高度1)，面积 = (2-1) * min(2,1) = 1 * 1 = 1
 *      选择索引0(高度1)和索引2(高度1)，面积 = (2-0) * min(1,1) = 2 * 1 = 2 ✓
 * 
 * 提示：
 * n == height.length
 * 2 <= n <= 10^5
 * 0 <= height[i] <= 10^4
 * 
 * 核心理解：
 * 1. 容器面积 = 两条线之间的距离 × 两条线中较短的那条
 *    公式：area = (right - left) × min(height[left], height[right])
 * 
 * 2. 为什么不能倾斜？
 *    如果倾斜容器，水会从较短的一边流出
 * 
 * 3. 关键洞察：
 *    - 如果移动较高的线，面积一定不会变大（宽度减小，高度不变或变小）
 *    - 如果移动较矮的线，面积可能会变大（宽度减小，但高度可能变大）
 * 
 * 解题思路：
 * 
 * 思路1：暴力法（不推荐）
 * - 双层循环枚举所有可能的组合
 * - 时间复杂度：O(n^2)
 * - 空间复杂度：O(1)
 * - 会超时！
 * 
 * 思路2：双指针法（推荐⭐⭐⭐）
 * - 左右两个指针分别指向数组两端
 * - 每次移动较矮的那一边
 * - 为什么这样是对的？
 *   * 假设 height[left] < height[right]
 *   * 如果我们移动 right，宽度减小，高度受限于 height[left]（更小），面积一定变小
 *   * 所以应该移动 left，虽然宽度减小，但高度可能增大
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(1)
 * 
 * 思路3：优化的双指针（扩展思考）
 * - 在双指针基础上，跳过比当前矮的线（因为它们不可能产生更大面积）
 * - 实际效果提升有限，但展示了优化思维
 * 
 * 易错点：
 * 1. 面积计算：是两线之间的距离，不是索引差+1
 * 2. 高度选择：必须是较矮的那条线（木桶效应）
 * 3. 移动策略：移动较矮的线，不是较高的
 * 4. 边界条件：数组长度为2的情况
 */
public class ContainerWithMostWater {
    
    /**
     * 主方法：计算盛最多水的容器
     * 
     * @param height 垂线高度数组
     * @return 最大容纳水量
     */
    public int maxArea(int[] height) {
        // ============================================
        
        // 提示：双指针法
        // 1. 初始化左右指针：left = 0, right = height.length - 1
        // 2. 初始化最大面积：maxArea = 0
        // 3. 当 left < right 时循环：
        //    a) 计算当前面积：area = (right - left) * Math.min(height[left], height[right])
        //    b) 更新最大面积：maxArea = Math.max(maxArea, area)
        //    c) 移动较矮的指针：
        //       - 如果 height[left] < height[right]，left++
        //       - 否则，right--
        // 4. 返回 maxArea
        
        // 关键理解：
        // 为什么移动较矮的指针？
        // - 假设 height[left] < height[right]
        // - 此时高度受限于 height[left]
        // - 如果移动 right，宽度↓，高度不变或↓，面积一定↓
        // - 如果移动 left，宽度↓，但高度可能↑，面积可能↑
        
        // 在这里编写你的实现代码
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            // 计算当前面积（可以调用辅助方法，也可以直接计算）
            int currentArea = (right - left) * Math.min(height[left], height[right]);
            
            // 更新最大面积
            maxArea = Math.max(maxArea, currentArea);
            
            // 移动较矮的指针（无论面积是否变大，都要继续移动）
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
        // ============================================

    }
    
    /**
     * 辅助方法：计算两条线之间的面积
     * 注：实际上这个方法可以省略，直接在主方法中计算即可
     */
    public int area(int left, int right, int[] height) {
        return (right - left) * Math.min(height[left], height[right]);
    }
    
    /**
     * 辅助方法：可视化容器
     * 
     * @param height 高度数组
     * @param left 左边界索引
     * @param right 右边界索引
     */
    private void visualizeContainer(int[] height, int left, int right) {
        int maxHeight = 0;
        for (int h : height) {
            maxHeight = Math.max(maxHeight, h);
        }
        
        System.out.println("\n容器可视化 (left=" + left + ", right=" + right + "):");
        for (int level = maxHeight; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < height.length; i++) {
                if (i == left || i == right) {
                    // 边界线用 # 表示
                    System.out.print(height[i] >= level ? " # " : "   ");
                } else if (i > left && i < right) {
                    // 容器内部：水用 ~ 表示，空气用空格
                    int waterLevel = Math.min(height[left], height[right]);
                    if (level <= waterLevel) {
                        System.out.print(" ~ ");
                    } else if (height[i] >= level) {
                        System.out.print(" | ");
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    // 容器外部的线
                    System.out.print(height[i] >= level ? " | " : "   ");
                }
            }
            System.out.println();
        }
        System.out.print("   +");
        for (int i = 0; i < height.length; i++) {
            System.out.print("---");
        }
        System.out.println();
        System.out.print("    ");
        for (int i = 0; i < height.length; i++) {
            System.out.printf(" %d ", i);
        }
        System.out.println();
        
        int area = (right - left) * Math.min(height[left], height[right]);
        System.out.println("面积 = (" + right + " - " + left + ") × min(" 
                         + height[left] + ", " + height[right] + ") = " + area);
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();
        
        System.out.println("=== LeetCode 11: 盛最多水的容器 ===");
        System.out.println("公式：面积 = 宽度 × 高度 = (right - left) × min(height[left], height[right])\n");
        
        // 测试用例1：官方示例
        int[] test1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int result1 = solution.maxArea(test1);
        System.out.println("测试用例1:");
        System.out.println("输入: [1,8,6,2,5,4,8,3,7]");
        System.out.println("输出: " + result1);
        System.out.println("预期: 49");
        System.out.println("通过: " + (result1 == 49));
        if (result1 == 49) {
            solution.visualizeContainer(test1, 1, 8);
        }
        System.out.println();
        
        // 测试用例2：最小情况
        int[] test2 = {1, 1};
        int result2 = solution.maxArea(test2);
        System.out.println("测试用例2:");
        System.out.println("输入: [1,1]");
        System.out.println("输出: " + result2);
        System.out.println("预期: 1");
        System.out.println("通过: " + (result2 == 1));
        System.out.println();
        
        // 测试用例3：两端最高
        int[] test3 = {4, 3, 2, 1, 4};
        int result3 = solution.maxArea(test3);
        System.out.println("测试用例3:");
        System.out.println("输入: [4,3,2,1,4]");
        System.out.println("输出: " + result3);
        System.out.println("预期: 16");
        System.out.println("通过: " + (result3 == 16));
        if (result3 == 16) {
            solution.visualizeContainer(test3, 0, 4);
        }
        System.out.println();
        
        // 测试用例4：需要仔细比较
        int[] test4 = {1, 2, 1};
        int result4 = solution.maxArea(test4);
        System.out.println("测试用例4:");
        System.out.println("输入: [1,2,1]");
        System.out.println("输出: " + result4);
        System.out.println("预期: 2");
        System.out.println("通过: " + (result4 == 2));
        System.out.println();
        
        // 测试用例5：递增序列
        int[] test5 = {1, 2, 3, 4, 5};
        int result5 = solution.maxArea(test5);
        System.out.println("测试用例5:");
        System.out.println("输入: [1,2,3,4,5]");
        System.out.println("输出: " + result5);
        System.out.println("预期: 6");
        System.out.println("说明: (4-1) × min(2,5) = 3 × 2 = 6");
        System.out.println("通过: " + (result5 == 6));
        System.out.println();
        
        // 测试用例6：递减序列
        int[] test6 = {5, 4, 3, 2, 1};
        int result6 = solution.maxArea(test6);
        System.out.println("测试用例6:");
        System.out.println("输入: [5,4,3,2,1]");
        System.out.println("输出: " + result6);
        System.out.println("预期: 6");
        System.out.println("说明: (3-0) × min(5,2) = 3 × 2 = 6");
        System.out.println("通过: " + (result6 == 6));
        System.out.println();
        
        // 测试用例7：相同高度
        int[] test7 = {5, 5, 5, 5, 5};
        int result7 = solution.maxArea(test7);
        System.out.println("测试用例7:");
        System.out.println("输入: [5,5,5,5,5]");
        System.out.println("输出: " + result7);
        System.out.println("预期: 20");
        System.out.println("说明: (4-0) × min(5,5) = 4 × 5 = 20");
        System.out.println("通过: " + (result7 == 20));
        System.out.println();
        
        // 测试用例8：中间有高峰
        int[] test8 = {2, 3, 10, 5, 7, 8, 9};
        int result8 = solution.maxArea(test8);
        System.out.println("测试用例8:");
        System.out.println("输入: [2,3,10,5,7,8,9]");
        System.out.println("输出: " + result8);
        System.out.println("预期: 36");
        System.out.println("说明: (6-2) × min(10,9) = 4 × 9 = 36");
        System.out.println("通过: " + (result8 == 36));
        System.out.println();
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 双指针法：从两端向中间移动");
        System.out.println("2. 移动策略：始终移动较矮的那一边");
        System.out.println("3. 面积公式：宽度 × 较矮的高度");
        System.out.println("4. 时间复杂度：O(n) - 只遍历一次");
        System.out.println("5. 贪心思想：移动较矮边才有可能增大面积");
    }
}

