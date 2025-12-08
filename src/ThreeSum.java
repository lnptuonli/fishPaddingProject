import java.util.*;

/**
 * LeetCode 15. 三数之和 - 3Sum
 * <p>
 * 题目描述：
 * 给你一个整数数组 nums，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足：
 * - i != j
 * - i != k
 * - j != k
 * - nums[i] + nums[j] + nums[k] == 0
 * <p>
 * 请你返回所有和为 0 且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2]
 * 注意，输出的顺序和三元组的顺序并不重要。
 * <p>
 * 示例 2：
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组和不为 0
 * <p>
 * 示例 3：
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组和为 0
 * <p>
 * 提示：
 * 3 <= nums.length <= 3000
 * -10^5 <= nums[i] <= 10^5
 * <p>
 * 核心理解：
 * <p>
 * 1. 三数之和 = 两数之和的升级版：
 * - 固定一个数 a
 * - 在剩余数组中找两个数 b + c = -a
 * - 这样就转化为两数之和问题
 * <p>
 * 2. 去重是关键难点：
 * - 不能有重复的三元组
 * - 例如：[-1,0,1] 和 [0,-1,1] 和 [1,-1,0] 是同一个三元组
 * - 需要在多个层面进行去重
 * <p>
 * 3. 关键洞察：
 * - 先排序！排序后可以：
 * * 使用双指针优化两数之和
 * * 方便去重（跳过相同的数）
 * * 提前剪枝（如果最小的三数之和>0，后面都不用看了）
 * <p>
 * 4. 去重策略：
 * a) 固定数去重：跳过与前一个相同的数
 * b) 左指针去重：找到答案后，跳过相同的数
 * c) 右指针去重：找到答案后，跳过相同的数
 * <p>
 * 解题思路：
 * <p>
 * 思路1：排序 + 双指针（推荐⭐⭐⭐）
 * - 先对数组排序
 * - 固定第一个数 nums[i]
 * - 用双指针在 [i+1, n-1] 范围内找两数之和 = -nums[i]
 * - 时间复杂度：O(n²)
 * - 空间复杂度：O(logn)，排序的栈空间
 * <p>
 * 思路2：HashMap（不推荐）
 * - 三层循环 + HashSet去重
 * - 时间复杂度：O(n³)
 * - 空间复杂度：O(n)
 * - 会超时，不推荐
 * <p>
 * 算法步骤：
 * <p>
 * 1. 排序数组
 * 2. 遍历数组，固定第一个数 nums[i]
 * 3. 如果 nums[i] > 0，直接返回（后面的数都是正数，和不可能为0）
 * 4. 跳过重复的 nums[i]（去重1）
 * 5. 使用双指针 left = i+1, right = n-1
 * 6. 计算三数之和：
 * - sum < 0：left++
 * - sum > 0：right--
 * - sum == 0：记录答案，跳过重复的left和right（去重2、3）
 * <p>
 * 易错点：
 * 1. 去重逻辑：
 * - nums[i] == nums[i-1] 要跳过，但 i=0 时不能这样判断
 * - 找到答案后，要同时移动 left 和 right 并跳过重复
 * 2. 边界条件：
 * - 数组长度 < 3
 * - 全是正数或全是负数
 * 3. 双指针移动：
 * - 找到答案后，不能只移动一个指针
 * - 要继续寻找其他可能的组合
 */
public class ThreeSum {

