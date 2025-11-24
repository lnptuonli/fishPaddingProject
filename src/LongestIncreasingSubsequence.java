import java.util.ArrayList;
import java.util.Arrays;

/**
 * LeetCode 300. 最长递增子序列 - Longest Increasing Subsequence (LIS)
 * <p>
 * 题目描述：
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * <p>
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
 * 例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 * 示例 1：
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * <p>
 * 示例 2：
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * 解释：最长递增子序列是 [0,1,2,3]
 * <p>
 * 示例 3：
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 * 解释：严格递增，相同元素不算
 * <p>
 * 提示：
 * - 1 <= nums.length <= 2500
 * - -10^4 <= nums[i] <= 10^4
 * <p>
 * 核心理解：
 * <p>
 * 1. 状态定义：
 * - dp[i]：以 nums[i] 结尾的最长递增子序列的长度
 * - 注意：必须以 nums[i] 结尾！
 * <p>
 * 2. 状态转移方程：
 * - 对于每个 nums[i]，需要找到所有 j < i 且 nums[j] < nums[i] 的位置
 * - dp[i] = max(dp[j] + 1)，其中 0 <= j < i 且 nums[j] < nums[i]
 * - 含义：在所有比 nums[i] 小的元素后面接上 nums[i]
 * <p>
 * 3. 初始条件：
 * - dp[i] = 1（每个元素单独成一个子序列，长度为1）
 * <p>
 * 4. 最终答案：
 * - 答案不是 dp[n-1]，而是 max(dp[0], dp[1], ..., dp[n-1])
 * - 因为最长递增子序列可能在任何位置结束
 * <p>
 * 5. 与最大子数组和的区别：
 * - 最大子数组和：dp[i] 只依赖 dp[i-1]（连续）
 * - 最长递增子序列：dp[i] 依赖所有 j < i（不连续）
 * <p>
 * 可视化过程（nums = [10,9,2,5,3,7,101,18]）：
 * <p>
 * 下标:    0   1   2   3   4   5   6    7
 * nums:   10   9   2   5   3   7  101  18
 * ↓   ↓   ↓   ↓   ↓   ↓   ↓    ↓
 * dp[i]:   1   1   1   2   2   3   4    4
 * <p>
 * 决策过程：
 * - dp[0] = 1（[10]）
 * - dp[1] = 1（[9]，10>9，不能接在10后面）
 * - dp[2] = 1（[2]）
 * - dp[3] = 2（[2,5]，可以接在2后面）
 * - dp[4] = 2（[2,3]，可以接在2后面）
 * - dp[5] = 3（[2,3,7] 或 [2,5,7]，可以接在3或5后面）
 * - dp[6] = 4（[2,3,7,101]，可以接在7后面）
 * - dp[7] = 4（[2,3,7,18]，可以接在7后面）
 * <p>
 * 最长递增子序列：[2,3,7,101] 或 [2,5,7,101] 等，长度为 4
 * <p>
 * 解题思路：
 * <p>
 * 方法1：动态规划（O(n^2)）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n^2)
 * - 空间复杂度：O(n)
 * - 经典解法，面试推荐
 * <p>
 * 方法2：动态规划 + 二分查找（O(n log n)）⭐⭐⭐⭐
 * - 时间复杂度：O(n log n)
 * - 空间复杂度：O(n)
 * - 进阶解法，需要理解贪心思想
 * <p>
 * 易错点：
 * <p>
 * 1. 状态定义的理解：
 * - dp[i] 是"以 i 结尾"，不是"前 i 个的最长长度"
 * - 这与最大子数组和类似
 * <p>
 * 2. 转移方程的理解：
 * - 需要遍历所有 j < i，找到所有 nums[j] < nums[i] 的位置
 * - 取其中 dp[j] 最大的，加1
 * <p>
 * 3. 初始条件：
 * - 每个 dp[i] 初始化为 1，不是 0
 * - 因为每个元素单独成一个子序列
 * <p>
 * 4. 答案不是 dp[n-1]：
 * - 需要遍历所有 dp[i]，找到最大值
 * <p>
 * 5. 严格递增 vs 非严格递增：
 * - 严格递增：nums[j] < nums[i]
 * - 非严格递增：nums[j] <= nums[i]
 * - 本题要求严格递增
 * <p>
 * 进阶：如何打印出具体的子序列？
 * <p>
 * - 在计算 dp 的同时，记录每个位置的前驱节点
 * - 找到最大的 dp[i] 后，从 i 开始回溯
 * <p>
 * 与相关题目的联系：
 * <p>
 * - LeetCode 673. 最长递增子序列的个数（求个数）
 * - LeetCode 354. 俄罗斯套娃信封问题（二维LIS）
 * - LeetCode 646. 最长数对链（变形）
 * - LeetCode 1143. 最长公共子序列（LCS，双序列DP）
 */
