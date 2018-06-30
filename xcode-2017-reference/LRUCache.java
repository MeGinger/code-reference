import java.util.*;

// Another implementation version of LRU is LossyDictionary
// Such as Dictionary<T, WeakReference> http://msdn.microsoft.com/en-us/library/system.weakreference%28v=vs.110%29.aspx
class LRUCacheItem {
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
	private Map<Integer, LRUCacheItem> entries;
	private LRUCacheItem head;
	private LRUCacheItem tail;

	public LRUCache(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		this.entries = new HashMap<>(capacity);
		// dummy nodes
		this.head = new LRUCacheItem(0, 0);
		this.tail = new LRUCacheItem(0, 0);
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
				this.entries.remove(end.key);
				endPrev.next = this.tail;
				this.tail.prev = endPrev;
				end.prev = end.next = null;
			}
			return item;
		});

		entry.val = value;
		moveToHead(entry);
	}

	private void moveToHead(LRUCacheItem entry) {
		// begin is the actual first LRUCacheItem, which is after dummy head
		LRUCacheItem begin = this.head.next;
		if (entry == begin || entry == null) {
			return;
		}
		LRUCacheItem next = entry.next;
		LRUCacheItem previous = entry.prev;

		// remove entry between next and previous
		if (next != null) {
			next.prev = previous;
		}
		if (previous != null) {
			previous.next = next;
		}

		// Add entry between dummy head and begin
		begin.prev = entry;
		this.head.next = entry;

		// adjust entry
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