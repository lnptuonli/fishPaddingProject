/**
 * LeetCode 8. 字符串转换整数 (atoi) - String to Integer (atoi)
 * <p>
 * 题目描述：
 * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数。
 * <p>
 * 函数 myAtoi(string s) 的算法如下：
 * <p>
 * 1. 空格：读入字符串并丢弃无用的前导空格（" "）
 * 2. 符号：检查下一个字符（假设还未到字符末尾）为 '-' 还是 '+'。
 * 如果两者都不存在，则假定结果为正。
 * 3. 转换：通过跳过前置零来读取该整数，直到遇到非数字字符或到达字符串的结尾。
 * 如果没有读取数字，则结果为0。
 * 4. 舍入：如果整数数超过 32 位有符号整数范围 [−2^31, 2^31 − 1]，
 * 需要截断这个整数，使其保持在这个范围内。
 * 具体来说，小于 −2^31 的整数应该被舍入为 −2^31，
 * 大于 2^31 − 1 的整数应该被舍入为 2^31 − 1。
 * 5. 返回整数作为最终结果。
 * <p>
 * 示例 1：
 * 输入：s = "42"
 * 输出：42
 * 解释：
 * 步骤1：无前导空格
 * 步骤2：无符号
 * 步骤3：读取 "42"
 * <p>
 * 示例 2：
 * 输入：s = "   -42"
 * 输出：-42
 * 解释：
 * 步骤1：丢弃前导空格 "   "
 * 步骤2：检测到符号 '-'
 * 步骤3：读取 "42"
 * <p>
 * 示例 3：
 * 输入：s = "4193 with words"
 * 输出：4193
 * 解释：
 * 步骤3：读取到 "4193"，遇到空格停止
 * <p>
 * 示例 4：
 * 输入：s = "words and 987"
 * 输出：0
 * 解释：
 * 步骤1：无前导空格
 * 步骤2：无符号
 * 步骤3：遇到非数字字符 'w'，停止，返回0
 * <p>
 * 示例 5：
 * 输入：s = "-91283472332"
 * 输出：-2147483648
 * 解释：
 * 步骤3：读取 "-91283472332"
 * 步骤4：超过范围，截断为 -2^31 = -2147483648
 * <p>
 * 提示：
 * 0 <= s.length <= 200
 * s 由英文字母（大小写）、数字（0-9）、' '、'+'、'-' 和 '.' 组成
 * <p>
 * 核心难点：
 * 1. 处理前导空格
 * 2. 处理正负号（只能有一个，且必须在数字前）
 * 3. 处理前导零
 * 4. 遇到非数字字符立即停止
 * 5. 溢出处理（截断而不是返回0）
 * 6. 边界情况：空字符串、只有空格、只有符号、无效字符等
 * <p>
 * 关键知识点：
 * - Integer.MAX_VALUE = 2147483647
 * - Integer.MIN_VALUE = -2147483648
 * - 溢出检测：在乘以10之前判断
 * - 注意：这道题溢出时要截断，不是返回0！
 * <p>
 * 解题思路：
 * 1. 使用索引遍历字符串
 * 2. 跳过前导空格
 * 3. 检测符号
 * 4. 逐位读取数字，同时检测溢出
 * 5. 遇到非数字字符停止
 */
public class StringToInteger {

    /**
     * 主方法：字符串转整数 (atoi)
     *
     * @param s 输入字符串
     * @return 转换后的整数，溢出时截断到边界值
     */
    public int myAtoi(String s) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================

        // 提示：
        // 1. 处理空字符串
        // 2. 跳过前导空格：while (i < len && s.charAt(i) == ' ')
        // 3. 检测符号：判断是 '+' 还是 '-'
        // 4. 逐位读取数字：
        //    - 判断字符是否为数字：c >= '0' && c <= '9'
        //    - 转换为数字：digit = c - '0'
        //    - 累加结果：result = result * 10 + digit
        // 5. 溢出检测（注意：这题溢出要截断，不是返回0）：
        //    - 正数：if (result > Integer.MAX_VALUE / 10 || 
        //             (result == Integer.MAX_VALUE / 10 && digit > 7))
        //             return Integer.MAX_VALUE;
        //    - 负数：if (result < Integer.MIN_VALUE / 10 || 
        //             (result == Integer.MIN_VALUE / 10 && digit > 8))
        //             return Integer.MIN_VALUE;

