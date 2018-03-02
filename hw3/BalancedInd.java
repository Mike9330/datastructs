package hw3;

import java.util.LinkedList;

/******************************************************************************
 * 
 * Your homework assignment is to design the below data structure in the form of
 * a balanced BST to maintain a symbol table of words and a list of page indices.
 * 
 * Hint: you may implement and use a LinkedList data structure to store the list
 * of indexes for each word.
 *
 */


public class BalancedInd implements Indexes {
	
	
	RedBlackBST<String, LinkedList<Integer>> bst = new RedBlackBST<String, LinkedList<Integer>>();
	
	@Override
	public void addIndex(String word, int index) {
		LinkedList<Integer> linkedlist;
		linkedlist = bst.get(word);
		if(linkedlist==null){
			LinkedList<Integer> newlinkedlist= new LinkedList<>();
			bst.put(word,newlinkedlist);
			newlinkedlist.add(index);
		}
		else{
		linkedlist.add(index);
		}
	}

	@Override
	public Iterable<Integer> getIndexes(String word) {
		LinkedList<Integer> linkedlist;
		linkedlist = bst.get(word);
		return linkedlist;
	}

	@Override
	public void remIndex(String word, int index) {
		LinkedList<Integer> linkedlist;
		linkedlist = bst.get(word);
		if(linkedlist==null){return;}
		linkedlist.remove((Integer)index);
		if(linkedlist.isEmpty()){bst.delete(word);}
		
	}

	@Override
	public boolean contains(String word) {
		if(bst.contains(word)){return true;}
		return false;
	}

	@Override
	public int numPairs() {
		int numIndexes=0;
		int currIndexes=0;
		System.out.println(bst.select(currIndexes));currIndexes++);
		
		//if(bst.getIndexes())!=null)
		return numIndexes;
	}
}