

import java.util.LinkedList;
import java.util.function.Predicate;

/*
 * SD2x Homework #1
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the signature of any of the methods!
 */

public class LinkedListUtils {

	public static void insertSorted(LinkedList<Integer> list, int value) {
		if (list == null) {
			return;
		}

		// Value is simply added to the list when no other values are present.
		if (list.isEmpty()) {
			list.add(value);
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			Integer currentValue = list.get(i);
			if (value <= currentValue) {
				list.add(i, value);
				return;
			}
		}

		// Value is added to the end of the list otherwise.
		list.add(value);
	}

	public static void removeMaximumValues(LinkedList<String> list, int N) {
		// Check if list is null, or if N is a non-positive number.
		if (list == null || N <= 0) {
			return;
		}

		if (N > list.size()) {
			list.removeAll(list);
			return;
		}

		for (int i = 0; i < N; i++) {
			String maxValue = getMaxValue(list);
			removeAllByValue(list, maxValue);
		}
	}

	private static String getMaxValue(LinkedList<String> list) {
		String maxValue = "";
		for (String currentValue : list) {
			// If currentValue is lexicographically greater than maxValue.
			if (currentValue.compareTo(maxValue) > 0) {
				maxValue = currentValue;
			}
		}

		return maxValue;
	}

	private static void removeAllByValue(LinkedList<String> list, String value) {
		for (int i = 0; i < list.size(); i++) {
			String element = list.get(i);
			if (element == value) {
				list.remove(i);
			}
		}
	}
	
	public static boolean containsSubsequence(LinkedList<Integer> one, LinkedList<Integer> two) {
		if (one == null || two == null) {
			return false;
		}

		if (one.isEmpty() || two.isEmpty()) {
			return false;
		}

		int oneSize = one.size();
		int twoSize = two.size();
		// This number will be greater than 0 when the size of the second list
		// does not divide evenly into the first.
		int extraneous = oneSize % twoSize;
		// Ensures that there is no unnecessary looping by ignoring extraneous values.
		int loopLength = oneSize - extraneous;
		for (int i = 0; i < loopLength; i++) {
			boolean isSubsequence = true;
			for (int j = 0; j < twoSize; j++) {
				if (one.get(i + j) != two.get(j)) {
					isSubsequence = false;
					break;
				}
			}

			if (isSubsequence) {
				return true;
			}
		}

		return false;
	}
}
