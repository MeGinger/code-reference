HashSet is Implemented using a hash table. Elements are not ordered. The add, remove, and contains methods have constant time complexity O(1).

TreeSet is implemented using a tree structure(red-black tree in algorithm book). The elements in a set are sorted, but the add, remove, and contains methods has time complexity of O(log (n)). It offers several methods to deal with the ordered set like first(), last(), headSet(), tailSet(), etc.

LinkedHashSet is between HashSet and TreeSet. It is implemented as a hash table with a linked list running through it, so it provides the order of insertion. The time complexity of basic methods is O(1).


### Terminology

* Binary tree
  * successor
  * predecessor

* Collections
  * Queue/PriorityQueue: poll, offer, peek
  * Stack: pop, push, peek

### Frequently Made Mistakes

* initialize all reference variables to null
```java
ListNode cur = null, pre = null;
for (...) { 
    ...
}
```
* initialize array of list
```java
RIGHT: List<Character> [] bucket = new List[s.length() + 1]; 
WRONG: List<Character> [] bucket = new List<Character>[s.length() + 1];
```

* add parenthesis to any bit operator
```java
if (...&& ((a & b) == 1)) {
   ...
}
```

* string.trim()
```java
  System.out.println("   Welcome to Tutorialspoint.com   ".trim()); 
  // OUTPUT: |Welcome to Tutorialspoint.com|
```
* misspelled Java APIs
```java
stack/priorityqueue.peek() // NOT peak..
```

* empty array initialization 

```java
RIGHT: return new int[]{};
RIGHT: return new int[0];

WRONG: return new int[];
```

* Lambda expression - **important**

```java
RIGHT: PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length, 
            (o1, o2) -> o1.val - o2.val); // min-heap
// OR (o1, o2) -> (o1.val - o2.val)
RIGHT: PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length, 
            (o1, o2) -> {return o1.val - o2.val;}); // min-heap

WRONG: PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length, 
            (o1, o2) -> return o1.val - o2.val); // min-heap
```

```java
Map<Integer, List<Integer>> map = new HashMap<>();
RIGHT: List<Integer> list = map.computeIfAbsent(col, c -> new ArrayList<>());

WRONG: List<Integer> list = map.computeIfAbsent(col, c - > new ArrayList<>()); // IMPORTANT
WRONG: List<Integer> list = map.computeIfAbsent(col, new ArrayList<>());

```

* avoid collection empty exception before doing operations..

```java
check with stack.isEmpty() before stack.pop()
```

* SWITCH-CASE STATEMENTS 

```java
    switch(c) {
	case '(': stack.push(')'); break;
	case '[': stack.push(']'); break;
	case '{': stack.push('}'); break;
	case ')':
	case ']':
	case '}': if (stack.isEmpty() || stack.pop() != c) {return false;}
    }
```

* Coding Habits
  * Dont forget **constructor** for self-defined data structure, like TrieNode, Trie, RandomizedCollection
  * Dont forget to adjust code, when you undo your thought/idea/approach, to avoid compilation errors

void Collections.reverse(list) 



### Java APIs

int a = Integer.parseInt("123");

Integer a = Integer.valueOf("123");



boolean set.add(E)

* if return true, set does not contain E and E is added successfully
* otherwise return false


static <T extends Comparable<? super T>> void sort(List<T> list)
Sorts the specified list into ascending order, according to the natural ordering of its elements.

static <T> void	sort(List<T> list, Comparator<? super T> c)
Sorts the specified list according to the order induced by the specified comparator.

Sort collection (may come from values from map), set?
Collections.sort(new ArrayList<>(collection))
Collections.sort(new ArrayList<>(set))


int[] odd ...
Arrays.toString(odd)


Integer.toBinaryString(int) -> String // easy to debug
* 170 -> "10101010" NOT "00...0010101010"

Integer.bitCount(int) -> int 
* 170 -> 4

Collections.emptyList() - Returns the empty list (immutable). <br/>
Collections.emptyMap() - Returns the empty map (immutable). <br/>
Collections.emptySet() - Returns the empty set (immutable). <br/>

```java
public List<List<String>> method(...) {
   if (invalidInputCondition) {
	return Collections.emptyList(); // more readable
   }
   ...
}
```



Collections.emptyList() returns an immutable list, i.e., a list to which you cannot add elements.
In the rare cases in which you do want to modify the returned list, this would thus not be an option.



```java
Arrays.binarySearch(array, key);
Collections.binarySearch(collection, key);
```

// Returns index of key in sorted list sorted in ascending order <br/>
public static int binarySearch(List slist, T key) <br/>

// Returns index of key in sorted list sorted in order defined by Comparator c. <br/>
public static int binarySearch(List slist, T key, Comparator c) </br>

If key is not present, the it returns "(-(insertion point) - 1)". <br/>
The insertion point is defined as the point at which the key <br/>
would be inserted into the list. <br/>

Character.isWhitespace(char ch) - space, tab, or new line <br/> 
* Character.isWhitespace(' ') -> true
* Character.isWhitespace('\t') -> true
* Character.isWhitespace('\n') -> true

Character.isDigit(char ch) - '0' ~ '9' <br/>
Character.isLetter(char ch) - 'a' ~ 'z', 'A' ~ 'Z' <br/>
Character.isLetterOrDigit(char ch) - **alphanumeric**



Character.isUpperCase(char ch) - 'A' ~ 'Z' <br/>
Character.isLowCase(char ch) - 'a' ~ 'z' <br/>
Character.toLowerCase(char ch) <br/>
Character.toUpperCase(char ch) <br/>

