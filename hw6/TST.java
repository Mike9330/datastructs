package hw6;

import java.util.Iterator;
import hw4.Set;
import hw5.Queue;

public class TST implements Set<String> {
    private int N;       // size
    private Node root;   // root of TST

    public static class Node {
        private char c;                 // character
        private Node left, mid, right;  // left, middle, and right subtries
        private boolean in;             // indicator if this word is in the set
    }

    /**
     * Initializes an empty string symbol table.
     */
    public TST() {
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     */
    public int size() {
        return N;
    }

	@Override
	public Iterator<String> iterator() {
		Queue<String> q = new Queue<String>();
		traverse(root, "", q);
		return q.iterator();
	}
	
	private void traverse(Node n, String rep, Queue<String> q) {
		if (n == null) return;
		traverse(n.left, rep, q);
		String word = rep + n.c;
		if (n.in) q.enqueue(word);
		traverse(n.mid, word, q);
		traverse(n.right, rep, q);
	}

	@Override
	public boolean contains(String s) {
		Node n=root;
		int d=0;
		while(n!=null && d<s.length()){
			char c=s.charAt(d);
			if(c<n.c){n=n.left;}
			else if(c>n.c){n=n.right;}
			else if(d<s.length()-1){n=n.mid;d++;}
		}
		if(n==null){return false;}
		else{
			return true;
		}
	}

	@Override
	public void add(String s) {
		if(root==null){root=new Node();}
		Node x=root;
		int d=0;
		if(!contains(s)){
			while(d<s.length()){
				char c=s.charAt(d);
				if(x.right==null && c>x.c){x.right=new Node();}
				else if(x.left==null && c<x.c){x.left=new Node();}
				else if(c<x.c){x=x.left;}
				else if(c>x.c){x=x.right;}
				else if(d<s.length()-1){x=x.mid;d++;}
			}
			x.in=true;
			x.equals(s);
			N++;
		}
		else{
			return;
		}
	}

	@Override
	public void delete(String s) {
		if(root==null){return;}
		Node x=root;
		int d=0;
		if(contains(s)){
			while(d<s.length()){
				char c=s.charAt(d);
				if(x.right.equals(s) && c>x.c){x.right=null;}
				else if(x.left.equals(s) && c<x.c){x.left=null;}
				else if(c<x.c){x=x.left;}
				else if(c>x.c){x=x.right;}
				else if(d<s.length()-1){x=x.mid;d++;}
			}
			x.in=false;
			N--;
		}
		else{
			return;
		}
		
	}
}