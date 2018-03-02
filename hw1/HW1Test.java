package hw1;

public class HW1Test {
	private static String[] countries = new String[] {"New Guinea", "Netherlands", "Germany", "New Zealand", "Brazil", "Austria", "United States", "United Kingdom", "Turkey", "Mexico", "Canada", "India", "Barbados", "Australia", "Albania"};
	private static boolean passed;
	private static String keys[];
	private static StringValueST<Integer> st;
	
	private static void printIntroMessage(String methodStr) {
		passed = true;
		System.out.println("**********");
		System.out.println();
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
	
	private static StringValueST<Integer> buildDict(int type) {
		if (type == 0) {
			st = new LinkedListST<Integer>();
		} else {
			st = new SortedArrayST<Integer>();
		}
		
		int size = countries.length;
		keys = new String[size];
		for (int i = 0; i < size; i++) {
			keys[i] = countries[i];
			st.put(keys[i], i);
		}
		return st;
	}
	
	public static String dictToString(StringValueST<Integer> st) {
		boolean first = true;
		StringBuilder s = new StringBuilder("{ ");
		for (String key : st) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}
			s.append(key + ":" + st.get(key));
		}
		s.append(" }");
		return s.toString();
	}
	
	private static void wrongResult(String case_id, String result) {
		System.out.println("Incorrect " + result + " for " + case_id);
		passed = false;
	}

	public static void testRank(int type) {
		String keys[] = { "A", "B", "China", "fr", "New", "S", "United States", "Z"};
		int result;
		int answers[] = { 0, 3, 6, 15, 10, 12, 14, 15 };
		
		buildDict(type);
		for (int j = 0; j < keys.length; j++) {
			result = st.rank(keys[j]);
			if (result != answers[j]) {
				wrongResult("rank(" + keys[j] + ")", "" + result);
				break;
			}
		}
	}

	public static void testFirst(int type) {
		String keys[] = { "A", "B", "China", "fr", "New", "S", "United States", "Z"};
		String result;
		String answers[] = { "Albania", "Barbados", null, null, "New Guinea", null, "United States", null };
		
		buildDict(type);
		for (int j = 0; j < keys.length; j++) {
			result = st.firstStartingWith(keys[j]);
			if (answers[j] == null ? result != null : !answers[j].equals(result)) {
				wrongResult("firstStartingWith(" + keys[j] + ")", result);
				break;
			}
		}
	}

	public static void testDelete(int type) {
		String keys[] = { "A", "B", "China", "fr", "New", "S", "United States", "Z"};
		
		buildDict(type);
		StringValueST<Integer> st2 = buildDict(type);
		for (int j = 0; j < keys.length; j++) {
			st.deleteAllStartingWith(keys[j]);
			
			for (String key : st2) {
				Integer val = st.get(key);
				if (key.startsWith(keys[j]) && val != null) {
					wrongResult("deleteAllStartingWith(" + keys[j] + ")", "(see below)");
					break;
				} else if (!key.startsWith(keys[j]) && (val == null || !val.equals(st2.get(key)))) {
					wrongResult("deleteAllStartingWith(" + keys[j] + ")", "(see below)");
					break;
				}
			}
			
			if (!passed) break;
			st2.deleteAllStartingWith(keys[j]);
		}
		
		if (!passed) {
			System.out.println("Before deletion: " + dictToString(st2));
			System.out.println("After deletion: " + dictToString(st));
		}
	}
	
	private static void testMethod(int typeId, int methodId) {
		String typeStr[] = { "(LinkedListST)", "(SortedArrayST)" };
		String methodStr[] = { "rank", "firstStartingWith", "deleteAllStartingWith" };
		
		printIntroMessage(methodStr[methodId] + typeStr[typeId]);
		try {
			switch(methodId) {
			case 0: testRank(typeId); break;
			case 1: testFirst(typeId); break;
			case 2: testDelete(typeId); break;
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
		buildDict(0);
		System.out.println("Dictionary used for testing is below (LinkedList version): ");
		System.out.println(dictToString(st));
		System.out.println();
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				testMethod(i, j);
			}
		}
	}
}