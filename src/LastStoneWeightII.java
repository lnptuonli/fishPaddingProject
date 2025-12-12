/**
 * LeetCode 1049. 最后一块石头的重量 II - Last Stone Weight II
 * 
 * 题目描述：
 * 有一堆石头，用整数数组 stones 表示。其中 stones[i] 表示第 i 块石头的重量。
 * 
 * 每一回合，从中选出 **任意两块石头** ，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。
 * 那么粉碎的可能结果如下：
 * 
 * - 如果 x == y，那么两块石头都会被完全粉碎；
 * - 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
 * 
 * 最后，**最多只会剩下一块** 石头。返回此石头 **最小的可能重量** 。如果没有石头剩下，就返回 0。
 * 
 * 示例 1：
 * 输入：stones = [2,7,4,1,8,1]
 * 输出：1
 * 解释：
 * 组合 2 和 4，得到 2，所以数组转化为 [2,7,1,8,1]，
 * 组合 7 和 8，得到 1，所以数组转化为 [2,1,1,1]，
 * 组合 2 和 1，得到 1，所以数组转化为 [1,1,1]，
 * 组合 1 和 1，得到 0，所以数组转化为 [1]，这就是最优值。
 * 
 * 示例 2：
 * 输入：stones = [31,26,33,21,40]
 * 输出：5
 * 
 * 示例 3：
 * 输入：stones = [1,2]
 * 输出：1
 * 
 * 提示：
 * - 1 <= stones.length <= 30
 * - 1 <= stones[i] <= 100
 * 
 * 核心理解：
 * 
 * 1. 问题转化（关键！）：
 *    - 每次粉碎两块石头，相当于给它们分别添加 '+' 和 '-'
 *    - 最终结果 = |sum(正号石头) - sum(负号石头)|
 *    - 假设正号石头和为 P，负号石头和为 N
 *    - 则有：P + N = sum（sum 为所有石头重量之和）
 *    - 最终结果 = |P - N| = |P - (sum - P)| = |2P - sum|
 *    - 要使结果最小，P 应该尽可能接近 sum / 2
 *    - 问题转化为：从石头中选出一些，使得它们的和尽可能接近 sum / 2，但不超过 sum / 2
 *    - 这就是一个 01背包问题！
 * 
 * 2. 数学推导：
 *    ```
 *    设正号石头和为 P，负号石头和为 N
 *    P + N = sum
 *    最终结果 = |P - N|
 *    
 *    因为 N = sum - P
 *    所以：|P - N| = |P - (sum - P)| = |2P - sum|
 *    
 *    要使 |2P - sum| 最小：
 *    - 如果 P = sum / 2，则结果为 0（理想情况）
 *    - P 应该尽可能接近 sum / 2，但不超过 sum / 2
 *    ```
 * 
 * 3. 状态定义：
 *    - dp[j]：能否凑出和为 j
 *    - 或者：dp[j] 表示不超过 j 的最大和
 * 
 * 4. 状态转移方程：
 *    - dp[j] = max(dp[j], dp[j - stone] + stone)
 *    - 含义：不选当前石头 vs 选当前石头
 * 
 * 5. 初始条件：
 *    - dp[0] = 0（和为0）
 * 
 * 6. 最终答案：
 *    - 找到最大的 P <= sum / 2，使得 dp[P] = true（或 dp[P] 最大）
 *    - 答案 = sum - 2 * P
 * 
 * 可视化过程（stones = [2,7,4,1,8,1]）：
 * 
 * sum = 23, target = 23 / 2 = 11
 * 问题转化为：从 [2,7,4,1,8,1] 中选出一些石头，使得和尽可能接近11但不超过11
 * 
 * 使用 boolean[] dp：
 * 初始：dp[0] = true
 * 
 * 处理石头2：dp[2] = true
 * 处理石头7：dp[7] = true, dp[9] = true
 * 处理石头4：dp[4] = true, dp[6] = true, dp[11] = true, ...
 * ...
 * 
 * 最终找到最大的 P = 11，使得 dp[11] = true
 * 答案 = 23 - 2 * 11 = 1
 * 
 * 解题思路：
 * 
 * 方法1：01背包（boolean数组）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n * sum)
 * - 空间复杂度：O(sum)
 * 
 * 方法2：01背包（int数组，记录最大和）
 * - 时间复杂度：O(n * sum)
 * - 空间复杂度：O(sum)
 * 
 * 易错点：
 * 
 * 1. 问题转化：
 *    - 一定要先转化为01背包问题
 *    - 不要直接模拟粉碎过程（会超时）
 * 
 * 2. 目标值：
 *    - 不是凑成 sum / 2，而是尽可能接近 sum / 2 但不超过
 *    - 需要找到最大的 P <= sum / 2
 * 
 * 3. 最终答案：
 *    - 不是 P，而是 sum - 2 * P
 *    - 因为 |P - N| = |2P - sum| = sum - 2P（当 P <= sum / 2 时）
 * 
 * 4. 循环顺序：
 *    - 01背包，必须倒序遍历
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 416. 分割等和子集（01背包，判断能否凑成 sum / 2）
 * - LeetCode 494. 目标和（01背包，求方案数）
 * - LeetCode 1046. 最后一块石头的重量（贪心 + 堆）
 */
