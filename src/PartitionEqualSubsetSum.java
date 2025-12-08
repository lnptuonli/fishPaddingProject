/**
 * LeetCode 416. 分割等和子集 - Partition Equal Subset Sum
 * 
 * 题目描述：
 * 给你一个 **只包含正整数** 的 **非空** 数组 nums 。
 * 请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * 
 * 示例 1：
 * 输入：nums = [1, 5, 11, 5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11]
 * 
 * 示例 2：
 * 输入：nums = [1, 2, 3, 5]
 * 输出：false
 * 解释：数组不能分割成两个和相等的子集
 * 
 * 提示：
 * - 1 <= nums.length <= 200
 * - 1 <= nums[i] <= 100
 * 
 * 核心理解：
 * 
 * 1. 问题转化：
 *    - 分割成两个和相等的子集
 *    - 等价于：能否从数组中选出一些数，使得它们的和等于 sum / 2
 *    - 这就是一个 01背包问题！
 * 
 * 2. 01背包问题：
 *    - 背包容量：target = sum / 2
 *    - 物品：nums 中的每个数（每个数只能用一次）
 *    - 目标：判断能否恰好装满背包
 * 
 * 3. 状态定义：
 *    - dp[i][j]：前 i 个数中，能否凑出和为 j
 *    - dp[i][j] = true 表示可以凑出，false 表示不能凑出
 * 
 * 4. 状态转移方程：
 *    - 对于第 i 个数 nums[i-1]，有两种选择：
 *      a) 不选：dp[i][j] = dp[i-1][j]
 *      b) 选：dp[i][j] = dp[i-1][j - nums[i-1]]（前提是 j >= nums[i-1]）
 *    - 只要有一种选择能凑出，就是 true
 *    - dp[i][j] = dp[i-1][j] || dp[i-1][j - nums[i-1]]
 * 
 * 5. 初始条件：
 *    - dp[0][0] = true（前0个数，凑出和为0，可以）
 *    - dp[0][j] = false（前0个数，凑出和为 j > 0，不可以）
 *    - dp[i][0] = true（凑出和为0，不选任何数即可）
 * 
 * 6. 最终答案：
 *    - dp[n][target]，n 为数组长度，target = sum / 2
 * 
 * 可视化过程（nums = [1, 5, 11, 5], target = 11）：
 * 
 *        j:  0    1    2    3    4    5    6    7    8    9   10   11
 * i=0 (无):   T    F    F    F    F    F    F    F    F    F    F    F
 * i=1 (1):   T    T    F    F    F    F    F    F    F    F    F    F
 * i=2 (5):   T    T    F    F    F    T    T    F    F    F    F    F
 * i=3 (11):  T    T    F    F    F    T    T    F    F    F    F    T
 * i=4 (5):   T    T    F    F    F    T    T    F    F    F    F    T
 * 
 * 决策过程：
 * - dp[0][0] = true（初始条件）
 * - dp[1][1] = dp[0][0] = true（选1，凑出1）
 * - dp[2][5] = dp[1][0] = true（选5，凑出5）
 * - dp[2][6] = dp[1][1] = true（选1+5，凑出6）
 * - dp[3][11] = dp[2][0] = true（选11，凑出11）
 * - dp[4][11] = dp[3][11] = true（不选第4个5，已经凑出11）
 * 
 * 最终答案：dp[4][11] = true，可以分割
 * 
 * 解题思路：
 * 
 * 方法1：二维DP（经典01背包）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n * target)
 * - 空间复杂度：O(n * target)
 * 
 * 方法2：一维DP（空间优化）⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n * target)
 * - 空间复杂度：O(target)
 * - 关键：内层循环从大到小（倒序）
 * 
 * 易错点：
 * 
 * 1. 边界情况：
 *    - sum 为奇数，直接返回 false
 *    - 数组只有一个元素，返回 false
 *    - 数组中最大元素 > sum / 2，返回 false
 * 
 * 2. 一维DP的循环顺序：
 *    - 必须从大到小遍历 j
 *    - 因为 dp[j] 依赖 dp[j - nums[i]]
 *    - 如果从小到大，dp[j - nums[i]] 已经被更新，会导致重复使用
 * 
 * 3. 状态转移的理解：
 *    - dp[i][j] = dp[i-1][j] || dp[i-1][j - nums[i-1]]
 *    - 不是"相加"，而是"或"
 *    - 因为只要有一种方案能凑出，就是 true
 * 
 * 4. 初始化：
 *    - dp[i][0] = true（凑出0，不选任何数）
 *    - 不要忘记这个初始化
 * 
 * 01背包 vs 完全背包：
 * 
 * | 特性 | 01背包 | 完全背包 |
 * |------|--------|---------|
 * | 物品使用次数 | 最多1次 | 无限次 |
 * | 一维DP循环顺序 | 从大到小（倒序） | 从小到大 |
 * | 状态转移 | dp[j] = max(dp[j], dp[j-w]+v) | dp[j] = max(dp[j], dp[j-w]+v) |
 * | 为什么不同 | 防止重复使用 | 允许重复使用 |
 * 
 * 为什么01背包要倒序？
 * - 因为每个物品只能用一次
 * - 倒序遍历时，dp[j - w] 还没被更新，是"上一行"的值
 * - 这样就保证了每个物品只用一次
 * 
 * 空间优化的理解：
 * 
 * 二维DP：
 * dp[i][j] = dp[i-1][j] || dp[i-1][j - nums[i-1]]
 * 
 * 一维DP：
 * dp[j] = dp[j] || dp[j - nums[i]]
 * 
 * 关键：
 * - dp[j] 的新值依赖 dp[j] 和 dp[j - nums[i]] 的旧值
 * - 倒序遍历时，dp[j - nums[i]] 还没被更新，是旧值
 * - 这样就实现了"滚动数组"的效果
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 698. 划分为k个相等的子集（回溯 + 剪枝）
 * - LeetCode 1049. 最后一块石头的重量 II（01背包变形）
 * - LeetCode 494. 目标和（01背包 + 数学转化）
 * - LeetCode 322. 零钱兑换（完全背包）
 */
