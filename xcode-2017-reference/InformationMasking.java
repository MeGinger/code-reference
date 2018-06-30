package twitter;

public class InformationMasking {

	public String maskEmail(String email) {
		int index = email.indexOf('@');
		return email.charAt(0) + "*****" + email.charAt(index - 1) + email.substring(index);
	}

	public String maskPhone(String phone) {
		StringBuilder res = new StringBuilder();
		int count = 0;
		for (int i = phone.length() - 1; i >= 0; i--) {
			char c = phone.charAt(i);
			if (Character.isWhitespace(c) || c == '(' || c == ')' || c == '-') {
				continue;
			}
			if (Character.isDigit(c)) {
				count++;
				if (count < 5) {
					res.append(c);
				} else {
					if (count == 5 || count == 8 || count == 11) {
						res.append('-');
					}
					res.append('*');
				}
			} else {
				res.append(c);
			}
		}
		
		return res.reverse().toString();
	}

	public static void main(String[] args) {
		InformationMasking i = new InformationMasking();
		System.out.println(i.maskEmail("jackAndJill@gmail.com"));
		System.out.println(i.maskEmail("jackAndJill@twitter.com"));
		System.out.println(i.maskPhone("+1 (333) 444-5678"));
		System.out.println(i.maskPhone("+91 (333) 444-5678"));
		System.out.println(i.maskPhone("+111 (333) 444-5678"));
		System.out.println(i.maskPhone("333 444 5678"));
		System.out.println(i.maskPhone("(333) 444-5678"));
		System.out.println(i.maskPhone("3334445678"));
		System.out.println(i.maskPhone("+13334445678"));
		System.out.println(i.maskPhone("+1(333) 444-5678"));
	}
}