List<E>	list.subList(int fromIndex, int toIndex) <br/>
* Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive. <br/>

E list.set(int index, E element) <br/>
* Replaces the element at the specified position in this list with the specified element (optional operation). <br/>

void list.add(int index, E element) <br/>
* Inserts the specified element at the specified position in this list (optional operation). <br/>


Collections


Collection -> ArrayList <br/>
ArrayList(Collection<? extends E> c) <br/>
e.g., new ArrayList<>(map.values()); <br/>

Clone an integer array <br/>
1.
int[] clone = new int[arr.length];
Arrays.fill(clone, 3);
2. 
int clone = arr.clone();

Comparator - Lambda

## Sorting 
### Array
Arrays.sort(arr); // ascending order  <br/>
Arrays.sort(arr, (e1, e2) -> {return e1 - e2;}); // ascending order   <br/>
Arrays.sort(arr, Collections.reverseOrder()); // descending order   <br/>
Arrays.sort(arr, (e1, e2) -> {return e2 - e1;}); // descending order   <br/>
#### examples:
Arrays.sort(word);  <br/>
output: word -> e, ew, ewq, ewqc, c, ca, cat, t, tt, ttq

### Collection
Collections.sort(list); // ascending order   <br/>
Collections.sort(list, Collections.reverseOrder()); // descending order   <br/>

### PriorityQueue
PriorityQueue<Map.Entry<Integer, Integer>> maxHeap =
  new PriorityQueue<>((a, b) -> (b.getValue() - a.getValue()));

## Collection CRUD API
### PriorityQueue
Add - pq.offer(object); <br/>
Read - Object obj = pq.peek(); <br/>
Remove - Object obj = pq.poll(); pq.remove(object); <br/>

ELSE  - common in Collections  <br/>
pq.clear();  <br/>
pq.contains();  <br/>
pq.size();  <br/>
pq.toArray();  <br/>

### List
Add - list.add(pos, elem); // add elem into position pos, the elements behind it should shift to right   <br/>


## Conversion between Array and Collection
### Conversion between Array and List
#### convert from Array to List
List<Character> list = Arrays.asList(‘A’, ‘B’, ‘C’);  <br/>
List<Integer> list = Arrays.asList(new int[]{1, 2, 3});  <br/>
List<String> list = Arrays.asList(arr);  <br/>

#### convert from List to Array
String[] arr = list.toArray(new String[list.size()]);  <br/>

String[] arr = list.toArray(new String[0]);  <br/>

```java
List<Integer> list = new ArraryList<>();
......
int[] array = list.stream().mapToInt(i->i).toArray();
```

#### convert from List to Set
Set<T> set = new HashSet<>(collection); // collection like list   <br/>

```java
	private static final int[][] dir = new int[][]{ 
							  {0, -1},
							  {0, 1},
							  {1, 0},
							  {-1, 0}
						   };
	int[] myIntArray = new int[3];
	int[] myIntArray = {1,2,3};
	int[] myIntArray = new int[]{1,2,3};

	String[] myStringArray = new String[3];
	String[] myStringArray = {"a","b","c"};
	String[] myStringArray = new String[]{"a","b","c"};
						   
	/*  System.arraycopy(src, srcPos, dest, destPos, length);
		src − This is the source array.
		srcPos − This is the starting position in the source array.
		dest − This is the destination array.
		destPos − This is the starting position in the destination data.
		length − This is the number of array elements to be copied.
	*/

	// Arrays.toString(arr)
	// Collection (list, set) 可以直接打 .toString()

	/*  Arrays.copyOfRange(original, from, to);
		original − This is the array from which a range is to to be copied.
		from − This is the initial index of the range to be copied, inclusive.
		to − This is the final index of the range to be copied, exclusive.
	*/						   

```

Exception Handling
```java
if (invalidInputCondition) {
   throw new IllegalArgumentException();
}
```

Math.floorDiv(a, b) - returns largest integer that less than or equal to the quotient <br/> 
* Math.floorDiv(25, 5) = 5 <br/>
* Math.floorDiv(123, 50) = 2 <br/>
* public static int floorDiv(data_type x, data_type y), data_type could be int or long

Math.floorMod(a, b) - returns (a - floorDiv(a, b) * b) <br/>
* Math.floorMod(123, 50) - 23

int e = 123, f = -50;
System.out.println(Math.floorMod(e, f));
//-27

int g = -123, h = 50;
System.out.println(Math.floorMod(g, h));
//27





```java
import java.util.StringTokenizer; // from Java7
import java.lang.StringBuilder;

// constructor takes two parameters
StringTokenizer st = new StringTokenizer(data, SEP); 
st.nextToken(); -> String
st.hasMoreTokens(); -> boolean
```




deleteCharAt or setLength, which way is better to remove last char from a StringBuilder/StringBuffer
```java
sb.deleteCharAt(sb.length() - 1);
sb.setLength(sb.length() - 1);
```

Actually, there is very little in it and is probably dependent on hardware and other factors.

The setLength() method simply alters the count and overwrites the unwanted value in the array with a zero byte.

The deleteCharAt() performs an array copy internally, before altering the count. That sounds dramatic, but the array being copied is actually zero-length because you're removing the last character.

I would recommend going for setLength() as it is shorter to type and I think makes it clearer what you are doing. If performance is an issue and, on measuring, you find this is the bottleneck for you, then perhaps you could consider a different algorithm that doesn't require changing the size (as per JB Nizet's answer).
