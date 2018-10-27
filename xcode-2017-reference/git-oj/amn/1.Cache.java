import java.util.*;

// Another implementation version of LRU is LossyDictionary
// Such as Dictionary<T, WeakReference> http://msdn.microsoft.com/en-us/library/system.weakreference%28v=vs.110%29.aspx
class LRUCacheItem { // item in doubly-linked-list
	int val;
	int key; 
	LRUCacheItem prev;
	LRUCacheItem next;

	public LRUCacheItem(int key, int val) {
		this.key = key;
		this.val = val;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.key);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}

		LRUCacheItem other = (LRUCacheItem) o;
		return Objects.equals(this.key, other.key);
	}
}

// Design and implement a data structure for Least Recently Used (LRU) cache. It
// should support the following operations: get and set.
// get(key) - Get the value (will always be positive) of the key if the key
// exists in the cache, otherwise return -1.
// set(key, value) - Set or insert the value if the key is not already present.
// When the cache reached its capacity, it should invalidate the least recently
// used item before inserting a new item.
public class LRUCache {
	private final int capacity;
	private Map<Integer, LRUCacheItem> entries; // mapping: key to Item
	private LRUCacheItem head; // just-used
	private LRUCacheItem tail; // least-recently-used

	public LRUCache(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		this.entries = new HashMap<>(capacity);
		// dummy nodes
		this.head = new LRUCacheItem(0, 0); // dummy head
		this.tail = new LRUCacheItem(0, 0); // dummy tail
		this.head.next = this.tail;
		this.tail.prev = this.head;
	}

	public int get(int key) {
		LRUCacheItem entry = this.entries.get(key);
		if (entry == null) {
			return -1;
		}
		moveToHead(entry);
		return entry.val;
	}

	public void put(int key, int value) {
		// System.out.println(key + "," + value);
		LRUCacheItem entry = this.entries.computeIfAbsent(key, k -> {
			LRUCacheItem item = new LRUCacheItem(key, value);
			if (this.entries.size() == this.capacity) {
				// end is the actual last node before dummy tail
				LRUCacheItem end = this.tail.prev;
				LRUCacheItem endPrev = end.prev;
				
				this.entries.remove(end.key); // remove in hashmap
				
				endPrev.next = this.tail; // remove in doubly-linked-list
				this.tail.prev = endPrev;

				end.prev = end.next = null; // eligible for garbage collection
			}
			return item;
		});

		entry.val = value;
		moveToHead(entry);
	}

	private void moveToHead(LRUCacheItem entry) {
		// begin is the actual first LRUCacheItem, which is after dummy head
		LRUCacheItem begin = this.head.next;
		// entry is already at the first position
		if (entry == begin) { // || entry == null might be unnecessary
			return;
		}
		LRUCacheItem next = entry.next;
		LRUCacheItem previous = entry.prev;

		// remove entry from the list if it is in list
		if (next != null) {
			next.prev = previous;
		}
		if (previous != null) {
			previous.next = next;
		}

		// Add entry 
		begin.prev = entry;
		this.head.next = entry;

		entry.prev = this.head;
		entry.next = begin;
	}

	public static void main(String[] args) {
		LRUCache lru = new LRUCache(2);
		lru.put(1, 1);
//		lru.put(2, 2);
//		lru.get(1);
	}

}





import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * LFU Cache
Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LFUCache cache = new LFUCache(2);

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.get(3);       // returns 3.
cache.put(4, 4);    // evicts key 1.
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4
 */
public class LFUCache {
	private final int capacity;
	// maintain a doubly linked list of Buckets
	private Bucket head; // dummy smallest bucket
	private Bucket tail; // dummy biggest bucket
	// Key is the key, value is its corresponding bucket
	private Map<Integer, Bucket> buckets;
	// key values
	private Map<Integer, Integer> values;

	// each Bucket contains all the keys with the same count
	private class Bucket {
		int count;
		LinkedHashSet<Integer> keySet;
		Bucket next;
		Bucket pre;

		public Bucket(int cnt) {
			this.count = cnt;
			this.keySet = new LinkedHashSet<>();
		}

		@Override
		public String toString() {
			return "[count = " + count + ", keys = " + keySet + ", pre = " + (pre == null ? "null" : pre.count)
					+ ", next = " + (next == null ? "null" : next.count) + "]";
		}
	}

	public LFUCache(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}

		this.capacity = capacity;
		this.head = new Bucket(Integer.MIN_VALUE);
		this.tail = new Bucket(Integer.MAX_VALUE);
		// head -> head.next -> ... -> tail.pre -> tail
		// MIN_VALUE -> smallest -> ... biggest -> MAX_VALUE
		this.head.next = this.tail;
		this.tail.pre = this.head;
		this.buckets = new HashMap<>();
		this.values = new HashMap<>();
	}

	public int get(int key) {
		Integer val = this.values.get(key);
		if (val == null) {
			return -1;
		}
		this.inc(key);
		return val;
	}

	public void put(int key, int value) {
		// only for create a new entry && size's full
		if (!this.values.containsKey(key) && this.values.size() >= this.capacity) {
			// kick out least frequently used key
			Bucket smallestBucket = this.head.next;
			int k = smallestBucket.keySet.iterator().next();
			removeKeyFromBucket(smallestBucket, k);
			this.buckets.remove(k);
			this.values.remove(k);
		}
		this.values.put(key, value);
		this.inc(key);
	}

	/**
	 * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
	 */
	private void inc(int key) {
		Bucket bucket = this.buckets.get(key);
		if (bucket != null) {
			changeKey(key, bucket);
			return;
		}

		// bucket == null
		if (this.head.next.count == 1) {
			bucket = this.head.next;
		} else {
			// head -> bucket (head.next)
			bucket = new Bucket(1);
			addBucketAfter(bucket, this.head);
		}
		this.buckets.put(key, bucket);
		bucket.keySet.add(key);
	}

	private void changeKey(int key, Bucket curBucket) {
		Bucket newBucket = curBucket.next;
		if (newBucket.count != curBucket.count + 1) {
			// target Bucket does not exist, add new Bucket
			// curBucket.pre -> curBucket -> newBucket
			newBucket = addBucketAfter(new Bucket(curBucket.count + 1), curBucket);
		}
		newBucket.keySet.add(key);
		this.buckets.put(key, newBucket);
		removeKeyFromBucket(curBucket, key);
	}

	private void removeKeyFromBucket(Bucket bucket, int key) {
		bucket.keySet.remove(key);
		if (bucket.keySet.isEmpty()) {
			removeBucketFromList(bucket);
		}
	}

	private void removeBucketFromList(Bucket bucket) {
		Bucket pre = bucket.pre;
		Bucket next = bucket.next;
		pre.next = next;
		next.pre = pre;
		bucket.next = null;
		bucket.pre = null;
	}

	// add newBucket after preBucket
	private Bucket addBucketAfter(Bucket newBucket, Bucket preBucket) {
		newBucket.pre = preBucket;
		newBucket.next = preBucket.next;
		preBucket.next.pre = newBucket;
		preBucket.next = newBucket;
		return newBucket;
	}
}