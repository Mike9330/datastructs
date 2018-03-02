package hw6;

import java.util.Iterator;
import hw4.Set;
import hw5.Queue;
import hw6.TST.Node;

public class Trie implements Set<String> {
    private static final int R = 256;        // extended ASCII

    private Node root;      // root of trie
    private int N;          // number of keys in trie

    // R-way trie node
    private static class Node {
    	private boolean in;
        private Node[] next = new Node[R];
    }

   /**
     * Initializes an empty string set.
     */
    public Trie() {
    }

    /**
     * Returns the number of key-value pairs in this set.
     */
    public int size() {
        return N;
    }

    /**
     * Is this set empty?
     */
    public boolean isEmpty() {
        return size() == 0;
    }

	@Override
	public Iterator<String> iterator() {
		Queue<String> q = new Queue<String>();
		traverse(root, "", q);
		return q.iterator();
	}
	
	private void traverse(Node n, String rep, Queue<String> q) {
		if (n == null) return;
		if (n.in) q.enqueue(rep);
		for (char c = 0; c < R; c++) {
			traverse(n.next[c], rep + c, q);
		}
	}
	
	@Override
	public boolean contains(String s) {
		// TODO Auto-generated method stub
		Node x=root; 
		if(root==null){return false;}
		int d=0;
		while(d<s.length() && x!=null){
			char c=s.charAt(d);
			x=x.next[c];
			d++;
		}
		if(x==null){return false;}
		else{
			return true;
		}
	}

	@Override
	public void add(String s) {
		// TODO Auto-generated method stub
		if(root==null){root=new Node();}
		Node x=root;
		int d=0;
		if(!contains(s)){
			while(d<s.length()){
				char c=s.charAt(d);
				if(x.next[c]==null){x.next[c]=new Node();}
				x=x.next[c];
				d++;
			}
			x.next.equals(s);
			x.in=true;
			N++;
		}
		else{
			return;
		}
	}

	@Override
	public void delete(String key) {
		root = delete(root, key, 0);
	}

	private Node delete(Node x, String key, int d) {
		if (x == null)
			return null;
		if (d == key.length()) {
			if (x != null)
				N--;
			x = null;
		} else {
			char c = key.charAt(d);
			x.next[c] = delete(x.next[c], key, d + 1);
		}
		if (x != null)
			return x;
		for (int c = 0; c < R; c++)
			if (x.next[c] != null)
				return x;
		return null;
	}

}