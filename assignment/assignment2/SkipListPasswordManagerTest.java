import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SkipListPasswordManagerTest {

	
	@Test
	public void printHashCollisions() throws FileNotFoundException, IOException {
		SkipListPasswordManager pm = new SkipListPasswordManager();
		BufferedReader br = new BufferedReader(new FileReader("datasetC.txt"));
		try {
			String line = br.readLine();
			while (line != null) {
				String username = line.trim();
				pm.addNewUser(username, "pwdMan");
                line = br.readLine();
			}
		}
	     finally {
	    	 System.out.println("number of users "+ pm.numberUsers());
	    	 System.out.println("Smith " + pm.searchSteps("SMITH"));
	    	 System.out.println("Johnson "+ pm.searchSteps("JOHNSON"));
	    	 System.out.println("Williams "+ pm.searchSteps("WILLIAMS"));
	    	 System.out.println("Brown "+ pm.searchSteps("BROWN"));
	    	 System.out.println("Jones "+ pm.searchSteps("JONES"));
	    	 System.out.println("Miller "+ pm.searchSteps("MILLER"));
	    	 System.out.println("Davis "+ pm.searchSteps("DAVIS"));
			br.close();
		}

	}
	@Test
	public void test(){
		SkipListPasswordManager pm = new SkipListPasswordManager();
		pm.addNewUser("john", "abc");
		assertEquals(1, pm.numberUsers());
		assertEquals("helen", pm.addNewUser("helen", "yolo"));
		assertEquals(Arrays.asList("helen","john"), pm.listUsers());
		pm.deleteUser("john", "abc");
		assertEquals(1, pm.numberUsers());
		pm.addNewUser("takashi1", "hi");
		pm.addNewUser("takashi2", "hihi");
		System.out.println(pm.searchSteps("john"));
		System.out.println(pm.searchSteps("takashi2"));
	}

}
