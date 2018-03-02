package hw4;

import java.util.Iterator;

public class SetSC<Item> implements Set<Item> {
    private static final int INIT_CAPACITY = 4;

    private int N;                                // number of items
    private int M;                                // hash table size
    private Node[] st;                            // array of linked-lists

    private static class Node {
    	Object item;
    	Node next;
    	
    	public Node(Object s) {
    		item = s;
    	}
    	
    	public Node(Object s, Node next) {
    		this(s);
    		this.next = next;
    	}
    }

    /**
     * Initializes an empty set.
     */
    public SetSC() {
        this(INIT_CAPACITY);
    } 

    /**
     * Initializes an empty set with M chains.
     */
	public SetSC(int M) {
        this.M = M;
        st = new Node[M];
    } 

    // hash value between 0 and M-1
    private int hash(Item s) {
        return (s.hashCode() & 0x7fffffff) % M;
    } 

    /**
     * Returns the size of this set.
     */
    public int size() {
        return N;
    } 

    /**
     * Returns true iff this set contains the specified item.
     */
    public boolean contains(Item s) {
    	int i = hash(s);
    	for(Node x=st[i];x!=null;x=x.next){
    		if(s.equals(x.item)){return true;}
    	}
    	// TODO
        return false;
    }
    /**
     * Inserts the specified item into this set (no duplicates).
     */
    public void add(Item s) {
    	int i=hash(s);
    	if(contains(s)==true){return;}
    	st[i]=new Node(s,st[i]);
    	// TODO  
    	N++;
    }

    /**
     * Removes the specified item from this set (if it is in this set)    
     */
    public void delete(Item s) {
    	int i = hash(s);
    	st[i]=delete(st[i],s);
    	// TODO
    }
    
    public Node delete(Node x, Item s){
    	if (x == null) return null;
        if (s.equals(x.item)) {
            N--;
            return x.next;
        }
        x.next = delete(x.next, s);
        return x;
    	
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
    	private Node n = st[0];

		public boolean hasNext() {
			while (n == null) {
				i++;
				if (i < M) {
					n = st[i];
				} else {
					return false;
				}
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		public Item next() {
			Item item = (Item) n.item;
			n = n.next;
			return item;
		}
    }
}