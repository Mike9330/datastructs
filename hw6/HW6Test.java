package hw6;

import static org.junit.Assert.*;
import org.junit.Test;

import hw4.Set;
import java.util.Random;

public class HW6Test {
	String border = "*******************************************\n";
	String passed = "* Passed!                                 *\n";
	String failed = "* Failed!                                 *\n";
	String test;
	
	AssertionError ae;
	Exception e;
	
	int size, psize;
	int p;
	String[] keys;
	boolean[] picked;
	
	String arg;
	
	Set<String> st;
	boolean trie;
	
	private Set<String> createSet(int cap, int n) {
		Set<String> st = trie ? new Trie() : new TST();
		if (keys != null) for (int i = 0; i < keys.length && i < n; i++) st.add(keys[i]);
		return st;
	}
	
	private String keysToString(boolean justPicked) {
		int sz = justPicked ? psize : size;
		StringBuilder s = new StringBuilder();
		s.append("size = " + sz + ", strings = ");
		int i, k;
		for (i = 0, k = 0; i < sz - 1; i++, k++) {
			if (justPicked) for (; p == k || !picked[k]; k++);
			s.append(keys[k] + ", ");
		}
		if (sz > 0) {
			if (justPicked) for (; p == k || !picked[k]; k++);
			s.append(keys[k]);
		}
		
		return s.toString();
	}

	private static String twoLetterString(int val) {
		char chars[] = new char[2];
		chars[0] = (char)('A' + ((val / 26) % 26));
		chars[1] = (char)('A' + (val % 26));
		return String.valueOf(chars);
	}
	
	private void randomTwoLetterStringsBelow(Random rand, int i0, int i1, int threshold) {
		int space = threshold;
		int size = i1 - i0;
		int i, val;
		
		boolean[] taken = new boolean[space];
		for (i = 0; i < size; i++) {
			while(true) {
				val = rand.nextInt(space);
				if (!taken[val]) break;
			}
			taken[val] = true;
		}
		for (i = i0, val = 0; val < space; val++) {
			if (taken[val]) keys[i++] = twoLetterString(val);
		}
	}
	
	private void tstAdd() {
		Set<String> st = createSet(0, 0);
		Random rand = new Random(0);
		int i;
		for (size = 1; size <= 100; size++) {
			st = createSet(size * 4, 0);
			keys = new String[size];
			picked = new boolean[size];
			randomTwoLetterStringsBelow(rand, 0, size, 26 * 26);
			for (psize = 0; psize < size; psize++) {
				while(true) {
					p = rand.nextInt(size);
					if (!picked[p]) break;
				}
				picked[p] = true;
				for (i = 0; i < size; i++) {
					if (picked[i]) st.add(keys[i]);
				}
				// testing size and keys
				if (trie) {
					assertEquals(psize + 1, ((Trie)st).size());
				} else {
					assertEquals(psize + 1, ((TST)st).size());
				}
				boolean check[] = new boolean[psize + 1];
				for (String s : st) {
					for (i = 0; i <= psize; i++) {
						if (s.equals(keys[i])) {
							assertTrue("String " + s + " should not be in the set (was not picked)", picked[i]);
							assertFalse("String " + s + " appears multiple times in the set", check[i]);
							check[i] = true;
							break;
						}
					}
					assertTrue("String " + s + " should not be in the set", i < size);
				}
			}
		}
	}
	
	private void tstContains() {
		Set<String> st = createSet(0, 0);
		Random rand = new Random(0);
		int i;
		for (size = 1; size <= 100; size++) {
			st = createSet(size * 4, 0);
			keys = new String[size];
			picked = new boolean[size];
			randomTwoLetterStringsBelow(rand, 0, size, 26 * 26);
			for (psize = 0; psize < size; psize++) {
				while(true) {
					p = rand.nextInt(size);
					if (!picked[p]) break;
				}
				picked[p] = true;
				for (i = 0; i < size; i++) {
					if (picked[i]) st.add(keys[i]);
				}
				for (i = 0; i <= psize; i++) {
					if (picked[i]) {
						assertTrue("should contain " + keys[i], st.contains(keys[i]));
					} else {
						assertFalse("should not contain " + keys[i], st.contains(keys[i]));
					}
				}
			}
		}
	}
	
	private void tstDel() {
		Set<String> st;
		Random rand = new Random(0);
		int i;
		for (size = 1; size <= 100; size++) {
			keys = new String[size];
			picked = new boolean[size];
			randomTwoLetterStringsBelow(rand, 0, size, 26 * 26);
			st = createSet(size * 4, size);
			for (psize = 0; psize < size; psize++) {
				while(true) {
					p = rand.nextInt(size);
					if (!picked[p]) break;
				}
				picked[p] = true;
				st.delete(keys[p]);
				for (i = 0; i < size; i++) {
					if (!picked[i]) st.add(keys[i]);
				}
				// testing size and keys
				if (trie) {
					assertEquals(size - psize - 1, ((Trie)st).size());
				} else {
					assertEquals(size - psize - 1, ((TST)st).size());
				}
				boolean check[] = new boolean[size];
				for (String s : st) {
					for (i = 0; i < size; i++) {
						if (s.equals(keys[i])) {
							assertTrue("String " + s + " should not be in the set (was deleted)", !picked[i]);
							assertFalse("String " + s + " appears multiple times in the set", check[i]);
							check[i] = true;
							break;
						}
					}
					assertTrue("String " + s + " should not be in the set", i < size);
				}
			}
		}
	}
	
	private void testMethod(int methodID, boolean trie) throws Exception {
		try {
			this.trie = trie;
			System.out.print(border + test + border);
			switch (methodID) {
			case 0: tstAdd(); break;
			case 1: tstContains(); break;
			case 2: tstDel(); break;
			}
		} catch(AssertionError aerr) {
			ae = aerr;
		} catch(Exception err) {
			e = err;
		}
		
		if (ae != null || e != null) {

			if (size <= 100) {
				System.out.println("Fails on the below strings:");
				System.out.println(keysToString(false));
				switch (methodID) {
				case 0: System.out.println("Failure in add after trying to add the string = " + keys[p] +  " (successfully added the following:)");
						System.out.println(keysToString(true));
						break;
				case 1: System.out.println("Failure in contains after adding string = " + keys[p] +  " (successfully added the following:)");
						System.out.println(keysToString(true));
						break;
				case 2: System.out.println("Failure in deleting string = " + keys[p] +  " (successfully deleted the following:)");
						System.out.println(keysToString(true));
						break;
				}
			}
			
			System.out.print("\n" + border + test + failed + border);
			
			if (ae != null) throw ae;
			if (e != null) throw e;
		} else {
			System.out.print(border + test + passed + border);
		}
	}
	
	@Test
	public void testDelTrie() throws Exception {
		test = "* Testing delete (Trie)                   *\n";
		testMethod(2, true);
	}
	
	@Test
	public void testContainsTrie() throws Exception {
		test = "* Testing contains (Trie)                 *\n";
		testMethod(1, true);
	}
	
	@Test
	public void testAddTrie() throws Exception {
		test = "* Testing add (Trie)                      *\n";
		testMethod(0, true);
	}
	
	@Test
	public void testDeleteTST() throws Exception {
		test = "* Testing delete (TST)                    *\n";
		testMethod(2, false);
	}
	
	@Test
	public void testContainsTST() throws Exception {
		test = "* Testing contains (TST)                  *\n";
		testMethod(1, false);
	}
	
	@Test
	public void testAddTST() throws Exception {
		test = "* Testing add (TST)                       *\n";
		testMethod(0, false);
	}
}