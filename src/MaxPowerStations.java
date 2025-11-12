import java.util.Arrays;

/**
 * LeetCode 2528. 最大化城市的最小电量 - Maximize the Minimum Powered City
 * <p>
 * 题目描述：
 * 给你一个下标从 0 开始长度为 n 的整数数组 stations，
 * 其中 stations[i] 表示第 i 座城市的供电站数目。
 * <p>
 * 每个供电站可以在一定范围内给所有城市提供电力。
 * 换句话说，如果给定的范围是 r，在城市 i 处的供电站可以给所有满足
 * |i - j| <= r 且 0 <= i, j <= n - 1 的城市 j 供电。
 * <p>
 * |x| 表示 x 的绝对值。比方说，|7 - 5| = 2，|3 - 10| = 7。
 * <p>
 * 一座城市的电量是所有能给它供电的供电站数目。
 * <p>
 * 政府批准了可以额外建造 k 座供电站，你需要决定这些供电站分别应该建在哪里，
 * 这些供电站与已经存在的供电站有相同的供电范围。
 * <p>
 * 给你两个整数 r 和 k，如果以最优策略建造额外的发电站，
 * 返回所有城市中，最小电量的最大值是多少。
 * <p>
 * 注意：这 k 座供电站可以建在多个城市。
 * <p>
 * 示例 1：
 * 输入：stations = [1,2,4,5,0], r = 1, k = 2
 * 输出：5
 * 解释：
 * 最优方案之一是在城市 1 建造 2 座供电站。
 * 每座城市的电量如下：
 * - 城市 0：1 + 2 = 3
 * - 城市 1：1 + 2 + 4 + 2 = 9
 * - 城市 2：2 + 4 + 5 = 11
 * - 城市 3：4 + 5 + 0 = 9
 * - 城市 4：5 + 0 = 5
 * 最小电量是 3，但我们可以做得更好。
 * <p>
 * 实际上，最优方案是在城市 0 和城市 4 各建 1 座供电站：
 * - 城市 0：1 + 2 + 1 = 4
 * - 城市 1：1 + 2 + 4 = 7
 * - 城市 2：2 + 4 + 5 = 11
 * - 城市 3：4 + 5 + 0 = 9
 * - 城市 4：5 + 0 + 1 = 6
 * 最小电量是 4，但还可以更好。
 * <p>
 * 最优方案是在城市 1 建 2 座：
 * - 城市 0：1 + 2 + 2 = 5
 * - 城市 1：1 + 2 + 4 + 2 = 9
 * - 城市 2：2 + 4 + 5 + 2 = 13
 * - 城市 3：4 + 5 + 0 = 9
 * - 城市 4：5 + 0 = 5
 * 最小电量是 5。
 * <p>
 * 示例 2：
 * 输入：stations = [4,4,4,4], r = 0, k = 3
 * 输出：4
 * 解释：
 * 无论如何建造，每座城市的电量都是 4。
 * <p>
 * 提示：
 * n == stations.length
 * 1 <= n <= 10^5
 * 0 <= stations[i] <= 10^5
 * 0 <= r <= n - 1
 * 0 <= k <= 10^9
 * <p>
 * 核心理解：
 * <p>
 * 1. 这是一道"最大化最小值"问题：
 * - 目标：让所有城市的电量尽可能均衡
 * - 要求：最小电量尽可能大
 * - 典型的二分答案题型
 * <p>
 * 2. 关键洞察：
 * - 如果最小电量可以达到 x，那么也可以达到 x-1, x-2, ...
 * - 如果最小电量无法达到 x，那么也无法达到 x+1, x+2, ...
 * - 这是一个单调性问题，可以用二分查找
 * <p>
 * 3. 供电范围的理解：
 * - 在城市 i 建供电站，可以给 [i-r, i+r] 范围内的城市供电
 * - 城市 j 的电量 = 所有在 [j-r, j+r] 范围内的供电站数量
 * <p>
 * 4. 贪心策略：
 * - 从左到右遍历城市
 * - 如果某个城市电量不足，应该在哪里建供电站？
 * - 答案：尽可能靠右建（i+r位置），这样能同时覆盖更多右边的城市
 * <p>
 * 解题思路：
 * <p>
 * 思路：二分答案 + 贪心验证（推荐⭐⭐⭐）
 * <p>
 * 步骤1：二分答案
 * - 二分最小电量的值 mid
 * - 左边界：0
 * - 右边界：sum(stations) + k（所有供电站都给一个城市）
 * <p>
 * 步骤2：验证是否可行
 * - 给定最小电量 mid，判断用 k 座供电站能否达到
 * - 使用贪心 + 滑动窗口
 * <p>
 * 步骤3：贪心策略
 * - 从左到右遍历每个城市
 * - 计算当前城市的电量（滑动窗口）
 * - 如果电量 < mid，在最右边（i+r）建供电站补足
 * - 如果用的供电站 > k，说明 mid 太大，不可行
 * <p>
 * 算法流程：
 * <p>
 * 1. 初始化：
 * - 计算每个城市的初始电量（滑动窗口求和）
 * - 设置二分边界
 * <p>
 * 2. 二分查找：
 * while (left < right) {
 * mid = (left + right + 1) / 2;
 * if (canAchieve(mid)) {
 * left = mid;  // 可以达到，尝试更大的
 * } else {
 * right = mid - 1;  // 不能达到，尝试更小的
 * }
 * }
 * <p>
 * 3. 验证函数 canAchieve(minPower)：
 * - 复制一份 stations 数组
 * - 用滑动窗口维护当前城市的电量
 * - 从左到右遍历：
 * * 如果电量 < minPower，在 i+r 位置建供电站
 * * 统计建造数量，如果 > k 返回 false
 * - 返回 true
 * <p>
 * 时间复杂度：
 * - 二分：O(log(sum + k))
 * - 每次验证：O(n)
 * - 总体：O(n * log(sum + k))
 * <p>
 * 空间复杂度：O(n)
 * <p>
 * 关键技巧：
 * <p>
 * 1. 滑动窗口计算电量：
 * - 城市 i 的电量 = sum(stations[i-r] 到 stations[i+r])
 * - 用差分数组或滑动窗口优化
 * <p>
 * 2. 贪心建站位置：
 * - 在 i+r 位置建站（尽可能靠右）
 * - 这样能同时覆盖 [i, i+2r] 范围的城市
 * <p>
 * 3. 差分数组优化：
 * - 在位置 i 建站，影响 [i-r, i+r]
 * - 用差分数组快速更新范围
 * <p>
 * 易错点：
 * <p>
 * 1. 二分边界：
 * - 右边界不是 max(stations) + k
 * - 应该是 sum(stations) + k
 * <p>
 * 2. 滑动窗口的边界处理：
 * - 左边界：max(0, i-r)
 * - 右边界：min(n-1, i+r)
 * <p>
 * 3. 贪心建站位置：
 * - 不是在 i 位置建
 * - 应该在 min(n-1, i+r) 位置建
 * <p>
 * 4. 整数溢出：
 * - sum(stations) + k 可能超过 int
 * - 使用 long 类型
 * <p>
 * 为什么这题是困难？
 * <p>
 * 1. 需要识别出"最大化最小值"模型
 * 2. 需要想到二分答案
 * 3. 需要设计贪心策略验证
 * 4. 需要用滑动窗口优化
 * 5. 边界条件复杂
 */
