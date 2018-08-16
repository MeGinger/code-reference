import java.util.*;

/*
http://www.careercup.com/question?id=9865865
k distinct integers [0, N) 
Select a random no [0,N) which is not in this k distinct list. 

Example: 
[4, 6, 9]  // ordered, we can use binary search
Choose a random no between 0 - 9 which is not 4, or 6, or 9. 

Valid output: 2 
Invalid output: 6

0 1 2 3 4 5 6 7 8 9
        X   X     X  k

0 1 2 3 5 7 8
       +1+2+2 // 遇到invalid 后面的数包括自己的数都+1
0 1 2 3 4 5 6
*/
public class RandomNextIntExceptK {

	private int[] k;
	private int[] kk;
	private int[] kAdd;

	private Random rand = new Random();

	// assume k is sorted
	public RandomNextIntExceptK(int[] k) {
		Arrays.sort(k);
		this.k = k;
		Map<Integer, Integer> kCount = new TreeMap<Integer, Integer>();
		for (int i = 0; i < k.length; i++) {
			int key = k[i] - i;
			if (kCount.containsKey(key)) {
				int count = kCount.get(key);
				kCount.put(key, count + 1);
			} else {
				kCount.put(key, 1);
			}
		}

		this.kk = new int[kCount.size()];
		this.kAdd = new int[kCount.size()];
		int index = 0, sum = 0;
		for (Map.Entry<Integer, Integer> entry : kCount.entrySet()) {
			this.kk[index] = entry.getKey();
			int count = entry.getValue();
			this.kAdd[index] = sum + count;
			sum += count;
			index++;
		}
	}

	public int nextInt2(int n) {
		int result = rand.nextInt(n - k.length);
		if (result >= this.kk[0]) {
			int indexInK = Test.binarySearch(this.kk, result);
			result += this.kAdd[indexInK];
		}
		return result;
	}

	public int nextInt(int n) {
		int result = rand.nextInt(n - k.length);

		for (int f : k) {
			if (result >= f) {
				++result;
			} else {
				break;
			}
		}

		return result;
	}

	/**
k = [1, 3, 4, 7, 8]
kk = [1, 2, 2, 4, 4] => [1(1), 2(2), 4(2)]
kAdd = [1, 3, 3, 5, 5] => [1(1), 2(3), 4(5)]
n - k.length = 5
[0, 1, 2, 3, 4] => [0, 2, 5, 6, 9] => [(>=1)+1, (>=2)+3, (>=4)+5]
	 */
	public static void main(String[] args) {
		int[] k = new int[]{1, 3, 4, 7, 8};
		RandomNextIntExceptK r = new RandomNextIntExceptK(k);
		for (int i = 0; i < 100 ; i++) {
			System.out.print(r.nextInt2(10));
		}
	}

}
