/**
 * LeetCode 2169. 得到 0 的操作数 - Count Operations to Obtain Zero
 * <p>
 * 题目描述：
 * 给你两个非负整数 num1 和 num2。
 * <p>
 * 每一步操作中，如果 num1 >= num2，你必须用 num1 减 num2；
 * 否则，你必须用 num2 减 num1。
 * <p>
 * 例如：
 * - num1 = 5 且 num2 = 4，应该用 num1 减 num2，因此得到 num1 = 1 和 num2 = 4
 * - num1 = 4 且 num2 = 5，一步操作后，得到 num1 = 4 和 num2 = 1
 * <p>
 * 返回使 num1 = 0 或 num2 = 0 的操作数。
 * <p>
 * 示例 1：
 * 输入：num1 = 2, num2 = 3
 * 输出：3
 * 解释：
 * - 操作 1：num1 = 2, num2 = 3。由于 num1 < num2，num2 减 num1，得到 num1 = 2, num2 = 1
 * - 操作 2：num1 = 2, num2 = 1。由于 num1 >= num2，num1 减 num2，得到 num1 = 1, num2 = 1
 * - 操作 3：num1 = 1, num2 = 1。由于 num1 >= num2，num1 减 num2，得到 num1 = 0, num2 = 1
 * 由于 num1 = 0，不需要再执行任何操作。总共执行 3 步操作，所以返回 3。
 * <p>
 * 示例 2：
 * 输入：num1 = 10, num2 = 10
 * 输出：1
 * 解释：
 * - 操作 1：num1 = 10, num2 = 10。由于 num1 >= num2，num1 减 num2，得到 num1 = 0, num2 = 10
 * 由于 num1 = 0，不需要再执行任何操作。总共执行 1 步操作，所以返回 1。
 * <p>
 * 提示：
 * 0 <= num1, num2 <= 10^5
 * <p>
 * 核心理解：
 * <p>
 * 1. 这道题的本质：
 * - 每次用大的减小的
 * - 直到其中一个变成0
 * - 这不就是辗转相除法（欧几里得算法）吗？
 * <p>
 * 2. 暴力解法：
 * - 模拟整个过程
 * - 每次减一次
 * - 时间复杂度：O(max(num1, num2))
 * - 当 num1 = 10^5, num2 = 1 时，需要 10^5 次操作
 * <p>
 * 3. 优化解法：
 * - 观察：如果 num1 = 10, num2 = 3
 * - 暴力：10-3=7, 7-3=4, 4-3=1, 1<3, 3-1=2, 2-1=1, 1-1=0
 * - 优化：10 / 3 = 3 次，余数 1，然后继续
 * - 本质上就是求 num1 / num2 的次数
 * <p>
 * 4. 与欧几里得算法的关系：
 * - 欧几里得算法求最大公约数（GCD）
 * - 这道题求操作次数
 * - 过程类似，但目标不同
 * <p>
 * 解题思路：
 * <p>
 * 思路1：暴力模拟（简单但可能超时）
 * - 循环：当 num1 != 0 且 num2 != 0
 * - 如果 num1 >= num2，num1 -= num2
 * - 否则，num2 -= num1
 * - 计数操作次数
 * - 时间复杂度：O(max(num1, num2))
 * - 空间复杂度：O(1)
 * <p>
 * 思路2：优化模拟（推荐⭐⭐⭐）
 * - 每次不是减1次，而是减多次
 * - 如果 num1 >= num2，操作次数 += num1 / num2，num1 %= num2
 * - 否则，操作次数 += num2 / num1，num2 %= num1
 * - 时间复杂度：O(log(min(num1, num2)))
 * - 空间复杂度：O(1)
 * <p>
 * 算法流程（优化版）：
 * <p>
 * 1. 初始化操作数 count = 0
 * 2. 循环：当 num1 != 0 且 num2 != 0
 * - 如果 num1 >= num2：
 * * count += num1 / num2
 * * num1 %= num2
 * - 否则：
 * * count += num2 / num1
 * * num2 %= num1
 * 3. 返回 count
 * <p>
 * 易错点：
 * <p>
 * 1. 边界条件：
 * - num1 = 0 或 num2 = 0 时，直接返回 0
 * - num1 = num2 时，只需要 1 次操作
 * <p>
 * 2. 除法和取模：
 * - 注意除数不能为 0
 * - 先判断 num2 != 0 再做除法
 * <p>
 * 3. 暴力解法可能超时：
 * - 当 num1 很大，num2 = 1 时
 * - 需要 num1 次操作
 * <p>
 * 为什么优化版快？
 * <p>
 * 例子：num1 = 10, num2 = 3
 * <p>
 * 暴力版：
 * 10 - 3 = 7  (1次)
 * 7 - 3 = 4   (2次)
 * 4 - 3 = 1   (3次)
 * 3 - 1 = 2   (4次)
 * 2 - 1 = 1   (5次)
 * 1 - 1 = 0   (6次)
 * 总共：6次操作
 * <p>
 * 优化版：
 * 10 / 3 = 3 次，余数 1  (count += 3, num1 = 1)
 * 3 / 1 = 3 次，余数 0   (count += 3, num2 = 0)
 * 总共：6次操作，但只循环了2次！
 */
public class CountOperations {

