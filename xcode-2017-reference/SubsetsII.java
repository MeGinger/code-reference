// XCode 2017 Lecture 10

/* Subsets II
 * Given a collection of integers that might contain duplicates S,
 * return all possible subsets. Elements in a subset must be in
 * non-descending order. The solution set must not contain duplicate
 * subsets. If S = [1,2,2], a solution is: [ [2], [1], [1,2,2], [2,2],
 * [1,2], [] ]
 */
public class SubsetsII {
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
}