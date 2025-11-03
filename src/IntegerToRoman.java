import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 12. 整数转罗马数字 - Integer to Roman
 * 
 * 题目描述：
 * 罗马数字是通过添加从最高到最低的小数位值的转换而形成的。
 * 将小数位值转换为罗马数字有以下规则：
 * 
 * 规则1：如果该值不是以 4 或 9 开头，请选择可以从输入中减去的最大值的符号，
 *       将该符号附加到结果，减去其值，然后将其余部分转换为罗马数字。
 * 
 * 规则2：如果该值以 4 或 9 开头，使用减法形式，表示从以下符号中减去一个符号。
 *       例如：4 是 5 (V) 减 1 (I): IV
 *            9 是 10 (X) 减 1 (I): IX
 *       仅使用以下减法形式：4 (IV)，9 (IX)，40 (XL)，90 (XC)，400 (CD)，900 (CM)
 * 
 * 规则3：只有 10 的次方（I, X, C, M）最多可以连续附加 3 次以代表 10 的倍数。
 *       你不能多次附加 5 (V)，50 (L) 或 500 (D)。
 *       如果需要将符号附加 4 次，请使用减法形式。
 * 
 * 给定一个整数，将其转换为罗马数字。
 * 
 * 罗马数字符号表：
 * ┌─────┬────┬────┬────┬────┬────┬────┬────┐
 * │  M  │ D  │ C  │ L  │ X  │ V  │ I  │    │
 * ├─────┼────┼────┼────┼────┼────┼────┼────┤
 * │1000 │500 │100 │ 50 │ 10 │ 5  │ 1  │    │
 * └─────┴────┴────┴────┴────┴────┴────┴────┘
 * 
 * 特殊组合（减法形式）：
 * ┌─────┬─────┬─────┬─────┬─────┬─────┐
 * │ CM  │ CD  │ XC  │ XL  │ IX  │ IV  │
 * ├─────┼─────┼─────┼─────┼─────┼─────┤
 * │ 900 │ 400 │ 90  │ 40  │ 9   │ 4   │
 * └─────┴─────┴─────┴─────┴─────┴─────┘
 * 
 * 示例 1：
 * 输入：num = 3
 * 输出："III"
 * 解释：3 = III
 * 
 * 示例 2：
 * 输入：num = 4
 * 输出："IV"
 * 解释：4 = 5 - 1 = IV
 * 
 * 示例 3：
 * 输入：num = 9
 * 输出："IX"
 * 解释：9 = 10 - 1 = IX
 * 
 * 示例 4：
 * 输入：num = 58
 * 输出："LVIII"
 * 解释：50 + 5 + 3 = L + V + III
 * 
 * 示例 5：
 * 输入：num = 1994
 * 输出："MCMXCIV"
 * 解释：1000 + 900 + 90 + 4 = M + CM + XC + IV
 * 
 * 示例 6：
 * 输入：num = 3999
 * 输出："MMMCMXCIX"
 * 解释：3000 + 900 + 90 + 9 = MMM + CM + XC + IX
 * 
 * 提示：
 * 1 <= num <= 3999
 * 
 * 核心理解：
 * 
 * 1. 贪心策略：
 *    - 从最大的罗马数字开始匹配
 *    - 能用多少次就用多少次
 *    - 然后继续匹配下一个较小的数字
 * 
 * 2. 为什么贪心是对的？
 *    - 罗马数字是十进制的符号表示
 *    - 优先使用大的符号，可以最快地"消耗"数字
 *    - 不会错过更优解（因为每个符号都是独立的）
 * 
 * 3. 关键技巧：
 *    - 将所有可能的符号（包括减法形式）放入数组
 *    - 按从大到小排序
 *    - 依次尝试每个符号
 * 
 * 4. 数字分解示例：
 *    1994 的转换过程：
 *    1994 → M(1000) → 994
 *    994  → CM(900) → 94
 *    94   → XC(90)  → 4
 *    4    → IV(4)   → 0
 *    结果：MCMXCIV
 * 
 * 解题思路：
 * 
 * 思路1：贪心算法（推荐⭐⭐⭐）
 * - 建立值-符号映射表（包含所有13个符号：7个基本 + 6个减法形式）
 * - 按值从大到小排列
 * - 对每个值：
 *   * 计算能使用多少次：count = num / value
 *   * 添加对应次数的符号到结果
 *   * 减去已使用的值：num %= value
 * - 时间复杂度：O(1)，因为符号数量固定
 * - 空间复杂度：O(1)
 * 
 * 思路2：模拟法（思路类似）
 * - 分别处理千位、百位、十位、个位
 * - 每一位根据规则生成对应的罗马数字
 * - 时间复杂度：O(1)
 * - 空间复杂度：O(1)
 * 
 * 易错点：
 * 1. 遗漏减法形式：必须包含 IV、IX、XL、XC、CD、CM
 * 2. 顺序错误：必须从大到小遍历
 * 3. 重复使用限制：
 *    - I、X、C、M 最多连续3次
 *    - V、L、D 不能重复
 *    - 4和9必须用减法形式
 * 4. 边界条件：num = 1 和 num = 3999
 */
