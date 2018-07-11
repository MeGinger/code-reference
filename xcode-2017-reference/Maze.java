//maze
public boolean maze(int[][] matrix) {
	if(matrix == null || matrix.length == 0 || matrix[0].length ==0) {
		return false;
	}
	if (matrix[0][0] == 9) {
		return true;
	}
	int m = matrix.length, n = matrix[0].length;
	Queue<int[]> queue = new LinkedList<>();
	queue.offer(new int[]{0,0});
	matrix[0][0] = 1;
	while(!queue.isEmpty()) {
		int[] cur = queue.poll();
		for (int[] dir : dirs ) {
			int x = cur[0] + dir[0];
			int y = cur[1] + dir[1];
			if (x>= 0&& x<m && y>= 0 && y < n) {
				if(matrix[x][y] == 9){
					return true;
				}
				if (matrix[x][y] ==0) {
					queue.offer(new int[] {x, y});
					matrix[x][y] = 1;
				}
			}
		}
	}
	return false;
}