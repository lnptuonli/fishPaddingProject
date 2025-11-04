import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 13. 罗马数字转整数 - Roman to Integer
 * 
 * 题目描述：
 * 罗马数字包含以下七种字符: I, V, X, L, C, D 和 M。
 * 
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。
 * 27 写做  XXVII, 即为 XX + V + II 。
 * 
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，
 * 例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。
 * 同样地，数字 9 表示为 IX。
 * 
 * 这个特殊的规则只适用于以下六种情况：
 * - I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * - X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * - C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 
 * 给定一个罗马数字，将其转换成整数。
 * 
 * 示例 1：
 * 输入: s = "III"
 * 输出: 3
 * 解释: III = 3
 * 
 * 示例 2：
 * 输入: s = "IV"
 * 输出: 4
 * 解释: IV = 5 - 1 = 4
 * 
 * 示例 3：
 * 输入: s = "IX"
 * 输出: 9
 * 解释: IX = 10 - 1 = 9
 * 
 * 示例 4：
 * 输入: s = "LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3
 * 
 * 示例 5：
 * 输入: s = "MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4
 * 
 * 提示：
 * 1 <= s.length <= 15
 * s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
 * 题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
 * 
 * 核心理解：
 * 
 * 1. 罗马数字的基本规则：
 *    - 从左到右累加
 *    - 大数字在前，小数字在后：正常相加（如 VI = 5 + 1 = 6）
 *    - 小数字在前，大数字在后：减法形式（如 IV = 5 - 1 = 4）
 * 
 * 2. 关键洞察：
 *    - 如果当前字符代表的值 < 下一个字符代表的值，说明是减法形式
 *    - 如果当前字符代表的值 >= 下一个字符代表的值，说明是正常相加
 * 
 * 3. 解题关键：
 *    - 遍历字符串，比较相邻两个字符的大小关系
 *    - 小的在大的前面：减去小的（如 IV 中的 I）
 *    - 其他情况：加上当前值
 * 
 * 4. 转换过程示例：
 *    "MCMXCIV" 的解析：
 *    M(1000) > C(100)  → 加 1000 → 总和 = 1000
 *    C(100)  < M(1000) → 减 100  → 总和 = 900
 *    M(1000) > X(10)   → 加 1000 → 总和 = 1900
 *    X(10)   < C(100)  → 减 10   → 总和 = 1890
 *    C(100)  > I(1)    → 加 100  → 总和 = 1990
 *    I(1)    < V(5)    → 减 1    → 总和 = 1989
 *    V(5)    (最后)    → 加 5    → 总和 = 1994
 * 
 * 解题思路：
 * 
 * 思路1：从左到右遍历 + 比较相邻字符（推荐⭐⭐⭐）
 * - 遍历字符串的每个字符
 * - 获取当前字符和下一个字符的数值
 * - 如果当前值 < 下一个值，减去当前值（减法形式）
 * - 否则，加上当前值
 * - 时间复杂度：O(n)，n为字符串长度
 * - 空间复杂度：O(1)
 * 
 * 思路2：先替换减法形式，再累加
 * - 将 "IV"、"IX"、"XL"、"XC"、"CD"、"CM" 替换为特殊符号
 * - 然后逐个累加
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(n)，需要创建新字符串
 * 
 * 思路3：从右到左遍历
 * - 从右向左遍历，维护一个最大值
 * - 如果当前值 < 最大值，减去当前值
 * - 否则，加上当前值并更新最大值
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(1)
 * 
 * 易错点：
 * 1. 边界处理：遍历到最后一个字符时，没有"下一个字符"
 * 2. 减法形式判断：要比较当前和下一个，不是当前和上一个
 * 3. 字符到数值的映射：可以用 HashMap 或 switch
 * 4. 只有6种减法形式：IV、IX、XL、XC、CD、CM，不要过度判断
 */
public class RomanToInteger {
    