public class IntegerToRoman {
    
    /**
     * 主方法：整数转罗马数字
     * 
     * @param num 输入的整数 (1 <= num <= 3999)
     * @return 罗马数字字符串
     */
    public String intToRoman(int num) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：贪心算法
        // 
        // 步骤1：定义值和符号的映射（从大到小）
        // int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        // String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        // 
        // 步骤2：使用 StringBuilder 构建结果
        // StringBuilder result = new StringBuilder();
        // 
        // 步骤3：遍历每个值
        // for (int i = 0; i < values.length; i++) {
        //     // 计算当前值可以使用多少次
        //     while (num >= values[i]) {
        //         result.append(symbols[i]);
        //         num -= values[i];
        //     }
        //     // 或者用除法：
        //     // int count = num / values[i];
        //     // for (int j = 0; j < count; j++) {
        //     //     result.append(symbols[i]);
        //     // }
        //     // num %= values[i];
        // }
        // 
        // 步骤4：返回结果
        // return result.toString();
        
        // 关键理解：
        // 为什么要包含减法形式（IV、IX等）？
        // - 因为4不能写成IIII，必须是IV
        // - 如果不在映射表中包含IV，就需要额外的逻辑判断
        // - 直接包含所有13个符号，代码更简洁
        
        // 为什么从大到小遍历？
        // - 贪心策略：优先使用大的符号
        // - 例如：900 应该用 CM，而不是 DCCCC
        
        // 在这里编写你的实现代码
        
        // 定义值和符号的映射（从大到小）
        // 包含13个符号：7个基本 + 6个减法形式
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        
        StringBuilder result = new StringBuilder();
        
        // 贪心算法：从大到小遍历每个符号，这个算法更偏向于解析每个符号的用处，而不是关注余额
        for (int i = 0; i < values.length; i++) {
            // 当前符号能用多少次就用多少次
            while (num >= values[i]) {
                result.append(symbols[i]);
                num -= values[i];
            }
            // 当前符号用完后，自动进入下一个更小的符号
        }
        
        return result.toString();