public class MaxPowerStations {

    /**
     * 主方法：最大化城市的最小电量
     *
     * @param stations 每个城市的供电站数量
     * @param r        供电范围
     * @param k        可以建造的供电站数量
     * @return 最小电量的最大值
     */
    public long maxPower(int[] stations, int r, int k) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================

        // 提示：二分答案 + 贪心验证
        // 
        // 步骤1：计算初始电量（滑动窗口）
        // int n = stations.length;
        // long[] power = new long[n];
        // 
        // // 初始化第一个城市的电量
        // for (int i = 0; i <= Math.min(n - 1, r); i++) {
        //     power[0] += stations[i];
        // }
        // 
        // // 滑动窗口计算其他城市的电量
        // for (int i = 1; i < n; i++) {
        //     power[i] = power[i - 1];
        //     // 右边界进入窗口
        //     if (i + r < n) {
        //         power[i] += stations[i + r];
        //     }
        //     // 左边界离开窗口
        //     if (i - r - 1 >= 0) {
        //         power[i] -= stations[i - r - 1];
        //     }
        // }
        // 
        // 步骤2：二分答案
        // long left = 0;
        // long right = 0;
        // for (long p : power) {
        //     right += p;
        // }
        // right += k;  // 最大可能：所有供电站都给一个城市
        // 
        // while (left < right) {
        //     long mid = left + (right - left + 1) / 2;
        //     if (canAchieve(stations, power, r, k, mid)) {
        //         left = mid;  // 可以达到，尝试更大的
        //     } else {
        //         right = mid - 1;  // 不能达到，尝试更小的
        //     }
        // }
        // 
        // return left;

