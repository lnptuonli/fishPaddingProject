import java.util.*;

/**
 * LeetCode 18. 四数之和 - 4Sum
 * <p>
 * 题目描述：
 * 给你一个由 n 个整数组成的数组 nums，和一个目标值 target。
 * 请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]]：
 * - 0 <= a, b, c, d < n
 * - a、b、c 和 d 互不相同
 * - nums[a] + nums[b] + nums[c] + nums[d] == target
 * <p>
 * 你可以按任意顺序返回答案。
 * 若两个四元组元素一一对应，则认为两个四元组重复。
 * <p>
 * 示例 1：
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * <p>
 * 示例 2：
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 * <p>
 * 提示：
 * 1 <= nums.length <= 200
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 * <p>
 * 核心理解：
 * <p>
 * 1. 四数之和 = 三数之和的升级版：
 * - 三数之和：固定1个数，双指针找2个数
 * - 四数之和：固定2个数，双指针找2个数
 * - 本质上就是多套一层循环！
 * <p>
 * 2. 去重策略（4个地方）：
 * - 第一个数 i 去重
 * - 第二个数 j 去重
 * - 第三个数 left 去重
 * - 第四个数 right 去重
 * <p>
 * 3. 关键洞察：
 * - 排序后使用双指针
 * - 时间复杂度：O(n³)（比暴力O(n⁴)快）
 * - 剪枝优化可以提前结束循环
 * <p>
 * 4. 易错点：
 * - 整数溢出！nums[i] + nums[j] + nums[left] + nums[right] 可能溢出
 * - 需要用 long 类型或者改用减法判断
 * - 去重逻辑要在4个地方都做好
 * <p>
 * 解题思路：
 * <p>
 * 思路：排序 + 双重循环 + 双指针（推荐⭐⭐⭐）
 * - 先对数组排序
 * - 外层循环固定第一个数 nums[i]
 * - 内层循环固定第二个数 nums[j]
 * - 用双指针在 [j+1, n-1] 范围内找另外两个数
 * - 时间复杂度：O(n³)
 * - 空间复杂度：O(logn)，排序的栈空间
 * <p>
 * 算法步骤：
 * <p>
 * 1. 排序数组
 * 2. 外层循环 i：固定第一个数
 * - 去重：跳过重复的 nums[i]
 * - 剪枝：如果 nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target，后面都不用看了
 * 3. 内层循环 j：固定第二个数
 * - 去重：跳过重复的 nums[j]
 * - 剪枝：如果 nums[i] + nums[j] + nums[j+1] + nums[j+2] > target，后面都不用看了
 * 4. 双指针 left = j+1, right = n-1
 * 5. 计算四数之和：
 * - sum < target：left++
 * - sum > target：right--
 * - sum == target：记录答案，跳过重复的left和right
 * <p>
 * 与三数之和的对比：
 * <p>
 * 相同点：
 * - 都是排序 + 双指针
 * - 都需要去重
 * - 都可以剪枝优化
 * <p>
 * 不同点：
 * - 三数之和：1层循环 + 双指针，O(n²)
 * - 四数之和：2层循环 + 双指针，O(n³)
 * - 四数之和：需要注意整数溢出问题
 * <p>
 * 整数溢出处理：
 * <p>
 * 方法1：使用 long 类型
 * long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
 * <p>
 * 方法2：改用减法（推荐）
 * if (nums[left] + nums[right] < target - nums[i] - nums[j])
 * <p>
 * 易错点：
 * 1. 整数溢出：
 * - nums[i] 范围是 -10^9 到 10^9
 * - 四个数相加可能超过 int 范围
 * 2. 去重逻辑：
 * - 4个地方都要去重：i、j、left、right
 * - 注意边界条件：i > 0, j > i+1
 * 3. 剪枝优化：
 * - 最小和 > target：break
 * - 最大和 < target：continue
 */
public class FourSum {

