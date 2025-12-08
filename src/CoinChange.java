/**
 * LeetCode 322. 零钱兑换 - Coin Change
 * 
 * 题目描述：
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * 
 * 计算并返回可以凑成总金额所需的 **最少的硬币个数** 。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1 。
 * 
 * 你可以认为每种硬币的数量是无限的。
 * 
 * 示例 1：
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 * 
 * 示例 2：
 * 输入：coins = [2], amount = 3
 * 输出：-1
 * 解释：无法用面额为2的硬币凑成3
 * 
 * 示例 3：
 * 输入：coins = [1], amount = 0
 * 输出：0
 * 解释：金额为0，不需要硬币
 * 
 * 提示：
 * - 1 <= coins.length <= 12
 * - 1 <= coins[i] <= 2^31 - 1
 * - 0 <= amount <= 10^4
 * 
 * 核心理解：
 * 
 * 1. 这是一个完全背包问题：
 *    - 背包容量：amount
 *    - 物品：coins（每种硬币可以无限使用）
 *    - 目标：求最少硬币个数
 * 
 * 2. 状态定义：
 *    - dp[i]：凑成金额 i 所需的最少硬币个数
 * 
 * 3. 状态转移方程：
 *    - 对于金额 i，可以选择任意一种硬币 coin
 *    - 如果选择 coin，则 dp[i] = dp[i - coin] + 1
 *    - 遍历所有硬币，取最小值：dp[i] = min(dp[i], dp[i - coin] + 1)
 * 
 * 4. 初始条件：
 *    - dp[0] = 0（金额为0，不需要硬币）
 *    - dp[i] = amount + 1 或 Integer.MAX_VALUE（表示不可达，初始化为一个大值）
 * 
 * 5. 最终答案：
 *    - 如果 dp[amount] > amount，说明无法凑成，返回 -1
 *    - 否则返回 dp[amount]
 * 
 * 可视化过程（coins = [1, 2, 5], amount = 11）：
 * 
 * 金额:     0   1   2   3   4   5   6   7   8   9  10  11
 *           ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓
 * dp[i]:    0   1   1   2   2   1   2   2   3   3   2   3
 * 
 * 决策过程：
 * - dp[0] = 0（初始条件）
 * - dp[1] = min(dp[1-1]+1) = 1（用1个硬币1）
 * - dp[2] = min(dp[2-1]+1, dp[2-2]+1) = 1（用1个硬币2）
 * - dp[3] = min(dp[3-1]+1, dp[3-2]+1) = 2（用1+2）
 * - dp[4] = min(dp[4-1]+1, dp[4-2]+1) = 2（用2+2）
 * - dp[5] = min(dp[5-1]+1, dp[5-2]+1, dp[5-5]+1) = 1（用1个硬币5）
 * - ...
 * - dp[11] = min(dp[11-1]+1, dp[11-2]+1, dp[11-5]+1) = 3（用5+5+1）
 * 
 * 最优方案：11 = 5 + 5 + 1，共3个硬币
 * 
 * 解题思路：
 * 
 * 方法1：动态规划（完全背包）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(amount * n)，n 为硬币种类数
 * - 空间复杂度：O(amount)
 * 
 * 方法2：BFS（广度优先搜索）
 * - 时间复杂度：O(amount * n)
 * - 空间复杂度：O(amount)
 * - 可以理解为从0开始，每次加一个硬币，找到最短路径
 * 
 * 易错点：
 * 
 * 1. 初始化的值：
 *    - dp[i] 不能初始化为 Integer.MAX_VALUE
 *    - 因为 Integer.MAX_VALUE + 1 会溢出
 *    - 应该初始化为 amount + 1（一个不可能达到的值）
 * 
 * 2. 循环顺序：
 *    - 完全背包：内层循环从小到大
 *    - 01背包：内层循环从大到小
 *    - 本题是完全背包，从小到大
 * 
 * 3. 无法凑成的判断：
 *    - 如果 dp[amount] > amount，说明无法凑成
 *    - 返回 -1
 * 
 * 4. 边界情况：
 *    - amount = 0 时，返回 0
 *    - coins 为空时，amount > 0 返回 -1
 * 
 * 完全背包 vs 01背包：
 * 
 * | 特性 | 完全背包 | 01背包 |
 * |------|---------|--------|
 * | 物品使用次数 | 无限次 | 最多1次 |
 * | 循环顺序 | 从小到大 | 从大到小 |
 * | 状态转移 | dp[i] = min(dp[i], dp[i-coin]+1) | dp[i][j] = max(dp[i-1][j], dp[i-1][j-w]+v) |
 * | 空间优化 | 一维数组 | 一维数组（倒序） |
 * 
 * 为什么完全背包从小到大？
 * - 因为每个物品可以用多次
 * - 从小到大遍历时，dp[i-coin] 已经更新过，包含了当前硬币
 * - 这样就实现了"可以重复使用"的效果
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 518. 零钱兑换 II（求方案数，完全背包）
 * - LeetCode 377. 组合总和 IV（求排列数，完全背包）
 * - LeetCode 416. 分割等和子集（01背包）
 * - LeetCode 474. 一和零（二维背包）
 */
