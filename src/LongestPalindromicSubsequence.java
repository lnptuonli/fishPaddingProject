/**
 * LeetCode 516. 最长回文子序列 - Longest Palindromic Subsequence
 * 
 * 题目描述：
 * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
 * 
 * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
 * 
 * 示例 1：
 * 输入：s = "bbbab"
 * 输出：4
 * 解释：一个可能的最长回文子序列为 "bbbb" 。
 * 
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出：2
 * 解释：一个可能的最长回文子序列为 "bb" 。
 * 
 * 提示：
 * - 1 <= s.length <= 1000
 * - s 仅由小写英文字母组成
 * 
 * 核心理解：
 * 
 * 1. 区间DP的定义：
 *    - 状态定义在一个区间 [i, j] 上
 *    - 通过子区间的状态推导当前区间的状态
 *    - 典型特征：dp[i][j] 表示区间 [i, j] 的某个属性
 * 
 * 2. 状态定义：
 *    - dp[i][j]：字符串 s[i...j] 的最长回文子序列长度
 * 
 * 3. 状态转移方程：
 *    - 如果 s[i] == s[j]：
 *      dp[i][j] = dp[i+1][j-1] + 2
 *      含义：两端字符相同，可以同时加入回文序列
 *    
 *    - 如果 s[i] != s[j]：
 *      dp[i][j] = max(dp[i+1][j], dp[i][j-1])
 *      含义：两端字符不同，只能选择去掉左端或右端
 * 
 * 4. 初始条件：
 *    - dp[i][i] = 1（单个字符的回文长度为1）
 * 
 * 5. 遍历顺序（关键！）：
 *    - 因为 dp[i][j] 依赖 dp[i+1][j-1]、dp[i+1][j]、dp[i][j-1]
 *    - 需要保证计算 dp[i][j] 时，这三个状态已经计算过
 *    - 方法1：按区间长度从小到大遍历
 *    - 方法2：i 从大到小，j 从小到大
 * 
 * 可视化过程（s = "bbbab"）：
 * 
 * 构建DP表格：
 * 
 *       b   b   b   a   b
 *     +---+---+---+---+---+
 *   b | 1 | 2 | 3 | 3 | 4 |
 *     +---+---+---+---+---+
 *   b |   | 1 | 2 | 2 | 3 |
 *     +---+---+---+---+---+
 *   b |   |   | 1 | 1 | 3 |
 *     +---+---+---+---+---+
 *   a |   |   |   | 1 | 1 |
 *     +---+---+---+---+---+
 *   b |   |   |   |   | 1 |
 *     +---+---+---+---+---+
 * 
 * 计算过程：
 * - dp[0][0] = 1（"b"）
 * - dp[0][1] = 2（"bb"，s[0]==s[1]，dp[1][0]+2）
 * - dp[0][2] = 3（"bbb"，s[0]==s[2]，dp[1][1]+2）
 * - dp[0][3] = 3（"bbba"，s[0]!=s[3]，max(dp[1][3], dp[0][2])）
 * - dp[0][4] = 4（"bbbab"，s[0]==s[4]，dp[1][3]+2）
 * 
 * 最终答案：dp[0][4] = 4
 * 
 * 区间DP vs 序列DP：
 * 
 * | 特性 | 序列DP | 区间DP |
 * |------|--------|--------|
 * | 状态定义 | dp[i]：前i个/以i结尾 | dp[i][j]：区间[i,j] |
 * | 转移方向 | 从前往后 | 按区间长度递增 |
 * | 典型题目 | 爬楼梯、打家劫舍 | 回文子序列、戳气球 |
 * | 空间复杂度 | O(n) | O(n²) |
 * 
 * 解题思路：
 * 
 * 方法：区间DP ⭐⭐⭐⭐⭐
 * - 时间复杂度：O(n²)
 * - 空间复杂度：O(n²)
 * 
 * 易错点：
 * 
 * 1. 遍历顺序错误：
 *    - 必须保证 dp[i+1][j-1]、dp[i+1][j]、dp[i][j-1] 先计算
 *    - i 从大到小，j 从小到大
 *    - 或者按区间长度从小到大
 * 
 * 2. 初始化错误：
 *    - dp[i][i] = 1，不要忘记
 *    - 其他位置初始化为0即可
 * 
 * 3. 边界条件：
 *    - 当 i+1 > j-1 时（即区间长度为2），dp[i+1][j-1] = 0
 *    - 这种情况下，如果 s[i] == s[j]，dp[i][j] = 2
 * 
 * 4. 与最长回文子串的区别：
 *    - 子串：必须连续
 *    - 子序列：可以不连续
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 5. 最长回文子串（中心扩展、动态规划）
 * - LeetCode 1143. 最长公共子序列（双序列DP）
 * - LeetCode 647. 回文子串（区间DP，计数）
 * - LeetCode 312. 戳气球（区间DP，困难）
 */
public class LongestPalindromicSubsequence {
    