        // ============================================
        // 以下是我的实现：
        //首先我认为这题涉及高效的映射，所以估计需要map
        //但是我后来发现map是无序的，所以无奈只能作罢，转而使用这种方式
/*        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        //贪心算法
        int balance= num;
        StringBuilder resultString= new StringBuilder();
        while (balance>0)
        {
            for(int i=0;i<values.length;i++){
                //如果减去这个指针位置的数值大于等于0，就至少再尝试一轮
                if((balance-values[i])>=0){
                    resultString.append(symbols[i]);
                    balance=balance-values[i];
                    //指针前移一位，再次尝试
                    i--;
                    continue;
                }
                else{
                    //这个continue没必要，但是能保证结构完整
                    continue;
                }
            }
        }
        return resultString.toString();*/
        // ============================================

    }
    
    /**
     * 辅助方法：解析罗马数字的组成（用于可视化）
     * 
     * @param num 输入的整数
     * @param roman 对应的罗马数字
     */
    private void explainConversion(int num, String roman) {
        System.out.println("\n详细转换过程：");
        System.out.println("输入：" + num);
        
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        
        int remaining = num;
        StringBuilder process = new StringBuilder();
        
        for (int i = 0; i < values.length; i++) {
            int count = remaining / values[i];
            if (count > 0) {
                String part = "";
                for (int j = 0; j < count; j++) {
                    part += symbols[i];
                }
                System.out.printf("  %d = %d × %s (%s)\n", 
                                count * values[i], count, symbols[i], part);
                process.append(part);
                remaining %= values[i];
            }
        }
        
        System.out.println("结果：" + process.toString());
        System.out.println("验证：" + process.toString().equals(roman));
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        IntegerToRoman solution = new IntegerToRoman();
        
        System.out.println("=== LeetCode 12: 整数转罗马数字 ===");
        System.out.println("罗马数字规则：");
        System.out.println("  基本符号：I=1, V=5, X=10, L=50, C=100, D=500, M=1000");
        System.out.println("  减法形式：IV=4, IX=9, XL=40, XC=90, CD=400, CM=900");
        System.out.println("  重复规则：I/X/C/M最多3次，V/L/D不重复\n");
        
        // 测试用例1：简单情况
        int test1 = 3;
        String result1 = solution.intToRoman(test1);
        System.out.println("测试用例1:");
        System.out.println("输入: " + test1);
        System.out.println("输出: " + result1);
        System.out.println("预期: III");
        System.out.println("通过: " + result1.equals("III"));
        System.out.println();
        
        // 测试用例2：减法形式 - 4
        int test2 = 4;
        String result2 = solution.intToRoman(test2);
        System.out.println("测试用例2:");
        System.out.println("输入: " + test2);
        System.out.println("输出: " + result2);
        System.out.println("预期: IV");
        System.out.println("通过: " + result2.equals("IV"));
        System.out.println();
        
        // 测试用例3：减法形式 - 9
        int test3 = 9;
        String result3 = solution.intToRoman(test3);
        System.out.println("测试用例3:");
        System.out.println("输入: " + test3);
        System.out.println("输出: " + result3);
        System.out.println("预期: IX");
        System.out.println("通过: " + result3.equals("IX"));
        System.out.println();
        
        // 测试用例4：组合情况
        int test4 = 58;
        String result4 = solution.intToRoman(test4);
        System.out.println("测试用例4:");
        System.out.println("输入: " + test4);
        System.out.println("输出: " + result4);
        System.out.println("预期: LVIII (50 + 5 + 3)");
        System.out.println("通过: " + result4.equals("LVIII"));
        if (result4.equals("LVIII")) {
            solution.explainConversion(test4, result4);
        }
        System.out.println();
        
        // 测试用例5：复杂情况
        int test5 = 1994;
        String result5 = solution.intToRoman(test5);
        System.out.println("测试用例5:");
        System.out.println("输入: " + test5);
        System.out.println("输出: " + result5);
        System.out.println("预期: MCMXCIV (1000 + 900 + 90 + 4)");
        System.out.println("通过: " + result5.equals("MCMXCIV"));
        if (result5.equals("MCMXCIV")) {
            solution.explainConversion(test5, result5);
        }
        System.out.println();
        
        // 测试用例6：最大值
        int test6 = 3999;
        String result6 = solution.intToRoman(test6);
        System.out.println("测试用例6:");
        System.out.println("输入: " + test6);
        System.out.println("输出: " + result6);
        System.out.println("预期: MMMCMXCIX (3000 + 900 + 90 + 9)");
        System.out.println("通过: " + result6.equals("MMMCMXCIX"));
        System.out.println();
        
        // 测试用例7：边界 - 最小值
        int test7 = 1;
        String result7 = solution.intToRoman(test7);
        System.out.println("测试用例7:");
        System.out.println("输入: " + test7);
        System.out.println("输出: " + result7);
        System.out.println("预期: I");
        System.out.println("通过: " + result7.equals("I"));
        System.out.println();
        
        // 测试用例8：包含所有减法形式
        int test8 = 3444;
        String result8 = solution.intToRoman(test8);
        System.out.println("测试用例8:");
        System.out.println("输入: " + test8);
        System.out.println("输出: " + result8);
        System.out.println("预期: MMMCDXLIV (3000 + 400 + 40 + 4)");
        System.out.println("通过: " + result8.equals("MMMCDXLIV"));
        System.out.println();
        
        // 测试用例9：整百整千
        int test9 = 2500;
        String result9 = solution.intToRoman(test9);
        System.out.println("测试用例9:");
        System.out.println("输入: " + test9);
        System.out.println("输出: " + result9);
        System.out.println("预期: MMD (2000 + 500)");
        System.out.println("通过: " + result9.equals("MMD"));
        System.out.println();
        
        // 测试用例10：连续相同符号
        int test10 = 3888;
        String result10 = solution.intToRoman(test10);
        System.out.println("测试用例10:");
        System.out.println("输入: " + test10);
        System.out.println("输出: " + result10);
        System.out.println("预期: MMMDCCCLXXXVIII (3000 + 800 + 80 + 8)");
        System.out.println("通过: " + result10.equals("MMMDCCCLXXXVIII"));
        System.out.println();
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 贪心策略：从最大的符号开始匹配");
        System.out.println("2. 符号表包含：7个基本符号 + 6个减法形式 = 13个");
        System.out.println("3. 必须按从大到小的顺序遍历");
        System.out.println("4. 时间复杂度：O(1) - 固定13次循环");
        System.out.println("5. 减法形式必须包含在映射表中，否则需要额外判断");
    }
}

