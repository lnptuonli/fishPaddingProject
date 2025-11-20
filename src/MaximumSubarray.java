import java.util.Arrays;

/**
 * LeetCode 53. 最大子数组和 - Maximum Subarray
 * <p>
 * 题目描述：
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），
 * 返回其最大和。
 * <p>
 * 子数组是数组中的一个连续部分。
 * <p>
 * 示例 1：
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * <p>
 * 示例 2：
 * 输入：nums = [1]
 * 输出：1
 * 解释：连续子数组 [1] 的和最大，为 1 。
 * <p>
 * 示例 3：
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 * 解释：连续子数组 [5,4,-1,7,8] 的和最大，为 23 。
 * <p>
 * 提示：
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * <p>
 * 核心理解：
 * <p>
 * 1. 状态定义：
 * - dp[i]：以 nums[i] 结尾的最大连续子数组和
 * - 注意：必须以 nums[i] 结尾，不是"前 i 个元素的最大和"
 * <p>
 * 2. 状态转移方程：
 * - 对于 nums[i]，有两种选择：
 * * 选择1：加入前面的子数组 → dp[i] = dp[i-1] + nums[i]
 * * 选择2：重新开始一个子数组 → dp[i] = nums[i]
 * - 取两者的最大值：dp[i] = max(dp[i-1] + nums[i], nums[i])
 * <p>
 * 3. 初始条件：
 * - dp[0] = nums[0]（第一个元素单独成子数组）
 * <p>
 * 4. 最终答案：
 * - 答案不是 dp[n-1]，而是 max(dp[0], dp[1], ..., dp[n-1])
 * - 因为最大子数组可能在任何位置结束
 * <p>
 * 5. Kadane's Algorithm（卡登算法）：
 * - 这道题的经典解法就是 Kadane's Algorithm
 * - 核心思想：维护"当前子数组和"，如果和为负，重新开始
 * <p>
 * 可视化过程（nums = [-2,1,-3,4,-1,2,1,-5,4]）：
 * <p>
 * 下标:    0   1   2   3   4   5   6   7   8
 * nums:   -2   1  -3   4  -1   2   1  -5   4
 * ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓
 * dp[i]:  -2   1  -2   4   3   5   6   1   5
 * <p>
 * 决策过程：
 * - dp[0] = -2（只有一个元素）
 * - dp[1] = max(-2+1, 1) = 1（重新开始更好）
 * - dp[2] = max(1-3, -3) = -2（加入前面）
 * - dp[3] = max(-2+4, 4) = 4（重新开始更好）
 * - dp[4] = max(4-1, -1) = 3（加入前面）
 * - dp[5] = max(3+2, 2) = 5（加入前面）
 * - dp[6] = max(5+1, 1) = 6（加入前面）⭐最大值
 * - dp[7] = max(6-5, -5) = 1（加入前面）
 * - dp[8] = max(1+4, 4) = 5（加入前面）
 * <p>
 * 最大子数组：[4,-1,2,1]，和为 6
 * <p>
 * 解题思路：
 * <p>
 * 方法1：动态规划（数组版）
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)
 * <p>
 * 方法2：动态规划（空间优化版）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(1)
 * <p>
 * 方法3：分治法（进阶）
 * - 时间复杂度：O(n log n)
 * - 空间复杂度：O(log n)
 * <p>
 * 易错点：
 * <p>
 * 1. 状态定义的理解：
 * - dp[i] 是"以 i 结尾"，不是"前 i 个的最大和"
 * - 这是关键！如果理解错了，转移方程就错了
 * <p>
 * 2. 答案不是 dp[n-1]：
 * - 需要遍历所有 dp[i]，找到最大值
 * - 或者在计算过程中维护一个 maxSum
 * <p>
 * 3. 转移方程的理解：
 * - dp[i] = max(dp[i-1] + nums[i], nums[i])
 * - 等价于：dp[i] = max(dp[i-1], 0) + nums[i]
 * - 含义：如果前面的和是负数，不如重新开始
 * <p>
 * 4. 全是负数的情况：
 * - 如果数组全是负数，答案是最大的那个负数
 * - 不是 0！因为子数组至少包含一个元素
 * <p>
 * Kadane's Algorithm 伪代码：
 * <p>
 * ```
 * maxSum = nums[0]
 * currentSum = nums[0]
 * <p>
 * for i from 1 to n-1:
 * currentSum = max(nums[i], currentSum + nums[i])
 * maxSum = max(maxSum, currentSum)
 * <p>
 * return maxSum
 * ```
 * <p>
 * 为什么叫 Kadane's Algorithm？
 * - 由 Joseph Born Kadane 在 1984 年提出
 * - 是解决最大子数组和问题的经典算法
 * - 时间复杂度 O(n)，空间复杂度 O(1)
 * <p>
 * 与相关题目的联系：
 * <p>
 * - LeetCode 152. 乘积最大子数组（变体，需要维护最小值）
 * - LeetCode 918. 环形子数组的最大和（环形版本）
 * - LeetCode 121. 买卖股票的最佳时机（变形应用）
 * - LeetCode 309. 最佳买卖股票时机含冷冻期（状态机DP）
 */
