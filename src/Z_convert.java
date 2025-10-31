/**
 * LeetCode 6. Z 字形变换 (Zigzag Conversion)
 * 
 * 题目描述：
 * 将一个给定字符串 s 根据给定的行数 numRows，以从上往下、从左到右进行Z字形排列。
 * 
 * 示例 1：
 * 输入：s = "PAYPALISHIRING", numRows = 3
 * 输出："PAHNAPLSIIGYIR"
 * 解释：
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 
 * 示例 2：
 * 输入：s = "PAYPALISHIRING", numRows = 4
 * 输出："PINALSIGYAHRPI"
 * 解释：
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 * 
 * 示例 3：
 * 输入：s = "A", numRows = 1
 * 输出："A"
 * 
 * 提示：
 * 1 <= s.length <= 1000
 * s 由英文字母（小写和大写）、',' 和 '.' 组成
 * 1 <= numRows <= 1000
 * 
 * 解题思路：
 * 1. 按行排序法：创建numRows个StringBuilder，模拟Z字形填充过程
 * 2. 规律法：找出每行字符在原字符串中的索引规律
 * 3. 数学公式法：计算每行元素的位置
 * 
 * 这道题确实有一定难度，需要：
 * - 理解Z字形的排列规律
 * - 处理好方向转换的逻辑
 * - 考虑边界条件（numRows=1, 字符串长度小于numRows等）
 */
public class Z_convert {
    
    /**
     * 主方法：Z字形变换
     * 
     * @param s 输入字符串
     * @param numRows 行数
     * @return Z字形排列后按行读取的字符串
     */
    public String convert(String s, int numRows) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 边界条件处理，即处理不满一列的
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        
        // 提示：可以考虑以下几种思路
        // 思路1：创建numRows个StringBuilder，按Z字形顺序填充
        // 思路2：找出每行字符在原字符串中的索引规律
        // 思路3：使用方向标记，模拟上下移动的过程
        
        // 在这里编写你的实现代码

        //我认为所有Z都可分割成V字形，计算V字的长度：
        int v_length=numRows+(numRows-2);
        //计算拼写V字的数量：
        int v_num=s.length()/v_length+1;
        //计算创建矩阵的列数,每个V字的宽度应该刚好等于行数-1
        int numVol=v_num*(numRows-1);

        //初始化矩阵，既然由英文字母（小写和大写）、',' 和 '.' 组成，那么用-填充
        char[][] matrix= new char[numRows][numVol];
        for (int i=0; i<numRows;i++){
            for (int j=0; j<numVol;j++){
                matrix[i][j] = '-';
            }
        }
        //初始化字符串游标
        int pin=0;
        //初始化数组游标
        int mat_x=0;
        int mat_y=0;

        //循环填充矩阵，次数为v字的数量
        for (int z=0; z<v_num;z++){
            for (int x=0; x<numRows;x++){
                if(pin<s.length()){
                    matrix[mat_x][mat_y]=s.charAt(pin);
                    //数组游标向下，string游标向前
                    mat_x++;
                    pin++;
                }
                else
                    break;
            }
            //更新游标位置，向右上方移动一次
            mat_x=mat_x-2;
            mat_y++;
            for (int y=0; y<(numRows-1)-1;y++){
                if(pin<s.length()){
                    matrix[mat_x][mat_y]=s.charAt(pin);
                    //数组游标向右上方移动
                    mat_x--;
                    mat_y++;
                    pin++;
                }
                else
                    break;
            }
        }
        //新建字符数组，方便转成string
        char[] matrixtoArray=new char[s.length()];
        int v=0;
        for (int i=0; i<numRows;i++){
            for (int j=0; j<numVol;j++){
                if(matrix[i][j] == '-'){
                    continue;
                }
                else{
                    matrixtoArray[v]=matrix[i][j];
                    v++;
                }
            }
        }
        String str = new String(matrixtoArray);
        // ============================================
        // TODO: 实现结束
        // ============================================
        
