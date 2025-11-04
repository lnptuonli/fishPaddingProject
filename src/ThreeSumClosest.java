import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 16. 最接近的三数之和 - 3Sum Closest
 * 
 * 题目描述：
 * 给你一个长度为 n 的整数数组 nums 和一个目标值 target。
 * 请你从 nums 中选出三个整数，使它们的和与 target 最接近。
 * 返回这三个数的和。
 * 
 * 假定每组输入只存在恰好一个解。
 * 
 * 示例 1：
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2)
 * 
 * 示例 2：
 * 输入：nums = [0,0,0], target = 1
 * 输出：0
 * 解释：与 target 最接近的和是 0 (0 + 0 + 0 = 0)
 * 
 * 提示：
 * 3 <= nums.length <= 1000
 * -1000 <= nums[i] <= 1000
 * -10^4 <= target <= 10^4
 * 
 * 核心理解：
 * 
 * 1. 与第15题（三数之和）的关系：
 *    - 第15题：找所有和为0的三元组
 *    - 第16题：找最接近target的一个三元组的和
 *    - 核心算法完全一样：排序 + 双指针
 * 
 * 2. 关键区别：
 *    - 不需要去重（只返回一个答案）
 *    - 不需要收集所有答案（只要最接近的）
 *    - 需要维护"最小差距"
 * 
 * 3. 最接近的含义：
 *    - |sum - target| 最小
 *    - 例如：target=1, sum1=2, sum2=0
 *      |2-1|=1 < |0-1|=1, 所以2和0都可以
 *      但通常会先遇到谁就返回谁
 * 
 * 4. 优化技巧：
 *    - 如果找到sum == target，直接返回（差距为0，不可能更接近）
 *    - 排序后用双指针，时间复杂度O(n²)
 * 
 * 解题思路：
 * 
 * 思路：排序 + 双指针（推荐⭐⭐⭐）
 * - 先对数组排序
 * - 固定第一个数 nums[i]
 * - 用双指针在 [i+1, n-1] 范围内找最接近的两数之和
 * - 维护全局最小差距和对应的sum
 * - 时间复杂度：O(n²)
 * - 空间复杂度：O(logn)，排序的栈空间
 * 
 * 算法步骤：
 * 
 * 1. 排序数组
 * 2. 初始化 closestSum（可以初始化为前三个数的和）
 * 3. 遍历数组，固定第一个数 nums[i]
 * 4. 使用双指针 left = i+1, right = n-1
 * 5. 计算三数之和 sum = nums[i] + nums[left] + nums[right]
 * 6. 如果 sum == target，直接返回（不可能更接近）
 * 7. 如果 |sum - target| < |closestSum - target|，更新 closestSum
 * 8. 根据 sum 与 target 的关系移动指针：
 *    - sum < target：left++（需要更大的和）
 *    - sum > target：right--（需要更小的和）
 * 9. 返回 closestSum
 * 
 * 与第15题的对比：
 * 
 * 相同点：
 * - 都是排序 + 双指针
 * - 都是固定一个数，找另外两个数
 * - 都是O(n²)时间复杂度
 * 
 * 不同点：
 * - 第15题需要去重，第16题不需要
 * - 第15题收集所有答案，第16题只要一个
 * - 第15题判断 sum == 0，第16题判断 |sum - target| 最小
 * 
 * 易错点：
 * 1. closestSum 的初始化：不能用Integer.MAX_VALUE，要用实际的三数之和
 * 2. 差距比较：要用绝对值 Math.abs()
 * 3. 提前返回：找到sum == target时直接返回
 */
public class ThreeSumClosest {
    
    /**
     * 主方法：最接近的三数之和
     * 
     * @param nums 整数数组
     * @param target 目标值
     * @return 最接近target的三数之和
     */
    public int threeSumClosest(int[] nums, int target) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================
        
        // 提示：排序 + 双指针（和第15题几乎一样）
        // 
        // 步骤1：排序
        // Arrays.sort(nums);
        // 
        // 步骤2：初始化最接近的和（用前三个数）
        // int closestSum = nums[0] + nums[1] + nums[2];
        // 
        // 步骤3：遍历数组，固定第一个数
        // for (int i = 0; i < nums.length - 2; i++) {
        //     int left = i + 1;
        //     int right = nums.length - 1;
        //     
        //     while (left < right) {
        //         int sum = nums[i] + nums[left] + nums[right];
        //         
        //         // 如果找到完全相等的，直接返回
        //         if (sum == target) {
        //             return sum;
        //         }
        //         
        //         // 如果当前和更接近target，更新closestSum
        //         if (Math.abs(sum - target) < Math.abs(closestSum - target)) {
        //             closestSum = sum;
        //         }
        //         
        //         // 根据sum与target的关系移动指针
        //         if (sum < target) {
        //             left++;   // 和太小，需要更大的数
        //         } else {
        //             right--;  // 和太大，需要更小的数
        //         }
        //     }
        // }
        // 
        // return closestSum;
        
        // 关键理解：
        // 为什么不需要去重？
        // - 因为只要一个答案，不需要避免重复
        // 
        // 如何判断"最接近"？
        // - 比较 |sum - target| 的大小
        // - 差距越小越接近
        // 
        // 什么时候可以提前返回？
        // - sum == target 时，差距为0，不可能更接近
        
