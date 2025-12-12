/**
 * LeetCode 1143. 最长公共子序列 - Longest Common Subsequence (LCS)
 * 
 * 题目描述：
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 **公共子序列** 的长度。如果不存在 **公共子序列** ，返回 0 。
 * 
 * 一个字符串的 **子序列** 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 
 * 两个字符串的 **公共子序列** 是这两个字符串所共同拥有的子序列。
 * 
 * 示例 1：
 * 输入：text1 = "abcde", text2 = "ace" 
 * 输出：3  
 * 解释：最长公共子序列是 "ace" ，它的长度为 3 。
 * 
 * 示例 2：
 * 输入：text1 = "abc", text2 = "abc"
 * 输出：3
 * 解释：最长公共子序列是 "abc" ，它的长度为 3 。
 * 
 * 示例 3：
 * 输入：text1 = "abc", text2 = "def"
 * 输出：0
 * 解释：两个字符串没有公共子序列，返回 0 。
 * 
 * 提示：
 * - 1 <= text1.length, text2.length <= 1000
 * - text1 和 text2 仅由小写英文字符组成。
 * 
 * 核心理解：
 * 
 * 1. 双序列DP的定义：
 *    - 状态定义在两个序列上
 *    - dp[i][j] 通常表示：第一个序列的前 i 个元素和第二个序列的前 j 个元素的某个属性
 *    - 典型特征：二维DP，两个维度分别对应两个序列
 * 
 * 2. 状态定义：
 *    - dp[i][j]：text1 的前 i 个字符和 text2 的前 j 个字符的最长公共子序列长度
 * 
 * 3. 状态转移方程：
 *    - 如果 text1[i-1] == text2[j-1]：
 *      dp[i][j] = dp[i-1][j-1] + 1
 *      含义：当前字符相同，LCS长度 = 之前的LCS长度 + 1
 *    
 *    - 如果 text1[i-1] != text2[j-1]：
 *      dp[i][j] = max(dp[i-1][j], dp[i][j-1])
 *      含义：当前字符不同，LCS长度 = 去掉 text1 当前字符 或 去掉 text2 当前字符，取较大值
 * 
 * 4. 初始条件：
 *    - dp[0][j] = 0（text1 为空，LCS长度为0）
 *    - dp[i][0] = 0（text2 为空，LCS长度为0）
 * 
 * 5. 最终答案：
 *    - dp[m][n]，m 和 n 分别为 text1 和 text2 的长度
 * 
 * 可视化过程（text1 = "abcde", text2 = "ace"）：
 * 
 * 构建DP表格：
 * 
 *       ""  a   c   e
 *     +---+---+---+---+
 *  "" | 0 | 0 | 0 | 0 |
 *     +---+---+---+---+
 *  a  | 0 | 1 | 1 | 1 |
 *     +---+---+---+---+
 *  b  | 0 | 1 | 1 | 1 |
 *     +---+---+---+---+
 *  c  | 0 | 1 | 2 | 2 |
 *     +---+---+---+---+
 *  d  | 0 | 1 | 2 | 2 |
 *     +---+---+---+---+
 *  e  | 0 | 1 | 2 | 3 |
 *     +---+---+---+---+
 * 
 * 计算过程：
 * - dp[1][1] = 1（'a' == 'a'，dp[0][0] + 1）
 * - dp[1][2] = 1（'a' != 'c'，max(dp[0][2], dp[1][1])）
 * - dp[3][2] = 2（'c' == 'c'，dp[2][1] + 1）
 * - dp[5][3] = 3（'e' == 'e'，dp[4][2] + 1）
 * 
 * 最终答案：dp[5][3] = 3
 * 
 * 双序列DP vs 单序列DP：
 * 
 * | 特性 | 单序列DP | 双序列DP |
 * |------|---------|---------|
 * | 状态定义 | dp[i]：前i个/以i结尾 | dp[i][j]：两个序列的前i和前j |
 * | 空间复杂度 | O(n) | O(m*n) |
 * | 典型题目 | 爬楼梯、LIS | LCS、编辑距离 |
 * | 转移方向 | 一维 | 二维 |
 * 
 * 解题思路：
 * 
 * 方法：双序列DP ⭐⭐⭐⭐⭐
 * - 时间复杂度：O(m * n)
 * - 空间复杂度：O(m * n)，可优化到 O(min(m, n))
 * 
 * 易错点：
 * 
 * 1. 索引问题：
 *    - dp[i][j] 表示前 i 个和前 j 个
 *    - 对应的字符是 text1[i-1] 和 text2[j-1]
 *    - 不要搞混！
 * 
 * 2. 初始化：
 *    - dp[0][j] = 0，dp[i][0] = 0
 *    - 表示空字符串的情况
 * 
 * 3. 状态转移：
 *    - 字符相同：dp[i-1][j-1] + 1
 *    - 字符不同：max(dp[i-1][j], dp[i][j-1])
 * 
 * 4. 空间优化：
 *    - 可以用滚动数组优化到 O(min(m, n))
 *    - 但需要额外变量保存 dp[i-1][j-1]
 * 
 * 如何打印出具体的LCS？
 * 
 * - 从 dp[m][n] 开始回溯
 * - 如果 text1[i-1] == text2[j-1]，加入结果，i--, j--
 * - 否则，向 dp 值较大的方向回溯
 * 
 * 与相关题目的联系：
 * 
 * - LeetCode 72. 编辑距离（双序列DP，求最少操作数）
 * - LeetCode 516. 最长回文子序列（LCS的变形：LCS(s, reverse(s))）
 * - LeetCode 583. 两个字符串的删除操作（双序列DP）
 * - LeetCode 712. 两个字符串的最小ASCII删除和（双序列DP）
 */
