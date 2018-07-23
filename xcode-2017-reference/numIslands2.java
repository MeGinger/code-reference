public List<Integer> numIslands2(int m, int n, int[][] positions) {
		UnionFind set = new UnionFind(m * n);
		Arrays.fill(set.id, -1);
		List<Integer> res = new ArrayList<>();
		int count = 0;
		for (int[] position : positions) {
			int x = position[0], y = position[1];
			int index = x * n + y;
			count++;
			set.id[index] = index;
			for (int i = 0; i < dirs.length; i++) {
				int newX = x + dirs[i][0];
				int newY = y + dirs[i][1];
				if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
					int newIndex = newX * n + newY;
					if (set.id[newIndex] == -1) {
						continue; // not visited
					}
					if (!set.find(index, newIndex)) {
						set.union(index, newIndex);
						count--;
					}
				}
			}
			res.add(count);
		}
		return res;
	}