    /**
     * 主方法：计算最长回文子序列的长度
     * 
     * @param s 字符串
     * @return 最长回文子序列长度
     */
    public int longestPalindromeSubseq(String s) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：区间DP
        // 
        // 第1步：定义状态
        // dp[i][j] 表示 s[i...j] 的最长回文子序列长度
        // 
        // 第2步：状态转移方程
        // if (s[i] == s[j]):
        //     dp[i][j] = dp[i+1][j-1] + 2
        // else:
        //     dp[i][j] = max(dp[i+1][j], dp[i][j-1])
        // 
        // 第3步：初始条件
        // dp[i][i] = 1（单个字符）
        // 
        // 第4步：遍历顺序
        // i 从 n-1 到 0（从大到小）
        // j 从 i+1 到 n-1（从小到大）
        // 
        // 第5步：最终答案
        // dp[0][n-1]
        
        // 参考实现：
        // int n = s.length();
        // int[][] dp = new int[n][n];
        // 
        // // 初始化：单个字符的回文长度为1
        // for (int i = 0; i < n; i++) {
        //     dp[i][i] = 1;
        // }
        // 
        // // 区间DP：i 从大到小，j 从小到大
        // for (int i = n - 1; i >= 0; i--) {
        //     for (int j = i + 1; j < n; j++) {
        //         if (s.charAt(i) == s.charAt(j)) {
        //             dp[i][j] = dp[i + 1][j - 1] + 2;
        //         } else {
        //             dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
        //         }
        //     }
        // }
        // 
        // return dp[0][n - 1];
        
        // 关键理解：
        // 1. 为什么 s[i] == s[j] 时，dp[i][j] = dp[i+1][j-1] + 2？
        //    - 两端字符相同，可以同时加入回文序列
        //    - 中间部分的最长回文长度是 dp[i+1][j-1]
        //    - 加上两端的两个字符，就是 +2
        // 
        // 2. 为什么 s[i] != s[j] 时，dp[i][j] = max(dp[i+1][j], dp[i][j-1])？
        //    - 两端字符不同，不能同时加入回文序列
        //    - 只能选择去掉左端（dp[i+1][j]）或去掉右端（dp[i][j-1]）
        //    - 取两者中较大的
        // 
        // 3. 为什么 i 从大到小，j 从小到大？
        //    - 因为 dp[i][j] 依赖 dp[i+1][j-1]、dp[i+1][j]、dp[i][j-1]
        //    - i+1 > i，所以 i 要从大到小
        //    - j-1 < j，所以 j 要从小到大
        //    - 这样才能保证依赖的状态已经计算过
        // 
        // 4. 这题与最长公共子序列的关系？
        //    - 最长回文子序列 = LCS(s, reverse(s))
        //    - 但区间DP的思路更直观
        
        // 在这里编写你的实现代码
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // ============================================
        // TODO: 实现结束
        // ============================================
        
        return 0;  // 记得返回结果
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        LongestPalindromicSubsequence solution = new LongestPalindromicSubsequence();
        
        System.out.println("=== LeetCode 516: 最长回文子序列 ===");
        System.out.println("中等题，区间DP入门\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        String s1 = "bbbab";
        int result1 = solution.longestPalindromeSubseq(s1);
        System.out.println("输入: s = \"" + s1 + "\"");
        System.out.println("输出: " + result1);
        System.out.println("预期: 4");
        System.out.println("解释: 一个可能的最长回文子序列为 \"bbbb\"");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        String s2 = "cbbd";
        int result2 = solution.longestPalindromeSubseq(s2);
        System.out.println("输入: s = \"" + s2 + "\"");
        System.out.println("输出: " + result2);
        System.out.println("预期: 2");
        System.out.println("解释: 一个可能的最长回文子序列为 \"bb\"");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3：单个字符
        System.out.println("测试用例3: 单个字符");
        String s3 = "a";
        int result3 = solution.longestPalindromeSubseq(s3);
        System.out.println("输入: s = \"" + s3 + "\"");
        System.out.println("输出: " + result3);
        System.out.println("预期: 1");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：完全回文
        System.out.println("测试用例4: 完全回文");
        String s4 = "abcba";
        int result4 = solution.longestPalindromeSubseq(s4);
        System.out.println("输入: s = \"" + s4 + "\"");
        System.out.println("输出: " + result4);
        System.out.println("预期: 5");
        System.out.println("解释: 整个字符串就是回文");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：无重复字符
        System.out.println("测试用例5: 无重复字符");
        String s5 = "abcde";
        int result5 = solution.longestPalindromeSubseq(s5);
        System.out.println("输入: s = \"" + s5 + "\"");
        System.out.println("输出: " + result5);
        System.out.println("预期: 1");
        System.out.println("解释: 任何单个字符都是长度为1的回文");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i][j] = s[i...j] 的最长回文子序列长度");
        System.out.println("2. 状态转移：s[i]==s[j] → dp[i+1][j-1]+2, 否则 → max(dp[i+1][j], dp[i][j-1])");
        System.out.println("3. 初始条件：dp[i][i] = 1");
        System.out.println("4. 遍历顺序：i 从大到小，j 从小到大（关键！）");
        System.out.println("5. 区间DP的核心：通过子区间推导当前区间");
        System.out.println("\n这是区间DP的入门题，务必掌握遍历顺序！");
    }
}



