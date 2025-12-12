/**
 * LeetCode 494. 目标和 - Target Sum
 * 
 * 题目描述：
 * 给你一个整数数组 nums 和一个整数 target 。
 * 
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 **表达式** ：
 * 
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，
 * 然后串联起来得到表达式 "+2-1" 。
 * 
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 **表达式的数目** 。
 * 
 * 示例 1：
 * 输入：nums = [1,1,1,1,1], target = 3
 * 输出：5
 * 解释：一共有 5 种方法让最终目标和为 3 。
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 * 
 * 示例 2：
 * 输入：nums = [1], target = 1
 * 输出：1
 * 
 * 提示：
 * - 1 <= nums.length <= 20
 * - 0 <= nums[i] <= 1000
 * - 0 <= sum(nums[i]) <= 1000
 * - -1000 <= target <= 1000
 * 
 * 核心理解：
 * 
 * 1. 问题转化（关键！）：
 *    - 假设添加 '+' 的数字和为 P，添加 '-' 的数字和为 N
 *    - 则有：P - N = target
 *    - 又有：P + N = sum（sum 为数组总和）
 *    - 联立方程：P = (sum + target) / 2
 *    - 问题转化为：从数组中选出一些数，使得它们的和等于 (sum + target) / 2
 *    - 这就是一个 01背包问题！
 * 
 * 2. 数学推导：
 *    ```
 *    P - N = target  ... (1)
 *    P + N = sum     ... (2)
 *    
 *    (1) + (2): 2P = sum + target
 *    所以: P = (sum + target) / 2
 *    ```
 * 
 * 3. 状态定义：
 *    - dp[j]：凑成和为 j 的方案数
 * 
 * 4. 状态转移方程：
 *    - dp[j] = dp[j] + dp[j - num]
 *    - 含义：不选 num 的方案数 + 选 num 的方案数
 * 
 * 5. 初始条件：
 *    - dp[0] = 1（凑成0的方案数为1）
 * 
 * 6. 循环顺序：
 *    - 01背包：外层遍历数字，内层倒序遍历和（从大到小）
 * 
 * 可视化过程（nums = [1,1,1,1,1], target = 3）：
 * 
 * sum = 5, target = 3
 * P = (5 + 3) / 2 = 4
 * 问题转化为：从 [1,1,1,1,1] 中选出一些数，使得和为 4
 * 
 * 初始：dp = [1, 0, 0, 0, 0]
 * 
 * 处理第1个1：
 * dp[1] = dp[1] + dp[0] = 0 + 1 = 1
 * dp = [1, 1, 0, 0, 0]
 * 
 * 处理第2个1：
 * dp[2] = dp[2] + dp[1] = 0 + 1 = 1
 * dp[1] = dp[1] + dp[0] = 1 + 1 = 2
 * dp = [1, 2, 1, 0, 0]
 * 
 * 处理第3个1：
 * dp[3] = dp[3] + dp[2] = 0 + 1 = 1
 * dp[2] = dp[2] + dp[1] = 1 + 2 = 3
 * dp[1] = dp[1] + dp[0] = 2 + 1 = 3
 * dp = [1, 3, 3, 1, 0]
 * 
 * 处理第4个1：
 * dp[4] = dp[4] + dp[3] = 0 + 1 = 1
 * dp[3] = dp[3] + dp[2] = 1 + 3 = 4
 * dp[2] = dp[2] + dp[1] = 3 + 3 = 6
 * dp[1] = dp[1] + dp[0] = 3 + 1 = 4
 * dp = [1, 4, 6, 4, 1]
 * 
 * 处理第5个1：
 * dp[4] = dp[4] + dp[3] = 1 + 4 = 5
 * dp = [1, 5, 10, 10, 5]
 * 
 * 最终答案：dp[4] = 5
 * 
 * 解题思路：
 * 
 * 方法：01背包（求方案数）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n * P)，P = (sum + target) / 2
 * - 空间复杂度：O(P)
 * 
 * 易错点：
 * 
 * 1. 边界条件判断：
 *    - 如果 sum < target，无解，返回 0
 *    - 如果 (sum + target) 为奇数，无解，返回 0
 *    - 如果 target 的绝对值 > sum，无解，返回 0
 * 
 * 2. 循环顺序：
 *    - 01背包，必须倒序遍历
 *    - 从 P 到 num，不是从 num 到 P
 * 
 * 3. 初始化：
 *    - dp[0] = 1，不是 0
 *    - 特殊情况：如果数组中有0，需要特殊处理
 * 
 * 4. 问题转化：
 *    - 一定要先转化为01背包问题
 *    - 不要直接暴力搜索（会超时）
 * 
 * 5. 数组中有0的情况：
 *    - 如果有 k 个 0，最终答案要乘以 2^k
 *    - 因为每个 0 可以选 + 或 -，都不影响结果
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 416. 分割等和子集（01背包，判断可行性）
 * - LeetCode 1049. 最后一块石头的重量 II（01背包变形）
 * - LeetCode 698. 划分为k个相等的子集（回溯）
 */
public class TargetSum {
    
