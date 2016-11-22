import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoubleHashMap<K extends Comparable<K>, V> {
	private HashMapNode<K,V>[] hashmap;
	private int multiplier;
	private int modulus;
	private int secondaryModulus;
	private int hashMapSize;
	private int noOfItems;
	private HashMapNode<K,V> defunct = new HashMapNode<K,V>(null,null);
	private int noPutCollisions = 0;
	private int totalCollisions = 0;
	private int maxCollisions = 0;
	private int noPutFailures = 0;
	// construct a DoubleHashMap with 4000 places and given hash parameters
    public DoubleHashMap(int multiplier, int modulus, int secondaryModulus){
    	hashmap = (HashMapNode<K,V>[]) new HashMapNode[4000];
    	this.hashMapSize = 4000;
    	this.multiplier = multiplier;
    	this.modulus = modulus;
    	this.secondaryModulus = secondaryModulus;
    	noOfItems = 0;
    }
    // construct a DoubleHashMap with given capacity and given hash parameters
    public DoubleHashMap(int hashMapSize, int multiplier, int modulus, 
    		int secondaryModulus){
    	this.hashMapSize = hashMapSize;
    	hashmap = (HashMapNode<K,V>[])new HashMapNode[hashMapSize];
    	this.multiplier = multiplier;
    	this.modulus = modulus;
    	this.secondaryModulus = secondaryModulus;
    	noOfItems = 0;
    }
    // hashing
    public int hash(K key){
    	return Math.abs(multiplier * key.hashCode() % modulus);
    }
    public int secondaryHash(K key){
    	return secondaryModulus - (Math.abs(key.hashCode()) % secondaryModulus);
    }
    // size (return the number of nodes currently stored in the map)
    public int size(){
    	return noOfItems;
    }
    // return is the hashmap is empty
    public boolean isEmpty(){
    	return (noOfItems == 0);
    }
    // interface methods
    // return the keys in hashmap
    public List<K> keys(){
    	List<K> list = new ArrayList<K>();
    	for(HashMapNode<K,V> entry : hashmap){
    		if(entry != null && entry != defunct){
    			list.add(entry.getKey());
    		}
    	}
    	return list;
    }
    // return the values is hashmap
    public List<V> values(){
    	List<V> list = new ArrayList<V>();
    	for(HashMapNode<K,V> entry : hashmap){
    		if(entry != null && entry != defunct){
    			list.add(entry.getValue());
    		}
    	}
    	return list;
    }
    // check if the spot is available
    private boolean isAvailable(int j){
    	return (hashmap[j] == null || hashmap[j] == defunct);
    }
    // return index at which the spot is available
    // taken from lecture notes
    private int getAvailableSpot(K key) {
		int index = -1;
		int hash = hash(key) % (hashmap.length);
		int j = 0;
		boolean firstTime = false;
		do {
			if (isAvailable(hash)) {
				if (index == -1){ 
					index = hash;
				}
				if (hashmap[hash] == null) {
					break; 
				}
			} else if (hashmap[hash].getKey().equals(key)) {
				return hash; 
			} 
			totalCollisions ++;
			if (!firstTime) {
				firstTime = true;
				noPutCollisions++;
			}
			j++;
			if (maxCollisions < j) {
				maxCollisions = j;
			}
			hash = (hash(key) + j * secondaryHash(key)) % (hashmap.length);
		} while (hash != hash(key) % (hashmap.length)); 
		if (index == -1) {
			noPutFailures++;
			throw new RuntimeException("Double Hashing failed to find a free position");
		}		
		return -(index + 1);
	}
    public V put(K key, V value) {
		int index = getAvailableSpot(key);
		V original;
		if (index >= 0) {
			original = hashmap[index].getValue();
		} else {
			index = -(index + 1);
			original = null;
			noOfItems += 1;
		}
		
		hashmap[index] = new HashMapNode<K, V>(key, value);
		return original;		
   
    }
    public V get(K key){
    	int index = getAvailableSpot(key);
    	if(index < 0){
    		return null;
    	}
    	return hashmap[index].getValue();
    	
    }
    public V remove(K key){
    	int index = getAvailableSpot(key);
    	// nothing to remove
    	if(index < 0){
    		return null;
    	}
    	V answer = hashmap[index].getValue();
    	hashmap[index] = defunct;
    	noOfItems--;
    	return answer;
    	
    }
    // collision statistics
    public int putCollisions(){
    	return noPutCollisions;
    }
    public int totalCollisions(){
    	return totalCollisions;
    }
    public int maxCollisions(){
    	return maxCollisions;
    }
    public int noPutFailures(){
    	return noPutFailures;
    }
    public void resetStatistics(){
    	noPutCollisions = 0;
    	totalCollisions = 0;
    	maxCollisions = 0;
    }
   
    
	
}