public class PartitionEqualSubsetSum {
    
    /**
     * 主方法：判断能否分割成两个和相等的子集
     * 
     * @param nums 正整数数组
     * @return 能否分割
     */
    public boolean canPartition(int[] nums) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：01背包问题
        // 
        // 第1步：计算总和，判断是否为偶数
        // int sum = 0;
        // for (int num : nums) {
        //     sum += num;
        // }
        // if (sum % 2 != 0) return false;  // 奇数无法分割
        // 
        // 第2步：定义状态
        // int target = sum / 2;
        // dp[j] 表示能否凑出和为 j
        // 
        // 第3步：状态转移方程
        // dp[j] = dp[j] || dp[j - nums[i]]
        // 解释：
        // - 对于第 i 个数，有两种选择：
        //   a) 不选：dp[j] 保持不变
        //   b) 选：dp[j] = dp[j - nums[i]]
        // - 只要有一种选择能凑出，就是 true
        // 
        // 第4步：初始条件
        // dp[0] = true（凑出0，不选任何数）
        // 
        // 第5步：计算顺序
        // 外层循环：遍历每个数
        // 内层循环：从 target 到 nums[i]（倒序！）
        // 
        // 第6步：最终答案
        // dp[target]
        
        // 参考实现（一维DP）：
        // int sum = 0;
        // for (int num : nums) {
        //     sum += num;
        // }
        // 
        // // 奇数无法分割
        // if (sum % 2 != 0) {
        //     return false;
        // }
        // 
        // int target = sum / 2;
        // boolean[] dp = new boolean[target + 1];
        // dp[0] = true;  // 初始条件
        // 
        // // 01背包：倒序遍历
        // for (int num : nums) {
        //     for (int j = target; j >= num; j--) {
        //         dp[j] = dp[j] || dp[j - num];
        //     }
        // }
        // 
        // return dp[target];
        
        // 关键理解：
        // 1. 为什么是 dp[j] || dp[j - num]？
        //    - 对于第 i 个数，有两种选择：选或不选
        //    - 不选：dp[j] 保持不变
        //    - 选：dp[j] = dp[j - num]（能凑出 j - num，就能凑出 j）
        //    - 只要有一种选择能凑出，就是 true
        // 
        // 2. 为什么要倒序遍历？
        //    - 因为每个数只能用一次（01背包）
        //    - 倒序遍历时，dp[j - num] 还没被更新，是"上一轮"的值
        //    - 这样就保证了每个数只用一次
        //    - 如果正序遍历，dp[j - num] 已经被更新，会导致重复使用
        // 
        // 3. 为什么 j 从 target 到 num？
        //    - 因为 j < num 时，无法选择当前数
        //    - j >= num 时，才能选择当前数
        // 
        // 4. 这题与零钱兑换的区别？
        //    - 零钱兑换：完全背包，每个硬币可以用无限次，正序遍历
        //    - 分割等和子集：01背包，每个数只能用一次，倒序遍历
        // 
        // 5. 如何优化？
        //    - 提前判断：如果最大元素 > target，直接返回 false
        //    - 排序优化：先排序，从大到小遍历，可以更快找到解
        
        // 在这里编写你的实现代码
        
