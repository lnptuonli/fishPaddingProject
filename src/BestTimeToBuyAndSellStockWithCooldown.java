/**
 * LeetCode 309. 最佳买卖股票时机含冷冻期 - Best Time to Buy and Sell Stock with Cooldown
 * 
 * 题目描述：
 * 给定一个整数数组 prices，其中第 prices[i] 表示第 i 天的股票价格 。
 * 
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * 
 * - 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 
 * 示例 1:
 * 输入: prices = [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 * 
 * 示例 2:
 * 输入: prices = [1]
 * 输出: 0
 * 
 * 提示：
 * - 1 <= prices.length <= 5000
 * - 0 <= prices[i] <= 1000
 * 
 * 核心理解：
 * 
 * 1. 状态机DP的定义：
 *    - 每一天可能处于不同的状态
 *    - 状态之间可以相互转换
 *    - 用DP记录每个状态下的最大利润
 * 
 * 2. 状态定义（关键！）：
 *    第 i 天可能处于以下3种状态：
 *    - 状态0（持有股票）：dp[i][0]
 *      - 今天买入
 *      - 之前就持有
 *    
 *    - 状态1（不持有股票，非冷冻期）：dp[i][1]
 *      - 今天什么都不做
 *      - 冷冻期结束
 *    
 *    - 状态2（不持有股票，冷冻期）：dp[i][2]
 *      - 今天卖出
 * 
 * 3. 状态转移方程：
 *    ```
 *    dp[i][0] = max(dp[i-1][0], dp[i-1][1] - prices[i])
 *    解释：今天持有 = 昨天就持有 或 今天买入（昨天不持有且非冷冻期）
 *    
 *    dp[i][1] = max(dp[i-1][1], dp[i-1][2])
 *    解释：今天不持有非冷冻 = 昨天就不持有非冷冻 或 昨天是冷冻期
 *    
 *    dp[i][2] = dp[i-1][0] + prices[i]
 *    解释：今天冷冻期 = 昨天持有今天卖出
 *    ```
 * 
 * 4. 初始条件：
 *    - dp[0][0] = -prices[0]（第0天买入）
 *    - dp[0][1] = 0（第0天不持有）
 *    - dp[0][2] = 0（第0天不可能卖出）
 * 
 * 5. 最终答案：
 *    - max(dp[n-1][1], dp[n-1][2])
 *    - 最后一天不持有股票的最大利润
 * 
 * 状态转换图：
 * 
 * ```
 *     买入(-price)
 *  ┌─────────────┐
 *  │             │
 *  │             ↓
 * [不持有] ←─ [持有]
 *  非冷冻      │
 *  ↑  ↑        │
 *  │  │        │ 卖出(+price)
 *  │  └────────┘
 *  │           ↓
 *  └──────── [冷冻期]
 *   冷冻期结束
 * ```
 * 
 * 可视化过程（prices = [1,2,3,0,2]）：
 * 
 * 天数  价格  持有   不持有非冷冻  冷冻期
 *  0     1    -1        0          0
 *  1     2    -1        0          1
 *  2     3    -1        1          2
 *  3     0    -1        2          2
 *  4     2     1        2          3
 * 
 * 最终答案：max(2, 3) = 3
 * 
 * 解题思路：
 * 
 * 方法：状态机DP ⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)，可优化到 O(1)
 * 
 * 易错点：
 * 
 * 1. 状态定义不清：
 *    - 一定要明确每个状态的含义
 *    - 画出状态转换图
 * 
 * 2. 状态转移错误：
 *    - 买入时，只能从"不持有非冷冻期"转换
 *    - 不能从"冷冻期"直接买入
 * 
 * 3. 初始化错误：
 *    - dp[0][0] = -prices[0]，不是 0
 *    - dp[0][2] = 0，第0天不可能卖出
 * 
 * 4. 最终答案：
 *    - 不是 dp[n-1][2]，而是 max(dp[n-1][1], dp[n-1][2])
 *    - 因为最后一天可能不在冷冻期
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 121. 买卖股票的最佳时机（只能交易一次）
 * - LeetCode 122. 买卖股票的最佳时机 II（无限次交易）
 * - LeetCode 123. 买卖股票的最佳时机 III（最多2次交易）
 * - LeetCode 188. 买卖股票的最佳时机 IV（最多k次交易）
 * - LeetCode 714. 买卖股票的最佳时机含手续费（有手续费）
 */
public class BestTimeToBuyAndSellStockWithCooldown {
    
    /**
     * 主方法：计算最大利润
     * 
     * @param prices 股票价格数组
     * @return 最大利润
     */
    public int maxProfit(int[] prices) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：状态机DP
        // 
        // 第1步：定义状态
        // dp[i][0]：第 i 天持有股票的最大利润
        // dp[i][1]：第 i 天不持有股票（非冷冻期）的最大利润
        // dp[i][2]：第 i 天不持有股票（冷冻期）的最大利润
        // 
        // 第2步：状态转移方程
        // dp[i][0] = max(dp[i-1][0], dp[i-1][1] - prices[i])
        // dp[i][1] = max(dp[i-1][1], dp[i-1][2])
        // dp[i][2] = dp[i-1][0] + prices[i]
        // 
        // 第3步：初始条件
        // dp[0][0] = -prices[0]
        // dp[0][1] = 0
        // dp[0][2] = 0
        // 
        // 第4步：最终答案
        // max(dp[n-1][1], dp[n-1][2])
        
