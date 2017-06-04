package cflow;

import java.util.*;

public class Cflow {
	public static int currentState = 1;

	public static int initialState = 1;

	public static ArrayList<Integer> finalStates;
	static {
		finalStates = new ArrayList<>();
		finalStates.add(6);
	}

	public static Map<Integer, Map<String, Integer>> transitions;
	static {
		transitions = new HashMap<>();

		HashMap<String, Integer> transitions1 = new HashMap<>();
		transitions1.put("A", 0);
		transitions.put(1, transitions1);

		HashMap<String, Integer> transitions5 = new HashMap<>();
		transitions5.put("B", 2);
		transitions5.put("F", 6);
		transitions.put(5, transitions5);

		HashMap<String, Integer> transitions6 = new HashMap<>();
		transitions.put(6, transitions6);

		HashMap<String, Integer> transitions3 = new HashMap<>();
		transitions3.put("E", 5);
		transitions.put(3, transitions3);

		HashMap<String, Integer> transitions2 = new HashMap<>();
		transitions2.put("C", 3);
		transitions2.put("D", 4);
		transitions.put(2, transitions2);

		HashMap<String, Integer> transitions4 = new HashMap<>();
		transitions4.put("E", 7);
		transitions.put(4, transitions4);

		HashMap<String, Integer> transitions0 = new HashMap<>();
		transitions0.put("B", 2);
		transitions.put(0, transitions0);

		HashMap<String, Integer> transitions7 = new HashMap<>();
		transitions7.put("B", 2);
		transitions7.put("F", 6);
		transitions.put(7, transitions7);
	}

	public static void next(String input) {
		if (transitions.get(currentState) != null && transitions.get(currentState).get(input) != null) {
			currentState = transitions.get(currentState).get(input);
		} else {
			currentState = -1;
		}
	}

	public static void success() {
		if (finalStates.contains(currentState)) {
			System.out.println("[CFLOW: Success]");
		} else {
			System.out.println("[CFLOW: Failed]");
		}
	}
}
