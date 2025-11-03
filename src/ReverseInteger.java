/**
 * LeetCode 7. 整数反转 (Reverse Integer)
 * <p>
 * 题目描述：
 * 给你一个 32 位的有符号整数 x，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围 [−2^31, 2^31 − 1]，就返回 0。
 * <p>
 * 重要约束：
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 * <p>
 * 示例 1：
 * 输入：x = 123
 * 输出：321
 * <p>
 * 示例 2：
 * 输入：x = -123
 * 输出：-321
 * <p>
 * 示例 3：
 * 输入：x = 120
 * 输出：21
 * <p>
 * 示例 4：
 * 输入：x = 0
 * 输出：0
 * <p>
 * 示例 5（溢出情况）：
 * 输入：x = 1534236469
 * 输出：0
 * 解释：反转后为 9646324351，超过了 2^31 - 1 = 2147483647，返回 0
 * <p>
 * 示例 6（负数溢出）：
 * 输入：x = -2147483648
 * 输出：0
 * 解释：反转后为 -8463847412，超过了 -2^31 = -2147483648，返回 0
 * <p>
 * 提示：
 * -2^31 <= x <= 2^31 - 1
 * <p>
 * 核心难点：
 * 1. 如何在不使用 64 位整数的情况下检测溢出？
 * 2. 如何处理负数？
 * 3. 如何处理末尾的零（如 120 → 21）？
 * <p>
 * 关键知识点：
 * - Integer.MAX_VALUE = 2147483647 (2^31 - 1)
 * - Integer.MIN_VALUE = -2147483648 (-2^31)
 * - 溢出检测：在乘以10之前，判断 result > Integer.MAX_VALUE / 10
 * - 负数处理：负数的取模和除法运算与正数一致
 * <p>
 * 解题思路：
 * 1. 逐位反转：通过 % 10 取出最后一位，通过 / 10 去掉最后一位
 * 2. 溢出检测：在每次 result * 10 + digit 之前检查是否会溢出
 * 3. 提前判断：如果 result > MAX/10 或 result < MIN/10，则必定溢出
 */
public class ReverseInteger {