        //首先，来做一些边界处理
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum += nums[i];
        }
        if(sum%2!=0)//除不尽，返回个p
            return false;
        int target=sum/2;
        //然后这题就成了凑硬币问题，区别在于同一位置的元素只能用一次
        //对于状态转移方程，我打算设计成，当前位置为结尾的子串下，target最接近0但不小于0的余额。
        //这让我想起了一个狗屁算法，叫卡登算法，即对于每一个新元素，检查是应该延续状态，还是重新开始
        //然而这题还有一些要考虑的东西。某个人提醒我，对于不能重复使用元素的背包问题，应当倒序遍历，防止已经用过的面值被重复使用
        //此外，对于硬币问题，就应该遍历每一个没有用到的面值

        //经过学习，以下是01背包问题的标准套路：
        //定义：dp[j] 表示是否能凑出和为 j，那么dp[0]自然为true，因为不需要任何元素
        //转移：对于每个数 num(注意是每个数，这需要遍历)，我们要么选它，要么不选它。
        //如果选它，那么 dp[j] = dp[j - num]。如果不选它，那么 dp[j] 保持原值。
        //其次是关键技巧：倒序遍历，如果正序遍历 j，那么在同一轮里，dp[j - num] 可能已经被更新过，会导致重复使用同一个元素多次。
        boolean[] dp=new boolean[target+1];//dp应初始化为，每个余额都有对应的状态序列
        dp[0]=true;
        for (int num : nums) {//对于每一种面值
            for (int j = target; j >= num; j--) {//遍历，检查每一种状态是否可达，并且由于dp[0]=true，nums=target的情况总是可达的
                dp[j] = dp[j] || dp[j - num];
                //这就是 01 背包的特点：即使循环很多次，真正能更新的状态取决于已有的 true 状态。
                //后续再加入其他元素（比如 2、3、4…），才会逐步把更多的 dp[j] 更新为 true，最终扩展到目标值。
                /*处理元素	新增可达状态（dp[j] = true）	说明
                1	1	因为 dp[0] 已经是 true，所以 dp[1] 更新为 true
                2	2, 3	dp[2] = dp[0]+2，dp[3] = dp[1]+2
                3	3, 4, 5, 6	dp[3] 已经 true，新增 dp[4], dp[5], dp[6]
                4	4, 5, 6, 7, 8, 9, 10	扩展更多状态
                5	5, 6, 7, 8, 9, 10, 11, 12, 13, 14	此时 dp[14] = true，目标达成
                6	更多状态（不再关键）	已经达成目标
                7	更多状态（不再关键）	已经达成目标*/
            }
        }
        return dp[target];
        //正所谓:
        //01背包倒序走，避免元素用两遍
        //完全背包正序行，允许元素用无限
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        PartitionEqualSubsetSum solution = new PartitionEqualSubsetSum();
        
        System.out.println("=== LeetCode 416: 分割等和子集 ===");
        System.out.println("中等题，经典01背包\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        int[] nums1 = {2, 2, 1, 1};
        boolean result1 = solution.canPartition(nums1);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums1));
        System.out.println("输出: " + result1);
        System.out.println("预期: true");
        System.out.println("解释: 数组可以分割成 [1, 5, 5] 和 [11]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        int[] nums2 = {1, 2, 3, 5};
        boolean result2 = solution.canPartition(nums2);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums2));
        System.out.println("输出: " + result2);
        System.out.println("预期: false");
        System.out.println("解释: 数组不能分割成两个和相等的子集");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：单个元素
        System.out.println("测试用例3: 单个元素");
        int[] nums3 = {1};
        boolean result3 = solution.canPartition(nums3);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums3));
        System.out.println("输出: " + result3);
        System.out.println("预期: false");
        System.out.println("解释: 单个元素无法分割");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：两个相同元素
        System.out.println("测试用例4: 两个相同元素");
        int[] nums4 = {1, 1};
        boolean result4 = solution.canPartition(nums4);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums4));
        System.out.println("输出: " + result4);
        System.out.println("预期: true");
        System.out.println("解释: 可以分割成 [1] 和 [1]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：和为奇数
        System.out.println("测试用例5: 和为奇数");
        int[] nums5 = {1, 2, 5};
        boolean result5 = solution.canPartition(nums5);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums5));
        System.out.println("输出: " + result5);
        System.out.println("预期: false");
        System.out.println("解释: 总和为8，无法分成两个和为4的子集");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例6：较大数组
        System.out.println("测试用例6: 较大数组");
        int[] nums6 = {1, 2, 3, 4, 5, 6, 7};
        boolean result6 = solution.canPartition(nums6);
        System.out.println("输入: nums = " + java.util.Arrays.toString(nums6));
        System.out.println("输出: " + result6);
        System.out.println("预期: true");
        System.out.println("解释: 总和为28，可以分成 [1,2,3,4,4] 和 [5,6,7]（错误），正确是 [1,2,4,7] 和 [3,5,6]");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 问题转化：分割等和子集 → 01背包问题");
        System.out.println("2. 状态定义：dp[j] = 能否凑出和为 j");
        System.out.println("3. 状态转移：dp[j] = dp[j] || dp[j - num]");
        System.out.println("4. 初始条件：dp[0] = true");
        System.out.println("5. 关键：内层循环必须倒序（j 从 target 到 num）");
        System.out.println("6. 01背包 vs 完全背包：倒序 vs 正序");
        System.out.println("\n这是01背包的经典题，理解倒序遍历的本质！");
    }
}