public class LongestIncreasingSubsequence {

    /**
     * 主方法：计算最长递增子序列的长度
     *
     * @param nums 整数数组
     * @return 最长递增子序列的长度
     */
    public int lengthOfLIS(int[] nums) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================

        // 提示：动态规划（O(n^2)解法）
        // 
        // 第1步：定义状态
        // dp[i] 表示以 nums[i] 结尾的最长递增子序列的长度
        // 
        // 第2步：状态转移方程
        // dp[i] = max(dp[j] + 1)，其中 0 <= j < i 且 nums[j] < nums[i]
        // 解释：
        // - 遍历所有 j < i
        // - 如果 nums[j] < nums[i]，可以将 nums[i] 接在 nums[j] 后面
        // - 取所有可能的 dp[j] 中最大的，加1
        // 
        // 第3步：初始条件
        // dp[i] = 1（每个元素单独成一个子序列）
        // 
        // 第4步：最终答案
        // 答案 = max(dp[0], dp[1], ..., dp[n-1])
        // 
        // 第5步：时间复杂度
        // O(n^2)：两层循环

        // 参考实现：
        // int n = nums.length;
        // int[] dp = new int[n];
        // 
        // // 初始化：每个元素单独成一个子序列
        // for (int i = 0; i < n; i++) {
        //     dp[i] = 1;
        // }
        // 
        // // 状态转移
        // for (int i = 1; i < n; i++) {
        //     for (int j = 0; j < i; j++) {
        //         if (nums[j] < nums[i]) {
        //             dp[i] = Math.max(dp[i], dp[j] + 1);
        //         }
        //     }
        // }
        // 
        // // 找到最大值
        // int maxLen = 0;
        // for (int i = 0; i < n; i++) {
        //     maxLen = Math.max(maxLen, dp[i]);
        // }
        // 
        // return maxLen;

        // 关键理解：
        // 1. 为什么是 max(dp[j] + 1)？
        //    - 对于 nums[i]，可以接在所有比它小的 nums[j] 后面
        //    - 选择其中最长的那个，加上 nums[i] 本身（+1）
        // 
        // 2. 为什么初始化为 1？
        //    - 每个元素单独成一个子序列，长度为 1
        //    - 这是最小的情况
        // 
        // 3. 这题与最大子数组和的区别？
        //    - 最大子数组和：子数组必须连续，dp[i] 只依赖 dp[i-1]
        //    - 最长递增子序列：子序列不连续，dp[i] 依赖所有 j < i
        // 
        // 4. 如何优化到 O(n log n)？
        //    - 使用贪心 + 二分查找
        //    - 维护一个 tails 数组，tails[i] 表示长度为 i+1 的递增子序列的最小末尾元素
        //    - 对于每个 nums[i]，二分查找它应该放在 tails 的哪个位置

        // 在这里编写你的实现代码
        //简单，对于每个状态dp[i]，只需要判断是应该继承上一状态还是从当前算法重新开始，即dp[i]是一个维护到当前元素为止，当前子序列长度的数组
        //但是坏了，它可没说是子序列必须是连续的，那我应该换个策略，dp[i]维护成一个假设每个元素都是“以当前位置为起点的，最长子序列的长度”
        //此外还得想办法应对递增序列不连续的情况，使用指针指向比较对象，如果中途 发现不连续则比较
        //但是这思路仍然有很大问题：在 LIS 中，dp[i] 应该表示 以第 i 个元素结尾的最长递增子序列长度，而我上面的考虑根本就是一个贪心算法，不是动态规划的思路。
        //本质上，对于每个位置i,都应该像选妃一样，对于每个有潜力的候选者，以其长度加上自身，取最大的一个。


