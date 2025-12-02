/**
 * LeetCode 518. 零钱兑换 II - Coin Change II
 * 
 * 题目描述：
 * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
 * 
 * 请你计算并返回可以凑成总金额的 **硬币组合数** 。如果任何硬币组合都无法凑出总金额，返回 0 。
 * 
 * 假设每一种面额的硬币有无限个。
 * 
 * 题目数据保证结果符合 32 位带符号整数。
 * 
 * 示例 1：
 * 输入：amount = 5, coins = [1, 2, 5]
 * 输出：4
 * 解释：有四种方式可以凑成总金额：
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 * 
 * 示例 2：
 * 输入：amount = 3, coins = [2]
 * 输出：0
 * 解释：只用面额 2 的硬币不能凑成总金额 3 。
 * 
 * 示例 3：
 * 输入：amount = 10, coins = [10]
 * 输出：1
 * 
 * 提示：
 * - 1 <= coins.length <= 300
 * - 1 <= coins[i] <= 5000
 * - coins 中的所有值 互不相同
 * - 0 <= amount <= 5000
 * 
 * 核心理解：
 * 
 * 1. 与 LeetCode 322 的区别：
 *    - 322：求最少硬币个数（最优化问题，用 min）
 *    - 518：求组合数（计数问题，用 相加）
 * 
 * 2. 组合 vs 排列：
 *    - 组合：[1,2] 和 [2,1] 算同一种
 *    - 排列：[1,2] 和 [2,1] 算两种
 *    - 本题求组合数！
 * 
 * 3. 状态定义：
 *    - dp[i]：凑成金额 i 的组合数
 * 
 * 4. 状态转移方程：
 *    - dp[i] = dp[i] + dp[i - coin]
 *    - 含义：使用当前硬币 coin，凑成 i 的方案数 = 不用这个硬币的方案数 + 用这个硬币的方案数
 * 
 * 5. 初始条件：
 *    - dp[0] = 1（凑成0的方案数为1：不选任何硬币）
 * 
 * 6. 循环顺序（关键！）：
 *    - 求组合数：外层遍历硬币，内层遍历金额（正序）
 *    - 求排列数：外层遍历金额，内层遍历硬币
 * 
 * 可视化过程（coins = [1, 2, 5], amount = 5）：
 * 
 * 初始：dp = [1, 0, 0, 0, 0, 0]
 * 
 * 处理硬币1：
 * dp = [1, 1, 1, 1, 1, 1]
 * 解释：用硬币1可以凑成任何金额，每个金额都有1种方案
 * 
 * 处理硬币2：
 * dp[2] = dp[2] + dp[0] = 1 + 1 = 2  (1+1, 2)
 * dp[3] = dp[3] + dp[1] = 1 + 1 = 2  (1+1+1, 2+1)
 * dp[4] = dp[4] + dp[2] = 1 + 2 = 3  (1+1+1+1, 2+1+1, 2+2)
 * dp[5] = dp[5] + dp[3] = 1 + 2 = 3  (1+1+1+1+1, 2+1+1+1, 2+2+1)
 * dp = [1, 1, 2, 2, 3, 3]
 * 
 * 处理硬币5：
 * dp[5] = dp[5] + dp[0] = 3 + 1 = 4  (新增：5)
 * dp = [1, 1, 2, 2, 3, 4]
 * 
 * 最终答案：4
 * 
 * 为什么外层遍历硬币？
 * 
 * - 外层遍历硬币，保证了对于每个金额，硬币是按顺序考虑的
 * - 例如：先考虑硬币1，再考虑硬币2
 * - 这样就不会出现 [1,2] 和 [2,1] 都被计数的情况
 * - 因为考虑硬币2时，已经默认硬币1在前面了
 * 
 * 如果内外层交换会怎样？
 * 
 * - 外层遍历金额，内层遍历硬币
 * - 对于金额5，会分别考虑：先用1再用2，先用2再用1
 * - 结果会计算排列数，而非组合数
 * - 例如：[1,2,2] 和 [2,1,2] 会被算成两种
 * 
 * 解题思路：
 * 
 * 方法：完全背包（求组合数）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(amount * n)，n 为硬币种类数
 * - 空间复杂度：O(amount)
 * 
 * 易错点：
 * 
 * 1. 循环顺序错误：
 *    - 求组合数：外层硬币，内层金额
 *    - 求排列数：外层金额，内层硬币
 *    - 本题求组合数，必须外层硬币！
 * 
 * 2. 状态转移错误：
 *    - 不是 dp[i] = min(...)，而是 dp[i] += dp[i - coin]
 *    - 计数问题用"相加"，最优化问题用"min/max"
 * 
 * 3. 初始化错误：
 *    - dp[0] = 1，不是 0
 *    - 因为凑成0的方案数为1（不选任何硬币）
 * 
 * 4. 与 LeetCode 377 的区别：
 *    - 518：组合数，外层硬币
 *    - 377：排列数，外层金额
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 322. 零钱兑换（求最少个数，完全背包）
 * - LeetCode 377. 组合总和 IV（求排列数，完全背包）
 * - LeetCode 416. 分割等和子集（01背包）
 * - LeetCode 494. 目标和（01背包变形）
 */
public class CoinChangeII {
    