    /**
     * 主方法：四数之和
     *
     * @param nums   整数数组
     * @param target 目标值
     * @return 所有和为target的不重复四元组
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        // ============================================
        // 在这里实现你的解法
        // ============================================

        // 提示：排序 + 双重循环 + 双指针
        // 
        // 步骤1：边界处理和排序
        // List<List<Integer>> result = new ArrayList<>();
        // if (nums == null || nums.length < 4) {
        //     return result;
        // }
        // Arrays.sort(nums);
        // 
        // 步骤2：外层循环，固定第一个数
        // for (int i = 0; i < nums.length - 3; i++) {
        //     // 去重1：跳过重复的第一个数
        //     if (i > 0 && nums[i] == nums[i - 1]) {
        //         continue;
        //     }
        //     
        //     // 剪枝1：最小和都大于target，后面不用看了
        //     if ((long)nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target) {
        //         break;
        //     }
        //     
        //     // 剪枝2：最大和都小于target，当前i不用看了
        //     if ((long)nums[i] + nums[n-3] + nums[n-2] + nums[n-1] < target) {
        //         continue;
        //     }
        //     
        //     // 步骤3：内层循环，固定第二个数
        //     for (int j = i + 1; j < nums.length - 2; j++) {
        //         // 去重2：跳过重复的第二个数
        //         if (j > i + 1 && nums[j] == nums[j - 1]) {
        //             continue;
        //         }
        //         
        //         // 剪枝3：最小和都大于target
        //         if ((long)nums[i] + nums[j] + nums[j+1] + nums[j+2] > target) {
        //             break;
        //         }
        //         
        //         // 剪枝4：最大和都小于target
        //         if ((long)nums[i] + nums[j] + nums[n-2] + nums[n-1] < target) {
        //             continue;
        //         }
        //         
        //         // 步骤4：双指针
        //         int left = j + 1;
        //         int right = nums.length - 1;
        //         
        //         while (left < right) {
        //             long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
        //             
        //             if (sum == target) {
        //                 // 找到答案
        //                 result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
        //                 
        //                 // 去重3：跳过重复的left
        //                 while (left < right && nums[left] == nums[left + 1]) {
        //                     left++;
        //                 }
        //                 // 去重4：跳过重复的right
        //                 while (left < right && nums[right] == nums[right - 1]) {
        //                     right--;
        //                 }
        //                 
        //                 // 移动双指针
        //                 left++;
        //                 right--;
        //             } else if (sum < target) {
        //                 left++;
        //             } else {
        //                 right--;
        //             }
        //         }
        //     }
        // }
        // 
        // return result;

        // 关键理解：
        // 为什么是O(n³)？
        // - 外层循环：O(n)
        // - 内层循环：O(n)
        // - 双指针：O(n)
        // - 总共：O(n) * O(n) * O(n) = O(n³)
        // 
        // 为什么要用 long？
        // - nums[i] 范围：-10^9 到 10^9
        // - 四个数相加：-4*10^9 到 4*10^9
        // - int 范围：-2.1*10^9 到 2.1*10^9
        // - 会溢出！必须用 long
        // 
        // 为什么4个地方都要去重？
        // - i 去重：避免 [1,1,2,3] 被找两次
        // - j 去重：避免 [1,2,2,3] 被找两次
        // - left 去重：避免 [1,2,3,3] 被找两次
        // - right 去重：避免 [1,2,3,4] 和 [1,2,3,4] 重复

        // 在这里编写你的实现代码
        //首先，还是排序
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        if (nums.length < 4)
            return result;

        long longtarget = target;

// 第一层循环：固定第一个数
        for (int i = 0; i < nums.length - 3; i++) {
            // 去重1：跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 剪枝1：最小和都大于target
            long sum = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (sum > longtarget) {
                break;  // 用break，不是return
            }

            // 剪枝2：最大和都小于target（可选）
            sum = (long) nums[i] + nums[nums.length - 3] + nums[nums.length - 2] + nums[nums.length - 1];
            if (sum < longtarget) {
                continue;  // 当前i不用看了，跳到下一个i
            }

            // 第二层循环：固定第二个数
            for (int j = i + 1; j < nums.length - 2; j++) {  // ✅ j从i+1开始
                // 去重2：跳过重复的第二个数
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // 剪枝3：最小和都大于target
                sum = (long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                if (sum > longtarget) {
                    break;  // 用break
                }

                // 剪枝4：最大和都小于target（可选）
                sum = (long) nums[i] + nums[j] + nums[nums.length - 2] + nums[nums.length - 1];
                if (sum < longtarget) {
                    continue;
                }

                // 双指针
                int left = j + 1;
                int right = nums.length - 1;

                while (left < right) {
                    sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum < longtarget) {
                        left++;
                    } else if (sum > longtarget) {
                        right--;
                    } else {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // 去重3：跳过重复的left
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;

                        // 去重4：跳过重复的right
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    }
                }
            }
        }

        return result;

        // ============================================
        // 实现结束
        // ============================================

    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        FourSum solution = new FourSum();

        System.out.println("=== LeetCode 18: 四数之和 ===");
        System.out.println("找出所有和为target的不重复四元组\n");

        // 测试用例1：官方示例1
        int[] test1 = {1, 0, -1, 0, -2, 2};
        int target1 = 0;
        List<List<Integer>> result1 = solution.fourSum(test1, target1);
        System.out.println("测试用例1:");
        System.out.println("输入: nums = [1,0,-1,0,-2,2], target = 0");
        System.out.println("输出: " + result1);
        System.out.println("预期: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]");
        System.out.println("答案数量: " + result1.size() + " (预期: 3)");
        System.out.println();

        // 测试用例2：官方示例2
        int[] test2 = {2, 2, 2, 2, 2};
        int target2 = 8;
        List<List<Integer>> result2 = solution.fourSum(test2, target2);
        System.out.println("测试用例2:");
        System.out.println("输入: nums = [2,2,2,2,2], target = 8");
        System.out.println("输出: " + result2);
        System.out.println("预期: [[2,2,2,2]]");
        System.out.println("通过: " + (result2.size() == 1));
        System.out.println();

        // 测试用例3：无解
        int[] test3 = {1, 2, 3, 4};
        int target3 = 100;
        List<List<Integer>> result3 = solution.fourSum(test3, target3);
        System.out.println("测试用例3:");
        System.out.println("输入: nums = [1,2,3,4], target = 100");
        System.out.println("输出: " + result3);
        System.out.println("预期: []");
        System.out.println("通过: " + (result3.size() == 0));
        System.out.println();

        // 测试用例4：全是0
        int[] test4 = {0, 0, 0, 0};
        int target4 = 0;
        List<List<Integer>> result4 = solution.fourSum(test4, target4);
        System.out.println("测试用例4:");
        System.out.println("输入: nums = [0,0,0,0], target = 0");
        System.out.println("输出: " + result4);
        System.out.println("预期: [[0,0,0,0]]");
        System.out.println("通过: " + (result4.size() == 1));
        System.out.println();

        // 测试用例5：负数target
        int[] test5 = {-3, -2, -1, 0, 0, 1, 2, 3};
        int target5 = 0;
        List<List<Integer>> result5 = solution.fourSum(test5, target5);
        System.out.println("测试用例5:");
        System.out.println("输入: nums = [-3,-2,-1,0,0,1,2,3], target = 0");
        System.out.println("输出: " + result5);
        System.out.println("答案数量: " + result5.size());
        System.out.println();

        // 测试用例6：整数溢出测试（重要！）
        int[] test6 = {1000000000, 1000000000, 1000000000, 1000000000};
        int target6 = -294967296;
        List<List<Integer>> result6 = solution.fourSum(test6, target6);
        System.out.println("测试用例6:");
        System.out.println("输入: nums = [1000000000,1000000000,1000000000,1000000000]");
        System.out.println("target = -294967296");
        System.out.println("输出: " + result6);
        System.out.println("预期: [] (和为4*10^9，不等于target)");
        System.out.println("通过: " + (result6.size() == 0));
        System.out.println("说明: 这个测试用例检查是否正确处理了整数溢出");
        System.out.println();

        // 测试用例7：有重复元素
        int[] test7 = {-1, 0, 1, 2, -1, -4};
        int target7 = -1;
        List<List<Integer>> result7 = solution.fourSum(test7, target7);
        System.out.println("测试用例7:");
        System.out.println("输入: nums = [-1,0,1,2,-1,-4], target = -1");
        System.out.println("输出: " + result7);
        System.out.println("答案数量: " + result7.size());
        System.out.println();

        // 测试用例8：最小数组
        int[] test8 = {1, 2, 3, 4};
        int target8 = 10;
        List<List<Integer>> result8 = solution.fourSum(test8, target8);
        System.out.println("测试用例8:");
        System.out.println("输入: nums = [1,2,3,4], target = 10");
        System.out.println("输出: " + result8);
        System.out.println("预期: [[1,2,3,4]]");
        System.out.println("通过: " + (result8.size() == 1));
        System.out.println();

        // 测试用例9：边界情况
        int[] test9 = {-5, -4, -3, -2, -1, 0, 0, 1, 2, 3, 4, 5};
        int target9 = 0;
        List<List<Integer>> result9 = solution.fourSum(test9, target9);
        System.out.println("测试用例9:");
        System.out.println("输入: nums = [-5,-4,-3,-2,-1,0,0,1,2,3,4,5], target = 0");
        System.out.println("输出: " + result9);
        System.out.println("答案数量: " + result9.size());
        System.out.println();

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 四数之和 = 三数之和 + 多一层循环");
        System.out.println("2. 时间复杂度：O(n³)");
        System.out.println("3. 4个地方去重：i、j、left、right");
        System.out.println("4. ⚠️ 注意整数溢出！必须用 long 类型");
        System.out.println("5. 剪枝优化可以提前结束循环");
    }
}

