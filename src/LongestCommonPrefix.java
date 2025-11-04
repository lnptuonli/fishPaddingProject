/**
 * LeetCode 14. 最长公共前缀 - Longest Common Prefix
 * <p>
 * 题目描述：
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 示例 1：
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * <p>
 * 示例 2：
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 * <p>
 * 示例 3：
 * 输入：strs = [""]
 * 输出：""
 * <p>
 * 示例 4：
 * 输入：strs = ["a"]
 * 输出："a"
 * <p>
 * 示例 5：
 * 输入：strs = ["ab", "a"]
 * 输出："a"
 * <p>
 * 提示：
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 * <p>
 * 核心理解：
 * <p>
 * 1. 公共前缀的特点：
 * - 所有字符串的前n个字符都相同
 * - 前缀长度不会超过最短字符串的长度
 * <p>
 * 2. 关键洞察：
 * - 可以以第一个字符串为基准，逐个字符比较
 * - 也可以先找最短字符串，以它为基准
 * - 遇到不匹配就立即返回
 * <p>
 * 3. 边界情况：
 * - 空数组：不会出现（题目保证 length >= 1）
 * - 空字符串：返回 ""
 * - 单个字符串：返回它本身
 * - 完全不匹配：返回 ""
 * <p>
 * 解题思路：
 * <p>
 * 思路1：横向扫描（推荐⭐⭐⭐）
 * - 以第一个字符串为基准
 * - 逐个字符与其他字符串比较
 * - 遇到不匹配或到达某个字符串末尾，返回当前前缀
 * - 时间复杂度：O(S)，S是所有字符串的字符总数
 * - 空间复杂度：O(1)
 * <p>
 * 思路2：纵向扫描
 * - 按列比较，第一列、第二列...
 * - 每一列所有字符串的字符都相同才继续
 * - 时间复杂度：O(S)
 * - 空间复杂度：O(1)
 * <p>
 * 思路3：分治法
 * - 将数组分成两半，分别求公共前缀
 * - 再求两个前缀的公共前缀
 * - 时间复杂度：O(S)
 * - 空间复杂度：O(m*logn)，递归栈空间
 * <p>
 * 思路4：二分查找
 * - 在 [0, minLen] 范围内二分查找前缀长度
 * - 时间复杂度：O(S*logm)，m是最短字符串长度
 * - 空间复杂度：O(1)
 * <p>
 * 易错点：
 * 1. 空字符串处理：数组中有空字符串，直接返回 ""
 * 2. 数组长度为1：直接返回唯一的字符串
 * 3. 索引越界：比较时要检查字符串长度
 * 4. 第一个字符就不匹配：返回 ""
 */
public class LongestCommonPrefix {

    /**
     * 主方法：查找最长公共前缀
     *41用罗马数字怎么表示
     * @param strs 字符串数组
     * @return 最长公共前缀
     */
    public String longestCommonPrefix(String[] strs) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================

        // 提示：横向扫描法
        // 
        // 步骤1：边界处理
        // if (strs == null || strs.length == 0) {
        //     return "";
        // }
        // if (strs.length == 1) {
        //     return strs[0];
        // }
        // 
        // 步骤2：以第一个字符串为基准
        // String prefix = strs[0];
        // 
        // 步骤3：遍历每个字符位置
        // for (int i = 0; i < prefix.length(); i++) {
        //     char c = prefix.charAt(i);
        //     
        //     // 检查其他所有字符串的第i个字符
        //     for (int j = 1; j < strs.length; j++) {
        //         // 如果某个字符串太短，或字符不匹配
        //         if (i >= strs[j].length() || strs[j].charAt(i) != c) {
        //             return prefix.substring(0, i);
        //         }
        //     }
        // }
        // 
        // 步骤4：如果完全匹配，返回第一个字符串
        // return prefix;

        // 关键理解：
        // 为什么以第一个字符串为基准？
        // - 公共前缀不会比任何一个字符串长
        // - 以第一个为基准最简单直接
        // 
        // 什么时候停止？
        // - 遇到不匹配的字符
        // - 某个字符串已经到末尾
        // - 遍历完第一个字符串

