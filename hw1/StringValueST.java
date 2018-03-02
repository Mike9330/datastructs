package hw1;

public interface StringValueST<Value> extends Iterable<String> {
	public int size();
	public Value get(String key);
	public void put(String key, Value val);
	public void deleteAllStartingWith(String s);
	public String firstStartingWith(String s);
	public int rank(String key);
}
