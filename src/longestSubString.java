import java.util.*;

/**
 * 无重复字符的最长子串
 *
 * 题目描述：
 * 给定一个字符串 s，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *
 * 注意：答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
public class longestSubString {

    /**
     * 方法一：自己的方法
     * 时间复杂度：O(n)
     * 空间复杂度：O(min(m,n))，其中m是字符集的大小
     *
     * @param s 输入字符串
     * @return 最长无重复字符子串的长度
     */
    public int lengthOfLongestSubstring(String s) {
        //自己的方法
        int left=0;
        int right=0;
        if (s.length()==1){
            return 1;
        }
        //从长度1开始，遍历所有长度
        while(right<s.length()){
            if(ifDuplicated(left,right,s)){
                left++;
                right++;
            }
            else{
                right++;
            }
        }
        return right-left; // 请替换为您的实现

    }

    /**
     * 方法二：优化后的规范方法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param s 输入字符串
     * @return 最长无重复字符子串的长度
     */
    public int lengthOfLongestSubstringArray(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0, right = 0, maxLen = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            if (!set.contains(c)) {
                set.add(c);
                right++;
                maxLen = Math.max(maxLen, right - left);
            } else {
                set.remove(s.charAt(left));
                left++;
            }
        }
        return maxLen;
    }

    /**
     * 方法三：滑动窗口 + HashSet
     * 时间复杂度：O(n)
     * 空间复杂度：O(min(m,n))
     *
     * @param s 输入字符串
     * @return 最长无重复字符子串的长度
     */
    public int lengthOfLongestSubstringSet(String s) {
        // TODO: 在这里实现您的算法
        // 提示：
        // 1. 使用HashSet记录当前窗口中的字符
        // 2. 使用双指针维护滑动窗口
        // 3. 当遇到重复字符时，移动左指针直到移除重复字符

        return 0; // 请替换为您的实现
    }

    public boolean ifDuplicated(int left, int right, String s){
        if (left==right){
            return false;
        }
        HashMap map=new HashMap<Character, Integer>();
        for (int i=left;i<=right;i++){
            if(map.containsKey(s.charAt(i))){
                map.clear();
                return true;
            }
            else{
                map.put(s.charAt(i),i);
            }
        }
        map.clear();
        return false;
    }
    /**
     * 辅助方法：打印调试信息
     *
     * @param s 输入字符串
     * @param method 方法名
     * @param result 结果
     */
    private void printDebug(String s, String method, int result) {
        System.out.println("方法: " + method);
        System.out.println("输入: \"" + s + "\"");
        System.out.println("输出: " + result);
        System.out.println("---");
    }

    /**
     * 测试用例
     */
    public void runTests() {
        System.out.println("=== 无重复字符的最长子串测试 ===");

        // 测试用例1：基本示例
        String test1 = "abcabcbb";
        int result1 = lengthOfLongestSubstring(test1);
        printDebug(test1, "HashMap方法", result1);

        // 测试用例2：重复字符
        String test2 = "bbbbb";
        int result2 = lengthOfLongestSubstring(test2);
        printDebug(test2, "HashMap方法", result2);

        // 测试用例3：复杂情况
        String test3 = "pwwkew";
        int result3 = lengthOfLongestSubstring(test3);
        printDebug(test3, "HashMap方法", result3);

        // 测试用例4：空字符串
        String test4 = "";
        int result4 = lengthOfLongestSubstring(test4);
        printDebug(test4, "HashMap方法", result4);

        // 测试用例5：单个字符
        String test5 = "a";
        int result5 = lengthOfLongestSubstring(test5);
        printDebug(test5, "HashMap方法", result5);

        // 测试用例6：无重复字符
        String test6 = "abcdef";
        int result6 = lengthOfLongestSubstring(test6);
        printDebug(test6, "HashMap方法", result6);

        // 测试用例7：特殊字符
        String test7 = "dvdf";
        int result7 = lengthOfLongestSubstring(test7);
        printDebug(test7, "HashMap方法", result7);

        // 测试用例8：中文和英文混合
        String test8 = "你好世界hello";
        int result8 = lengthOfLongestSubstring(test8);
        printDebug(test8, "HashMap方法", result8);
    }

    /**
     * 性能测试
     */
    public void performanceTest() {
        System.out.println("=== 性能测试 ===");

        // 生成测试数据
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        String longString = sb.toString();

        // 测试HashMap方法
        long startTime = System.nanoTime();
        int result1 = lengthOfLongestSubstring(longString);
        long endTime = System.nanoTime();
        System.out.println("HashMap方法 - 结果: " + result1 + ", 耗时: " + (endTime - startTime) / 1000000 + "ms");

        // 测试数组方法
        startTime = System.nanoTime();
        int result2 = lengthOfLongestSubstringArray(longString);
        endTime = System.nanoTime();
        System.out.println("数组方法 - 结果: " + result2 + ", 耗时: " + (endTime - startTime) / 1000000 + "ms");

        // 测试HashSet方法
        startTime = System.nanoTime();
        int result3 = lengthOfLongestSubstringSet(longString);
        endTime = System.nanoTime();
        System.out.println("HashSet方法 - 结果: " + result3 + ", 耗时: " + (endTime - startTime) / 1000000 + "ms");
    }

    /**
     * 主方法 - 程序入口
     */
    public static void main(String[] args) {
        longestSubString solution = new longestSubString();

        // 运行基本测试
        solution.runTests();

        // 运行性能测试
        solution.performanceTest();

        // 交互式测试
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== 交互式测试 ===");
        System.out.println("请输入要测试的字符串（输入'quit'退出）:");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if ("quit".equals(input)) {
                break;
            }

            if (input.isEmpty()) {
                System.out.println("输入不能为空，请重新输入");
                continue;
            }

            int result = solution.lengthOfLongestSubstring(input);
            System.out.println("输入: \"" + input + "\"");
            System.out.println("最长无重复字符子串长度: " + result);
            System.out.println();
        }

        scanner.close();
        System.out.println("程序结束");
    }
}