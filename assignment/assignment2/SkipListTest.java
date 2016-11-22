import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class SkipListTest {

	@Test
	public void test() {
		SkipList<String, Integer> list = new SkipList<String, Integer>();
		assertTrue(list.isEmpty());
		list.put("a", 1);
		list.put("b", 2);
		list.put("c", 3);
		list.put("d", 4);
		assertEquals(4, list.size());
		assertEquals(Arrays.asList("a", "b","c", "d"), list.keys());
		list.remove("a");
		assertEquals(3, list.size());
		assertEquals(Arrays.asList("b","c","d"), list.keys());
	}

}
