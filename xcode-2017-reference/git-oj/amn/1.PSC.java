Permutation

SubSet

Combination



Permutation Sequence

return the kth permutation sequence.
class Solution {
    public String getPermutation(int n, int k) {
        int pos = 0;
        List<Integer> nums = new ArrayList<>();
        int[] factorial = new int[n];
        StringBuilder sb = new StringBuilder();
        
        // factorial lookup: {1,  1,  2,  6,  24, ... (n-1)!}
        // factorial lookup: {0!, 1!, 2!, 3!, 4!, ... (n-1)!}
        int sum = 1;
        factorial[0] = 1;
        for (int i = 1; i < n; i++) {
            sum *= i;
            factorial[i] = sum;
        }
        
        
        // nums: {1, 2, 3, 4, ... n}
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }
        
        k--; // k is the position/index
        // index starts from 0 
        // n * (n - 1)!  -> 0...n-1
        for (int i = 1; i <= n; i++) {
            int index = k / factorial[n - i]; // 
            sb.append(nums.get(index));
            nums.remove(index);
            // k -= index * factorial[n - i]; // ?
            k = k % factorial[n - i]; 
        }
        
        return sb.toString();
    }
}