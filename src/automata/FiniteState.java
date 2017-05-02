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

    public void merge(FiniteState other) {
        HashMap<String, ArrayList<FiniteState>> others = other.getTransitions();
        transitions.putAll(others);
    }

    public ArrayList<FiniteState> getChildren() {
        ArrayList<FiniteState> children = new ArrayList<>();
        for (ArrayList<FiniteState> transition : transitions.values()) {
            children.addAll(transition);
        }
        return children;
    }

    public HashMap<String, ArrayList<FiniteState>> getTransitions() {
        return transitions;
    }
}