        // 参考实现：
        // int n = prices.length;
        // if (n == 1) return 0;
        // 
        // int[][] dp = new int[n][3];
        // dp[0][0] = -prices[0];  // 第0天买入
        // dp[0][1] = 0;           // 第0天不持有
        // dp[0][2] = 0;           // 第0天不可能卖出
        // 
        // for (int i = 1; i < n; i++) {
        //     dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] - prices[i]);
        //     dp[i][1] = Math.max(dp[i-1][1], dp[i-1][2]);
        //     dp[i][2] = dp[i-1][0] + prices[i];
        // }
        // 
        // return Math.max(dp[n-1][1], dp[n-1][2]);
        
        // 空间优化版本（O(1)）：
        // int hold = -prices[0];      // 持有
        // int notHold = 0;            // 不持有非冷冻
        // int cooldown = 0;           // 冷冻期
        // 
        // for (int i = 1; i < prices.length; i++) {
        //     int newHold = Math.max(hold, notHold - prices[i]);
        //     int newNotHold = Math.max(notHold, cooldown);
        //     int newCooldown = hold + prices[i];
        //     
        //     hold = newHold;
        //     notHold = newNotHold;
        //     cooldown = newCooldown;
        // }
        // 
        // return Math.max(notHold, cooldown);
        
        // 关键理解：
        // 1. 为什么买入时只能从 dp[i-1][1] 转换？
        //    - 因为有冷冻期限制
        //    - 卖出后第二天不能买入
        //    - 只能从"不持有非冷冻期"买入
        // 
        // 2. 为什么 dp[i][1] = max(dp[i-1][1], dp[i-1][2])？
        //    - 今天不持有非冷冻期
        //    - 可能昨天就不持有非冷冻期
        //    - 可能昨天是冷冻期，今天冷冻期结束
        // 
        // 3. 为什么 dp[i][2] = dp[i-1][0] + prices[i]？
        //    - 今天是冷冻期，说明今天刚卖出
        //    - 昨天必须持有股票
        //    - 今天卖出，获得 prices[i]
        // 
        // 4. 状态机DP的核心：
        //    - 明确定义每个状态
        //    - 画出状态转换图
        //    - 根据转换关系写出状态转移方程
        
        // 在这里编写你的实现代码
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // ============================================
        // TODO: 实现结束
        // ============================================
        
        return 0;  // 记得返回结果
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        BestTimeToBuyAndSellStockWithCooldown solution = new BestTimeToBuyAndSellStockWithCooldown();
        
        System.out.println("=== LeetCode 309: 最佳买卖股票时机含冷冻期 ===");
        System.out.println("中等题，状态机DP\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] prices1 = {1, 2, 3, 0, 2};
        int result1 = solution.maxProfit(prices1);
        System.out.println("输入: prices = " + java.util.Arrays.toString(prices1));
        System.out.println("输出: " + result1);
        System.out.println("预期: 3");
        System.out.println("解释: [买入, 卖出, 冷冻期, 买入, 卖出]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] prices2 = {1};
        int result2 = solution.maxProfit(prices2);
        System.out.println("输入: prices = " + java.util.Arrays.toString(prices2));
        System.out.println("输出: " + result2);
        System.out.println("预期: 0");
        System.out.println("解释: 只有一天，无法交易");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：递增
        System.out.println("测试用例3: 递增");
        int[] prices3 = {1, 2, 3, 4, 5};
        int result3 = solution.maxProfit(prices3);
        System.out.println("输入: prices = " + java.util.Arrays.toString(prices3));
        System.out.println("输出: " + result3);
        System.out.println("预期: 4");
        System.out.println("解释: 第1天买入，第5天卖出");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：递减
        System.out.println("测试用例4: 递减");
        int[] prices4 = {5, 4, 3, 2, 1};
        int result4 = solution.maxProfit(prices4);
        System.out.println("输入: prices = " + java.util.Arrays.toString(prices4));
        System.out.println("输出: " + result4);
        System.out.println("预期: 0");
        System.out.println("解释: 价格递减，不交易");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：多次交易
        System.out.println("测试用例5: 多次交易");
        int[] prices5 = {1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
        int result5 = solution.maxProfit(prices5);
        System.out.println("输入: prices = " + java.util.Arrays.toString(prices5));
        System.out.println("输出: " + result5);
        System.out.println("预期: 13");
        System.out.println("解释: 多次买卖，考虑冷冻期");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：持有、不持有非冷冻、冷冻期");
        System.out.println("2. 状态转移：根据状态转换图推导");
        System.out.println("3. 买入限制：只能从不持有非冷冻期买入");
        System.out.println("4. 最终答案：max(不持有非冷冻, 冷冻期)");
        System.out.println("5. 状态机DP的核心：明确状态，画出转换图");
        System.out.println("\n这是状态机DP的经典题，务必掌握状态定义和转换！");
    }
}