        // 步骤3：验证函数
        // private boolean canAchieve(int[] stations, long[] power, int r, int k, long minPower) {
        //     int n = stations.length;
        //     long[] add = new long[n];  // 记录额外建造的供电站
        //     long used = 0;  // 已使用的供电站数量
        //     long currentPower = power[0];  // 当前城市的电量
        //     
        //     for (int i = 0; i < n; i++) {
        //         // 更新当前城市的电量
        //         if (i > 0) {
        //             // 右边界进入
        //             if (i + r < n) {
        //                 currentPower += stations[i + r] + add[i + r];
        //             }
        //             // 左边界离开
        //             if (i - r - 1 >= 0) {
        //                 currentPower -= stations[i - r - 1] + add[i - r - 1];
        //             }
        //         }
        //         
        //         // 如果电量不足，建供电站
        //         if (currentPower < minPower) {
        //             long need = minPower - currentPower;
        //             used += need;
        //             
        //             if (used > k) {
        //                 return false;  // 超过预算
        //             }
        //             
        //             // 在最右边建站（贪心）
        //             int pos = Math.min(n - 1, i + r);
        //             add[pos] += need;
        //             currentPower += need;
        //         }
        //     }
        //     
        //     return true;
        // }

        // 关键理解：
        // 为什么二分答案？
        // - 如果最小电量可以是 x，那么也可以是 x-1
        // - 如果最小电量不能是 x，那么也不能是 x+1
        // - 单调性 → 二分查找
        // 
        // 为什么在 i+r 建站？
        // - 要让城市 i 的电量增加
        // - 在 [i, i+r] 任何位置建站都能覆盖城市 i
        // - 在 i+r 建站，还能覆盖 [i+1, i+2r] 的城市
        // - 贪心：尽可能靠右，覆盖更多未来的城市
        // 
        // 滑动窗口的作用？
        // - 城市 i 的电量 = sum(stations[i-r] 到 stations[i+r])
        // - 每次移动，只需要加上右边新进入的，减去左边离开的
        // - 时间复杂度从 O(n*r) 降到 O(n)

        // 在这里编写你的实现代码
        //1.计算每个城市的初始电量
        long[] power = new long[stations.length];
        sumupPower(stations, power, r);

        //2.最大化最小值问题：所有电站新增完毕后，尽可能让电量最小的城市，拥有电量尽可能多
        //使用遍历方式逐个验证，首先确认遍历的边界：
        long minPower = Arrays.stream(power).min().getAsLong();
        long maxPower = Arrays.stream(power).max().getAsLong() + k;
        while (minPower < maxPower) {
            long mid = minPower + (maxPower - minPower + 1) / 2;
            if (canAchieve(stations, power, r, k, mid)) {
                minPower = mid;  // 可以达到，尝试更大的
            } else {
                maxPower = mid - 1;  // 不能达到，尝试更小的
            }
        }
        return minPower;


