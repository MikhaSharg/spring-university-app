
public class Counter {

	public int counterChars (String str) {
		
		int count = 0;
		
//		char [] l = str.toCharArray();
//		
//		for (int i =0; i<str.length()-1; i++ ) {
//			System.out.println(l[i]);
//			System.out.println(l[1]=='e');
//			if (l[i]=='a' || l[i]=='e' || l[i]=='i' || l[i]=='o' || l[i]=='u') {
//				count++;
				
		
		return str.replaceAll("[^aeiou]","").length();
	}
	
	public static void main(String[] args) {
		
		
		
		String [] letter = new String [10];
		letter [0] = "a";
		String str = "hello world";
		String [] l = str.split("");
		String test = new String ("e");
		System.out.println(letter[0]=="a"); 
		System.out.println(l[1]==test);
		
		System.out.println(l[1].equals(test));
	}
}
