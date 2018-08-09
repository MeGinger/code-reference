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
<br/>
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

