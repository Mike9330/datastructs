package hw3;

// this interface defines a set of methods that generalizes symbol tables as follows:
// Indexes interface models a symbol table that associates a word with a list of indices.
// It allows addition and removal of a single index as opposed to
// addition and removal of an entire word along with the indexes associated with it.

public interface Indexes {
	// adds a (word, index) pair into the symbol table
	// if the word does not exist in the symbol table, it creates a new entry
	// otherwise, it adds index into the list of indexes for word
	public void addIndex(String word, int index);
	
	// returns the list of indexes associated with the given word
	public Iterable<Integer> getIndexes(String word);
	
	// removes the particular index associated with the given word
	public void remIndex(String word, int index);
	
	// returns true if and only if the symbol table contains an entry
	// for the given word with at least one index
	public boolean contains(String word);
	
	// returns the number of (word,index) pairs in the dictionary 
	public int numPairs();
	
}