public class LastStoneWeightII {
    
    /**
     * 主方法：计算最后一块石头的最小可能重量
     * 
     * @param stones 石头重量数组
     * @return 最小可能重量
     */
    public int lastStoneWeightII(int[] stones) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：01背包问题
        // 
        // 第1步：问题转化
        // 将石头分成两堆，一堆加 '+'，一堆加 '-'
        // 设正号石头和为 P，负号石头和为 N
        // P + N = sum
        // 最终结果 = |P - N| = |2P - sum|
        // 要使结果最小，P 应该尽可能接近 sum / 2
        // 
        // 第2步：计算总和
        // int sum = 0;
        // for (int stone : stones) {
        //     sum += stone;
        // }
        // 
        // 第3步：定义状态
        // int target = sum / 2;
        // dp[j] 表示能否凑出和为 j（或不超过 j 的最大和）
        // 
        // 第4步：状态转移方程
        // 方法1（boolean数组）：
        // dp[j] = dp[j] || dp[j - stone]
        // 
        // 方法2（int数组）：
        // dp[j] = max(dp[j], dp[j - stone] + stone)
        // 
        // 第5步：初始条件
        // dp[0] = true（或 dp[0] = 0）
        // 
        // 第6步：计算顺序
        // 外层循环：遍历每块石头
        // 内层循环：从 target 到 stone（倒序！01背包）
        // 
        // 第7步：最终答案
        // 找到最大的 P <= target，使得 dp[P] = true
        // 答案 = sum - 2 * P
        
        // 参考实现（方法1：boolean数组）：
        // int sum = 0;
        // for (int stone : stones) {
        //     sum += stone;
        // }
        // 
        // int target = sum / 2;
        // boolean[] dp = new boolean[target + 1];
        // dp[0] = true;  // 初始条件
        // 
        // // 01背包：倒序遍历
        // for (int stone : stones) {
        //     for (int j = target; j >= stone; j--) {
        //         dp[j] = dp[j] || dp[j - stone];
        //     }
        // }
        // 
        // // 找到最大的 P <= target，使得 dp[P] = true
        // int P = 0;
        // for (int i = target; i >= 0; i--) {
        //     if (dp[i]) {
        //         P = i;
        //         break;
        //     }
        // }
        // 
        // return sum - 2 * P;
        