        // ============================================
        // 实现结束
        // ============================================
    }

    private void sumupPower(int[] stations, long[] power, int r) {
        int n = stations.length;

        // 初始化第一个城市的电量(n-1为整个范围，r为影响范围，取小的，因为影响范围可能大于全部边界)
        for (int i = 0; i <= Math.min(n - 1, r); i++) {
            power[0] += stations[i];
        }

        // 滑动窗口计算其他城市
        for (int i = 1; i < n; i++) {
            power[i] = power[i - 1];  // 继承上一个城市的电量

            // 右边界进入窗口：
            // 城市 i 的供电范围是 [i - r, i + r]
            // 相比城市 i - 1，右边多了一个 i + r
            // 所以把 stations[i + r] 加进来
            if (i + r < n) {
                power[i] += stations[i + r];
            }

            // 左边界离开窗口
            // 相比城市i-1，城市i的左边少了一个 i - r - 1
            // 把它从电量中减去
            if (i - r - 1 >= 0) {
                power[i] -= stations[i - r - 1];
            }
        }
    }

    /**
     * 验证函数：判断是否可以达到最小电量 minPower
     * （可以在这里实现）
     */
    private boolean canAchieve(int[] stations, long[] power, int r, int k, long minPower) {
        int n = stations.length;
        long[] add = new long[n];  // 记录额外建造的供电站
        long used = 0;  // 已使用的供电站数量
        long currentPower = power[0];  // 当前城市的电量

        for (int i = 0; i < n; i++) {
            // 滑动窗口更新当前城市的电量
            if (i > 0) {
                // 右边界进入
                if (i + r < n) {
                    currentPower += stations[i + r] + add[i + r];
                }
                // 左边界离开
                if (i - r - 1 >= 0) {
                    currentPower -= stations[i - r - 1] + add[i - r - 1];
                }
            }

            // 如果电量不足，建供电站
            if (currentPower < minPower) {
                long need = minPower - currentPower;
                used += need;

                if (used > k) {
                    return false;  // 超过预算
                }

                // 贪心：在最右边建站（i+r位置）
                int pos = Math.min(n - 1, i + r);
                add[pos] += need;
                currentPower += need;
            }
        }

        return true;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        MaxPowerStations solution = new MaxPowerStations();

        System.out.println("=== LeetCode 2528: 最大化城市的最小电量 ===");
        System.out.println("二分答案 + 贪心 + 滑动窗口\n");

        // 测试用例1：官方示例1
        int[] test1 = {1, 2, 4, 5, 0};
        int r1 = 1;
        int k1 = 2;
        long result1 = solution.maxPower(test1, r1, k1);
        System.out.println("测试用例1:");
        System.out.println("输入: stations = [1,2,4,5,0], r = 1, k = 2");
        System.out.println("输出: " + result1);
        System.out.println("预期: 5");
        System.out.println("通过: " + (result1 == 5));
        System.out.println();

        // 测试用例2：官方示例2
        int[] test2 = {4, 4, 4, 4};
        int r2 = 0;
        int k2 = 3;
        long result2 = solution.maxPower(test2, r2, k2);
        System.out.println("测试用例2:");
        System.out.println("输入: stations = [4,4,4,4], r = 0, k = 3");
        System.out.println("输出: " + result2);
        System.out.println("预期: 4");
        System.out.println("通过: " + (result2 == 4));
        System.out.println();

        // 测试用例3：简单情况
        int[] test3 = {1, 1, 1};
        int r3 = 1;
        int k3 = 3;
        long result3 = solution.maxPower(test3, r3, k3);
        System.out.println("测试用例3:");
        System.out.println("输入: stations = [1,1,1], r = 1, k = 3");
        System.out.println("输出: " + result3);
        System.out.println("解释: 初始电量 [2,3,2]，在两端各建1站，中间建1站 → [4,5,4]");
        System.out.println();

        // 测试用例4：k=0
        int[] test4 = {1, 2, 3};
        int r4 = 1;
        int k4 = 0;
        long result4 = solution.maxPower(test4, r4, k4);
        System.out.println("测试用例4:");
        System.out.println("输入: stations = [1,2,3], r = 1, k = 0");
        System.out.println("输出: " + result4);
        System.out.println("解释: 不能建新站，初始电量 [3,6,5]，最小值是3");
        System.out.println();

        // 测试用例5：大范围
        int[] test5 = {0, 0, 0, 0, 0};
        int r5 = 2;
        int k5 = 5;
        long result5 = solution.maxPower(test5, r5, k5);
        System.out.println("测试用例5:");
        System.out.println("输入: stations = [0,0,0,0,0], r = 2, k = 5");
        System.out.println("输出: " + result5);
        System.out.println("解释: 所有站都建在中间位置");
        System.out.println();

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 识别'最大化最小值'模型 → 二分答案");
        System.out.println("2. 贪心策略：在 i+r 位置建站（尽可能靠右）");
        System.out.println("3. 滑动窗口：快速计算每个城市的电量");
        System.out.println("4. 时间复杂度：O(n * log(sum + k))");
        System.out.println("5. 这是一道困难题，综合了多种算法技巧");
        System.out.println("\n提示：");
        System.out.println("- 先理解二分答案的思想");
        System.out.println("- 再理解贪心建站的策略");
        System.out.println("- 最后实现滑动窗口优化");
        System.out.println("- 如果觉得太难，可以先做一些二分答案的简单题");
    }
}

