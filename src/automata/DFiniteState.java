package automata;

import java.util.ArrayList;
import java.util.HashMap;

public class DFiniteState {
    private static int nextId = 1;
    private HashMap<String, DFiniteState> transitions;
    private int id;

    public DFiniteState() {
        transitions = new HashMap<String, DFiniteState>();
        id=nextId;
        nextId++;
    }

    public boolean addTransition(DFiniteState state, String input) {
        DFiniteState currentTransition = transitions.get(input);
        if (currentTransition == null) {
            transitions.put(input, state);
            return true;
        } else return false;
    }

    public ArrayList<DFiniteState> getChildren() {
        ArrayList<DFiniteState> children = new ArrayList<>();
        for (DFiniteState transition : transitions.values()) {
            children.add(transition);
        }
        return children;
    }

    public HashMap<String, DFiniteState> getTransitions() {
        return transitions;
    }

    public int getId(){
        return id;
    }

    @Override
    public boolean equals(Object o){
        if(o==null)
            return false;

        DFiniteState state;
        if(o instanceof DFiniteState)
            state = (DFiniteState) o;
        else return false;

        return id==state.getId();
    }
}