public class LongestCommonSubsequence {
    
    /**
     * 主方法：计算最长公共子序列的长度
     * 
     * @param text1 字符串1
     * @param text2 字符串2
     * @return 最长公共子序列长度
     */
    public int longestCommonSubsequence(String text1, String text2) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：双序列DP
        // 
        // 第1步：定义状态
        // dp[i][j] 表示 text1 的前 i 个字符和 text2 的前 j 个字符的 LCS 长度
        // 
        // 第2步：状态转移方程
        // if (text1[i-1] == text2[j-1]):
        //     dp[i][j] = dp[i-1][j-1] + 1
        // else:
        //     dp[i][j] = max(dp[i-1][j], dp[i][j-1])
        // 
        // 第3步：初始条件
        // dp[0][j] = 0（text1 为空）
        // dp[i][0] = 0（text2 为空）
        // 
        // 第4步：遍历顺序
        // i 从 1 到 m
        // j 从 1 到 n
        // 
        // 第5步：最终答案
        // dp[m][n]
        
        // 参考实现：
        // int m = text1.length();
        // int n = text2.length();
        // int[][] dp = new int[m + 1][n + 1];
        // 
        // // 初始化：dp[0][j] = 0, dp[i][0] = 0（默认就是0）
        // 
        // // 双序列DP
        // for (int i = 1; i <= m; i++) {
        //     for (int j = 1; j <= n; j++) {
        //         if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
        //             dp[i][j] = dp[i - 1][j - 1] + 1;
        //         } else {
        //             dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        //         }
        //     }
        // }
        // 
        // return dp[m][n];
        