        // 在这里编写你的实现代码
        // 简单题也得一步一步来，我先找最短的子串
        int shortest = strs[0].length();
        int flag = 0;//标志找到最长子串
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() < shortest) {
                shortest = strs[i].length();
            }
        }
        StringBuilder pubsb = new StringBuilder();
        for (int j = 0; j < shortest; j++) {
            pubsb.append(strs[0].charAt(j));
            for (int k = 0; k < strs.length; k++) {
                if (strs[k].substring(0, pubsb.length()).equals(pubsb.toString())) {
                    //如果传入的子串前面几位符合pubstring，继续校验
                    continue;
                } else {
                    //否则新加的这个字符不符合，删掉，最长子串找到了
                    pubsb.deleteCharAt(j);
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                return pubsb.toString();
            }
        }
        return pubsb.toString();  // 记得返回结果

        // ============================================
        // TODO: 实现结束
        // ============================================


    }

    /**
     * 辅助方法：求两个字符串的公共前缀
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 公共前缀
     */
    private String commonPrefix(String str1, String str2) {
        int minLen = Math.min(str1.length(), str2.length());
        for (int i = 0; i < minLen; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return str1.substring(0, i);
            }
        }
        return str1.substring(0, minLen);
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        LongestCommonPrefix solution = new LongestCommonPrefix();

        System.out.println("=== LeetCode 14: 最长公共前缀 ===");
        System.out.println("找出字符串数组中所有字符串的最长公共前缀\n");

        // 测试用例1：官方示例1
        String[] test1 = {"flower", "flow", "flight"};
        String result1 = solution.longestCommonPrefix(test1);
        System.out.println("测试用例1:");
        System.out.println("输入: [\"flower\",\"flow\",\"flight\"]");
        System.out.println("输出: \"" + result1 + "\"");
        System.out.println("预期: \"fl\"");
        System.out.println("通过: " + result1.equals("fl"));
        System.out.println();

        // 测试用例2：官方示例2 - 无公共前缀
        String[] test2 = {"dog", "racecar", "car"};
        String result2 = solution.longestCommonPrefix(test2);
        System.out.println("测试用例2:");
        System.out.println("输入: [\"dog\",\"racecar\",\"car\"]");
        System.out.println("输出: \"" + result2 + "\"");
        System.out.println("预期: \"\"");
        System.out.println("通过: " + result2.equals(""));
        System.out.println();

        // 测试用例3：单个字符串
        String[] test3 = {"alone"};
        String result3 = solution.longestCommonPrefix(test3);
        System.out.println("测试用例3:");
        System.out.println("输入: [\"alone\"]");
        System.out.println("输出: \"" + result3 + "\"");
        System.out.println("预期: \"alone\"");
        System.out.println("通过: " + result3.equals("alone"));
        System.out.println();

        // 测试用例4：包含空字符串
        String[] test4 = {"", "b"};
        String result4 = solution.longestCommonPrefix(test4);
        System.out.println("测试用例4:");
        System.out.println("输入: [\"\",\"b\"]");
        System.out.println("输出: \"" + result4 + "\"");
        System.out.println("预期: \"\"");
        System.out.println("通过: " + result4.equals(""));
        System.out.println();

        // 测试用例5：完全相同
        String[] test5 = {"test", "test", "test"};
        String result5 = solution.longestCommonPrefix(test5);
        System.out.println("测试用例5:");
        System.out.println("输入: [\"test\",\"test\",\"test\"]");
        System.out.println("输出: \"" + result5 + "\"");
        System.out.println("预期: \"test\"");
        System.out.println("通过: " + result5.equals("test"));
        System.out.println();

        // 测试用例6：长度不同
        String[] test6 = {"ab", "a"};
        String result6 = solution.longestCommonPrefix(test6);
        System.out.println("测试用例6:");
        System.out.println("输入: [\"ab\",\"a\"]");
        System.out.println("输出: \"" + result6 + "\"");
        System.out.println("预期: \"a\"");
        System.out.println("通过: " + result6.equals("a"));
        System.out.println();

        // 测试用例7：第一个字符就不同
        String[] test7 = {"abc", "bcd", "cde"};
        String result7 = solution.longestCommonPrefix(test7);
        System.out.println("测试用例7:");
        System.out.println("输入: [\"abc\",\"bcd\",\"cde\"]");
        System.out.println("输出: \"" + result7 + "\"");
        System.out.println("预期: \"\"");
        System.out.println("通过: " + result7.equals(""));
        System.out.println();

        // 测试用例8：两个字符串
        String[] test8 = {"flower", "flow"};
        String result8 = solution.longestCommonPrefix(test8);
        System.out.println("测试用例8:");
        System.out.println("输入: [\"flower\",\"flow\"]");
        System.out.println("输出: \"" + result8 + "\"");
        System.out.println("预期: \"flow\"");
        System.out.println("通过: " + result8.equals("flow"));
        System.out.println();

        // 测试用例9：单字符
        String[] test9 = {"a", "a", "a"};
        String result9 = solution.longestCommonPrefix(test9);
        System.out.println("测试用例9:");
        System.out.println("输入: [\"a\",\"a\",\"a\"]");
        System.out.println("输出: \"" + result9 + "\"");
        System.out.println("预期: \"a\"");
        System.out.println("通过: " + result9.equals("a"));
        System.out.println();

        // 测试用例10：长字符串
        String[] test10 = {"interspecies", "interstellar", "interstate"};
        String result10 = solution.longestCommonPrefix(test10);
        System.out.println("测试用例10:");
        System.out.println("输入: [\"interspecies\",\"interstellar\",\"interstate\"]");
        System.out.println("输出: \"" + result10 + "\"");
        System.out.println("预期: \"inters\"");
        System.out.println("通过: " + result10.equals("inters"));
        System.out.println();

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 以第一个字符串为基准，逐字符比较");
        System.out.println("2. 遇到不匹配或字符串结束，立即返回");
        System.out.println("3. 注意边界：空字符串、单个字符串");
        System.out.println("4. 时间复杂度：O(S)，S是所有字符的总数");
        System.out.println("5. 空间复杂度：O(1)");
    }
}

