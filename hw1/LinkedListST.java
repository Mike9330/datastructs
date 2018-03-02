/**
 * Your homework is to complete the methods marked TODO.
 * You must not change the declaration of any method.
 */

package hw1;

import java.util.Iterator;

/**
 *  The LinkedListST class represents an (unordered) symbol table of
 *  String-value pairs.
 */
public class LinkedListST<Value> implements StringValueST<Value> {
	private Node first;      // the linked list of key-value pairs

	// a helper linked list data type
	public class Node {
		private String key;
		private Value val;
		private Node next;

		public Node(String key, Value val, Node next)  {
			this.key  = key;
			this.val  = val;
			this.next = next;
		}
	}

	/**
	 * Initializes an empty symbol table.
	 */
	public LinkedListST() {
	}
	
	/**
	 * Returns the number of keys in this symbol table.
	 */
	public int size() {
		int s = 0;
		for (Node n = first; n != null; n = n.next) {
			s++;
		}
		return s;
	}

	/**
	 * Returns the value associated with the given key in this symbol table.
	 */
	public Value get(String key) {
		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key)) {
				return x.val;
			}
		}
		return null;
	}

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is null.
	 */
	public void put(String key, Value val) {
		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key)) {
				x.val = val;
				return;
			}
		}
		first = new Node(key, val, first);
	}

	/**
	 * Returns an iterator that iterates over the keys in this symbol table.
	 */
	public Iterator<String> iterator() {
		return new KeysIterator();  
	}

	private class KeysIterator implements Iterator<String> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}
		
		public String next() {
			String key = current.key;
			current = current.next; 
			return key;
		}
	}
	
	/**
	 * The method deleteAllStartingWith removes all keys (strings) that start with
	 * the given string, as well as their associated values.
	 * This method does not do anything if there are no such keys (strings).
	 */
	public void deleteAllStartingWith(String s) {
		while(first.key.startsWith(s)){first=first.next;}
		for (Node x=first.next;x!=null;x=x.next){
			if(x.next==null){return;}
			if(x.next.key.startsWith(s)){x.next=x.next.next;}
		}
		// TODO
	}

	/**
	 * The method firstStartingWith returns the lexicographically first key (string)
	 * in the symbol table that starts with the given string s.
	 * This method returns null if no such key (string) exists in the symbol table.
	 */
	public String firstStartingWith(String s) {
		for(Node x=first;x!=null;x=x.next){
			//if(x==null || x.next==null){return null;}
			if(x.key.startsWith(s)){return x.key;}}
		return null; // TODO
	}

	/**
	 * rank returns the number of keys in this symbol table that is less than the given key.
	 * your implementation should be recursive. 
	 */
	public int rank(String key) {
		return rankHelp(first,key,0);
		}
    private int rankHelp(Node current, String key, int rank){
    	if (current==null){
    		return rank;}
    	int val = current.key.compareTo(key);
    	if(val < 0){
    		rank++;}
    	return rankHelp(current.next,key,rank);
    }
	
	public static void main(String[]args){
		StringValueST<Integer>st;
		st= new LinkedListST<Integer>();
		st.put("Alice", 20);
		st.put("Allie", 25);
		st.put("Alex", 24);
		st.put("Bob", 23);
		st.put("Brian", 19);
		st.put("Alfonso", 49);
		
		String s="Al";
		for(String key : st){
			System.out.println("Key= "+ key+", Value= "+st.get(key));
			System.out.println("Key starts with "+ s +":"+key.startsWith(s));
			
		}
		
		st.deleteAllStartingWith(s);
		System.out.println("After deleting:"+s);
		for (String key:st){
			System.out.println("Key= "+key+",Value= "+st.get(key));
			
		//st.firstStartingWith(s);
		//System.out.println("Values starting with S:"+s);
		//for (String key2:st){
		//	System.out.println("Key= "+key2+",Value= "+st.get(key2));
		}
		
	}
}
//}