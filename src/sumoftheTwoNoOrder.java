import java.util.HashMap;

public class sumoftheTwoNoOrder {
    public static int[] sumoftheTwo(int[] nums, int target) {
        // 使用HashMap存储数组元素和对应的索引
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            //将nums中的元素与目标值分别作差
            int complement = target - nums[i];
            map.get(complement);
            // 如果complement在map中存在，说明找到了答案
            if (map.containsKey(complement)) {
                System.out.println(map.get(complement));
                return new int[]{map.get(complement), i};
            }
            
            // 将当前元素和索引存入map
            map.put(nums[i], i);
        }
        
        // 如果没有找到答案，返回空数组
        return new int[]{};
    }
    
    public static void main(String[] args) {
        int target = 30;
        int[] nums = {7, 2, 11, 19, 27, 31};
        
        int[] result = sumoftheTwo(nums, target);
        
        if (result.length == 2) {
            System.out.println("找到答案：索引 " + result[0] + " 和索引 " + result[1]);
            System.out.println("对应的值：" + nums[result[0]] + " + " + nums[result[1]] + " = " + target);
        } else {
            System.out.println("没有找到答案");
        }
    }
}
