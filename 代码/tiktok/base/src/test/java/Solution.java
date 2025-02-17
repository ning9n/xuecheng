import java.util.*;

public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().lengthOfLongestSubstring(" "));
    }
    public int lengthOfLongestSubstring(String s) {
        int ans=1;
        boolean[] booleans=new boolean[255];
        if(s.isEmpty()){
            return 0;
        }
        char[] arr=s.toCharArray();
        int left=0;
        int right=1;
        booleans[arr[0]]=true;
        while (right<s.length()){
            while(right<s.length()&&!booleans[arr[right]]){
                booleans[arr[right]]=true;
                right++;
            }
            ans=Math.max(ans,right-left);
            booleans[arr[left++]]=false;
        }
        return ans;
    }
}
