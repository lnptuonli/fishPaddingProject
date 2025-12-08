/**
 * LeetCode 746. 使用最小花费爬楼梯 - Min Cost Climbing Stairs
 * 
 * 题目描述：
 * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。
 * 一旦你支付此费用，即可选择向上爬一个或者两个台阶。
 * 
 * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
 * 
 * 请你计算并返回达到楼梯顶部的最低花费。
 * 
 * 示例 1：
 * 输入：cost = [10,15,20]
 * 输出：15
 * 解释：你将从下标为 1 的台阶开始。
 * - 支付 15 ，向上爬两个台阶，到达楼梯顶部。
 * 总花费为 15 。
 * 
 * 示例 2：
 * 输入：cost = [1,100,1,1,1,100,1,1,100,1]
 * 输出：6
 * 解释：你将从下标为 0 的台阶开始。
 * - 支付 1 ，向上爬两个台阶，到达下标为 2 的台阶。
 * - 支付 1 ，向上爬两个台阶，到达下标为 4 的台阶。
 * - 支付 1 ，向上爬两个台阶，到达下标为 6 的台阶。
 * - 支付 1 ，向上爬一个台阶，到达下标为 7 的台阶。
 * - 支付 1 ，向上爬两个台阶，到达下标为 9 的台阶。
 * - 支付 1 ，向上爬一个台阶，到达楼梯顶部。
 * 总花费为 6 。
 * 
 * 提示：
 * - 2 <= cost.length <= 1000
 * - 0 <= cost[i] <= 999
 * 
 * 核心理解：
 * 
 * 1. 状态定义：
 *    - dp[i]：到达第 i 个台阶的最小花费
 *    - 注意：楼梯顶部在 cost.length，不是 cost.length - 1
 * 
 * 2. 状态转移方程：
 *    - 要到达第 i 个台阶，可以从哪里来？
 *      * 从第 i-1 个台阶爬 1 步，花费 cost[i-1]
 *      * 从第 i-2 个台阶爬 2 步，花费 cost[i-2]
 *    - 所以：dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])
 * 
 * 3. 初始条件：
 *    - dp[0] = 0（从第0个台阶开始，不需要花费）
 *    - dp[1] = 0（从第1个台阶开始，不需要花费）
 * 
 * 4. 计算顺序：
 *    - 从前往后：dp[2], dp[3], ..., dp[cost.length]
 * 
 * 5. 与爬楼梯的区别：
 *    - 爬楼梯：求方法数，用"相加"
 *    - 最小花费爬楼梯：求最小值，用"min"
 *    - 爬楼梯：不考虑每一步的代价
 *    - 最小花费爬楼梯：每一步有代价 cost[i]
 * 
 * 可视化过程（cost = [1,100,1,1,1,100,1,1,100,1]）：
 * 
 * 台阶：   [0]   [1]   [2]   [3]   [4]   [5]   [6]   [7]   [8]   [9]   [顶部]
 * cost:    1     100   1     1     1     100   1     1     100   1     --
 *          ↓     ↓     ↓     ↓     ↓     ↓     ↓     ↓     ↓     ↓     ↓
 * dp[0]:   0   (起点，免费)
 * dp[1]:   0   (起点，免费)
 * dp[2]:   1   (从0跳2步，花费1)
 * dp[3]:   2   (从2跳1步，花费1)
 * dp[4]:   2   (从2跳2步，花费1)
 * dp[5]:   3   (从4跳1步，花费1)
 * dp[6]:   3   (从4跳2步，花费1)
 * dp[7]:   4   (从6跳1步，花费1)
 * dp[8]:   5   (从7跳1步，花费1)
 * dp[9]:   6   (从8跳1步，花费1)
 * dp[10]:  6   (从9跳1步，花费1 或 从8跳2步，花费100，取min=6)
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
 * 1. 楼梯顶部的理解：
 *    - 楼梯顶部在 cost.length，不是 cost.length - 1
 *    - 数组长度为 n，则有 n+1 个状态（0到n）
 * 
 * 2. 初始条件：
 *    - dp[0] = 0 和 dp[1] = 0，不是 cost[0] 和 cost[1]
 *    - 因为可以从 0 或 1 开始，不需要支付起始台阶的费用
 * 
 * 3. 转移方程中的 cost：
 *    - dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])
 *    - 注意是 cost[i-1] 和 cost[i-2]，不是 cost[i]
 *    - 因为是"从 i-1 台阶爬到 i"需要支付 cost[i-1]
 * 
 * 4. 与爬楼梯70的区别：
 *    - 爬楼梯70：dp[i] = dp[i-1] + dp[i-2]（求和）
 *    - 最小花费：dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])（求最小）
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 70. 爬楼梯（基础版）
 * - LeetCode 91. 解码方法（变体）
 * - LeetCode 377. 组合总和 IV（完全背包变体）
 */
public class MinCostClimbingStairs {
    
    /**
     * 主方法：计算达到楼梯顶部的最低花费
     * 
     * @param cost 每个台阶向上爬的费用
     * @return 达到楼梯顶部的最低花费
     */
    public int minCostClimbingStairs(int[] cost) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：动态规划五步法
        // 
        // 第1步：定义状态
        // dp[i] 表示到达第 i 个台阶的最小花费
        // 注意：楼梯顶部在 cost.length，不是 cost.length - 1
        // 
        // 第2步：状态转移方程
        // dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])
        // 解释：
        // - dp[i-1] + cost[i-1]：从 i-1 爬1步到 i，需支付 cost[i-1]
        // - dp[i-2] + cost[i-2]：从 i-2 爬2步到 i，需支付 cost[i-2]
        // 
        // 第3步：初始条件
        // dp[0] = 0（从第0个台阶开始，免费）
        // dp[1] = 0（从第1个台阶开始，免费）
        // 
        // 第4步：计算顺序
        // 从前往后：dp[2], dp[3], ..., dp[cost.length]
        // 
        // 第5步：空间优化（可选）
        // 只需要两个变量：prev2 和 prev1
        
