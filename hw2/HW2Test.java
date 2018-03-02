package hw2;

public class HW2Test {
	private static class Entry {
		String key;
		Integer val;
		
		public Entry(String key, Integer val) {
			this.key = key;
			this.val = val;
		}
	}
	
	private final static Entry[] entries = new Entry[20];
	private final static int[][] cases = new int[10][];
	private final static String[][] keys = new String[10][];
	private static boolean passed;
	
	private static void wrongResult(String case_id, String result) {
		System.out.println("Incorrect " + result + " for " + case_id);
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
	
	private static BST<String, Integer> newBST(int caseId) {
		BST<String, Integer> list = new BST<String, Integer>();
		for (int i = 0; i < cases[caseId].length; i++) {
			list.put(entries[cases[caseId][i]].key, entries[cases[caseId][i]].val);
		}
		return list;
	}
	
	private static String bstStr(int i) {
		if (i == 0) return "---";
		return newBST(i).toString();
	}

	public static void testNextNode() {
		int j = 0;
		for (int i = 1; i < cases.length; i++) {
			j = 0;
			String prev = null;
			boolean p = true;
			for (String result : newBST(i)) {
				if (j >= keys[i].length) {
					wrongResult("bst.nextNode(" + prev + ") where bst = " + bstStr(i), "return != null (too many keys, total > " + keys[i].length + ")");
					p = false;
					break;
				}
				if (!result.equals(keys[i][j])) {
					wrongResult("bst.nextNode(" + prev + ") where bst = " + bstStr(i), "return = " + result);
					p = false;
					break;
				}
				prev = result;
				j++;
			}
			if (p && j != keys[i].length) {
				wrongResult("bst.nextNode(" + prev + ") where bst = " + bstStr(i), "return = null");
			}
		}
	}
	
	public static void testLevelSize() {
		int result;
		int answers[][] = { { 0, 0, 0, 0, 0 }, // case 0
	                        { 1, 0, 0, 0, 0 }, // case 1
	                        { 1, 1, 0, 0, 0 }, // case 2
	                        { 1, 2, 1, 1, 0 }, // case 3
	                        { 1, 1, 1, 2, 1 }, // case 4
	                        { 1, 1, 2, 2, 0 }, // case 5
	                        { 1, 2, 1, 0, 0 }, // case 6
	                        { 1, 2, 4, 2, 1 }, // case 7
	                        { 1, 2, 1, 2, 1 }, // case 8
	                        { 1, 2, 4, 3, 4 }, // case 9
	                      };
		
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < 5; j++) {
				result = newBST(i).sizeOfLevel(j);
				if (result != answers[i][j]) {
					wrongResult("bst.sizeOfLevel(" + j + ") where bst = " + bstStr(i), "return = " + result);
					break;
				}
			}
		}
	}

	public static void testHeight() {
		int result;
		int answers[] = { -1, 0, 1, 3, 4, 3, 2, 4, 5, 4 };  
		
		for (int i = 0; i < cases.length; i++) {
			result = newBST(i).height();
			if (result != answers[i]) wrongResult("bst.height() where bst = " + bstStr(i), "return = " + result);
		}
	}

	public static void testDelete() {
		BST<String, Integer> bst;
		String delkeys[] = new String[]{ "C", "J", "Q", "Z" };
		
		for (int i = 0; i < cases.length; i++) {
			bst = newBST(i);
			for (int j = 0; j < delkeys.length; j++) {
				bst.deleteAllLessThan(delkeys[j]);
				boolean error = false;
				for (int k = 0; k < keys[i].length; k++) {
					Integer val = bst.get(keys[i][k]);
					error = (keys[i][k].compareTo(delkeys[j]) < 0) != (val == null);
					if (error) {
						bst = newBST(i);
						System.out.println("After the call bst.deleteAllLessThan(" + delkeys[0] + ") where bst = " + bstStr(i));
						for (int jj = 0; jj < j; jj++) {
							bst.deleteAllLessThan(delkeys[jj]);
							System.out.println("After the call bst.deleteAllLessThan(" + delkeys[jj + 1] + ") where bst = " + bst);
						}
						wrongResult("get(" + keys[i][k] + ")", "return " + (val == null ? "==" : "!=") + " null");
						break;
					}
				}
				if (error) break;
			}
		}
	}
	
	public static void testMerge() {
		BST<String, Integer> list, arg, orig;
		String errorStr;
		
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < cases.length; j++) {
				orig = newBST(i);
				list = newBST(i);
				arg = newBST(j);
				list.merge(newBST(j));
				errorStr = "";
				String key = "";
				Integer val, vmrg = null;
				for (int k = 0; k < keys[i].length; k++) {
					key = keys[i][k];
					val = orig.get(key);
					vmrg = list.get(key);
					Integer varg = arg.get(key);
					if (varg != null) val = varg;
					if (vmrg == null) {
						errorStr = key + " should not be deleted in the merge";
						break;
					}
					if (!vmrg.equals(val)) {
						errorStr = "correct value should be " + val;
						break;
					}
				}
				if (errorStr != "") {
					wrongResult("get(" + key + ") after this.merge(bst), where this = " + bstStr(i) + " and bst = " + bstStr(j) + " : " + errorStr, "return = " + vmrg);
					break;
				}
				for (int k = 0; k < keys[j].length; k++) {
					key = keys[j][k];
					val = arg.get(key);
					vmrg = list.get(key);
					if (vmrg == null) {
						errorStr = key + " should be in the merge";
						break;
					}
					if (!vmrg.equals(val)) {
						errorStr = "correct value should be updated to " + val;
						break;
					}
				}
				if (errorStr != "") {
					wrongResult("get(" + key + ") after this.merge(bst), where this = " + bstStr(i) + " and bst = " + bstStr(j) + " : " + errorStr, "return = " + vmrg);
					break;
				}
			}
		}
	}
	
	private static void testMethod(int methodId) {
		String methodStr[] = { "nextNode", "sizeOfLevel", "height", "deleteAllLessThan", "merge" };
		
		printIntroMessage(methodStr[methodId]);
		try {
			switch(methodId) {
			case 0: testNextNode(); break;
			case 1: testLevelSize(); break;
			case 2: testHeight(); break;
			case 3: testDelete(); break;
			case 4: testMerge(); break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			System.out.flush();
			passed = false;
		}
		printPassMessage(methodStr[methodId]);
	}

	public static void main(String[] args) {
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
		keys[0] = new String[] { }; 
		keys[1] = new String[] { "A" }; 
		keys[2] = new String[] { "E", "K" }; 
		keys[3] = new String[] { "A", "C", "H", "K", "P" }; 
		keys[4] = new String[] { "A", "H", "S", "W", "Y", "Z" }; 
		keys[5] = new String[] { "A", "F", "H", "K", "U", "X" }; 
		keys[6] = new String[] { "H", "K", "Y", "Z" }; 
		keys[7] = new String[] { "A", "B", "D", "E", "H", "P", "S", "W", "X", "Z" }; 
		keys[8] = new String[] { "A", "D", "H", "K", "P", "W", "X", "Z" }; 
		keys[9] = new String[] { "A", "B", "C", "D", "E", "F", "H", "K", "P", "S", "U", "X", "Y", "Z" };
		
		for (int i = 0; i < 5; i++) {
			testMethod(i);
		}
	}
	
	private static void initializeEntries() {
		entries[0] = new Entry("A", 5);
		entries[1] = new Entry("C", 4);
		entries[2] = new Entry("E", 2);
		entries[3] = new Entry("H", 3);
		entries[4] = new Entry("K", -5);
		entries[5] = new Entry("P", -1);
		entries[6] = new Entry("S", 0);
		entries[7] = new Entry("U", 5);
		entries[8] = new Entry("X", 3);
		entries[9] = new Entry("Z", 9);
		entries[10] = new Entry("B", -5);
		entries[11] = new Entry("D", 3);
		entries[12] = new Entry("F", 10);
		entries[13] = new Entry("H", 5);
		entries[14] = new Entry("K", 3);
		entries[15] = new Entry("P", 1);
		entries[16] = new Entry("S", 10);
		entries[17] = new Entry("W", 5);
		entries[18] = new Entry("Y", 3);
		entries[19] = new Entry("Z", 2);
	}
}