    /**
     * 主方法：三数之和
     *
     * @param nums 整数数组
     * @return 所有和为0的不重复三元组
     */
    public List<List<Integer>> threeSum(int[] nums) {
        // ============================================
        // 在这里实现你的解法
        // ============================================

        // 提示：排序 + 双指针
        // 
        // 步骤1：边界处理和排序
        // List<List<Integer>> result = new ArrayList<>();
        // if (nums == null || nums.length < 3) {
        //     return result;
        // }
        // Arrays.sort(nums);  // 排序！
        // 
        // 步骤2：遍历数组，固定第一个数
        // for (int i = 0; i < nums.length - 2; i++) {
        //     // 优化：如果当前数字大于0，后面不可能有和为0的三元组
        //     if (nums[i] > 0) {
        //         break;
        //     }
        //     
        //     // 去重1：跳过重复的第一个数（注意：i > 0 才能比较）
        //     if (i > 0 && nums[i] == nums[i - 1]) {
        //         continue;
        //     }
        //     
        //     // 双指针
        //     int left = i + 1;
        //     int right = nums.length - 1;
        //     int target = -nums[i];  // 需要找的两数之和
        //     
        //     while (left < right) {
        //         int sum = nums[left] + nums[right];
        //         
        //         if (sum == target) {
        //             // 找到答案
        //             result.add(Arrays.asList(nums[i], nums[left], nums[right]));
        //             
        //             // 去重2：跳过重复的left
        //             while (left < right && nums[left] == nums[left + 1]) {
        //                 left++;
        //             }
        //             // 去重3：跳过重复的right
        //             while (left < right && nums[right] == nums[right - 1]) {
        //                 right--;
        //             }
        //             
        //             // 移动双指针
        //             left++;
        //             right--;
        //         } else if (sum < target) {
        //             left++;
        //         } else {
        //             right--;
        //         }
        //     }
        // }
        // 
        // return result;

        // 关键理解：
        // 为什么要排序？
        // 1. 可以使用双指针（有序才能根据sum大小移动指针）
        // 2. 方便去重（相同的数在一起）
        // 3. 可以剪枝（nums[i]>0 直接结束）
        // 
        // 为什么三个地方都要去重？
        // 1. i 去重：避免 [-1,-1,2] 被找两次
        // 2. left 去重：避免 [-1,0,1] 和 [-1,0,1] 重复
        // 3. right 去重：同理

        // 在这里编写你的实现代码

        //以下是我自己的思路了：首先，这道题可以通过固定数组的某一个锚点，变成两数之和问题，然而问题是，两数之和的答案可能不止一个
        //那我就返回所有两数组合就好了呗，某个锚点用过一次之后，我认为包含此锚点的所有组合都应该又过了，所以下次遍历就不该有此元素
        //所以说最后两个元素是无需遍历的。最后在结果集一去重就完事
        //更简单一点点，我要是先对数组进行排序，那不仅能很方便的用双指针法去做两数之和，还能解决map难以排除使用过的锚点这个问题
        //我把{-1, 0, 1, 2, -1, -4}摆在这里写一下试试：
        List<List<Integer>> result = new ArrayList<>();
        //边界处理
        if (nums.length <= 2) {
            return result;
        }
        Arrays.sort(nums);
        //锚点遍历
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && (nums[i - 1] == nums[i])) {
                continue;
            }
            //既然排序后，有大于0的锚点了，后面的数当然不可能加起来小于0，搞笑呢
            if (nums[i]>0) {
                break;
            }
            int left = i + 1;
            int right = nums.length - 1;
            int target = 0 - nums[i];
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum < target) {
                    //这里还没找到答案，去重也不是不行，但是没啥收益
                        left++;
                } else if (sum > target) {
                    //这里还没找到答案，去重也不是不行，但是没啥收益
                        right--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    //虽然找到了一组，但是这次就应该把所有的可能的组合都找出来
                    //本来想在找到所有组合之后对List去重，但是似乎这种方法需要自己实现去重方法，相当恶心，所以作罢了
                    // 去重2：跳过所有重复的left
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    //再移动一次，因为归根结底这个数已经验过了
                    left++;

                    // 去重3：跳过所有重复的right
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    //再移动一次，因为归根结底这个数已经验过了
                    right--;
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
        ThreeSum solution = new ThreeSum();

        System.out.println("=== LeetCode 15: 三数之和 ===");
        System.out.println("找出所有和为0的不重复三元组\n");

        // 测试用例1：官方示例1
        int[] test1 = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result1 = solution.threeSum(test1);
        System.out.println("测试用例1:");
        System.out.println("输入: [-1,0,1,2,-1,-4]");
        System.out.println("输出: " + result1);
        System.out.println("预期: [[-1,-1,2],[-1,0,1]]");
        System.out.println("通过: " + (result1.size() == 2));
        System.out.println();

        // 测试用例2：无解
        int[] test2 = {0, 1, 1};
        List<List<Integer>> result2 = solution.threeSum(test2);
        System.out.println("测试用例2:");
        System.out.println("输入: [0,1,1]");
        System.out.println("输出: " + result2);
        System.out.println("预期: []");
        System.out.println("通过: " + (result2.size() == 0));
        System.out.println();

        // 测试用例3：全是0
        int[] test3 = {0, 0, 0};
        List<List<Integer>> result3 = solution.threeSum(test3);
        System.out.println("测试用例3:");
        System.out.println("输入: [0,0,0]");
        System.out.println("输出: " + result3);
        System.out.println("预期: [[0,0,0]]");
        System.out.println("通过: " + (result3.size() == 1));
        System.out.println();

        // 测试用例4：有多个0
        int[] test4 = {0, 0, 0, 0};
        List<List<Integer>> result4 = solution.threeSum(test4);
        System.out.println("测试用例4:");
        System.out.println("输入: [0,0,0,0]");
        System.out.println("输出: " + result4);
        System.out.println("预期: [[0,0,0]]");
        System.out.println("通过: " + (result4.size() == 1));
        System.out.println();

        // 测试用例5：有重复数字
        int[] test5 = {-2, 0, 0, 2, 2};
        List<List<Integer>> result5 = solution.threeSum(test5);
        System.out.println("测试用例5:");
        System.out.println("输入: [-2,0,0,2,2]");
        System.out.println("输出: " + result5);
        System.out.println("预期: [[-2,0,2]]");
        System.out.println("通过: " + (result5.size() == 1));
        System.out.println();

        // 测试用例6：复杂情况
        int[] test6 = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
        List<List<Integer>> result6 = solution.threeSum(test6);
        System.out.println("测试用例6:");
        System.out.println("输入: [-4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6]");
        System.out.println("输出: " + result6);
        System.out.println("预期答案数量: 6");
        System.out.println("实际答案数量: " + result6.size());
        System.out.println();

        // 测试用例7：最小情况
        int[] test7 = {0, 0, 0};
        List<List<Integer>> result7 = solution.threeSum(test7);
        System.out.println("测试用例7:");
        System.out.println("输入: [0,0,0]");
        System.out.println("输出: " + result7);
        System.out.println("预期: [[0,0,0]]");
        System.out.println("通过: " + (result7.size() == 1));
        System.out.println();

        // 测试用例8：全是正数
        int[] test8 = {1, 2, 3};
        List<List<Integer>> result8 = solution.threeSum(test8);
        System.out.println("测试用例8:");
        System.out.println("输入: [1,2,3]");
        System.out.println("输出: " + result8);
        System.out.println("预期: []");
        System.out.println("通过: " + (result8.size() == 0));
        System.out.println();

        // 测试用例9：全是负数
        int[] test9 = {-1, -2, -3};
        List<List<Integer>> result9 = solution.threeSum(test9);
        System.out.println("测试用例9:");
        System.out.println("输入: [-1,-2,-3]");
        System.out.println("输出: " + result9);
        System.out.println("预期: []");
        System.out.println("通过: " + (result9.size() == 0));
        System.out.println();

        // 测试用例10：边界情况
        int[] test10 = {-1, 0, 1};
        List<List<Integer>> result10 = solution.threeSum(test10);
        System.out.println("测试用例10:");
        System.out.println("输入: [-1,0,1]");
        System.out.println("输出: " + result10);
        System.out.println("预期: [[-1,0,1]]");
        System.out.println("通过: " + (result10.size() == 1));
        System.out.println();

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 先排序！这是解题的基础");
        System.out.println("2. 固定一个数，双指针找另外两个数");
        System.out.println("3. 三个地方去重：i、left、right");
        System.out.println("4. 时间复杂度：O(n²)");
        System.out.println("5. 难点：去重逻辑要正确");
    }
}