    /**
     * 主方法：计算操作数
     *
     * @param num1 第一个非负整数
     * @param num2 第二个非负整数
     * @return 使其中一个变为0的操作数
     */
    public int countOperations(int num1, int num2) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================

        // 提示：优化模拟
        // 
        // 边界条件：
        // if (num1 == 0 || num2 == 0) {
        //     return 0;
        // }
        // 
        // int count = 0;
        // 
        // while (num1 != 0 && num2 != 0) {
        //     if (num1 >= num2) {
        //         // num1 减 num2，可以减多次
        //         count += num1 / num2;
        //         num1 %= num2;
        //     } else {
        //         // num2 减 num1，可以减多次
        //         count += num2 / num1;
        //         num2 %= num1;
        //     }
        // }
        // 
        // return count;

        // 关键理解：
        // 为什么可以用除法？
        // - 如果 num1 = 10, num2 = 3
        // - 连续减3次：10-3-3-3 = 1
        // - 等价于：10 - 3*3 = 1
        // - 也就是：10 / 3 = 3 次，余数 1
        // 
        // 为什么像欧几里得算法？
        // - 欧几里得算法：gcd(a, b) = gcd(b, a % b)
        // - 这道题：count(a, b) = a/b + count(b, a%b)
        // - 过程一样，只是目标不同

        // 在这里编写你的实现代码


        //只关心次数，直接想到除法
        int count = 0;

        while (num1 != 0 && num2 != 0) {

            if (num1 >= num2) {
                count += num1 / num2;
                num1 = num1 % num2;

            } else {
                count += num2 / num1;
                num2 = num2 % num1;
            }
        }
        return count;

        // ============================================
        // TODO: 实现结束
        // ============================================
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        CountOperations solution = new CountOperations();

        System.out.println("=== LeetCode 2169: 得到 0 的操作数 ===");
        System.out.println("简单题，但要注意优化\n");

        // 测试用例1：官方示例1
        int result1 = solution.countOperations(2, 3);
        System.out.println("测试用例1:");
        System.out.println("输入: num1 = 2, num2 = 3");
        System.out.println("输出: " + result1);
        System.out.println("预期: 3");
        System.out.println("解释: 2<3 → 3-2=1 (1次)");
        System.out.println("      2>1 → 2-1=1 (2次)");
        System.out.println("      1>=1 → 1-1=0 (3次)");
        System.out.println("通过: " + (result1 == 3));
        System.out.println();

        // 测试用例2：官方示例2
        int result2 = solution.countOperations(10, 10);
        System.out.println("测试用例2:");
        System.out.println("输入: num1 = 10, num2 = 10");
        System.out.println("输出: " + result2);
        System.out.println("预期: 1");
        System.out.println("解释: 10>=10 → 10-10=0 (1次)");
        System.out.println("通过: " + (result2 == 1));
        System.out.println();

        // 测试用例3：边界条件
        int result3 = solution.countOperations(0, 5);
        System.out.println("测试用例3:");
        System.out.println("输入: num1 = 0, num2 = 5");
        System.out.println("输出: " + result3);
        System.out.println("预期: 0");
        System.out.println("解释: num1 已经是 0，不需要操作");
        System.out.println("通过: " + (result3 == 0));
        System.out.println();

        // 测试用例4：大数差距
        int result4 = solution.countOperations(100, 1);
        System.out.println("测试用例4:");
        System.out.println("输入: num1 = 100, num2 = 1");
        System.out.println("输出: " + result4);
        System.out.println("预期: 100");
        System.out.println("解释: 100 / 1 = 100 次");
        System.out.println("通过: " + (result4 == 100));
        System.out.println();

        // 测试用例5：互质数
        int result5 = solution.countOperations(7, 5);
        System.out.println("测试用例5:");
        System.out.println("输入: num1 = 7, num2 = 5");
        System.out.println("输出: " + result5);
        System.out.println("解释: 7-5=2 (1次), 5-2=3 (2次), 3-2=1 (3次)");
        System.out.println("      2-1=1 (4次), 1-1=0 (5次)");
        System.out.println();

        // 测试用例6：较大数字
        int result6 = solution.countOperations(12345, 6789);
        System.out.println("测试用例6:");
        System.out.println("输入: num1 = 12345, num2 = 6789");
        System.out.println("输出: " + result6);
        System.out.println("解释: 测试优化版的性能");
        System.out.println();

        // 测试用例7：斐波那契数
        int result7 = solution.countOperations(89, 55);
        System.out.println("测试用例7:");
        System.out.println("输入: num1 = 89, num2 = 55");
        System.out.println("输出: " + result7);
        System.out.println("解释: 斐波那契数，欧几里得算法的最坏情况");
        System.out.println();

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 简单题，但要注意优化");
        System.out.println("2. 暴力解法：每次减1次，O(max(num1, num2))");
        System.out.println("3. 优化解法：每次减多次，O(log(min(num1, num2)))");
        System.out.println("4. 本质：辗转相除法（欧几里得算法）");
        System.out.println("5. 关键：用除法代替多次减法");
        System.out.println("\n提示：");
        System.out.println("- 暴力版也能过，但优化版更优雅");
        System.out.println("- 注意边界条件：num1 = 0 或 num2 = 0");
        System.out.println("- 这题是欧几里得算法的变形");
    }
}

