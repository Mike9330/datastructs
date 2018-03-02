/**
 * Your homework is to complete the methods marked TODO.
 * You must not change the declaration of any method.
 */

package hw2;
import java.util.Iterator;

import hw1.LinkedListST.Node;

/**
 *  The B(inary)S(earch)T(ree) class represents a symbol table of
 *  generic key-value pairs.  It supports put, get, and delete methods.
 */
public class BST<Key extends Comparable<Key>, Value> implements Iterable<Key> {
	private Node root;            // root of BST

	private class Node {
		public Key key;           // sorted by key
		public Value val;         // associated data
		public Node left, right, parent;  // left and right subtrees
		
		public Node(Key key, Value val) {
			this.key = key;
			this.val = val; 
		}
		
		/**
		 * Appends the preorder string representation of the sub-tree to the given StringBuilder.
		 */
		public void buildString(StringBuilder s) {
			s.append(left == null ? '[' : '(');
			s.append(key + "," + val);
			s.append(right == null ? ']' : ')');
			if (left != null) left.buildString(s);
			if (right != null) right.buildString(s);
		}
	}
	
	/**
	 * Initializes an empty symbol table.
	 */
	public BST() {
	}
	
	/**
	 * Returns the value associated with the given key.
	 */
	public Value get(Key key) {
		return get(root, key);
	}
	
	private Value get(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) return get(x.left, key);
		else if (cmp > 0) return get(x.right, key);
		else              return x.val;
	}
	
	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 */
	public void put(Key key, Value val) {
		root = put(root, key, val);
	}
	
	private Node put(Node x, Key key, Value val) {
		if (x == null) return new Node(key, val);
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = put(x.left,  key, val);
		else if (cmp > 0) x.right = put(x.right, key, val);
		else              x.val   = val;
		return x;
	}
	
	/**
	 * Returns the preorder string representation of the BST.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		root.buildString(s);
		return s.toString();
	}

	/**
	 * Returns an iterator that iterates over the keys in this symbol table.
	 */
	public Iterator<Key> iterator() {
		return new NodeIterator();  
	}

	// Do not change the implementation of this class
	private class NodeIterator implements Iterator<Key> {
		private Node current;
		
		// constructor starts with the minimum node (left-most)
		public NodeIterator() {
			current = root;
			while (current.left != null) {
				current = current.left;
			}
		}

		// return if there are any more keys to iterate over
		public boolean hasNext() {
			return current != null;
		}

		// returns the next key of the iteration while moving over to the next node
		public Key next() {
			Key curKey = current.key;
			current = nextNode(current);
			return curKey;
		}
	}
	
	/**
	 * Returns the node that contains the next higher key is the sorted order.
	 */
	
	private Node nextNode(Node n) {
		if(n==null){return null;}
		if (n.right != null) {
            return min(n.right);}
		Node x=n.parent;
		if(x==null){return null;}
		while(x!=null && n==x.right){
				n=x;
				x=x.parent;
			}
		return x;
}
	/**
	 * Returns the number of nodes that are exactly at distance l away from the root.
	 */
	public int sizeOfLevel(int l){
		Node n=root;
		return sizeOfLevel(l,0,n);
	    }
	public int sizeOfLevel(int l, int counter, Node n){
		if(n==null){return 0;}
		if(l==counter){return 1;}
		int rightheight=sizeOfLevel(l,counter+1,n.right);
		int leftheight=sizeOfLevel(l,counter+1,n.left);
		return rightheight+leftheight;
	}
	
	
	/**
	 * Returns the length of the maximum distance from the root to a leaf node.
	 * (The distance from the root to itself is 0.) 
	 */
	//return height of the subtree at n
	
	public int height() {
		return height(root); // TODO
	}
	public int height(Node n){
		if(n==null){return -1;}
		else{
			int leftheight=height(n.left);
			int rightheight=height(n.right);
			if(leftheight>rightheight){
				return 1+leftheight;}
			else{
				return 1+rightheight;
		}
	}
	}
	/**
	 * Deletes all keys (and values associated with them) that are less than the given key.
	 */
	
	public void deleteAllLessThan(Key key) {
		if(key==null){return;}		
		deleteHelp(root,key);
	
	}
		
	public void deleteHelp(Node n,Key key){
		if(n==null){return;}
		int cmp=n.key.compareTo(key);
		if(cmp<0){
			if(n.right==null && n.left==null){
				if(n.parent.left==n){
					n.parent.left=null;}
				if(n.parent.right==n){
					n.parent.right=null;}
		}
			else if(n.right!=null || n.left!=null){
				if(n.right!=null){n=n.right;}
				if(n.left!=null){n=n.left;}
		}
			else{
				n=nextNode(n);
		}
		}
		deleteHelp(n.right,key);
		deleteHelp(n.left,key);
	}
		// TODO
	
	/**
	 * Merges the given symbol table into this symbol table.
	 * If the given symbol table contains keys that is already in this symbol table
	 * merge overwrites those keys' values with the values in the given symbol table.
	 */
	public void merge(BST<Key, Value> bst) {
		merge(bst,root);
		
		
		// TODO
	}
	public void merge(Node n,BST<Key, Value> bst){
		if(n==null){return;}
		root=merge(n.left, bst);
		n.left=root;
		return merge(n.right,root);
		
	}
	
	public Node min(Node n){
		Node current=n;
		while (current.left!=null){
			current=current.left;
		}
		return current;
	}
	public Key inclassmin(){
		if(root==null){return null;}
		Node x=root;
		while(x.left!=null){
			x=x.left;
		}
		return x.key;
	}
	//minimum of given subtree at n 
	public Key inclassMin(Node n){
		if(n==null) return null;
		if(n.left==null) return n.key;
		return inclassMin(n.left);
	}
	public int size(){
		return size(root);
		}
	public int size(Node n){
		if(n==null) return 0;
		return 1+size(n.left)+size(n.right);
		}
	/*
	public void buildString(StringBuilder s){
		if(left!=null) left.buildString(s);
		if (right!=null) right.buildString(s);
		s.append(left==null ? '[':'(');
		s.append(key+","+val);
		s.append(right==null ? ']':')');
	}
	*/
	public static void main(String args[]){
		BST<String,Integer>bst;
		bst=new BST <String,Integer>();
		bst.put("S",10);
		bst.put("E",5);
		bst.put("X",1);
		bst.put("A",3);
		bst.put("R",3);
		bst.put("C",2);
		bst.put("H",7);
		bst.put("G",7);
		bst.put("M",7);
		
	}
}