public class MaximumSubarray {

    /**
     * 主方法：计算最大连续子数组和
     *
     * @param nums 整数数组
     * @return 最大连续子数组和
     */
    public int maxSubArray(int[] nums) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================

        // 提示：Kadane's Algorithm（卡登算法）
        // 
        // 第1步：定义状态
        // dp[i] 表示以 nums[i] 结尾的最大连续子数组和
        // 注意：必须以 nums[i] 结尾！
        // 
        // 第2步：状态转移方程
        // dp[i] = max(dp[i-1] + nums[i], nums[i])
        // 解释：
        // - dp[i-1] + nums[i]：将 nums[i] 加入前面的子数组
        // - nums[i]：重新开始一个新的子数组
        // 
        // 第3步：初始条件
        // dp[0] = nums[0]
        // 
        // 第4步：最终答案
        // 答案 = max(dp[0], dp[1], ..., dp[n-1])
        // 
        // 第5步：空间优化
        // 只需要一个变量 currentSum 和 maxSum

        // 方法1：动态规划（数组版）
        // int[] dp = new int[nums.length];
        // dp[0] = nums[0];
        // int maxSum = dp[0];
        // for (int i = 1; i < nums.length; i++) {
        //     dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);
        //     maxSum = Math.max(maxSum, dp[i]);
        // }
        // return maxSum;

        // 方法2：Kadane's Algorithm（空间优化版）⭐推荐
        // int maxSum = nums[0];
        // int currentSum = nums[0];
        // for (int i = 1; i < nums.length; i++) {
        //     currentSum = Math.max(nums[i], currentSum + nums[i]);
        //     maxSum = Math.max(maxSum, currentSum);
        // }
        // return maxSum;

        // 关键理解：
        // 1. 为什么是 max(dp[i-1] + nums[i], nums[i])？
        //    - 对于 nums[i]，有两种选择：
        //      * 加入前面的子数组：dp[i-1] + nums[i]
        //      * 重新开始：nums[i]
        //    - 如果 dp[i-1] < 0，重新开始更好
        //    - 如果 dp[i-1] >= 0，加入前面更好
        // 
        // 2. 为什么答案不是 dp[n-1]？
        //    - 因为 dp[i] 是"以 i 结尾"的最大和
        //    - 最大子数组可能在任何位置结束
        //    - 所以需要维护全局最大值 maxSum
        // 
        // 3. 等价变换：
        //    - dp[i] = max(dp[i-1] + nums[i], nums[i])
        //    - 等价于：dp[i] = max(dp[i-1], 0) + nums[i]
        //    - 含义：如果前面的和是负数，就舍弃
        // 
        // 4. 这题与其他DP的区别：
        //    - 爬楼梯：dp[i] 依赖 dp[i-1] 和 dp[i-2]（求和）
        //    - 打家劫舍：dp[i] 依赖 dp[i-1] 和 dp[i-2]（取max）
        //    - 最大子数组：dp[i] 只依赖 dp[i-1]（取max）

