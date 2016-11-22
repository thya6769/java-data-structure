import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PasswordManagerTest {

	
	@Test
	public void printHashCollisions() throws FileNotFoundException, IOException {
		DoubleHashMap<Long, List<String>> map = new DoubleHashMap<Long, List<String>>(4000, 50000, 1,
				56897);
		PasswordManager pm = new PasswordManager();
		BufferedReader br = new BufferedReader(new FileReader("datasetB.txt"));
		try {
			String line = br.readLine();
			while (line != null) {
				String password = line.trim();
                Long passwordHash = pm.hash(password); // hash the password to Long value
                // if passwordHash is in it, add password to its list value
                if(map.keys().contains(passwordHash)){
                	map.get(passwordHash).add(password);
                }
                // else, instantiate a new ArrayList and add password to it
                else{
                	List<String> list = new ArrayList<String>();
                	list.add(password);
                	map.put(passwordHash, list);
                	System.out.println(map.get(passwordHash).toString());
                }
                line = br.readLine();
			}
		} catch(RuntimeException e) {
	        if(!e.getMessage().equals("Double Hashing failed to find a free position")) {
	        	throw e; 
	        }
		}
	     finally {
			br.close();
		}
		// get the keys in the map
		List<Long> hashes = map.keys();
		int collisions = 0;
		for (Long hash : hashes){
			List<String> passwords = map.get(hash);
			if (passwords.size() > 1){
				// all passwords in this list have the same hash representation
				collisions++;
			} 
		}
		System.out.println(collisions);
	}
	@Test
	public void test(){
		PasswordManager pm = new PasswordManager();
		pm.addNewUser("john", "abc");
		assertEquals(1, pm.numberUsers());
		assertEquals(Arrays.asList("john"), pm.listUsers());
		pm.deleteUser("john", "abc");
		assertEquals(0, pm.numberUsers());
		pm.addNewUser("takashi1", "hi");
		pm.addNewUser("takashi2", "hihi");
		pm.resetPassword("takashi1", "hi", "fukuchan");
		User takashi1 = pm.getUser("takashi1");
		//assertEquals("fukuchan", takashi1.getPassword("pwdMan"));
	}
}

