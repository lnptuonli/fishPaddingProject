/**
 * LeetCode 213. 打家劫舍 II - House Robber II
 * 
 * 题目描述：
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。
 * 这个地方所有的房屋都 **围成一圈** ，这意味着第一个房屋和最后一个房屋是紧挨着的。
 * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，
 * 系统会自动报警。
 * 
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，
 * 今晚能够偷窃到的最高金额。
 * 
 * 示例 1：
 * 输入：nums = [2,3,2]
 * 输出：3
 * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
 * 
 * 示例 2：
 * 输入：nums = [1,2,3,1]
 * 输出：4
 * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 * 
 * 示例 3：
 * 输入：nums = [1,2,3]
 * 输出：3
 * 解释：偷窃第2号或第3号房屋，金额都是3
 * 
 * 提示：
 * - 1 <= nums.length <= 100
 * - 0 <= nums[i] <= 1000
 * 
 * 核心理解：
 * 
 * 1. 与 LeetCode 198 的区别：
 *    - 198：线性排列的房屋
 *    - 213：环形排列的房屋（首尾相连）
 * 
 * 2. 环形数组的关键约束：
 *    - 第一个房子和最后一个房子不能同时偷
 *    - 这是与 198 唯一的区别
 * 
 * 3. 破环成链的思想：
 *    - 情况1：偷第一个房子 → 不能偷最后一个 → 范围 [0, n-2]
 *    - 情况2：不偷第一个房子 → 可以偷最后一个 → 范围 [1, n-1]
 *    - 答案 = max(情况1, 情况2)
 * 
 * 4. 状态转移方程（与 198 相同）：
 *    - dp[i] = max(dp[i-1], dp[i-2] + nums[i])
 * 
 * 可视化过程（nums = [2,3,2]）：
 * 
 * 环形结构：
 *       [2] ← → [2]
 *        ↓       ↑
 *       [3] ← ← ←
 * 
 * 分解为两个线性问题：
 * 
 * 情况1：偷第一个房子（[2,3,2] → 考虑 [2,3]）
 * - 不能考虑最后一个房子
 * - 范围：[0, n-2] = [2, 3]
 * - 最大金额：max(2, 3) = 3
 * 
 * 情况2：不偷第一个房子（[2,3,2] → 考虑 [3,2]）
 * - 可以考虑最后一个房子
 * - 范围：[1, n-1] = [3, 2]
 * - 最大金额：max(3, 2) = 3
 * 
 * 最终答案：max(3, 3) = 3
 * 
 * 解题思路：
 * 
 * 方法1：分两次 DP
 * - 调用两次 rob198（LeetCode 198 的解法）
 * - 第一次：范围 [0, n-2]
 * - 第二次：范围 [1, n-1]
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(1)
 * 
 * 易错点：
 * 
 * 1. 为什么要分两种情况？
 *    - 因为环形数组的首尾约束
 *    - 偷第一个 vs 不偷第一个，决定了能否偷最后一个
 * 
 * 2. 边界情况：
 *    - nums.length == 1：只有一个房子，直接返回 nums[0]
 *    - nums.length == 2：两个房子，返回 max(nums[0], nums[1])
 * 
 * 3. 为什么是 max(rob[0,n-2], rob[1,n-1])？
 *    - rob[0,n-2]：考虑第一个，排除最后一个
 *    - rob[1,n-1]：排除第一个，考虑最后一个
 *    - 这两种情况覆盖了所有可能
 * 
 * 4. 能否三种情况：偷第一个、偷最后一个、都不偷？
 *    - 不需要！"都不偷"的情况包含在前两种中
 *    - 如果第一个和最后一个都不偷，前两种情况的DP会自动处理
 * 
 * 思考题：
 * 
 * Q1: 为什么不用担心"两个都不偷"的情况？
 * A1: 因为 rob[0,n-2] 和 rob[1,n-1] 的 DP 过程会自动考虑"不偷某些房子"的情况。
 *     如果最优解确实是"两个都不偷"，这会被包含在两次 DP 的结果中。
 * 
 * Q2: 能否用一次 DP 解决？
 * A2: 不能。环形约束导致状态之间的依赖关系变复杂，破环成链是最优解。
 * 
 * Q3: 如果是三维环形（首尾和中间都有约束）怎么办？
 * A3: 需要分更多情况讨论，或者用更复杂的状态定义。
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 198. 打家劫舍（基础版）
 * - LeetCode 337. 打家劫舍 III（树形版）
 * - LeetCode 740. 删除并获得点数（变形）
 * - 环形数组问题的通用思路：破环成链
 */
public class HouseRobberII {
    
    /**
     * 主方法：计算环形房屋能偷到的最高金额
     * 
     * @param nums 每个房屋存放的金额
     * @return 能够偷窃到的最高金额
     */
    public int rob(int[] nums) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：破环成链
        // 
        // 核心思想：
        // 环形数组的约束是"第一个和最后一个不能同时偷"
        // 
        // 解决方法：
        // 1. 分两种情况讨论
        // 2. 每种情况都变成线性问题（LeetCode 198）
        // 3. 取两种情况的最大值
        // 
        // 情况1：偷第一个房子
        // - 不能偷最后一个
        // - 范围：[0, n-2]
        // 
        // 情况2：不偷第一个房子
        // - 可以偷最后一个
        // - 范围：[1, n-1]
        // 
        // 边界情况：
        // - n == 1：只有一个房子，直接返回
        // - n == 2：两个房子，返回较大的
        
