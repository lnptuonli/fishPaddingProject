import java.util.ArrayList;

public class sumoftheTwo {
    public static ArrayList sumoftheTwoArrayList(ArrayList<Integer> num,int target){

        int left=0;
        int right=num.size()-1;
        ArrayList<Integer>down=new ArrayList<>();
        while(left<right)
        {
            int sum= num.get(left)+num.get(right);
            if(sum<target){
                left++;
            }
            else if(sum>target){
                right--;
            }
            else{

                down.add(left);
                down.add(right);
                break;
            }
        }
        return down;
    }


    public static void main(String[] args) {
        //假设有序数组，
        int target=30;
        ArrayList<Integer>num1=new ArrayList<>();
        num1.add(2);
        num1.add(7);
        num1.add(11);
        num1.add(15);
        num1.add(17);
        num1.add(19);
        num1.add(21);
        ArrayList<Integer>result=new ArrayList<>();
        result=sumoftheTwoArrayList(num1,target);
        System.out.println(result);
    }
}