        // 方法1：动态规划（数组版）
        // int n = cost.length;
        // int[] dp = new int[n + 1];
        // dp[0] = 0;
        // dp[1] = 0;
        // for (int i = 2; i <= n; i++) {
        //     dp[i] = Math.min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2]);
        // }
        // return dp[n];
        
        // 方法2：动态规划（空间优化版）⭐推荐
        // int prev2 = 0;  // dp[i-2]
        // int prev1 = 0;  // dp[i-1]
        // for (int i = 2; i <= cost.length; i++) {
        //     int curr = Math.min(prev1 + cost[i-1], prev2 + cost[i-2]);
        //     prev2 = prev1;
        //     prev1 = curr;
        // }
        // return prev1;
        
        // 关键理解：
        // 1. 为什么是 min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])？
        //    - 这是一个"最优化"问题，要找最小花费
        //    - 从 i-1 到 i：需要支付 cost[i-1]（离开 i-1 的费用）
        //    - 从 i-2 到 i：需要支付 cost[i-2]（离开 i-2 的费用）
        //    - 取两者中的最小值
        // 
        // 2. 为什么 dp[0] = 0 和 dp[1] = 0？
        //    - 题目允许从 0 或 1 开始
        //    - 起始台阶不需要支付费用
        // 
        // 3. 这题与爬楼梯70的区别？
        //    - 爬楼梯70：dp[i] = dp[i-1] + dp[i-2]（求方法数，相加）
        //    - 最小花费：dp[i] = min(...)（求最优解，取最小）
        //    - 爬楼梯70：到达 cost.length - 1
        //    - 最小花费：到达 cost.length（顶部在数组之外）
        
        // 在这里编写你的实现代码
        
        //dp[0]:可以从下标0或者1开始，所以代价0
        //dp[1]:同理
        //dp[2]:要到达2，可以从cost[0]或者cost[1]选一个小的，但这实际上是dp[0]+cost[0]，以及dp[1]+cost[1]
        //后面的我认为类似，每一个级都迭代一遍，最终得到的应该是包含所有台阶最优解的数组
        int[] dp =new int[cost.length+1];
        if (cost.length==1)
            return 0;
        dp[0]=0;
        if (cost.length==2)
            return Math.min(dp[0]+cost[0],dp[1]+cost[1]);
        dp[1]=0;
        for(int i=2;i<cost.length+1;i++)
            dp[i]=Math.min(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2]);
        return dp[cost.length];

    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        MinCostClimbingStairs solution = new MinCostClimbingStairs();
        
        System.out.println("=== LeetCode 746: 使用最小花费爬楼梯 ===");
        System.out.println("简单题，爬楼梯变体\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] cost1 = {10, 15, 20};
        int result1 = solution.minCostClimbingStairs(cost1);
        System.out.println("输入: cost = " + java.util.Arrays.toString(cost1));
        System.out.println("输出: " + result1);
        System.out.println("预期: 15");
        System.out.println("解释: 从下标1开始，支付15，爬2步到顶部");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        int result2 = solution.minCostClimbingStairs(cost2);
        System.out.println("输入: cost = " + java.util.Arrays.toString(cost2));
        System.out.println("输出: " + result2);
        System.out.println("预期: 6");
        System.out.println("解释: 从下标0开始，每次跳过高花费的台阶");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：边界情况 - 只有两个台阶
        System.out.println("测试用例3: 边界情况 - 只有两个台阶");
        int[] cost3 = {1, 100};
        int result3 = solution.minCostClimbingStairs(cost3);
        System.out.println("输入: cost = " + java.util.Arrays.toString(cost3));
        System.out.println("输出: " + result3);
        System.out.println("预期: 1");
        System.out.println("解释: 从下标0开始，支付1，爬2步到顶部");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：所有台阶花费相同
        System.out.println("测试用例4: 所有台阶花费相同");
        int[] cost4 = {5, 5, 5, 5, 5};
        int result4 = solution.minCostClimbingStairs(cost4);
        System.out.println("输入: cost = " + java.util.Arrays.toString(cost4));
        System.out.println("输出: " + result4);
        System.out.println("预期: 10");
        System.out.println("解释: 0->2->4->顶部，花费5+5=10");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：递增花费
        System.out.println("测试用例5: 递增花费");
        int[] cost5 = {1, 2, 3, 4, 5};
        int result5 = solution.minCostClimbingStairs(cost5);
        System.out.println("输入: cost = " + java.util.Arrays.toString(cost5));
        System.out.println("输出: " + result5);
        System.out.println("预期: 6");
        System.out.println("解释: 0->2->4->顶部，花费1+3+2=6 或 1->3->顶部，花费2+4=6");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i] = 到达第i个台阶的最小花费");
        System.out.println("2. 状态转移：dp[i] = min(dp[i-1]+cost[i-1], dp[i-2]+cost[i-2])");
        System.out.println("3. 初始条件：dp[0] = 0, dp[1] = 0（起点免费）");
        System.out.println("4. 关键区别：顶部在 cost.length，不是 cost.length-1");
        System.out.println("5. 与爬楼梯的区别：求最小值（min）而非方法数（sum）");
        System.out.println("\n这是爬楼梯的经典变体，理解转移方程的变化！");
    }
}