public class CoinChange {
    
    /**
     * 主方法：计算凑成总金额所需的最少硬币个数
     * 
     * @param coins 硬币面额数组
     * @param amount 总金额
     * @return 最少硬币个数，无法凑成返回 -1
     */
    public int coinChange(int[] coins, int amount) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：完全背包问题
        // 
        // 第1步：定义状态
        // dp[i] 表示凑成金额 i 所需的最少硬币个数
        // 
        // 第2步：状态转移方程
        // dp[i] = min(dp[i], dp[i - coin] + 1)
        // 解释：
        // - 对于金额 i，可以选择任意一种硬币 coin
        // - 如果选择 coin，则需要 dp[i - coin] + 1 个硬币
        // - 遍历所有硬币，取最小值
        // 
        // 第3步：初始条件
        // dp[0] = 0（金额为0，不需要硬币）
        // dp[i] = amount + 1（初始化为一个不可能的大值）
        // 
        // 第4步：计算顺序
        // 外层循环：金额从 1 到 amount
        // 内层循环：遍历所有硬币
        // 
        // 第5步：最终答案
        // 如果 dp[amount] > amount，返回 -1
        // 否则返回 dp[amount]
        
        // 参考实现：
        // if (amount == 0) return 0;
        // 
        // int[] dp = new int[amount + 1];
        // 
        // // 初始化：dp[0] = 0，其他为 amount + 1
        // for (int i = 1; i <= amount; i++) {
        //     dp[i] = amount + 1;
        // }
        // 
        // // 状态转移
        // for (int i = 1; i <= amount; i++) {
        //     for (int coin : coins) {
        //         if (i >= coin) {
        //             dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        //         }
        //     }
        // }
        // 
        // // 判断是否能凑成
        // return dp[amount] > amount ? -1 : dp[amount];
        
        // 关键理解：
        // 1. 为什么是 min(dp[i], dp[i - coin] + 1)？
        //    - 对于金额 i，可以选择任意一种硬币
        //    - 选择 coin 后，还需要凑 i - coin
        //    - dp[i - coin] 表示凑 i - coin 的最少硬币数
        //    - 加上当前这个硬币，就是 dp[i - coin] + 1
        //    - 遍历所有硬币，取最小值
        // 
        // 2. 为什么初始化为 amount + 1？
        //    - 表示"不可能凑成"
        //    - 因为最多用 amount 个硬币1就能凑成
        //    - amount + 1 是一个不可能达到的值
        //    - 不能用 Integer.MAX_VALUE，因为 +1 会溢出
        // 
        // 3. 为什么是完全背包？
        //    - 每种硬币可以用无限次
        //    - 从小到大遍历，dp[i - coin] 已经包含了当前硬币
        // 
        // 4. 这题与爬楼梯的区别？
        //    - 爬楼梯：求方法数，用"相加"
        //    - 零钱兑换：求最少个数，用"min"
        
        // 在这里编写你的实现代码

