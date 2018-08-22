package xcode_class;

public class valid_parentheses {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public boolean checkValidString(String s){
		int low = 0, high = 0;
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			switch(c) {
			case '(':
				low++;
				high++;
				break;
			case ')':
				if (low > 0) {
					low--;
				}
				high--;
				break;
			case '*':
				if(low > 0) {
					low--;
				}
				high++;
			}
			default: 
				throw new IllegalArgumentException();
			if(high < 0) {
				return false;
			}
		}
		
		return low == 0;
	}

}
