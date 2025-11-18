/**
 * LeetCode 70. 爬楼梯 - Climbing Stairs
 * 
 * 题目描述：
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 
 * 示例 1：
 * 输入：n = 2
 * 输出：2
 * 解释：有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 * 
 * 示例 2：
 * 输入：n = 3
 * 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 * 
 * 示例 3：
 * 输入：n = 5
 * 输出：8
 * 解释：有八种方法可以爬到楼顶。
 * 
 * 提示：
 * - 1 <= n <= 45
 * 
 * 核心理解：
 * 
 * 1. 状态定义：
 *    - dp[i]：爬到第 i 阶楼梯的方法数
 * 
 * 2. 状态转移方程：
 *    - 要到达第 i 阶，可以从哪里来？
 *      * 从第 i-1 阶爬 1 步
 *      * 从第 i-2 阶爬 2 步
 *    - 所以：dp[i] = dp[i-1] + dp[i-2]
 * 
 * 3. 初始条件：
 *    - dp[0] = 1（0阶有1种方法：不爬）
 *    - dp[1] = 1（1阶有1种方法：爬1步）
 * 
 * 4. 计算顺序：
 *    - 从前往后：dp[2], dp[3], ..., dp[n]
 * 
 * 5. 空间优化：
 *    - 观察：dp[i] 只依赖 dp[i-1] 和 dp[i-2]
 *    - 可以用两个变量代替整个数组
 *    - 空间复杂度：O(n) → O(1)
 * 
 * 可视化过程（n=5）：
 * 
 * dp[0]=1 → dp[1]=1 → dp[2]=2 → dp[3]=3 → dp[4]=5 → dp[5]=8
 *    ↓         ↓         ↓         ↓         ↓         ↓
 *  (不爬)   (爬1步)   (1+1或2)  (1+1+1,  (1+1+1+1, (1+1+1+1+1,
 *                              1+2,     1+1+2,    1+1+1+2,
 *                              2+1)     1+2+1,    1+1+2+1,
 *                                       2+1+1,    1+2+1+1,
 *                                       2+2)      2+1+1+1,
 *                                                 1+2+2,
 *                                                 2+1+2,
 *                                                 2+2+1)
 * 
 * 解题思路：
 * 
 * 方法1：动态规划（数组版）
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)
 * 
 * 方法2：动态规划（空间优化版）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(1)
 * 
 * 易错点：
 * 
 * 1. 初始条件的理解：
 *    - dp[0] = 1 还是 0？
 *    - 答案：dp[0] = 1，因为"不爬"也是一种方法
 * 
 * 2. 边界情况：
 *    - n = 0 或 n = 1 要特殊处理
 * 
 * 3. 数组越界：
 *    - 确保 dp 数组大小至少为 n+1
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 509. 斐波那契数（完全相同的递推关系）
 * - LeetCode 746. 使用最小花费爬楼梯（状态转移稍有变化）
 * - LeetCode 1137. 第 N 个泰波那契数（三项递推）
 */
public class ClimbingStairs {
    
    /**
     * 主方法：计算爬到第 n 阶楼梯的方法数
     * 
     * @param n 楼梯的阶数
     * @return 爬到楼顶的方法数
     */
    public int climbStairs(int n) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：动态规划五步法
        // 
        // 第1步：定义状态
        // dp[i] 表示爬到第 i 阶楼梯的方法数
        // 
        // 第2步：状态转移方程
        // dp[i] = dp[i-1] + dp[i-2]
        // 
        // 第3步：初始条件
        // dp[0] = 1
        // dp[1] = 1
        // 
        // 第4步：计算顺序
        // 从前往后：dp[2], dp[3], ..., dp[n]
        // 
        // 第5步：空间优化（可选）
        // 只需要两个变量：prev2 和 prev1
        
        // 方法1：动态规划（数组版）
        // if (n <= 1) return 1;
        // int[] dp = new int[n + 1];
        // dp[0] = 1;
        // dp[1] = 1;
        // for (int i = 2; i <= n; i++) {
        //     dp[i] = dp[i-1] + dp[i-2];
        // }
        // return dp[n];
        
        // 方法2：动态规划（空间优化版）⭐推荐
        // if (n <= 1) return 1;
        // int prev2 = 1;  // dp[i-2]
        // int prev1 = 1;  // dp[i-1]
        // for (int i = 2; i <= n; i++) {
        //     int curr = prev1 + prev2;
        //     prev2 = prev1;
        //     prev1 = curr;
        // }
        // return prev1;
        
        // 关键理解：
        // 1. 为什么是 dp[i] = dp[i-1] + dp[i-2]？
        //    - 到达第 i 阶有两种方式：
        //      * 从第 i-1 阶爬1步
        //      * 从第 i-2 阶爬2步
        //    - 这两种方式互不重叠，所以相加
        // 
        // 2. 为什么 dp[0] = 1？
        //    - 0阶代表"不爬"，也是一种"方法"
        //    - 这样定义能让递推公式统一
        // 
        // 3. 如何空间优化？
        //    - 观察：dp[i] 只依赖 dp[i-1] 和 dp[i-2]
        //    - 只需要两个变量滚动更新即可
        
        // 在这里编写你的实现代码
            if (n <= 1) return 1;
            int k = 2;  // 原题：一次爬1或2个台阶
            int[] dp = new int[n + 1];
            dp[0] = 1;

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= Math.min(i, k); j++) {
                    dp[i] += dp[i - j];
                }
            }

            return dp[n];

        // ============================================
        // ============================================

    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        ClimbingStairs solution = new ClimbingStairs();
        
        System.out.println("=== LeetCode 70: 爬楼梯 ===");
        System.out.println("简单题，DP入门经典\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int n1 = 2;
        int result1 = solution.climbStairs(n1);
        System.out.println("输入: n = " + n1);
        System.out.println("输出: " + result1);
        System.out.println("预期: 2");
        System.out.println("解释: 1+1 或 2");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int n2 = 3;
        int result2 = solution.climbStairs(n2);
        System.out.println("输入: n = " + n2);
        System.out.println("输出: " + result2);
        System.out.println("预期: 3");
        System.out.println("解释: 1+1+1, 1+2, 2+1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        int n3 = 5;
        int result3 = solution.climbStairs(n3);
        System.out.println("输入: n = " + n3);
        System.out.println("输出: " + result3);
        System.out.println("预期: 8");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：边界情况
        System.out.println("测试用例4: 边界情况");
        int n4 = 1;
        int result4 = solution.climbStairs(n4);
        System.out.println("输入: n = " + n4);
        System.out.println("输出: " + result4);
        System.out.println("预期: 1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：较大的值
        System.out.println("测试用例5: 较大的值");
        int n5 = 10;
        int result5 = solution.climbStairs(n5);
        System.out.println("输入: n = " + n5);
        System.out.println("输出: " + result5);
        System.out.println("预期: 89");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i] = 爬到第i阶的方法数");
        System.out.println("2. 状态转移：dp[i] = dp[i-1] + dp[i-2]");
        System.out.println("3. 初始条件：dp[0] = 1, dp[1] = 1");
        System.out.println("4. 计算顺序：从前往后");
        System.out.println("5. 空间优化：只需两个变量");
        System.out.println("\n这是DP的入门经典题，务必掌握！");
    }
}

