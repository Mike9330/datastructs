package hw4;

import java.util.Iterator;


public class SetLP<Item> implements Set<Item> {
    private static final int INIT_CAPACITY = 4;

    private int N;           // number of items in the hash table
    private int M;           // size of linear probing table
    private Item[] items;    // the items

    /**
     * Initializes an empty set.
     */
    public SetLP() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty set with the specified initial capacity.
     */
    @SuppressWarnings("unchecked")
	public SetLP(int capacity) {
        M = capacity;
        items = (Item [])new Object[M];
    }

    /**
     * Returns the size of this set.
     */
    public int size() {
        return N;
    }

    // hash function for items - returns value between 0 and M-1
    private int hash(Item s) {
        return (s.hashCode() & 0x7fffffff) % M;
    }


    /**
     * Returns true iff this set contains the specified item.
     */
    public boolean contains(Item s) {
    	for (int i = hash(s); items[i] != null; i = (i+1) % M){
    		 if (s.equals(items[i])){
    			 return true;}
    	}
    	// TODO
        return false;
    }
    	// TODO
     
    
    /**
     * Inserts the specified item into this set (no duplicates).
     */
    public void add(Item s) {
    	int i;
		for (i = hash(s); items[i] != null; i = (i + 1) % M) {
			if (items[i].equals(s)) {
				break;
			}
		}
			items[i] = s;
		
		// TODO
    }

    /**
     * Removes the specified item from this set (if it is in this set)    
     */
    public void delete(Item s) {
    	// TODO
    }

    /**
     * Returns an iterator for this set
     */
    public Iterator<Item> iterator() {
    	return new SetIterator();
    }
    
    /**
     * Iterator class for SetLP    
     */
    private class SetIterator implements Iterator<Item> {
    	private int i = 0;

		public boolean hasNext() {
			while (i < M) {
				if (items[i] != null) return true;
				i++;
			}
			return false;
		}

		public Item next() {
			return items[i++];
		}
    }
}