        // 在这里编写你的实现代码
        s = s.trim();
        if(s.length()==0){
            return 0;
        }
        int plusorMinus = 1;
        int i = 0;
        if (s.charAt(0) == '-') {
            i = 1;
            plusorMinus = 0;
        } else if (s.charAt(0) == '+') {
            i = 1;
        } else {
            i = 0;
        }

        int result = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            i++;
            int digit;
            if (c >= '0' && c <= '9') {
                digit = c - '0';
            } else
                break;
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && digit > 7))
                return Integer.MAX_VALUE;
            if (result < Integer.MIN_VALUE / 10 ||
                    (result == Integer.MIN_VALUE / 10 && digit > 8))
                return Integer.MIN_VALUE;
            if (plusorMinus == 0)
                result = result * 10 - digit;
            else
                result = result * 10 + digit;
        }

        return result;

        // ============================================
        // TODO: 实现结束
        // ============================================
    }

    /**
     * 辅助方法：判断字符是否为数字
     */
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        StringToInteger solution = new StringToInteger();

        System.out.println("=== LeetCode 8: 字符串转换整数 (atoi) ===");
        System.out.println("注意：32位整数范围 [" + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "]\n");

        // 测试用例1：基本正数
        String test1 = "42";
        int result1 = solution.myAtoi(test1);
        System.out.println("测试用例1:");
        System.out.println("输入: \"" + test1 + "\"");
        System.out.println("输出: " + result1);
        System.out.println("预期: 42");
        System.out.println("通过: " + (result1 == 42));
        System.out.println();

        // 测试用例2：前导空格和负数
        String test2 = "   -42";
        int result2 = solution.myAtoi(test2);
        System.out.println("测试用例2:");
        System.out.println("输入: \"" + test2 + "\"");
        System.out.println("输出: " + result2);
        System.out.println("预期: -42");
        System.out.println("通过: " + (result2 == -42));
        System.out.println();

        // 测试用例3：数字后有文字
        String test3 = "4193 with words";
        int result3 = solution.myAtoi(test3);
        System.out.println("测试用例3:");
        System.out.println("输入: \"" + test3 + "\"");
        System.out.println("输出: " + result3);
        System.out.println("预期: 4193");
        System.out.println("通过: " + (result3 == 4193));
        System.out.println();

        // 测试用例4：文字开头
        String test4 = "words and 987";
        int result4 = solution.myAtoi(test4);
        System.out.println("测试用例4:");
        System.out.println("输入: \"" + test4 + "\"");
        System.out.println("输出: " + result4);
        System.out.println("预期: 0");
        System.out.println("通过: " + (result4 == 0));
        System.out.println();

        // 测试用例5：负数溢出
        String test5 = "-91283472332";
        int result5 = solution.myAtoi(test5);
        System.out.println("测试用例5（负数溢出）:");
        System.out.println("输入: \"" + test5 + "\"");
        System.out.println("输出: " + result5);
        System.out.println("预期: " + Integer.MIN_VALUE + " (截断)");
        System.out.println("通过: " + (result5 == Integer.MIN_VALUE));
        System.out.println();

        // 测试用例6：正数溢出
        String test6 = "91283472332";
        int result6 = solution.myAtoi(test6);
        System.out.println("测试用例6（正数溢出）:");
        System.out.println("输入: \"" + test6 + "\"");
        System.out.println("输出: " + result6);
        System.out.println("预期: " + Integer.MAX_VALUE + " (截断)");
        System.out.println("通过: " + (result6 == Integer.MAX_VALUE));
        System.out.println();

        // 测试用例7：正号
        String test7 = "+123";
        int result7 = solution.myAtoi(test7);
        System.out.println("测试用例7:");
        System.out.println("输入: \"" + test7 + "\"");
        System.out.println("输出: " + result7);
        System.out.println("预期: 123");
        System.out.println("通过: " + (result7 == 123));
        System.out.println();

        // 测试用例8：前导零
        String test8 = "00000123";
        int result8 = solution.myAtoi(test8);
        System.out.println("测试用例8:");
        System.out.println("输入: \"" + test8 + "\"");
        System.out.println("输出: " + result8);
        System.out.println("预期: 123");
        System.out.println("通过: " + (result8 == 123));
        System.out.println();

        // 测试用例9：只有空格
        String test9 = "   ";
        int result9 = solution.myAtoi(test9);
        System.out.println("测试用例9:");
        System.out.println("输入: \"" + test9 + "\"");
        System.out.println("输出: " + result9);
        System.out.println("预期: 0");
        System.out.println("通过: " + (result9 == 0));
        System.out.println();

        // 测试用例10：只有符号
        String test10 = "+-12";
        int result10 = solution.myAtoi(test10);
        System.out.println("测试用例10:");
        System.out.println("输入: \"" + test10 + "\"");
        System.out.println("输出: " + result10);
        System.out.println("预期: 0 (符号后不是数字)");
        System.out.println("通过: " + (result10 == 0));
        System.out.println();

        // 测试用例11：空字符串
        String test11 = "";
        int result11 = solution.myAtoi(test11);
        System.out.println("测试用例11:");
        System.out.println("输入: \"" + test11 + "\"");
        System.out.println("输出: " + result11);
        System.out.println("预期: 0");
        System.out.println("通过: " + (result11 == 0));
        System.out.println();

        // 测试用例12：小数点
        String test12 = "3.14159";
        int result12 = solution.myAtoi(test12);
        System.out.println("测试用例12:");
        System.out.println("输入: \"" + test12 + "\"");
        System.out.println("输出: " + result12);
        System.out.println("预期: 3 (遇到'.'停止)");
        System.out.println("通过: " + (result12 == 3));
        System.out.println();

        // 测试用例13：空格+符号+空格
        String test13 = "  +  123";
        int result13 = solution.myAtoi(test13);
        System.out.println("测试用例13:");
        System.out.println("输入: \"" + test13 + "\"");
        System.out.println("输出: " + result13);
        System.out.println("预期: 0 (符号后有空格)");
        System.out.println("通过: " + (result13 == 0));
        System.out.println();

        // 测试用例14：负零
        String test14 = "-0";
        int result14 = solution.myAtoi(test14);
        System.out.println("测试用例14:");
        System.out.println("输入: \"" + test14 + "\"");
        System.out.println("输出: " + result14);
        System.out.println("预期: 0");
        System.out.println("通过: " + (result14 == 0));
        System.out.println();

        // 测试用例15：边界值
        String test15 = "2147483647";
        int result15 = solution.myAtoi(test15);
        System.out.println("测试用例15:");
        System.out.println("输入: \"" + test15 + "\"");
        System.out.println("输出: " + result15);
        System.out.println("预期: " + Integer.MAX_VALUE);
        System.out.println("通过: " + (result15 == Integer.MAX_VALUE));
        System.out.println();

        // 测试用例16：边界值+1
        String test16 = "2147483648";
        int result16 = solution.myAtoi(test16);
        System.out.println("测试用例16:");
        System.out.println("输入: \"" + test16 + "\"");
        System.out.println("输出: " + result16);
        System.out.println("预期: " + Integer.MAX_VALUE + " (截断)");
        System.out.println("通过: " + (result16 == Integer.MAX_VALUE));
        System.out.println();

        System.out.println("=== 重要提示 ===");
        System.out.println("1. 这道题溢出时要截断到边界值，不是返回0！");
        System.out.println("2. 符号只能有一个，且必须紧跟在空格之后");
        System.out.println("3. 遇到任何非数字字符（包括空格）立即停止");
        System.out.println("4. 前导零可以忽略");
        System.out.println("5. 只有空格、只有符号、空字符串都返回0");
    }
}



