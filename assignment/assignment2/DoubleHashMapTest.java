import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class DoubleHashMapTest {

	@Test
	public void testConstruction(){
		DoubleHashMap<String, Double> hashmap = new DoubleHashMap<String, Double>(2003, 1, 4271, 1);
		assertEquals(0, hashmap.size());
		assertTrue(hashmap.isEmpty());
		assertEquals(Arrays.asList(), hashmap.keys());
		assertEquals(Arrays.asList(), hashmap.values());
	}
	@Test
	public void testSize(){
		DoubleHashMap<String, Double> hashmap = new DoubleHashMap<String, Double>(2003, 1, 4271, 1);
		hashmap.put("a", 0.5);
		hashmap.put("b", 0.6);
		hashmap.put("c", 0.7);
		assertEquals(Arrays.asList("a", "b", "c"), hashmap.keys());
		assertEquals(3, hashmap.size());
		hashmap.remove("a");
		assertEquals(2, hashmap.size());
		hashmap.remove("b");
		assertEquals(1, hashmap.size());
		hashmap.remove("c");
		assertEquals(0, hashmap.size());
		assertTrue(hashmap.isEmpty());
	}
	@Test
	public void testSmallHashMap(){
		DoubleHashMap<String, Double> hashmap = new DoubleHashMap<String, Double>(2000, 1, 4271, 1);
		hashmap.put("a", 0.5);
		hashmap.put("b", 0.6);
		hashmap.put("c", 0.7);
		assertEquals((Double)0.5,hashmap.get("a"));
		assertEquals((Double)0.6,hashmap.get("b"));
		assertEquals((Double)0.7,hashmap.get("c"));

		assertEquals(3, hashmap.size());
		hashmap.remove("a");
		assertEquals(null, hashmap.get("a"));
		assertEquals((Double)0.6, hashmap.get("b"));
		assertEquals(2, hashmap.size());
		hashmap.remove("b");
		assertEquals(1, hashmap.size());
		hashmap.remove("c");
		assertEquals(0, hashmap.size());
		assertTrue(hashmap.isEmpty());
	}
	@Test
	public void test() throws FileNotFoundException, IOException{
		exploreData(2000, 1, 4271,1);
		exploreData(2000, 1, 4271,223);
		exploreData(2000, 1, 4271,647);
		exploreData(4000, 1, 4271,1);
		exploreData(4000, 1, 4271,223);
		exploreData(4000, 1, 4271,647);
		exploreData(2000, 53, 4271, 1);
		exploreData(6000, 1, 4271, 223);
		exploreData(4000, 1, 4271, 857);


	}
	
	 public void exploreData(int size, int multiplier, int modulus, int secModulus) throws FileNotFoundException, IOException {
		    // instantiate hash maps
			DoubleHashMap<String, Double> hashmap = new DoubleHashMap<String, Double>(size, multiplier, modulus, secModulus);
		    BufferedReader br = new BufferedReader(new FileReader("datasetA.txt"));
		    try {
		        String line = br.readLine();
		        while (line != null) {
		        	// stores the one line as a string array
		            String[] pieces = line.trim().split("\\s+");
		            if (pieces.length == 4){
		            	double piece1 = Double.parseDouble(pieces[1]);
		            	hashmap.put(pieces[0], piece1);
		            }
		            line = br.readLine();
		        }
		    } catch(RuntimeException e) {
		        if(!e.getMessage().equals("Double Hashing failed to find a free position")) {
		        	throw e; }
		    }
		    finally {
		    	System.out.println();
		    	System.out.print(hashmap.putCollisions());
		    	System.out.print(" " + hashmap.totalCollisions());
		    	System.out.print(" " + hashmap.maxCollisions());
		    	System.out.print(" " + hashmap.noPutFailures());

		        br.close();
		    }
	    }
}