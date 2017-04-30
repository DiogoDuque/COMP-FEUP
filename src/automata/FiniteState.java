package automata;

import java.util.ArrayList;
import java.util.HashMap;
public class FiniteState {
    private HashMap<String, ArrayList<FiniteState>> transitions;

    public FiniteState() {
        transitions = new HashMap<String, ArrayList<FiniteState>>();
    }

    public void addTransition(FiniteState state, String input) {
        ArrayList<FiniteState> currentTransitions = transitions.get(input);
        if (currentTransitions == null) {
            ArrayList<FiniteState> next = new ArrayList<>();
            next.add(state);
            transitions.put(input, next);
        } else {
            currentTransitions.add(state);
        }
    }

    public HashMap<String, ArrayList<FiniteState>> getTransitions() {
        return transitions;
    }

    public void merge(FiniteState other) {
        HashMap<String, ArrayList<FiniteState>> others = other.getTransitions();
        for (String key : others.keySet()) {
            ArrayList<FiniteState> value = others.get(key);
            if (transitions.get(key) == null) {
                transitions.put(key, value);
            } else {
                for (FiniteState state : value) {
                    transitions.get(key).add(state);
                }
            }
        }
    }

    public void dump(String prefix) {
        System.out.println(prefix + "#State");
        for (String input : transitions.keySet()) {
            System.out.println(prefix + "#Transition <" + input + ">:");
            ArrayList<FiniteState> nexts = transitions.get(input);
            for (FiniteState next : nexts) {
                next.dump(prefix + "  ");
            }
        }
    }
}