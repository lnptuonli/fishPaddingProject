/**
 * LeetCode 198. 打家劫舍 - House Robber
 * 
 * 题目描述：
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，
 * 影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * 
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，
 * 一夜之内能够偷窃到的最高金额。
 * 
 * 示例 1：
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 * 
 * 示例 2：
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 * 
 * 示例 3：
 * 输入：[2,1,1,2]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 2)，然后偷窃 4 号房屋 (金额 = 2)。
 *      偷窃到的最高金额 = 2 + 2 = 4 。
 * 
 * 提示：
 * - 1 <= nums.length <= 100
 * - 0 <= nums[i] <= 400
 * 
 * 核心理解：
 * 
 * 1. 状态定义：
 *    - dp[i]：考虑前 i 个房子，能偷到的最大金额
 * 
 * 2. 状态转移方程：
 *    - 对于第 i 个房子，有两种选择：
 *      * 偷第 i 个房子：dp[i] = dp[i-2] + nums[i]
 *        （因为不能偷相邻的，所以只能从 dp[i-2] 转移）
 *      * 不偷第 i 个房子：dp[i] = dp[i-1]
 *        （保持前 i-1 个房子的最大值）
 *    - 取两者的最大值：dp[i] = max(dp[i-1], dp[i-2] + nums[i])
 * 
 * 3. 初始条件：
 *    - dp[0] = nums[0]（只有1个房子，偷它）
 *    - dp[1] = max(nums[0], nums[1])（两个房子，偷金额大的）
 * 
 * 4. 计算顺序：
 *    - 从前往后：dp[2], dp[3], ..., dp[n-1]
 * 
 * 5. 空间优化：
 *    - 观察：dp[i] 只依赖 dp[i-1] 和 dp[i-2]
 *    - 可以用两个变量代替整个数组
 *    - 空间复杂度：O(n) → O(1)
 * 
 * 可视化过程（nums = [2,7,9,3,1]）：
 * 
 * 房子：   [2]     [7]     [9]     [3]     [1]
 *          ↓       ↓       ↓       ↓       ↓
 * dp[0]:   2     (偷房子0)
 * dp[1]:   7     (不偷0，偷1，因为7>2)
 * dp[2]:  11     (偷0+偷2 = 2+9 = 11，比只偷1的7大)
 * dp[3]:  11     (偷0+偷2=11 vs 偷1+偷3=7+3=10，取11)
 * dp[4]:  12     (偷0+偷2+偷4=2+9+1=12 vs 11，取12)
 * 
 * 决策过程：
 * - 第0个房子：偷（金额=2）
 * - 第1个房子：不偷（因为偷了金额=7，但7>2，所以选7）
 * - 第2个房子：偷（金额=9，加上第0个的2，总共11）
 * - 第3个房子：不偷（因为偷了总额=10，不如不偷保持11）
 * - 第4个房子：偷（金额=1，加上前面的11，总共12）
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
 * 1. "不能相邻"的理解：
 *    - 不是说必须隔一个，而是不能连续偷两个
 *    - 可以隔多个，只要不相邻即可
 * 
 * 2. 初始条件：
 *    - dp[1] = max(nums[0], nums[1])，不是 nums[1]
 *    - 因为两个房子可以只偷一个，选金额大的
 * 
 * 3. 边界情况：
 *    - nums.length == 1 时，直接返回 nums[0]
 *    - nums.length == 0 时，返回 0（题目保证 length >= 1，可忽略）
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 213. 打家劫舍 II（房子排成环形）
 * - LeetCode 337. 打家劫舍 III（房子排成二叉树）
 * - LeetCode 740. 删除并获得点数（变形题）
 */
public class HouseRobber {
    
    /**
     * 主方法：计算不触动警报装置的情况下，能够偷窃到的最高金额
     * 
     * @param nums 每个房屋存放的金额
     * @return 能够偷窃到的最高金额
     */
    public int rob(int[] nums) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：动态规划五步法
        // 
        // 第1步：定义状态
        // dp[i] 表示考虑前 i 个房子（下标0到i-1），能偷到的最大金额
        // 
        // 第2步：状态转移方程
        // dp[i] = max(dp[i-1], dp[i-2] + nums[i])
        // 解释：
        // - dp[i-1]：不偷第i个房子，保持前i-1个的最大值
        // - dp[i-2] + nums[i]：偷第i个房子，加上前i-2个的最大值
        // 
        // 第3步：初始条件
        // dp[0] = nums[0]
        // dp[1] = max(nums[0], nums[1])
        // 
        // 第4步：计算顺序
        // 从前往后：dp[2], dp[3], ..., dp[n-1]
        // 
        // 第5步：空间优化（可选）
        // 只需要两个变量：prev2 和 prev1
        
        // 方法1：动态规划（数组版）
        // if (nums.length == 1) return nums[0];
        // int[] dp = new int[nums.length];
        // dp[0] = nums[0];
        // dp[1] = Math.max(nums[0], nums[1]);
        // for (int i = 2; i < nums.length; i++) {
        //     dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        // }
        // return dp[nums.length - 1];
        
