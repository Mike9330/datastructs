package hw3;

import java.util.*;

public class HW3Test {
	
	private static class Entry {
		String word;
		int[] indexes;
		
		public Entry(String word, int[] indexes)
		{
			this.word = word;
			this.indexes = indexes;
		}
	}
	
	private final static Entry[] entries = new Entry[20];
	private final static int[][] cases = new int[10][];
	private static boolean passed;
	private static Indexes test;
	private static LinkedList<Operation> exec;
	
	private static class Operation {
		final int op;
		final String word;
		final Integer index;
		
		public Operation(int op, String word, Integer index) {
			this.op = op;
			this.word = word;
			this.index = index;
		}
		
		public String toString() {
			switch (op) {
			case 0: return "addIndex(" + word + ", " + index + ");";
			case 1: return "getIndexes(" + word + ");";
			case 2: return "remIndex(" + word + ", " + index + ");";
			case 3: return "contains(" + word + ");";
			case 4: return "numPairs();";
			}
			return null;
		}
	}
	
	private static void wrongResult(Object result) {
		System.out.println("Incorrect value " + result + " after executing the following calls");
		for (Operation o : exec) {
			System.out.println(o);
		}
		passed = false;
	}
	
	private static void printIntroMessage(String methodStr) {
		passed = true;
		System.out.println("Testing " + methodStr);
	}
	
	private static void printPassMessage(String methodStr) {
		System.out.print("Done testing " + methodStr + "...");
		if (passed) {
			System.out.println("all tests passed!");
		} else {
			System.out.println("some tests failed!");
		}
		System.out.println();
		System.out.println("**********");
		System.out.println();
	}
	
	private static void reset() {
		exec = new LinkedList<Operation>();
		test = new BalancedInd();
	}
	
	private static void addIndex(int caseId, int entryId, int indexId) {
		Entry e = entries[cases[caseId][entryId]];
		int i = e.indexes[indexId];
		exec.add(new Operation(0, e.word, i));
		test.addIndex(e.word, i);
	}
	
	private static Iterable<Integer> getIndexes(String w) {
		exec.add(new Operation(1, w, null));
		return test.getIndexes(w);
	}
	
	private static void remIndex(int caseId, int entryId, int indexId) {
		Entry e = entries[cases[caseId][entryId]];
		int i = e.indexes[indexId];
		exec.add(new Operation(2, e.word, i));
		test.remIndex(e.word, i);
	}
	
	private static boolean contains(String word) {
		exec.add(new Operation(3, word, null));
		return test.contains(word);
	}
	
	private static int numPairs() {
		exec.add(new Operation(4, null, null));
		return test.numPairs();
	}
	
	private static boolean verifyIndexes(Iterable<Integer> indxs, int ind[], int len) {
		if (ind == null || len == 0) return (indxs == null);
		if (indxs == null) return false;
		int ct[] = new int[len];
		for (Integer i : indxs) {
			boolean found = false;
			for (int d = 0; d < len; d++) {
				if (ind[d] == i) {
					found = true;
					ct[d]++;
					break;
				}
			}
			if (!found) return false;
		}
		for (int d = 0; d < len; d++) {
			if (ct[d] != 1) return false;
		}
		return true;
	}

	private static void testRemIndex() {
		Entry entry;
		for (int c = 0; c < cases.length; c++) {
			reset();
			for (int e = 0; e < cases[c].length; e++) {
				entry = entries[cases[c][e]];
				for (int i = 0; i < entry.indexes.length; i++) {
					addIndex(c, e, i);
				}
			}
			for (int e = 0; e < cases[c].length; e++) {
				entry = entries[cases[c][e]];
				for (int i = entry.indexes.length; i > 0; i--) {
					Iterable<Integer> indxs = getIndexes(entry.word);
					if (!verifyIndexes(indxs, entry.indexes, i)) {
						wrongResult(indxs);
						return;
					}
					if (i > 1) remIndex(c, e, i - 1);
				}
			}
			for (int e = 0; e < cases[c].length; e++) {
				entry = entries[cases[c][e]];
				remIndex(c, e, 0);
				remIndex(c, e, 0);
				Iterable<Integer> indxs = getIndexes(entry.word);
				if (!verifyIndexes(indxs, null, 0)) {
					wrongResult(indxs);
					return;
				}
			}
		}
	}
	
