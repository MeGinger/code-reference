/**
	 * Given an integer array, find number of subsets that its sum of min and max is less than k.
	 * Use two pointers to find [min, max], then the subsets need to include min
	 * while max is optional,
	 * Refer: Valid Triangle Number 
	 */
	public int numOfSubsetsWithMinAndMaxSumLessThanK(int[] nums, int k) {
		Arrays.sort(nums);
		int count = 0;
		int l = 0, r = nums.length - 1;
		while (l <= r) {
			if (nums[l] + nums[r] >= k) {
				r--;
				continue;
			}
			// l needs to be included while we have [l + 1 ~ r] for combinations
			int n = r - l; // r - (l + 1) + 1;
			count += 1 << n; // 2 ^ (n)
			l++;
		}
		return count;
	}