        // 方法2：动态规划（空间优化版）⭐推荐
        // if (nums.length == 1) return nums[0];
        // int prev2 = nums[0];               // dp[i-2]
        // int prev1 = Math.max(nums[0], nums[1]);  // dp[i-1]
        // for (int i = 2; i < nums.length; i++) {
        //     int curr = Math.max(prev1, prev2 + nums[i]);
        //     prev2 = prev1;
        //     prev1 = curr;
        // }
        // return prev1;
        
        // 关键理解：
        // 1. 为什么是 max(dp[i-1], dp[i-2] + nums[i])？
        //    - 这是一个"偷或不偷"的决策问题
        //    - 偷第i个：必须放弃第i-1个，从dp[i-2]转移
        //    - 不偷第i个：保持dp[i-1]的最大值
        //    - 取两者中的最大值
        // 
        // 2. 为什么不能直接累加所有不相邻的？
        //    - 因为不知道具体偷哪些能获得最大值
        //    - DP帮我们考虑所有可能的组合，找到最优解
        // 
        // 3. 这题与爬楼梯的区别？
        //    - 爬楼梯：两种方式相加（都要到达）
        //    - 打家劫舍：两种方式取max（选最优）
        
        // 在这里编写你的实现代码
        //我认为关键的理解是，到达当前状态的时候，决策要不要偷，这里分几种情况：
        //1.上家在i-1，这家不能偷
        //2.上家在i-2，接着偷这家
        //3.上家在i-3，i-1和i-2都没偷，选了这家
        //但实际上，第三种情况不会发生。作为状态转移方程，应该只考虑当前状态i，那么只有两种情况：i偷了和i没偷
        //如果i偷了：那么i-1必定没偷，需要继承i-2状态的值，并且加上这家的财产（这不代表i-2偷了，只是在说路过i-2时的最优解，跟决策i是一个道理）
        //如果i没偷，那么i的状态就原封不动的继承i-1的值
        int[] dp= new int[nums.length];
        if (nums.length==0)
            return 0;
        dp[0]=nums[0];//0的状态：最优解当然是目前最大的
        if (nums.length==1)
            return dp[0];
        dp[1]=nums[1]>nums[0]?nums[1]:nums[0];//1的状态：最优解是两个里面较大的
        //剩余状态：考虑i偷还是不偷
        if (nums.length==2)
            return dp[1];
        for(int i=2;i<nums.length;i++)
            dp[i]=Math.max(dp[i-1],(dp[i-2]+nums[i]));
        return dp[nums.length-1];

        // ============================================
        // TODO: 实现结束
        // ============================================

    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        HouseRobber solution = new HouseRobber();
        
        System.out.println("=== LeetCode 198: 打家劫舍 ===");
        System.out.println("中等题，DP经典问题\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] nums1 = {1, 2, 3, 1};
        int result1 = solution.rob(nums1);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums1));
        System.out.println("输出: " + result1);
        System.out.println("预期: 4");
        System.out.println("解释: 偷窃 1 号房屋 (1) 和 3 号房屋 (3)，总金额 = 1 + 3 = 4");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] nums2 = {2, 7, 9, 3, 1};
        int result2 = solution.rob(nums2);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums2));
        System.out.println("输出: " + result2);
        System.out.println("预期: 12");
        System.out.println("解释: 偷窃 1 号房屋 (2), 3 号房屋 (9) 和 5 号房屋 (1)");
        System.out.println("     总金额 = 2 + 9 + 1 = 12");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        int[] nums3 = {2, 1, 1, 2};
        int result3 = solution.rob(nums3);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums3));
        System.out.println("输出: " + result3);
        System.out.println("预期: 4");
        System.out.println("解释: 偷窃 1 号房屋 (2) 和 4 号房屋 (2)，总金额 = 2 + 2 = 4");
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
        System.out.println("解释: 只偷第一个房子");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例6：递增序列
        System.out.println("测试用例6: 递增序列");
        int[] nums6 = {1, 2, 3, 4, 5};
        int result6 = solution.rob(nums6);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums6));
        System.out.println("输出: " + result6);
        System.out.println("预期: 9");
        System.out.println("解释: 偷窃 1、3、5 号房屋，总金额 = 1 + 3 + 5 = 9");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i] = 考虑前i个房子的最大金额");
        System.out.println("2. 状态转移：dp[i] = max(dp[i-1], dp[i-2] + nums[i])");
        System.out.println("3. 初始条件：dp[0] = nums[0], dp[1] = max(nums[0], nums[1])");
        System.out.println("4. 决策：偷第i个 vs 不偷第i个，取最大");
        System.out.println("5. 空间优化：只需两个变量");
        System.out.println("\n这是DP决策问题的经典代表，务必掌握！");
    }
}

