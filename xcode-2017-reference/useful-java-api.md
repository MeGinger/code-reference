Integer.toBinaryString(int) -> String // easy to debug
* 170 -> "10101010" NOT "00...0010101010"

Integer.bitCount(int) -> int 
* 170 -> 4

Collections.emptyList() - Returns the empty list (immutable).

Collections.emptyMap() - Returns the empty map (immutable).

Collections.emptySet() - Returns the empty set (immutable).


public List<List<String>> method(...) {
	if (invalidInputCondition) {
		return Collections.emptyList(); // more readable
	}
  ......
}
  
Collections.emptyList() returns an immutable list, i.e., a list to which you cannot add elements.
In the rare cases in which you do want to modify the returned list, this would thus not be an option.
