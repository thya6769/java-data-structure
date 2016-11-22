import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import interfaces.Position;
import simpletree.SimplePosition;

public class MyTreeTest {

	/*
	 * These tests have timeouts set to 1000 milliseconds (1 second)
	 * While using the debugger you will need to comment out the
	 * corresponding timeout
	 */
	
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testTraversal(){
    	MyTree<String> tree = new MyTree<String>();
        Position<String> a = new SimplePosition<String>("a");
        tree.setRoot(a);
        Position<String> b = new SimplePosition<String>("b");
        Position<String> c = new SimplePosition<String>("c");
        Position<String> d = new SimplePosition<String>("d");
        Position<String> e = new SimplePosition<String>("e");

        tree.insert(a, b);
        tree.insert(a, c);
        tree.insert(c, d);
        tree.insert(c, e);
        assertEquals(Arrays.asList("a","b","c","d","e"), tree.preOrder());
        assertEquals(Arrays.asList("b","c","d","e","a"), tree.postOrder());
        assertEquals(Arrays.asList("b","a","d","c","e"), tree.inOrder());
    }
    @Test
    public void testHeight(){
    	MyTree<String> tree = new MyTree<String>();
        Position<String> a = new SimplePosition<String>("a");
        tree.setRoot(a);
        Position<String> b = new SimplePosition<String>("b");
        Position<String> c = new SimplePosition<String>("c");
        Position<String> d = new SimplePosition<String>("d");
        Position<String> e = new SimplePosition<String>("e");

        tree.insert(a, b);
        tree.insert(a, c);
        tree.insert(c, d);
        tree.insert(c, e);
        assertEquals(2, tree.height());
        assertEquals(1, tree.height(1));
    }
    @Test
    public void testNumLeaves(){
    	MyTree<String> tree = new MyTree<String>();
        Position<String> a = new SimplePosition<String>("a");
        tree.setRoot(a);
        Position<String> b = new SimplePosition<String>("b");
        Position<String> c = new SimplePosition<String>("c");
        Position<String> d = new SimplePosition<String>("d");
        Position<String> e = new SimplePosition<String>("e");

        tree.insert(a, b);
        tree.insert(a, c);
        tree.insert(c, d);
        tree.insert(c, e);
        assertEquals(3, tree.numLeaves());
        assertEquals(2, tree.numLeaves(2));
    }
    @Test
    public void testPositions(){
    	MyTree<String> tree = new MyTree<String>();
        Position<String> a = new SimplePosition<String>("a");
        tree.setRoot(a);
        Position<String> b = new SimplePosition<String>("b");
        Position<String> c = new SimplePosition<String>("c");
        Position<String> d = new SimplePosition<String>("d");
        Position<String> e = new SimplePosition<String>("e");

        tree.insert(a, b);
        tree.insert(a, c);
        tree.insert(c, d);
        tree.insert(c, e);
        assertEquals(2, tree.numPositions(2));
        assertEquals(2, tree.numPositions(1));
    }
    @Test
    public void testBinary(){
    	MyTree<String> tree = new MyTree<String>();
        Position<String> a = new SimplePosition<String>("a");
        tree.setRoot(a);
        Position<String> b = new SimplePosition<String>("b");
        Position<String> c = new SimplePosition<String>("c");
        Position<String> d = new SimplePosition<String>("d");
        Position<String> e = new SimplePosition<String>("e");

        tree.insert(a, b);
        tree.insert(a, c);
        tree.insert(c, d);
        tree.insert(c, e);
        assertTrue(tree.isBinary());
        assertTrue(tree.isProperBinary());
        assertFalse(tree.isComplete());
    }
    @Test
    public void testHeap(){
    	MyTree<Integer> tree = new MyTree<Integer>();
    	Position<Integer> a = new SimplePosition<Integer>(1);
    	Position<Integer> b = new SimplePosition<Integer>(2);
    	Position<Integer> c = new SimplePosition<Integer>(3);
    	Position<Integer> d = new SimplePosition<Integer>(4);
    	Position<Integer> e = new SimplePosition<Integer>(5);
    	tree.setRoot(a);
    	tree.insert(a, b);
    	tree.insert(a, c);
    	tree.insert(b, d);
    	tree.insert(b, e);
    	assertTrue(tree.isHeap(false));
    }
    
}