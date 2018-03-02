/**
 * Your homework is to complete the methods marked TODO.
 * You must not change the declaration of any method.
 */

package hw1;

import java.util.Iterator;

import hw1.LinkedListST.Node;

/**
 *  The SortedArrayST class represents an (ordered) symbol table of
 *  String-value pairs.
 */
public class SortedArrayST<Value> implements StringValueST<Value> {
	private static final int MIN_SIZE = 2;
	private String[] keys;   // the keys array
	private Value[] vals;    // the values array
	private int N = 0;       // size of the symbol table

	/**
	 * Initializes an empty symbol table.
	 */
	public SortedArrayST() {
		this(MIN_SIZE);
	}

	/**
	 * Initializes an empty symbol table of given size.
	 */
	@SuppressWarnings("unchecked")
	public SortedArrayST(int size) {
		keys = new String[size];
		vals = (Value[])(new Object[size]);
	}
	
	/**
	 * Initializes a symbol table with given sorted key-value pairs.
	 * If given keys list is not sorted in (strictly) increasing order,
	 * then the input is discarded and an empty symbol table is initialized.
	 */
	public SortedArrayST(String[] keys, Value[] vals) {
		this(keys.length < MIN_SIZE ? MIN_SIZE : keys.length);
		N = (keys.length == vals.length ? keys.length : 0);
		int i;
		for (i = 1; i < N && keys[i].compareTo(keys[i - 1]) > 0; i++);
		if (i < N) { // input is not sorted
			System.err.println("SortedArrayST(Key[], Value[]) constructor error:");
			System.err.println("Given keys array of size " + N + " was not sorted!");
			System.err.println("Initializing an empty symbol table!");
			N = 0;
		} else {
			for (i = 0; i < N; i++) {
				this.keys[i] = keys[i];
				this.vals[i] = vals[i];
			}
		}
	}
	
	// resize the underlying arrays
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		String[] tempk = new String[capacity];
		Value[] tempv = (Value[]) new Object[capacity];
		for (int i = 0; i < N; i++) {
			tempk[i] = keys[i];
			tempv[i] = vals[i];
		}
		vals = tempv;
		keys = tempk;
	}
	
	/**
	 * Returns the number of keys in this symbol table.
	 */
	public int size() {
		return N;
	}
	
	/**
	 * Returns the value associated with the given key in this symbol table.
	 */
	public Value get(String key) {
		int r = rank(key);
		if (r < N && key.equals(keys[r])) return vals[r];
		return null;
	}
	
	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 */
	public void put(String key, Value val) {
		int r = rank(key);
		if (r >= N || !key.equals(keys[r])) {
			// insert new key-value pair
			if (N == keys.length) resize(2 * keys.length);

			for (int j = N; j > r; j--)  {
				keys[j] = keys[j-1];
				vals[j] = vals[j-1];
			}
			keys[r] = key;
			N++;
		}
		vals[r] = val;
	}
	/**
	 * Returns an iterator that iterates over the keys in this symbol table.
	 */
	public Iterator<String> iterator() {
		return new KeysIterator();  
	}

	private class KeysIterator implements Iterator<String> {
		private int i = 0;

		public boolean hasNext() {
			return i < N;
		}

		public String next() {
			return keys[i++];
		}
	}
	
//From Algs4 book/site
	public boolean isEmpty() {
        return size() == 0;}
	public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null"); 
        if (isEmpty()) return;
        int i = rank(key);
        if (i == N || keys[i].compareTo(key) != 0) {
            return;
        }
        for (int j = i; j < N-1; j++)  {
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];}
        N--;
        keys[N] = null;
        vals[N] = null;
        if (N > 0 && N == keys.length/4) resize(keys.length/2);
    } 	
	
	/**
	 * The method deleteAllStartingWith removes all keys (strings) that start with
	 * the given string, as well as their associated values.
	 * This method does not do anything if there are no such keys (strings).
	 */

	public void deleteAllStartingWith(String s) {
		int counter = 0;
		int i=0;
			for (; i < N; i++) {
				if (keys[i].startsWith(s)) {
					keys[i] = keys[i + 1];
					vals[i] = vals[i + 1];
					counter++;
				}
				N-=counter;
			}
			for ( i = 0; i < counter; i++) {
				keys[N + i] = null;
				vals[N + i] = null;
			}

	}
	// TODO
	

	/**
	 * The method firstStartingWith returns the lexicographically first key (string)
	 * in the symbol table that starts with the given string s.
	 * This method returns null if no such key (string) exists in the symbol table.
	 */
	public String firstStartingWith(String s) {
		for (int i=0;i<size();i++){
			if(keys[i].startsWith(s)){return keys[i];}}
		return null;
		// TODO
		}
 // TODO (correct functionality of put depends on correct functionality of rank)
	

	/**
	 * rank returns the number of keys in this symbol table that is less than the given key.
	 * your implementation should be recursive. 
	 */
	public int rank(String key) {
		int low=0;
		int high=N-1;
		while(low<=high){
			int median=(low+high)/2;
			int cmp=keys[median].compareTo(key);
			if(cmp==0)return median;
			else if(cmp<0)low=median+1;
			else high=median-1;
			}
		
		return low;
 // TODO (correct functionality of put depends on correct functionality of rank)
	}
}