    /**
     * 主方法：计算构造目标和的表达式数目
     * 
     * @param nums 整数数组
     * @param target 目标和
     * @return 表达式数目
     */
    public int findTargetSumWays(int[] nums, int target) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：01背包（求方案数）
        // 
        // 第1步：问题转化
        // 假设添加 '+' 的数字和为 P，添加 '-' 的数字和为 N
        // P - N = target
        // P + N = sum
        // 所以：P = (sum + target) / 2
        // 问题转化为：从数组中选出一些数，使得和为 P
        // 
        // 第2步：边界判断
        // if (sum < target) return 0;
        // if ((sum + target) % 2 != 0) return 0;
        // 
        // 第3步：定义状态
        // int P = (sum + target) / 2;
        // dp[j] 表示凑成和为 j 的方案数
        // 
        // 第4步：状态转移方程
        // dp[j] = dp[j] + dp[j - num]
        // 解释：
        // - 不选 num：dp[j] 保持不变
        // - 选 num：dp[j] += dp[j - num]
        // 
        // 第5步：初始条件
        // dp[0] = 1（凑成0的方案数为1）
        // 
        // 第6步：计算顺序
        // 外层循环：遍历每个数
        // 内层循环：从 P 到 num（倒序！01背包）
        // 
        // 第7步：最终答案
        // dp[P]
        
        // 参考实现：
        // int sum = 0;
        // for (int num : nums) {
        //     sum += num;
        // }
        // // 边界判断
        // if (sum < Math.abs(target) || (sum + target) % 2 != 0) {
        //     return 0;
        // }
        // 
        // int P = (sum + target) / 2;
        // int[] dp = new int[P + 1];
        // dp[0] = 1;  // 初始条件
        // 
        // // 01背包：倒序遍历
        // for (int num : nums) {
        //     for (int j = P; j >= num; j--) {
        //         dp[j] += dp[j - num];
        //     }
        // }
        // 
        // return dp[P];
        
        // 关键理解：
        // 1. 为什么要转化为01背包？
        //    - 直接暴力搜索：2^n，会超时
        //    - 转化为01背包：O(n * P)，可以通过
        // 
        // 2. 为什么 P = (sum + target) / 2？
        //    - P - N = target
        //    - P + N = sum
        //    - 联立方程得到
        // 
        // 3. 为什么要倒序遍历？
        //    - 01背包，每个数只能用一次
        //    - 倒序保证 dp[j - num] 是"上一轮"的值
        // 
        // 4. 这题与分割等和子集的区别？
        //    - 416：判断可行性，dp[j] 是 boolean
        //    - 494：求方案数，dp[j] 是 int
        
        // 在这里编写你的实现代码
        int sum = 0;
        for (int num : nums) {//求和
            sum += num;
        }

        // 边界判断
        if (sum < Math.abs(target) || (sum + target) % 2 != 0) {
            return 0;
        }

        int P = (sum + target) / 2;
        int[] dp = new int[P + 1];
        dp[0] = 1;  // 初始条件

        // 01背包：倒序遍历
        for (int num : nums) {
            for (int j = P; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }

        return dp[P];

    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        TargetSum solution = new TargetSum();
        
        System.out.println("=== LeetCode 494: 目标和 ===");
        System.out.println("中等题，01背包变形\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] nums1 = {1, 1, 1, 1, 1};
        int target1 = 3;
        int result1 = solution.findTargetSumWays(nums1, target1);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums1) + ", target = " + target1);
        System.out.println("输出: " + result1);
        System.out.println("预期: 5");
        System.out.println("解释: 有5种方法让最终目标和为3");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] nums2 = {1};
        int target2 = 1;
        int result2 = solution.findTargetSumWays(nums2, target2);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums2) + ", target = " + target2);
        System.out.println("输出: " + result2);
        System.out.println("预期: 1");
        System.out.println("解释: 只有一种方法：+1 = 1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：target = 0
        System.out.println("测试用例3: target = 0");
        int[] nums3 = {1, 1};
        int target3 = 0;
        int result3 = solution.findTargetSumWays(nums3, target3);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums3) + ", target = " + target3);
        System.out.println("输出: " + result3);
        System.out.println("预期: 2");
        System.out.println("解释: +1-1=0, -1+1=0");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：无解
        System.out.println("测试用例4: 无解");
        int[] nums4 = {1, 2, 3};
        int target4 = 7;
        int result4 = solution.findTargetSumWays(nums4, target4);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums4) + ", target = " + target4);
        System.out.println("输出: " + result4);
        System.out.println("预期: 0");
        System.out.println("解释: sum=6 < target=7，无解");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：包含0
        System.out.println("测试用例5: 包含0");
        int[] nums5 = {0, 0, 0, 0, 0, 0, 0, 0, 1};
        int target5 = 1;
        int result5 = solution.findTargetSumWays(nums5, target5);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums5) + ", target = " + target5);
        System.out.println("输出: " + result5);
        System.out.println("预期: 256");
        System.out.println("解释: 8个0可以任意选+或-，2^8=256种方案");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 问题转化：P - N = target, P + N = sum → P = (sum + target) / 2");
        System.out.println("2. 状态定义：dp[j] = 凑成和为 j 的方案数");
        System.out.println("3. 状态转移：dp[j] += dp[j - num]");
        System.out.println("4. 初始条件：dp[0] = 1");
        System.out.println("5. 循环顺序：01背包，倒序遍历");
        System.out.println("\n问题转化是这题的关键！将加减问题转化为01背包问题！");
    }
}



