/**
  * Subsets
Given a set of distinct integers, nums, return all possible subsets.
Note: The solution set must not contain duplicate subsets.
For example,
If nums = [1,2,3], a solution is:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
  */
 public List<List<Integer>> subsets(int[] nums) {
  List<List<Integer>> res = new ArrayList<>(1 << nums.length);
  Arrays.sort(nums);
  res.add(new ArrayList<>()); // empty list
  subsets(nums, new int[nums.length], 0, 0, res);
  return res;
 }

 private void subsets(int[] in, int[] out, int recurlen, int start,
   List<List<Integer>> res) {
  for (int i = start; i < in.length; i++) {
   out[recurlen] = in[i];
   List<Integer> l = new ArrayList<>(recurlen + 1);
   for (int j = 0; j <= recurlen; j++) {
    l.add(out[j]);
   }
   res.add(l);
   if (i < in.length - 1) {
    subsets(in, out, recurlen + 1, i + 1, res);
   }
  }
 }

 public List<List<Integer>> subsets2(int[] nums) {
  Arrays.sort(nums);
  List<List<Integer>> ret = new ArrayList<>(1 << nums.length); // 2^num.length
  ret.add(new ArrayList<>()); // empty list []

  for (int i = 0; i < nums.length; i++) {
   int size = ret.size();
   for (int j = 0; j < size; j++) {
    List<Integer> sub = new ArrayList<>(ret.get(j));
    sub.add(nums[i]);
    ret.add(sub);
   }
  }

  return ret;
 }

 /**
  * Subsets II
  * Given a collection of integers that might contain duplicates,
  * S, return all possible subsets. Elements in a subset must be in
  * non-descending order. The solution set must not contain duplicate
  * subsets. If S = [1,2,2], a solution is: [ [2], [1], [1,2,2], [2,2],
  * [1,2], [] ]
  */
 public List<List<Integer>> subsetsWithDup(int[] nums) {
  Arrays.sort(nums);
  List<List<Integer>> ret = new ArrayList<>(1 << nums.length); // 2^num.length
  ret.add(new ArrayList<>()); // empty list []

  int start = 0;
  for (int i = 0; i < nums.length; i++) {
   int size = ret.size();
   for (int j = start; j < size; j++) {
    List<Integer> sub = new ArrayList<>(ret.get(j));
    sub.add(nums[i]);
    ret.add(sub);
   }
   if (i < nums.length - 1 && nums[i + 1] == nums[i]) {
    start = size;
   } else {
    start = 0;
   }
  }

  return ret;
 }