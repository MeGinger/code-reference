* Gray Code (判断两个byte number 是不是互为gray code. The gray code is a binary numeral system where two successive values differ in only one bit)
* K Closest Points (http://www.lintcode.com/en/problem/k-closest-points/)
* Kth Largest Element in an Array (https://leetcode.com/problems/kth-largest-element-in-an-array/)
* Longest Palindrome (https://leetcode.com/problems/longest-palindrome/)
* Maximum Sum of 3 Non-Overlapping Subarrays (https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays)

```java
  /*
    byte: 8 bits
    int : 32 bits

    a(byte) ^ b(byte)
        24 | 8
    11..11 | result

    0xFF
        24 | 8
    00..00 | 1111 1111

    (c & (c - 1)) used to remove last one bit

    TEST CASE
    a: 3 (0000 0011)
    b: 7 (0000 0111)
    c:
        24 | 8
    11..11 | 0000 0100
  */
  public static boolean grayCode(byte a, byte b) {
      int c = (a ^ b) & 0xFF;
      System.out.println(Integer.toBinaryString(c));
      return c != 0 && (c & (c - 1)) == 0;
  }
```


```java
  // maxHeap solution, worst O(nlogk) time, best O(n) time, O(k) space
  public Point[] findKClosestPoints(Point[] points, int k, Point target) {
      if (points == null || points.length == 0 || k == 0) {
          return new Point[0];
      }
      if (k >= points.length) {
          return points;
      }
      PointWithDistance[] distances = Arrays.stream(points).map(p -> new PointWithDistance(p, getDistance(p, target)))
              .toArray(size -> new PointWithDistance[size]);
      System.out.println(Arrays.toString(distances));
      Queue<PointWithDistance> maxHeap = new PriorityQueue<>(k);
      for (PointWithDistance point : distances) {
          if (maxHeap.size() < k) {// put k points to heap
              maxHeap.offer(point);
              continue;
          }
          if (maxHeap.peek().compareTo(point) < 0) {
              // if maxHeap's point's distance is far from target than point
              maxHeap.poll();
              maxHeap.offer(point);
          }
      }
      Point[] result = new Point[k];
      for (int i = k - 1; i >= 0; i--) {
          result[i] = maxHeap.poll().point;
      }
      return result;
  }

  public Point[] findKClosestPoints2(Point[] points, int k, Point target) {
      if (points == null || points.length == 0 || k == 0) {
          return new Point[0];
      }
      if (k >= points.length) {
          return points;
      }
      Comparator<Point> pointComparator = new Comparator<Point>() {
          @Override // compare point a and point b's distance from target
          public int compare(Point a, Point b) {
              long d1 = getDistance(a, target);
              long d2 = getDistance(b, target);
              return Long.compare(d2, d1);
          }
      };
      Queue<Point> maxHeap = new PriorityQueue<>(k, pointComparator);
      for (Point point : points) {
          if (maxHeap.size() < k) {// put k points to heap
              maxHeap.offer(point);
              continue;
          }
          if (pointComparator.compare(maxHeap.peek(), point) < 0) {
              // if maxHeap's point's distance is far from target than point
              maxHeap.poll();
              maxHeap.offer(point);
          }
      }
      Point[] result = new Point[k];
      for (int i = k - 1; i >= 0; i--) {
          result[i] = maxHeap.poll();
      }
      return result;
  }

  // QuickSelect solution, average O(n) time(O(n + klogk) time if output is
  // sorted), worst case O(n^2) time, O(1) space
  public Point[] findKClosestPoints3(Point[] points, int k, Point target) {
      if (points == null || points.length == 0 || k == 0) {
          return new Point[0];
      }
      if (k >= points.length) {
          return points;
      }
      int start = 0, end = points.length - 1, index = k - 1;
      PointWithDistance[] distances = Arrays.stream(points).map(p -> new PointWithDistance(p, getDistance(p, target)))
              .toArray(size -> new PointWithDistance[size]);
      while (start < end) {
          int pivot = partition(distances, start, end);
          if (pivot < index) {
              start = pivot + 1;
          } else if (pivot > index) {
              end = pivot - 1;
          } else {
              break;
          }
      }

      System.out.println(Arrays.toString(distances));
      Point[] result = new Point[k];
      for (int i = 0; i < k; i++) {
          result[i] = distances[i].point;
      }
      return result;
  }

  private int partition(PointWithDistance[] points, int start, int end) {
      int pivot = start;
      while (start <= end) {
          while (start <= end && points[start].distance <= points[pivot].distance) {
              start++;
          }
          while (start <= end && points[end].distance > points[pivot].distance) {
              end--;
          }
          if (start > end) {
              break;
          }
          swap(points, start, end);
      }
      swap(points, pivot, end);
      return end;
  }

  private long getDistance(Point a, Point b) {
      return ((long) a.x - b.x) * ((long) a.x - b.x) + ((long) a.y - b.y) * ((long) a.y - b.y);
  }

  private void swap(PointWithDistance[] points, int a, int b) {
      PointWithDistance tmp = points[a];
      points[a] = points[b];
      points[b] = tmp;
  }

  class PointWithDistance implements Comparable<PointWithDistance> {
      Point point;
      long distance;

      PointWithDistance(Point point, long distance) {
          this.point = point;
          this.distance = distance;
      }

      @Override
      public String toString() {
          return "[point=" + point + ", distance=" + distance + "]";
      }

      @Override
      public int compareTo(PointWithDistance o) {
          return Long.compare(o.distance, this.distance);
      }
  }
```

```java
  public String longestPalindrome(String s) {
      int len = s.length();
      while (len >= 0) {
          for (int i = 0; i + len - 1 < s.length(); i++) {
              int left = i;
              int right = i + len - 1;
              boolean good = true;
              while (left < right) {
                  if (s.charAt(left) != s.charAt(right)) {
                      good = false;
                      break;
                  }
                  left++;
                  right--;
              }
              if (good) {
                  return s.substring(i, i + len);
              }
          }
          --len;
      }
      return "";
  }
```

```java

    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/
    /*

    */ 
    public int findKthLargest(int[] nums, int k) {
        int start = 0, end = nums.length - 1, index = nums.length - k;
        while (start < end) {
            int pivot = partition(nums, start, end);
            if (pivot < index) {
                start = pivot + 1;
            } else if (pivot > index) {
                end = pivot - 1;
            } else {
                return nums[pivot];
            }
        }
        // 
        return nums[start];

    }

    // left | pivot | right
    // nums in the left  < nums[pivot]
    // nums in the right > nums[pivot]


    private int partition(int[] nums, int start, int end) {
        int pivot = start;
        while (start <= end) {
            while (start <= end && nums[start] <= nums[pivot]) {
                start++;
            }
            while (start <= end && nums[end] > nums[pivot]) {
                end--;
            }
            if (start > end) {
                break;
            }
            swap(nums, start, end);
        }
        // at the end !!!!!!!!!!!!!!
        // nums[start] > nums[pivot]
        // nums[end] <= nums[pivot]
        // so swap end and pivot
        swap(nums, end, pivot);
        return end;
    }
```

```java
  public int findLargestSumIn3NonOverlappingSubarrays(int[] nums, int k) {
      int n = nums.length;
      if (n < 3 * k) {
          return 0;
      }
      int[] presum = getPresum(nums, k);
      int[] maxFromLeft = getMaxFromLeft(nums, k, presum);
      int[] maxFromRight = getMaxFromRight(nums, k, presum);
      int result = Integer.MIN_VALUE;
      for (int i = k; i <= n - 2 * k; i++) {
          result = Math.max(result, maxFromLeft[i - k] + presum[i] + maxFromRight[i + k]);
      }

      return result;
  }

  private int[] getMaxFromLeft(int[] nums, int k, int[] presum) {
      int n = nums.length;
      int[] maxFromLeft = new int[n - 2 * k + 1];
      maxFromLeft[0] = presum[0];
      int max = presum[0];
      for (int i = 1; i <= n - 2 * k; i++) {
          max = Math.max(max, presum[i]);
          maxFromLeft[i] = max;
      }
      return maxFromLeft;
  }

  private int[] getMaxFromRight(int[] nums, int k, int[] presum) {
      int n = nums.length;
      int[] maxFromRight = new int[n - k + 1];
      int max = presum[n - k];
      maxFromRight[n - k] = max;
      for (int i = n - k - 1; i >= 2 * k; i--) {
          max = Math.max(max, presum[i]);
          maxFromRight[i] = max;
      }
      return maxFromRight;
  }
```
