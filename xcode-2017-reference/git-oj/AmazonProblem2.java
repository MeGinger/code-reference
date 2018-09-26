import java.util.*;
public class AmazonProblem2{
    // 日志排序

     public static void main(String[] args) {
		String[] a = {
				"a1 9 2 3 1",
				"g1 Act car",
				"zo4 4 7",
				"ab1 off KEY dog",
				"a8 act zoo"
				};
		String[] b = {
				"mi2 jog mid pet",
				"wz3 34 54 398",
				"a1 alps cow bar",
				"x4 45 21 7"
				};
		String[] c = {
				"t2 13 121 98",
				"r1 box ape bit",
				"b4 xi me nu",
				"br8 eat nim did",
				"w1 has uni gry",
				"f3 52 54 31"
				};
		answer(a);
		answer(b);
		answer(c);
		
     }	
     private static void answer(String[] a){
        List<String> logs = Arrays.asList(a);
        System.out.println("========="); 
		System.out.println("Input");
		System.out.println(""); 
		for(String it : logs){
		 	System.out.println(it);   
		}
		System.out.println(""); 
		System.out.println("Output"); 
		System.out.println(""); 
		for(String it : reorderLog(logs.size(),logs)){
		 	System.out.println(it);   
		}
	
     }
	
	/**
	 * reorder the logs.
	 * 
	 * */
	static public List<String> reorderLog(int logFileSize, List<String> logLines) {
	   if(logFileSize == 0){
	        return logLines;
	    }
	    Collections.sort(logLines, new Comparator<String>(){
	        public int compare(String s1, String s2){
	            String[] s1a = s1.split(" +");
	            String[] s2a = s2.split(" +");
	            char c1 = s1a[1].charAt(0);
	            char c2 = s2a[1].charAt(0);
	            if( Character.isDigit(c1) && Character.isDigit(c2)){
	                return 0;
	            }else if( !Character.isDigit(c1) && Character.isDigit(c2)){
	                return -1;
	            }else if( Character.isDigit(c1) && !Character.isDigit(c2)){
	                return 1 ;
	            }else{
	                int s1l = s1a.length;
	                int s2l = s2a.length;
	                int minLen = Math.min(s1l,s2l);
	                for( int i = 1 ; i < minLen ; i++){
	                    String line1 = s1a[i];
	                    String line2 = s2a[i];
	                    int lex = line1.compareTo(line2);
	                    if( lex != 0){
	                        return lex; 
	                    }
	                }
	                if(s1l == s2l){
	                    return  s1a[0].compareTo(s2a[0]);
	                }else{
	                    return s1l-s2l;
	                }
	            }
	            
	        }
	    });
	    return logLines;
	}
}
