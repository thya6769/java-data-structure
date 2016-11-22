import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SkipList<K extends Comparable<K>, V> {
	private SkipListNode<K,V> head;
	private SkipListNode<K,V> tail;
	
	public Random random;
	private int level;
	private int noOfEntries;
	public final K MINUS_INF = null;
	public final K PLUS_INF = null;
	public int searchSteps = 0;
	// construction
	public SkipList(){
		SkipListNode<K,V> negInf = new SkipListNode<K,V>(MINUS_INF,null);
		SkipListNode<K,V> posInf = new SkipListNode<K,V>(PLUS_INF,null);
		
		negInf.setNext(posInf);
		posInf.setPrev(negInf);
		
		head = negInf;
		tail = posInf;
		noOfEntries = 0;
		level = 0;
		random = new Random();
	}
	
	// size
	public int size(){
		return noOfEntries;
	}

	public boolean isEmpty(){
		if(size() == 0){
			return true;
		}
		return false;
	}
	// interface methods
	public List<K> keys(){
		List<K> list = new ArrayList<K>();
		SkipListNode<K,V> node = head;
		while(node.getBelow() != null){
			node = node.getBelow();
		}
		while(node.getNext() != PLUS_INF){
			node = node.getNext();
			if(node.getKey() != null){
				list.add(node.getKey());
			}
		}
		return list;
	}
	/*
	 * Return the SkipListNode with the closest key to that given, 
	 * without exceeding it. if the skip is empty, return the head of 
	 * the lowermost layer of linked lists.
	 */
	public SkipListNode<K, V> search(K key){
		SkipListNode<K,V> node = head;
		searchSteps = 0;
		while(true){
			while(node.getNext().getKey() != PLUS_INF && node.getNext().getKey().compareTo(key) <= 0){
				searchSteps++;
				node = node.getNext();
			}
			// go down one level if you can
			if(node.getBelow() != null){
				node = node.getBelow();
			}
			else{
				break;
			}
		}
		return node;
	}
	public V put(K key, V value){
		SkipListNode<K,V> nodeP = search(key);
		// if key is found update the value 
		if(key.equals(nodeP.getKey())){
			V oldValue = nodeP.getValue();
			nodeP.setValue(value);
			return oldValue;
		}
		// key is not found
		SkipListNode<K,V> nodeQ = new SkipListNode<K,V>(key, value);
		nodeQ.setPrev(nodeP);
		nodeQ.setNext(nodeP.getNext());
		nodeP.getNext().setPrev(nodeQ);
		nodeP.setNext(nodeQ);
		int index = 0;
		while(random.nextDouble() < 0.5){ // coin toss
			index++;
			// if we reach the top level
			if(index >= level){
				expandLayer();
			}
			// find first element with an Above link
			while(nodeP.getAbove() == null){
				nodeP = nodeP.getPrev();
			}
			
			// make nodeP point to this Above element
			nodeP = nodeP.getAbove();
			// add one more (k, V) to the column
			SkipListNode<K,V> nodeE = new SkipListNode<K,V>(key, null);
			nodeE.setPrev(nodeP);
			nodeE.setNext(nodeP.getNext());
			nodeE.setBelow(nodeQ);
			
			//change the neighouring linkage
			nodeP.getNext().setPrev(nodeE);
			nodeP.setNext(nodeE);
			nodeQ.setAbove(nodeE);
			
			nodeQ = nodeE; // set q up for next iteration
			
			index++;
		}
		noOfEntries++;
		return null; //no old value
		
 	}
	private void expandLayer(){
		SkipListNode<K,V> p1 = new SkipListNode<K,V>(MINUS_INF, null);
		SkipListNode<K,V> p2 = new SkipListNode<K,V>(PLUS_INF, null);
		
		p1.setNext(p2);
		p1.setBelow(head);
		p2.setNext(p1);
		p2.setBelow(tail);
		
		head.setAbove(p1);
		tail.setAbove(p2);
		
		head = p1;
		tail = p2;
		
		level++; 
	}
	private void compressLayer(){
		SkipListNode<K,V> p1 = new SkipListNode<K,V>(null, null);
		SkipListNode<K,V> p2 = new SkipListNode<K,V>(null, null);
		
		p1.setNext(p2);
		p1.setBelow(head);
		p2.setNext(p1);
		p2.setBelow(tail);
		
		head.setAbove(p1);
		tail.setAbove(p2);
		
		head = p1;
		tail = p2;
		
		level++; 
	}

	public V get(K key){
		SkipListNode<K,V> node = search(key);
		if(key.equals(node.getKey())){
			return node.getValue();
		}
		else{
			return null;
		}
	}

	public V remove(K key){
		SkipListNode<K,V> node = search(key);
		// not found
		if(node.getKey() != key){
			return null;
		}
		V oldValue = node.getValue();
		// we are at level 0
		// travel up the tower and link the left and right neighbors
		while(node != null){
			node.getPrev().setNext(node.getNext());
			node.getNext().setPrev(node.getPrev());
			node = node.getAbove();
		}
		noOfEntries--;
		return oldValue;
	}
}