        // 关键理解：
        // 1. 为什么 text1[i-1] == text2[j-1] 时，dp[i][j] = dp[i-1][j-1] + 1？
        //    - 当前字符相同，可以加入LCS
        //    - 之前的LCS长度是 dp[i-1][j-1]
        //    - 加上当前这个字符，就是 +1
        // 
        // 2. 为什么 text1[i-1] != text2[j-1] 时，dp[i][j] = max(dp[i-1][j], dp[i][j-1])？
        //    - 当前字符不同，不能同时加入LCS
        //    - 只能选择去掉 text1 的当前字符（dp[i-1][j]）
        //    - 或者去掉 text2 的当前字符（dp[i][j-1]）
        //    - 取两者中较大的
        // 
        // 3. 为什么 dp[i][j] 表示"前 i 个"而不是"以 i 结尾"？
        //    - 因为子序列可以不连续
        //    - "前 i 个"更符合LCS的定义
        //    - 如果是"以 i 结尾"，状态转移会很复杂
        // 
        // 4. 这题与最长递增子序列的区别？
        //    - LIS：单序列DP，dp[i] 表示以 i 结尾
        //    - LCS：双序列DP，dp[i][j] 表示前 i 和前 j
        
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
        LongestCommonSubsequence solution = new LongestCommonSubsequence();
        
        System.out.println("=== LeetCode 1143: 最长公共子序列 ===");
        System.out.println("中等题，双序列DP经典\n");
        
        // 测试用例1
        System.out.println("测试用例1:");
        String text1_1 = "abcde";
        String text2_1 = "ace";
        int result1 = solution.longestCommonSubsequence(text1_1, text2_1);
        System.out.println("输入: text1 = \"" + text1_1 + "\", text2 = \"" + text2_1 + "\"");
        System.out.println("输出: " + result1);
        System.out.println("预期: 3");
        System.out.println("解释: 最长公共子序列是 \"ace\"");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例2
        System.out.println("测试用例2:");
        String text1_2 = "abc";
        String text2_2 = "abc";
        int result2 = solution.longestCommonSubsequence(text1_2, text2_2);
        System.out.println("输入: text1 = \"" + text1_2 + "\", text2 = \"" + text2_2 + "\"");
        System.out.println("输出: " + result2);
        System.out.println("预期: 3");
        System.out.println("解释: 最长公共子序列是 \"abc\"");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例3
        System.out.println("测试用例3:");
        String text1_3 = "abc";
        String text2_3 = "def";
        int result3 = solution.longestCommonSubsequence(text1_3, text2_3);
        System.out.println("输入: text1 = \"" + text1_3 + "\", text2 = \"" + text2_3 + "\"");
        System.out.println("输出: " + result3);
        System.out.println("预期: 0");
        System.out.println("解释: 两个字符串没有公共子序列");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例4：部分重叠
        System.out.println("测试用例4: 部分重叠");
        String text1_4 = "abcdefg";
        String text2_4 = "xyzabc";
        int result4 = solution.longestCommonSubsequence(text1_4, text2_4);
        System.out.println("输入: text1 = \"" + text1_4 + "\", text2 = \"" + text2_4 + "\"");
        System.out.println("输出: " + result4);
        System.out.println("预期: 3");
        System.out.println("解释: 最长公共子序列是 \"abc\"");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 测试用例5：交错
        System.out.println("测试用例5: 交错");
        String text1_5 = "oxcpqrsvwf";
        String text2_5 = "shmtulqrypy";
        int result5 = solution.longestCommonSubsequence(text1_5, text2_5);
        System.out.println("输入: text1 = \"" + text1_5 + "\", text2 = \"" + text2_5 + "\"");
        System.out.println("输出: " + result5);
        System.out.println("预期: 2");
        System.out.println("解释: 最长公共子序列是 \"qr\" 或其他长度为2的子序列");
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 状态定义：dp[i][j] = text1前i个和text2前j个的LCS长度");
        System.out.println("2. 状态转移：相同→dp[i-1][j-1]+1, 不同→max(dp[i-1][j], dp[i][j-1])");
        System.out.println("3. 初始条件：dp[0][j] = 0, dp[i][0] = 0");
        System.out.println("4. 索引对应：dp[i][j] 对应 text1[i-1] 和 text2[j-1]");
        System.out.println("5. 双序列DP的核心：在两个序列上同时进行状态转移");
        System.out.println("\n这是双序列DP的经典题，务必掌握！");
    }
}