        // 参考实现：
        // if (nums.length == 1) return nums[0];
        // if (nums.length == 2) return Math.max(nums[0], nums[1]);
        // 
        // // 情况1：考虑第一个，不考虑最后一个
        // int max1 = robRange(nums, 0, nums.length - 2);
        // 
        // // 情况2：不考虑第一个，考虑最后一个
        // int max2 = robRange(nums, 1, nums.length - 1);
        // 
        // return Math.max(max1, max2);
        
        // 关键理解：
        // 1. 为什么要分两种情况？
        //    - 环形数组的核心约束：首尾不能同时偷
        //    - 偷第一个 → 不能偷最后一个
        //    - 不偷第一个 → 可以偷最后一个
        //    - 这两种情况互斥且完备
        // 
        // 2. 为什么不需要考虑"两个都不偷"？
        //    - 因为 DP 过程会自动考虑"不偷某些房子"
        //    - 如果最优解是"两个都不偷"，它会被包含在上述两种情况中
        // 
        // 3. robRange 方法就是 LeetCode 198 的解法
        //    - 完全相同的状态转移方程
        //    - dp[i] = max(dp[i-1], dp[i-2] + nums[i])
        
        // 在这里编写你的实现代码
        //说简单也简单，既然首位两个房子不能同时偷，那么可以分成考虑首不考虑尾，考虑尾不考虑首两个问题，取大的
        //有点晚了，我赶时间，不再废话了
        int[] dp=new int[nums.length];
        if(nums.length==1)
            return nums[0];
        if(nums.length==2)//有两家，只能挑一家去偷
            return Math.max(nums[0],nums[1]);

        //考虑首
        dp[0]=nums[0];
        dp[1]=Math.max(nums[0],nums[1]);
        for(int i=2;i<nums.length-1;i++)
            dp[i]=Math.max(dp[i-2]+nums[i],dp[i-1]);//决策：是继承dp[i-2]的并且抢i，还是保留dp[i-1]的路过i
        int max= dp[nums.length-2];

        //考虑尾
        dp[1]=nums[1];
        dp[2]=Math.max(nums[1],nums[2]);
        for(int i=3;i<nums.length;i++)
            dp[i]=Math.max(dp[i-2]+nums[i],dp[i-1]);
        return Math.max(max,dp[nums.length-1]);

    }
    
    /**
     * 辅助方法：在指定范围内进行打家劫舍（线性版本）
     * 这就是 LeetCode 198 的解法
     * 
     * @param nums 房屋金额数组
     * @param start 起始下标（包含）
     * @param end 结束下标（包含）
     * @return 指定范围内能偷到的最高金额
     */
    private int robRange(int[] nums, int start, int end) {
        // ============================================
        // TODO: 在这里实现 LeetCode 198 的解法
        // ============================================
        
        // 提示：这就是打家劫舍 I 的解法
        // 
        // 状态转移方程：
        // dp[i] = max(dp[i-1], dp[i-2] + nums[i])
        // 
        // 空间优化版（推荐）：
        // int prev2 = 0;
        // int prev1 = 0;
        // for (int i = start; i <= end; i++) {
        //     int curr = Math.max(prev1, prev2 + nums[i]);
        //     prev2 = prev1;
        //     prev1 = curr;
        // }
        // return prev1;
        
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
        HouseRobberII solution = new HouseRobberII();
        
        System.out.println("=== LeetCode 213: 打家劫舍 II ===");
        System.out.println("中等题，环形数组DP\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] nums1 = {2, 3, 2};
        int result1 = solution.rob(nums1);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums1));
        System.out.println("输出: " + result1);
        System.out.println("预期: 3");
        System.out.println("解释: 不能同时偷第一个和最后一个，只能偷中间的3");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] nums2 = {1, 2, 3, 1};
        int result2 = solution.rob(nums2);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums2));
        System.out.println("输出: " + result2);
        System.out.println("预期: 4");
        System.out.println("解释: 偷第1个和第3个，1 + 3 = 4");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        int[] nums3 = {1, 2, 3};
        int result3 = solution.rob(nums3);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums3));
        System.out.println("输出: " + result3);
        System.out.println("预期: 3");
        System.out.println("解释: 偷第2个或第3个，金额都是3");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：边界情况 - 只有一个房子
        System.out.println("测试用例4: 边界情况 - 只有一个房子");
        int[] nums4 = {5};
        int result4 = solution.rob(nums4);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums4));
        System.out.println("输出: " + result4);
        System.out.println("预期: 5");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：两个房子
        System.out.println("测试用例5: 两个房子");
        int[] nums5 = {5, 3};
        int result5 = solution.rob(nums5);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums5));
        System.out.println("输出: " + result5);
        System.out.println("预期: 5");
        System.out.println("解释: 只能偷一个，选较大的");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例6：所有房子金额相同
        System.out.println("测试用例6: 所有房子金额相同");
        int[] nums6 = {5, 5, 5, 5, 5};
        int result6 = solution.rob(nums6);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums6));
        System.out.println("输出: " + result6);
        System.out.println("预期: 10");
        System.out.println("解释: 偷第1、3个或第2、4个，金额都是10");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 破环成链：将环形问题转化为两个线性问题");
        System.out.println("2. 情况1：考虑第一个，范围[0, n-2]");
        System.out.println("3. 情况2：不考虑第一个，范围[1, n-1]");
        System.out.println("4. 答案：max(情况1, 情况2)");
        System.out.println("5. robRange 就是 LeetCode 198 的解法");
        System.out.println("\n这是环形数组DP的经典模板，理解破环成链的思想！");
    }
}