        // 在这里编写你的实现代码
        Arrays.sort(nums);
        //锚点遍历,初始化差值
        int result=nums[0]+nums[1] + nums[nums.length - 1];
        int ABSmindistance=Math.abs(target-nums[0]-nums[1] - nums[nums.length - 1]);
        for (int i = 0; i < nums.length - 2; i++) {
            //只有一个解，那不用去重
            int left = i + 1;
            int right = nums.length - 1;
            while(left<right){
                int distance=target-nums[i]-nums[left] - nums[right];
                int ABSdistance=Math.abs(distance);
                //若差距较小,要更新结果
                if(ABSdistance<ABSmindistance){
                    result=nums[i]+nums[left] + nums[right];
                    ABSmindistance=ABSdistance;
                }
                if(ABSdistance==0){
                    return nums[i]+nums[left] + nums[right];
                }
                if((nums[i]+nums[left] + nums[right])<target)
                    left++;
                else
                    right--;
            }

        }
        return result;
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // ============================================
        // TODO: 实现结束
        // ============================================

    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        ThreeSumClosest solution = new ThreeSumClosest();
        
        System.out.println("=== LeetCode 16: 最接近的三数之和 ===");
        System.out.println("找出最接近target的三数之和\n");
        
        // 测试用例1：官方示例1
        int[] test1 = {-1, 2, 1, -4};
        int target1 = 1;
        int result1 = solution.threeSumClosest(test1, target1);
        System.out.println("测试用例1:");
        System.out.println("输入: nums = [-1,2,1,-4], target = 1");
        System.out.println("输出: " + result1);
        System.out.println("预期: 2");
        System.out.println("解释: -1 + 2 + 1 = 2");
        System.out.println("通过: " + (result1 == 2));
        System.out.println();
        
        // 测试用例2：官方示例2
        int[] test2 = {0, 0, 0};
        int target2 = 1;
        int result2 = solution.threeSumClosest(test2, target2);
        System.out.println("测试用例2:");
        System.out.println("输入: nums = [0,0,0], target = 1");
        System.out.println("输出: " + result2);
        System.out.println("预期: 0");
        System.out.println("通过: " + (result2 == 0));
        System.out.println();
        
        // 测试用例3：完全相等
        int[] test3 = {1, 1, 1, 0};
        int target3 = 3;
        int result3 = solution.threeSumClosest(test3, target3);
        System.out.println("测试用例3:");
        System.out.println("输入: nums = [1,1,1,0], target = 3");
        System.out.println("输出: " + result3);
        System.out.println("预期: 3 (1+1+1=3)");
        System.out.println("通过: " + (result3 == 3));
        System.out.println();
        
        // 测试用例4：负数target
        int[] test4 = {-1, 2, 1, -4};
        int target4 = -3;
        int result4 = solution.threeSumClosest(test4, target4);
        System.out.println("测试用例4:");
        System.out.println("输入: nums = [-1,2,1,-4], target = -3");
        System.out.println("输出: " + result4);
        System.out.println("预期: -3 (-4+(-1)+2=-3, 完全相等!)");
        System.out.println("通过: " + (result4 == -3));
        System.out.println();
        
        // 测试用例5：大target
        int[] test5 = {1, 1, 1, 1};
        int target5 = 100;
        int result5 = solution.threeSumClosest(test5, target5);
        System.out.println("测试用例5:");
        System.out.println("输入: nums = [1,1,1,1], target = 100");
        System.out.println("输出: " + result5);
        System.out.println("预期: 3 (1+1+1=3)");
        System.out.println("通过: " + (result5 == 3));
        System.out.println();
        
        // 测试用例6：小target
        int[] test6 = {1, 1, 1, 1};
        int target6 = -100;
        int result6 = solution.threeSumClosest(test6, target6);
        System.out.println("测试用例6:");
        System.out.println("输入: nums = [1,1,1,1], target = -100");
        System.out.println("输出: " + result6);
        System.out.println("预期: 3 (1+1+1=3)");
        System.out.println("通过: " + (result6 == 3));
        System.out.println();
        
        // 测试用例7：有正有负
        int[] test7 = {-3, -2, -5, 3, -4};
        int target7 = -1;
        int result7 = solution.threeSumClosest(test7, target7);
        System.out.println("测试用例7:");
        System.out.println("输入: nums = [-3,-2,-5,3,-4], target = -1");
        System.out.println("输出: " + result7);
        System.out.println("预期: -2 (-5+3+(-2)=-4 或 -3-2+3=-2)");
        System.out.println("实际差距: |" + result7 + " - (-1)| = " + Math.abs(result7 - (-1)));
        System.out.println();
        
        // 测试用例8：最小数组
        int[] test8 = {0, 1, 2};
        int target8 = 0;
        int result8 = solution.threeSumClosest(test8, target8);
        System.out.println("测试用例8:");
        System.out.println("输入: nums = [0,1,2], target = 0");
        System.out.println("输出: " + result8);
        System.out.println("预期: 3 (0+1+2=3)");
        System.out.println("通过: " + (result8 == 3));
        System.out.println();
        
        // 测试用例9：有重复元素
        int[] test9 = {1, 2, 4, 8, 16, 32, 64, 128};
        int target9 = 82;
        int result9 = solution.threeSumClosest(test9, target9);
        System.out.println("测试用例9:");
        System.out.println("输入: nums = [1,2,4,8,16,32,64,128], target = 82");
        System.out.println("输出: " + result9);
        System.out.println("预期: 82 (2+16+64=82 或其他组合)");
        System.out.println("通过: " + (result9 == 82));
        System.out.println();
        
        System.out.println("=== 核心要点 ===");
        System.out.println("1. 与第15题（三数之和）几乎完全相同");
        System.out.println("2. 排序 + 双指针，时间复杂度O(n²)");
        System.out.println("3. 不需要去重（只要一个答案）");
        System.out.println("4. 维护最小差距：|sum - target|");
        System.out.println("5. sum == target 时可以直接返回");
    }
}