	private static void testGetIndexes() {
		Entry entry;
		for (int c = 0; c < cases.length; c++) {
			reset();
			for (int e = 0; e < cases[c].length; e++) {
				entry = entries[cases[c][e]];
				for (int i = 0; i < entry.indexes.length; i++) {
					addIndex(c, e, i);
				}
				Iterable<Integer> indxs = getIndexes(entry.word);
				if (!verifyIndexes(indxs, entry.indexes, entry.indexes.length)) {
					wrongResult(indxs);
					return;
				}
			}
		}
	}

	private static void testContains() {
		Entry entry;
		for (int c = 0; c < cases.length; c++) {
			reset();
			for (int e = 0; e < cases[c].length; e++) {
				entry = entries[cases[c][e]];
				boolean cont = contains(entry.word);
				if (cont) {
					wrongResult(cont);
					return;
				}
				for (int i = 0; i < entry.indexes.length; i++) {
					addIndex(c, e, i);
				}
				for (int d = 0; d <= e; d++) {
					entry = entries[cases[c][d]];
					cont = contains(entry.word);
					if (!cont) {
						wrongResult(cont);
						return;
					}
				}
			}
		}
	}
	
	private static void testNumPairs() {
		Entry entry;
		for (int c = 0; c < cases.length; c++) {
			int t = 0;
			reset();
			for (int e = 0; e < cases[c].length; e++) {
				entry = entries[cases[c][e]];
				addIndex(c, e, 0);
				for (int i = 0; i < entry.indexes.length; i++, t++) {
					addIndex(c, e, i);
				}
				int n = numPairs();
				if (n != t) {
					wrongResult(n);
					return;
				}
			}
		}
	}
	
	private static void testMethod(int methodId) {
		String methodStr[] = { "numPairs", "contains", "getIndexes", "remIndex" };
		
		printIntroMessage(methodStr[methodId]);
		try {
			switch(methodId) {
			case 0: testNumPairs(); break;
			case 1: testContains(); break;
			case 2: testGetIndexes(); break;
			case 3: testRemIndex(); break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			System.out.flush();
			passed = false;
		}
		printPassMessage(methodStr[methodId]);
	}

	public static void main(String args[])
	{
		initializeEntries();
		
		cases[0] = new int[]{ };
		cases[1] = new int[]{ 0 };
		cases[2] = new int[]{ 2, 4 };
		cases[3] = new int[]{ 1, 0, 3, 14, 15 };
		cases[4] = new int[]{ 9, 0, 6, 16, 13, 17, 18 };
		cases[5] = new int[]{ 0, 3, 7, 8, 12, 14 };
		cases[6] = new int[]{ 4, 3, 18, 19 };
		cases[7] = new int[]{ 6, 8, 2, 0, 3, 9, 13, 15, 17, 11, 10, 16 };
		cases[8] = new int[]{ 8, 0, 3, 4, 5, 11, 17, 19, 14, 13 };
		cases[9] = new int[]{ 5, 2, 4, 0, 3, 7, 1, 6, 8, 9, 10, 12, 11, 15, 14, 13, 18, 19 };
		
		for (int i = 0; i < 4; i++) {
			testMethod(i);
		}
	}
	
	private static void initializeEntries()
	{
		entries[0] = new Entry("A", new int[]{2,23,14,58});
		entries[1] = new Entry("C", new int[]{4,47,82,109});
		entries[2] = new Entry("E", new int[]{12,5,8,23,43,65});
		entries[3] = new Entry("H", new int[]{125});
		entries[4] = new Entry("K", new int[]{89,65,23,11});
		entries[5] = new Entry("P", new int[]{84,32,4});
		entries[6] = new Entry("S", new int[]{12,32});
		entries[7] = new Entry("U", new int[]{19,21,43,45,76,23,12,54});
		entries[8] = new Entry("X", new int[]{5,32,234,12,54,76});
		entries[9] = new Entry("Z", new int[]{4,37,85,9});
		entries[10] = new Entry("B", new int[]{4,7,8,12});
		entries[11] = new Entry("D", new int[]{123,7,43,92});
		entries[12] = new Entry("F", new int[]{76,43,3,23,54});
		entries[13] = new Entry("I", new int[]{134});
		entries[14] = new Entry("J", new int[]{221,3});
		entries[15] = new Entry("Q", new int[]{6,165,134,21});
		entries[16] = new Entry("ST", new int[]{177,162,194,146,124});
		entries[17] = new Entry("W", new int[]{143,154});
		entries[18] = new Entry("Y", new int[]{184,83,92,3,8,22,103});
		entries[19] = new Entry("ZZZ", new int[]{6});
	}
}