    /**
     * 主方法：整数反转
     *
     * @param x 输入的32位有符号整数
     * @return 反转后的整数，溢出则返回0
     */
    public int reverse(int x) {
        // ============================================
        // 在这里实现你的解法
        // ============================================

        // 提示：
        // 1. 使用 result = result * 10 + x % 10 来构建反转后的数字
        // 2. 在每次乘以10之前，检查是否会溢出
        // 3. 溢出判断条件：
        //    - 如果 result > Integer.MAX_VALUE / 10，则 result * 10 必定溢出
        //    - 如果 result < Integer.MIN_VALUE / 10，则 result * 10 必定溢出
        //    - 如果 result == Integer.MAX_VALUE / 10，还需检查最后一位
        // 4. 负数不需要特殊处理，Java的取模和除法对负数同样适用

        // 在这里编写你的实现代码
        int result = 0;

        while (x != 0) {
            int lastValue = x % 10;
            x = x / 10;
            // 检查是否会溢出
            // 正数溢出检查
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && lastValue > 7)) {
                return 0;
            }
            // 负数溢出检查
            if (result < Integer.MIN_VALUE / 10 ||
                    (result == Integer.MIN_VALUE / 10 && lastValue < -8)) {
                return 0;
            }
            result = result * 10 + lastValue;
        }
        return result;


        // ============================================
        // 实现结束
        // ============================================

    }

    /**
     * 辅助方法：检查整数是否会在反转后溢出（用于验证）
     */
    private boolean willOverflow(int x) {
        try {
            String str = String.valueOf(Math.abs((long) x));
            String reversed = new StringBuilder(str).reverse().toString();
            long result = Long.parseLong(reversed);
            if (x < 0) result = -result;
            return result > Integer.MAX_VALUE || result < Integer.MIN_VALUE;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 辅助方法：使用字符串方式反转（仅用于对比验证，不符合题目要求）
     */
    private int reverseUsingString(int x) {
        try {
            boolean negative = x < 0;
            String str = String.valueOf(Math.abs((long) x));
            String reversed = new StringBuilder(str).reverse().toString();
            long result = Long.parseLong(reversed);
            if (negative) result = -result;

            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                return 0;
            }
            return (int) result;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        ReverseInteger solution = new ReverseInteger();

        System.out.println("=== LeetCode 7: 整数反转 ===");
        System.out.println("注意：32位整数范围 [" + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "]\n");

        // 测试用例1：正数
        int test1 = 123;
        int result1 = solution.reverse(test1);
        System.out.println("测试用例1:");
        System.out.println("输入: " + test1);
        System.out.println("输出: " + result1);
        System.out.println("预期: 321");
        System.out.println("通过: " + (result1 == 321));
        System.out.println();

        // 测试用例2：负数
        int test2 = -123;
        int result2 = solution.reverse(test2);
        System.out.println("测试用例2:");
        System.out.println("输入: " + test2);
        System.out.println("输出: " + result2);
        System.out.println("预期: -321");
        System.out.println("通过: " + (result2 == -321));
        System.out.println();

        // 测试用例3：末尾有零
        int test3 = 120;
        int result3 = solution.reverse(test3);
        System.out.println("测试用例3:");
        System.out.println("输入: " + test3);
        System.out.println("输出: " + result3);
        System.out.println("预期: 21");
        System.out.println("通过: " + (result3 == 21));
        System.out.println();

        // 测试用例4：零
        int test4 = 0;
        int result4 = solution.reverse(test4);
        System.out.println("测试用例4:");
        System.out.println("输入: " + test4);
        System.out.println("输出: " + result4);
        System.out.println("预期: 0");
        System.out.println("通过: " + (result4 == 0));
        System.out.println();

        // 测试用例5：正数溢出
        int test5 = 1534236469;
        int result5 = solution.reverse(test5);
        System.out.println("测试用例5（正数溢出）:");
        System.out.println("输入: " + test5);
        System.out.println("反转应为: 9646324351 (超过 " + Integer.MAX_VALUE + ")");
        System.out.println("输出: " + result5);
        System.out.println("预期: 0");
        System.out.println("通过: " + (result5 == 0));
        System.out.println();

        // 测试用例6：负数溢出
        int test6 = -2147483648;  // Integer.MIN_VALUE
        int result6 = solution.reverse(test6);
        System.out.println("测试用例6（负数溢出）:");
        System.out.println("输入: " + test6 + " (Integer.MIN_VALUE)");
        System.out.println("反转应为: -8463847412 (超过 " + Integer.MIN_VALUE + ")");
        System.out.println("输出: " + result6);
        System.out.println("预期: 0");
        System.out.println("通过: " + (result6 == 0));
        System.out.println();

        // 测试用例7：接近溢出但不溢出
        int test7 = 1463847412;
        int result7 = solution.reverse(test7);
        System.out.println("测试用例7（接近溢出但不溢出）:");
        System.out.println("输入: " + test7);
        System.out.println("反转为: 2147483641 (未超过 " + Integer.MAX_VALUE + ")");
        System.out.println("输出: " + result7);
        System.out.println("预期: 2147483641");
        System.out.println("通过: " + (result7 == 2147483641));
        System.out.println();

        // 测试用例8：单个数字
        int test8 = 9;
        int result8 = solution.reverse(test8);
        System.out.println("测试用例8:");
        System.out.println("输入: " + test8);
        System.out.println("输出: " + result8);
        System.out.println("预期: 9");
        System.out.println("通过: " + (result8 == 9));
        System.out.println();

        // 测试用例9：负数单个数字
        int test9 = -9;
        int result9 = solution.reverse(test9);
        System.out.println("测试用例9:");
        System.out.println("输入: " + test9);
        System.out.println("输出: " + result9);
        System.out.println("预期: -9");
        System.out.println("通过: " + (result9 == -9));
        System.out.println();

        // 测试用例10：多个零
        int test10 = 1000;
        int result10 = solution.reverse(test10);
        System.out.println("测试用例10:");
        System.out.println("输入: " + test10);
        System.out.println("输出: " + result10);
        System.out.println("预期: 1");
        System.out.println("通过: " + (result10 == 1));
        System.out.println();

        // 边界值测试
        System.out.println("=== 边界值测试 ===");
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("Integer.MIN_VALUE = " + Integer.MIN_VALUE);
        System.out.println("MAX_VALUE / 10 = " + (Integer.MAX_VALUE / 10));
        System.out.println("MIN_VALUE / 10 = " + (Integer.MIN_VALUE / 10));
        System.out.println();

        System.out.println("提示：");
        System.out.println("1. 溢出检测的关键：在乘以10之前判断");
        System.out.println("2. 如果 result > 214748364，则 result * 10 必定超过 2147483647");
        System.out.println("3. 如果 result < -214748364，则 result * 10 必定小于 -2147483648");
        System.out.println("4. 如果 result == 214748364，还需要检查最后一位数字");
    }
}

