package automata;

import java.util.ArrayList;
import java.util.HashMap;
public class NFiniteState {
    private HashMap<String, ArrayList<NFiniteState>> transitions;

    public NFiniteState() {
        transitions = new HashMap<String, ArrayList<NFiniteState>>();
    }

    public void addTransition(NFiniteState state, String input) {
        ArrayList<NFiniteState> currentTransitions = transitions.get(input);
        if (currentTransitions == null) {
            ArrayList<NFiniteState> next = new ArrayList<>();
            next.add(state);
            transitions.put(input, next);
        } else {
            currentTransitions.add(state);
        }
    }

    public void merge(NFiniteState other) {
        HashMap<String, ArrayList<NFiniteState>> others = other.getTransitions();
        transitions.putAll(others);
    }

    public ArrayList<NFiniteState> getChildren() {
        ArrayList<NFiniteState> children = new ArrayList<>();
        for (ArrayList<NFiniteState> transition : transitions.values()) {
            children.addAll(transition);
        }
        return children;
    }

    public HashMap<String, ArrayList<NFiniteState>> getTransitions() {
        return transitions;
    }
}