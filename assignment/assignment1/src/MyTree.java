import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

import interfaces.BalancedBST;
import interfaces.Position;
import interfaces.TreeArithmetic;
import interfaces.TreeProperties;
import interfaces.TreeTraversals;
import simpletree.SimpleTree;


/**
 * @author put your unikey here
 * @author and your partner's unikey, if working in a pair
 * 
 * This class, MyTree, should be your solution to the assignment
 * It should remain in the (default package)
 * 
 * Implement as many of the required methods as you can.
 */

public class MyTree<E extends Comparable<E>> extends SimpleTree<E> implements
				TreeTraversals<E>,      //PART 1
				TreeProperties,         //PART 2
				//Comparable<Tree<E>>,    //PART 3 (only if enrolled in INFO1105)
				BalancedBST<E>,       //PART 3 (only if enrolled in INFO1905)
				TreeArithmetic          //PART 4
{
	
	//constructor
	public MyTree() {
		super(); //call the constructor of SimpleTree with no arguments
	}

	@Override
	// Output the values of a pre-order traversal of the tree
	public  List<E> preOrder(){
		List<E> preorder = new ArrayList<E>();
		preorderSubtree(root(), preorder);
		return preorder;
	}
	private void preorderSubtree(Position<E> pos, List<E> preorder){
		preorder.add(pos.getElement());
		for(Position<E> c : children(pos)){
			preorderSubtree(c,preorder);
		}
	}
	@Override
	// Output the values of a post-order traversal of the tree
	public List<E> postOrder(){
		List<E> postorder = new ArrayList<E>();
		postorderSubtree(root(), postorder);
		return postorder;
	}
	private void postorderSubtree(Position<E> pos, List<E> postorder){
		for(Position<E> c : children(pos)){
			preorderSubtree(c,postorder);
		}
		postorder.add(pos.getElement());
	}
	@Override
	// Output the values of a in-order traversal of the tree
		// This operation should only be performed if the tree is a proper binary tree.
		// If it is not, then throw an UnsupportedOperationException instead of returning a value
		// Otherwise, perform the traversal with child 0 on the left, and child 1 on the right.
	public List<E> inOrder(){
		List<E> inOrder = new ArrayList<E>();
		if(!isEmpty()){
			inorderSubtree(root(), inOrder);
		}
		return inOrder;
	}
	private void inorderSubtree(Position<E> pos, List<E> inorder){
		if(pos == null){
			return;
		}
		if(numChildren(pos) == 0){
			inorder.add(pos.getElement());
		}
		else{
			inorderSubtree(children(pos).get(0), inorder);
			inorder.add(pos.getElement());
			inorderSubtree(children(pos).get(1), inorder);
		}
	}
	@Override
	public boolean isArithmetic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double evaluateArithmetic() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getArithmeticString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int height() {
		if (isEmpty()){
			return 0;
		}
		else{
			int height = 0;
			for (int i = 0; i < numChildren(root());i++){
				height += height(root().getChildren().get(i));
			}
			return height;
		}
	}
	private int height(Position<E> root){
		if(root().getChildren().isEmpty()){
			return 0;
		}
		if (root.getParent() == null){
			return 0;
		}
		else{
			return 1 + height(parent(root));
		}
	}

	@Override
	public int height(int maxDepth) {
		if (isEmpty()){
			return 0;
		}
		
		else{
			int height = 0;
			for (int i = 0; i < numChildren(root()) && height < maxDepth;i++){
				height += height(root().getChildren().get(i));
			}
			return height;
		}
	}
	@Override
	public int numLeaves() {
		return numLeaves(root());
	}
	private int numLeaves(Position<E> root){
		if(root != null){
			if(numChildren(root) == 0){
				return 1;
			}
			else{
				return numLeaves(root.getChildren().get(0)) + numLeaves(root.getChildren().get(1));
			}
		}
		// when root == null
		return 0;
	}

	@Override
	public int numLeaves(int depth) {
		if(root() == null)
			return 0;
		return leavesPosition(depth).size();
		
	}
	// creates list of leaves at given depth
	private List<Position<E>> leavesPosition(int depth){
		List<Position<E>> leaves = new ArrayList<Position<E>>();
		Position<E> leaveParent = root();
		Position<E> leave = root();
		for(int i = 0; i <= height(); i++){
			if(numChildren(leaveParent) != 0){
				for(int j = 0; j < numChildren(leaveParent); j++){
					leave = children(leaveParent).get(j);
					if(i == depth && numChildren(leave) == 0){
						leaves.add(leave);
					}
				}
				if(numChildren(leave) != 0){
					leaveParent = leave;
				}
			}
		}
		return leaves;
	}

	@Override
	public int numPositions(int depth) {
		if(root() == null)
			return 0;
		return posDepth(depth).size();
	}
	// stores position at depth depth
	private List<Position<E>> posDepth(int depth){
		List<Position<E>> positions = new ArrayList<Position<E>>();
		Position<E> pos = root();
		Position<E> posParent = root();
		for(int i = 0; i <= height(); i++){
			if(numChildren(posParent) != 0){
				for(int j = 0; j < numChildren(posParent); j++){
					pos = children(posParent).get(j);
					if(i == depth){
						positions.add(pos);
					}
					if(numChildren(pos) != 0 && i != depth){
						posParent = pos;
					}
				}
			}
		}
		return positions;
	}

	@Override
	public boolean isBinary() {
		Position<E> pos = root();
		Position<E> posParent = root();
		for(int i = 0; i <= height(); i++){
			if(numChildren(posParent) != 0){
				for(int j = 0; j < numChildren(posParent); j++){
					pos = children(posParent).get(j);
					if(numChildren(posParent) > 2 || numChildren(pos) > 2){
						return false;
					}
					posParent = pos;
				}
			}
		}
 		return true;
	}

	@Override
	public boolean isProperBinary() {
		Position<E> pos = root();
		Position<E> posParent = root();
		for(int i = 0; i <= height(); i++){
			if(numChildren(posParent) != 0){
				for(int j = 0; j < numChildren(posParent); j++){
					if(!isFull(posParent)){
						return false;
					}
					pos = children(posParent).get(j);
					if(isLeaf(pos)){
						continue;
					}
					if(isFull(pos)){
						posParent = pos;
					}
				}
			}
		}
 		return true;
	}

	// returns true if all the levels except the last one is full and 
	// last level is filled from left to right
	@Override
	public boolean isComplete() {
		if(isEmpty()){
			return false;
		}
		Position<E> pos = root();
		Position<E> posParent = root();
		// check until the level height - 1
		for(int i = 0; i < height(); i++){
			if(numChildren(posParent) != 0){
				for(int j = 0; j < numChildren(posParent); j++){
					pos = children(posParent).get(j);
					posParent = pos;
				}
			}
		}
		for(int k = 0; k < numChildren(posParent); k++){
			if(numChildren(pos))
		}
 		return true;
	}
	// checks if the position is a leaf
	private boolean isLeaf(Position<E> pos){
		if(numChildren(pos) == 0){
			return true;
		}
		return false;
	}
	// checks if position is full
	private boolean isFull(Position<E> pos){
		if(numChildren(pos) == 2){
			return true;
		}
		else{
			return false;
		}
	}
	// checks if any difference in height of leaves is less than 1
	@Override
	public boolean isBalanced() {
		List<Position<E>> leaves = leavesPosition();
		int max = height(leaves.get(0));
		int min = height(leaves.get(0));
		for (int i = 1; i < leaves.size();i++){
			if(max < height(leaves.get(i))){
				max = height(leaves.get(i));
			}
			if(min > height(leaves.get(i))){
				min = height(leaves.get(i));
			}
		}
		if (max - min > 1){
			return false;
		}
		return true;
	}
	private List<Position<E>> leavesPosition(){
		List<Position<E>> leaves = new ArrayList<Position<E>>();
		Position<E> leaveParent = root();
		Position<E> leave = root();
		for(int i = 0; i <= height(); i++){
			if(numChildren(leaveParent) != 0){
				for(int j = 0; j < numChildren(leaveParent); j++){
					leave = children(leaveParent).get(j);
					if(numChildren(leave) == 0){
						leaves.add(leave);
					}
				}
				if(numChildren(leave) != 0){
					leaveParent = leave;
				}
			}
		}
		return leaves;
	}
	@Override
	public boolean isHeap(boolean min) {
		if(!isComplete()){
			return false;
		}
		Position<E> pos = root();
		Position<E> parent = root();
		E parentValue = root().getElement();
		E posValue = root().getElement();
		for(int i = 0; i < height(); i++){
			if (min){
				if(numChildren(parent) != 0){
					for (int j = 0; j < numChildren(parent); j++){
						parentValue = parent.getElement();
						pos = children(parent).get(j);
						posValue = pos.getElement();
						if(posValue.compareTo(parentValue) >= 0){
							return false;
						}
					}
					if(numChildren(pos) != 0){
						parent = pos;
					}
				}
			}
			else {
				if(numChildren(parent) != 0){
					for (int j = 0; j < numChildren(parent); j++){
						parentValue = parent.getElement();
						pos = children(parent).get(j);
						posValue = pos.getElement();
						if(posValue.compareTo(parentValue) <= 0){
							return false;
						}
					}
					if(numChildren(pos) != 0){
						parent = pos;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean isBinarySearchTree() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(E value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(E value) {
		// TODO Auto-generated method stub
		return false;
	}

}