    /**
     * 主方法：罗马数字转整数
     * 
     * @param s 罗马数字字符串
     * @return 对应的整数值
     */
    public int romanToInt(String s) {

        
        // 提示：从左到右遍历 + 比较相邻字符
        // 
        // 步骤1：建立字符到数值的映射
        // 方法1：使用 HashMap
        // Map<Character, Integer> map = new HashMap<>();
        // map.put('I', 1);
        // map.put('V', 5);
        // map.put('X', 10);
        // map.put('L', 50);
        // map.put('C', 100);
        // map.put('D', 500);
        // map.put('M', 1000);
        // 
        // 方法2：使用辅助方法
        // private int getValue(char c) {
        //     switch(c) {
        //         case 'I': return 1;
        //         case 'V': return 5;
        //         case 'X': return 10;
        //         case 'L': return 50;
        //         case 'C': return 100;
        //         case 'D': return 500;
        //         case 'M': return 1000;
        //         default: return 0;
        //     }
        // }
        // 
        // 步骤2：遍历字符串
        // int result = 0;
        // for (int i = 0; i < s.length(); i++) {
        //     int current = getValue(s.charAt(i));
        //     
        //     // 判断是否是最后一个字符
        //     if (i < s.length() - 1) {
        //         int next = getValue(s.charAt(i + 1));
        //         
        //         // 如果当前值 < 下一个值，说明是减法形式
        //         if (current < next) {
        //             result -= current;  // 减去当前值
        //         } else {
        //             result += current;  // 加上当前值
        //         }
        //     } else {
        //         // 最后一个字符，直接加上
        //         result += current;
        //     }
        // }
        // 
        // 步骤3：返回结果
        // return result;
        
        // 关键理解：
        // 为什么比较当前和下一个？
        // - IV：I(1) < V(5)，所以 I 要减去
        // - VI：V(5) > I(1)，所以 V 要加上
        // 
        // 为什么是减去而不是特殊处理？
        // - IV = -1 + 5 = 4 ✓
        // - IX = -1 + 10 = 9 ✓
        // - 这样可以统一处理，不需要特判6种情况
        
        // 在这里编写你的实现代码
        //
        // ============================================
        // 实现开始
        // ============================================
        //实际上我认为，这题关键就是判断每个字符的正负号就完事了
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int[] multElem = new int[s.length()];
        for(int i=0;i<s.length();i++){
            //如果遍历尚未到末尾，且后一字符的值较大，说明当前值应为负号
            //此处我使用的是手动拆箱，实际上Java 编译器会在需要基本类型（如 int）的地方，自动将包装类型（如 Integer）转换为对应的基本类型。也就是所谓的自动拆箱
            //赋值给基本类型变量、用于算术运算、用于比较操作这些地方，都会使用自动拆箱
            //需要特别主意的是，自动拆箱一定保证值不为null，否则会抛异常
            if(i+1<s.length() && map.get(s.charAt(i+1)).intValue()>map.get(s.charAt(i)).intValue()){
                multElem[i]=-1;
            }
            else
                multElem[i]=1;
        }
        int sum=0;
        for(int i=0;i<s.length();i++){
            sum+=map.get(s.charAt(i)).intValue()*multElem[i];
        }

        return sum;  // 记得返回结果
        // ============================================
        // 实现结束
        // ============================================

    }
    