        /*//那么开始实现：
        int[] dp =new int[nums.length];
        //初始化：
        for (int x=0;x<nums.length;x++){
            dp[x]=1;
        }
        int max=1;
        for(int i=0;i<nums.length;i++){
            for (int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    //开始选妃，看看是妃子带着嫁妆合适，还是已经保留了更好的妃子
                    dp[i]=Math.max(dp[j]+1,dp[i]);
                }
            }
            if(max<dp[i])
                max=dp[i];
        }
        return max;*/
        //嘛，以上是复杂度n^2的解法，我们要掌握的是nlogn的解法：维护一个候选数组tails，如果新元素大于其最后一位，就喜加一；
        //如果并不大，就用其替换第一个大于等于它的元素（从前往后数，我们要保持它递增）
        ArrayList<Integer> tails = new ArrayList<Integer>();
        tails.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > tails.get(tails.size() - 1))
                tails.add(nums[i]);
                //否则，二分查找
            else {
                int left = 0, right = tails.size();//标准二分查找，应保持边界为留左不留右
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (tails.get(mid) < nums[i]) {//如果小，说明在右半边
                        left = mid + 1;
                    } else {//如果大，说明在左半边
                        right = mid;
                    }
                }
                tails.set(left, nums[i]);
            }
        }
        return tails.size();
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        LongestIncreasingSubsequence solution = new LongestIncreasingSubsequence();

        System.out.println("=== LeetCode 300: 最长递增子序列 ===");
        System.out.println("中等题，经典LIS问题\n");

        // 测试用例1
        System.out.println("测试用例1:");
        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        int result1 = solution.lengthOfLIS(nums1);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums1));
        System.out.println("输出: " + result1);
        System.out.println("预期: 4");
        System.out.println("解释: 最长递增子序列是 [2,3,7,101]");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例2
        System.out.println("测试用例2:");
        int[] nums2 = {0, 1, 0, 3, 2, 3};
        int result2 = solution.lengthOfLIS(nums2);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums2));
        System.out.println("输出: " + result2);
        System.out.println("预期: 4");
        System.out.println("解释: 最长递增子序列是 [0,1,2,3]");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例3
        System.out.println("测试用例3:");
        int[] nums3 = {7, 7, 7, 7, 7, 7, 7};
        int result3 = solution.lengthOfLIS(nums3);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums3));
        System.out.println("输出: " + result3);
        System.out.println("预期: 1");
        System.out.println("解释: 严格递增，相同元素不算");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例4：单个元素
        System.out.println("测试用例4: 单个元素");
        int[] nums4 = {1};
        int result4 = solution.lengthOfLIS(nums4);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums4));
        System.out.println("输出: " + result4);
        System.out.println("预期: 1");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例5：递减序列
        System.out.println("测试用例5: 递减序列");
        int[] nums5 = {5, 4, 3, 2, 1};
        int result5 = solution.lengthOfLIS(nums5);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums5));
        System.out.println("输出: " + result5);
        System.out.println("预期: 1");
        System.out.println("解释: 任何单个元素都是长度为1的递增子序列");
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 测试用例6：递增序列
        System.out.println("测试用例6: 递增序列");
        int[] nums6 = {1, 2, 3, 4, 5};
        int result6 = solution.lengthOfLIS(nums6);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums6));
        System.out.println("输出: " + result6);
        System.out.println("预期: 5");
        System.out.println("解释: 整个数组就是递增子序列");
        System.out.println("\n" + "=".repeat(50) + "\n");

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i] = 以 nums[i] 结尾的最长递增子序列长度");
        System.out.println("2. 状态转移：dp[i] = max(dp[j] + 1)，j < i 且 nums[j] < nums[i]");
        System.out.println("3. 初始条件：dp[i] = 1（每个元素单独成序列）");
        System.out.println("4. 答案：max(dp[0], dp[1], ..., dp[n-1])");
        System.out.println("5. 时间复杂度：O(n^2)（两层循环）");
        System.out.println("\n这是序列DP的经典问题，务必掌握！");
    }
}

