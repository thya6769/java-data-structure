package interfaces;

public interface BalancedBST<E> {


	/**
	 * PART 3 (INFO1905)
	 * 
	 * If you are enrolled in INFO1905, implement the following methods, which
	 * allow balanced insertion and deletion to a binary search tree. You may
	 * assume that the tree is a balanced binary search tree before either of
	 * these methods are called.
	 * 
	 * If you are enrolled in INFO1105, you should instead implement the
	 * Comparable<Tree<E>> interface (see assignment description for detail)
	 */

	public boolean add(E value);
	// if value is already in the balanced BST, do nothing and return false
	// otherwise, add value to the balanced binary search tree (BST) and return true
	// use the algorithm shown in the week 6 lecture - the BST must remain balanced

	public boolean remove(E value);
	// if value is in the balanced BST, remove it and return true
	// otherwise, do nothing and return false
	// implement the algorithm shown in the week 6 lecture to ensure that the BST remains balanced


}