    /**
     * 辅助方法：获取罗马字符对应的数值
     * 
     * @param c 罗马字符
     * @return 对应的数值
     */
    private int getValue(char c) {
        switch(c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
    
    /**
     * 辅助方法：解析罗马数字的转换过程（用于可视化）
     * 
     * @param s 罗马数字字符串
     */
    private void explainConversion(String s) {
        System.out.println("\n详细转换过程：");
        System.out.println("输入：" + s);
        System.out.println();
        
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            int current = getValue(currentChar);
            
            if (i < s.length() - 1) {
                char nextChar = s.charAt(i + 1);
                int next = getValue(nextChar);
                
                if (current < next) {
                    System.out.printf("  %c(%d) < %c(%d) → 减法形式，减去 %d → 总和 = %d\n", 
                                    currentChar, current, nextChar, next, current, result - current);
                    result -= current;
                } else {
                    System.out.printf("  %c(%d) ≥ %c(%d) → 正常相加，加上 %d → 总和 = %d\n", 
                                    currentChar, current, nextChar, next, current, result + current);
                    result += current;
                }
            } else {
                System.out.printf("  %c(%d) (最后) → 加上 %d → 总和 = %d\n", 
                                currentChar, current, current, result + current);
                result += current;
            }
        }
        
        System.out.println("\n最终结果：" + result);
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        RomanToInteger solution = new RomanToInteger();
        
        System.out.println("=== LeetCode 13: 罗马数字转整数 ===");
        System.out.println("基本规则：");
        System.out.println("  I=1, V=5, X=10, L=50, C=100, D=500, M=1000");
        System.out.println("  减法形式：小数字在大数字前面，表示减法");
        System.out.println("  IV=4, IX=9, XL=40, XC=90, CD=400, CM=900\n");
        
        // 测试用例1：简单情况
        String test1 = "III";
        int result1 = solution.romanToInt(test1);
        System.out.println("测试用例1:");
        System.out.println("输入: " + test1);
        System.out.println("输出: " + result1);
        System.out.println("预期: 3");
        System.out.println("通过: " + (result1 == 3));
        System.out.println();
        
        // 测试用例2：减法形式 - IV
        String test2 = "IV";
        int result2 = solution.romanToInt(test2);
        System.out.println("测试用例2:");
        System.out.println("输入: " + test2);
        System.out.println("输出: " + result2);
        System.out.println("预期: 4");
        System.out.println("通过: " + (result2 == 4));
        if (result2 == 4) {
            solution.explainConversion(test2);
        }
        System.out.println();
        
        // 测试用例3：减法形式 - IX
        String test3 = "IX";
        int result3 = solution.romanToInt(test3);
        System.out.println("测试用例3:");
        System.out.println("输入: " + test3);
        System.out.println("输出: " + result3);
        System.out.println("预期: 9");
        System.out.println("通过: " + (result3 == 9));
        System.out.println();
        
        // 测试用例4：组合情况
        String test4 = "LVIII";
        int result4 = solution.romanToInt(test4);
        System.out.println("测试用例4:");
        System.out.println("输入: " + test4);
        System.out.println("输出: " + result4);
        System.out.println("预期: 58 (50 + 5 + 3)");
        System.out.println("通过: " + (result4 == 58));
        System.out.println();
        
        // 测试用例5：复杂情况
        String test5 = "MCMXCIV";
        int result5 = solution.romanToInt(test5);
        System.out.println("测试用例5:");
        System.out.println("输入: " + test5);
        System.out.println("输出: " + result5);
        System.out.println("预期: 1994 (1000 + 900 + 90 + 4)");
        System.out.println("通过: " + (result5 == 1994));
        if (result5 == 1994) {
            solution.explainConversion(test5);
        }
        System.out.println();
        
        // 测试用例6：单个字符
        String test6 = "M";
        int result6 = solution.romanToInt(test6);
        System.out.println("测试用例6:");
        System.out.println("输入: " + test6);
        System.out.println("输出: " + result6);
        System.out.println("预期: 1000");
        System.out.println("通过: " + (result6 == 1000));
        System.out.println();
        
        // 测试用例7：所有减法形式
        String test7 = "CDXLIV";
        int result7 = solution.romanToInt(test7);
        System.out.println("测试用例7:");
        System.out.println("输入: " + test7);
        System.out.println("输出: " + result7);
        System.out.println("预期: 444 (400 + 40 + 4)");
        System.out.println("通过: " + (result7 == 444));
        System.out.println();
        
        // 测试用例8：连续相同字符
        String test8 = "MMMDCCCLXXXVIII";
        int result8 = solution.romanToInt(test8);
        System.out.println("测试用例8:");
        System.out.println("输入: " + test8);
        System.out.println("输出: " + result8);
        System.out.println("预期: 3888 (3000 + 800 + 80 + 8)");
        System.out.println("通过: " + (result8 == 3888));
        System.out.println();
        
        // 测试用例9：最大值
        String test9 = "MMMCMXCIX";
        int result9 = solution.romanToInt(test9);
        System.out.println("测试用例9:");
        System.out.println("输入: " + test9);
        System.out.println("输出: " + result9);
        System.out.println("预期: 3999 (3000 + 900 + 90 + 9)");
        System.out.println("通过: " + (result9 == 3999));
        System.out.println();
        
        // 测试用例10：混合情况
        String test10 = "DCXXI";
        int result10 = solution.romanToInt(test10);
        System.out.println("测试用例10:");
        System.out.println("输入: " + test10);
        System.out.println("输出: " + result10);
        System.out.println("预期: 621 (500 + 100 + 20 + 1)");
        System.out.println("通过: " + (result10 == 621));
        System.out.println();
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 比较相邻字符：当前 < 下一个 → 减法");
        System.out.println("2. 从左到右遍历，逐个累加或减去");
        System.out.println("3. 注意最后一个字符的边界处理");
        System.out.println("4. 时间复杂度：O(n)，空间复杂度：O(1)");
        System.out.println("5. 与第12题互为逆向操作");
    }
}

