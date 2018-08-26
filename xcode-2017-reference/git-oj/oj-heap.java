    /**
		Five Scores
		写好了一个叫Result的类，中文翻译成节点，题目是输入是一堆节点，节点里面有两个属性学生id和分数
		保证每个学生至少会有5个分数，求每个人最高的5个分数的平均分。返回的是Map, key是id，value是每个人最高5个分数的平均分double是平均分。
     */
    public static Map<Integer, Double> getHighFive(Result[] results) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>(); 
        // PriorityQueue: minHeap as default
        for (Result r : results) {
            PriorityQueue<Integer> q = map.computeIfAbsent(r.id, k -> new PriorityQueue<Integer>(5));
            if (q.size() < 5) {
                q.offer(r.value);
                continue;
            }
            
            if (q.peek() >= r.value) { // only compare with the min top node
                continue;
            }

            q.poll();
            q.offer(r.value);
        }

        Map<Integer, Double> res = new HashMap<>();
        for (Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            int id = entry.getKey();
            int sum = 0;
            PriorityQueue<Integer> q = entry.getValue();
            // 比每次调用poll() 时间性能得到优化，以上的课有note
            // for : O(k)
            // poll : O(klog(k)), 但preserve order
            for (int n : q) { 
                sum += n;
            }
            res.put(id, ((double) sum) / 5); // low cast to high - no precision issue
        }
        return res;
    }




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
        // 做这一步的原因是性能考虑：comparator.compare()调用时，都需要计算distance
        // 这个wrapper class，pre-compute distance，便于比较
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
    // priority queue
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

        /*
        Java8 - AMAZON OA 17年不支持java8
        Queue<Point> maxHeap = new PriorityQueue<>(k, (a, b) -> {
            long d1 = getDistance(a, target);
            long d2 = getDistance(b, target);
            return Long.compare(d2, d1);
        });        
        */
        for (Point point : points) {
            if (maxHeap.size() < k) {// put k points to heap
                maxHeap.offer(point);
                continue;
            }
            // Since we use pointComparator, compare(a, b) return Long.compare(d2, d1);
            // compare(maxHeap.peek(), point) < 0 -> maxHeap.peek() > point
            // normally, compare(a, b) < 0 -> a < b
            if (pointComparator.compare(maxHeap.peek(), point) < 0) {
                // if maxHeap's point's distance is far from target than point
                maxHeap.poll(); // remove root
                maxHeap.offer(point);
            }
        }
        
        Point[] result = new Point[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxHeap.poll();
        }

        /*
         可以直接遍历, 但不保证大小顺序
         for (Point p : maxHeap) {
            ... 
         }
        */ 
        return result;
    }

    // OPTIMAL SOLUTION - 
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