        // 参考实现（方法2：int数组）：
        // int sum = 0;
        // for (int stone : stones) {
        //     sum += stone;
        // }
        // 
        // int target = sum / 2;
        // int[] dp = new int[target + 1];
        // 
        // // 01背包：倒序遍历
        // for (int stone : stones) {
        //     for (int j = target; j >= stone; j--) {
        //         dp[j] = Math.max(dp[j], dp[j - stone] + stone);
        //     }
        // }
        // 
        // // dp[target] 就是不超过 target 的最大和
        // return sum - 2 * dp[target];
        
        // 关键理解：
        // 1. 为什么要转化为01背包？
        //    - 粉碎过程相当于给石头分配 '+' 或 '-'
        //    - 最终结果 = |sum(正) - sum(负)|
        //    - 要使结果最小，两堆重量应该尽可能接近
        // 
        // 2. 为什么 P 要尽可能接近 sum / 2？
        //    - |P - N| = |2P - sum|
        //    - 当 P = sum / 2 时，结果为 0（最小）
        //    - P 越接近 sum / 2，结果越小
        // 
        // 3. 为什么答案是 sum - 2 * P？
        //    - |P - N| = |P - (sum - P)| = |2P - sum|
        //    - 因为 P <= sum / 2，所以 2P <= sum
        //    - 所以 |2P - sum| = sum - 2P
        // 
        // 4. 这题与分割等和子集的区别？
        //    - 416：判断能否恰好凑成 sum / 2
        //    - 1049：找到最接近 sum / 2 的和，求最小差值
        
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
        LastStoneWeightII solution = new LastStoneWeightII();
        
        System.out.println("=== LeetCode 1049: 最后一块石头的重量 II ===");
        System.out.println("中等题，01背包变形\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] stones1 = {2, 7, 4, 1, 8, 1};
        int result1 = solution.lastStoneWeightII(stones1);
        System.out.println("输入: stones = " + java.util.Arrays.toString(stones1));
        System.out.println("输出: " + result1);
        System.out.println("预期: 1");
        System.out.println("解释: sum=23, P=11, 23-2*11=1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] stones2 = {31, 26, 33, 21, 40};
        int result2 = solution.lastStoneWeightII(stones2);
        System.out.println("输入: stones = " + java.util.Arrays.toString(stones2));
        System.out.println("输出: " + result2);
        System.out.println("预期: 5");
        System.out.println("解释: sum=151, P=73, 151-2*73=5");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        int[] stones3 = {1, 2};
        int result3 = solution.lastStoneWeightII(stones3);
        System.out.println("输入: stones = " + java.util.Arrays.toString(stones3));
        System.out.println("输出: " + result3);
        System.out.println("预期: 1");
        System.out.println("解释: sum=3, P=1, 3-2*1=1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：所有石头重量相同
        System.out.println("测试用例4: 所有石头重量相同");
        int[] stones4 = {1, 1, 1, 1};
        int result4 = solution.lastStoneWeightII(stones4);
        System.out.println("输入: stones = " + java.util.Arrays.toString(stones4));
        System.out.println("输出: " + result4);
        System.out.println("预期: 0");
        System.out.println("解释: sum=4, P=2, 4-2*2=0");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：单块石头
        System.out.println("测试用例5: 单块石头");
        int[] stones5 = {100};
        int result5 = solution.lastStoneWeightII(stones5);
        System.out.println("输入: stones = " + java.util.Arrays.toString(stones5));
        System.out.println("输出: " + result5);
        System.out.println("预期: 100");
        System.out.println("解释: 只有一块石头，直接返回");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 问题转化：|P - N| = |2P - sum| → P 尽可能接近 sum/2");
        System.out.println("2. 状态定义：dp[j] = 能否凑出和为 j（或不超过 j 的最大和）");
        System.out.println("3. 状态转移：dp[j] = dp[j] || dp[j - stone]");
        System.out.println("4. 最终答案：sum - 2 * P（P 是不超过 sum/2 的最大和）");
        System.out.println("5. 循环顺序：01背包，倒序遍历");
        System.out.println("\n问题转化是关键！将粉碎问题转化为01背包问题！");
    }
}