        // 在这里编写你的实现代码

        //那么，对于这道题，既然已经知道了属于动态规划算法，那么就一定要找出当前状态和上一状态的关联关系
        //既然是状态转移，那么每个状态对应的一定是当前已知位置状况最好的结果，及考虑当前位置的数组和的最大值
        //很有意思，对于当前位置，有两种选择，要么继承上个状态的值喜加一，要么前面的状态还没自己本身大，那就重新开始
        //那么开始实现：
        int[] dp = new int[nums.length];
        if (nums.length == 1)
            return nums[0];
        dp[0] = nums[0];
        int max = dp[0];
        //我感觉从两个开始，就能推导了，这又不像是爬楼梯需要两个状态
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);//状态转移方程哦
            if (dp[i] > max)
                max = dp[i];
        }
        //不对，哪有这么简单，既然是要最大的数组，那这个数嫌小的话，不加这个数不就好了
        //额，好像就是这么简单，因为他说是连续数组,但确实有个小问题，我应该留下到现在为止的最大值
        return max;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        MaximumSubarray solution = new MaximumSubarray();

        System.out.println("=== LeetCode 53: 最大子数组和 ===");
        System.out.println("中等题，Kadane's Algorithm 经典\n");

        // 测试用例1
        System.out.println("测试用例1:");
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result1 = solution.maxSubArray(nums1);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums1));
        System.out.println("输出: " + result1);
        System.out.println("预期: 6");
        System.out.println("解释: 连续子数组 [4,-1,2,1] 的和最大，为 6");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例2
        System.out.println("测试用例2:");
        int[] nums2 = {1};
        int result2 = solution.maxSubArray(nums2);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums2));
        System.out.println("输出: " + result2);
        System.out.println("预期: 1");
        System.out.println("解释: 只有一个元素");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例3
        System.out.println("测试用例3:");
        int[] nums3 = {5, 4, -1, 7, 8};
        int result3 = solution.maxSubArray(nums3);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums3));
        System.out.println("输出: " + result3);
        System.out.println("预期: 23");
        System.out.println("解释: 连续子数组 [5,4,-1,7,8] 的和最大，为 23");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例4：全是负数
        System.out.println("测试用例4: 全是负数");
        int[] nums4 = {-3, -2, -5, -1, -4};
        int result4 = solution.maxSubArray(nums4);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums4));
        System.out.println("输出: " + result4);
        System.out.println("预期: -1");
        System.out.println("解释: 最大的子数组是 [-1]");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例5：全是正数
        System.out.println("测试用例5: 全是正数");
        int[] nums5 = {1, 2, 3, 4, 5};
        int result5 = solution.maxSubArray(nums5);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums5));
        System.out.println("输出: " + result5);
        System.out.println("预期: 15");
        System.out.println("解释: 整个数组的和最大");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例6：正负交替
        System.out.println("测试用例6: 正负交替");
        int[] nums6 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result6 = solution.maxSubArray(nums6);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums6));
        System.out.println("输出: " + result6);
        System.out.println("预期: 6");
        System.out.println("解释: 子数组 [4,-1,2,1]");
        System.out.println("\n" + "=".repeat(50) + "\n");

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i] = 以 nums[i] 结尾的最大子数组和");
        System.out.println("2. 状态转移：dp[i] = max(dp[i-1] + nums[i], nums[i])");
        System.out.println("3. 初始条件：dp[0] = nums[0]");
        System.out.println("4. 答案：max(dp[0], dp[1], ..., dp[n-1])");
        System.out.println("5. 空间优化：只需 currentSum 和 maxSum 两个变量");
        System.out.println("\n这是 Kadane's Algorithm，面试超高频经典算法！");
    }
}