        //首先我觉得这更像是贪心算法问题，而不是动态规划，每次拿余额减去面值最大的零钱，直到余额为0为止
        //但是某人为我指出了这并不一定正确，为我举了一个反例：{1, 3, 4}，要凑 6
        //我的直觉认为贪心可行，是因为我使用的货币体系，自然保证了贪心是可行的，但对于算法问题 并不一定奏效。
        //那么，考虑状态转移方程
        //dp[i]表示在余额i下，达到此余额需要的最少硬币数量，但是dp[i]与dp[i-1]，我不清楚有没有什么必然联系，比方说6，需要两个面值为3的硬币，但是对于余额7
        //可能一个面值为7的硬币就能满足。。。
        //那么这样说，dp[i]的上一个状态，并不一定是dp[i-1]，而是dp[i-所有面值，这个面值包括0]，所以：
        int[] dp=new int[amount+1];
        dp[0]=0;//初始化，金额0需要0枚硬币
        for(int i=1;i<=amount;i++){
            //对于每一个状态i，遍历所有可能的货币面值作为上一状态，最后取最小的作为当前状态
            int tempmin=-1;

            for(int j=0;j<coins.length;j++){
                if(i-coins[j]<0)//遍历的面值大于当前金额
                    continue;
                if(i-coins[j]==0)//遍历的面值等于当前金额，即找到刚刚好的，那么本金额的最小硬币就是1枚，跳出循环
                {
                    tempmin=1;
                    break;
                }
                //以下属于遍历的金额小于当前金额,但还需要考虑dp[i-coins[j]]这个状态不成立的情况
                if(dp[i-coins[j]]==-1){
                    continue;
                }
                int tempnum=1+dp[i-coins[j]];
                if(tempmin>tempnum||tempmin==-1)//要么是发现了更小的，要么是初始化最小值
                    tempmin=tempnum;
            }
            dp[i]=tempmin;//遍历结束，给dp[i]赋值
        }

        return dp[amount];  // 记得返回结果



        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // ============================================
        // TODO: 实现结束
        // ============================================

    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        CoinChange solution = new CoinChange();
        
        System.out.println("=== LeetCode 322: 零钱兑换 ===");
        System.out.println("中等题，完全背包入门\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] coins1 = {1, 2, 5};
        int amount1 = 11;
        int result1 = solution.coinChange(coins1, amount1);
        System.out.println("输入: coins = " + java.util.Arrays.toString(coins1) + ", amount = " + amount1);
        System.out.println("输出: " + result1);
        System.out.println("预期: 3");
        System.out.println("解释: 11 = 5 + 5 + 1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] coins2 = {2};
        int amount2 = 3;
        int result2 = solution.coinChange(coins2, amount2);
        System.out.println("输入: coins = " + java.util.Arrays.toString(coins2) + ", amount = " + amount2);
        System.out.println("输出: " + result2);
        System.out.println("预期: -1");
        System.out.println("解释: 无法用面额为2的硬币凑成3");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        int[] coins3 = {1};
        int amount3 = 0;
        int result3 = solution.coinChange(coins3, amount3);
        System.out.println("输入: coins = " + java.util.Arrays.toString(coins3) + ", amount = " + amount3);
        System.out.println("输出: " + result3);
        System.out.println("预期: 0");
        System.out.println("解释: 金额为0，不需要硬币");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：只有一种硬币
        System.out.println("测试用例4: 只有一种硬币");
        int[] coins4 = {1};
        int amount4 = 2;
        int result4 = solution.coinChange(coins4, amount4);
        System.out.println("输入: coins = " + java.util.Arrays.toString(coins4) + ", amount = " + amount4);
        System.out.println("输出: " + result4);
        System.out.println("预期: 2");
        System.out.println("解释: 2 = 1 + 1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：大面额硬币
        System.out.println("测试用例5: 大面额硬币");
        int[] coins5 = {1, 5, 10, 25};
        int amount5 = 41;
        int result5 = solution.coinChange(coins5, amount5);
        System.out.println("输入: coins = " + java.util.Arrays.toString(coins5) + ", amount = " + amount5);
        System.out.println("输出: " + result5);
        System.out.println("预期: 4");
        System.out.println("解释: 41 = 25 + 10 + 5 + 1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例6：贪心不是最优
        System.out.println("测试用例6: 贪心不是最优");
        int[] coins6 = {1, 3, 4};
        int amount6 = 6;
        int result6 = solution.coinChange(coins6, amount6);
        System.out.println("输入: coins = " + java.util.Arrays.toString(coins6) + ", amount = " + amount6);
        System.out.println("输出: " + result6);
        System.out.println("预期: 2");
        System.out.println("解释: 6 = 3 + 3（贪心会选4+1+1=3个，但DP找到最优2个）");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i] = 凑成金额 i 的最少硬币个数");
        System.out.println("2. 状态转移：dp[i] = min(dp[i], dp[i-coin] + 1)");
        System.out.println("3. 初始条件：dp[0] = 0, dp[i] = amount + 1");
        System.out.println("4. 完全背包：每种硬币可以用无限次");
        System.out.println("5. 循环顺序：从小到大（与01背包不同）");
        System.out.println("\n这是完全背包的经典入门题，理解完全背包的核心！");
    }
}