        return str;  // 记得返回结果
    }
    
    /**
     * 辅助方法：可视化Z字形排列（用于调试）
     * 
     * @param s 输入字符串
     * @param numRows 行数
     */
    private void visualizeZigzag(String s, int numRows) {
        if (numRows == 1) {
            System.out.println(s);
            return;
        }
        
        int len = s.length();
        int cycleLen = 2 * numRows - 2;
        int cols = (len / cycleLen) * (numRows - 1) + Math.min(len % cycleLen, numRows);
        
        char[][] matrix = new char[numRows][cols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = ' ';
            }
        }
        
        int idx = 0;
        int col = 0;
        while (idx < len) {
            // 向下
            for (int row = 0; row < numRows && idx < len; row++) {
                matrix[row][col] = s.charAt(idx++);
            }
            col++;
            
            // 斜向上
            for (int row = numRows - 2; row > 0 && idx < len; row--) {
                matrix[row][col++] = s.charAt(idx++);
            }
        }
        
        // 打印矩阵
        System.out.println("Z字形排列可视化：");
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        Z_convert solution = new Z_convert();
        
        System.out.println("=== LeetCode 6: Z字形变换 ===\n");
        
        // 测试用例1
        String test1 = "PAYPALISHIRING";
        int numRows1 = 3;
        System.out.println("测试用例1:");
        System.out.println("输入: s = \"" + test1 + "\", numRows = " + numRows1);
        solution.visualizeZigzag(test1, numRows1);
        String result1 = solution.convert(test1, numRows1);
        System.out.println("输出: \"" + result1 + "\"");
        System.out.println("预期: \"PAHNAPLSIIGYIR\"");
        System.out.println("通过: " + result1.equals("PAHNAPLSIIGYIR"));
        System.out.println();
        
        // 测试用例2
        String test2 = "PAYPALISHIRING";
        int numRows2 = 4;
        System.out.println("测试用例2:");
        System.out.println("输入: s = \"" + test2 + "\", numRows = " + numRows2);
        solution.visualizeZigzag(test2, numRows2);
        String result2 = solution.convert(test2, numRows2);
        System.out.println("输出: \"" + result2 + "\"");
        System.out.println("预期: \"PINALSIGYAHRPI\"");
        System.out.println("通过: " + result2.equals("PINALSIGYAHRPI"));
        System.out.println();
        
        // 测试用例3：单字符
        String test3 = "A";
        int numRows3 = 1;
        System.out.println("测试用例3:");
        System.out.println("输入: s = \"" + test3 + "\", numRows = " + numRows3);
        String result3 = solution.convert(test3, numRows3);
        System.out.println("输出: \"" + result3 + "\"");
        System.out.println("预期: \"A\"");
        System.out.println("通过: " + result3.equals("A"));
        System.out.println();
        
        // 测试用例4：单行
        String test4 = "ABCDEFG";
        int numRows4 = 1;
        System.out.println("测试用例4:");
        System.out.println("输入: s = \"" + test4 + "\", numRows = " + numRows4);
        String result4 = solution.convert(test4, numRows4);
        System.out.println("输出: \"" + result4 + "\"");
        System.out.println("预期: \"ABCDEFG\"");
        System.out.println("通过: " + result4.equals("ABCDEFG"));
        System.out.println();
        
        // 测试用例5：两行
        String test5 = "ABCDE";
        int numRows5 = 2;
        System.out.println("测试用例5:");
        System.out.println("输入: s = \"" + test5 + "\", numRows = " + numRows5);
        solution.visualizeZigzag(test5, numRows5);
        String result5 = solution.convert(test5, numRows5);
        System.out.println("输出: \"" + result5 + "\"");
        System.out.println("预期: \"ACEBD\"");
        System.out.println("通过: " + result5.equals("ACEBD"));
        System.out.println();
        
        // 测试用例6：字符串长度小于行数
        String test6 = "ABC";
        int numRows6 = 5;
        System.out.println("测试用例6:");
        System.out.println("输入: s = \"" + test6 + "\", numRows = " + numRows6);
        solution.visualizeZigzag(test6, numRows6);
        String result6 = solution.convert(test6, numRows6);
        System.out.println("输出: \"" + result6 + "\"");
        System.out.println("预期: \"ABC\"");
        System.out.println("通过: " + result6.equals("ABC"));
        System.out.println();
        
        // 测试用例7：较长字符串
        String test7 = "ABCDEFGHIJKLMNOP";
        int numRows7 = 5;
        System.out.println("测试用例7:");
        System.out.println("输入: s = \"" + test7 + "\", numRows = " + numRows7);
        solution.visualizeZigzag(test7, numRows7);
        String result7 = solution.convert(test7, numRows7);
        System.out.println("输出: \"" + result7 + "\"");
        System.out.println("预期: \"AGMBFHLNCEIKODjp\"");
        System.out.println("通过: " + result7.equals("AGMBFHLNCEIKODJO"));
        System.out.println();
        
        // 性能测试
        System.out.println("=== 性能测试 ===");
        StringBuilder longStr = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            longStr.append((char)('A' + (i % 26)));
        }
        String test8 = longStr.toString();
        int numRows8 = 10;
        
        long startTime = System.nanoTime();
        String result8 = solution.convert(test8, numRows8);
        long endTime = System.nanoTime();
        
        System.out.println("输入长度: " + test8.length());
        System.out.println("行数: " + numRows8);
        System.out.println("输出长度: " + result8.length());
        System.out.println("耗时: " + (endTime - startTime) / 1000000.0 + " ms");
        
        System.out.println("\n提示：这道题的难点在于：");
        System.out.println("1. 理解Z字形的排列规律（一个周期 = 2*numRows-2）");
        System.out.println("2. 模拟方向变化（向下 → 斜向上）");
        System.out.println("3. 处理特殊情况（numRows=1, 字符串太短等）");
    }
}