    /**
     * 主方法：计算凑成总金额的硬币组合数
     * 
     * @param amount 总金额
     * @param coins 硬币面额数组
     * @return 硬币组合数
     */
    public int change(int amount, int[] coins) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：完全背包（求组合数）
        // 
        // 第1步：定义状态
        // dp[i] 表示凑成金额 i 的组合数
        // 
        // 第2步：状态转移方程
        // dp[i] = dp[i] + dp[i - coin]
        // 解释：
        // - 对于金额 i，可以选择任意一种硬币 coin
        // - 如果选择 coin，则方案数为 dp[i - coin]
        // - 遍历所有硬币，累加方案数
        // 
        // 第3步：初始条件
        // dp[0] = 1（凑成0的方案数为1：不选任何硬币）
        // 
        // 第4步：计算顺序（关键！）
        // 外层循环：遍历每种硬币
        // 内层循环：金额从 coin 到 amount（正序）
        // 
        // 第5步：最终答案
        // dp[amount]
        
        // 参考实现：
        // int[] dp = new int[amount + 1];
        // dp[0] = 1;  // 初始条件
        // 
        // // 外层遍历硬币（求组合数的关键！）
        // for (int coin : coins) {
        //     // 内层遍历金额（完全背包，正序）
        //     for (int i = coin; i <= amount; i++) {
        //         dp[i] += dp[i - coin];
        //     }
        // }
        // 
        // return dp[amount];
        
        // 关键理解：
        // 1. 为什么是 dp[i] += dp[i - coin]？
        //    - 这是计数问题，不是最优化问题
        //    - 使用硬币 coin 凑成 i 的方案数 = dp[i - coin]
        //    - 遍历所有硬币，累加所有方案数
        // 
        // 2. 为什么外层遍历硬币？
        //    - 求组合数，不求排列数
        //    - 外层遍历硬币，保证硬币按顺序考虑
        //    - 避免 [1,2] 和 [2,1] 都被计数
        // 
        // 3. 为什么是完全背包？
        //    - 每种硬币可以用无限次
        //    - 内层循环正序遍历
        // 
        // 4. 这题与零钱兑换的区别？
        //    - 零钱兑换：求最少个数，用 min
        //    - 零钱兑换II：求组合数，用 +=
        
        // 在这里编写你的实现代码
        //写写推导过程：
        //假设dp[i]为达到余额i的方法数量
        //以目标余额5，面值【1,2,5】为例
        //dp[0]=1；
        //dp[1]=dp[0]
        //dp[2]=dp[0]+dp[1]
        //dp[3]=dp[3-3](废弃)+dp[3-2]+dp[3-1]
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for (int coin : coins) {          // 先枚举硬币
            for (int i = coin; i <= amount; i++) {  // 再枚举金额（从当前金额以上开始枚举）
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        CoinChangeII solution = new CoinChangeII();
        
        System.out.println("=== LeetCode 518: 零钱兑换 II ===");
        System.out.println("中等题，完全背包求组合数\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int amount1 = 5;
        int[] coins1 = {1, 2, 5};
        int result1 = solution.change(amount1, coins1);
        System.out.println("输入: amount = " + amount1 + ", coins = " + java.util.Arrays.toString(coins1));
        System.out.println("输出: " + result1);
        System.out.println("预期: 4");
        System.out.println("解释: 5=5, 5=2+2+1, 5=2+1+1+1, 5=1+1+1+1+1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int amount2 = 3;
        int[] coins2 = {2};
        int result2 = solution.change(amount2, coins2);
        System.out.println("输入: amount = " + amount2 + ", coins = " + java.util.Arrays.toString(coins2));
        System.out.println("输出: " + result2);
        System.out.println("预期: 0");
        System.out.println("解释: 只用面额2的硬币不能凑成总金额3");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        int amount3 = 10;
        int[] coins3 = {10};
        int result3 = solution.change(amount3, coins3);
        System.out.println("输入: amount = " + amount3 + ", coins = " + java.util.Arrays.toString(coins3));
        System.out.println("输出: " + result3);
        System.out.println("预期: 1");
        System.out.println("解释: 只有一种方案：10=10");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：amount = 0
        System.out.println("测试用例4: amount = 0");
        int amount4 = 0;
        int[] coins4 = {1, 2, 5};
        int result4 = solution.change(amount4, coins4);
        System.out.println("输入: amount = " + amount4 + ", coins = " + java.util.Arrays.toString(coins4));
        System.out.println("输出: " + result4);
        System.out.println("预期: 1");
        System.out.println("解释: 不选任何硬币，方案数为1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：较大金额
        System.out.println("测试用例5: 较大金额");
        int amount5 = 10;
        int[] coins5 = {1, 2, 5};
        int result5 = solution.change(amount5, coins5);
        System.out.println("输入: amount = " + amount5 + ", coins = " + java.util.Arrays.toString(coins5));
        System.out.println("输出: " + result5);
        System.out.println("预期: 10");
        System.out.println("解释: 有10种组合方式");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i] = 凑成金额 i 的组合数");
        System.out.println("2. 状态转移：dp[i] += dp[i - coin]（计数问题用相加）");
        System.out.println("3. 初始条件：dp[0] = 1");
        System.out.println("4. 循环顺序：外层硬币，内层金额（求组合数的关键！）");
        System.out.println("5. 与322的区别：求组合数 vs 求最少个数");
        System.out.println("\n组合 vs 排列的循环顺序是DP中的重要考点！");
    }
}

