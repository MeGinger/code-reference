Max Consecutive Ones
public int findMaxConsecutiveOnes(int[] nums) {
    int max = 0;
    for (int l = 0, h = 0; h < nums.length; h++) {
        if (nums[h] == 0) {
            l = h + 1;
            continue;
        }
        max = Math.max(max, h - l + 1);
    }                                                               
    return max;  
}

Max Consecutive Ones II
public int findMaxConsecutiveOnes(int[] nums) {
    int max = 0, zero = 0, k = 1; // flip at most k zero
    for (int l = 0, h = 0; h < nums.length; h++) {
        if (nums[h] == 0)                                           
            zero++;
        while (zero > k)
            if (nums[l++] == 0)
                zero--;                                     
        max = Math.max(max, h - l + 1);
    }                                                               
    return max;             
}

Max Consecutive Ones II - follow up: input stream - use Queue
public int findMaxConsecutiveOnes(int[] nums) {                 
    int max = 0, k = 1; // flip at most k zero
    Queue<Integer> zeroIndex = new LinkedList<>(); 
    for (int l = 0, h = 0; h < nums.length; h++) {
        if (nums[h] == 0)
            zeroIndex.offer(h);
        if (zeroIndex.size() > k)                                   
            l = zeroIndex.poll() + 1;
        max = Math.max(max, h - l + 1);
    }
    return max;                     
}


Comparator - 
a.compareTo(b) < 0
a is lexicographically smaller


Greatest Common Divisor
public int gcd(int a, int b) {
    return b > 0 ? gcd(b, a % b) : a;
}

Answers within 10^-6 of the true value will be accepted as correct.

while (left + 1e-6 < right) {
	...            
}
return right;


线段树（segment tree）- complete binary tree
* 是用来存放给定区间（segment, or interval）内对应信息的一种数据结构。
* 与树状数组（binary indexed tree）相似
* 线段树所使用的这个二叉树是用一个数组保存的，与堆（Heap）的实现方式相同。
* 长度为N的输入数组，线段树的高度为logN
* 以根结点为例，根结点代表arr[0:N]区间所对应的信息，接着根结点被分为两个子树，
分别存储arr[0:(N-1)/2]及arr[(N-1)/2+1:N]两个子区间对应的信息

1. T的根结点代表整个数组所在的区间对应的信息，及arr[0:N]（不含N)所对应的信息。 
2. T的每一个叶结点存储对应于输入数组的每一个单个元素构成的区间arr[i]所对应的信息，此处0≤i<N。 
3. T的每一个中间结点存储对应于输入数组某一区间arr[i:j]对应的信息，此处0≤i<j<N。 




线段树 
* 数组相应的区间查询（range query）
* 元素更新（update）操作。

与树状数组不同的是，线段树
* 区间最大值/最小值（Range Minimum/Maximum Query problem）
* 区间异或值的查询。

树状数组，线段树：
* 更新（update）的操作为O(logn)
* 进行区间查询（range query）的操作也为O(logn)




















