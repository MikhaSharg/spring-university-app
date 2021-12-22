import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Search {

	public static void main(String[] args) throws InterruptedException {
		
		List<Integer> list = new ArrayList<>();
		
		for (int i =0; i<100000000; i++) {
			
			list.add(i);
			
		}
		Long  startTime = System.nanoTime();
		
//		System.out.println(list.indexOf( 100000000-1));
		System.out.println(Collections.binarySearch(list, 100000000-1));
		
		System.out.println(System.nanoTime()-startTime);
		
		
	}
	
	
}
